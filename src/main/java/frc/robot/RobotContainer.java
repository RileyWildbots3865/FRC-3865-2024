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
import frc.robot.commands.cmdAuto_CrossLine;
import frc.robot.commands.cmdAuto_IntakePosition;
import frc.robot.commands.cmdIntakeAngle_TeleOp;
import frc.robot.commands.cmdIntake_TeleOp;
import frc.robot.commands.cmdLongJohn_TeleOp;
import frc.robot.commands.cmdSwerve_TeleOp;
import frc.robot.subsystems.subIntake;
import frc.robot.subsystems.subIntakeAngle;
import frc.robot.subsystems.subLongJohn;
import frc.robot.subsystems.subSwerve;

public class RobotContainer {
  private final CommandPS5Controller driverOne = new CommandPS5Controller(OperatorConstants.DriverOne);
  private final CommandPS5Controller driverTwo = new CommandPS5Controller(OperatorConstants.DriverTwo);
  private final subSwerve swerve = new subSwerve();
  private final subIntake intake = new subIntake();
  private final subIntakeAngle intakeAngle = new subIntakeAngle();
  private final subLongJohn longJohn = new subLongJohn();

  public static boolean fieldCentric = false;
  
  SendableChooser<Command> chooser = new SendableChooser<>();
  public RobotContainer() {
    configureDriverOne();
    configureDriverTwo();
    addAutoOptions();
  }

  private void configureDriverOne() {
    driverOne.cross().onTrue(new InstantCommand(() -> fieldCentric = !fieldCentric));
    swerve.setDefaultCommand(
      new cmdSwerve_TeleOp(
          swerve,
          () -> MathUtil.applyDeadband(driverOne.getLeftY(), 0.1),
          () -> MathUtil.applyDeadband(driverOne.getLeftX(), 0.1),
          () -> MathUtil.applyDeadband(driverOne.getRightX(), 0.01),
          () -> fieldCentric));

    driverOne.PS().onTrue(new InstantCommand(() -> swerve.zeroHeading()));
  }

  private void addAutoOptions(){
    chooser.setDefaultOption("Do Nothing", new InstantCommand());
    chooser.addOption("Base- DO NOT USE IF POSSIBLE", new cmdAuto_Base(swerve, 0.25));
    chooser.addOption("CrossLine", new cmdAuto_CrossLine(swerve, 0.4));
    SmartDashboard.putData("Auto Options", chooser);
  }

  public void configureDriverTwo() {
    driverTwo.povUp().whileTrue(new cmdLongJohn_TeleOp(longJohn, true));
    driverTwo.povDown().whileTrue(new cmdLongJohn_TeleOp(longJohn, false));

    driverTwo.povRight().whileTrue(new cmdIntake_TeleOp(intake, true));
    driverTwo.povLeft().whileTrue(new cmdIntake_TeleOp(intake, false));

    //L1 moves the intke towards the battery
    driverTwo.L1().whileTrue(new cmdIntakeAngle_TeleOp(intakeAngle, true));
    //R1 moves the intake away from the battery7
    driverTwo.R1().whileTrue(new cmdIntakeAngle_TeleOp(intakeAngle, false));

    //use a variable to set .46 as the desired angle
    driverTwo.circle().whileTrue(new cmdAuto_IntakePosition(intakeAngle, Constants.MechanismConstants.SukPosition));
    driverTwo.square().whileTrue(new cmdAuto_IntakePosition(intakeAngle, Constants.MechanismConstants.shootPosition));
    driverTwo.cross().whileTrue(new cmdAuto_IntakePosition(intakeAngle, Constants.MechanismConstants.InPosition ));
    //code the other button(s) to set the position for shooting
  }

  public Command getAutonomousCommand() {
    return chooser.getSelected();
  }
}
