import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/*
 * Saryuhan, this displays the pieces of clothing in every category and you can browse through the different pieces and categories. 
 */
public class Closet extends JFrame implements ActionListener, MouseListener {

	// declaration
	private JLabel logo = new JLabel();
	private JLabel title = new JLabel();
	private JPanel panel = new JPanel();
	private JButton addPiece = new JButton();
	private JLabel[] visiblePiece = new JLabel[3];
	private JButton move = new JButton();
	private JLabel[] categories = new JLabel[5];
	private String[] catText = { "Shoes", "Bottoms", "Shirts", "Hoodies",
			"Jackets" };
	private String[] paths = new String[3];
	private String[] selectType = new String[3];
	private int fileIndex = 0;
	private int origin;
	private Scanner in;
	String type;

	// constructor
	public Closet(int origin) {
		// setting the frame
		frameSet();

		// setting the component
		setComp();

		// show the shirts
		type = "Shirts";
		display("Shirts");
		this.origin = origin;
	}

	// setting the frame
	public void frameSet() {
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

		// setting the pieces label holding their image
		int xVal = 35;
		for (int x = 0; x < 3; x++) {
			visiblePiece[x] = new JLabel();
			panel.add(visiblePiece[x]);
			visiblePiece[x].setOpaque(true);
			visiblePiece[x].setBackground(Color.decode("#f9cf00"));
			visiblePiece[x].setBounds(xVal, 85, 275, 300);
			visiblePiece[x].addMouseListener(this);
			xVal += 280;
		}

		// button for the new piece additions
		addPiece.setText("Add Piece");
		panel.add(addPiece);
		addPiece.setBounds(460, 400, 125, 40);
		addPiece.setBackground(Color.decode("#f19f4d"));
		addPiece.addActionListener(this);

		// adding the buttons that shift the clothes

		panel.add(move);

		move.setBounds(900, 200, 115, 100);
		move.setFont(new Font("Arial", Font.BOLD, 18));
		move.setText("Next");
		move.setBackground(Color.decode("#f19f4d"));
		move.addActionListener(this);

		// the categories for the clothing pieces
		int xPos = 275;
		for (int k = 0; k < 5; k++) {
			categories[k] = new JLabel(catText[k]);
			panel.add(categories[k]);
			categories[k].setBackground(Color.decode("#f19f4d"));
			categories[k].setBounds(xPos, 20, 85, 50);
			categories[k].setOpaque(true);
			categories[k].setBackground(Color.decode("#f19f4d"));
			categories[k].setHorizontalAlignment(SwingConstants.CENTER);
			categories[k].setBorder(BorderFactory.createLineBorder(Color
					.decode("#f9cf00")));
			categories[k].addMouseListener(this);
			xPos += 90;
		}
		categories[2].setBackground(Color.decode("#4484ce"));

	}

	// showing the pieces
	public void display(String type) {
		try {
			in = new Scanner(new File("files/" + Test.getName() + "/" + type
					+ "Text"));

			// cancels out the previous ones presented
			for (int i = 0; i < fileIndex; i++) {
				if (in.hasNextLine()) {
					in.nextLine();
				}
			}

			// add 3 new pieces
			for (int a = 0; a < 3; a++) {
				if (in.hasNextLine()) {
					String val = in.nextLine();
					visiblePiece[a].setIcon(new ImageIcon(new ImageIcon(val)
							.getImage().getScaledInstance(275, 300, 0)));
					paths[a] = val;
					selectType[a] = type;
					fileIndex++;
				} else {
					// reseting and looping back
					fileIndex = 0;
				}
			}
			if (paths[0].equals("images/clear.png")) {
				display(type);
			}
		} catch (Exception e) {
			System.out.println("Error Man!");
		}
	}

	// Attaining and adding a product to the label
	public void addProduct() {

	}

	// shifting the order
	public void shift() {
		for (int x = 0; x < 3; x++) {
			visiblePiece[x].setIcon(new ImageIcon(new ImageIcon(
					"images/clear.png").getImage().getScaledInstance(275, 300,
					0)));
			paths[x] = "images/clear.png";
			selectType[x] = "none";
		}
		display(type);
	}

	// Adding the piece as a piece in the other page
	public void addPieceForOutfit() {

	}

	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == move) {
			shift();
		} else if (event.getSource() == addPiece) {
			new AddingPiece();
		}
	}

	public void mouseClicked(MouseEvent event) {
		// what to do when certain buttons are clicked (some fo them are the
		// categries)

		if (event.getSource() == logo) {
			dispose();
			new HomePage();
		} else if (event.getSource() == categories[0]) {
			display("shoes");

			for (int x = 0; x < 5; x++) {
				categories[x].setBackground(Color.decode("#f19f4d"));

			}
			fileIndex = 0;
			type = "Shoes";
			display(type);
			categories[0].setBackground(Color.decode("#4484ce"));

		} else if (event.getSource() == categories[1]) {
			display("bottoms");
			for (int x = 0; x < 5; x++) {
				categories[x].setBackground(Color.decode("#f19f4d"));
			}
			fileIndex = 0;
			type = "Bottoms";
			display(type);
			categories[1].setBackground(Color.decode("#4484ce"));
		} else if (event.getSource() == categories[2]) {
			display("shirts");
			for (int x = 0; x < 5; x++) {
				categories[x].setBackground(Color.decode("#f19f4d"));
			}
			fileIndex = 0;
			type = "Shirts";
			display(type);
			categories[2].setBackground(Color.decode("#4484ce"));
		} else if (event.getSource() == categories[3]) {
			display("hoodies");
			for (int x = 0; x < 5; x++) {
				categories[x].setBackground(Color.decode("#f19f4d"));
			}
			fileIndex = 0;
			type = "Hoodies";
			display(type);
			categories[3].setBackground(Color.decode("#4484ce"));
		} else if (event.getSource() == categories[4]) {
			display("jackets");
			for (int x = 0; x < 5; x++) {
				categories[x].setBackground(Color.decode("#f19f4d"));
			}
			fileIndex = 0;
			type = "Jackets";
			display(type);
			categories[4].setBackground(Color.decode("#4484ce"));
		} else if (event.getSource() == visiblePiece[0] && origin == 1) {
			// adds the piece to the outfit
			System.out.println(paths[0]);
			CreatingOutfit.getItems()[CreatingOutfit.getItemIndex()]
					.setIcon(new ImageIcon(new ImageIcon(paths[0]).getImage()
							.getScaledInstance(200, 175, 0)));
			CreatingOutfit.setItemIndex(CreatingOutfit.getItemIndex() + 1);
			CreatingOutfit.getOutfit().add(paths[0]);
			CreatingOutfit.getTypes().add(selectType[0]);
			dispose();
		} else if (event.getSource() == visiblePiece[1] && origin == 1) {
			// adds the piece to the outfit
			System.out.println(paths[1]);
			System.out.println(paths[1]);
			CreatingOutfit.getItems()[CreatingOutfit.getItemIndex()]
					.setIcon(new ImageIcon(new ImageIcon(paths[1]).getImage()
							.getScaledInstance(200, 175, 0)));
			CreatingOutfit.setItemIndex(CreatingOutfit.getItemIndex() + 1);
			CreatingOutfit.getOutfit().add(paths[1]);
			CreatingOutfit.getTypes().add(selectType[1]);

			dispose();
		} else if (event.getSource() == visiblePiece[2] && origin == 1) {

			// adds the piece to the outfit
			System.out.println(paths[2]);
			System.out.println(paths[2]);
			CreatingOutfit.getItems()[CreatingOutfit.getItemIndex()]
					.setIcon(new ImageIcon(new ImageIcon(paths[2]).getImage()
							.getScaledInstance(200, 175, 0)));
			CreatingOutfit.setItemIndex(CreatingOutfit.getItemIndex() + 1);
			CreatingOutfit.getOutfit().add(paths[2]);
			CreatingOutfit.getTypes().add(selectType[2]);

			dispose();
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
}
