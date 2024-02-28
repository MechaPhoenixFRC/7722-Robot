package frc.robot.subsystems.shooter;

import com.revrobotics.RelativeEncoder;

import edu.wpi.first.wpilibj2.command.Command;

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import frc.robot.Constants;
import frc.robot.Constants.DrivetrainConstants;
import frc.robot.Constants.IOConstants;
import frc.robot.Constants.ShooterConstants;
import edu.wpi.first.wpilibj2.command.SubsystemBase;




public class Shooter extends SubsystemBase {

    private TalonSRX kA_BeltDriver_m;
    private TalonSRX kA_WheelDriver_m;



    public Shooter() {

        
       
            kA_BeltDriver_m = new TalonSRX(Constants.ShooterConstants.kABeltDriverCanId);
            kA_WheelDriver_m = new TalonSRX(Constants.ShooterConstants.kAWheelDriverCanId);

        
        
    }
}
