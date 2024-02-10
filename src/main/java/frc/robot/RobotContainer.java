// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants.OperatorConstants;
import frc.robot.subsystems.Drive;

public class RobotContainer {

   // The robot's subsystems and commands are defined here...
  private final Drive m_drive = new Drive();

  //define the controller (PS4) and a new drive stick
  private final CommandXboxController drive_controller = new CommandXboxController(OperatorConstants.kDriverControllerPort);

  public RobotContainer() {
    configureBindings();
  }

  private void configureBindings() {
    m_drive.setDefaultCommand(Commands.run(() -> m_drive.teleopDrive(-drive_controller.getLeftY(), drive_controller.getLeftX()), m_drive));
  }

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}
