// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.pathplanner.lib.auto.AutoBuilder;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants.IOConstants;
import frc.robot.subsystems.Drivetrain;

public class RobotContainer {
  private final Drivetrain m_drivetrain;

  private final SendableChooser<Command> m_autoChooser;

  private final CommandXboxController m_driverController;
  public RobotContainer() {
    m_drivetrain = new Drivetrain();

    m_autoChooser = AutoBuilder.buildAutoChooser();
    SmartDashboard.putData("Auto Chooser", m_autoChooser);

    m_driverController = new CommandXboxController(IOConstants.kDriverControllerPort);

    configureBindings();
  }

  private void configureBindings() {
    m_drivetrain.setDefaultCommand(
      m_drivetrain.teleopDrive(
        () -> m_driverController.getLeftY(),
        () -> m_driverController.getRightX(),
        () -> m_driverController.rightBumper().getAsBoolean()
      )
    );
  }

  public Command getAutonomousCommand() {
    return m_autoChooser.getSelected();
  }
}
