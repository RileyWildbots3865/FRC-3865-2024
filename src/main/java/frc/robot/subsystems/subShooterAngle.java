// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class subShooterAngle extends SubsystemBase {
  /** Creates a new subShooterAngle. */
  public CANSparkMax shooterAngleMotor;
  public subShooterAngle() {
    shooterAngleMotor = new CANSparkMax(Constants.MechanismConstants.kshooterAngleCanId, MotorType.kBrushed);
    shooterAngleMotor.restoreFactoryDefaults();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
