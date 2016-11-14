package main;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Date;

public class ExampleUsage {
	
	
	// CREATE A NEW DATASENDER OBJECT IN THE ROBOT CLASS, AND USE THAT OBJECT EVERYWHERE YOU WANT TO USE IT
	
	//static DataSender sender = new DataSender("IP ADDRESS OF SERVER", /*PORT OF SERVER:*/ 0);
	static DataSender sender = new DataSender("127.0.0.1", 9102);
	public static void main(String[] args) throws IOException {
		
		//EXAMPLE:
		
		//This is like the executor method of the command
		while(true){
			//INITIALIZE A STREAMERPACKET. IT AUTO CREATES THE SEND DATE AS THE CURRENT TIME 
			//IF YOU CREATE IT SIGNIFICANTLY BEFORE YOU SEND IT, USE PACKETNAME.SETDATECURRENT
			//YOU CAN INITIALIZE IT WITH IT'S TYPE, BUT IF YOU DONT YOU CAN USE SETTYPE
			StreamerPacket packet = new StreamerPacket("Encoder");
			//TO ADD VALUES DO THIS:
			packet.addAttribute("leftEncoder", 0);
			packet.addAttribute("rightEncoder", 0);
			//TO SEND:
			sender.send(packet);
		}
		
		
		
		
	}

}
