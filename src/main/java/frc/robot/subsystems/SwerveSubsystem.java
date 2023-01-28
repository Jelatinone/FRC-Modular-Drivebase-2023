//Root Package
package frc.robot.subsystems;

//Libraries
import com.ctre.phoenix.sensors.SensorInitializationStrategy;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.TalonFXInvertType;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatorCurrentLimitConfiguration;
import com.ctre.phoenix.sensors.CANCoder;
import com.ctre.phoenix.sensors.Pigeon2;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import java.util.Objects;


//Drive Subsystem Class
public class SwerveSubsystem extends SubsystemBase
{
  //CANcoder List
  private CANCoder[] Encoder;
  //S-Rotational Lists
  private WPI_TalonFX[] K_Azimuth;
  private WPI_TalonFX[] N_Azimuth;
  //S-Drive Lists
  private WPI_TalonFX[] K_Drive;
  private WPI_TalonFX[] N_Drives;
  //Large Lists
  private final WPI_TalonFX[] Azimuth;
  private final WPI_TalonFX[] Drive;  
  //Group Lists
  private final WPI_TalonFX[] [] Azimuth_Groups;
  private final WPI_TalonFX[] [] Drive_Groups;  
  //Gyroscope
  private final Pigeon2 M_Gyro;
  //Rotational Face
  private int R_Face;
  //Primary Constructor
  public SwerveSubsystem(Pigeon2 Gyro)
  {
    //Grouping Instancization
    Encoder = new CANCoder[(Constants.FACE_COUNT)];
    Drive = new WPI_TalonFX[(Constants.FACE_COUNT)];
    Drive_Groups = new WPI_TalonFX[(Constants.FACE_COUNT)] [2];
    Azimuth = new WPI_TalonFX[(Constants.FACE_COUNT)];
    Azimuth_Groups = new WPI_TalonFX[(Constants.FACE_COUNT)] [2];
    //Modular Instancization
    if(Objects.equals(Constants.AUTOMATIC_INSTANCIZATION,true))
    {
      for(int i = 0; i < Constants.FACE_COUNT; i++)
      {
        try
        {
          Encoder[i] = new CANCoder(i);
          Drive[i] = new WPI_TalonFX(i);
          Azimuth[i] = new WPI_TalonFX(i+Constants.FACE_COUNT);
        }
        catch(NullPointerException exception) 
        {System.out.println("At Modular Instancization, Caught Null Pointer Exception. \n Could not Instancize Motor " + i);}
      }
    }
    //Default Instancization
    else
    {
      for(int i = 0; i < Constants.FACE_COUNT; i++)
      {
        try
        {
          Encoder[i] = new CANCoder(Constants.CANCODER_INDEX[i]);
          Drive[i] = new WPI_TalonFX(Constants.DRIVE_MOTORS_INDEX[i]);
          Azimuth[i] = new WPI_TalonFX(Constants.ROTATIONAL_MOTORS_INDEX[i]);
        }
        catch(NullPointerException exception) 
        {System.out.println("At Manual Instancization, Caught Null Pointer Exception. \n Could not Instancize Motor " + i);}
      }
    }
    //Positioning, Grouping, and Configurations
    for(int i = 0; i < Constants.FACE_COUNT; i++)
    {
      //Encoder Configuration
      Encoder[i].configFactoryDefault();
      Encoder[i].configMagnetOffset(Constants.CANCODER_OFFSET[i]);
      Encoder[i].configSensorInitializationStrategy(SensorInitializationStrategy.BootToAbsolutePosition);
      Encoder[i].setPositionToAbsolute();
      //Drive Configuration
      Drive[i].setNeutralMode(NeutralMode.Brake);
      Drive[i].setInverted(TalonFXInvertType.CounterClockwise);
      Drive[i].configStatorCurrentLimit(new StatorCurrentLimitConfiguration());
      Drive[i].config_kP(0, Constants.SS_D_KP);
      Drive[i].config_kF(0, Constants.SS_D_KF);
      //Rotational Configuration
      Azimuth[i].setNeutralMode(NeutralMode.Brake);
      Azimuth[i].configFactoryDefault();
      Azimuth[i].setInverted(TalonFXInvertType.CounterClockwise);
      Azimuth[i].configRemoteFeedbackFilter(Encoder[i], 0);
      Azimuth[i].configSelectedFeedbackSensor(FeedbackDevice.RemoteSensor0);
      Azimuth[i].setSelectedSensorPosition(Encoder[i].getAbsolutePosition());
      Azimuth[i].config_kP(0, Constants.SS_A_KP);
      Azimuth[i].config_kD(0, Constants.SS_A_KD);
      //Additional Grouping
      if(!Objects.equals(i,Constants.FACE_COUNT-1)){Azimuth_Groups[i] = new WPI_TalonFX[] {Azimuth[i],Azimuth[i+1]};}
      if(!Objects.equals(i,Constants.FACE_COUNT-1)){Drive_Groups[i] = new WPI_TalonFX[] {Drive[i],Drive[i+1]};}
      if(Objects.equals((i % 2),0)){Azimuth[i].setInverted(true);}
      //Turn to respective Angle
      toAngle(Azimuth[i],(Objects.equals((i % 2),0))? (Math.atan(-180/Constants.FACE_COUNT)): (Math.atan(180/Constants.FACE_COUNT)));
    }
    //Gyroscope
    M_Gyro = Gyro;
    M_Gyro.setYaw(0.0);
    //Rotational Face
    R_Face = 0;
  }
  //Periodic Subsystem
  @Override
  public void periodic() 
  {
    K_Drive = Drive_Groups[(((int)Math.round(M_Gyro.getCompassHeading()/((addFaces(Constants.FACE_COUNT, R_Face))/360)) > Drive_Groups.length)? (0): ((int)Math.round(M_Gyro.getCompassHeading()/((addFaces(Constants.FACE_COUNT, R_Face))/360))))];
    K_Azimuth = Azimuth_Groups[(((int)Math.round(M_Gyro.getCompassHeading()/((addFaces(Constants.FACE_COUNT, R_Face))/360)) > Azimuth_Groups.length)? (0): ((int)Math.round(M_Gyro.getCompassHeading()/((addFaces(Constants.FACE_COUNT, R_Face))/360))))];
    N_Drives = new WPI_TalonFX[(Drive_Groups.length-1)];
    N_Azimuth = new WPI_TalonFX[(Azimuth_Groups.length-1)];
    for(int i = 0, j = 0; i < (Drive.length) && j < (N_Drives.length); i++)
    {
      if(!(Objects.equals(Drive[i],K_Drive[0]))){N_Drives[j] = Drive[i];j++;}
      else if(!(Objects.equals(Drive[i],K_Drive[1]))){N_Drives[j] = Drive[i];j++;}
    }
    for(int i = 0, j = 0; i < (Azimuth.length) && j < (N_Azimuth.length); i++)
    {
      if(!(Objects.equals(Azimuth[i],K_Azimuth[0]))){N_Azimuth[j] = Azimuth[i];j++;}
      else if(!(Objects.equals(Azimuth[i],K_Azimuth[1]))){N_Azimuth[j] = Azimuth[i];j++;}
    }
  }
  //Simulation Periodic
  @Override
  public void simulationPeriodic() {}
  
  //Drive Method
  public void SwerveDrive(double JoystickL_X, double JoystickL_Y, double JoystickR_X)
  {
    toSpeed(K_Drive[(JoystickR_X > 0)? (1): ((JoystickR_X < 0)? (0): (0))],(Math.pow(JoystickL_Y,2)));
    toAngle(K_Azimuth[(JoystickR_X > 0)? (1): ((JoystickR_X < 0)? (0): (0))],(Math.atan((180/(JoystickR_X * 100)))));
    for(WPI_TalonFX N_Drive: N_Drives)
    {
      if(!(Objects.equals(N_Drive,K_Drive[(JoystickR_X > 0)? (1): ((JoystickR_X < 0)? (0): (0))])))
        toSpeed(N_Drive,((Math.pow(JoystickL_Y,2) + Math.pow(JoystickL_Y,2))/2));
    }
    for(WPI_TalonFX N_Rotate: N_Azimuth)
    {
      if(!(Objects.equals(N_Rotate,K_Azimuth[(JoystickR_X > 0)? (1): ((JoystickR_X < 0)? (0): (0))])))
        toAngle(N_Rotate,((Math.atan(JoystickL_Y/JoystickL_X) + Math.atan((180/(JoystickR_X * 100)))/2)));
    }
  }
  
  //Add Two Faces
  public int addFaces(int Face_One, int Face_Two){if((Face_One + Face_Two) > (Constants.FACE_COUNT-1)){return ((Face_One + Face_Two) - Constants.FACE_COUNT);}else{return (Face_One + Face_Two);}}

  //Turn to Angle
  public void toAngle(WPI_TalonFX Motor, Double Angle) {Motor.set(ControlMode.Position,((4096/(2*Math.PI)) * Angle));}

  //Turn to Speed
  public void toSpeed(WPI_TalonFX Motor, Double Speed) {Motor.set(ControlMode.Velocity,(Motor.getSelectedSensorVelocity()*Constants.DRIVE_GEAR_RATIO/(Math.PI * Constants.WHEEL_DIAMETER_METERS)*4096)/10);}

  //Decrement
  public void DecrementRotationalFace(){if(Objects.equals(R_Face,0)) {R_Face = (Constants.FACE_COUNT-1);} else {R_Face--;}}

  //Increment
  public void IncrementRotationalFace(){if(Objects.equals(R_Face,(Constants.FACE_COUNT-1))) {R_Face = 0;} else {R_Face++;}}
}
