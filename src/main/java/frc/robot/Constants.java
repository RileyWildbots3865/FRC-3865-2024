package frc.robot;

public final class Constants {
  public static class OperatorConstants {
    public static final int DriverOne = 0;
    public static final int DriverTwo = 1;
  }
  public static final class DriveConstants {
    public static final double kMaxSpeedMetersPerSecond = 4.8;
    public static final double kMaxAngularSpeed = 2 * Math.PI; // radians per second
  }
  public static final class MechanismConstants {
    public static final int kshooterAngleCanId = 13;
    public static final int kintakeAnglecanId = 9;

    public static final int kshooterCanId = 10;
    public static final int kshooterInvCanId = 11;

    public static final int kintakeCanId = 12;

    public static final double kshooterSpeed = 0.5;
    public static final double kshooterAngleSpeed = 0.5;
    public static final double kintakeSpeed = 0.5;
    public static final double kintakeAngleSpeed = 0.5;

    public static final int klongjohnCanId = 77;
  }
}