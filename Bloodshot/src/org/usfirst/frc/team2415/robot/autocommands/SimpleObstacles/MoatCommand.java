package org.usfirst.frc.team2415.robot.autocommands.SimpleObstacles;

import org.usfirst.frc.team2415.robot.Robot;
import org.usfirst.frc.team2415.robot.autocommands.SimpleTraverseCommand;
import org.usfirst.frc.team2415.robot.autocommands.StopAtDefenseCommand;
import org.usfirst.frc.team2415.robot.autocommands.WaitCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class MoatCommand extends CommandGroup {
	
	public MoatCommand(){
		Robot.driveSubsystem.resetYaw();
		addSequential(new SimpleTraverseCommand(.75));
		addSequential(new WaitCommand(1, Robot.driveSubsystem));
		//addSequential(new StopAtDefenseCommand(-.25));
	}
	
}
