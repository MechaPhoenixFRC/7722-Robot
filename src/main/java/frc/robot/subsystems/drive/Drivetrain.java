package frc.robot.subsystems.drive;

// import java.lang.Thread;
import java.util.function.DoubleSupplier;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkBase.IdleMode;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants;
import frc.robot.Constants.DrivetrainConstants;

public class Drivetrain extends SubsystemBase {
    private final CANSparkMax m_leftMotor;
    private final CANSparkMax m_leftMotorFollower;

    private final CANSparkMax m_rightMotor;
    private final CANSparkMax m_rightMotorFollower;

    private final DifferentialDrive m_robotDrive;
    public Object teleopDrive;

    public Drivetrain() {
        m_leftMotor = new CANSparkMax(DrivetrainConstants.kLMotorFrontCanId, MotorType.kBrushless);
        m_leftMotor.setIdleMode(IdleMode.kBrake);
        m_leftMotor.burnFlash();

        m_leftMotorFollower = new CANSparkMax(DrivetrainConstants.kLMotorRearCanId, MotorType.kBrushless);
        m_leftMotorFollower.follow(m_leftMotor);
        m_leftMotorFollower.setIdleMode(IdleMode.kBrake);
        m_leftMotorFollower.burnFlash();

        m_rightMotor = new CANSparkMax(DrivetrainConstants.kRMotorFrontCanId, MotorType.kBrushless);
        m_rightMotor.setInverted(false);
        m_rightMotor.setIdleMode(IdleMode.kBrake);
        m_rightMotor.burnFlash();
        
        m_rightMotorFollower = new CANSparkMax(DrivetrainConstants.kRMotorRearCanId, MotorType.kBrushless);
        m_rightMotorFollower.follow(m_rightMotor);
        m_rightMotorFollower.setIdleMode(IdleMode.kBrake);
        m_rightMotorFollower.burnFlash();

        m_robotDrive = new DifferentialDrive(m_leftMotor, m_rightMotor);       
    }

    @Override
    public void periodic() {

    }

    public Command teleopDrive(DoubleSupplier speed, DoubleSupplier rotation) {
        return run(() -> { // This is a Command factory that uses a lambda that you feed into your CommandXboxController
            double xSpeed = MathUtil.applyDeadband(speed.getAsDouble(), Constants.IOConstants.kDriverDeadband); // Deadband battles stick drift and
            double zRotation = MathUtil.applyDeadband(rotation.getAsDouble(), Constants.IOConstants.kDriverDeadband); // adds a area on your joystick that's "dead" or ignored
            m_robotDrive.arcadeDrive(-xSpeed, zRotation);
        });
    }

    public void teleopDrive(double d, double e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'teleopDrive'");
    }
}