// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.subsystems.drive.Drivetrain;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import frc.robot.subsystems.drive.Drivetrain;

public class Robot extends TimedRobot {
  private final Timer m_timer = new Timer();
  private Command m_autonomousCommand;
  private RobotContainer m_robotContainer;
  //private final Drivetrain drivetrain;

  /*
   * Autonomous selection options.
   */
  private static final String kNothingAuto = "do nothing";
  private static final String kDrive = "drive";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();



  @Override
  public void robotInit() {
    m_robotContainer = new RobotContainer();
    // Robot go Boom!!!

    m_chooser.setDefaultOption("do nothing", kNothingAuto);
    m_chooser.addOption("drive", kDrive);
    SmartDashboard.putData("Auto choices", m_chooser);
  }

  
  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
  }

  @Override
  public void disabledInit() {
   
  }

  @Override
  public void disabledPeriodic() {}

  @Override
  public void disabledExit() {}

  @Override
  public void autonomousInit() {
    m_timer.restart();
    m_autoSelected = m_chooser.getSelected();
  
  //  m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    //if (m_autonomousCommand != null) {
    //  m_autonomousCommand.schedule();
   // }

  if(m_autoSelected == kDrive) {
      // AAAAA 
   }
   else if(m_autoSelected == kNothingAuto)
   {
 // I was your dad and got the milk                             
   }
  }

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void autonomousExit() {}

  @Override
  public void teleopInit() {
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  //  System.println("AA!! T Minus 1 min till explode of robot");
  }

  @Override
  public void teleopPeriodic() {}

  @Override
  public void teleopExit() {}

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void testPeriodic() {}

  @Override
  public void testExit() {}

  
}
