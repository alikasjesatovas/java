package mypac;

import javax.swing.*;
import javax.swing.tree.*;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class FileExplorer extends JApplet {
	private static final long serialVersionUID = 1L;
	private JTextField jtf = new JTextField("c:\\book");
	private JTree tree;
	
	public FileExplorer() {
		JButton jbtSet = new JButton("Set Root Dir");
		
		File rootFile = new File(jtf.getText());
		DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(rootFile.getName());
		setChilds(rootNode, rootFile);
		tree = new JTree(rootNode);
		
		add(new JScrollPane(tree));
		
		JPanel jpNorth = new JPanel(new BorderLayout());
		jpNorth.add(jtf);
		jpNorth.add(jbtSet, BorderLayout.EAST);
		
		add(jpNorth, BorderLayout.NORTH);
		
		jbtSet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File rootFile = new File(jtf.getText());
				if(!rootFile.exists()) {
					JOptionPane.showMessageDialog(null, "File or Directory does not Exist", "Error message", JOptionPane.ERROR_MESSAGE);
					return;
				}
				DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(rootFile.getName());
				setChilds(rootNode, rootFile);
				((DefaultTreeModel)tree.getModel()).setRoot(rootNode);
				((DefaultTreeModel)tree.getModel()).reload();
			}
		});
	}
	
	private void setChilds(DefaultMutableTreeNode parent, File file) {
		if(file.isDirectory()) {
			File[] files = file.listFiles();
			for(int i = 0; i < files.length; i++) {
				DefaultMutableTreeNode child = new DefaultMutableTreeNode(files[i].getName());
				parent.add(child);
				setChilds(child, files[i]);
			}
		}
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("File Explorer");
		JApplet applet = new FileExplorer();
		frame.add(applet);
		applet.init();
		applet.start();
		frame.setSize(600, 600);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
