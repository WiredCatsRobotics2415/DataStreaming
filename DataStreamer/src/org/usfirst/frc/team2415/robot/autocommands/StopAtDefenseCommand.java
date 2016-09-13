package org.usfirst.frc.team2415.robot.autocommands;

import org.usfirst.frc.team2415.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Drives the robot a given speed backwards until it reaches the defenses.
 * @author Jabari
 *
 */
public class StopAtDefenseCommand extends Command {
	private final double SPEED;
	private long start;
	
	public StopAtDefenseCommand(double speed){
    	requires(Robot.driveSubsystem);
    	SPEED = speed;
	}
	
	protected void initialize() {
		start = System.currentTimeMillis();
	}

    private boolean isDone = false;
    protected void execute() {
    	Robot.driveSubsystem.setMotors(SPEED, -SPEED);
    	
    	if((System.currentTimeMillis() - start)/1000.0 <= .5) return;
    	
    	if(Math.abs(Robot.driveSubsystem.getPitch()) > 4){
			isDone = true;
			Robot.driveSubsystem.setMotors(0, 0);
			Robot.driveSubsystem.enableLeftBreakState();
			Robot.driveSubsystem.enableRightBreakState();
    	}
	}

	protected boolean isFinished() {
		return isDone;
	}

	protected void end() {
		Robot.driveSubsystem.setMotors(0, 0);
		Robot.driveSubsystem.enableLeftBreakState();
		Robot.driveSubsystem.enableRightBreakState();
		
	}

	protected void interrupted() {
		isDone = true;
		Robot.driveSubsystem.setMotors(0, 0);
		Robot.driveSubsystem.enableLeftBreakState();
		Robot.driveSubsystem.enableRightBreakState();
		
	}

}
