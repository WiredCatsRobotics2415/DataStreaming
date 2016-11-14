package main;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;

public class DataSender {
	
	String ip;
	int port;
	DatagramSocket sock;
	public DataSender(String ip, int port){
		this.ip=ip;
		this.port = port;
		try {
			sock = new DatagramSocket();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	void send(StreamerPacket streamerPacket){
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ObjectOutputStream os= null;
		try{
		os = new ObjectOutputStream(outputStream);
		os.writeObject(streamerPacket);
		}catch(Exception e){
			e.printStackTrace();
		}
		byte[] data = outputStream.toByteArray();
	
		
		try {
			sock.send(new DatagramPacket(data, data.length, InetAddress.getByName(ip),port));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}


