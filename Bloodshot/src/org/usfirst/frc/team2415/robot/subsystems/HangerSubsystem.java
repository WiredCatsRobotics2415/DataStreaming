package org.usfirst.frc.team2415.robot.subsystems;

import org.usfirst.frc.team2415.robot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class HangerSubsystem extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	private Solenoid lock, unlock;
	private CANTalon winchMotor;
	private boolean isLocked;
	
	public HangerSubsystem(){
		winchMotor = new CANTalon(RobotMap.WINCH_MOTOR);
		lock = new Solenoid(RobotMap.PCM_ID, RobotMap.WINCH_LOCK[0]);
		unlock = new Solenoid(RobotMap.PCM_ID, RobotMap.WINCH_LOCK[1]);
		unlockWinch();
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void winch(){
    	lock.set(false);
    	unlock.set(true);
    	winchMotor.set(-0.3);
    }
    
    public void winchMotor(double speed){
    	winchMotor.set(speed);
    }
    
    public boolean isLocked(){
    	return isLocked;
    }
    
    public void lockWinch(){
    	lock.set(true);
    	unlock.set(false);
    	isLocked = true;
    }
    
    public void unlockWinch(){
    	lock.set(false);
    	unlock.set(true);
    	isLocked = false;
    }
    
    public void stop(){
    	lock.set(true);
    	unlock.set(false);
    	winchMotor.set(0);
    }
}

