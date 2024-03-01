package frc.robot.classes;

import com.ctre.phoenix6.hardware.CANcoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkPIDController;

import edu.wpi.first.math.controller.PIDController;

public class mechanisms {
    private final CANcoder rotationEncoder;

    private final CANSparkMax shooterSparkMax;
    private final CANSparkMax intakeSparkMax;

    private final RelativeEncoder shooterEncoder;

    private final SparkPIDController shooterPidController;
    private final PIDController intakePIDControlller;

    private final String name; 

    public mechanismsModule(int shooterCanId, int shooterAgnleCanId, int intakeCanId, int intakeAngleCanId)

    
}
