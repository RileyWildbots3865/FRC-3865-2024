package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.classes.mechanisms;

public class subMechanisms extends SubsystemBase {
    public static final double kshooterAngleCanId = 13;
    public static final double kintakeAnglecanId = 9;

    public static final double kshooterCanId = 10;
    public static final double kshooterInvCanId = 11;

    public static final double kintakeCanId = 12;

    private final subMechanisms shooterAngle = new subMechanisms(kshooterAngleCanId);
    private final subMechanisms shooter = new subMechanisms(kshooterCanId, kshooterInvCanId);
    private final subMechanisms intake = new subMechanisms(kintakeCanId);



   
    
        
    }

