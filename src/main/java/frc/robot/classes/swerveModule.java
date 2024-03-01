package frc.robot.classes;

import com.ctre.phoenix6.configs.CANcoderConfiguration;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.signals.AbsoluteSensorRangeValue;
import com.ctre.phoenix6.signals.SensorDirectionValue;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkPIDController;
import com.revrobotics.CANSparkBase.IdleMode;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class swerveModule {
    private final CANcoder rotationEncoder;

    private final CANSparkMax drivingSparkMax;
    private final CANSparkMax turningSparkMax;

    private final RelativeEncoder drivingEncoder;

    private final SparkPIDController drivingPIDController;
    private final PIDController turningPIDController;

    private final String name;

    private SwerveModuleState desiredState = new SwerveModuleState(0.0, new Rotation2d());

    public swerveModule(int drivingCANId, int turningCANId, int canCoderId, double angularOffset, String name) {
        this.name = name;
        rotationEncoder = new CANcoder(canCoderId);
        CANcoderConfiguration canCoderConfiguration = new CANcoderConfiguration();
        canCoderConfiguration.MagnetSensor.AbsoluteSensorRange = AbsoluteSensorRangeValue.Unsigned_0To1;
        canCoderConfiguration.MagnetSensor.MagnetOffset = -angularOffset;
        //canCoderConfiguration.MagnetSensor.SensorDirection = SensorDirectionValue.CounterClockwise_Positive;
        rotationEncoder.getConfigurator().apply(canCoderConfiguration);
        rotationEncoder.getPosition().setUpdateFrequency(100);
        rotationEncoder.getVelocity().setUpdateFrequency(100);

        drivingSparkMax = new CANSparkMax(drivingCANId, MotorType.kBrushless);
        turningSparkMax = new CANSparkMax(turningCANId, MotorType.kBrushless);

        drivingSparkMax.restoreFactoryDefaults();
        turningSparkMax.restoreFactoryDefaults();

        //drivingSparkMax.setInverted(true);
        turningSparkMax.setInverted(true);

        drivingEncoder = drivingSparkMax.getEncoder();

        drivingPIDController = drivingSparkMax.getPIDController();
        drivingPIDController.setFeedbackDevice(drivingEncoder);

        drivingEncoder.setPositionConversionFactor(moduleConstants.kDrivingEncoderPositionFactor);
        drivingEncoder.setVelocityConversionFactor(moduleConstants.kDrivingEncoderVelocityFactor);

        drivingPIDController.setP(moduleConstants.kDrivingP);
        drivingPIDController.setI(moduleConstants.kDrivingI);
        drivingPIDController.setD(moduleConstants.kDrivingD);
        drivingPIDController.setFF(moduleConstants.kDrivingFF);
        drivingPIDController.setOutputRange(moduleConstants.kDrivingMinOutput, moduleConstants.kDrivingMaxOutput);

        drivingSparkMax.setIdleMode(moduleConstants.kDrivingMotorIdleMode);
        turningSparkMax.setIdleMode(IdleMode.kCoast);//moduleConstants.kTurningMotorIdleMode);
        drivingSparkMax.setSmartCurrentLimit(moduleConstants.kDrivingMotorCurrentLimit);
        turningSparkMax.setSmartCurrentLimit(moduleConstants.kTurningMotorCurrentLimit);

        turningPIDController = new PIDController(0.01, 0, 0);
         turningPIDController.setIntegratorRange(-1, 1);
        turningPIDController.enableContinuousInput(0, 360);

        drivingSparkMax.burnFlash();
        turningSparkMax.burnFlash();

        drivingEncoder.setPosition(0);
    }
    
    public SwerveModulePosition getPosition() {
        return new SwerveModulePosition(drivingEncoder.getPosition(), getRotation2d());
    }
    
    public void setDesiredState(SwerveModuleState desiredState) {
        SwerveModuleState optimizedDesiredState = SwerveModuleState.optimize(desiredState, getRotation2d());
        if(Math.abs(optimizedDesiredState.speedMetersPerSecond) < 0.02){
            stopModule();
            return;
        }
        drivingPIDController.setReference(optimizedDesiredState.speedMetersPerSecond, CANSparkMax.ControlType.kVelocity);

        turningSparkMax.set(turningPIDController.calculate(getAngle(), optimizedDesiredState.angle.getDegrees()));
        SmartDashboard.putNumber(name + " Wheel Angle: ", getAngle());
        SmartDashboard.putNumber(name + " Wheel Goal", optimizedDesiredState.angle.getDegrees());
        SmartDashboard.putNumber(name + " Wheel PID", turningPIDController.calculate(getAngle(), optimizedDesiredState.angle.getDegrees()));
    }
    public double getAngle(){
        return rotationEncoder.getAbsolutePosition().getValueAsDouble() * 360;
    }
    public double getRawAngle(){        
        return rotationEncoder.getAbsolutePosition().getValueAsDouble();    
    }
    public Rotation2d getRotation2d() {   
        return Rotation2d.fromDegrees(getAngle());     
    }
    public void stopModule(){         
        drivingSparkMax.stopMotor(); turningSparkMax.stopMotor();    
    }
}
