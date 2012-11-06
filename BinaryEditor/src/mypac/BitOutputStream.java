package mypac;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class BitOutputStream {
	BufferedOutputStream output;
	int b = 0;
	int count = 0;
	BitOutputStream(File file) {
		try {
			output = new BufferedOutputStream(new FileOutputStream(file));
		} catch (FileNotFoundException e) {
			System.out.println("File does not exist. Java will create new file");
		}
	}
	
	public void writeBit(char bit) {
		if(bit < 48 || bit > 49) {
			System.out.println("input invalid");
		}
		else {
			count++;
			int val = bit - 48;
			b = b << 1;
			b += val;
			if(count == 8) {
				try {
					output.write(b);
				} catch (IOException e) {
					e.printStackTrace();
				}

				count = 0;
				b = 0;
			}
		}
	}
	
	public void writeBit(String bit) {
		for(int i = 0; i < bit.length(); i++) {
			if(bit.charAt(i) < 48 || bit.charAt(i) > 49) {
				System.out.println("Invalid input");
				return;
			}
		}
		for(int i = 0; i < bit.length(); i++) {
			count++;
			int val = bit.charAt(i) - 48;
			b = b << 1;
			b += val;
			if(count == 8) {
				try {
					output.write(b);
				} catch (IOException e) {
					e.printStackTrace();
				}

				count = 0;
				b = 0;
			}
		}
	}
	public void close() throws IOException {
		if(count > 0) {
			int ZeroFill = 8 - count;
			for(int i = 0; i < ZeroFill; i++) {
				b = b << 1;
				b += 0;
			}
			output.write(b);
			output.close();
		}
		else
			output.close();
	}	
}
