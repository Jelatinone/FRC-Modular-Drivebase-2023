//Package
package frc.robot;

//Libraries
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

//Constant Class
public final class Constants 
{
    //SWERVE SUBSYSTEM
    public static final Integer[] AZIMUTH_MOTORS_INDEX = {21,22,23,24};
    public static final Integer[] DRIVE_MOTORS_INDEX = {11,12,13,14};
    public static final Integer[] CANCODER_INDEX = {5,6,7,8};
    public static final Integer FACE_COUNT = 4;
    public static final Integer GYRO_INDEX = 4;  
    public static final Double[] CANCODER_OFFSET = {-313.682,-166.553,-246.006,-204.258};
    public static final Double WHEEL_DIAMETER_METERS = 0.1016;
    public static final Double DRIVE_GEAR_RATIO = 6.75;  
    public static final Double SS_D_KP = 0.044057;
    public static final Double SS_D_KI = 0.0;
    public static final Double SS_D_KF = 0.028998;
    public static final Double SS_A_KP = 0.4;
    public static final Double SS_A_KI = 0.0;
    public static final Double SS_A_KD = 12.0;
    public static final Boolean AUTOMATIC_INSTANCIZATION = false;

    //ROBOT CONTAINER
    public static final Integer CONTROLLER_INDEX = 0;
    public static final Trigger CONTROLLER_INCREMENT_BUTTON = new CommandXboxController(CONTROLLER_INDEX).a();
    public static final Trigger CONTROLLER_DECREMENT_BUTTON = new CommandXboxController(CONTROLLER_INDEX).b();

    //TELEOPERATED DRIVE COMMAND
    public static final Double LEFT_X_JOYSTICK_DEADZONE = 0.05;
    public static final Double LEFT_Y_JOYSTICK_DEADZONE = 0.05;
    public static final Double RIGHT_X_JOYSTICK_DEADZONE = 0.05;

}
