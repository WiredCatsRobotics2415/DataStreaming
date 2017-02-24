package main;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.usfirst.frc.team2415.robot.StreamerPacket;

@name(type = "carouselData")
public class CarouselHandler extends Handler{
	StreamerPacket packet;
	FileWriter writer;
	
	
	
	public CarouselHandler(StreamerPacket packet) {
		// TODO Auto-generated constructor stub
		
		this.packet=packet;
	}

	public CarouselHandler(){

		String csvFile = "/Users/omarimatthews/Desktop/carouselData.csv";
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
			list.add(packet.getAttribute("carouselCurrent").toString());
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
