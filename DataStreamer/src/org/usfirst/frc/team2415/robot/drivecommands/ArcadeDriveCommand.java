package org.usfirst.frc.team2415.robot.drivecommands;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.net.URLConnection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.usfirst.frc.team2415.robot.Robot;
import org.usfirst.frc.team2415.robot.WiredCatGamepad;
import org.usfirst.frc.team2415.robot.WiredCatJoystick;
import org.usfirst.frc.team2415.robot.subsystems.DriveSubsystem;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2415.robot.DataThread;
/**
 *
 */
public class ArcadeDriveCommand extends Command {
	String ip = "IP of web server";
	ExecutorService executor = Executors.newFixedThreadPool(5);
	private float DEADBAND = 0.05f;
	private float INTERPOLATION_FACTOR = 0.75f;   //Nathan's Settings
	private float STRAIGHT_LIMITER = 0.95f;
	private float TURN_BOOSTER = 1.3f;
	
//	private float DEADBAND = 0.05f;
//	private float INTERPOLATION_FACTOR = 0.5f;
//	private float STRAIGHT_LIMITER = .95f;
//	private float TURN_BOOSTER = 1.1f;
	

    public ArcadeDriveCommand() {
        requires(Robot.driveSubsystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.driveSubsystem.stop();
    	Robot.driveSubsystem.disableRightBreakState();
    	Robot.driveSubsystem.disableLeftBreakState();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	double leftY, rightX;
    	
    	if(Robot.singlePlayerMode){
    		leftY = -Robot.operator.Y();
        	rightX = Robot.operator.X();
    	} else {
    		leftY = -Robot.gamepad.leftY();
        	rightX = Robot.gamepad.rightX();
    	}
    	
    	if(Math.abs(leftY) < DEADBAND) leftY = 0;
    	if(Math.abs(rightX) < DEADBAND) rightX = 0;
    	
    	leftY = INTERPOLATION_FACTOR*Math.pow(leftY, 3) + (1 - INTERPOLATION_FACTOR)*leftY;
    	rightX = INTERPOLATION_FACTOR*Math.pow(rightX, 3) + (1 - INTERPOLATION_FACTOR)*rightX;
    	
    	double left = leftY*STRAIGHT_LIMITER + rightX*TURN_BOOSTER;
    	double right =  leftY*STRAIGHT_LIMITER - rightX*TURN_BOOSTER;
    	
//    	if(Math.abs(left) >= 1) Robot.driveSubsystem.enableRightBreakState();
//    	if(Math.abs(right) >= 1) Robot.driveSubsystem.enableLeftBreakState();
    	
    	if(Robot.gamepad.rightBumper.get()){
        	Robot.driveSubsystem.setMotors(left*0.25, -right*0.25);
    	} else {
        	Robot.driveSubsystem.setMotors(left, -right);
    	}
    	
    	Double leftEncoder = Robot.driveSubsystem.getLeftEncoder();
    	Double rightEncoder = Robot.driveSubsystem.getRightEncoder();
    	
    	
    	
    	String url = "http://"+ ip + ":8080/home";
    	
    	Map<String, String> data = new HashMap<String, String>();
		
		//whatever data you want put it here in the string format
		data.put("left", String.valueOf(Robot.driveSubsystem.getLeftEncoder()));
		data.put("right", String.valueOf(Robot.driveSubsystem.getRightEncoder()));
		
		
		// for data formatting
		data.put("timeStamp", String.valueOf((new Date()).getTime()));
		
		//thread
		
		executor.execute(new DataThread(url,data));
    	
    	
    	//:TODO make it stream encoder values
    }

    
    public void doSubmit(String url, Map<String, String> data) throws IOException {
        URL siteUrl = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) siteUrl.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", 
               "application/x-www-form-urlencoded");
        conn.setUseCaches (true);
        conn.setDoOutput(true);
        conn.setDoInput(true);

        DataOutputStream out = new DataOutputStream(conn.getOutputStream());

        Set keys = data.keySet();
        Iterator keyIter = keys.iterator();
        String content = "";
        for(int i=0; keyIter.hasNext(); i++) {
            Object key = keyIter.next();
            if(i!=0) {
                content += "&";
            }
            content += key + "=" + URLEncoder.encode(data.get(key), "UTF-8");
        }
        System.out.println(content);
        out.writeBytes(content);
        out.flush();
        out.close();
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line = "";
        while((line=in.readLine())!=null) {
            System.out.println(line);
        }
        in.close();
    }
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.driveSubsystem.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.driveSubsystem.stop();
    }
}
