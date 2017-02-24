package main;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.annotation.Annotation;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import org.usfirst.*;

import org.usfirst.frc.team2415.robot.StreamerPacket;

public class UDPServer implements Runnable {
	List<Class<? extends Handler>> classes = new ArrayList<Class<? extends Handler>>();
	DatagramSocket server;

	public UDPServer(int port) {
		classes.add(ShooterHandler.class);
		classes.add(FeederHandler.class);
		try {
			server = new DatagramSocket(port);
			server.setReceiveBufferSize(1);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void run() {
		while (true) {
			byte[] buf = new byte[1024];
			DatagramPacket packet = new DatagramPacket(buf, buf.length);
			try {
				server.receive(packet);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			byte[] data = packet.getData();
			try {
				ByteArrayInputStream in = new ByteArrayInputStream(data);
				ObjectInputStream is = new ObjectInputStream(in);

				StreamerPacket streamerPacket = (StreamerPacket) is.readObject();

				for (int i = 0; i < classes.size(); i++) {
					Annotation annotation = classes.get(i).getAnnotation(name.class);
					name type = (name) annotation;
					if (type.type().equals(streamerPacket.getType())) {
						Handler handler = classes.get(i).newInstance();
						handler.setPacket(streamerPacket);
						
						Thread thread = new Thread(handler);
						thread.start();
						break;
					}
				}
				/*
				 * Main.modList(streamerPacket.toString());
				 * //System.out.println("Student object received = "+student); }
				 * catch (Exception e) { e.printStackTrace(); }
				 */

				// System.out.println(data);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
