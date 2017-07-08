package grabber;

/**
 * Contains utility methods and final arrays for the OSRS hiscore package
 * 
 * @author Dylan Weaver
 *
 */
public final class Utility {

	// An array of every skill in old school runescape
	static final String[] SKILL_NAMES = { "overall", "attack", "defence", "strength", "hitpoints", "ranged", "prayer",
			"magic", "cooking", "woodcutting", "fletching", "fishing", "firemaking", "crafting", "smithing", "mining",
			"herblore", "agility", "thieving", "slayer", "farming", "runecrafting", "hunter", "construction" };

	// An array of every player in the clan
	static final String[] playersList = {"1488", "karambjohn", "nosteponsnek", "daft blunt", "rackle",
			"uim pwc", "timeofgrace", "yeetbihh", "pure impure", "shakenspear", "holly spring", "im speshl", "texman",
			"toti594", "ludena", "layor trop", "figarothecat", "whiplash796", "picleboii", "bonghits", "itdoesnt",
			"the alomais", "dragonsfury", "tjommyt", "luch", "clint  3", "lostatonce", "sirartorian", "powbong",
			"frofuzz", "yeex", "thatzally", "mr zoltan", "praisekek88", "monkfromhell", "megapanda87", "not rackle",
			"llabnonnac", "goondragon", "illegalaryan", "manlikeandy", "saucyturtle", "summonz", "masterchiefe",
			"the package", "r y l o c k", "uzil05", "drunkendunkn", "disco bisqit", "burningpraye", "fishy man555",
			"fegarothecat", "divorced man", "its footer", "semi vz", "derek 3 0", "wild life", "linkinpark69", "voatco",
			"msmapes", "dots anti", "rngjesus", "impended", "end game", "quinn pks", "iron freak1", "jokerman557",
			"wordtm", "ecthelionii", "xnotabotjkx", "zakarius", "babashook", "fruitsaladz", "geepee", "fu man brews",
			"peter fox", "jjboy501", "robots222", "hcbong", "turtljoe", "iron sucrose", "ziekecijfers", "steve shives",
			"thicc ghost", "fishnchips20", "puggiepanda7", "hopes gone", "thefluffster", "18oo", "hard c0ded",
			"maclue vz", "leckel" };

	/**
	 * A private constructor to ensure this class is not subclassed or messed with
	 * 
	 * @throws assertionError
	 *             This class should not be instantiated
	 */
	private Utility() {
		throw new AssertionError("This class can not be instantiated.");
	}

	/**
	 * Capitalizes the first letter of a given string
	 * 
	 * @param str
	 *            The string to capitalize
	 * @return A Capitalized string
	 */
	public final static String capitalizeFirst(String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1, str.length());
	}

	/**
	 * Determines which suffix to use at the end of a given position (st, th, nd,
	 * rd)
	 * 
	 * @param position
	 *            The current position in an array being looped through
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
}
