//Root Package
package frc.robot.subsystems;


//Libraries
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.sensors.Pigeon2;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import java.util.Objects;

//Drive Subsystem Class
public class SwerveSubsystem extends SubsystemBase
{
  //S-Rotational List
  private WPI_TalonFX[] K_Rotational;
  private WPI_TalonFX[] N_Rotationals;
  //S-Drive List
  private WPI_TalonFX[] K_Drive;
  private WPI_TalonFX[] N_Drives;
  //Large List
  private final WPI_TalonFX[] Rotational;
  private final WPI_TalonFX[] Drive;  
  //Group Lists
  private final WPI_TalonFX[] [] Rotational_Groups;
  private final WPI_TalonFX[] [] Drive_Groups;  
  //PID Controller
  private PIDController M_PID;
  //Gyroscope
  final Pigeon2 M_Gyro;
  //Rotational Face
  private int R_Face;
  //Primary Constructor
  public SwerveSubsystem(Pigeon2 Gyro)
  {
    //PID Controller
    M_PID = new PIDController(Constants.SS_KP, Constants.SS_KI, Constants.SS_KD);
    //Modular Motor Instancisation
    Rotational = new WPI_TalonFX[(Constants.FACE_COUNT)];
    Rotational_Groups = new WPI_TalonFX[(Constants.FACE_COUNT)] [2];
    Drive = new WPI_TalonFX[(Constants.FACE_COUNT)];
    Drive_Groups = new WPI_TalonFX[(Constants.FACE_COUNT)] [2];
    for(int i = 0; i < Constants.FACE_COUNT; i++)
    {
      Drive[i] = new WPI_TalonFX(i);
      Rotational[i] = new WPI_TalonFX(i+Constants.FACE_COUNT);
      Drive[i].setNeutralMode(NeutralMode.Brake);
      Rotational[i].setNeutralMode(NeutralMode.Brake);
      if(Objects.equals((i % 2),0)){Rotational[i].setInverted(true);}
      Rotational[i].set((Objects.equals((i % 2),0))? (M_PID.calculate(Math.atan(-180/Constants.FACE_COUNT))): (M_PID.calculate(Math.atan(180/Constants.FACE_COUNT))));
    }
    for(int i = 0; i < Constants.FACE_COUNT-1; i++)
    {
      Rotational_Groups[i] = new WPI_TalonFX[] {Rotational[i],Rotational[i+1]};
      Drive_Groups[i] = new WPI_TalonFX[] {Drive[i],Drive[i+1]};
    }
    //Gyroscope
    M_Gyro = Gyro;
    //Rotational Face
    R_Face = 0;
  }
  //Periodic Subsystem
  @Override
  public void periodic() 
  {
    K_Drive = Drive_Groups[(((int)Math.round(M_Gyro.getCompassHeading()/((addFaces(Constants.FACE_COUNT, R_Face))/360)) > Drive_Groups.length)? (0): ((int)Math.round(M_Gyro.getCompassHeading()/((addFaces(Constants.FACE_COUNT, R_Face))/360))))];
    K_Rotational = Rotational_Groups[(((int)Math.round(M_Gyro.getCompassHeading()/((addFaces(Constants.FACE_COUNT, R_Face))/360)) > Rotational_Groups.length)? (0): ((int)Math.round(M_Gyro.getCompassHeading()/((addFaces(Constants.FACE_COUNT, R_Face))/360))))];
    N_Drives = new WPI_TalonFX[(Drive_Groups.length-1)];
    N_Rotationals = new WPI_TalonFX[(Rotational_Groups.length-1)];
    for(int i = 0, j = 0; i < (Drive.length) && j < (N_Drives.length); i++)
    {
      if(!(Objects.equals(Drive[i],K_Drive[0]))){N_Drives[j] = Drive[i];j++;}
      else if(!(Objects.equals(Drive[i],K_Drive[1]))){N_Drives[j] = Drive[i];j++;}
    }
    for(int i = 0, j = 0; i < (Rotational.length) && j < (N_Rotationals.length); i++)
    {
      if(!(Objects.equals(Rotational[i],K_Rotational[0]))){N_Rotationals[j] = Rotational[i];j++;}
      else if(!(Objects.equals(Rotational[i],K_Rotational[1]))){N_Rotationals[j] = Rotational[i];j++;}
    }
  }
  //Simulation Periodic
  @Override
  public void simulationPeriodic() {}
  
  //Drive Method
  public void SwerveDrive(double JoystickL_X, double JoystickL_Y, double JoystickR_X)
  {
    K_Drive[(JoystickR_X > 0)? (1): ((JoystickR_X < 0)? (0): (0))].set(M_PID.calculate(Math.pow(JoystickL_Y,2)));
    K_Rotational[(JoystickR_X > 0)? (1): ((JoystickR_X < 0)? (0): (0))].set(M_PID.calculate(Math.atan((180/(JoystickR_X * 100)))));
    for(WPI_TalonFX N_Drive: N_Drives)
    {
      if(!(Objects.equals(N_Drive,K_Drive[(JoystickR_X > 0)? (1): ((JoystickR_X < 0)? (0): (0))])))
        N_Drive.set(M_PID.calculate((Math.pow(JoystickL_Y,2) + Math.pow(JoystickL_Y,2))/2));
    }
    for(WPI_TalonFX N_Rotate: N_Rotationals)
    {
      if(!(Objects.equals(N_Rotate,K_Rotational[(JoystickR_X > 0)? (1): ((JoystickR_X < 0)? (0): (0))])))
        N_Rotate.set(M_PID.calculate((Math.atan(JoystickL_Y/JoystickL_X) + Math.atan((180/(JoystickR_X * 100))))/2));
    }
  }

  //Decrement
  public void DecrementRotationalFace(){if(Objects.equals(R_Face,0)) {R_Face = (Constants.FACE_COUNT-1);} else {R_Face--;}}

  //Increment
  public void IncrementRotationalFace(){if(Objects.equals(R_Face,(Constants.FACE_COUNT-1))) {R_Face = 0;} else {R_Face++;}}

  //Add Two Faces
  public int addFaces(int Face_One, int Face_Two){if((Face_One + Face_Two) > (Constants.FACE_COUNT-1)){return ((Face_One + Face_Two) - Constants.FACE_COUNT);}else{return (Face_One + Face_Two);} }
}
