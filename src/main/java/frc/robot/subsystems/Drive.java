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

public class Drive extends SubsystemBase {

  //define the motors and their device ID + motor type for the left motors
  private final CANSparkMax m_left_leader = new CANSparkMax(MotorConstants.kLMotorFront, MotorType.kBrushless);
  private final CANSparkMax m_left_follower = new CANSparkMax(MotorConstants.kLMotorRear, MotorType.kBrushless);

  //define the motors and their device ID + motor type for the right motors
  private final CANSparkMax m_right_leader = new CANSparkMax(MotorConstants.kRMotorFront, MotorType.kBrushless);
  private final CANSparkMax m_right_follower = new CANSparkMax(MotorConstants.kRMotorRear, MotorType.kBrushless);

  //generate the differential drive 
  private final DifferentialDrive m_robotDrive = new DifferentialDrive(m_left_leader, m_right_leader);

  /** Creates a new Drive. */
  public Drive() {

    // We need to invert one side of the drivetrain so that positive voltages
    // result in both sides moving forward. Depending on how your robot's
    // gearbox is constructed, you might have to invert the left side instead.
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
    // This method will be called once per scheduler run
  }

  public void teleopDrive(double x, double y) {
    m_robotDrive.arcadeDrive(x, y);
  }

}
