package org.usfirst.frc.team2415.robot.autocommands;


import org.usfirst.frc.team2415.robot.intakecommands.IntakeCommand;
import org.usfirst.frc.team2415.robot.intakecommands.TogglePivotStateCommand;
import org.usfirst.frc.team2415.robot.subsystems.PivotSubsystem;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoIntake extends CommandGroup {

    public AutoIntake() {
        // Use requires() here to declare subsystem dependencies
        this.addSequential(new TogglePivotStateCommand(PivotSubsystem.INTERIOR));
        this.addSequential(new IntakeCommand(false), 1);
    }
}
