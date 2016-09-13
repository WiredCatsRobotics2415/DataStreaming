package org.usfirst.frc.team2415.robot.subsystems;

import org.usfirst.frc.team2415.robot.RobotMap;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class PivotSubsystem extends Subsystem {
    
    private Solenoid longPiston1, longPiston2, shortPiston1, shortPiston2;
    
    public static final byte INTERIOR = 0;
    public static final byte INTAKE = 1;
    public static final byte OUTTAKE = 2;
    public static final byte GROUND = 3;
    
    private byte lastState;
    
    public static byte intakeState;

    public PivotSubsystem(){
    	longPiston1 = new Solenoid(RobotMap.PCM_ID, RobotMap.LONG_SOLENOID[0]);
    	longPiston2 = new Solenoid(RobotMap.PCM_ID, RobotMap.LONG_SOLENOID[1]);
    	shortPiston1 = new Solenoid(RobotMap.PCM_ID, RobotMap.SHORT_SOLENOID[0]); //forward
    	shortPiston2 = new Solenoid(RobotMap.PCM_ID, RobotMap.SHORT_SOLENOID[1]); //backwards
    }
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void setPivot(byte state){
    	if(state == this.INTAKE){
    		longPiston1.set(true);
    		longPiston2.set(false);
    		shortPiston1.set(false);
    		shortPiston2.set(true);
    		intakeState = this.INTAKE;
//    		if(!Robot.intakingSubsystem.getIR()) {
//    			Robot.intakingSubsystem.setIntakeSpeed(1);
//    		}
    		
    	}
    	if(state == this.GROUND){
    		longPiston1.set(true);
    		longPiston2.set(false);
    		shortPiston1.set(true);
    		shortPiston2.set(false);
    		intakeState = this.GROUND;
    	}
    	if(state == this.INTERIOR){
    		longPiston1.set(false);
    		longPiston2.set(true);
    		shortPiston1.set(false);
    		shortPiston2.set(true);
    		intakeState = this.INTERIOR;
    	}
    	if(state == this.OUTTAKE){
    		longPiston1.set(false);
    		longPiston2.set(true);
    		shortPiston1.set(true);
    		shortPiston2.set(false);
    		intakeState = this.OUTTAKE;
    	}
    }
    
    public byte getState(){
    	return intakeState;
    }
    
    public byte getlastState(){
    	return lastState;
    }
    
    public void setLastState(byte state){
    	lastState = state;
    }
    
    public boolean isIntaking(){
    	return intakeState == 1;
    }
    
    public void updateStatus(){
    	SmartDashboard.putBoolean("Forward Short Piston", shortPiston1.get());
    	SmartDashboard.putBoolean("Backward Short Piston", shortPiston2.get());
    }
}
