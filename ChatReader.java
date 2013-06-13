import java.net.*;
import java.awt.TextArea;
import java.awt.TextField;
import java.io.*;
import java.util.*;

import javax.swing.JTextArea;

public class ChatReader extends Thread {
	public String input;
	private Socket socket;
	private DataInputStream fromServer;
	private DataOutputStream toServer;
	private Scanner keyboard;
	private TextArea textArea;
	private JTextArea textArea_1;
	private String[] tempAddress;
	public ChatReader(Socket socket, DataInputStream fromServer, DataOutputStream toServer, Scanner keyboard, TextArea textArea, JTextArea textArea_1){
		this.socket = socket;
		this.fromServer = fromServer;
		this.toServer = toServer;
		this.keyboard = keyboard;
		this.textArea = textArea;
		this.textArea_1 = textArea_1;
	}
	public void run() {
		while (socket.isConnected()){
			try {
				input = fromServer.readUTF();
				if (input.contains("@protocolInitxxsz")) {
					tempAddress = input.split("@");
					if (!textArea_1.getText().contains(tempAddress[0])) {
						textArea_1.append(tempAddress[0] + "\n");
					}
				}
				else if (input.contains("@protocolInitxxsx")) {
					tempAddress = input.split("@");
					textArea_1.setText(textArea_1.getText().replaceFirst(tempAddress[0], ""));
				}
				else {
				textArea.append(input + "\n");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
