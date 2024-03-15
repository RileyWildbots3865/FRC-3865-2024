package frc.robot;

//import com.pathplanner.lib.auto.AutoBuilder;
import com.revrobotics.SparkAbsoluteEncoder.Type;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandPS5Controller;
import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.cmdAuto_Base;
import frc.robot.commands.cmdAuto_CrossLine;
// import frc.robot.commands.cmdIntake;
import frc.robot.commands.cmdSwerve_TeleOp;
import frc.robot.subsystems.subIntake;
import frc.robot.subsystems.subIntakeAngle;
// import frc.robot.subsystems.subLongJohn;
//import frc.robot.subsystems.subLongJohn;
//import frc.robot.subsystems.subShooter;
//import frc.robot.subsystems.subShooterAngle;
import frc.robot.subsystems.subSwerve;

public class RobotContainer {
  private final CommandPS5Controller driverOne = new CommandPS5Controller(OperatorConstants.DriverOne);
  private final CommandPS5Controller driverTwo = new CommandPS5Controller(OperatorConstants.DriverTwo);
  private final subSwerve swerve = new subSwerve();
  private final subIntake intake = new subIntake();
  private final subIntakeAngle intakeAngle = new subIntakeAngle();
  // private final subLongJohn longJohn = new subLongJohn();
  //private final subShooter shooter = new subShooter();
  //private final subShooterAngle shooterAngle = new subShooterAngle();
  //private final subLongJohn longJohn = new subLongJohn();
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
    chooser.addOption("CrossLine", new cmdAuto_CrossLine(swerve, 0.4));
    SmartDashboard.putData("Auto Options", chooser);
  }

  public void configureDriverTwo() {
    // driverTwo.povUp().onTrue(new InstantCommand(() -> longJohn.longJohnMotor.set(Constants.MechanismConstants.klongJohnSpeed)));
    // driverTwo.povUp().onFalse(new InstantCommand(() -> longJohn.longJohnMotor.set(0)));

    // driverTwo.povRight().whileTrue(new cmdIntake(intake, true));
    driverTwo.povRight().onTrue(new InstantCommand(() -> intake.intakeMotor.set(Constants.MechanismConstants.kintakeSpeedIn)));
    driverTwo.povRight().onFalse(new InstantCommand(() -> intake.intakeMotor.set(0))); 
    // driverTwo.povLeft().whileTrue(new cmdIntake(intake, false));

    driverTwo.povLeft().onTrue(new InstantCommand(() -> intake.intakeMotor.set(-Constants.MechanismConstants.kintakeSpeedIn)));
    driverTwo.povLeft().onFalse(new InstantCommand(() -> intake.intakeMotor.set(0))); 

    driverTwo.L2().onTrue(new InstantCommand(() -> intake.intakeMotor.set(-Constants.MechanismConstants.kintakeSpeedOut)));
    driverTwo.L2().onFalse(new InstantCommand(() -> intake.intakeMotor.set(0))); 
    
    driverTwo.R1().onTrue(new InstantCommand(() -> intakeAngle.intakeAngleMotor.set(Constants.MechanismConstants.kintakeAngleSpeed)));
    driverTwo.R1().onFalse(new InstantCommand(() -> intakeAngle.intakeAngleMotor.set(0)));
    
    driverTwo.R2().onTrue(new InstantCommand(() -> intakeAngle.intakeAngleMotor.set(-Constants.MechanismConstants.kintakeAngleSpeed)));
    driverTwo.R2().onFalse(new InstantCommand(() -> intakeAngle.intakeAngleMotor.set(0)));

    // driverTwo.L1().onTrue(new InstantCommand(() -> intakeAngle.intakeAngleMotor(set)
    //use a variable to set .46 as the desired angle
    driverTwo.circle().onTrue(new InstantCommand(() -> moveToPosition(Constants.MechanismConstants.SukPosition)));
    driverTwo.square().onTrue(new InstantCommand(() -> moveToPosition(Constants.MechanismConstants.shootPosition)));
    driverTwo.cross().onTrue(new InstantCommand(() -> moveToPosition(Constants.MechanismConstants.InPosition)));
    //code the other button(s) to set the position for shooting
  }

  public Command getAutonomousCommand() {
    return chooser.getSelected();
  }

  public void moveToPosition(double DesiredPosition){
    double currAngle = intakeAngle.intakeAngleMotor.getAbsoluteEncoder(Type.kDutyCycle).getPosition();

    while (Math.abs(DesiredPosition - currAngle) > .03
    ){
      //add if logic to determine which direction the motor needs to turn
      //add max and min angles, so that the motor doens't try to go all the way around 
      if (currAngle > DesiredPosition){
        intakeAngle.intakeAngleMotor.set(-Constants.MechanismConstants.kpresetAngleSpeed);
        currAngle = intakeAngle.intakeAngleMotor.getAbsoluteEncoder(Type.kDutyCycle).getPosition();
      }
      
      else
      intakeAngle.intakeAngleMotor.set(Constants.MechanismConstants.kpresetAngleSpeed);
      currAngle = intakeAngle.intakeAngleMotor.getAbsoluteEncoder(Type.kDutyCycle).getPosition();
      
    }
    intakeAngle.intakeAngleMotor.set(0);
  }
}
