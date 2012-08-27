/*This GUI program lets the user enter the loan amount, number
of years, and interest rate and displays the amortization schedule for the loan.
The program can work as stand alone or as JApplet.*/

package mypackage;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import java.text.*;
import java.awt.*;
import java.awt.event.*;

public class LoanSchedule extends JApplet {
	private static final long serialVersionUID = 1L;
	JTextField jtfloan = new JTextField("107000");
	JTextField jtfYears = new JTextField("15");
	JTextField jtfInterest = new JTextField("6.75");
	JTextArea jta = new JTextArea();

	public LoanSchedule() {
		JPanel jpNorthLeft = new JPanel(new GridLayout(3, 2));
		jpNorthLeft.setBorder(new TitledBorder(
				"Enter Loan loan, Number of Years, and Annual Interest Rate"));
		jpNorthLeft.add(new JLabel("Loan loan"));
		jpNorthLeft.add(jtfloan);
		jpNorthLeft.add(new JLabel("Number of Years"));
		jpNorthLeft.add(jtfYears);
		jpNorthLeft.add(new JLabel("Annual Interest Rate"));
		jpNorthLeft.add(jtfInterest);

		JButton jbtDisplay = new JButton("Display Loan Schedule");

		JPanel jpNorth = new JPanel(new BorderLayout());
		jpNorth.add(jpNorthLeft);
		jpNorth.add(jbtDisplay, BorderLayout.EAST);

		add(new JScrollPane(jta));
		add(jpNorth, BorderLayout.NORTH);

		jbtDisplay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				jta.setText("");
				try {
					double loan = Double.parseDouble(jtfloan.getText());
					double years = Double.parseDouble(jtfYears.getText());
					double rate = new Double(jtfInterest.getText()).doubleValue() / 1200;
					double monthlyPayment = loan * rate	/ (1 - (Math.pow(1 / (1 + rate), years * 12)));
					double total = monthlyPayment * years * 12;
					NumberFormat nf = NumberFormat.getCurrencyInstance();
					jta.append("Mothly payment: " + nf.format(monthlyPayment)
							+ "\n");
					jta.append("Total payment: " + nf.format(total) + "\n\n");	
					jta.append("Payment#\tInterest\tPrincipal\tBalance\n");
					for(int i = 1; i <= years * 12; i++) {
						double interest = total * rate;
						double principal = monthlyPayment - interest;
						total = total - monthlyPayment;
						jta.append(i + "\t" + nf.format(interest) + "\t" + nf.format(principal) + "\t" + nf.format(total) + "\n");
					}
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Incorrect input!");
				}

			}
		});
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setSize(600, 350);
		frame.setLocationRelativeTo(null);
		frame.add(new LoanSchedule());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
