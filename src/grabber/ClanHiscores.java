package grabber;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.ws.http.HTTPException;

/**
 * A standalone program that ranks a list of players in order by total level
 * 
 * @author Dylan Weaver
 *
 */
public class ClanHiscores {
	// An arraylist of player objects which will later be sorted and printed
	private static List<Player> players = new ArrayList<>();
	// Marks the position/rank (NOT index) that we are at in a list
	private static int position = 1;
	// The current line of data - null before any data is read
	private static String currentLine = null;
	// Any excluded players (due to errors) will br place in here
	private static List<String> playerErrors = new ArrayList<>();

	/**
	 * For the given position, connect to the hiscores page for the username and
	 * read the data
	 * 
	 * @param position The current index of the array
	 */
	private static boolean connect(int position) {
		// Connect to the hiscores using the username found at the position index of
		// Utlity.playersList
		String stringURL = "http://services.runescape.com/m=hiscore_oldschool/index_lite.ws?player="
				+ Utility.playersFinal[position].trim().replaceAll(" ", "_");
		try {
			// Open a connection to StringUrl
			URL url = new URL(stringURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			if (conn.getResponseCode() == 404) {
				throw new HTTPException(404);
			}
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			// Read the line of data (which contains overall level, rank, and experience)
			currentLine = in.readLine();
			// Close the stream
			in.close();
			return true;
		} catch (HTTPException e) {
			// If we 404, add it to the list of player errors and print a note about it
			if (e.getStatusCode() == 404) {
				playerErrors.add(Utility.playersFinal[position]);
				System.out.println(
						"404 returned for: " + "\"" + Utility.playersFinal[position].replaceAll(" ", "_") + "\"");
			}
			return false;
		} catch (Exception e) {
			System.out.println("Error connecting " + e);
			return false;
		}
	}

	/**
	 * Grabs the level and experience data for each player, adding it to a player
	 * object and then adding the player object into an arraylist
	 */
	private static void runGrabber() {
		System.out.println("CLAN HISCORES");
		System.out.println("----");
		System.out.println("Please be patient, this takes a bit. Any errors (such as a name change or invalid username)"
						+ " will appear directly below this line.");
		// Go through the entire list of players
		for (int i = 0; i < Utility.playersFinal.length; i++) {
			try {
				// Connect to the hiscores for the current index
				if (connect(i)) {
					// Break up the line into an array of rank, level, and experience respectively
					String[] skillBrokenUp = currentLine.replaceAll(" ", "").split(",");
					int level = Integer.parseInt(skillBrokenUp[1]);
					int experience = Integer.parseInt(skillBrokenUp[2]);

					// Create a Player object and add this object to the players arrayList
					Player p = new Player(Utility.playersFinal[i], level, experience);
					players.add(p);
				}
			} catch (Exception e) {
				// Useful in cases of a misspelled name or a name change within the clan
				e.printStackTrace();
			}
		}
		// Sort the list by level and experience using Player's compareTo method
		Collections.sort(players, Collections.reverseOrder());
		// We exited the loop, so we're done!
		System.out.println("\nDONE");
	}

	/**
	 * Runs the grabber, printing out a formatted list of the current clan standings
	 */
	private static void printHiscores() {
		runGrabber();

		// Print a list of all the players that aren't included so the user is aware
		// that the results may be incorrect
		if (!playerErrors.isEmpty()) {
			System.out.println("Players excluded from results due to errors:");
			for (String players : playerErrors) {
				System.out.println(players.replace(" ", "_"));
			}
		}

		// Print the final rankings
		System.out.println("\nResults:");
		for (Player list : players) {
			System.out.println(position + Utility.getPositionSuffix(position) + list.toString());
			position++;
		}
	}

	public static void main(String[] args) {
		printHiscores();
	}

}
