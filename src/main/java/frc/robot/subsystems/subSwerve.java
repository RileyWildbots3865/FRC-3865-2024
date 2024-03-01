
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.SerialPort.Port;
import com.kauailabs.navx.frc.AHRS;
//import com.pathplanner.lib.auto.AutoBuilder;
//import com.pathplanner.lib.util.HolonomicPathFollowerConfig;
//import com.pathplanner.lib.util.PIDConstants;
//import com.pathplanner.lib.util.ReplanningConfig;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.classes.moduleConstants;
import frc.robot.classes.swerveModule;

public class subSwerve extends SubsystemBase {

  public static final double kFrontLeftOffset = 0.73974609375;
  public static final double kFrontRightOffset = 0.261962890625;
  public static final double kRearLeftOffset = 0.809326171875;
  public static final double kRearRightOffset = 0.570068359375;

  public static final int kFrontLeftDrivingCanId = 5;
  public static final int kFrontRightDrivingCanId = 3;
  public static final int kRearLeftDrivingCanId = 7;
  public static final int kRearRightDrivingCanId = 2;

  public static final int kFrontLeftTurningCanId = 6;
  public static final int kFrontRightTurningCanId = 4;
  public static final int kRearLeftTurningCanId = 8;
  public static final int kRearRightTurningCanId = 1;

  public static final int kFrontLeftCANcoder = 1;
  public static final int kFrontRightCANcoder = 4;
  public static final int kRearLeftCANcoder = 2;
  public static final int kRearRightCANcoder = 3;

  private final swerveModule frontLeftModule = new swerveModule(kFrontLeftDrivingCanId,kFrontLeftTurningCanId,kFrontLeftCANcoder,kFrontLeftOffset, "Front Left");
  private final swerveModule frontRightModule = new swerveModule(kFrontRightDrivingCanId,kFrontRightTurningCanId,kFrontRightCANcoder,kFrontRightOffset, "Front Right");
  private final swerveModule rearLeftModule = new swerveModule(kRearLeftDrivingCanId,kRearLeftTurningCanId,kRearLeftCANcoder,kRearLeftOffset, "Rear Left");
  private final swerveModule rearRightModule = new swerveModule(kRearRightDrivingCanId,kRearRightTurningCanId,kRearRightCANcoder,kRearRightOffset, "Rear Right");
  
  private final AHRS gyro;
  public SwerveDriveOdometry odometry;

  public subSwerve() {
    gyro = new AHRS(Port.kUSB1);
    // warning: thread may reset gyro while trying to read during odomerty intit
    new Thread(() -> {
      try {
        Thread.sleep(1000);
        gyro.reset();
        gyro.zeroYaw();
      } catch (Exception e) { }
    }).start();

    odometry = new SwerveDriveOdometry(
      moduleConstants.kDriveKinematics,
      gyro.getRotation2d(),
      new SwerveModulePosition[] {
        frontLeftModule.getPosition(),
        frontRightModule.getPosition(),
        rearLeftModule.getPosition(),
        rearRightModule.getPosition()
      });
  }

  public Pose2d getPose() { return odometry.getPoseMeters(); }
  public void resetPose(Pose2d pose) {
    odometry.resetPosition(
      getRotation2d(),
      new SwerveModulePosition[] {
        frontLeftModule.getPosition(),
        frontRightModule.getPosition(),
        rearLeftModule.getPosition(),
        rearRightModule.getPosition()
      },
      pose);
  }
  public void updateOdometry(){
    odometry.update(
    getRotation2d(),
      new SwerveModulePosition[] {
        frontLeftModule.getPosition(),
        frontRightModule.getPosition(),
        rearLeftModule.getPosition(),
        rearRightModule.getPosition()
      });
  }

  
  public void drive(double xSpeed, double ySpeed, double rot) {
    var swerveModuleStates = moduleConstants.kDriveKinematics.toSwerveModuleStates(ChassisSpeeds.fromFieldRelativeSpeeds(xSpeed, ySpeed, rot, getRotation2d()));
    setModuleStates(swerveModuleStates);
  }

  public void setModuleStates(SwerveModuleState[] desiredStates) {
    SwerveDriveKinematics.desaturateWheelSpeeds(desiredStates, Constants.DriveConstants.kMaxSpeedMetersPerSecond);
    frontLeftModule.setDesiredState(desiredStates[0]);
    frontRightModule.setDesiredState(desiredStates[1]);
    rearLeftModule.setDesiredState(desiredStates[2]);
    rearRightModule.setDesiredState(desiredStates[3]);
  }

  public SwerveModulePosition[] getModulePosition(){
    SwerveModulePosition[] positions = new SwerveModulePosition[4];
    positions[0] = frontLeftModule.getPosition();
    positions[1] = frontRightModule.getPosition();
    positions[2] = rearLeftModule.getPosition();
    positions[3] = rearRightModule.getPosition();
    return positions;
  }

  public void stopModules(){
    frontLeftModule.stopModule();
    frontRightModule.stopModule();
    rearLeftModule.stopModule();
    rearRightModule.stopModule();
  }

  public void zeroHeading() { gyro.reset(); }
  public Rotation2d getRotation2d() { return gyro.getRotation2d();}

  @Override
  public void periodic() {
    updateOdometry();
    SmartDashboard.putNumber("Heading", gyro.getRotation2d().getDegrees() );
    SmartDashboard.putString("Robot Location", getPose().getTranslation().toString());
    SmartDashboard.putNumber("Front Left Angle Raw", frontLeftModule.getRawAngle());    
    SmartDashboard.putNumber("Front Right Angle Raw", frontRightModule.getRawAngle());    
    SmartDashboard.putNumber("Back Left Angle Raw", rearLeftModule.getRawAngle());    
    SmartDashboard.putNumber("Back Right Angle Raw", rearRightModule.getRawAngle());    
  }
}