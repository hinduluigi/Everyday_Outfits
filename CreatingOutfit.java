import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;

/*
 * Saryuhan, this class allows for the user to go through their pieces and create outfits of their own and add it to their collection
 */
public class CreatingOutfit extends JFrame implements ActionListener,
		MouseListener {
	// declaration
	private JLabel logo = new JLabel();
	private JLabel title = new JLabel();
	private JPanel panel = new JPanel();
	private JLabel subTitle = new JLabel("Create Outfit");
	private JButton confirm = new JButton("Confirm");
	private JButton addItem = new JButton("Add Piece");
	private static JLabel[] items = new JLabel[6];
	private static int itemIndex = 0;
	private String weather;
	private JPanel extraPanel = new JPanel();
	private static String[] text = { "HOT", "MEDIUM", "COLD" };
	private JRadioButton[] options = new JRadioButton[3];
	private static ArrayList<String> outfit = new ArrayList<String>();
	private static ArrayList<String> types = new ArrayList<String>();
	private boolean first = true;

	// constructor
	public CreatingOutfit() {

		// getAdditionalInfo();
		// setting the frame
		setFrame();

		// setting the component
		setComp();
	}

	// setting the frame
	public void setFrame() {
		setSize(1100, 700);
		setVisible(true);
		getContentPane().setBackground(Color.decode("#4484ce"));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(null);
	}

	// setting the components
	public void setComp() {
		// this is the top title area
		logo.setIcon(new ImageIcon(new ImageIcon("images/logo.png").getImage()
				.getScaledInstance(85, 85, 0)));
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

		// the confirm and adding buttons
		confirm.setFont(new Font("Arial", Font.PLAIN, 18));
		panel.add(confirm);
		confirm.setBounds(820, 265, 120, 95);
		confirm.setBackground(Color.decode("#f9cf00"));
		confirm.addActionListener(this);

		// the add item button
		addItem.setFont(new Font("Arial", Font.PLAIN, 15));
		panel.add(addItem);
		addItem.setBounds(820, 150, 120, 95);
		addItem.setBackground(Color.decode("#f9cf00"));
		addItem.addActionListener(this);

		// setting the possible items
		int xVal = 75;
		int yVal = 70;
		for (int i = 0; i < 6; i++) {
			items[i] = new JLabel("EMPTY");
			items[i].setOpaque(true);
			items[i].setHorizontalAlignment(SwingConstants.CENTER);
			items[i].setBackground(Color.decode("#f19f4d"));
			items[i].setBorder(BorderFactory.createLineBorder(Color.black));
			panel.add(items[i]);
			items[i].setBounds(xVal, yVal, 200, 175);
			xVal += 225;
			if (i == 2) {
				yVal += 190;
				xVal = 75;
			}

		}
	}

	// Selecting the outfit
	public void selectingPiece() {

	}

	// Reformatting the label formation
	public void reformatLabels() {

	}

	// Adding to the database
	public void addOutfit() {

	}

	// Getting some information about the custom outfit
	public void getAdditionalInfo() {
		// obtaining the weather for the application
		for (int x = 0; x < 3; x++) {
			options[x] = new JRadioButton(text[x]);
			extraPanel.add(options[x]);
		}

		JOptionPane.showMessageDialog(null, extraPanel);
		if (options[0].isSelected()) {
			weather = text[0];
		} else if (options[1].isSelected()) {
			weather = text[1];
		} else if (options[2].isSelected()) {
			weather = text[2];
		}
	}

	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == addItem) {
			// getting the person to select a piece from the closet
			if (itemIndex < 6) {
				new Closet(1);
			}

		} else if (event.getSource() == confirm) {
			// get some of the additional informations
			getAdditionalInfo();

			File f = new File("files/" + Test.getName() + "/Outfits");
			if (f.exists()) {
				first = false;
			}

			// write the outfit to the text file
			try {
				FileWriter fw = new FileWriter("files/" + Test.getName()
						+ "/Outfits", true);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter pw = new PrintWriter(bw);

				if (first) {
					pw.print("Outfits");
					System.out.println("OPOPOPOP");
				}

				pw.print("\n");
				pw.print(weather + ", " + itemIndex + ", ");
				for (int x = 0; x < outfit.size(); x++) {

					if (x + 1 == outfit.size()) {
						pw.print(types.get(x) + ", ");
						pw.print(outfit.get(x));
					} else {
						pw.print(types.get(x) + ", ");
						pw.print(outfit.get(x) + ", ");
					}

				}
				pw.flush();
				pw.close();
			} catch (Exception e) {

			}
			outfit.clear();
			types.clear();
			dispose();
			new HomePage();
			JOptionPane.showMessageDialog(new JFrame(), "Outfit added!");

		}
	}

	public void mouseClicked(MouseEvent event) {
		if (event.getSource() == logo) {
			dispose();
			new HomePage();
			itemIndex = 0;
			outfit.clear();
			types.clear();
		}
	}

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	// getters and setters
	public static JLabel[] getItems() {
		return items;
	}

	public void setItems(JLabel[] items) {
		this.items = items;
	}

	public static int getItemIndex() {
		return itemIndex;
	}

	public static void setItemIndex(int input) {
		itemIndex = input;
	}

	public static ArrayList<String> getOutfit() {
		return outfit;
	}

	public static void setOutfit(ArrayList<String> outfit) {
		CreatingOutfit.outfit = outfit;
	}

	public static ArrayList<String> getTypes() {
		return types;
	}

	public static void setTypes(ArrayList<String> types) {
		CreatingOutfit.types = types;
	}

}
