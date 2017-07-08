package grabber;

import java.util.Comparator;

/**
 * Used for sorting players based on the value of their levels or levels gained
 * 
 * @author Dylan Weaver
 *
 */
public class PlayerLevelGainComparator implements Comparator<Player> {
	
	@Override
	public int compare(Player p1, Player p2) {
		return p1.getLevel() < p2.getLevel() ? -1 : p1.getLevel() > p2.getLevel() ? 1 : 0;
	}

}
