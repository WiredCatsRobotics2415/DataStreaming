package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.*;
import java.util.Date;

public class WebServer implements Runnable {
	ServerSocket server;
	int num = 0;

	public WebServer(int port) {
		try {
			server = new ServerSocket(port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		while (true) {
			try { // Thread.sleep(1000);
				Socket socket = server.accept();
				//System.out.println("rc");
				Date today = new Date();
				String httpResponse = today.toString();
				//System.out.println(httpResponse);
				Thread response = new Thread(new thread(socket, httpResponse));
				response.start(); //

			} catch (IOException e) { // TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	class thread implements Runnable {
		Socket socket;
		String response;

		public thread(Socket socket, String response) {
			this.socket = socket;
			this.response = response;
		}

		String getPage(String s) {
			int begin = 0;
			for (int i = s.length() - 1; i >= 0; i--) {
				if (s.charAt(i) == '/') {
					begin = i + 1;
					break;
				}
			}
			return s.substring(begin);
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
				String s;
				while ((s = in.readLine()) != null) {
					if (s.length() > 7) {
						System.out.println(s);
					}
					System.out.println(getPage(s));
					if (s.length() > 7 && s.substring(0, 7).equals("Referer")) {
						System.out.println(getPage(s));
						if (getPage(s).equals("test")) {
							response += "testPage";
							System.out.println(response);
							// break;
						}
					}
					if (s.length() > 7 && s.substring(0, 3).equals("GET")) {
						if (s.length() > 7 && s.substring(5, 9).equals("test")) {
							response += "testPage";
						}
					}

					if (s.isEmpty()) {
						break;
					}
				}

				out.write("HTTP/1.1 200 OK\r\n");

				out.write("Content-Type: text/html\r\n");

				out.write("\r\n");
				out.write("<TITLE>Example</TITLE>");
				out.write("<P>Most Recent List Values as of: " + response + "</P>\n");
				for (int i = Main.list.size() - 1; i >= 0; i--) {
					out.write(Main.list.get(i) + "<br>");
				}
				out.flush();
				out.close();
				in.close();

				socket.close();
				System.out.println("sent");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

}
