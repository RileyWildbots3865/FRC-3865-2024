package frc.robot;

//import com.pathplanner.lib.auto.AutoBuilder;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandPS5Controller;
import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.cmdAuto_Base;
import frc.robot.commands.cmdSwerve_TeleOp;
import frc.robot.subsystems.subIntake;
import frc.robot.subsystems.subIntakeAngle;
import frc.robot.subsystems.subShooter;
import frc.robot.subsystems.subShooterAngle;
import frc.robot.subsystems.subSwerve;

public class RobotContainer {
  private final CommandPS5Controller driverOne = new CommandPS5Controller(OperatorConstants.DriverOne);
  private final CommandPS5Controller driverTwo = new CommandPS5Controller(OperatorConstants.DriverTwo);
  private final subSwerve swerve = new subSwerve();
  private final subIntake intake = new subIntake();
  private final subIntakeAngle intakeAngle = new subIntakeAngle();
  private final subShooter shooter = new subShooter();
  private final subShooterAngle shooterAngle = new subShooterAngle();
  SendableChooser<Command> chooser = new SendableChooser<>();
  public RobotContainer() {
    configureDriverOne();
    configureDriverTwo();
    addAutoOptions();
  }

  private void configureDriverOne() {
    swerve.setDefaultCommand(

    //This talks to the controller and sends the commands to the swerve drive
      new cmdSwerve_TeleOp(
          swerve,
          () -> MathUtil.applyDeadband(driverOne.getLeftY(), 0.1),
          () -> MathUtil.applyDeadband(driverOne.getLeftX(), 0.1),
          () -> MathUtil.applyDeadband(driverOne.getRightX(), 0.01)));

    driverOne.PS().onTrue(new InstantCommand(() -> swerve.zeroHeading()));
  }

  private void addAutoOptions(){
    chooser.setDefaultOption("Do Nothing", new InstantCommand());
    chooser.addOption("Base- DO NOT USE IF POSSIBLE", new cmdAuto_Base(swerve, 0.25));
    SmartDashboard.putData("Auto Options", chooser);
  }

  public void configureDriverTwo() {
    driverTwo.circle().onTrue(new InstantCommand(() -> intake.intakeMotor.set(Constants.MechanismConstants.kintakeSpeed))); 

    driverTwo.R1().onTrue(new InstantCommand(() -> intakeAngle.intakeAngleMotor.set(Constants.MechanismConstants.kintakeAngleSpeed)));
    driverTwo.L1().onTrue(new InstantCommand(() -> intakeAngle.intakeAngleMotor.set(-Constants.MechanismConstants.kintakeAngleSpeed)));

    driverTwo.cross().onTrue(new InstantCommand(() -> shooter.shooterUpperMotor.set(Constants.MechanismConstants.kshooterSpeed)));

    driverTwo.povUp().onTrue(new InstantCommand(() -> shooterAngle.shooterAngleMotor.set(Constants.MechanismConstants.kshooterAngleSpeed)));
    driverTwo.povDown().onTrue(new InstantCommand(() -> shooterAngle.shooterAngleMotor.set(Constants.MechanismConstants.kshooterAngleSpeed)));
  }

  public Command getAutonomousCommand() {
    return chooser.getSelected();
  }
}