//Root Package
package frc.robot;

//Local
import frc.robot.commands.AutonomousDriveCommand;
import frc.robot.commands.TeleoperatedDriveCommand;
import frc.robot.subsystems.SwerveSubsystem;
//Libraries
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.Command;
import com.ctre.phoenix.sensors.Pigeon2;
import java.lang.NullPointerException;
import java.util.Objects;
//Container Class
public class RobotContainer
{
  //INSTANCE VARIABLES
  //Subsystems
  private SwerveSubsystem M_Drive;
  //Controllers
  private CommandXboxController M_Controller;
  //Buttons
  private Trigger Controller_A;
  private Trigger Controller_B;
  //Gyroscopes
  private Pigeon2 M_Gyro;

  //Constructor
  public RobotContainer() 
  {
      //Controllers
      for(int i = 0; i <= 20 && Objects.equals(M_Controller,null); i++)
      {
        try{M_Controller = new CommandXboxController(i);}
        catch(NullPointerException x) {M_Controller = null; System.out.println("Error: XboxController Not Found"); System.exit(0);}
      }
      //Buttons
      try{Controller_A = M_Controller.a(); }
      catch(NullPointerException x) {System.out.println("Error: XboxController A Button Not Found"); System.exit(0);}
      try{Controller_A = M_Controller.b(); }
      catch(NullPointerException x) {System.out.println("Error: XboxController B Button Not Found"); System.exit(0);}
      //Gyroscopes
      try{M_Gyro = new Pigeon2(4);}
      catch(NullPointerException x) {System.out.println("Error: Gyroscope Not Found"); System.exit(0);}
      //Subsystems
      M_Drive = new SwerveSubsystem(M_Gyro);  
      //Set Default
      M_Drive.setDefaultCommand(new TeleoperatedDriveCommand(
      M_Drive,
      () -> M_Controller.getLeftX(),
      () -> M_Controller.getLeftY(),
      () -> M_Controller.getRightX(),
      M_Gyro));     
      //Configure Bindings
      configureButtonBindings();
  }
  //Config Bindings
  private void configureButtonBindings() 
  {
    //When A Pressed, Increment Rotational Face.
    Controller_A.onFalse(Commands.run(M_Drive::IncrementRotationalFace));
    //When B Pressed, Decrement Rotational Face.
    Controller_B.onFalse(Commands.run(M_Drive::DecrementRotationalFace));
  }
  //ACESSORS

  //Return Drive
  public SwerveSubsystem getDrive(){return M_Drive;}
  //Return Autonomous Command
  public Command getAutonomousCommand(){return new AutonomousDriveCommand(M_Drive);}
  //Return Controller
  public CommandXboxController getController(){return M_Controller;}
  //Return Gyroscope
  public Pigeon2 getGyro() {return M_Gyro;}
}