package frc.robot.subsystems.intake;

import edu.wpi.first.wpilibj2.command.SubsystemBase;


import com.revrobotics.RelativeEncoder;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;

import edu.wpi.first.wpilibj2.command.Command;

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import frc.robot.Constants;
import frc.robot.Constants.DrivetrainConstants;
import frc.robot.Constants.IOConstants;
import frc.robot.Constants.ShooterConstants;

import edu.wpi.first.wpilibj2.command.button.Trigger;


public class Intake extends SubsystemBase {
    private VictorSPX kA_BeltDriver_m;
    private VictorSPX kA_WheelDriver_m;
   


    public Intake() {
            kA_BeltDriver_m = new VictorSPX(Constants.ShooterConstants.kABeltDriverCanId);
            kA_WheelDriver_m = new VictorSPX(Constants.ShooterConstants.kAWheelDriverCanId);


    }

    public Command intaker(Trigger xTrigger){

kA_BeltDriver_m.set(VictorSPXControlMode.PercentOutput, -0.40);
kA_WheelDriver_m.set(VictorSPXControlMode.PercentOutput, -0.40);
return null;

}


}