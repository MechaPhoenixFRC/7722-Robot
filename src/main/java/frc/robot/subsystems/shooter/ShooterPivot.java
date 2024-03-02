package frc.robot.subsystems.shooter;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.Encoder;
import frc.robot.Constants;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShooterPivot extends SubsystemBase{
// Configs To move Piviot
private static final int Encoder_Channel1 = 0;
private static final int Encoder_Channel2 = 1;
private static final double Encoder_DistencePerPulse = 360.0 / 1024.0;

// Motor Define
    private VictorSPX ka_Shoulder_Motor;
    private VictorSPX ka_Elbow_Motor;

    public Encoder encoder;
    private static final double TARGET_ANGLE = 20.0; // Target angle in degrees
    private static final double MAX_SPEED = 0.69; // Hehe 69
    private static final double P_CONSTANT = 0.4;
    private static final double RESET_ANGLE = 0;

  public void Piviot() {
    // Motor Define 
 ka_Elbow_Motor = new VictorSPX(Constants.ShooterConstants.kaSholderCanId);
ka_Shoulder_Motor = new VictorSPX(Constants.ShooterConstants.kaElbowCanId);

// Invert 1 Side of the Cim
    ka_Shoulder_Motor.setInverted(true);
  
    // Encoder 😒
    encoder = new Encoder(Encoder_Channel1, Encoder_Channel2);
    encoder.setDistancePerPulse(Encoder_DistencePerPulse);


    }
    // public Command InfinityandBeyond(Trigger x){ // Toy Story
     
    //   if (x.negate() != null) {
    //  double currentAngle = encoder.getDistance();
    //     double error = RESET_ANGLE - currentAngle;
    //     double correction = P_CONSTANT * error;
    //       ka_Elbow_Motor.set(ControlMode.PercentOutput, 0.5 + correction);// Move to 0 degrees with 50% speed
    //     if (Math.abs(error) < 1.0) { // Reset encoder if close to 0 degrees
    //         encoder.reset();
    //   }
     
    //   } else {
    //     double currentAngle = encoder.getDistance();
    //     double error = TARGET_ANGLE - currentAngle;
    //     double correction = P_CONSTANT * error;
    //    ka_Elbow_Motor.set(ControlMode.PercentOutput, 0.5 + correction);// Move to 20 degrees with 50% speed
    // }
    //    return null;
    // }
}
