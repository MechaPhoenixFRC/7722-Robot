// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants.MotorConstants;
import frc.robot.Constants.OperatorConstants;
import edu.wpi.first.math.filter.SlewRateLimiter;

// SlewRateLimiter filter = new SlewRateLimiter(0.5);

public class Drive extends SubsystemBase {

  private final CANSparkMax m_left_leader = new CANSparkMax(MotorConstants.kLMotorFront, MotorType.kBrushless);
  private final CANSparkMax m_left_follower = new CANSparkMax(MotorConstants.kLMotorRear, MotorType.kBrushless);

  private final CANSparkMax m_right_leader = new CANSparkMax(MotorConstants.kRMotorFront, MotorType.kBrushless);
  private final CANSparkMax m_right_follower = new CANSparkMax(MotorConstants.kRMotorRear, MotorType.kBrushless);

  private final DifferentialDrive m_robotDrive = new DifferentialDrive(m_left_leader, m_right_leader);

  private final SlewRateLimiter m_slewRateLimiterX = new SlewRateLimiter(0.5);
  private final SlewRateLimiter m_slewRateLimiterY = new SlewRateLimiter(0.5);


  public Drive() {
    m_left_leader.restoreFactoryDefaults();
    m_left_leader.setIdleMode(IdleMode.kBrake);
    m_left_leader.setInverted(true);
    m_left_leader.burnFlash();
    
    m_left_follower.restoreFactoryDefaults();
    m_left_follower.setIdleMode(IdleMode.kBrake);
    m_left_follower.follow(m_left_leader);
    m_left_follower.burnFlash();
        
    m_right_leader.restoreFactoryDefaults();
    m_right_leader.setIdleMode(IdleMode.kBrake);
    m_right_leader.burnFlash();
        
    m_right_follower.restoreFactoryDefaults();
    m_right_follower.setIdleMode(IdleMode.kBrake);
    m_right_follower.follow(m_right_leader);
    m_right_follower.burnFlash();

  }

  @Override
  public void periodic() {

  }

  public void teleopDrive(double x, double y) {

    double limitedX = m_slewRateLimiterX.calculate(x);
    double limitedY = m_slewRateLimiterY.calculate(y);

   
    m_robotDrive.arcadeDrive(limitedX, limitedY);
    // m_robotDrive.arcadeDrive(x, y);
  }

}
