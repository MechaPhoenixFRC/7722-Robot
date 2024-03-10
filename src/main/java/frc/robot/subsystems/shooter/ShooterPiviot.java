package frc.robot.subsystems.shooter;

import java.util.function.BooleanSupplier;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.fasterxml.jackson.databind.ser.std.BooleanSerializer;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.motorcontrol.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ShooterPiviot extends SubsystemBase {
    private final Timer m_Timer = new Timer();
    private boolean ispiviot = false;
    private VictorSPX ka_Shoulder_Motor;
    private VictorSPX k_ElbowMotor;
    private static boolean somethingIsSet = false;
private TalonSRX piviotMotor;

    public void MasterPiviot(){
    // Motor Define 
 k_ElbowMotor = new VictorSPX(Constants.ShooterConstants.kaSholderCanId);
ka_Shoulder_Motor = new VictorSPX(Constants.ShooterConstants.kaElbowCanId);
piviotMotor = new TalonSRX(Constants.ShooterConstants.PiviotMotor);
 // Flag
 SmartDashboard.putBoolean("Piviot is up", somethingIsSet);
    
// Invert 1 Side of the Cim
    ka_Shoulder_Motor.setInverted(true);
    piviotMotor.setNeutralMode(NeutralMode.Brake);
    }

public Command AutoPiviot(){
    somethingIsSet = true;
    m_Timer.start();
    piviotMotor.set(ControlMode.PercentOutput, 1);
    m_Timer.delay(0.33);
    piviotMotor.set(ControlMode.PercentOutput, 0);
    m_Timer.delay(7);
    piviotMotor.set(ControlMode.PercentOutput, -1);
    m_Timer.delay(0.22);
    piviotMotor.set(ControlMode.PercentOutput, 0);
    m_Timer.stop();
    somethingIsSet = false;
    return null;
}

public Command PiviotUp(){
  somethingIsSet = true;
  m_Timer.start();
  piviotMotor.set(ControlMode.PercentOutput, 1);
  m_Timer.delay(0.33);
  piviotMotor.set(ControlMode.PercentOutput, 0);
  m_Timer.stop();
  return null;  
}

public Command PiviotDown(){
  somethingIsSet = true;
  m_Timer.start();
  piviotMotor.set(ControlMode.PercentOutput, -1);
  m_Timer.delay(0.32);
  piviotMotor.set(ControlMode.PercentOutput, 0);
  somethingIsSet = false;
  m_Timer.stop();
  return null;
}

}