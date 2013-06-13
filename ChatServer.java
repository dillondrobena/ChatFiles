import java.net.*;
import java.io.*;
import java.util.*;

public class ChatServer {
	public ChatServer server;
	public int count = 0;
	public List<String> userNameList = new ArrayList<String>();
	public List<Socket> serverSocketList = new LinkedList<Socket>();
	public List<ServerReader> serverReaderList = new LinkedList<ServerReader>();
	public Hashtable<Integer, DataOutputStream> writeToClient = new Hashtable<Integer, DataOutputStream>();
	public static Socket socket;
	public static ServerReader serverReader;
	public static ServerSocket serverSocket;
	public DataInputStream fromClient;
	public DataOutputStream toClient;
	public static void main(String[] args) throws IOException{
		new ChatServer();
	}
	public ChatServer() throws IOException{
		serverSocket = new ServerSocket(4444);
		server = this;
		while(true){
			System.out.println("Waiting for client...");
			socket = serverSocket.accept();
			System.out.println("Client connected.");
			write("User has connected.", "Server", 0);
			fromClient = new DataInputStream(socket.getInputStream());
			toClient = new DataOutputStream(socket.getOutputStream());
			serverReader = new ServerReader(server, socket, fromClient, count, userNameList);
			serverSocketList.add(socket);
			writeToClient.put(count, toClient);
			serverReaderList.add(serverReader);
			Thread z = new Thread(serverReader);
			z.start();
			count++;
		}
	}
	public void adjustThreadCount(int index) {
		writeToClient.remove(index);
	}
	public void write(String input, String name, int protocol) throws IOException{
		if (protocol == 1){
			userNameList.add(name);
			for (DataOutputStream i : writeToClient.values()){
				for (String s : userNameList) {
					i.writeUTF(s + "@protocolInitxxsz");
				}
			}
		}
		if (protocol == 2){
			for (DataOutputStream i : writeToClient.values()){
				i.writeUTF(name + "@protocolInitxxsx");
			}
		}
		if (protocol == 0) {
			for (DataOutputStream i : writeToClient.values()){
				i.writeUTF(name + ": " + input);
			}
		}
	}
}
