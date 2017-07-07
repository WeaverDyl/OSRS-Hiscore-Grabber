package grabber;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A standalone program that ranks a list of players in order by total level
 * 
 * TODO handle 404 errors and other responses
 * 
 * @author Dylan Weaver
 *
 */
public class ClanHiscores {
	// An arraylist of player objects which will later be sorted and printed
	private static List<Player> players = new ArrayList<Player>();
	// Marks the position/rank (NOT index) that we are at in a list
	private static int position = 1;
	// The current line of data - null before any data is read
	private static String currentLine = null;

	/**
	 * For the given position, connect to the hiscores page for the username and
	 * read the data
	 * 
	 * @param position The current index of the array
	 */
	private static void connect(int position) {
		// Connect to the hiscores using the username found at the position index of Utlity.playersList
		String stringURL = "http://services.runescape.com/m=hiscore_oldschool/index_lite.ws?player="
				+ Utility.playersList[position].trim().replaceAll(" ", "_");
		try {
			// Open a connection to StringUrl
			URL url = new URL(stringURL);
			URLConnection conn = url.openConnection();
			conn.connect();
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			// Read the line of data (which contains overall level, rank, and experience)
			currentLine = in.readLine();
			// Close the stream
			in.close();
		} catch (Exception e) {
			System.out.println("Error connecting " + e);
		}
	}
	
	/**
	 * Grabs the level and experience data for each player, adding it to a player object
	 * and then adding the player object into an arraylist
	 */
	private static void runGrabber() {
		System.out.println("CLAN HISCORES");
		System.out.println("----");
		System.out.println(
				"Please be patient, this takes a bit. Any errors (such as a name change or incorrect username)"
				+ " will appear directly below this line.");
			// Go through the entire list of players
			for (int i = 0; i < Utility.playersList.length; i++) {
				try {
					// Connect to the hiscores for the current index
					connect(i);
					
					// Break up the line into an array of rank, level, and experience respectively 
					String[] skillBrokenUp = currentLine.replaceAll(" ", "").split(",");
					int level = Integer.parseInt(skillBrokenUp[1]);
					int experience = Integer.parseInt(skillBrokenUp[2]);
					
					// Create a Player object and add this object to the players arrayList
					Player p = new Player(Utility.playersList[i], level, experience);
					players.add(p);
				} catch (Exception e) {
					// Useful in cases of a misspelled name or a name change within the clan
					e.printStackTrace();
				}
			}
			// Sort the list by level and experience using Player's compareTo method
			Collections.sort(players, Collections.reverseOrder());
			// We exited the loop, so we're done!
			System.out.println("\nDONE\n");
	}

	/**
	 * Runs the grabber, printing out a formatted list of the current clan standings
	 */
	private static void printHiscores() {
		runGrabber();

		for (Player list : players) {
			System.out.println(position + Utility.getPositionSuffix(position) + list.toString());
			position++;
		}
	}

	public static void main(String[] args) {
		printHiscores();
	}

}
