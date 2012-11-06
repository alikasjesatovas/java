package mypac;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Server extends JFrame {
	private static final long serialVersionUID = 1L;
	private JTextArea jta = new JTextArea();
	private HashSet<ClientHandlerTo> set = new HashSet<ClientHandlerTo>();
	private final int PORT = 8000;
	
	public Server() {
		JScrollPane jsp = new JScrollPane(jta);
		add(jsp);
		try {
			ServerSocket serverSocket = new ServerSocket(PORT);
			System.out.println("MultiThreadServer started at " + new java.util.Date());
			while(true) {
				Socket socket = serverSocket.accept();
				System.out.println("Connection from " + socket + ".");
				ClientHandlerFrom from = new ClientHandlerFrom(socket.getInputStream());
				ClientHandlerTo to = new ClientHandlerTo(socket.getOutputStream());
				set.add(to);
				from.start();
				to.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	class ClientHandlerTo extends Thread {
		private boolean got;
		boolean run = true;
		private String message;
		ObjectOutputStream toClient;
		
		public ClientHandlerTo(OutputStream outputStream) {
			try {
				toClient = new ObjectOutputStream(outputStream);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		public void sendMessage(String message) {
			if(!run)
				return;
			this.message = message;
			got = true;
		}
		
		public void run() {
			while(run) {
				if(!got) {
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					continue;
				}
				try {
					toClient.writeObject(message);
					toClient.flush();
				} catch (IOException e) {
					System.out.println("Client left. (ClientHandlerTo)");
					run = false;
				}
				got = false;
			}
		}
	}
	
	class ClientHandlerFrom extends Thread {
		ObjectInputStream fromClient;
		private boolean run = true;
		
		public ClientHandlerFrom(InputStream inputStream) {
			try {
				this.fromClient = new ObjectInputStream(inputStream);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		public void run() {
			while(run) {
				try {
					String message = (String)fromClient.readObject();
					sendToAll(message);
					System.out.println(message);
				} catch (Exception e) {
					System.out.println("Client left. (ClientHandlerFrom)");
					run = false;
				}
			}
		}
	}
	
	
	public synchronized void sendToAll(String message) {
		Iterator<ClientHandlerTo> iter = set.iterator();
		while(iter.hasNext()) {
			ClientHandlerTo to = iter.next();
			if(!to.run) {
				iter.remove();
			}
			to.sendMessage(message);
		}
	}
	
	public static void main(String[] args) {
		JFrame frame = new Server();
		frame.setTitle("E30_13");
		frame.setSize(400, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
