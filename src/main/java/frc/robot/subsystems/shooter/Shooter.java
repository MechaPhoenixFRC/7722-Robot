package frc.robot.subsystems.shooter;

import com.revrobotics.RelativeEncoder;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Encoder;
import frc.robot.Constants;
import frc.robot.Constants.DrivetrainConstants;
import frc.robot.Constants.IOConstants;
import frc.robot.Constants.ShooterConstants;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.Trigger;




public class Shooter extends SubsystemBase {

    private VictorSPX kA_BeltDriver_m;
    private VictorSPX kA_WheelDriver_m;

    public Command shoot() {
        return null;
//if(m_driverController.get)
        }

    public Shooter() {

    
    // Motor Config
            kA_BeltDriver_m = new VictorSPX(Constants.ShooterConstants.kABeltDriverCanId);
            kA_WheelDriver_m = new VictorSPX(Constants.ShooterConstants.kAWheelDriverCanId);

     
    }

  
// Command To Fire/Shoot
    public Command LightingMcQueenJr(Trigger leftTrigger, Trigger rightTrigger) {
        // TODO Auto-generated method stub
       // kA_WheelDriver_m.set(VictorSPXControlMode.PercentOutput, leftTrigger.negate() != null ? 0 : ShooterConstants.RevShootTimePower);
        if(leftTrigger.negate() != null){ // If Left trigger = Negitive set motor to 0 but if true send motor 30%
            kA_WheelDriver_m.set(VictorSPXControlMode.PercentOutput, 0);
        }
       else {
        kA_WheelDriver_m.set(VictorSPXControlMode.PercentOutput, ShooterConstants.RevShootTimePower);
       }

       if(rightTrigger.negate() != null){  // If Left trigger = Negitive set motor to 0 but if true send motor 30%
kA_BeltDriver_m.set(VictorSPXControlMode.PercentOutput, 0);
       } else{ 
kA_BeltDriver_m.set(VictorSPXControlMode.PercentOutput, ShooterConstants.ShootTimePower);

       }
    return null; 


       
    }

  
}
