// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants.IOConstants;
import frc.robot.subsystems.drive.Drivetrain;
import frc.robot.subsystems.shooter.Shooter;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RobotContainer {
  private final Drivetrain drivetrain;
  private final Shooter shooter;

  private final CommandXboxController m_driverController; // A controller object that you can feed a command
  public RobotContainer() {
    drivetrain = new Drivetrain(); // Create a new Drivetrain
    shooter = new Shooter();
    //pivot
    //climb
// ShuffleBord Jumble
//Shuffleboard.putData("Driving Stuff", drivetrain);
SmartDashboard.putData("Drive", drivetrain);



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
    m_driverController.leftTrigger().whileTrue(shooter.shoot(() -> m_driverController.rightTrigger().getAsBoolean()));
  }

  public Command getAutonomousCommand() {
    return null;
  }
}
