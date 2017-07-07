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
		return p1.level < p2.level ? -1 : p1.level == p1.level ? 0 : 1;
	}

}
