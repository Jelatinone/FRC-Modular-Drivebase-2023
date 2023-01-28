//Package
package frc.robot;

//Constant Class
public final class Constants 
{
    //SWERVE SUBSYSTEM
    public static final Integer[] ROTATIONAL_MOTORS_INDEX = {21,22,23,24};
    public static final Integer[] DRIVE_MOTORS_INDEX = {11,12,13,14};
    public static final Integer[] CANCODER_INDEX = {5,6,7,8};
    public static final Double[] CANCODER_OFFSET = {-313.682,-166.553,-246.006,-204.258};
    public static final Boolean AUTOMATIC_INSTANCIZATION = false;
    public static final Integer FACE_COUNT = 4;
    public static final Double SS_KP = 0.1;
    public static final Double SS_KI = 0.0;
    public static final Double SS_KD = 0.1;

    //TELEOPERATED DRIVE COMMAND
    public static final Double JOYSTICK_DEADZONE = 0.05;
}
