package main;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.usfirst.frc.team2415.robot.StreamerPacket;

@name(type = "shooterData")
public class ShooterHandler extends Handler{
	StreamerPacket packet;
	FileWriter writer;
	
	
	
	public ShooterHandler(StreamerPacket packet) {
		// TODO Auto-generated constructor stub
		
		this.packet=packet;
	}

	public ShooterHandler(){

		String csvFile = "/Users/omarimatthews/Desktop/shooterData.csv";
		try {
			writer = new FileWriter(csvFile,true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	//DO WHATEVER YOU SO CHOOSE WITH THE DATA
	//THIS IS WHERE YOU CAN WRITE TO A CSV
	@Override
	public void run() {
		//System.out.println(packet.toString());
		Main.modList(packet.toString());
		try{
			ArrayList<String> list = new ArrayList<String>();
			list.add(String.valueOf(packet.getDateCreated().getTime()));
			list.add(packet.getAttribute("shooterSpeed").toString());
			CSVUtils.writeLine(writer, list);
			writer.flush();	
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	void setPacket(StreamerPacket packet) {
		this.packet = packet;
		
	}

	@Override
	StreamerPacket getPacket() {
		return this.packet;
		
	}

}
