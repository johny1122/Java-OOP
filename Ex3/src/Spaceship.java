import oop.ex3.spaceship.Item;


/**
 * Implementation of a spaceship class
 */
public class Spaceship {


	/** symbols a return function value of minus one */
	private static final int VALUE_MINUS_ONE = (-1);

	/** symbols a return function value of minus two */
	private static final int VALUE_MINUS_TWO = (-2);

	/** symbols a return function value of minus three */
	private static final int VALUE_MINUS_THREE = (-3);

	/** symbols a return function value of zero */
	private static final int VALUE_ZERO = 0;


	private final String name;
	private final int[] crewIDs;
	private final int numOfLockers;
	private int current_numOfLockers;
	private final Item[][] constraints;
	private final Locker[] lockers;
	private final LongTermStorage lts = new LongTermStorage();


	/**
	 * constructor of Spaceship
	 * @param name: string as the name of the spaceship
	 * @param crewIDs: int array of IDs of the spaceship crew members
	 * @param numOfLockers: int of the maximum number of lockers possible in the spaceship
	 * @param constraints: array of size-2 arrays of all the constraints between the items
	 */
	public Spaceship(String name, int[] crewIDs, int numOfLockers, Item[][] constraints) {
		this.name = name;
		this.crewIDs = crewIDs;
		this.numOfLockers = numOfLockers;
		this.constraints = constraints;
		this.current_numOfLockers = 0;
		this.lockers = new Locker[numOfLockers];
	}


	/**
	 * return the Long-Term storage of the ship
	 * @return LTS of the ship
	 */
	public LongTermStorage getLongTermStorage() {
		return lts;
	}

	/**
	 * create a new Locker to the ship if all the conditions are valid
	 * @param crewID: int of the crew member which the locker will belong
	 * @param capacity: max capacity of the new locker
	 * @return (- 1) if id doesnt exist in the ship, (-2) if capacity is not positive, (-3) if there is not
	 * 		more space for new lockers, 0	if succeeded to create the locker
	 */
	public int createLocker(int crewID, int capacity) {
		boolean idExist = false;
		for (int id : crewIDs) {
			if (id == crewID) {
				idExist = true;
				break;
			}
		}
		if (!idExist) {    // id is not in crewsIDs
			return VALUE_MINUS_ONE;
		}

		if (capacity <= 0) {    // invalid capacity
			return VALUE_MINUS_TWO;
		}

		if (current_numOfLockers == numOfLockers) { // reached max lockers possible
			return VALUE_MINUS_THREE;
		}
		// ok to create :)
		lockers[current_numOfLockers] = new Locker(lts, capacity, constraints);
		current_numOfLockers++;
		return VALUE_ZERO;
	}


	/**
	 * returns an array with the crew's ids
	 * @return int array of the crew's ids
	 */
	public int[] getCrewIDs() {
		return crewIDs;
	}


	/**
	 * returns an array of the lockers in the ship
	 * @return Locker array of the locker in the ship
	 */
	public Locker[] getLockers() {
		return lockers;
	}
}
