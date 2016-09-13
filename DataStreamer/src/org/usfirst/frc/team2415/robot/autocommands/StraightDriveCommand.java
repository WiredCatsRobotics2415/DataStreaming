package org.usfirst.frc.team2415.robot.autocommands;

import java.util.ArrayList;

import org.usfirst.frc.team2415.robot.DataAnalyzer;
import org.usfirst.frc.team2415.robot.PID;
import org.usfirst.frc.team2415.robot.Robot;
import org.usfirst.frc.team2415.robot.subsystems.DriveSubsystem;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class StraightDriveCommand extends Command {
	
	private double leftStart, rightStart;
	private double distance, leftErr, rightErr;
	private double speedCap;
	private double stdErrLeft = 0, stdErrRight = 0;
	private PID pidLeft, pidRight;
	boolean isDone = false;
	private static final double STEADY_STATE_TOLERANCE = .05,
								SAMPLE_SIZE = 6;
	
	private ArrayList<Double> leftSamples, rightSamples;
	
    public StraightDriveCommand(double distance, double speedCap) {
    	
    	pidLeft = new PID(0.01, 0,0.0015);
    	pidRight = new PID(0.01, 0, 0.0015);
    	
    	this.speedCap = speedCap;
    	
    	this.distance = (distance/(2*Math.PI*DriveSubsystem.WHEEL_RADIUS))*DriveSubsystem.TICKS_PER_REV;
    	
    	leftSamples = /*rightSamples =*/ new ArrayList<Double>();
    }

    protected void initialize() {
    	Robot.driveSubsystem.disableLeftBreakState();
    	Robot.driveSubsystem.disableRightBreakState();
    	Robot.driveSubsystem.resetEncoders();
    	leftStart = Robot.driveSubsystem.getLeftEncoder();
//    	rightStart = Robot.driveSubsystem.getRightEncoder();
    }

    protected void execute() {
    	leftErr =  distance - (Robot.driveSubsystem.getLeftEncoder() - leftStart);
//    	rightErr = distance - (Robot.driveSubsystem.getRightEncoder() - rightStart);
    	
    	if(leftSamples.size() >= SAMPLE_SIZE){
    		leftSamples.remove(0);
    		leftSamples.add(leftErr);
    		stdErrLeft = DataAnalyzer.stdError(leftSamples);
    	}else leftSamples.add(leftErr);

//    	if(rightSamples.size() >= SAMPLE_SIZE){
//    		rightSamples.remove(0);
//    		rightSamples.add(rightErr);
//    		stdErrRight = DataAnalyzer.stdError(rightSamples);
//    	}else rightSamples.add(rightErr);
    	
    	if(Math.abs(leftErr/distance) < STEADY_STATE_TOLERANCE /*&&
    			Math.abs(rightErr/distance) < STEADY_STATE_TOLERANCE*/) isDone = true;
    	
    	if(stdErrLeft <= STEADY_STATE_TOLERANCE && /*stdErrRight <= STEADY_STATE_TOLERANCE &&*/ 
    			stdErrLeft != 0 /*&& stdErrRight != 0*/) isDone = true;
    	
    	double leftOut = pidLeft.pidOut(leftErr);
    	//double rightOut = pidRight.pidOut(rightErr);
    	

    	if(Math.abs(leftOut) > speedCap) leftOut = ((leftOut > 0) ? 1:-1) * speedCap;
    	//if(Math.abs(rightOut) > speedCap) rightOut = ((rightOut > 0) ? 1:-1) * speedCap;
    	
    	Robot.driveSubsystem.setMotors(leftOut, -leftOut);
    }

    protected boolean isFinished() {
        return isDone;
    }

    protected void end() {
    	Robot.driveSubsystem.stop();
    	Robot.driveSubsystem.enableLeftBreakState();
    	Robot.driveSubsystem.enableRightBreakState();
    }

    protected void interrupted() {
    	Robot.driveSubsystem.stop();
    	Robot.driveSubsystem.enableLeftBreakState();
    	Robot.driveSubsystem.enableRightBreakState();
    }
}
