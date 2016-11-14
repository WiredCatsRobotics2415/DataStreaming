package main;

import java.lang.annotation.Annotation;
import java.util.ArrayList;

public class Main {

	static ArrayList<String> list = new ArrayList<String>();
	
	//URL FOR WEB SERVER WILL BE HTTP://127.0.0.1:55555/
	//FORMATTING AND SQL DATABASE WILL BE ADDED LATER IF NEEDED 
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		UDPServer server = new UDPServer(9102);
		Thread thread = new Thread(server);
		thread.start();
		WebServer webServer = new WebServer(55555);
		
		Thread thread2 = new Thread(webServer);
		thread2.start();
		
		//TO ADD A NEW UDP STREAM TYPE CREATE A NEW CLASS, HAVE IT EXTEND HANDLER, ADD A CONSTRUCTOR 
    
		//URL FOR WEB SERVER WILL BE HTTP://127.0.0.1:55555/
		
		
	}
	
	static void modList(String add){
		list.add(add);
		while(list.size()>50){
			list.remove(0);
		}
	}
	

}
