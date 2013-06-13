import java.awt.Button;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.util.*;


public class ChatWriter extends Thread implements ActionListener{
	private Socket socket;
	private DataInputStream fromServer;
	private DataOutputStream toServer;
	private Scanner keyboard;
	private Button button;
	private TextField textField;
	private String userName;
	
	public ChatWriter(Socket socket, DataInputStream fromServer, DataOutputStream toServer, Scanner keyboard, Button button, TextField textField, String userName){
		this.socket = socket;
		this.fromServer = fromServer;
		this.toServer = toServer;
		this.keyboard = keyboard;
		this.textField = textField;
		this.userName = userName;
		button.addActionListener(this);
		textField.addActionListener(this);
	}
	public void run() {
		try {
			toServer.writeUTF(userName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		try {
			toServer.writeUTF(textField.getText() + "@protocolxxsy" + userName);
			textField.setText("");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}
