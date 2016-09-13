package org.usfirst.frc.team2415.robot.autocommands.SimpleObstacles;

import org.usfirst.frc.team2415.robot.Robot;
import org.usfirst.frc.team2415.robot.autocommands.AutoIntake;
import org.usfirst.frc.team2415.robot.autocommands.PixyAutoCommand;
import org.usfirst.frc.team2415.robot.autocommands.StraightDriveCommand;
import org.usfirst.frc.team2415.robot.autocommands.WaitCommand;
import org.usfirst.frc.team2415.robot.catapultcommands.FireCatapultCommand;
import org.usfirst.frc.team2415.robot.intakecommands.TogglePivotStateCommand;
import org.usfirst.frc.team2415.robot.subsystems.PivotSubsystem;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class LowBarAutonomous extends CommandGroup {
    
	private Subsystem[] subs = {(Subsystem)Robot.pivotSubsystem, (Subsystem)Robot.driveSubsystem};
	
    public  LowBarAutonomous() {
    	addSequential(new TogglePivotStateCommand(PivotSubsystem.GROUND));
        addSequential(new WaitCommand(1, subs));
        addSequential(new StraightDriveCommand(17, .5));
        addSequential(new WaitCommand(1, subs));
        addSequential(new PixyAutoCommand());
        addSequential(new WaitCommand(1, subs));
        addSequential(new AutoIntake());
        addSequential(new FireCatapultCommand());
    }
}
