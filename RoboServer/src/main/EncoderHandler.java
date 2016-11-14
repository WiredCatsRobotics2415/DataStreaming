package main;

@name(type = "Encoder")
public class EncoderHandler extends Handler{
	StreamerPacket packet;
	
	
	
	public EncoderHandler(StreamerPacket packet) {
		// TODO Auto-generated constructor stub
		
		this.packet=packet;
	}

	public EncoderHandler(){
		
	}
	//DO WHATEVER YOU SO CHOOSE WITH THE DATA
	@Override
	public void run() {
		//System.out.println(packet.toString());
		Main.modList(packet.toString());
		
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
