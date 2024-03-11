// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants.IOConstants;
import frc.robot.subsystems.drive.Drivetrain;
import frc.robot.subsystems.shooter.Shooter;
import frc.robot.subsystems.shooter.ShooterPiviot;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RobotContainer {
  private final Drivetrain drivetrain;
  private final Shooter shooter;
  private final ShooterPiviot piviot;

  private final CommandXboxController m_driverController; // A controller object that you can feed a command
  public RobotContainer() {
    drivetrain = new Drivetrain(); // Create a new Drivetrain
    shooter = new Shooter();
    piviot = new ShooterPiviot();
    //pivot
    //climb
// ShuffleBord Jumble
SmartDashboard.putData("Drive",drivetrain);



    m_driverController = new CommandXboxController(IOConstants.kDriverControllerPort); // Creates new controller

    configureBindings();
  }

  private void configureBindings() {
   drivetrain.setDefaultCommand( // 
      drivetrain.teleopDrive(
        () -> m_driverController.getLeftY(), // teleopDrive needs a BooleanSupplier so we do that with a lamba; () -> method()
        () -> m_driverController.getRightX()
      ));

    m_driverController.x().whileTrue(shooter.runIntake());
    m_driverController.leftTrigger().whileTrue(shooter.LightingMcQueenJR(() -> m_driverController.rightTrigger().getAsBoolean()));
   m_driverController.povLeft().whileTrue(piviot.UpwardsDog());
   m_driverController.povDownLeft().whileTrue(piviot.DownwordsDog());
  }

  public Command getAutonomousCommand() {
    return null;
  }
}
