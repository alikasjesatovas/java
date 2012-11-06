package mypac;

import java.io.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class FileSplitter extends JFrame {
	JTextField jtfFile = new JTextField();
	JTextField jtfNumberOfPieces = new JTextField();
	JProgressBar jpb = new JProgressBar();
	JTextArea jtaInfo = new JTextArea("Choose File to be splited and specify how many pieces");
	File file;
	
	public FileSplitter() {
		jpb.setStringPainted(true);
		jtaInfo.setWrapStyleWord(true);
		jtaInfo.setLineWrap(true);
		jtaInfo.setEditable(false);
		JScrollPane jscp = new JScrollPane(jtaInfo);
		
		JPanel jpFile = new JPanel(new BorderLayout(2, 2));
		jpFile.add(new JLabel("Enter of Choose a file: "), BorderLayout.WEST);
		jpFile.add(jtfFile);
		JButton jbtBrowse = new JButton("Browse");
		jpFile.add(jbtBrowse, BorderLayout.EAST);
		
		JPanel jpPieces = new JPanel(new BorderLayout(2, 2));
		jpPieces.add(new JLabel("Specify the number of smaller file: "), BorderLayout.WEST);
		jpPieces.add(jtfNumberOfPieces);
		
		JButton jbtStart = new JButton("Start");
		
		this.setLayout(new GridLayout(5, 1, 2, 2));
		this.getContentPane().add(jscp);
		this.getContentPane().add(jpFile);
		this.getContentPane().add(jpPieces);
		this.getContentPane().add(jbtStart);
		this.getContentPane().add(jpb);
		
		jbtBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser();
				if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					file = chooser.getSelectedFile();
					jtfFile.setText(file.getPath());
				}
			}
		});
		jbtStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Thread thread = new Thread(new Runnable() {
					public void run() {
						if(jtfFile.getText().equals("") || jtfNumberOfPieces.getText().equals("")) {
							jtaInfo.setText("Error, not all fields filled");
							return;
						}
						file = new File(jtfFile.getText());
						if(!file.exists()) {
							jtaInfo.setText("Specified file does not exist");
							return;
						}
						if(!isDigits(jtfNumberOfPieces.getText()) || Integer.parseInt(jtfNumberOfPieces.getText()) < 2) {
							jtaInfo.setText("Specified number of smaller files format is incorrect or it must be biger then 2");
							return;
						}
						String name = file.getName();
						int pieces = Integer.parseInt(jtfNumberOfPieces.getText());
						BufferedInputStream input;
						try {
							input = new BufferedInputStream(new FileInputStream(file));
							int percent = input.available() / 100;
							int count = 0;
							long pieceSize;
							pieceSize = (int)Math.ceil(input.available() / pieces);
							String[] names = new String[pieces];
							for(int i = 1; i  <= pieces; i++) {
								names[i - 1] = name + "." + i;
								BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(file + "." + i));
								int b;
								for(int j = 0; j < pieceSize && (b = input.read()) != -1; j++) {
									count++;
									output.write(b);
									if(count % percent == 0) {
										jpb.setValue(count / percent);
									}
								}
								output.close();
							}
							input.close();
							String infoText = ("Job Done! file name has been splited into: ");
							for(int i = 0; i < names.length; i++) {
								if(i == names.length - 1)
									infoText += " and " + names[i] + " into same directory as sourse file";
								else if(i == 0)
									infoText += names[i];
								else
									infoText += ", " + names[i];
							}
							jtaInfo.setText(infoText);
						} 
						catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
				thread.start();
			}
			
		});
		jtfFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!jtfFile.getText().equals("") && !jtfNumberOfPieces.getText().equals("")) {
					file = new File(jtfFile.getText());
					if(!file.exists()) {
						jtaInfo.setText("Specified file does not exist");
						return;
					}
					if(!isDigits(jtfNumberOfPieces.getText()) || Integer.parseInt(jtfNumberOfPieces.getText()) < 2) {
						jtaInfo.setText("Specified number of smaller files format is incorrect or it must be biger then 2");
						return;
					}
					String infoText = "If you split file named " + file.getName() + " into " + jtfNumberOfPieces.getText() + " smaller files, the three smaller files are:";
					for(int i = 1; i <= Integer.parseInt(jtfNumberOfPieces.getText()); i++) {
						if(i == Integer.parseInt(jtfNumberOfPieces.getText()))
							infoText += " and " + file.getName() + "." + i;
						else if(i == 1)
							infoText += " " + file.getName() + "." + i;
						else
							infoText += ", " + file.getName() + "." + i;
					}
					jtaInfo.setText(infoText);
				}
			}
		});
		jtfNumberOfPieces.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if(!jtfFile.getText().equals("") && !jtfNumberOfPieces.getText().equals("")) {
					file = new File(jtfFile.getText());
					if(!file.exists()) {
						jtaInfo.setText("Specified file does not exist");
						return;
					}
					if(!isDigits(jtfNumberOfPieces.getText()) || Integer.parseInt(jtfNumberOfPieces.getText()) < 2) {
						jtaInfo.setText("Specified number of smaller files format is incorrect or it must be biger then 2");
						return;
					}
					String infoText = "If you split file named " + file.getName() + " into " + jtfNumberOfPieces.getText() + " smaller files, the three smaller files are:";
					for(int i = 1; i <= Integer.parseInt(jtfNumberOfPieces.getText()); i++) {
						if(i == Integer.parseInt(jtfNumberOfPieces.getText()))
							infoText += " and " + file.getName() + "." + i;
						else if(i == 1)
							infoText += " " + file.getName() + "." + i;
						else
							infoText += ", " + file.getName() + "." + i;
					}
					jtaInfo.setText(infoText);
				}
			
			}
		});

	}
	
	private boolean isDigits(String s) {
		for(int i = 0; i < s.length(); i++) {
			if(!Character.isDigit(s.charAt(i)))
				return false;
		}
		return true;
	}
	
	public static void main(String[] args) {
		FileSplitter frame = new FileSplitter();
		frame.setTitle("Splitter");
		frame.setSize(350, 210);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
