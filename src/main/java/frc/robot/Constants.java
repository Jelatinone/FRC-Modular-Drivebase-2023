//Package
package frc.robot;

//Constant Class
public final class Constants 
{
    //SWERVE SUBSYSTEM
    public static final int[] DRIVE_MOTORS_INDEX = {11,12,13,14};
    public static final int[] ROTATIONAL_MOTORS_INDEX = {21,22,23,24};
    public static final Boolean AUTOMATIC_INSTANCIZATION = true;
    public static final Integer FACE_COUNT = 4;
    public static final Double SS_KP = 0.1;
    public static final Double SS_KI = 0.0;
    public static final Double SS_KD = 0.1;

    //TELEOPERATED DRIVE COMMAND
    public static final Double JOYSTICK_DEADZONE = 0.05;
}
