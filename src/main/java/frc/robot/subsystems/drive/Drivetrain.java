package frc.robot.subsystems.drive;

// import java.lang.Thread;
import java.util.Optional;
import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import com.kauailabs.navx.frc.AHRS;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.PathPlannerPath;
import com.pathplanner.lib.util.ReplanningConfig;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkBase.IdleMode;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants;
import frc.robot.Constants.DrivetrainConstants;
import frc.robot.Constants.IOConstants;

public class Drivetrain extends SubsystemBase {

    
    private  AddressableLED ledStrip;
    private  AddressableLEDBuffer ledBuffer;

    private final CANSparkMax m_leftMotor;
    private final CANSparkMax m_leftMotorFollower;

    private final CANSparkMax m_rightMotor;
    private final CANSparkMax m_rightMotorFollower;

    private final RelativeEncoder m_leftEncoders;
    private final RelativeEncoder m_rightEncoders;

    private final SlewRateLimiter m_driveSlewLimiter;

    private final DifferentialDrive m_robotDrive;

    private final AHRS m_navx;

    private final DifferentialDriveOdometry m_odometry;

    public Drivetrain() {

        ledStrip = new AddressableLED(0);
        ledStrip.setLength(Constants.IOConstants.StripLEDLength);
        ledBuffer = new AddressableLEDBuffer(30);
        ledStrip.setData(ledBuffer);
        ledStrip.start();

     

        m_leftMotor = new CANSparkMax(DrivetrainConstants.kLMotorFrontCanId, MotorType.kBrushless);
        m_leftMotorFollower = new CANSparkMax(DrivetrainConstants.kLMotorRearCanId, MotorType.kBrushless);
        m_leftMotorFollower.follow(m_leftMotor);

        m_leftMotor.setIdleMode(IdleMode.kBrake);
        m_leftMotorFollower.setIdleMode(IdleMode.kBrake);
        m_leftMotor.burnFlash();
        m_leftMotorFollower.burnFlash();

        m_leftEncoders = m_leftMotor.getEncoder();
        
        
        m_rightMotor = new CANSparkMax(DrivetrainConstants.kRMotorFrontCanId, MotorType.kBrushless);
        m_rightMotorFollower = new CANSparkMax(DrivetrainConstants.kRMotorRearCanId, MotorType.kBrushless);
        m_rightMotor.setInverted(true);
        m_rightMotorFollower.follow(m_rightMotor);

        m_rightMotor.setIdleMode(IdleMode.kBrake);
        m_rightMotorFollower.setIdleMode(IdleMode.kBrake);
        m_rightMotor.burnFlash();
        m_rightMotorFollower.burnFlash();

        m_rightEncoders = m_rightMotor.getEncoder();

        // ---------------------------------------------- \\
        // Set's the Strip to Yellow to let us know it works.
        setLEDsYellow();
      

        m_driveSlewLimiter = new SlewRateLimiter(0.5);
        m_robotDrive = new DifferentialDrive(m_leftMotor, m_rightMotor);
        m_robotDrive.setSafetyEnabled(false); // If you dont do this your robot will disable after a while of not sending the motors commands

        m_navx = new AHRS(SPI.Port.kMXP); // Creates a new NavX instance that is on your RoboRio MXP port

        m_odometry = new DifferentialDriveOdometry( // Odometry tracks your robots position in space, you can see this with a Field2d
            getHeading(),
            m_leftEncoders.getPosition(),
            m_rightEncoders.getPosition()
        );


        AutoBuilder.configureRamsete( // Configuring PathPlannerLib, Autobuilder handles manipulating your robot from the Pathplanner JSON files it creates 
            this::getPose, // Gets your robots position in space (odometry)
            this::resetPose, // Resets your odometry 
            this::getChassisSpeeds, // Gets the velocity of your robot 
            this::driveByChassisSpeeds, // Drive your robot from input velocities
            new ReplanningConfig(), 
            () -> {
                Optional<Alliance> alliance = DriverStation.getAlliance(); // This gets your alliance set in the FRC Driver Station, if you're Red 
                if (alliance.isPresent()) {                                // it will flip the input paths if its Blue it will not flip the paths.
                    return alliance.get() == DriverStation.Alliance.Red;
                }
                return false;
            },
            this
        );
    }

    @Override
    public void periodic() {
        m_odometry.update( // Every periodic cycle you update your odometry
            getHeading(), 
            m_leftEncoders.getPosition(),
            m_rightEncoders.getPosition()
        );
    }

    public Command teleopDrive(DoubleSupplier speed, DoubleSupplier rot, BooleanSupplier speedLimiter) {
        return run(() -> { // This is a Command factory that uses a lambda that you feed into your CommandXboxController
            double xSpeed = -MathUtil.applyDeadband(m_driveSlewLimiter.calculate(speed.getAsDouble()), Constants.IOConstants.kDriverDeadband); // Deadband battles stick drift and
            double zRotation = -MathUtil.applyDeadband(m_driveSlewLimiter.calculate(rot.getAsDouble()), Constants.IOConstants.kDriverDeadband); // adds a area on your joystick 
                                                                                                                                                // that's "dead" or ignored
            if (speedLimiter.getAsBoolean()) { // If speedLimiter is true then divide the output to your motors for more precise movements when needed
                xSpeed = xSpeed / IOConstants.kSpeedLimiter;
                zRotation = zRotation / IOConstants.kSpeedLimiter;
            }

            m_robotDrive.arcadeDrive(xSpeed, zRotation);
        });
    }

    public Command followPath(PathPlannerPath path) { // This follows an inputed PathPlannerPath with your AutoBuilder
        return AutoBuilder.followPath(path);
    }

    public void driveByChassisSpeeds(ChassisSpeeds speeds) { // Drive your robot with a ChassisSpeeds    
        m_leftMotor.set(speeds.vxMetersPerSecond);
        m_rightMotor.set(speeds.vxMetersPerSecond);
    }

    public Pose2d getPose() { // Get pose from odometry
        return m_odometry.getPoseMeters();
    }

    public void resetPose(Pose2d pose) { // Resets your odometry
        resetEncoders();
        m_odometry.resetPosition(
            getHeading(),
            m_leftEncoders.getPosition(),
            m_rightEncoders.getPosition(),
            pose
        );
    }

       private void setLEDsYellow() {
            for (int i = 0; i < ledBuffer.getLength(); i++) {
                int redValue = 255;
                int greenValue = 251;
                int blueValue = 0;
                ledBuffer.setRGB(i, redValue, greenValue, blueValue);
            }
            ledStrip.setData(ledBuffer);
        }

    public Rotation2d getHeading() { // Gets your NavX's heading
        return m_navx.getRotation2d();
    }

    public void resetEncoders() { // Reset your encoders
        m_leftEncoders.setPosition(0);
        m_rightEncoders.setPosition(0);
    }

    public void resetGyro() { // Resets your robot heading 
        m_navx.reset();
    }

    public ChassisSpeeds getChassisSpeeds() { // Get Chassis velocity
        double leftWheelSpeed = m_leftEncoders.getVelocity();
        double rightWheelSpeed = m_rightEncoders.getVelocity();

        double angularVelocity = (rightWheelSpeed - leftWheelSpeed) / Constants.DrivetrainConstants.kDrivetrainWidthMeters; // Get your angular velocity of your robot

        return new ChassisSpeeds( // Creates a new ChassisSpeeds from your robots velocities.
            (leftWheelSpeed + rightWheelSpeed) / 2.0,
            0.0,
            angularVelocity
        );
    }

    public static void m_robotDrive(double d, double e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'm_robotDrive'");
    }
}