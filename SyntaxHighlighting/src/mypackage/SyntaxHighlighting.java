/*This program converts a Java file into an HTML
file. In the HTML file, the keywords, comments, and literals are displayed in
bold navy, green, and blue, respectively. Use the command line to pass a Java
file and an HTML file. For example, the following command
java SyntaxHighlighting Welcome.java Welcome.HTML
converts ComputeArea.java into ComputeArea.HTML.*/

package mypackage;

import java.io.*;
import java.util.*;

public class SyntaxHighlighting {
	public static void main(String[] args) throws Exception{
		String startString = "<html><head><title>java to html</title></head><body><p>";
		String endString = "</p></body></html>";
		
		File inputFile = new File(args[0]);
		Scanner input = new Scanner(inputFile);
		
		LinkedList<String> list = new LinkedList<String>();
		while(input.hasNext()) {
			list.add(input.nextLine() + "</br>");
		}
		input.close();
		
		
		setLiterals(list);
		putTabs(list);
		setkeyWords(list);
		setComments(list);
		
		list.addFirst(startString);
		list.addLast(endString);
		PrintWriter output = new PrintWriter(new File(args[1]));
		String outputString = "";
		for(String s: list) 
			outputString += s;
		output.append(outputString);
		output.close();
	}
	public static void putTabs(LinkedList<String> list) {
		for(int i = 0; i < list.size(); i++) {
			String s = list.get(i);
			String s2 = s.replace("" + (char)9, "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
			list.set(i, s2);
		}
	}

	public static void setComments(LinkedList<String> list) {
		for(int k = 0; k < list.size(); k++) {
			String s = list.get(k);
			String s2 = "";
			for(int i = 0; i < s.length() -1; i++) {
				if(s.substring(i, i + 2).equals("//")) {
					s2 = s.replace("//", "<font color = \"#01DF01\">//");
					s2 += "</font>";
					list.set(k, s2);
					break;
				}
			}
		}
	}
	
	public static void setkeyWords(LinkedList<String> list) {
		for(int i = 0; i < list.size(); i++) {
			String line = list.get(i);
			String[] words = line.split("( |//.*</br>|\".*\"|\\.|</br>|&nbsp;)");
			for(int j = 0; j < words.length; j++) {
				String s = words[j];
				if(s.length() > 1 && containsNotLetters(s))
					continue;
				s = s.replace("abstract", "<b><font color = \"#8904B1\">abstract</font></b>");
				s = s.replace("assert", "<b><font color = \"#8904B1\">assert</font></b>");
				s = s.replace("boolean", "<b><font color = \"#8904B1\">boolean</font></b>");
				s = s.replace("break", "<b><font color = \"#8904B1\">break</font></b>");
				s = s.replace("byte", "<b><font color = \"#8904B1\">byte</font></b>");
				s = s.replace("case", "<b><font color = \"#8904B1\">case</font></b>");
				s = s.replace("catch", "<b><font color = \"#8904B1\">catch</font></b>");
				s = s.replace("char", "<b><font color = \"#8904B1\">char</font></b>");
				s = s.replace("class", "<b><font color = \"#8904B1\">class</font></b>");
				s = s.replace("const", "<b><font color = \"#8904B1\">const</font></b>");
				s = s.replace("continue", "<b><font color = \"#8904B1\">continue</font></b>");
				s = s.replace("default", "<b><font color = \"#8904B1\">default</font></b>");
				s = s.replace("do", "<b><font color = \"#8904B1\">do</font></b>");
				s = s.replace("double", "<b><font color = \"#8904B1\">double</font></b>");
				s = s.replace("else", "<b><font color = \"#8904B1\">else</font></b>");
				s = s.replace("enum", "<b><font color = \"#8904B1\">enum</font></b>");
				s = s.replace("extends", "<b><font color = \"#8904B1\">extends</font></b>");
				s = s.replace("final", "<b><font color = \"#8904B1\">final</font></b>");
				s = s.replace("finally", "<b><font color = \"#8904B1\">finally</font></b>");
				s = s.replace("float", "<b><font color = \"#8904B1\">float</font></b>");
				s = s.replace("for", "<b><font color = \"#8904B1\">for</font></b>");
				s = s.replace("if", "<b><font color = \"#8904B1\">if</font></b>");
				s = s.replace("goto", "<b><font color = \"#8904B1\">goto</font></b>");
				s = s.replace("implement", "<b><font color = \"#8904B1\">implement</font></b>");
				s = s.replace("import", "<b><font color = \"#8904B1\">import</font></b>");
				s = s.replace("instanceof", "<b><font color = \"#8904B1\">instance of</font></b>");
				s = s.replace("int", "<b><font color = \"#8904B1\">int</font></b>");
				s = s.replace("interface", "<b><font color = \"#8904B1\">interface</font></b>");
				s = s.replace("long", "<b><font color = \"#8904B1\">long</font></b>");
				s = s.replace("native", "<b><font color = \"#8904B1\">native</font></b>");
				s = s.replace("new", "<b><font color = \"#8904B1\">new</font></b>");
				s = s.replace("package", "<b><font color = \"#8904B1\">package</font></b>");
				s = s.replace("private", "<b><font color = \"#8904B1\">private</font></b>");
				s = s.replace("protected", "<b><font color = \"#8904B1\">protected</font></b>");
				s = s.replace("public", "<b><font color = \"#8904B1\">public</font></b>");
				s = s.replace("return", "<b><font color = \"#8904B1\">return</font></b>");
				s = s.replace("short", "<b><font color = \"#8904B1\">short</font></b>");
				s = s.replace("static", "<b><font color = \"#8904B1\">static</font></b>");
				s = s.replace("strictfp", "<b><font color = \"#8904B1\">stricktfp</font></b>");
				s = s.replace("super", "<b><font color = \"#8904B1\">super</font></b>");
				s = s.replace("switch", "<b><font color = \"#8904B1\">switch</font></b>");
				s = s.replace("synchronized", "<b><font color = \"#8904B1\">synchronized</font></b>");
				s = s.replace("this", "<b><font color = \"#8904B1\">this</font></b>");
				s = s.replace("throw", "<b><font color = \"#8904B1\">throw</font></b>");
				s = s.replace("throws", "<b><font color = \"#8904B1\">throws</font></b>");
				s = s.replace("transient", "<b><font color = \"#8904B1\">transient</font></b>");
				s = s.replace("try", "<b><font color = \"#8904B1\">try</font></b>");
				s = s.replace("void", "<b><font color = \"#8904B1\">void</font></b>");
				s = s.replace("volatile", "<b><font color = \"#8904B1\">volatile</font></b>");
				s = s.replace("while", "<b><font color = \"#8904B1\">while</font></b>");
				s = s.replace("null", "<b><font color = \"#8904B1\">null</font></b>");
				s = s.replace("true", "<b><font color = \"#8904B1\">true</font></b>");
				s = s.replace("false", "<b><font color = \"#8904B1\">false</font></b>");
				line = line.replace(words[j], s);
			}
			list.set(i, line);
		}		
	}
	public static boolean containsNotLetters(String s) {
		for(int i = 0; i < s.length(); i++) {
			if(!Character.isLetter(s.charAt(i)))
				return true;
		}
		return false;
	}
	
	public static void setLiterals(LinkedList<String> list) {
		for(int k = 0; k < list.size(); k++) {
			String s = list.get(k);
			for(int i = 0; i < s.length() - 1; i++)
				for(int j = i + 2; j < s.length(); j++) {
					String sub = s.substring(i, j);
					if(sub.matches("\".*\"")) {
						s = s.substring(0, i) + "<font color = \"blue\">" + sub + "</font>" + s.substring(j, s.length() );
						i = j + 21;
						j = i + 2;
					}
				}
			list.set(k, s);
		}
	}
}
