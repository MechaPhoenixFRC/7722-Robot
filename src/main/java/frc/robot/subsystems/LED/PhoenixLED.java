package frc.robot.subsystems.LED;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import java.util.Optional;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.Timer;

public class PhoenixLED extends SubsystemBase {
    private final Timer m_Timer = new Timer();
    private  AddressableLED ledStrip;
    private  AddressableLEDBuffer ledBuffer;

    public PhoenixLED(){
           ledStrip = new AddressableLED(0);
        ledStrip.setLength(Constants.IOConstants.StripLEDLength);
        ledBuffer = new AddressableLEDBuffer(30);
        ledStrip.setData(ledBuffer);
        ledStrip.start();

      // ---------------------------------------------- \\
        // Set's the Strip to Yellow to let us know it works.
      RobotOn();
    }

    public void RobotOn() {
        m_Timer.start();
          setGreenLED();
           m_Timer.delay(0.7);
           LEDOff();
           m_Timer.delay(0.7);  
           m_Timer.stop();
    }
    
    public void DisableLED(){
         for (int i = 0; i < ledBuffer.getLength(); i++) {
            int redValue = 155;
            int greenValue = 52;
            int blueValue = 52;
            ledBuffer.setRGB(i, redValue, greenValue, blueValue);
        }
        ledStrip.setData(ledBuffer); 
    }
     public void ShootNote(){
         for (int i = 0; i < ledBuffer.getLength(); i++) {
            int redValue = 214;
            int greenValue = 70;
            int blueValue = 26;
            ledBuffer.setRGB(i, redValue, greenValue, blueValue);
        }
        ledStrip.setData(ledBuffer); 
    }


     public void LEDOff(){
         for (int i = 0; i < ledBuffer.getLength(); i++) {
            int redValue = 0;
            int greenValue = 0;
            int blueValue = 0;
            ledBuffer.setRGB(i, redValue, greenValue, blueValue);
        }
        ledStrip.setData(ledBuffer); 
    }


     public void TeleLED(){
         for (int i = 0; i < ledBuffer.getLength(); i++) {
            int redValue = 9;
            int greenValue = 118;
            int blueValue = 106;
            ledBuffer.setRGB(i, redValue, greenValue, blueValue);
        }
        ledStrip.setData(ledBuffer); 
    }


      public void AutoLED(){
         for (int i = 0; i < ledBuffer.getLength(); i++) {
            int redValue = 143;
            int greenValue = 155;
            int blueValue = 255;
            ledBuffer.setRGB(i, redValue, greenValue, blueValue);
        }
        ledStrip.setData(ledBuffer); 
    }

    public void setLEDsYellow() {
        for (int i = 0; i < ledBuffer.getLength(); i++) {
            int redValue = 255;
            int greenValue = 251;
            int blueValue = 0;
            ledBuffer.setRGB(i, redValue, greenValue, blueValue);
        }
        ledStrip.setData(ledBuffer);
    }

     public void setGreenLED() {
        for (int i = 0; i < ledBuffer.getLength(); i++) {
            int redValue = 0;
            int greenValue = 255;
            int blueValue = 0;
            ledBuffer.setRGB(i, redValue, greenValue, blueValue);
        }
        ledStrip.setData(ledBuffer);
    }   

}
