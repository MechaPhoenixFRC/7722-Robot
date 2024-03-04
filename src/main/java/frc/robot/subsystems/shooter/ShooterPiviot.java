package frc.robot.subsystems.shooter;

import java.util.function.BooleanSupplier;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.fasterxml.jackson.databind.ser.std.BooleanSerializer;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ShooterPiviot extends SubsystemBase {
    private final Timer m_Timer = new Timer();
    private VictorSPX ka_Shoulder_Motor;
    private VictorSPX ka_Elbow_Motor;

    public void MasterPiviot(){
    // Motor Define 
 ka_Elbow_Motor = new VictorSPX(Constants.ShooterConstants.kaSholderCanId);
ka_Shoulder_Motor = new VictorSPX(Constants.ShooterConstants.kaElbowCanId);

// Invert 1 Side of the Cim
    ka_Shoulder_Motor.setInverted(true);
    ka_Elbow_Motor.setNeutralMode(NeutralMode.Brake);
    }

public Command AutoPiviot(){
   m_Timer.start();
ka_Elbow_Motor.set(ControlMode.PercentOutput, 1);
   m_Timer.delay(0.33);
   ka_Elbow_Motor.set(ControlMode.PercentOutput, 0);
     m_Timer.delay(7);
     ka_Elbow_Motor.set(ControlMode.PercentOutput, -1);
     m_Timer.delay(0.27);
ka_Elbow_Motor.set(ControlMode.PercentOutput, 0);
m_Timer.stop();
return null;
}
public Command Piviot(BooleanSupplier piviotButton){
    if(piviotButton.getAsBoolean()) {
        m_Timer.start();
        ka_Elbow_Motor.set(ControlMode.PercentOutput, 1);
        m_Timer.delay(0.25);
            ka_Elbow_Motor.set(ControlMode.PercentOutput, 0);
            m_Timer.stop();
        }
        else {
            ka_Elbow_Motor.set(ControlMode.PercentOutput, 0);
        }

return null;
    
    }
}
