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
import frc.robot.subsystems.drive.Drivetrain;
import frc.robot.subsystems.shooter.Shooter;

public class RobotContainer {
  private final Drivetrain m_drivetrain;
  private final Shooter a_shooter;

  private final SendableChooser<Command> m_autoChooser; // Essntially a dropdown that can be sent to your dashboard

  private final CommandXboxController m_driverController; // A controller object that you can feed a command
  public RobotContainer() {
    m_drivetrain = new Drivetrain(); // Create a new Drivetrain
    a_shooter = new Shooter();

    m_autoChooser = AutoBuilder.buildAutoChooser(); // Gets the AutoBuilder that was created in Drivetrain.java
    SmartDashboard.putData("Auto Chooser", m_autoChooser); // Puts the SendableChooser for autos onto SmartDashboard (can be changed)

    m_driverController = new CommandXboxController(IOConstants.kDriverControllerPort); // Creates new controller

    configureBindings();
  }

  private void configureBindings() {
    m_drivetrain.setDefaultCommand( // 
      m_drivetrain.teleopDrive(
        () -> m_driverController.getLeftY(), // teleopDrive needs a BooleanSupplier so we do that with a lamba; () -> method()
        () -> m_driverController.getRightX(),
        () -> m_driverController.rightBumper().getAsBoolean()
      )
    );
  }

  public Command getAutonomousCommand() {
    return m_autoChooser.getSelected(); // Sets your autonomous command to what is selected in your SendableChooser that the PathplannerLib handles
  }
}
