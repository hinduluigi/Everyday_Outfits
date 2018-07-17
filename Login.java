import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
/*
 * Saryuhan, allows for the person to login or sign-up
 */
public class Login extends JFrame implements ActionListener {

	// Declarations
	private JButton login = new JButton();
	private JButton signUp = new JButton();
	private JLabel title = new JLabel();
	private JLabel logo = new JLabel();
	private JButton status = new JButton("not logged in");

	// for the input of the user-name and password
	private JTextField username = new JTextField();
	private JTextField password = new JTextField();
	private String name, passwd, tempName, tempPasswd;
	private Scanner in;
	private boolean playerNotVerified, loggedIn;
	private PrintWriter prwr;

	Object[] input = { "Username: ", username, "Password: ", password };

	// constructor
	public Login() {
		// setting up the frame
		frameSet();
		// Adding the components
		addComp();
	}

	// setting the frame up
	public void frameSet() {
		setSize(1100, 700);
		setVisible(true);
		getContentPane().setBackground(Color.decode("#4484ce"));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(null);
	}

	// create a file for the player
	public void createPlayerFile() {
		String name = Test.getName();
	}

	// Adding the components
	public void addComp() {
		// logo settings
		logo.setIcon(new ImageIcon(new ImageIcon("images/logo.png").getImage().getScaledInstance(105, 105, 0)));
		add(logo);
		logo.setBounds(498, 25, 105, 105);

		// setting the title of the application
		title.setText("Everyday Outfits");
		title.setFont(new Font("Arial", Font.BOLD, 54));
		title.setForeground(Color.decode("#f9cf00"));
		add(title);
		title.setBounds(350, 150, 500, 75);

		// setting the sign-up button
		signUp.addActionListener(this);
		signUp.setText("Sign-up");
		signUp.setFont(new Font("Arial", Font.PLAIN, 38));
		signUp.setForeground(Color.decode("#f19f4d"));
		signUp.setBackground(Color.decode("#d9d9d9"));
		add(signUp);
		signUp.setBounds(450, 265, 225, 100);

		// setting the login button
		login.addActionListener(this);
		login.setText("Login");
		login.setFont(new Font("Arial", Font.PLAIN, 38));
		login.setForeground(Color.decode("#f19f4d"));
		login.setBackground(Color.decode("#d9d9d9"));
		add(login);
		login.setBounds(450, 370, 225, 100);

		// setting the status button
		status.setText("not logged in");
		status.setFont(new Font("Arial", Font.PLAIN, 24));
		status.setForeground(Color.red);
		status.setBackground(Color.decode("#d9d9d9"));
		add(status);
		status.setBounds(450, 475, 225, 100);

	}

	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == login) {
			// attain the user-name and password
			JOptionPane.showConfirmDialog(null, input, "Enter the informations", JOptionPane.OK_CANCEL_OPTION);

			loggedIn = false;

			name = username.getText();
			passwd = password.getText();

			password.setText("");
			username.setText("");

			// verify that the info inserted is correct
			try {
				// setting the scanner
				in = new Scanner(new File("files/loginData"));
				in.useDelimiter("[,\n]");

				// loop while there is another line
				while (in.hasNextLine()) {
					// getting the existing user value
					tempName = in.next();
					tempPasswd = in.next();

					// checking if the user's name is already taken
					if (name.trim().equals(tempName.trim()) && passwd.trim().equals(tempPasswd.trim())) {
						System.out.println("in");
						loggedIn = true;
						Test.setName(name.trim());
						createPlayerFile();
						dispose(); 
						//new UserPreference();
						new HomePage();

					} else {
						System.out.println("nope");
					}
				}

				if (!loggedIn) {
					JOptionPane.showMessageDialog(null, "Incorrect entries");
				}
				in.close();
			} catch (Exception e) {
				System.out.println("BRUH");
			}
			
			// resetting the value for the new schedule
			if (Test.getDate().equals("Monday")) {
				try {
					prwr = new PrintWriter(new File("files/" + Test.getName() + "/NeedForNewSchedule"));
					prwr.print("yes");
					prwr.flush();
					prwr.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}

		} else if (event.getSource() == signUp) {
			// attain the user-name and password
			JOptionPane.showConfirmDialog(null, input, "Enter the informations", JOptionPane.OK_CANCEL_OPTION);

			playerNotVerified = false;

			name = username.getText();
			passwd = password.getText();

			password.setText("");
			username.setText("");

			try {

				// scanner set up
				in = new Scanner(new File("files/loginData"));
				in.useDelimiter("[,\n]");

				// going through the text file to check for the key word
				while (in.hasNextLine()) {
					// getting the existing user value
					tempName = in.next();
					in.next();

					// checking if the user's name is already taken
					if (tempName.trim().equals(name.trim())) {
						playerNotVerified = true;
						JOptionPane.showMessageDialog(new JFrame(), "Username already exists!");
						break;
					}
				}
				in.close();

			} catch (Exception e) {

			}

			if (!playerNotVerified && !name.equals("")) {
				// Adding the player information to the information
				try {
					FileWriter fw = new FileWriter("files/loginData", true);
					BufferedWriter bw = new BufferedWriter(fw);
					PrintWriter pw = new PrintWriter(bw);

					// this is the document setup
					pw.print("\n");
					pw.print(name + "," + passwd);
					pw.flush();
					pw.close();

					// creating new files for the new user
					new File("files/" + name.trim()).mkdirs();
					new File("files/" + name.trim() + "/Bottoms").mkdirs();
					new File("files/" + name.trim() + "/Hoodies").mkdirs();
					new File("files/" + name.trim() + "/Shirts").mkdirs();
					new File("files/" + name.trim() + "/Shoes").mkdirs();
					new File("files/" + name.trim() + "/Jackets").mkdirs();

					// telling the user that the player was added
					JOptionPane.showMessageDialog(null, "User was sucessfully added");
				} catch (Exception e) {
					System.out.println("ERROR");
				}

				playerNotVerified = false;
			} else {
				JOptionPane.showMessageDialog(null, "Invalid values");
			}

			// verify that the info inserted is correct

		}
	}
}
