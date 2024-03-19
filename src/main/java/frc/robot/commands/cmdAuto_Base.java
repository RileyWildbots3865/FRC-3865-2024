// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.subsystems.subSwerve;

public class cmdAuto_Base extends Command {
  /** Creates a new cmdAuto_Base. */
  subSwerve swerve;
  double speed;
  Timer time;
  public cmdAuto_Base(subSwerve swerve, double speed) {
    this.swerve = swerve;
    this.speed = speed;
    addRequirements(swerve);
    time = new Timer();
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    time.reset();
    time.start();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(time.get() < 2){
      swerve.drive(-speed, 0, 0, true);
    }
    else if (time.get() < 3){
      swerve.drive(0, 0, 0, true);
    }
    else if (time.get() < 4){
      swerve.drive(speed, 0, 0, true);
    }
    else{
      swerve.drive(0, 0, 0, true);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    swerve.stopModules();
    time.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return time.hasElapsed(4);
  }
}
