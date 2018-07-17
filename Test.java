/*
Name(s): 
Saryuhan Logeswaran 
- Test Class 
- Log In Screen 
- Closet
- Creating Outfit
- Home Page
- Schedule
- AI 
- Clothing Hierarchy (Includes the certain categories)
Aranesh Athavan 
- Upload Clothe
- User preference 
- Scraping of the weather 
Date:
-June 13th 2018
Course Code:
-ICS 4U1 
-Instructor - Mr.Fernandes 
Title:
Everyday Outfits
Description:
Everyday Outfits is an application that helps users pick their outfits so that
their time can be spent on more effective concepts later on in the their day. 
The user creates their outfits and the AI picks their outfits for every day of
the week. Factors that affect the selection process would be the weather the 
amount of times the outfit has been selected. Users are given the chance to 
upload their own clothing and create their own outfits.
Features:
	- The AI 
	- Scraping the data of google    
	- Good Graphics 
	- Add your pieces of clothing
	- Create your own outfits
Major Skills:
	-Using Arraylists 
	-Hierarchy for the AI 
	-Writing in the textfiles and manipulating the text files   
	-Scraping data of google 


Areas of Concern:
	-No concerns 

 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {
	private static String name;

	public static void main(String[] args) {
		new Login();
		// new Closet(0);
		// new CreatingOutfit();
		// new HomePage();
		// new AddingPiece();
		// new Schedule();

	}

	// gets the day of the week and set/get name
	// (knowm.org/get-day-of-week-from-date-object-in-java/)
	public static String getDate() {
		Date d = new Date();
		SimpleDateFormat s = new SimpleDateFormat("EEEE");
		return s.format(d);
	}

	public static String getName() {
		return name;
	}

	public static void setName(String nom) {
		name = nom;
	}

}
