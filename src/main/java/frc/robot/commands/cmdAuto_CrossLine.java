// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.subSwerve;

public class cmdAuto_CrossLine extends Command {
  /** Creates a new cmdAuto_CrossLine. */
  subSwerve swerve;
  double speed;
  Timer time;
  public cmdAuto_CrossLine(subSwerve swerve, double speed) {
    // Use addRequirements() here to declare subsystem dependencies.
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
    if(time.get() < 5){
      swerve.drive(-speed, 0, 0);
    } else {
      swerve.drive(0, 0, 0);
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
    return time.hasElapsed(5);
  }
}
