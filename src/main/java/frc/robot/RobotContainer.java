package frc.robot;

//import com.pathplanner.lib.auto.AutoBuilder;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandPS5Controller;
import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.cmdSwerve_TeleOp;
import frc.robot.subsystems.subSwerve;

public class RobotContainer {
  private final CommandPS5Controller driverOne = new CommandPS5Controller(OperatorConstants.DriverOne);
  private final subSwerve swerve = new subSwerve();
  SendableChooser<Command> chooser = new SendableChooser<>();
  public RobotContainer() {
    configureDriverOne();
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
    SmartDashboard.putData("Auto Options", chooser);
  }

  public Command getAutonomousCommand() {
    return chooser.getSelected();
  }
}