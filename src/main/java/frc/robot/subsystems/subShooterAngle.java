// // Copyright (c) FIRST and other WPILib contributors.
// // Open Source Software; you can modify and/or share it under the terms of
// // the WPILib BSD license file in the root directory of this project.

// package frc.robot.subsystems;

// import com.revrobotics.CANSparkMax;
// import com.revrobotics.RelativeEncoder;
// import com.revrobotics.CANSparkLowLevel.MotorType;
// import com.revrobotics.SparkRelativeEncoder;

// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
// import edu.wpi.first.wpilibj2.command.SubsystemBase;
// import frc.robot.Constants;

// public class subShooterAngle extends SubsystemBase {
//   /** Creates a new subShooterAngle. */
//   public CANSparkMax shooterAngleMotor;
//   public RelativeEncoder m_encoder;


//   public subShooterAngle() {
//     shooterAngleMotor = new CANSparkMax(Constants.MechanismConstants.kshooterAngleCanId, MotorType.kBrushed);
//     shooterAngleMotor.restoreFactoryDefaults();

//     m_encoder = shooterAngleMotor.getEncoder(SparkRelativeEncoder.Type.kQuadrature, 8192);
//     m_encoder.setPositionConversionFactor(2* Math.PI);

//     shooterAngleMotor.enableSoftLimit(CANSparkMax.SoftLimitDirection.kForward, true);
//     shooterAngleMotor.enableSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, true);
    
//     shooterAngleMotor.setSoftLimit(CANSparkMax.SoftLimitDirection.kForward, 0);
//     shooterAngleMotor.setSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, 30);
//   }

//   @Override
//   public void periodic() {
//     // This method will be called once per scheduler run
//     SmartDashboard.putNumber("Pos", m_encoder.getPosition());
//   }
// }
