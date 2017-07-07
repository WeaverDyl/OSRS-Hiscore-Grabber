# OSRS-HISCORE-GRABBER

## WHAT THIS IS

This is a program serving two main purposes, Both of which deal with the hiscore system of the game known as "Old School RuneScape".

1. This program can be used to track the progress of a number of people in a certain skill. This is useful for clan skilling events or similar activities.
2. This program can be used to compile the overall ranking of the same number of people, listing them by overall level and experience

## WHY THIS EXISTS

This program was created so that holding clan events in Old School RuneScape would become more convenient, because this makes it unnecessary to manually record the data for an entire clan, which can have hundreds of members. This automates the entire process, saving time.

## HOW TO USE

#### TO USE THE SKILL GRABBER:
To begin, update the playersList in the Utility class with all of the players you want to track.

In the Grabber class, call <runGrabber("skill")> where *skill* is any valid skill name, all of which are located in *Utility.SKILL_NAMES*.

This will print experience and level data in an array format, ready to be copy and pasted directly into an array in the
CalculateGains class.

##### If the Grabber class was run to collect initial data:
1. Copy and paste the experience data into the *experienceForPlayerBegin* array, located in the *CalculateGains* class.
2. Copy and paste the level data into the *levelForPlayerBegin* array, also located in the *CalculateGains* class.

**Keep this data saved in the two arrays, and do not lose it.**

##### If the Grabber class was run to collect final data:
1. Copy and paste the experience data into the *experienceForPlayerEnd* array, located in the *CalculateGains* class.
2. Copy and paste the level data into the *levelForPlayerEnd* array, also located in the *CalculateGains* class.

Now that both the beginning and ending arrays are both full of data, run the *CalculateGains* class, and two
lists will be printed out. One list ranks players by how much experience was gained in a certain skill, the other
ranks players by how many levels they gained.

#### TO USE THE HISCORE GRABBER:

To begin, update the *playersList* in the *Utility* class with all of the players you want to collect data for.

Then, run the *ClanHiscores* class, and a sorted list of players will appear, ranked by total level, and then total experience if two people's total level is the same.

## License
This project is licensed under the MIT License - see the LICENSE.md file for details.
