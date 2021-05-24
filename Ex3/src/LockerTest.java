import oop.ex3.spaceship.Item;
import oop.ex3.spaceship.ItemFactory;
import org.junit.*;

import java.util.Map;
import java.util.TreeMap;

import static org.junit.Assert.*;


public class LockerTest {

	/** symbols an expected value of zero */
	private static final int EXPECTED_ZERO = 0;

	/** symbols an expected value of minus one */
	private static final int EXPECTED_MINUS_ONE = (-1);

	/** symbols an expected value of minus two */
	private static final int EXPECTED_MINUS_TWO = (-2);

	/** symbols an expected value of one */
	private static final int EXPECTED_ONE = 1;

	/** symbols 20% */
	private static final double TWENTY_PERCENT = 0.2;

	/** symbols an expected value of six */
	private static final int EXPECTED_SIX = 6;

	/** symbols an expected value of four */
	private static final int EXPECTED_FOUR = 4;

	/** representing a football string */
	private static final String FOOTBALL = "football";

	/** representing a baseball bat string */
	private static final String BASEBALL_BAT = "baseball bat";

	/** representing a spores engine string */
	private static final String SPORES_ENGINE = "spores engine";

	/** representing size of 20 capacity */
	private static final int CAPACITY_20 = 20;

	/** representing size of 10 capacity */
	private static final int CAPACITY_10 = 10;

	/** representing size of 5 capacity */
	private static final int CAPACITY_5 = 5;


	private LongTermStorage lts_20 = new LongTermStorage();
	private LongTermStorage lts_10 = new LongTermStorage();
	private LongTermStorage lts_5 = new LongTermStorage();

	Item[][] constraints =  ItemFactory.getConstraintPairs();

	private Locker locker_capacity_20 = new Locker(lts_20, CAPACITY_20, constraints);
	private Locker locker_capacity_10 = new Locker(lts_10, CAPACITY_10, constraints);
	private Locker locker_capacity_5 = new Locker(lts_5, CAPACITY_5, constraints);

	static Item footballItem = ItemFactory.createSingleItem(FOOTBALL);
	static Item baseballBatItem = ItemFactory.createSingleItem(BASEBALL_BAT);
	static Item sporesEngineItem = ItemFactory.createSingleItem(SPORES_ENGINE);




	/**
	 * reset the lockers after each test to have no items
	 */
	@Before
	public void reset() {
		lts_20 = new LongTermStorage();
		lts_10 = new LongTermStorage();
		lts_5 = new LongTermStorage();
		locker_capacity_20 = new Locker(lts_20, CAPACITY_20, constraints);
		locker_capacity_10 = new Locker(lts_10, CAPACITY_10, constraints);
		locker_capacity_5 = new Locker(lts_5, CAPACITY_5, constraints);
	}


	/**
	 * test constructor function
	 */
	@Test
	public void constructorTest() {
		assertNotNull(locker_capacity_5);
		assertNotNull(locker_capacity_10);
		assertNotNull(locker_capacity_20);
	}


	/**
	 * test getCapacity function
	 */
	@Test
	public void getCapacityTest() {
		assertEquals(10, locker_capacity_10.getCapacity());
		assertEquals(5, locker_capacity_5.getCapacity());
	}

	/**
	 * test getAvailableCapacity function
	 */
	@Test
	public void getAvailableCapacityTest() {
		locker_capacity_10.addItem(baseballBatItem, 2);    // space taken = 4
		locker_capacity_20.addItem(footballItem, 2);    // space taken = 8
		assertEquals(locker_capacity_10.getCapacity() - (baseballBatItem.getVolume() * 2),
					 locker_capacity_10.getAvailableCapacity());
		assertEquals(locker_capacity_20.getCapacity() - (footballItem.getVolume() * 2),
					 locker_capacity_20.getAvailableCapacity());
	}


	/**
	 * test getInventory function
	 */
	@Test
	public void getInventoryTest() {
		Map<String, Integer> expectedMap = new TreeMap<String, Integer>();
		expectedMap.put(BASEBALL_BAT, 3);
		expectedMap.put(SPORES_ENGINE, 1);

		locker_capacity_20.addItem(baseballBatItem, 3);
		locker_capacity_20.addItem(sporesEngineItem, 1);
		assertEquals(expectedMap, locker_capacity_20.getInventory());
	}


	/**
	 * test getItemCount function
	 */
	@Test
	public void getItemCountTest() {
		assertEquals(EXPECTED_MINUS_ONE, locker_capacity_20.getItemCount(null));
		locker_capacity_20.addItem(baseballBatItem, 4);
		assertEquals(EXPECTED_ZERO, locker_capacity_20.getItemCount(FOOTBALL));
		assertEquals(EXPECTED_FOUR, locker_capacity_20.getItemCount(BASEBALL_BAT));
	}


	/**
	 * test removeItem function
	 */
	@Test
	public void removeItemTest() {
		// null item
		assertEquals(EXPECTED_MINUS_ONE, locker_capacity_20.removeItem(null, 1));

		// locker doesnt contain the item
		assertEquals(EXPECTED_MINUS_ONE, locker_capacity_20.removeItem(footballItem, 1));

		// n is negative
		assertEquals(EXPECTED_MINUS_ONE, locker_capacity_20.removeItem(footballItem, -1));

		// n is bigger the amount of item in the locker
		locker_capacity_20.addItem(footballItem, 1);
		assertEquals(EXPECTED_MINUS_ONE, locker_capacity_20.removeItem(footballItem, 2));

		// locker doesnt contain the item and we try to remove zero amount of it
		assertEquals(EXPECTED_ZERO, locker_capacity_20.removeItem(baseballBatItem, 0));

		// n is equal to amount of item in the locker
		assertEquals(EXPECTED_ZERO, locker_capacity_20.removeItem(footballItem, 1));
		assertEquals(locker_capacity_20.getCapacity(), locker_capacity_20.getAvailableCapacity());

		// n is less than the amount of item in the locker
		locker_capacity_20.addItem(footballItem, 2);
		assertEquals(EXPECTED_ZERO, locker_capacity_20.removeItem(footballItem, 1));
		assertEquals(locker_capacity_20.getCapacity() - footballItem.getVolume(),
					 locker_capacity_20.getAvailableCapacity());
	}


	/**
	 * test addItem function
	 */
	@Test
	public void addItemTest() {

		// --------------------- expected 0 ---------------------
		assertEquals(EXPECTED_ZERO, locker_capacity_10.addItem(baseballBatItem, 0));
		assertEquals(EXPECTED_ZERO, locker_capacity_10.addItem(baseballBatItem, 1)); //space taken (10) = 2
		assertEquals(EXPECTED_ZERO, locker_capacity_10.addItem(baseballBatItem, 1)); //space taken (10) = 4
		// check for correct available capacity
		assertEquals(EXPECTED_SIX, locker_capacity_10.getAvailableCapacity());


		// --------------------- expected 1 ---------------------
		//				   take more than 50% space

		// can keep all 20% in locker and there's enough room in LTS
		assertEquals(EXPECTED_ONE, locker_capacity_10.addItem(baseballBatItem, 1));
		// check if added up to 20% of item to locker
		assertTrue((locker_capacity_10.getItemCount(BASEBALL_BAT) * baseballBatItem.getVolume()) <=
				   (TWENTY_PERCENT * locker_capacity_10.getCapacity()));

		// cannot keep all 20% in locker and enough room in LTS
		assertEquals(EXPECTED_ONE, locker_capacity_5.addItem(footballItem, 1));
		// check if added up to 20% of item to locker
		assertTrue((locker_capacity_5.getItemCount(FOOTBALL) * footballItem.getVolume()) <=
				   (TWENTY_PERCENT * locker_capacity_5.getCapacity()));

		// --------------------- expected -1 ---------------------
		assertEquals(EXPECTED_MINUS_ONE, locker_capacity_10.addItem(null, 1)); // null item
		assertEquals(EXPECTED_MINUS_ONE, locker_capacity_10.addItem(baseballBatItem, -1)); // not valid n

		// total volume of items is bigger than capacity
		assertEquals(EXPECTED_MINUS_ONE, locker_capacity_20.addItem(sporesEngineItem, 3));

		// adding item is with volume smaller than capacity but not enough space in locker
		assertEquals(EXPECTED_MINUS_ONE, locker_capacity_5.addItem(footballItem, 2));

		lts_20.resetInventory();
		lts_20.addItem(baseballBatItem, 500);    // LTS is full
		Item[][] constrainsWithSameItem = {{footballItem,baseballBatItem}, {sporesEngineItem,
				sporesEngineItem}};
		locker_capacity_20 = new Locker(lts_20, CAPACITY_20, constrainsWithSameItem);
		// not enough space in long-term storage
		assertEquals(EXPECTED_MINUS_ONE, locker_capacity_20.addItem(footballItem, 3)); //take more than 50%

		// --------------------- expected -2 ---------------------

		locker_capacity_20.addItem(footballItem, 1);
		// check constraints with already stored items
		assertEquals(EXPECTED_MINUS_TWO, locker_capacity_20.addItem(baseballBatItem, 1));

		locker_capacity_20.addItem(sporesEngineItem, 1);
		// check constraints of same item == (item_x, item_x)
		assertEquals(EXPECTED_MINUS_TWO, locker_capacity_20.addItem(sporesEngineItem, 1));
	}
}
