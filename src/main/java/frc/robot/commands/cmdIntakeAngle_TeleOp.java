package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.subIntakeAngle;


public class cmdIntakeAngle_TeleOp extends Command {
    private subIntakeAngle angle;
    private boolean in;

    public cmdIntakeAngle_TeleOp(subIntakeAngle angle, boolean in) {
        this.angle = angle;
        this.in = in;
    }

    @Override
    public void execute() {
        angle.intakeAngleMotor.set((in) ? Constants.MechanismConstants.kAngleSpeed : -Constants.MechanismConstants.kAngleSpeed);
    }

    @Override
    public void end(boolean isFinished) {
        angle.intakeAngleMotor.set(0);
    }
}