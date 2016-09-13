package org.usfirst.frc.team2415.robot.subsystems;

import org.usfirst.frc.team2415.robot.RobotMap;
import org.usfirst.frc.team2415.robot.drivecommands.ArcadeDriveCommand;

import com.kauailabs.nav6.frc.IMU;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveSubsystem extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	private final int BAUD_RATE = 57600;
	
	private final byte REFRESH_RATE = 50;
	
	public CANTalon leftTalOne, leftTalTwo, rightTalOne, rightTalTwo;
	private Encoder rightEncoder, leftEncoder;
	private IMU imu;
	public static final double 	TICKS_PER_REV = 120, WHEEL_RADIUS = 4,
								WHEEL_TRACK = 25 + (3./16);	//radius and track in inches
	
	
	
	public DriveSubsystem(){
		leftTalOne = new CANTalon(RobotMap.LEFT_TALON_BACK);
		leftTalTwo = new CANTalon(RobotMap.LEFT_TALON_FRONT);
		rightTalOne = new CANTalon(RobotMap.RIGHT_TALON_BACK);
		rightTalTwo = new CANTalon(RobotMap.RIGHT_TALON_FRONT);
		
		rightEncoder = new Encoder(RobotMap.RIGHT_ENCODER[0],RobotMap.RIGHT_ENCODER[1]);
		leftEncoder = new Encoder(RobotMap.LEFT_ENCODER[0],RobotMap.LEFT_ENCODER[1]);
		
		resetEncoders();
		
		SerialPort imuSerialPort = new SerialPort(BAUD_RATE, SerialPort.Port.kMXP);
		imu = new IMU(imuSerialPort, REFRESH_RATE);
		imu.zeroYaw();
		

		LiveWindow.addActuator("Drive Subsystem", "Left Back Talon", leftTalOne);
		LiveWindow.addActuator("Drive Subsystem", "Left Front Talon", leftTalTwo);
		LiveWindow.addActuator("Drive Subsystem", "Right Back Talon", rightTalOne);
		LiveWindow.addActuator("Drive Subsystem", "Right Front Talon", rightTalTwo);
		LiveWindow.addSensor("Drive Subsystem", "Left Encoder", leftEncoder);
		LiveWindow.addSensor("Drive Subsystem", "Right Encoder", rightEncoder);
		
	}
	
    public void initDefaultCommand() {
    	setDefaultCommand(new ArcadeDriveCommand());
    	
    }
    
    public void stop(){
    	leftTalOne.set(0);
    	leftTalTwo.set(0);
    	rightTalOne.set(0);
    	rightTalTwo.set(0);
    }
    
    public void setMotors(double left, double right){
    	leftTalOne.set(left);
    	leftTalTwo.set(left);
    	rightTalOne.set(right);
    	rightTalTwo.set(right);
    }
    
    public double getLeftTal(){
    	return leftTalOne.get();
    }
    
    public double getRightTal(){
    	return rightTalOne.get();
    }
    
    public double getRightEncoder(){
    	return -rightEncoder.get();
    }
    
   
    public double getLeftEncoder(){
    	return leftEncoder.get();
    }
    
    public void resetEncoders(){
    	leftEncoder.reset();
    	rightEncoder.reset();
    }
    
    public double getYaw(){
    	return imu.getYaw();
    }
    
    public double getPitch(){
    	return imu.getPitch();
    }
    
    public double getRoll(){
    	return imu.getRoll();
    }
    
    public void resetYaw(){
    	imu.zeroYaw();
    }
    
    public void enableRightBreakState(){
    	rightTalOne.enableBrakeMode(true);
    	rightTalTwo.enableBrakeMode(true);
    }
    
    public void enableLeftBreakState(){
    	leftTalOne.enableBrakeMode(true);
    	leftTalTwo.enableBrakeMode(true);
    }
    
    public void disableRightBreakState(){
    	rightTalOne.enableBrakeMode(false);
    	rightTalTwo.enableBrakeMode(false);
    }
    
    public void disableLeftBreakState(){
    	leftTalOne.enableBrakeMode(false);
    	leftTalTwo.enableBrakeMode(false);
    }
    
    /**
     * Get's the break state of the right side
     * @return True if break is on, false if coast is on
     */
    public boolean getRightBreakState(){
    	return (rightTalOne.getBrakeEnableDuringNeutral() && rightTalTwo.getBrakeEnableDuringNeutral());
    }
    
    /**
     * Get's the break state of the left side
     * @return True if break is on, false if coast is on
     */
    public boolean getLeftBreakState(){
    	return (leftTalOne.getBrakeEnableDuringNeutral() && leftTalTwo.getBrakeEnableDuringNeutral());
    }

	public void updateStatus() {
		SmartDashboard.putNumber("Left Encoder", getLeftEncoder());
		SmartDashboard.putNumber("Right Encoder", getRightEncoder());
		SmartDashboard.putNumber("Yaw", getYaw());
		SmartDashboard.putNumber("Pitch", getPitch());
		SmartDashboard.putNumber("Roll", getRoll());
		SmartDashboard.putNumber("Right Talon", getRightTal());
		SmartDashboard.putNumber("Left Talon", getLeftTal());
		SmartDashboard.putBoolean("Left Break State", getLeftBreakState());
		SmartDashboard.putBoolean("Right Break State", getRightBreakState());
		
	}
}

