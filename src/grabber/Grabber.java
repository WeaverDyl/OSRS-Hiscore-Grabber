package grabber;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.ws.http.HTTPException;

/**
 * Grabs data about a user specified skill for a list of players
 * 
 * @author Dylan Weaver
 * 
 */
public class Grabber extends Thread {
	private static int counter; // This is used to make the output a little bit nicer
	private static String currentLine = null; // The current line of data - null before anything is read
	private static List<Player> players = new ArrayList<Player>(); // Stores name, experience, level data for each player
	private static List<String> playerErrors = new ArrayList<>(); // Any excluded players (due to errors) will be here

	/**
	 * For the given position, connect to the hiscores page for the username and
	 * read the data
	 * 
	 * @param skill The skill that we are collecting data for
	 * @param position The current index of the array
	 */
	private static boolean connect(String skill, int position) {
		// Connect to the hiscores using the username found at the position index of
		// Utlity.playersList
		String stringURL = "http://services.runescape.com/m=hiscore_oldschool/index_lite.ws?player="
				+ Utility.playersFinal[position].replaceAll(" ", "_");
		try {
			// Open a connection to StringUrl
			URL url = new URL(stringURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			if (conn.getResponseCode() == 404) {
				throw new HTTPException(404);
			}
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			// Go to the correct line where the skill information is
			for (int i = 0; i < Arrays.asList(Utility.skills).indexOf(skill); i++) {
				in.readLine();
			}
			// Read this correct line
			currentLine = in.readLine();
			// Close the stream
			in.close();
			return true;
		} catch (HTTPException e) {
			// If we 404, add it to the list of player errors and print a warning about it
			if (e.getStatusCode() == 404) {
				playerErrors.add(Utility.playersFinal[position]);
				System.out.println("404 returned for: " + "\"" + Utility.playersFinal[position] + "\""
						+ ". Fix this before using the printed data.");
			}
			return false;
		} catch (Exception e) {
			// Occurs when a connection can't be established
			System.out.println("Error connecting " + e);
			return false;
		}
	}

	/**
	 * Connects to the OSRS hiscores for each player, grabbing data for each member
	 * in the {@code Utility.playersList} array, and storing that in
	 * {@code players}
	 * 
	 * @param skill The skill to collect data for
	 */
	private static void runGrabber(String skill) {
		// Only continue if skill exists in the Utility.SKILL_NAMES array
		if (Utility.checkForValidSkill(skill)) {
			System.out.println("DATA FOR " + skill.toUpperCase() + " EVENT");
			System.out.println("Please wait while the data processes... Errors will appear directly below");
			for (int i = 0; i < Utility.playersFinal.length; i++) {
				try {
					// Connect to the hiscores with a reference of the current position so we know
					// which player to process
					if (connect(skill, i)) {
						// Break up the current line into an array which consists of rank (index 0), level,
						// experience respectively
						String[] skillBrokenUp = currentLine.replaceAll(" ", "").split(",");
						int level = Integer.parseInt(skillBrokenUp[1]);
						int experience = Integer.parseInt(skillBrokenUp[2]) == -1 ? 0 : Integer.parseInt(skillBrokenUp[2]);

						// Create a new Player object which stores the current name, level and
						// experience and add that to Players
						Player p = new Player(Utility.playersFinal[i], level, experience);
						players.add(p);
					}
				} catch (Exception e) {
					System.out.println(Utility.playersFinal[i] + "'s "
							+ skill + " Experience: ERROR" + " " + e);
				}
			}
		} else {
			throw new IllegalArgumentException("Invalid skill name passed.");
		}
	}

	/**
	 * Prints out the experience data for each player
	 */
	private static void printExperience() {
		for (int i = 0; i < players.size(); i++) {
			System.out.print(players.get(i).getExperience() + (i < players.size() - 1 ? ", " : ""));
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
			System.out.print(players.get(i).getLevel() + (i < players.size() - 1 ? ", " : ""));
			counter++;
			// Print on a new line every 10 data points
			if (counter % 10 == 0) {
				System.out.println();
			}
		}
		// Reset the counter
		counter = 0;
	}
	
	private static void printErrors() {
		// Print a list of all the players that aren't included so the user is aware
		// that the results may be incorrect
		if (!playerErrors.isEmpty()) {
			System.out.println("\nPlayers excluded from results due to errors:");
			for (String players : playerErrors) {
				System.out.println(players.replace(" ", "_"));
			}
		}
	}

	public static void main(String[] args) {
		runGrabber("slayer");
		
		//print the names we had trouble grabbing data for
		printErrors();

		System.out.println("\n--- Experience Data ---");
		printExperience();

		System.out.println("\n\n--- Level Data ---");
		printLevel();
	}
}
