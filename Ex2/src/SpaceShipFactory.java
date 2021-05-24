import oop.ex2.*;

/**
 * This class has a single static method (createSpaceships(String[]). It is used by the supplied driver to
 * create all the spaceship objects according to the command line arguments.
 */
public class SpaceShipFactory {

	/** symbols a spaceship of type Human */
	private static final char HUMAN_CHAR_INPUT = 'h';

	/** symbols a spaceship of type Runner */
	private static final char RUNNER_CHAR_INPUT = 'r';

	/** symbols a spaceship of type Basher */
	private static final char BASHER_CHAR_INPUT = 'b';

	/** symbols a spaceship of type Aggressive */
	private static final char AGGRESSIVE_CHAR_INPUT = 'a';

	/** symbols a spaceship of type Drunkard */
	private static final char DRUNKARD_CHAR_INPUT = 'd';

	/** symbols a spaceship of type Special */
	private static final char SPECIAL_CHAR_INPUT = 's';

	/**
	 * The function gets the input args array of strings and create new spaceships according to the given
	 * types. if the supplied array contains the strings "a" and "b", the method will return an array
	 * containing an Aggressive ship and a Basher ship..
	 * @param args an array of strings from the user
	 * @return an array of SpaceShips
	 */
	public static SpaceShip[] createSpaceShips(String[] args) {
		SpaceShip[] all_space_ships = new SpaceShip[args.length];
		for (int i = 0; i < args.length; i++) {
			if (args[i].length() == 1){
				switch (args[i].charAt(0)) {
				case HUMAN_CHAR_INPUT:
					all_space_ships[i] = new Human();
					break;
				case RUNNER_CHAR_INPUT:
					all_space_ships[i] = new Runner();
					break;
				case BASHER_CHAR_INPUT:
					all_space_ships[i] = new Basher();
					break;
				case AGGRESSIVE_CHAR_INPUT:
					all_space_ships[i] = new Aggressive();
					break;
				case DRUNKARD_CHAR_INPUT:
					all_space_ships[i] = new Drunkard();
					break;
				case SPECIAL_CHAR_INPUT:
					all_space_ships[i] = new Special();
					break;
				}
			}
		}
		return all_space_ships;
	}
}
