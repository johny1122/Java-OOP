import oop.ex3.spaceship.Item;
import oop.ex3.spaceship.ItemFactory;
import org.junit.*;

import java.util.Arrays;

import static org.junit.Assert.*;


public class SpaceshipTest {


	/** symbols an expected value of zero */
	private static final int EXPECTED_ZERO = 0;

	/** symbols an expected value of minus one */
	private static final int EXPECTED_MINUS_ONE = (-1);

	/** symbols an expected value of minus two */
	private static final int EXPECTED_MINUS_TWO = (-2);

	/** symbols an expected value of minus three */
	private static final int EXPECTED_MINUS_THREE = (-3);

	/** representing a air-force-one string */
	private static final String AIR_FORCE_ONE = "Air-Force-One";

	/** representing a air-force-two string */
	private static final String AIR_FORCE_TWO = "Air-Force-Two";

	/** representing an array of ints of the crew members */
	private static final int[] CREW_IDS = {123, 456};

	/** representing an ID which is not in the crewID array */
	private static final int INVALID_ID = 789;

	/** total storage unit capacity of long-term storage */
	private static final int TOTAL_LTS_CAPACITY = 1000;

	/** representing size of 5 capacity */
	private static final int CAPACITY_5 = 5;

	/** representing size of 10 capacity */
	private static final int CAPACITY_10 = 10;

	/** representing size of 20 capacity */
	private static final int CAPACITY_20 = 20;

	/** representing size of 0 capacity */
	private static final int CAPACITY_0 = 0;

	/** representing size of (-1) capacity */
	private static final int CAPACITY_MINUS_ONE = (-1);

	/** symbols an expected value of three */
	private static final int EXPECTED_THREE = 3;

	/** symbols an three number of lockers in ship */
	private static final int NUMBER_OF_LOCKERS_THREE = 3;

	/** symbols an one number of lockers in ship */
	private static final int NUMBER_OF_LOCKERS_ONE = 1;

	/** representing a football string */
	private static final String FOOTBALL = "football";


	private final Item[][] constraints =  ItemFactory.getConstraintPairs();
	private static final Item footballItem = ItemFactory.createSingleItem(FOOTBALL);
	private LongTermStorage lts = new LongTermStorage();



	private Spaceship ship = new Spaceship(AIR_FORCE_ONE, CREW_IDS, NUMBER_OF_LOCKERS_THREE, constraints);
	private Spaceship ship2 = new Spaceship(AIR_FORCE_TWO, CREW_IDS, NUMBER_OF_LOCKERS_ONE, constraints);

	/**
	 * reset the LTS after each test to have no items
	 */
	@Before
	public void reset() {
		lts = new LongTermStorage();
		ship = new Spaceship(AIR_FORCE_ONE, CREW_IDS, NUMBER_OF_LOCKERS_THREE, constraints);
		ship2 = new Spaceship(AIR_FORCE_TWO, CREW_IDS, NUMBER_OF_LOCKERS_ONE, constraints);
	}


	/**
	 * test constructor function
	 */
	@Test
	public void constructorTest() {
		assertNotNull(ship);
	}

	/**
	 * test getLongTermStorage function
	 */
	@Test
	public void getLongTermStorageTest() {
		// check when empty
		assertEquals(TOTAL_LTS_CAPACITY, ship.getLongTermStorage().available_capacity);
		assertEquals(lts.getInventory(), ship.getLongTermStorage().getInventory());

		ship.createLocker(CREW_IDS[0], CAPACITY_5);
		ship.getLockers()[0].addItem(footballItem, 1); // insert item which must move to LTS
		lts.addItem(footballItem, 1);

		assertEquals(lts.getInventory(), ship.getLongTermStorage().getInventory());
	}


	/**
	 * test createLocker function
	 */
	@Test
	public void createLockerTest() {
		// --------------------- expected 0 ---------------------
		assertEquals(EXPECTED_ZERO, ship.createLocker(CREW_IDS[0], CAPACITY_10));
		assertEquals(EXPECTED_ZERO, ship2.createLocker(CREW_IDS[0], CAPACITY_10));


		// --------------------- expected -1 ---------------------
		// crewId is not in the ship
		assertEquals(EXPECTED_MINUS_ONE, ship.createLocker(INVALID_ID, CAPACITY_10));

		// --------------------- expected -2 ---------------------
		// capacity is invalid - 0
		assertEquals(EXPECTED_MINUS_TWO, ship.createLocker(CREW_IDS[0], CAPACITY_0));
		// capacity is invalid - (-1)
		assertEquals(EXPECTED_MINUS_TWO, ship.createLocker(CREW_IDS[0], CAPACITY_MINUS_ONE));

		// --------------------- expected -3 ---------------------
		//the ship can has just one locker and it already has
		assertEquals(EXPECTED_MINUS_THREE, ship2.createLocker(CREW_IDS[0], CAPACITY_10));

	}


	/**
	 * test getCrewIDs function
	 */
	@Test
	public void getCrewIDsTest() {
		assertEquals(CREW_IDS, ship.getCrewIDs());
	}


	/**
	 * test getLockers function
	 */
	@Test
	public void getLockersTest() {

		// at the beginning lockers array must be null
		assertNotNull(ship.getLockers());

		// add lockers
		ship.createLocker(CREW_IDS[0], CAPACITY_10);
		ship.createLocker(CREW_IDS[1], CAPACITY_20);

		// length is numOfLockers
		assertEquals(EXPECTED_THREE, ship.getLockers().length);

		boolean capacityFound = false;
		for (Locker locker: ship.getLockers()) {
			if (locker.capacity == CAPACITY_10) {
				capacityFound = true;
				break;
			}
		}
		assertTrue(capacityFound);
		capacityFound = false;
		for (Locker locker: ship.getLockers()) {
			if (locker.capacity == CAPACITY_20) {
				capacityFound = true;
				break;
			}
		}
		assertTrue(capacityFound);

	}
}
