//Package
package frc.robot;

//Constant Class
public final class Constants 
{
    //SWERVE SUBSYSTEM
    //Motor Indices
    public static final Integer[] ROTATIONAL_MOTORS_INDEX = {21,22,23,24};
    public static final Integer[] DRIVE_MOTORS_INDEX = {11,12,13,14};
    //Cancoder Values
    public static final Integer[] CANCODER_INDEX = {5,6,7,8};
    public static final Double[] CANCODER_OFFSET = {-313.682,-166.553,-246.006,-204.258};
    //Drive PIF Values
    public static final Double SS_D_KP = 0.044057;
    public static final Double SS_D_KI = 0.0;
    public static final Double SS_D_KF = 0.028998;
    //Azimuth PID Values
    public static final Double SS_A_KP = 0.4;
    public static final Double SS_A_KI = 0.0;
    public static final Double SS_A_KD = 12.0;
    //Information
    public static final Boolean AUTOMATIC_INSTANCIZATION = false;
    public static final Integer FACE_COUNT = 4;
    public static final Double WHEEL_DIAMETER_METERS = 0.1016;
    public static final Double DRIVE_GEAR_RATIO = 6.75;

    //TELEOPERATED DRIVE COMMAND
    public static final Double JOYSTICK_DEADZONE = 0.05;
}
