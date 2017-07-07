package grabber;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Prints out the total amount of experience gained by each player by
 * subtracting beginning experience from ending experience, and putting the
 * results into an array that correspond to
 * 
 * @author Dylan Weaver
 *
 */
public class CalculateGains {
	// The current rank for experience gained
	private static int experiencePosition = 1;
	// The current rank for levels gained
	private static int levelPosition = 1;
	// A list of experience and level gains based on players, to be sorted and printed
	private static List<Player> playerGains = new ArrayList<Player>();

	// Total experience at the beginning of the event
	private static Integer[] experienceForPlayerBegin = {};

	// Total experience at the end of the event
	private static Integer[] experienceForPlayerEnd = {};

	// Skill Level at the beginning of the event
	private static Integer[] levelForPlayerBegin = {};

	// Skill level at the end of the event
	private static Integer[] levelForPlayerEnd = {};

	// The difference, or gained experience, of each player
	private static Integer[] experienceGained = new Integer[Utility.playersList.length];

	// The difference, or gained levels, of each player
	private static Integer[] levelsGained = new Integer[Utility.playersList.length];

	/**
	 * Takes the difference of an array containing ending values and an array containing beginning values
	 * and puts the difference in a new array
	 * 
	 * @param beginning An array containing beginning values
	 * @param end An array containing ending values
	 * @param destination The array to put the difference of beginning and end into
	 * @throws ArraySizeUnequalException Thrown when the length of beginning and end are not the same
	 */
	private static void calculateTotalGains(Integer[] beginning, Integer[] end, Integer[] destination)
			throws ArraySizeUnequalException {
		if (beginning.length == end.length) {
			for (int i = 0; i < end.length; i++) {
				destination[i] = end[i] - beginning[i];
			}
		} else {
			throw new ArraySizeUnequalException("The passed arrays are not of equal length.");
		}
	}

	/**
	 * Calculates the amount of experience and levels gained for each player and puts that data
	 * into an array. Those two arrays are then sorted and printed out in descending order in
	 * a readable format
	 * 
	 * @throws ArraySizeUnequalException Thrown when Utility.playersList and experienceGained or levelsGained are not the same length
	 */
	private static void printGainedExperience() throws ArraySizeUnequalException {
		calculateTotalGains(experienceForPlayerBegin, experienceForPlayerEnd, experienceGained);
		calculateTotalGains(levelForPlayerBegin, levelForPlayerEnd, levelsGained);

		if ((Utility.playersList.length == experienceGained.length)
				&& (Utility.playersList.length == levelsGained.length)) {
			System.out.println("CLAN EVENT");
			System.out.println("Total gains from every member: ");

			// Go through the playerList, creating a new Player object for each player in the array
			for (int i = 0; i < Utility.playersList.length; i++) {
				Player p = new Player(Utility.playersList[i], levelsGained[i], experienceGained[i]);
				playerGains.add(p);
			}

			// Sort and print experience gains and print this information out
			System.out.println("----\nExperience Gains");
			Collections.sort(playerGains, new PlayerExperienceGainComparator().reversed());
			for (Player list : playerGains) {
				System.out.println(experiencePosition + Utility.getPositionSuffix(experiencePosition) + "\""
						+ list.name.replace(" ", "_") + "\"" + " with: "
						+ NumberFormat.getInstance().format(list.experience) + " experience.");
				// Iterate the position to maintain correct rankings
				experiencePosition++;
			}

			// Sort and print level gains and print this information out
			System.out.println("----\nLevel Gains");
			Collections.sort(playerGains, new PlayerLevelGainComparator().reversed());
			for (Player list : playerGains) {
				System.out.println(levelPosition + Utility.getPositionSuffix(levelPosition) + "\""
						+ list.name.replace(" ", "_") + "\"" + " with: " + list.level + " levels.");
				// Iterate the position to maintain correct rankings
				levelPosition++;
			}
		} else {
			throw new ArraySizeUnequalException(
					"Error, the array of names is not the same length as the array of experience gained");
		}
	}

	public static void main(String[] args) throws ArraySizeUnequalException {
		printGainedExperience();
	}
}
