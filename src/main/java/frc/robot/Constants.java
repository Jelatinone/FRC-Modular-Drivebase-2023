//Package
package frc.robot;

//Libraries
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

//Constant Class
public final class Constants 
{
    /**Swerve subsystem constants class*/
    public static class SWERVE
    {
        /** Azimuth Motor Indexes */
        public static final Integer[] AZIMUTH_MOTORS_INDEX_VALUES = {21,22,23,24};
        /** Drive Motor Indexes */
        public static final Integer[] DRIVE_MOTORS_INDEX_VALUES = {11,12,13,14};
        /** CanCODER Indexex */
        public static final Integer[] CANCODER_INDEX_VALUES = {5,6,7,8};
        /**Number of Faces */
        public static final Integer FACE_COUNT = 4;
        /** Main Gyroscope Index Value */
        public static final Integer GYRO_INDEX = 4;  
        /** CanCODER Offset Values */
        public static final Double[] CANCODER_OFFSET_VALUES = {-313.682,-166.553,-246.006,-204.258};
        /** Module Wheel Diameters in Meters */
        public static final Double WHEEL_DIAMETER_METERS = 0.1016;
        /** Drive Gear Train Ratio */
        public static final Double DRIVE_GEAR_RATIO = 6.75;  
        /** Drive PIF Controller KP Value */
        public static final Double DRIVE_KP = 0.044057;
        /** Drive PIF Controller KI Value */
        public static final Double DRIVE_KI = 0.0;
        /**  Drive PIF Controller KF Value */
        public static final Double DRIVE_KF = 0.028998;
        /** Drive PID Controller KP Value */        
        public static final Double AZIMUTH_KP = 0.4;
        /** Drive PID Controller KI Value */
        public static final Double AZIMUTH_KI = 0.0;
        /** Drive PID Controller KD Value */
        public static final Double AZIMUTH_KD = 12.0;
        /** Modular Instancization Boolean */
        public static final Boolean AUTOMATIC_INSTANCIZATION = false;
    }
    /** Robot container constants class */
    public static class CONTAINER
    {
        /** Primary Controller Index */
        public static final Integer CONTROLLER_INDEX = 0;
        /** Primary Controller Increment Button */
        public static final Trigger CONTROLLER_INCREMENT_BUTTON = new CommandXboxController(CONTROLLER_INDEX).a();
        /** Primary Controller Decrement Button */
        public static final Trigger CONTROLLER_DECREMENT_BUTTON = new CommandXboxController(CONTROLLER_INDEX).b();
    }

    /** Teleoperated drive command constants class */
    public static class TELEDRIVECOMMAND
    {
        /** Primary Controller Left X Deadzone */
        public static final Double LEFT_X_JOYSTICK_DEADZONE = 0.05;
        /** Primary Controller Left Y Deadzone */
        public static final Double LEFT_Y_JOYSTICK_DEADZONE = 0.05;
        /** Primary Controller Right X Deadzone */
        public static final Double RIGHT_X_JOYSTICK_DEADZONE = 0.05;
    }

}
