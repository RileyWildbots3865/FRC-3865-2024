// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;
//import com.revrobotics.SparkPIDController;
import com.revrobotics.SparkAbsoluteEncoder.Type;

//import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class subIntakeAngle extends SubsystemBase {
  /** Creates a new subIntakeAngle. */
  public CANSparkMax intakeAngleMotor;
  public AbsoluteEncoder m_encoder;

  //private PIDController SetAnglePID = new PIDController(0,0,0);
  

  public subIntakeAngle() {
    intakeAngleMotor = new CANSparkMax(Constants.MechanismConstants.kintakeAnglecanId, MotorType.kBrushless);
    intakeAngleMotor.restoreFactoryDefaults();
    m_encoder = intakeAngleMotor.getAbsoluteEncoder(Type.kDutyCycle);

  }
  
     

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("armAbsolutePose", m_encoder.getPosition());
  }
}