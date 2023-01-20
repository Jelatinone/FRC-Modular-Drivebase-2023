//Root Package
package frc.robot.commands;

//Local
import frc.robot.subsystems.SwerveSubsystem;

//Libraries
import edu.wpi.first.wpilibj2.command.CommandBase;
import java.util.Objects;

public class AutonomousDriveCommand extends CommandBase
{
    //Suppress Warnings
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})

    //Instance Variables
    private boolean Command_Complete = false;
    private final SwerveSubsystem Parent_Subsystem;

    //Constructors
    public AutonomousDriveCommand(SwerveSubsystem Parent)
    {
        //Parent Subsystem
        Parent_Subsystem = Parent;
        //Add Command To Parent Subsystem
        addRequirements(Parent_Subsystem);
    }
    //Initialize Command
    @Override
    public void initialize() {}
    //Execute Command
    @Override
    public void execute() 
    {
        new TeleoperatedDriveCommand(Parent_Subsystem,() -> 0.1 ,() -> 0.1,() ->1.0);
        Command_Complete = true;
    }
    //End Command
    @Override
    public void end(boolean interrupted) {if(Objects.equals(interrupted,true)){Command_Complete = true;}}
    //Check Command Complete
    @Override
    public boolean isFinished() {return Command_Complete;}
}

