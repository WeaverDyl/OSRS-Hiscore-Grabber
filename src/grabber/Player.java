package grabber;

import java.text.NumberFormat;

/**
 * A class to represent a player.
 * A player has a username, an experience, and a level
 * 
 * @author Dylan Weaver
 *
 */
public class Player implements Comparable<Player> {
	String name; // Represents a players username
	int level; // Represents a players level
	int experience; // Represents a players experience
	int i; // Used as a return for the compareTo method

	/**
	 * A constructor representing a player
	 * 
	 * @param name The username of the player
	 * @param level The level of the player
	 * @param experience The experience of the player
	 */
	public Player(String name, int level, int experience) {
		this.name = name;
		this.level = level;
		this.experience = experience;
	}

	/**
	 * Compares the level and experience parameters of two players
	 * 
	 * @param other The other player that is being compared
	 * @return i "1" if the current player's level is greater than the other
	 * 	     player's OR both players have equal levels and the current player's
	 *           experience is greater than the other player's
	 * 
	 *           "-1" if the other player's level is greater than the current player's
	 *           OR both players have equal levels and the other player's experience
	 *           is greater than the current player's
	 * 
	 *           "0" if both players have the same level AND the same experience
	 */
	public int compareTo(Player other) {
		if (this.level > other.level) {
			i = 1;
		} else if (other.level > this.level) {
			i = -1;
		} else if (this.level == other.level) {
			if (this.experience == other.experience) {
				i = 0;
			} else {
				i = this.experience - other.experience;
			}
		}
		return i;
	}

	/**
	 * Represents a player as a string.
	 */
	public String toString() {
		return "\"" + name + "\"" + " total level: " + level + " with " + NumberFormat.getInstance().format(experience) + " xp";
	}
}
