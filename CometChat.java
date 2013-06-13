

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.TextArea;
import java.awt.TextField;
import javax.swing.SpringLayout;
import java.awt.Color;
import java.awt.Button;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.swing.JProgressBar;
import javax.swing.JTextArea;

public class CometChat extends JFrame {

	private JPanel contentPane;
	public Button button;
	public String userName;
	public TextField textField;
	public TextArea textArea;
	public JTextArea textArea_1;
	public DataInputStream fromServer;
	public DataOutputStream toServer;
	public Socket socket;
	public Scanner keyboard = new Scanner(System.in);
	public ChatReader chatReader;
	public ChatWriter chatWriter;

	/**
	 * Launch the application.
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					CometChat frame = new CometChat();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public CometChat() {
		userName = JOptionPane.showInputDialog("Username: ", null);
		setTitle("CometChat");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 668, 434);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		SpringLayout sl_contentPane = new SpringLayout();
		contentPane.setLayout(sl_contentPane);
		
		textArea = new TextArea();
		textArea.setBackground(Color.WHITE);
		textArea.setEditable(false);
		sl_contentPane.putConstraint(SpringLayout.NORTH, textArea, 15, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, textArea, 29, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, textArea, 287, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, textArea, 523, SpringLayout.WEST, contentPane);
		contentPane.add(textArea);
		
		textField = new TextField();
		sl_contentPane.putConstraint(SpringLayout.NORTH, textField, 11, SpringLayout.SOUTH, textArea);
		sl_contentPane.putConstraint(SpringLayout.WEST, textField, 39, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, textField, -62, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, textField, -107, SpringLayout.EAST, contentPane);
		contentPane.add(textField);
		
		button = new Button("Send");
		sl_contentPane.putConstraint(SpringLayout.NORTH, button, 298, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, button, 11, SpringLayout.EAST, textField);
		sl_contentPane.putConstraint(SpringLayout.EAST, button, 74, SpringLayout.EAST, textField);
		contentPane.add(button);
		
		Label label = new Label("Users");
		label.setBackground(Color.WHITE);
		sl_contentPane.putConstraint(SpringLayout.WEST, label, 11, SpringLayout.EAST, textArea);
		sl_contentPane.putConstraint(SpringLayout.EAST, label, -6, SpringLayout.EAST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.NORTH, label, 0, SpringLayout.NORTH, textArea);
		contentPane.add(label);
		
		textArea_1 = new JTextArea();
		textArea_1.setEditable(false);
		sl_contentPane.putConstraint(SpringLayout.NORTH, textArea_1, 6, SpringLayout.SOUTH, label);
		sl_contentPane.putConstraint(SpringLayout.WEST, textArea_1, 9, SpringLayout.EAST, textArea);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, textArea_1, -5, SpringLayout.SOUTH, textArea);
		sl_contentPane.putConstraint(SpringLayout.EAST, textArea_1, 0, SpringLayout.EAST, label);
		contentPane.add(textArea_1);
		
		JProgressBar progressBar = new JProgressBar();
		sl_contentPane.putConstraint(SpringLayout.WEST, progressBar, 32, SpringLayout.WEST, contentPane);
		contentPane.add(progressBar);
		
		try {
			textArea.append("Connecting to host...\n");
			socket = new Socket("localhost", 4444);
			textArea.append("Connection successful.\n");
			fromServer = new DataInputStream(socket.getInputStream());
			toServer = new DataOutputStream(socket.getOutputStream());
			chatReader = new ChatReader(socket, fromServer, toServer, keyboard, textArea, textArea_1);
			Thread x = new Thread(chatReader);
			x.start();
			chatWriter = new ChatWriter(socket, fromServer, toServer, keyboard, button, textField, userName);
			Thread y = new Thread(chatWriter);
			y.start();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
