package frc.robot.subsystems;

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
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.DrivetrainConstants;
import frc.robot.Constants.IOConstants;

public class Drivetrain extends SubsystemBase {
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
      
        m_driveSlewLimiter = new SlewRateLimiter(0.5);

        m_robotDrive = new DifferentialDrive(m_leftMotor, m_rightMotor);
        m_robotDrive.setSafetyEnabled(false);

        m_navx = new AHRS(SPI.Port.kMXP);

        m_odometry = new DifferentialDriveOdometry(
            getHeading(),
            m_leftEncoders.getPosition(),
            m_rightEncoders.getPosition()
        );


        AutoBuilder.configureRamsete(
            this::getPose,
            this::resetPose,
            this::getChassisSpeeds,
            this::driveByChassisSpeeds,
            new ReplanningConfig(),
            () -> {
                Optional<Alliance> alliance = DriverStation.getAlliance();
                if (alliance.isPresent()) {
                    return alliance.get() == DriverStation.Alliance.Red;
                }
                return false;
            },
            this
        );
    }

    @Override
    public void periodic() {
        m_odometry.update(
            getHeading(), 
            m_leftEncoders.getPosition(),
            m_rightEncoders.getPosition()
        );
    }

    public Command teleopDrive(DoubleSupplier speed, DoubleSupplier rot, BooleanSupplier speedLimiter) {
        return run(() -> {
            double xSpeed = -MathUtil.applyDeadband(m_driveSlewLimiter.calculate(speed.getAsDouble()), Constants.IOConstants.kDriverDeadband);
            double zRotation = -MathUtil.applyDeadband(m_driveSlewLimiter.calculate(rot.getAsDouble()), Constants.IOConstants.kDriverDeadband);

            if (speedLimiter.getAsBoolean()) {
                xSpeed = xSpeed / IOConstants.kSpeedLimiter;
                zRotation = zRotation / IOConstants.kSpeedLimiter;
            }

            m_robotDrive.arcadeDrive(xSpeed, zRotation);
        });
    }

    public Command followPath(PathPlannerPath path) {
        return AutoBuilder.followPath(path);
    }

    public void driveByChassisSpeeds(ChassisSpeeds speeds) {        
        m_leftMotor.set(speeds.vxMetersPerSecond);
        m_rightMotor.set(speeds.vxMetersPerSecond);
    }

    public Pose2d getPose() {
        return m_odometry.getPoseMeters();
    }

    public void resetPose(Pose2d pose) {
        resetEncoders();
        m_odometry.resetPosition(
            getHeading(),
            m_leftEncoders.getPosition(),
            m_rightEncoders.getPosition(),
            pose
        );
    }

    public Rotation2d getHeading() {
        return m_navx.getRotation2d();
    }

    public void resetEncoders() {
        m_leftEncoders.setPosition(0);
        m_rightEncoders.setPosition(0);
    }

    public void resetGyro() {
        m_navx.reset();
    }

    public ChassisSpeeds getChassisSpeeds() {
        double leftWheelSpeed = m_leftEncoders.getVelocity();
        double rightWheelSpeed = m_rightEncoders.getVelocity();

        double angularVelocity = (rightWheelSpeed - leftWheelSpeed) / Constants.DrivetrainConstants.kDrivetrainWidthMeters;

        return new ChassisSpeeds(
            (leftWheelSpeed + rightWheelSpeed) / 2.0,
            0.0,
            angularVelocity
        );
    }
}