// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class subIntakeAngle extends SubsystemBase {
  /** Creates a new subIntakeAngle. */
  public CANSparkMax intakeAngleMotor;
  public subIntakeAngle() {
    intakeAngleMotor = new CANSparkMax(Constants.MechanismConstants.kintakeAnglecanId, MotorType.kBrushed);
    intakeAngleMotor.restoreFactoryDefaults();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
