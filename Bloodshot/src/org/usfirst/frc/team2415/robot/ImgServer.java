package org.usfirst.frc.team2415.robot;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.ni.vision.NIVision;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.vision.USBCamera;

public class ImgServer {
	
	private final int CAM_W = 640, CAM_H = 480,
					  EXPOSURE = 25, WHITE_BALANCE = USBCamera.WhiteBalance.kFixedIndoor,
					  BRIGHTNESS = 25, FPS = 30;
	
	//UPDATE AT COMPETITION AFTER MOUNTING CAMERA
	private final int[] TARGET = {(int)((8.1/13.7)*CAM_W), (int)((2/10.2)*CAM_H)};
	
	private final NIVision.Point VERT1 = new NIVision.Point(TARGET[0], TARGET[1] - 30),
								 VERT2 = new NIVision.Point(TARGET[0], TARGET[1] + 30),
								 HORI1 = new NIVision.Point(TARGET[0] - 30, TARGET[1]),
								 HORI2 = new NIVision.Point(TARGET[0] + 30, TARGET[1]);
	
	private USBCamera cam;
	private ServerSocket serverSocket;
	
	private NIVision.Image frame;
	private NIVision.RawData colorTable;
	
	private long lastTime;
	
	public ImgServer(String camName, int port){
		cam = new USBCamera(camName);
		
		cam.openCamera();
		cam.startCapture();
		cam.setSize(CAM_W, CAM_H);
		cam.setFPS(FPS);
		
		cam.setBrightness(BRIGHTNESS);
//		cam.setExposureManual(EXPOSURE);
//		cam.setExposureHoldCurrent();
//		
//		cam.setWhiteBalanceManual(WHITE_BALANCE);
//		cam.setWhiteBalanceHoldCurrent();
		
		frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
		colorTable = new NIVision.RawData();
		
		try{
			serverSocket = new ServerSocket(port);
			serverSocket.setSoTimeout(100);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		lastTime = System.currentTimeMillis();
	}
	
	public void showImg(){
		cam.getImage(frame);
		CameraServer.getInstance().setImage(frame);
	}
	
	public void teleopShowImg(){
		cam.getImage(frame);
		NIVision.imaqDrawLineOnImage(frame, frame, NIVision.DrawMode.DRAW_VALUE, VERT1, VERT2, 0.0f);
		NIVision.imaqDrawLineOnImage(frame, frame, NIVision.DrawMode.DRAW_VALUE, HORI1, HORI2, 0.0f);
		CameraServer.getInstance().setImage(frame);
		
	}
	
	public void sendImg(){
		if(System.currentTimeMillis() - lastTime < 100) return;
		
		cam.getImage(frame);
    	NIVision.imaqWriteJPEGFile(frame, "/home/lvuser/img.jpg", 2000, colorTable);
		
		try{
			Socket server = serverSocket.accept();
			DataOutputStream out = new DataOutputStream(server.getOutputStream());
			File load = new File("/home/lvuser/img.jpg");
			byte[] buffer = new byte[(int)load.length()];
			
			int count;
			BufferedInputStream img = new BufferedInputStream(new FileInputStream(load));
			
			while((count = img.read(buffer)) > 0){
				  out.write(buffer, 0, count);
				  out.flush();
			}
			
			DataInputStream in = new DataInputStream(server.getInputStream());
			System.out.println(in.readDouble());
			
			server.close();
		}catch(Exception e){
			System.out.println("No Connection!");
		}
		lastTime = System.currentTimeMillis();
	}
}
