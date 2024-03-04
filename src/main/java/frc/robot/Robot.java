// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.subsystems.drive.Drivetrain;
import frc.robot.subsystems.shooter.ShooterPiviot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;



public class Robot extends TimedRobot {
  private final Timer m_timer = new Timer();
  private Command m_autonomousCommand;
  private RobotContainer m_robotContainer;
  private Drivetrain james_drive;
  private ShooterPiviot piviot;
      private TalonSRX shooterMotor = new TalonSRX(Constants.ShooterConstants.kAWheelDriverCanId);
    private VictorSPX intakeMotor = new VictorSPX(Constants.ShooterConstants.kIntakeMotor);
    double rpm = shooterMotor.getSelectedSensorVelocity();
  //private final Drivetrain drivetrain;

  /*
   * Autonomous selection options.
   */
  private static final String kNothingAuto = "Get Mobility";
  private static final String shootSpeaker = "Speaker Score & Mobility";
   private static final String MaxrpmShoot = "Test max RPM";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();



  @Override
  public void robotInit() {
    m_robotContainer = new RobotContainer();
    // Robot go Boom!!!

    m_chooser.setDefaultOption("Get Mobility", kNothingAuto);
    m_chooser.addOption("Speaker Score & Mobility", shootSpeaker);
     m_chooser.addOption("Max RPM Shoot (Test)", MaxrpmShoot);
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

  if(m_autoSelected == shootSpeaker) {
     piviot.AutoPiviot();
    m_timer.start();
    shooterMotor.set(ControlMode.PercentOutput, 0.8);
    m_timer.delay(0.3);
      shooterMotor.set(ControlMode.PercentOutput, 0.8);
      intakeMotor.set(ControlMode.PercentOutput, 0.4);
      m_timer.delay(2);
      james_drive.teleopDrive(-0.50,-0.50);
      m_timer.delay(1.5);
      james_drive.teleopDrive(0,0);
      m_timer.stop();
   }
   else if(m_autoSelected == kNothingAuto){ // This Auto will drive backwords for 2 Secs and then stop and give a mobility Bonus (+2 Points)
m_timer.start();
     james_drive.teleopDrive(-0.50,-0.50);
     m_timer.delay(2);  
     james_drive.teleopDrive(0,0);
     double elapsedTime = m_timer.get(); // Get the elapsed time
     SmartDashboard.putNumber("Auto Movement Time", elapsedTime);
     m_timer.stop();
   } else if(m_autoSelected == MaxrpmShoot){ // DO NOT RUN AT COMP THIS IS ONLY FOR TESTING
m_timer.start();
shooterMotor.set(ControlMode.PercentOutput, 1);
SmartDashboard.putNumber("RPM (Shooter)",rpm);
m_timer.delay(30);
shooterMotor.set(ControlMode.PercentOutput, 0);
m_timer.stop();
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
