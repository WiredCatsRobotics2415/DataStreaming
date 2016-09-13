package org.usfirst.frc.team2415.robot.autocommands;

import org.usfirst.frc.team2415.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class MindlessTraverseCommand extends Command {
	
	long start;
	boolean isDone = false;
	
    public MindlessTraverseCommand() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.driveSubsystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.driveSubsystem.setMotors(0, 0);
    	Robot.driveSubsystem.disableLeftBreakState();
    	Robot.driveSubsystem.disableRightBreakState();
    	start = System.currentTimeMillis();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.driveSubsystem.setMotors(1, -1);
    	if((System.currentTimeMillis() - start)/1000.0 >= 2.25) isDone = true;
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isDone;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.driveSubsystem.setMotors(0, 0);
    	Robot.driveSubsystem.enableLeftBreakState();
    	Robot.driveSubsystem.enableRightBreakState();
    	start = System.currentTimeMillis();
    	while((System.currentTimeMillis() - start)/1000.0 < .5){
    		Robot.driveSubsystem.setMotors(-.5, .5);
    	}
    	Robot.driveSubsystem.setMotors(0, 0);
    	
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.driveSubsystem.setMotors(0, 0);
    	Robot.driveSubsystem.enableLeftBreakState();
    	Robot.driveSubsystem.enableRightBreakState();
    }
}
