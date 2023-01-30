//Root Package
package frc.robot;

//Local
import frc.robot.commands.*;
import frc.robot.subsystems.SwerveSubsystem;
//Libraries
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.Command;
import java.lang.NullPointerException;

//Container Class
public class RobotContainer
{
  //INSTANCE VARIABLES
  //Subsystems
  private SwerveSubsystem M_Drive;
  //Controllers
  private CommandXboxController M_Controller;
  //Buttons
  private Trigger Controller_Increment_Button;
  private Trigger Controller_Decrement_Button;
 
  //Constructor
  public RobotContainer() 
  {
      //Controllers
      try{M_Controller = new CommandXboxController(Constants.CONTROLLER_INDEX);}
      catch(NullPointerException x) {M_Controller = null; System.out.println("Error: XboxController Not Found");}
      //Buttons
      try{Controller_Increment_Button = Constants.CONTROLLER_INCREMENT_BUTTON; }
      catch(NullPointerException x) {System.out.println("Error: XboxController Increment Button Not Found");}
      try{Controller_Decrement_Button = Constants.CONTROLLER_DECREMENT_BUTTON; }
      catch(NullPointerException x) {System.out.println("Error: XboxController Decrement Button Not Found");}
      //Subsystems
      M_Drive = new SwerveSubsystem();  
      //Set Default
      M_Drive.setDefaultCommand(new TeleoperatedDriveCommand(
      M_Drive,
      () -> M_Controller.getLeftX(),
      () -> M_Controller.getLeftY(),
      () -> M_Controller.getRightX()));     
      //Configure Bindings
      configureButtonBindings();
  }
  //Config Bindings
  private void configureButtonBindings() 
  {
    //When Increment Button Pressed, Increment Rotational Face
    Controller_Increment_Button.onTrue(Commands.run(M_Drive::IncrementRotationalFace));
    //When Decrement Button Pressed, Decrement Rotational Face
    Controller_Decrement_Button.onTrue(Commands.run(M_Drive::DecrementRotationalFace));
  }
  //ACESSORS

  //Return Autonomous Command
  public Command getAutonomousCommand(){return new AutonomousDriveCommand(M_Drive);}
}