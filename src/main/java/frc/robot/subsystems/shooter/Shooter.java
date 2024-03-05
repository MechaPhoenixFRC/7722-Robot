package frc.robot.subsystems.shooter;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import java.util.function.BooleanSupplier;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import frc.robot.Constants;
import frc.robot.subsystems.LED.PhoenixLED;

public class Shooter extends SubsystemBase {
    private final double kintakeSpeed = -0.40;
    private final double kIntakeReverseSpeed = 0.40;
    private final double kShooterSpeed = 0.8;
      private PhoenixLED led;
          private final Timer m_Timer = new Timer();

    private TalonSRX shooterMotor = new TalonSRX(Constants.ShooterConstants.kAWheelDriverCanId);
    private VictorSPX intakeMotor = new VictorSPX(Constants.ShooterConstants.kIntakeMotor);
    
    public Shooter() {
        shooterMotor.setNeutralMode(NeutralMode.Coast);
        intakeMotor.setNeutralMode(NeutralMode.Brake);
    }
  
    public void repeatLED() {
        while (true) {
          led.ShootNote();
           m_Timer.delay(0.7);
           led.LEDOff();
           m_Timer.delay(0.7);   
    }
}
    /**Command To shoot. */
    public Command LightingMcQueenJR(BooleanSupplier shootButton) {
        return Commands.runEnd(() -> {
         repeatLED();
            shooterMotor.set(ControlMode.PercentOutput, kShooterSpeed);


            if(shootButton.getAsBoolean()) {
                intakeMotor.set(ControlMode.PercentOutput, kintakeSpeed);
            }
            else {
                intakeMotor.set(ControlMode.PercentOutput, 0);
            }
        },
        () ->{
            intakeMotor.set(ControlMode.PercentOutput, 0);
            shooterMotor.set(ControlMode.PercentOutput, 0);
        },
        this);
    }

    /**Runs the intake while the command is active. Stops the intake when the command ends. */
    public Command runIntake() {
        return Commands.startEnd(
            () -> {intakeMotor.set(ControlMode.PercentOutput, kintakeSpeed);  repeatLED();},
            () -> {intakeMotor.set(ControlMode.PercentOutput, 0);}, 
            this);
    }

    /**Runs the intake backwards while the command is active. Stops the intake when the command ends. */
    public Command Babyburbing() {
                return Commands.startEnd(
            () -> {intakeMotor.set(ControlMode.PercentOutput, kIntakeReverseSpeed);  repeatLED();},
            () -> {intakeMotor.set(ControlMode.PercentOutput, 0);}, 
            this);
    }
}
