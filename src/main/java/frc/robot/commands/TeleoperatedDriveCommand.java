//Root Package
package frc.robot.commands;

import frc.robot.Constants;
//Local
import frc.robot.subsystems.SwerveSubsystem;
//Libraries
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.math.controller.PIDController;
import com.ctre.phoenix.sensors.Pigeon2;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
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
  private final PIDController M_PID;
  private boolean Command_Complete = false;


  //Constructors
  public TeleoperatedDriveCommand(SwerveSubsystem Parent, DoubleSupplier Left_X, DoubleSupplier Left_Y, DoubleSupplier Right_X,Pigeon2 gyro)
  {
    //Joystick Assignment
    if(Left_X.getAsDouble() > Constants.JOYSTICK_DEADZONE){JoystickL_X = Left_X.getAsDouble();}else{JoystickL_X = 0.0;}
    if(Left_Y.getAsDouble() > Constants.JOYSTICK_DEADZONE){JoystickL_Y = Left_Y.getAsDouble();}else{JoystickL_Y = 0.0;}
    if(Right_X.getAsDouble() > Constants.JOYSTICK_DEADZONE){JoystickR_X = Right_X.getAsDouble();}else{JoystickR_X = 0.0;}
    //Parent Subsystem
    Parent_Subsystem = Parent;
    //PID Controller
    M_PID = new PIDController(Constants.TDC_KP,Constants.TDC_KI,Constants.TDC_KD);
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
    Parent_Subsystem.getKDrive()[(JoystickR_X > 0)? (1): ((JoystickR_X < 0)? (0): (0))].set(M_PID.calculate(Math.pow(JoystickL_Y,2)));
    Parent_Subsystem.getKRotate()[(JoystickR_X > 0)? (1): ((JoystickR_X < 0)? (0): (0))].set(M_PID.calculate(Math.atan((180/(JoystickR_X * 100)))));
    for(WPI_TalonFX N_Drive: Parent_Subsystem.getNDrives())
    {
      if(!(Objects.equals(N_Drive,Parent_Subsystem.getKDrive()[(JoystickR_X > 0)? (1): ((JoystickR_X < 0)? (0): (0))])))
        N_Drive.set(M_PID.calculate((Math.pow(JoystickL_Y,2)+Math.pow(JoystickL_Y,2))/2));
    }
    for(WPI_TalonFX N_Rotates: Parent_Subsystem.getNRotates())
    {
      if(!(Objects.equals(N_Rotates,Parent_Subsystem.getKRotate()[(JoystickR_X > 0)? (1): ((JoystickR_X < 0)? (0): (0))])))
        N_Rotates.set(M_PID.calculate((Math.atan(JoystickL_Y/JoystickL_X)+Math.atan((180/(JoystickR_X * 100))))/2));
    }
    Command_Complete = true;
  }
  //End Command
  @Override
  public void end(boolean interrupted) {if(Objects.equals(interrupted,true)){Command_Complete = true;}}

  //Check Command Complete
  @Override
  public boolean isFinished() {return Command_Complete;}
}