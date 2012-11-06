/*GUI application that lets the user enter a file name in the
text field and press the Enter key to display its binary representation in a text
area. The user can also modify the binary code and save it back to the file.*/

package mypac;

import java.io.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BitEditor extends JFrame { 
	private static final long serialVersionUID = 1L;
	JTextArea jta = new JTextArea();
	JTextField jtfFile = new JTextField();
	File inputFile;
	
	public BitEditor() {
		JPanel jpNorth = new JPanel(new BorderLayout());
		jpNorth.add(new JLabel("Enter a file "), BorderLayout.WEST);
		jpNorth.add(jtfFile);
		
		JScrollPane jscrPane = new JScrollPane(jta);
		jta.setLineWrap(true);
		jta.setWrapStyleWord(true);
		
		JButton jbt = new JButton("Save the bits to the file");
		
		add(jpNorth, BorderLayout.NORTH);
		add(jscrPane);
		add(jbt, BorderLayout.SOUTH);
		
		jtfFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!jtfFile.getText().equals("")) {
					File file = new File(jtfFile.getText());
					if(!file.exists()) {
						inputFile = null;
						jta.setText("File doesn't exist");
					}
					else {
						inputFile = file;
						try {
							BufferedInputStream input = new BufferedInputStream(new FileInputStream(file));
							int b;
							String s = "";
							while((b = input.read()) != -1) {
								s += getBits(b);
							}
							jta.setText(s);
							input.close();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		});
		jbt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(inputFile == null) {
					jta.setText("File doesn't exist");
				}
				else {
					BitOutputStream output = new BitOutputStream(inputFile);
					output.writeBit(jta.getText());
					try {
						output.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		jta.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent arg0) {
				if(arg0.getKeyChar() < '0' || arg0.getKeyChar() > '1')
					arg0.setKeyChar((char)0);
			}
		});
	}
	public static void main(String[] args) {
		JFrame frame = new BitEditor();
		frame.setTitle("Bit Editor");
		frame.setSize(500, 250);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public static String getBits(int value) {
		int mask = 1;
		String s = "";
		for(int i = 0; i <= 7; i++) {
			int temp = value >> i;
			int bit = mask & temp;
			s = bit + s; 
		}
		return s;
	}
}
