package mypac;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Client extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JTextField jtfName = new JTextField("Enter your name and press enter to join chat");
	JTextField jtfEnterText = new JTextField();
	JTextArea jta = new JTextArea();
	ObjectInputStream fromServer;
	ObjectOutputStream toServer;
	private boolean isFirstMessage = true;
	private String name;
	
	public Client() {
		JPanel jpTop = new JPanel(new BorderLayout());
		jpTop.add(new JLabel("Name "), BorderLayout.WEST);
		jpTop.add(jtfName);
		
		JPanel jpMiddle = new JPanel(new BorderLayout());
		jpMiddle.add(new JLabel("Enter text "), BorderLayout.WEST);
		jpMiddle.add(jtfEnterText);
		
		JPanel jpNorth = new  JPanel(new BorderLayout());
		jpNorth.add(jpTop, BorderLayout.NORTH);
		jpNorth.add(jpMiddle, BorderLayout.SOUTH);
		
		JScrollPane jsp = new JScrollPane(jta);
		jta.setEditable(false);
		jtfEnterText.setEnabled(false);
		jtfName.select(0, jtfName.getText().length());
		
		add(jpNorth, BorderLayout.NORTH);
		add(jsp);
		
		Thread thread = new Thread(new Runnable() {
			public void run() {
				try {
					Socket socket = new Socket("localhost", 8000);
					toServer = new ObjectOutputStream(socket.getOutputStream());
					fromServer = new ObjectInputStream(socket.getInputStream());
					jta.append("**connection established**\n");
					while(true) {
						String message = (String)fromServer.readObject();
						jta.append(message + "\n");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
		thread.start();
		
		jtfName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				jtfEnterText.setEnabled(true);
				name = jtfName.getText();
				if(name.equals("Enter your name and press enter to join chat")) {
					name = "Anoym";
					jtfName.setText(name);
				}
				jtfName.setEnabled(false);
			}
		});
		
		jtfEnterText.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {	
				if(toServer == null) {
					JOptionPane.showMessageDialog(null, "Not Connected to server");
					return;
				}
				String s = name + ": " + jtfEnterText.getText();
				if(isFirstMessage) {
					isFirstMessage = false;
					s = "<< " + name + " has joined chat room >>\n" + s; 
				}
				try {
					toServer.writeObject(s);
					toServer.flush();
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
			}
		});
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent arg0) {
				if(toServer == null)
					return;
				try {
					toServer.writeObject("<<" + name + " has left chat>>");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static void main(String[] args) {
		JFrame frame = new Client();
		frame.setTitle("E30_13Client");
		frame.setSize(400, 300);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
