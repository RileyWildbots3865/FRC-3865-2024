package frc.robot;

public final class Constants {
  public static class OperatorConstants {
    public static final int DriverOne = 0;
    public static final int DriverTwo = 1;
  }
  public static final class DriveConstants {
    public static final double kMaxSpeedMetersPerSecond = 5;
    public static final double kMaxAngularSpeed = 2.5 * Math.PI; // radians per second
  }
  public static final class MechanismConstants {
    public static final int kshooterAngleCanId = 13;
    public static final int kintakeAnglecanId = 9;

    public static final int kshooterCanId = 10;
    public static final int kshooterInvCanId = 11;

    public static final int kintakeCanId = 12;

    
    public static final double kIntakeInOffset = 0.0;
    public static final double kIntakeShootOffset = 0.6;

    
    public static final double kshooterSpeed = 0.5;
    public static final double kshooterAngleSpeed = 0.7;
    public static final double kintakeSpeedIn = 0.5;
    public static final double kintakeSpeedOut = 0.5;
    public static final double kintakeAngleSpeed = 0.7;



    public static final int klongJohnCanId = 14;

    public static final double klongJohnSpeed = 1.0;
  }
}