package frc.robot;

public final class Constants {
  public static class IOConstants {
    public static final int kDriverControllerPort = 0;

    public static final double kDriverDeadband = 0.1;

    public static final double kSpeedLimiter = 4;

    public static final int StripLEDLength = 30; // Plz change this.
  }

  public static class DrivetrainConstants {
    public static final int kRMotorFrontCanId = 4;
    public static final int kRMotorRearCanId = 5;
    public static final int kLMotorFrontCanId = 6;
    public static final int kLMotorRearCanId = 7;

    public static final double kDrivetrainWidthMeters = 1.0; // Width in meters between wheels
  }

  public static class ShooterConstants {
    public static final double kASpeedLimiter = 0.2;

    public static final int kABeltDriverCanId = 8;
    public static final int kAWheelDriverCanId = 9;
    // Add the Real CAN Id
    public static final int kaElbowCanId = 99;
    public static final int kaSholderCanId = 98;

    // public static final int FireButton = 0;
  }



}