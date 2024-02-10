// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.MotorConstants;
import frc.robot.Constants.OperatorConstants;
import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.PS4Controller;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

/**
 * This is a demo program showing the use of the DifferentialDrive class. Runs the motors with
 * arcade steering.
 */
public class Robot extends TimedRobot {
  
  //define the motors and their device ID + motor type for the left motors
  private final CANSparkMax m_leftMotor_front = new CANSparkMax(MotorConstants.kLMotorFront, MotorType.kBrushless);
  private final CANSparkMax m_leftMotor_rear = new CANSparkMax(MotorConstants.kLMotorRear, MotorType.kBrushless);

  //generate the motor group for left motors
  private final MotorControllerGroup mgroup_left = new MotorControllerGroup(m_leftMotor_front, m_leftMotor_rear);

  //define the motors and their device ID + motor type for the right motors
  private final CANSparkMax m_rightMotor_front = new CANSparkMax(MotorConstants.kRMotorFront, MotorType.kBrushless);
  private final CANSparkMax m_rightMotor_rear = new CANSparkMax(MotorConstants.kRMotorRear, MotorType.kBrushless);

  //generate the motor group for the right motors
  private final MotorControllerGroup mgroup_right = new MotorControllerGroup(m_rightMotor_front, m_rightMotor_rear);

  //generate the differential drive 
  private final DifferentialDrive m_robotDrive = new DifferentialDrive(mgroup_right::set, mgroup_left::set);

  //define the controller (PS4) and a new drive stick
  private final CommandXboxController drive_controller = new CommandXboxController(OperatorConstants.kDriverControllerPort);

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
    // m_robotDrive.arcadeDrive(-drive_controller.getLeftY(), -drive_controller.getLeftX());
    m_robotDrive.tankDrive(-drive_controller.getLeftY(), -drive_controller.getRightY());

  }
}
