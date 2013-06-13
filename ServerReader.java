import java.net.*;
import java.io.*;
import java.util.*;

public class ServerReader extends Thread {
	public String input;
	private int userID = 0;
	private ChatServer server;
	private Socket socket;
	private DataInputStream fromClient;
	private String userName;
	private String[] tempAddress;
	
	public ServerReader(ChatServer server, Socket socket, DataInputStream fromClient, int userID, List userNameList){
		this.server = server;
		this.socket = socket;
		this.fromClient = fromClient;
		this.userID = userID;
	}
	public void run() {
		int count = 0;
		while (socket.isConnected()){
			try {
				input = fromClient.readUTF();
				if (count == 0){
					userName = input;
					server.write(null, input, 1);
					count = 1;
				}
				else {
					tempAddress = input.split("@protocolxxsy");
					server.write(tempAddress[0], tempAddress[1], 0);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				server.adjustThreadCount(userID);
				try {
					server.write(null, userName, 2);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				break;
			}
		}
	}

}
