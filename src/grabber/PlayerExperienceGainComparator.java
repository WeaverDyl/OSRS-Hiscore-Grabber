package grabber;

import java.util.Comparator;

/**
 * Used for sorting players based on their experience or experience gained
 * 
 * @author Dylan Weaver
 *
 */
public class PlayerExperienceGainComparator implements Comparator<Player> {

	@Override
	public int compare(Player p1, Player p2) {
		return p1.getExperience() < p2.getExperience() ? -1 : p1.getExperience() > p2.getExperience() ? 1 : 0;
	}

}
