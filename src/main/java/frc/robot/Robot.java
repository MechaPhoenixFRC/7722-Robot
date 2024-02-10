// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PS4Controller;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

/**
 * This is a demo program showing the use of the DifferentialDrive class. Runs the motors with
 * arcade steering.
 */
public class Robot extends TimedRobot {
  
  //define the motors and their device ID + motor type for the left motors
  private final CANSparkMax m_leftMotor_front = new CANSparkMax(4, MotorType.kBrushless);
  private final CANSparkMax m_leftMotor_rear = new CANSparkMax(5, MotorType.kBrushless);

  //generate the motor group for left motors
  private final MotorControllerGroup mgroup_left = new MotorControllerGroup(m_leftMotor_front, m_leftMotor_rear);

  //define the motors and their device ID + motor type for the right motors
  private final CANSparkMax m_rightMotor_front = new CANSparkMax(6, MotorType.kBrushless);
  private final CANSparkMax m_rightMotor_rear = new CANSparkMax(7, MotorType.kBrushless);

  //generate the motor group for the right motors
  private final MotorControllerGroup mgroup_right = new MotorControllerGroup(m_rightMotor_front, m_rightMotor_rear);

  private final DifferentialDrive m_robotDrive = new DifferentialDrive(mgroup_right::set, mgroup_left::set);
  private final PS4Controller drive_controller = new PS4Controller(0);

  public Robot() {
    SendableRegistry.addChild(m_robotDrive, mgroup_left);
    SendableRegistry.addChild(m_robotDrive, mgroup_right);
  }

  @Override
  public void robotInit() {
    // We need to invert one side of the drivetrain so that positive voltages
    // result in both sides moving forward. Depending on how your robot's
    // gearbox is constructed, you might have to invert the left side instead.
    mgroup_right.setInverted(true);
  }

  @Override
  public void teleopPeriodic() {
    // Drive with arcade drive.
    // That means that the Y axis drives forward
    // and backward, and the X turns left and right.
    m_robotDrive.arcadeDrive(-m_stick.getY(), -m_stick.getX());
  }
}
