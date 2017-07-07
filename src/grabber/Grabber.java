package grabber;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Grabs data about a user specified skill for a list of players
 * 
 * @author Dylan Weaver
 * 
 */
public class Grabber extends Thread {
	private static int counter; // This is used to make the output a little bit nicer
	private static String currentLine = null; // The current line of data - null before anything is read
	private static List<Player> players = new ArrayList<Player>(); // Stores name, experience, and level data for each player
	int threads = Runtime.getRuntime().availableProcessors(); //The number of threads to create

	/**
	 * For the given position, connect to the hiscores page for the username and
	 * read the data
	 * 
	 * @param skill The skill that we are collecting data for
	 * @param position The current index of the array
	 */
	private static void connect(String skill, int position) {
		// Connect to the hiscores using the username found at the position index of Utlity.playersList
		String stringURL = "http://services.runescape.com/m=hiscore_oldschool/index_lite.ws?player="
				+ Utility.playersList[position].replaceAll(" ", "_");
		try {
			// Open a connection to StringUrl
			URL url = new URL(stringURL);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.connect();
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			// Go to the correct line where the skill information is
			for (int i = 0; i < Arrays.asList(Utility.SKILL_NAMES).indexOf(skill); i++) {
				in.readLine();
			}
			// Read this correct line
			currentLine = in.readLine();
			// Close the stream
			in.close();
		} catch (Exception e) {
			// Occurs when a connection can't be established TODO handle 404
			System.out.println("Error connecting " + e);
		}
	}

	/**
	 * Connects to the OSRS hiscores for each player, grabbing data for each
	 * member in the <CODE>Utility.playersList</CODE> array, and storing that in <CODE>players</CODE>
	 * 
	 * @param skill The skill to collect data for
	 */
	private static void runGrabber(String skill) {
		// Only continue if skill exists in the Utility.SKILL_NAMES array
		if (checkForValidSkill(skill)) {
			System.out.println("DATA FOR " + skill.toUpperCase() + " EVENT");
			System.out.println("Please wait while the data processes...");
			for (int i = 0; i < Utility.playersList.length; i++) {
				try {
					// Connect to the hiscores with a reference of the current position so we know which player to process
					connect(skill, i);
					
					// Break up the current line into an array which consists of rank, level, experience respectively
					String[] skillBrokenUp = currentLine.replaceAll(" ", "").split(",");
					int level = Integer.parseInt(skillBrokenUp[1]);
					int experience = Integer.parseInt(skillBrokenUp[2]) == -1 ? 0 :  Integer.parseInt(skillBrokenUp[2]);
					
					// Create a new Player object which stores the current name, level and experience and add that to Players
					Player p = new Player(Utility.playersList[i], level, experience);
					players.add(p);
				} catch (Exception e) {
					// Useful in cases of a misspelled name or a name change within the clan
					System.out.println(Utility.capitalizeFirst(Utility.playersList[i].trim() + "'s "
							+ Utility.capitalizeFirst(skill) + " Experience: ERROR" + " " + e));
				}
			}
		} else {
			System.out.println("Error, invalid skill name.");
		}
	}
	
	/**
	 * Prints out the experience data for each player
	 */
	private static void printExperience() {
		for (int i = 0; i < players.size(); i++) {
			System.out.print(players.get(i).experience + (i < players.size() - 1 ? ", " : ""));
			counter++;
			// Print on a new line every 10 data points
			if (counter % 10 == 0) {
				System.out.println();
			}
		}
		// Reset the counter
		counter = 0;
	}
	
	/**
	 * Prints out the level data for each player
	 */
	private static void printLevel() {
		for (int i = 0; i < players.size(); i++) {
			System.out.print(players.get(i).level + (i < players.size() - 1 ? ", " : ""));
			counter++;
			// Print on a new line every 10 data points
			if (counter % 10 == 0) {
				System.out.println();
			}
		}
		// Reset the counter
		counter = 0;
	}
	
	/**
	 * Checks that <CODE>skill</CODE> is in <CODE>Utility.SKILL_NAMES</CODE> 
	 * 
	 * @param str The string to check against SKILL_NAMES
	 */
	static boolean checkForValidSkill(String str) {
		for (int i = 0; i < Utility.SKILL_NAMES.length; i++) {
			if (Utility.SKILL_NAMES[i].equalsIgnoreCase(str)) {
				return true;
			}
		}
		return false;
	}

	public static void main(String[] args) {
		runGrabber("hunter");
		
		System.out.println("\n--- Experience Data ---");
		printExperience();
		
		System.out.println("\n\n--- Level Data ---");
		printLevel();
	}
}
