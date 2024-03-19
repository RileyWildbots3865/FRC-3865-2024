package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.subIntakeAngle;

public class cmdAuto_IntakePosition extends Command {
  subIntakeAngle intake;
  double goal;
  PIDController PIDIntake = new PIDController(0.022, 0, 0);
  public cmdAuto_IntakePosition(subIntakeAngle intake, Double goal) {
    this.intake = intake;
    this.goal = goal;
    PIDIntake.setIntegratorRange(-0.5, 0.5);
    addRequirements(intake);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    intake.TeleOp(-PIDIntake.calculate(goal, intake.getEncoder()));
    System.out.println(PIDIntake.calculate(goal, intake.getEncoder()));
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return false;
  }
}
