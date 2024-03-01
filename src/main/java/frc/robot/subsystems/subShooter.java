// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class subShooter extends SubsystemBase {
  /** Creates a new subShooter. */
  public CANSparkMax shooterUpperMotor;
  public CANSparkMax shooterLowerMotor;
  public subShooter() {
    shooterUpperMotor = new CANSparkMax(Constants.MechanismConstants.kshooterCanId, MotorType.kBrushless);
    shooterLowerMotor = new CANSparkMax(Constants.MechanismConstants.kshooterInvCanId, MotorType.kBrushless);
    shooterLowerMotor.follow(shooterUpperMotor, true);
    shooterUpperMotor.restoreFactoryDefaults();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
