package grabber;

/**
 * Contains utility methods and final arrays for the OSRS hiscore package
 * 
 * @author Dylan Weaver
 *
 */
public final class Utility {

	// An array of every skill in old school runescape
	static final String[] skills = { "overall", "attack", "defence", "strength", "hitpoints", "ranged", "prayer",
			"magic", "cooking", "woodcutting", "fletching", "fishing", "firemaking", "crafting", "smithing", "mining",
			"herblore", "agility", "thieving", "slayer", "farming", "runecrafting", "hunter", "construction" };

	// An array of every ranked member
	static final String[] players = {};

	/**
	 * A private constructor to ensure this class is not subclassed or messed with
	 * 
	 * @throws assertionError This class should not be instantiated
	 */
	private Utility() {
		throw new AssertionError("This class can not be instantiated.");
	}

	/**
	 * Capitalizes the first letter of a given string
	 * 
	 * @param str The string to capitalize
	 * @return A Capitalized string
	 */
	public final static String capitalizeFirst(String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1, str.length());
	}

	/**
	 * Determines which suffix to use at the end of a given position (st, th, nd, rd)
	 * 
	 * @param position The current position in an array being looped through
	 * @return A suffix to be appended to the position
	 */
	public final static String getPositionSuffix(int position) {
		if (position >= 11 && position <= 13) {
			return "th: ";
		} else {
			switch (position % 10) {
			case 1:
				return "st: ";
			case 2:
				return "nd: ";
			case 3:
				return "rd: ";
			default:
				return "th: ";
			}
		}
	}

	/**
	 * Determines if a number of arrays passed in are of the same length or not.
	 * 
	 * @param arrays A collection of arrays to check for length equality
	 * @return True if the arrays are all of the same length, false otherwise
	 */
	public static boolean areSameLength(Object[]... arrays) {
		int firstLength = arrays[0].length;
		for (Object[] playerData : arrays) {
			if (playerData.length != firstLength) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Checks that {@code skill} is in {@code SKILL_NAMES}
	 * 
	 * @param str The string to check against {@code SKILL_NAMES}
	 */
	static boolean checkForValidSkill(String str) {
		for (int i = 0; i < skills.length; i++) {
			if (skills[i].equalsIgnoreCase(str)) {
				return true;
			}
		}
		return false;
	}
}
