package org.usfirst.frc.team2415.robot.autocommands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class WaitCommand extends Command {
    
	private double time, start;
	private boolean isDone = false;
	
    public WaitCommand(double time, Subsystem[] subs){
		this.time = time;
    	if(subs == null) return;
    	for(Subsystem sys : subs) requires(sys);
    }
    
    public WaitCommand(double time, Subsystem sys){
		this.time = time;
    	if(sys == null) return;
    	requires(sys);
    }

    protected void initialize() {
    	start = System.currentTimeMillis();
    }

    protected void execute() {
    	if((System.currentTimeMillis() - start)/1000.0 >= time) isDone = true;
    }

    protected boolean isFinished() {
        return isDone;
    }
    
    protected void end() {
    }

    protected void interrupted() {
    }
}