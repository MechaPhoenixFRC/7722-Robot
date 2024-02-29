package frc.robot.subsystems.shooter;

import com.revrobotics.RelativeEncoder;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import frc.robot.Constants;
import frc.robot.Constants.DrivetrainConstants;
import frc.robot.Constants.IOConstants;
import frc.robot.Constants.ShooterConstants;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.Trigger;




public class Shooter extends SubsystemBase {

    private TalonSRX kA_BeltDriver_m;
    private TalonSRX kA_WheelDriver_m;

    public Command shoot() {
        return null;
//if(m_driverController.get)
        }

    public Shooter() {

    
    
            kA_BeltDriver_m = new TalonSRX(Constants.ShooterConstants.kABeltDriverCanId);
            kA_WheelDriver_m = new TalonSRX(Constants.ShooterConstants.kAWheelDriverCanId);

     
    }

    public Command shoot(Trigger lefTrigger) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'shoot'");
    }

    public Command shoottime(Trigger leftTrigger, Trigger rightTrigger) {
        // TODO Auto-generated method stub

        if(leftTrigger.negate() != null){
            kA_WheelDriver_m.set(TalonSRXControlMode.PercentOutput, 0);
        }
       else {
        kA_WheelDriver_m.set(TalonSRXControlMode.PercentOutput, 0.30);
       }

       if(rightTrigger.negate() != null){
kA_BeltDriver_m.set(TalonSRXControlMode.PercentOutput, 0);
       } else{ 
kA_BeltDriver_m.set(TalonSRXControlMode.PercentOutput, 0.27);

       }
    return null; 


       
    }

    public void setDefaultCommand(Object shoottime) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setDefaultCommand'");
    }
}
