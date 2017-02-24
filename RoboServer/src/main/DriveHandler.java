package main;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.usfirst.frc.team2415.robot.StreamerPacket;

@name(type = "driveData")
public class DriveHandler extends Handler {
	StreamerPacket packet;
	FileWriter writer;

	public DriveHandler(StreamerPacket packet) {
		// TODO Auto-generated constructor stub

		this.packet = packet;
	}

	public DriveHandler() {
		String csvFile = "/Users/omarimatthews/Desktop/driveData.csv";
		try {
			writer = new FileWriter(csvFile, true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// DO WHATEVER YOU SO CHOOSE WITH THE DATA
	// THIS IS WHERE YOU CAN WRITE TO A CSV
	@Override
	public void run() {
		// System.out.println(packet.toString());
		Main.modList(packet.toString());
		try {

			ArrayList<String> list = new ArrayList<String>();
			list.add(String.valueOf(packet.getDateCreated().getTime()));
			list.add(packet.getAttribute("Yaw").toString());
			list.add(packet.getAttribute("leftSpeed").toString());
			list.add(packet.getAttribute("rightSpeed").toString());
			list.add(packet.getAttribute("leftPosition").toString());
			list.add(packet.getAttribute("rightPosition").toString());
			CSVUtils.writeLine(writer, list);

			writer.flush();

		} catch (Exception e) {
			
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
