import oop.ex3.spaceship.Item;
import oop.ex3.spaceship.ItemFactory;
import org.junit.*;

import java.util.Map;
import java.util.TreeMap;

import static org.junit.Assert.*;

public class LongTermTest {

	/** symbols an expected value of zero */
	private static final int EXPECTED_ZERO = 0;

	/** symbols an expected value of four */
	private static final int EXPECTED_FOUR = 4;

	/** symbols an expected value of nine hundred ninety */
	private static final int EXPECTED_NINE_HUNDRED_NINETY = 990;

	/** symbols an expected value of minus one */
	private static final int EXPECTED_MINUS_ONE = (-1);

	/** total storage unit capacity of long-term storage */
	private static final int TOTAL_LTS_CAPACITY = 1000;

	/** representing a football string */
	private static final String FOOTBALL = "football";

	/** representing a baseball bat string */
	private static final String BASEBALL_BAT = "baseball bat";


	private LongTermStorage lts = new LongTermStorage();
	private LongTermStorage lts2 = new LongTermStorage();

	static private final Item footballItem = ItemFactory.createSingleItem(FOOTBALL);
	static private final Item baseballBatItem = ItemFactory.createSingleItem(BASEBALL_BAT);


	/**
	 * reset the LTS after each test to have no items
	 */
	@Before
	public void reset() {
		lts = new LongTermStorage();
		lts2 = new LongTermStorage();
	}


	/**
	 * test constructor function
	 */
	@Test
	public void constructorTest() {
		assertNotNull(lts);
		assertNotNull(lts2);
	}


	/**
	 * test getCapacity function
	 */
	@Test
	public void getCapacityTest() {
		assertEquals(TOTAL_LTS_CAPACITY, lts.getCapacity());
	}


	/**
	 * test getAvailableCapacity function
	 */
	@Test
	public void getAvailableCapacityTest() {
		lts.addItem(baseballBatItem, 2);    // space taken = 4
		assertEquals(lts.getCapacity() - (baseballBatItem.getVolume() * 2),
					 lts.getAvailableCapacity());
	}


	/**
	 * test getInventory function
	 */
	@Test
	public void getInventoryTest() {
		Map<String, Integer> expectedMap = new TreeMap<String, Integer>();
		expectedMap.put(BASEBALL_BAT, 3);
		expectedMap.put(FOOTBALL, 1);

		lts.addItem(ItemFactory.createSingleItem(BASEBALL_BAT), 3);
		lts.addItem(ItemFactory.createSingleItem(FOOTBALL), 1);
		assertEquals(expectedMap, lts.getInventory());
	}


	/**
	 * test getItemCount function
	 */
	@Test
	public void getItemCountTest() {
		assertEquals(EXPECTED_MINUS_ONE, lts.getItemCount(null));
		lts.addItem(baseballBatItem, 4);
		assertEquals(EXPECTED_ZERO, lts.getItemCount(FOOTBALL));
		assertEquals(EXPECTED_FOUR, lts.getItemCount(BASEBALL_BAT));
	}


	/**
	 * test resetInventory function
	 */
	@Test
	public void resetInventoryTest() {
		lts.addItem(baseballBatItem, 4);
		lts.addItem(footballItem, 3);
		lts.resetInventory();
		assertEquals(TOTAL_LTS_CAPACITY, lts.getAvailableCapacity());
		assertTrue(lts.getInventory().isEmpty());
		Map<String, Integer> expectedMap = new TreeMap<String, Integer>();
		assertEquals(expectedMap, lts.getInventory());
	}


	/**
	 * test addItem function
	 */
	@Test
	public void addItemTest() {

		// --------------------- expected 0 ---------------------
		assertEquals(EXPECTED_ZERO, lts.addItem(baseballBatItem, 0));
		assertEquals(EXPECTED_ZERO, lts.addItem(baseballBatItem, 2));
		assertEquals(EXPECTED_ZERO, lts.addItem(baseballBatItem, 3));
		assertEquals(EXPECTED_NINE_HUNDRED_NINETY, lts.getAvailableCapacity());

		assertEquals(EXPECTED_ZERO, lts2.addItem(baseballBatItem, 500));
		assertEquals(EXPECTED_ZERO, lts2.getAvailableCapacity());

		// --------------------- expected -1 ---------------------
		assertEquals(EXPECTED_MINUS_ONE, lts.addItem(null, 1)); // null item
		assertEquals(EXPECTED_MINUS_ONE, lts.addItem(baseballBatItem, -1)); // not valid n

		// total volume of items is bigger than capacity
		assertEquals(EXPECTED_MINUS_ONE, lts.addItem(footballItem, 300));

		// adding item is with volume smaller than capacity but not enough space in locker
		assertEquals(EXPECTED_MINUS_ONE, lts2.addItem(footballItem, 1));
	}


}
