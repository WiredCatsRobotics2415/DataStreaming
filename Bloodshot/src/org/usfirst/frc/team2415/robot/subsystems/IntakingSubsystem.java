package org.usfirst.frc.team2415.robot.subsystems;

import java.util.ArrayList;

import org.usfirst.frc.team2415.robot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class IntakingSubsystem extends Subsystem {

    public static DigitalInput leftIR, rightIR;
    private CANTalon intakeMotor;
    
    ArrayList<Boolean> samples;
	private final int SAMPLE_SIZE = 6;
    
    public IntakingSubsystem(){
    	leftIR = new DigitalInput(RobotMap.INTAKE_IR[0]);
    	rightIR = new DigitalInput(RobotMap.INTAKE_IR[1]);
    	intakeMotor = new CANTalon(RobotMap.SPIN_INTAKE_TALON);

    	samples = new ArrayList<Boolean>();
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void enableBreakMode(){
    	intakeMotor.enableBrakeMode(true);
    }
    
    public void setIntakeSpeed(double speed){
    	intakeMotor.set(speed);
    }
    
    public double getIntakeSpeed(){
    	return intakeMotor.get();
    }
    
    public boolean getIR(){
    	return leftIR.get() || rightIR.get();
    }
    
    public double getIntakeCurrent(){
    	return intakeMotor.getOutputCurrent();
    }
    
    public void updateStatus(){
    	SmartDashboard.putNumber("Intake Speed", getIntakeSpeed());
    	SmartDashboard.putNumber("Intake Current", getIntakeCurrent());
    	SmartDashboard.putBoolean("IR Sensor", getIR());

    	SmartDashboard.putBoolean("Left IR Sensor", leftIR.get());
    	SmartDashboard.putBoolean("Right IR Sensor", rightIR.get());
    }
}

