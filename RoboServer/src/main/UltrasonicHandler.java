package main;

import org.usfirst.frc.team2415.robot.StreamerPacket;

//import org.usfirst.frc.team2415.robot.StreamerPacket;

@name(type = "Ultrasonic")
public class UltrasonicHandler extends Handler {
	StreamerPacket packet;
	@Override
	public void run() {
		// TODO Auto-generated method stub
		//Main.modUltraList(packet.toString());
	}
	
	@Override
	void setPacket(StreamerPacket packet) {
		// TODO Auto-generated method stub
		this.packet=packet;
	}

	@Override
	StreamerPacket getPacket() {
		// TODO Auto-generated method stub
		return packet;
	}

}
