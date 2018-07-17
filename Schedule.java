import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
/*
 * Saryuhan, creates the weekly schedule and edisplays it for the user to see.
 */
public class Schedule extends JFrame implements MouseListener {

	// declarations
	private JLabel logo = new JLabel();
	private JLabel title = new JLabel();
	private JPanel panel = new JPanel();
	private JLabel subTitle = new JLabel("Schedule");

	private JLabel[] daysLabel = new JLabel[7];
	private String[] days = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" };
	private JPanel[] fit = new JPanel[7];
	private JLabel[][] pieces = new JLabel[7][6];
	private ArrayList<ArrayList<Clothing>> fits = new ArrayList<ArrayList<Clothing>>();
	private ArrayList<ArrayList<Clothing>> chosen = new ArrayList<ArrayList<Clothing>>();

	private ArrayList<Integer> amounts = new ArrayList<Integer>();
	private ArrayList<String> holder = new ArrayList<String>();
	private ArrayList<Clothing> tmp = new ArrayList<Clothing>();

	private Scanner in;
	private Scanner input;
	private Scanner scanner;
	private String temp;
	private int numOfElements = 0;
	private int day = -1;
	private PrintWriter prwr;
	private String booleanDicatator;

	public UserPreference uP= new UserPreference();
	// constructor
	public Schedule() {
		// setting the frame
		frameSet();
		// setting the components
		setComp();

		// checking if this is the day for a new schedule
		try {
			scanner = new Scanner(new File("files/" + Test.getName() + "/NeedForNewSchedule"));
			booleanDicatator = scanner.next();
			System.out.println(booleanDicatator);

		} catch (Exception e) {
		}

		// selecting how to get the schedule (and deciding whether we need to to
		// make one or display current one)

		// creating the first schedule
		File check = new File("files/" + Test.getName() + "/Schedule");
		if (!check.exists()) {
			firstSchedule();
			System.out.println("yes sir");
		} // if it is the change day
		else if (Test.getDate().equals("Monday") && booleanDicatator.trim().equals("yes")) {
			newWeek();
			System.out.println("oh yesss");

			// resetting the files
			try {
				prwr = new PrintWriter(new File("files/" + Test.getName() + "/NeedForNewSchedule"));
				prwr.print("no");
				prwr.flush();
				prwr.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		// if not display the other day
		else {
			try {
				in = new Scanner(new File("files/" + Test.getName() + "/Schedule"));
				in.nextLine();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			for (int w = 0; w < 7; w++) {
				day++;

				numOfElements = Integer.parseInt(in.next());

				System.out.println(numOfElements + " pop");

				// when there isn't an outfit for that day
				if (numOfElements == -1 && in.hasNextLine()) {
					continue;
				}

				for (int u = 0; u < numOfElements; u++) {
					pieces[day][u].setIcon(new ImageIcon(
							new ImageIcon(in.next().trim()).getImage().getScaledInstance(65, 2 * (320 / 6) - 25, 0)));
				}
			}

		}
	}

	// first schedule
	public void firstSchedule() {
		// getting outfits for all of the days
		int count;
		int skip = 0;
		int val = 0;
		// getting the start date
		if (Test.getDate().equals("Monday")) {
			val = 0;
		} else if (Test.getDate().equals("Tuesday")) {
			val = 1;
		} else if (Test.getDate().equals("Wednesday")) {
			val = 2;
		} else if (Test.getDate().equals("Thursday")) {
			val = 3;
		} else if (Test.getDate().equals("Friday")) {
			val = 4;
		} else if (Test.getDate().equals("Saturday")) {
			val = 5;
		} else if (Test.getDate().equals("Sunday")) {
			val = 6;
		}
		int x = -1;
		for (int top = val; top < 7; top++) {
			x++;
			boolean valid = true;

			// setting for the reading of a file
			fits.add(new ArrayList<Clothing>());
			try {
				in = new Scanner(new File("files/" + Test.getName() + "/Outfits"));
				in.useDelimiter("[,\n]");
				in.nextLine();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

			// scrapping the weather
			temp = scrappingWeather(top);

			// skipping the outfits already looked at
			for (int z = 0; z < skip; z++) {
				System.out.println(in.nextLine() + " " + skip);

			}
			// if there is another line it looks if the next outfit matches the
			// weather
			while (in.hasNextLine()) {
				boolean breaker = false;
				valid = true;
				skip++;
				// System.out.println(skip);
				boolean been = false;
				if (in.next().trim().equals(temp.trim())) {
					count = Integer.parseInt(in.next().trim());
					for (int t = 0; t < count; t++) {
						been = true;
						String kind = in.next();
						String dir;

						// check what type of clothe to create
						if (kind.trim().equals("Shirts")) {

							dir = in.next();
							fits.get(x).add(new Shirts(dir));

						} else if (kind.trim().equals("Jackets")) {

							dir = in.next();
							fits.get(x).add(new Jackets(dir));

						} else if (kind.trim().equals("Shoes")) {

							dir = in.next();
							fits.get(x).add(new Shoes(dir));

						} else if (kind.trim().equals("Bottoms")) {

							dir = in.next();
							fits.get(x).add(new Bottoms(dir));

						} else if (kind.trim().equals("Hoodies")) {

							dir = in.next();
							fits.get(x).add(new Hoodies(dir));

						}

						// checking if the piece is repeated
						if (x != 0 && usedThisWeek(fits.get(x).get(t).getFileDirectory())
								&& fits.get(x).get(t).isWashable()) {

							System.out.println("ssss" + fits.get(x).get(t).getFileDirectory());
							valid = false;
							fits.get(x).clear();
							for (int oop = t + 1; oop < count; oop++) {
								in.next();
								in.next();
							}
							breaker = true;
							break;

						}

					}

					// continuing if needed
					if (breaker) {
						continue;
					}

					// adding piece if possible
					if (valid == true) {
						chosen.add(fits.get(x));
						System.out.println(chosen.size() + " is the current size");
						amounts.add(count);
						System.out.println("new");
						// for (ArrayList<Clothing> c : chosen) {
						// for (Clothing cl : c) {
						// System.out.println(cl.getFileDirectory());
						// }
						// }
						break;
					}
				}
				// if the outfit does not fit criteria
				if (!been) {
					count = Integer.parseInt(in.next().trim());
					for (int k = 0; k < (count * 2); k++) {
						// System.out.println(in.next());
						in.next();
					}
				}

			}

		}

		// show the outfits
		displayOutfits(val);

		// save the schedule for the week in text file
		try {
			PrintWriter pw = new PrintWriter(new File("files/" + Test.getName() + "/Schedule"));

			int variable = 0;
			// this is the document setup
			pw.print("Schedule");
			for (int n = 0; n < 7; n++) {
				pw.print("\n");
				if (n >= val) {
					int counter = 0;
					pw.print(amounts.get(counter) + " ");
					for (Clothing clo : chosen.get(variable)) {

						pw.print(clo.getFileDirectory() + " ");

					}
					counter++;
					variable++;

				} else {
					pw.print("-1");
				}
			}
			// pw.print();
			pw.flush();
			pw.close();
		} catch (Exception e) {

		}
	}

	/////////////////////////////////////////////////////////
	// setting the schedule for normal new week
	public void newWeek() {

		// re-mixing the order of the outfits
		try {
			input = new Scanner(new File("files/" + Test.getName() + "/Outfits"));
			input.nextLine();
		} catch (Exception e) {
		}

		while (input.hasNextLine()) {
			String s = input.nextLine();
			holder.add(s);
		}

		try {
			PrintWriter p = new PrintWriter(new File("files/" + Test.getName() + "/Outfits"));
			p.print("Schedule");

			for (int v = (holder.size() - 1); v >= 0; v--) {
				p.print("\n");
				p.print(holder.get(v));
				System.out.println(holder.get(v));
			}

			p.flush();
			p.close();

		} catch (Exception e) {

		}

		// getting outfits for all of the days
		int count;
		int skip = 0;

		int x = -1;
		for (int top = 0; top < 7; top++) {
			x++;
			boolean valid = true;

			fits.add(new ArrayList<Clothing>());

			// getting ready for the reading of data
			try {
				in = new Scanner(new File("files/" + Test.getName() + "/Outfits"));
				in.useDelimiter("[,\n]");
				in.nextLine();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

			// scrapping the weather
			temp = scrappingWeather(top);

			// skipping the outfits already looked at
			for (int z = 0; z < skip; z++) {
				System.out.println(in.nextLine() + " " + skip);

			}
			// if there is another line it looks if the next outfit matches the
			// weather
			while (in.hasNextLine()) {
				boolean breaker = false;
				valid = true;
				skip++;
				// System.out.println(skip);
				boolean been = false;
				if (in.next().trim().equals(temp.trim())) {
					count = Integer.parseInt(in.next().trim());
					for (int t = 0; t < count; t++) {
						been = true;
						String kind = in.next();
						String dir;

						// check what type of clothe to create
						if (kind.trim().equals("Shirts")) {

							dir = in.next();
							fits.get(x).add(new Shirts(dir));

						} else if (kind.trim().equals("Jackets")) {

							dir = in.next();
							fits.get(x).add(new Jackets(dir));

						} else if (kind.trim().equals("Shoes")) {

							dir = in.next();
							fits.get(x).add(new Shoes(dir));

						} else if (kind.trim().equals("Bottoms")) {

							dir = in.next();
							fits.get(x).add(new Bottoms(dir));

						} else if (kind.trim().equals("Hoodies")) {

							dir = in.next();
							fits.get(x).add(new Hoodies(dir));

						}

						// checking if the piece is repeated
						if (x != 0 && usedThisWeek(fits.get(x).get(t).getFileDirectory())
								&& fits.get(x).get(t).isWashable()) {
							System.out.println("ssss" + fits.get(x).get(t).getFileDirectory());
							valid = false;
							fits.get(x).clear();
							for (int oop = t + 1; oop < count; oop++) {
								in.next();
								in.next();
							}
							breaker = true;
							break;
						}

					}

					// continuing if needed
					if (breaker) {
						continue;
					}

					// // adding piece if possible
					if (valid == true) {

						chosen.add(fits.get(x));
						System.out.println(chosen.size() + " is the current size");

						amounts.add(count);
						System.out.println("new");
						// for (ArrayList<Clothing> c : chosen) {
						// for (Clothing cl : c) {
						// System.out.println(cl.getFileDirectory());
						// }
						// }
						break;
					}
				}
				// if the piece never matched any of the needs then it is
				// skipped here
				if (!been) {
					count = Integer.parseInt(in.next().trim());
					for (int k = 0; k < (count * 2); k++) {
						// System.out.println(in.next());
						in.next();
					}
				}

			}
			// if no one still has picked an outfit for the day
			// if (chosen.get(x) == null) {
			// System.out.println("bruh this is the prblm");
			// }

		}

		// if there are no possible outfits add a temp one
		if (chosen.size() < 7) {
			tmp.add(new Shoes("files/bob/Shoes/cement.jpeg"));
			tmp.add(new Bottoms("files/bob/Bottoms/levi.jpg"));
			tmp.add(new Hoodies("files/bob/Hoodies/chanelHood.jpg"));

			chosen.add(tmp);
		}

		// show the outfits
		displayOutfits(0);

		// save the schedule for the week in text file
		try {
			PrintWriter pw = new PrintWriter(new File("files/" + Test.getName() + "/Schedule"));

			int variable = 0;
			// this is the document setup
			pw.print("Schedule");
			for (int n = 0; n < 7; n++) {
				pw.print("\n");
				if (n >= 0) {
					int counter = 0;
					pw.print(amounts.get(counter) + " ");
					for (Clothing clo : chosen.get(variable)) {

						pw.print(clo.getFileDirectory() + " ");

					}
					counter++;
					variable++;

				} else {
					pw.print("-1");
				}
			}
			// pw.print();
			pw.flush();
			pw.close();
		} catch (Exception e) {

		}
	}

	// checks if a piece has been used already that week
	public boolean usedThisWeek(String direct) {
		for (ArrayList<Clothing> c : chosen) {
			for (Clothing cl : c) {
				if (cl.getFileDirectory().trim().equals(direct.trim())) {
					return true;
				}
			}
		}
		return false;
	}

	// setting up the frame
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
		subTitle.setBounds(390, 10, 250, 50);
		subTitle.setHorizontalAlignment(SwingConstants.CENTER);
		subTitle.setForeground(Color.decode("#f19f4d"));

		// setting the days labels
		int xVal = 38;
		for (int x = 0; x < 7; x++) {
			daysLabel[x] = new JLabel(days[x]);
			daysLabel[x].setFont(new Font("Arial", Font.PLAIN, 18));
			daysLabel[x].setHorizontalAlignment(SwingConstants.CENTER);
			daysLabel[x].setForeground(Color.decode("#4484ce"));
			fit[x] = new JPanel();
			fit[x].setBackground(Color.decode("#f19f4d"));
			fit[x].setBorder(BorderFactory.createLineBorder(Color.black));
			fit[x].setLayout(null);

			panel.add(fit[x]);
			panel.add(daysLabel[x]);

			fit[x].setBounds(xVal - 20, 105, 140, 310);
			daysLabel[x].setBounds(xVal, 30, 100, 100);

			// adding the piece holders of each page
			int yer = 25;
			for (int y = 0; y < 5; y += 2) {
				pieces[x][y] = new JLabel();
				fit[x].add(pieces[x][y]);
				pieces[x][y].setOpaque(true);
				pieces[x][y].setBackground(Color.decode("#f19f4d"));
				pieces[x][y].setBounds(3, yer, 65, 2 * (320 / 6) - 25);

				pieces[x][y + 1] = new JLabel();
				fit[x].add(pieces[x][y + 1]);
				pieces[x][y + 1].setOpaque(true);
				pieces[x][y + 1].setBackground(Color.decode("#f19f4d"));
				pieces[x][y + 1].setBounds(72, yer, 65, 2 * (320 / 6) - 25);
				yer += (670 / 6) - 20;
			}
			xVal += 145;
		}
	}

	// scraping data from the (Aranesh) Source of help -https://www.youtube.com/watch?v=X5XC5b75tus
	public String scrappingWeather(int whatDay) {
		// ArrayList of all the days
		ArrayList<String> daysOfTemps = new ArrayList<String>();

		// ArrayList of all the temperatures
		ArrayList<Integer> temps = new ArrayList<Integer>();

		int testcount = 0;

		int daycounter = 0;
		int tempCounter = 0;
		int validDay = 0;

		int tempOfTheDay = 0;
		int tempOfScrap;

		String theDay = null;
		Document doc;

		try {
			// Library gets the website 
			
			String web = "https://www.google.com/search?q="+uP.getlocT()+"+weather&num=20";
			doc = Jsoup.connect(web).get();

			// Elements that refer to the days on googles weather website
			Elements days = doc.getElementsByAttributeValue("style", "padding-top:7px;line-height:15px");

			// Elements that refer to the degrees on googles weather website
			Elements degrees = doc.getElementsByAttributeValue("style", "display:inline");

			String returnStringDay = "";

			int count = 0;

			// Loop through all the days on googles website
			for (Element day : days) {

				returnStringDay = day.toString();
				String d = returnStringDay.substring(73, 75); // Gets the day

				daysOfTemps.add(d); // Stores the day in an arraylist
				System.out.println(returnStringDay.substring(73, 75));
				count++;

				// Breaks out of the loop when it reaches the 7th day,
				// disregarding the 8th day
				if (count == 7) {
					break;
				}
			}

			// scrappingWeather
			System.out.println("break");

			// Loop through all the degrees in googles website
			for (Element degree : degrees) {

				testcount++;

				// Adds all the high degrees for each day to an array list
				if (testcount == 3 || testcount == 5 || testcount == 7 || testcount == 9 || testcount == 11
						|| testcount == 13 || testcount == 15 || testcount == 17) {

					String rreturnString = degree.toString();
					String test = rreturnString.replaceAll("<span class=\"wob_t\" style=\"display:inline\">", "");
					test = test.replaceAll("</span>", "");
					tempOfScrap = Integer.parseInt(test);
					temps.add(tempOfScrap);
					// System.out.println(test);

				}

			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		// Sets the day what the day the user is looking for
		if (whatDay == 0) {

			theDay = "Mo";
		} else if (whatDay == 1) {

			theDay = "Tu";

		} else if (whatDay == 2) {

			theDay = "We";

		} else if (whatDay == 3) {

			theDay = "Th";
		} else if (whatDay == 4) {

			theDay = "Fr";

		} else if (whatDay == 5) {

			theDay = "Sa";

		} else if (whatDay == 6) {

			theDay = "Su";
		}

		// System.out.println("Pause");

		// Loop through all the days
		for (String day : daysOfTemps) {

			// If the day of the user equals to one of the days on googles
			// weather, set the index of where that day is
			if (theDay.equals(day)) {

				validDay = daycounter;
				break;
			}

			daycounter++;
		}

		// Loop through all the temperatures
		for (int temp : temps) {

			// If the index of the temperature is equal to the index that of the
			// chosen day save the temperature
			if (tempCounter == validDay) {

				tempOfTheDay = temp;
				break;
			}

			// System.out.println(temp);
			tempCounter++;

		}

		System.out.println(tempOfTheDay);

		// Returns hot
		if (tempOfTheDay > uP.getHotT()) {
			String weath = "HOT";
			return weath;
		}

		// Returns cold
		else if (tempOfTheDay < uP.getColdT()) {

			String weath = "COLD";
			return weath;
		}

		// Returns medium
		else {
			{

				String weath = "MEDIUM";
				return weath;
			}
		}
	}

	// scraping the day of the week
	public void setDayOfWeek() {

	}

	// displaying the outfits onto the frames
	public void displayOutfits(int i) {
		int count = -1;
		// adding the outfits to the page
		for (int x = i; x < 7; x++) {
			int box = 0;
			count++;
			// adding outfit for the specific days
			for (Clothing c : chosen.get(count)) {
				pieces[x][box].setIcon(new ImageIcon(new ImageIcon(c.getFileDirectory().trim()).getImage()
						.getScaledInstance(65, 2 * (320 / 6) - 25, 0)));
				System.out.println("put" + c.getFileDirectory());
				box++;
			}
		}
	}

	// goes home when the logo is clicked
	public void mouseClicked(MouseEvent event) {
		if (event.getSource() == logo) {
			dispose();
			new HomePage();
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
