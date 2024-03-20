package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.subLongJohn;


public class cmdLongJohn_TeleOp extends Command {
    private subLongJohn longJohn;
    private boolean in;

    public cmdLongJohn_TeleOp(subLongJohn longJohn, boolean in) {
        this.longJohn = longJohn;
        this.in = in;
    }

    @Override
    public void execute() {
        longJohn.longJohnMotor.set((in) ? Constants.MechanismConstants.klongJohnSpeed : -Constants.MechanismConstants.klongJohnSpeed);
    }

    @Override
    public void end(boolean isFinished) {
        longJohn.longJohnMotor.set(0);
    }
}