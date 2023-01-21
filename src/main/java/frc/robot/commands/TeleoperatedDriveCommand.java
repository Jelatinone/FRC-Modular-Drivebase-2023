//Root Package
package frc.robot.commands;

import frc.robot.Constants;
//Local
import frc.robot.subsystems.SwerveSubsystem;
//Libraries
import edu.wpi.first.wpilibj2.command.CommandBase;
import java.util.function.DoubleSupplier;
import java.util.Objects;

public class TeleoperatedDriveCommand extends CommandBase
{
  //Suppress Warnings
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})

  //Instance Variables
  private final double JoystickL_X;
  private final double JoystickL_Y;
  private final double JoystickR_X;
  private final SwerveSubsystem Parent_Subsystem;
  private boolean Command_Complete = false;

  //Constructors
  public TeleoperatedDriveCommand(SwerveSubsystem Parent, DoubleSupplier Left_X, DoubleSupplier Left_Y, DoubleSupplier Right_X)
  {
    //Joystick Assignment
    if(Math.abs(Left_X.getAsDouble()) > Constants.JOYSTICK_DEADZONE){JoystickL_X = Left_X.getAsDouble();}else{JoystickL_X = 0.0;}
    if(Math.abs(Left_Y.getAsDouble()) > Constants.JOYSTICK_DEADZONE){JoystickL_Y = Left_Y.getAsDouble();}else{JoystickL_Y = 0.0;}
    if(Math.abs(Right_X.getAsDouble()) > Constants.JOYSTICK_DEADZONE){JoystickR_X = Right_X.getAsDouble();}else{JoystickR_X = 0.0;}
    //Parent Subsystem
    Parent_Subsystem = Parent;
    //Add Child Command To Parent Subsystem
    addRequirements(Parent_Subsystem);
  }

  //Initialize Command
  @Override
  public void initialize() {}

  //Execute Command
  @Override
  public void execute() 
  {
    Parent_Subsystem.SwerveDrive(JoystickL_X, JoystickL_Y, JoystickR_X);
    Command_Complete = true;
  }
  //End Command
  @Override
  public void end(boolean interrupted) {if(Objects.equals(interrupted,true)){Command_Complete = true;}}

  //Check Command Complete
  @Override
  public boolean isFinished() {return Command_Complete;}
}