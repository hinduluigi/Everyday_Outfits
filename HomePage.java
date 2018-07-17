import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
/*
 * Saryuhan, this is the home page and the user can see the outfit for the day, and may also redirect tot eh other pages
 */
public class HomePage extends JFrame implements ActionListener, MouseListener {
	// declarations
	private JLabel logo = new JLabel();
	private JLabel title = new JLabel();
	private JPanel panel = new JPanel();
	private JLabel subTitle = new JLabel("Today's Outfit");
	private JPanel todayFit = new JPanel();
	private JButton schedule = new JButton("Schedule");
	private JButton createOutfit = new JButton("Create Outfit");
	private JButton[] rating = new JButton[2];
	private JButton closet = new JButton("Closet");

	// current outfit
	private JLabel[] pieces = new JLabel[6];
	private Scanner in;
	private int skip;

	// constructor
	public HomePage() {
		CreatingOutfit.setItemIndex(0);
		// setting the frame
		setFrame();
		// add the components
		addCom();
	}

	// setting the frame
	public void setFrame() {
		setSize(1100, 700);
		setVisible(true);
		getContentPane().setBackground(Color.decode("#4484ce"));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(null);
	}

	// add the components
	public void addCom() {
		// this is the top title area
		logo.setIcon(new ImageIcon(new ImageIcon("images/logo.png").getImage().getScaledInstance(85, 85, 0)));
		add(logo);
		logo.setBounds(490, 5, 105, 105);
		logo.addMouseListener(this);

		// setting the title of the application
		title.setText("Everyday Outfits");
		title.setFont(new Font("Arial", Font.BOLD, 54));
		title.setForeground(Color.decode("#f9cf00"));
		add(title);
		title.setBounds(315, 100, 500, 75);

		// setting the panel
		panel.setBackground(Color.decode("#d9d9d9"));
		panel.setLayout(null);
		add(panel);
		panel.setBounds(20, 190, 1045, 455);

		// setting the subtitle
		subTitle.setFont(new Font("Arial", Font.PLAIN, 32));
		panel.add(subTitle);
		subTitle.setBounds(385, 10, 250, 50);
		subTitle.setHorizontalAlignment(SwingConstants.CENTER);
		subTitle.setForeground(Color.decode("#f19f4d"));

		// adding the panel that holds the day's outfit
		panel.add(todayFit);
		todayFit.setBounds(185, 65, 650, 370);
		todayFit.setBackground(Color.decode("#f9cf00"));
		todayFit.setLayout(null);

		// // adding the like and dislike buttons
		// rating[0] = new JButton("Like");
		// rating[1] = new JButton("Dislike");
		// rating[0].setFont(new Font("Arial", Font.BOLD, 18));
		// rating[1].setFont(new Font("Arial", Font.BOLD, 18));
		// rating[0].setBackground(Color.decode("#f19f4d"));
		// rating[1].setBackground(Color.decode("#f19f4d"));
		// todayFit.add(rating[0]);
		// todayFit.add(rating[1]);
		// rating[0].setBounds(225, 300, 100, 50);
		// rating[1].setBounds(330, 300, 100, 50);

		// button to create a new outfit
		createOutfit.setFont(new Font("Arial", Font.BOLD, 14));
		panel.add(createOutfit);
		createOutfit.setBounds(880, 295, 125, 100);
		createOutfit.setBackground(Color.decode("#4484ce"));
		createOutfit.setForeground(Color.decode("#f9cf00"));
		createOutfit.addMouseListener(this);

		// button to see the closet
		closet.setFont(new Font("Arial", Font.BOLD, 20));
		panel.add(closet);
		closet.setBounds(30, 175, 125, 150);
		closet.setBackground(Color.decode("#4484ce"));
		closet.setForeground(Color.decode("#f9cf00"));
		closet.addMouseListener(this);

		// button to see the schedule
		schedule.setFont(new Font("Arial", Font.BOLD, 18));
		panel.add(schedule);
		schedule.setBounds(880, 125, 125, 150);
		schedule.setBackground(Color.decode("#4484ce"));
		schedule.setForeground(Color.decode("#f9cf00"));
		schedule.addMouseListener(this);

		// adding the pieces
		int yVal = 35;
		for (int p = 0; p < 6; p += 3) {
			pieces[p] = new JLabel();
			pieces[p].setOpaque(true);
			pieces[p].setBackground(Color.decode("#f9cf00"));
			todayFit.add(pieces[p]);
			pieces[p].setBounds(90, yVal, 150, 140);

			pieces[p + 1] = new JLabel();
			pieces[p + 1].setOpaque(true);
			pieces[p + 1].setBackground(Color.decode("#f9cf00"));
			todayFit.add(pieces[p + 1]);
			pieces[p + 1].setBounds(250, yVal, 150, 140);

			pieces[p + 2] = new JLabel();
			pieces[p + 2].setOpaque(true);
			pieces[p + 2].setBackground(Color.decode("#f9cf00"));
			todayFit.add(pieces[p + 2]);
			pieces[p + 2].setBounds(410, yVal, 150, 140);

			yVal += 160;
		}
		// assigning images to the boxes
		try {
			in = new Scanner(new File("files/"+Test.getName()+"/Schedule"));
			in.nextLine();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int daysAmount = 0;
		// getting the start date
		if (Test.getDate().equals("Monday")) {
			daysAmount = 0;
		} else if (Test.getDate().equals("Tuesday")) {
			daysAmount = 1;
		} else if (Test.getDate().equals("Wednesday")) {
			daysAmount = 2;
		} else if (Test.getDate().equals("Thursday")) {
			daysAmount = 3;
		} else if (Test.getDate().equals("Friday")) {
			daysAmount = 4;
		} else if (Test.getDate().equals("Saturday")) {
			daysAmount = 5;
		} else if (Test.getDate().equals("Sunday")) {
			daysAmount = 6;
		}

		for (int r = 0; r < daysAmount; r++) {
			in.nextLine();
		}

		skip = Integer.parseInt(in.next());

		for (int a = 0; a < skip; a++) {
			pieces[a].setIcon(new ImageIcon(new ImageIcon(in.next().trim()).getImage().getScaledInstance(150, 140, 0)));
		}
	}

	// displays the day's outfit
	public void displayOutfit() {

	}

	// adjust outfit display format
	public void reformat() {

	}

	public void actionPerformed(ActionEvent e) {
		// Actions for the buttons

	}

	public void mouseClicked(MouseEvent event) {
		// moving around to other pages
		if (event.getSource() == logo) {

		} else if (event.getSource() == schedule) {
			dispose();
			new Schedule();
		} else if (event.getSource() == createOutfit) {
			dispose();
			new CreatingOutfit();
		} else if (event.getSource() == closet) {
			dispose();
			new Closet(0);
		}
	}

	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
}
