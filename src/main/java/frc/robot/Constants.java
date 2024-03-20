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
    public static final int kintakeAnglecanId = 9;
    public static final int klongJohnCanId = 11;
    public static final int kintakeCanId = 12;
    
    public static final double kintakeSpeedIn = 0.5;
    public static final double kintakeSpeedOut = -0.29;
    public static final double kAngleSpeed = 0.9;
    public static final double klongJohnSpeed = 1.0;

    public static final double SukPosition = 13.6; //155.93
    public static final double shootPosition = 99.4; //289.4
    public static final double InPosition = 207.2; //263.4
  }
}