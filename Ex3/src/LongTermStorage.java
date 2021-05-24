import oop.ex3.spaceship.Item;

import java.util.TreeMap;


/**
 * Implementation of a Long-Term storage class
 */
public class LongTermStorage extends Storage {

	/** total storage unit capacity of long-term storage */
	private static final int TOTAL_LTS_CAPACITY = 1000;


	/**
	 * constructor of LongTermStorage
	 */
	public LongTermStorage() {
		this.capacity = TOTAL_LTS_CAPACITY;
		this.available_capacity = TOTAL_LTS_CAPACITY;
	}


	/**
	 * try to add n items of the given item to the storage
	 * @param item: type of item to add
	 * @param n: number of items to add
	 * @return: 0 if addition was successful (-1) if there is not enough space in storage
	 */
	public int addItem(Item item, int n) {
		if ((n < 0) || (item == null)) { // invalid input
			System.out.println(MESSAGE_GENERAL_ERROR);
			return VALUE_MINUS_ONE;
		}
		if (available_capacity < (item.getVolume() * n)) {
			System.out.println(MESSAGE_NO_ROOM_BEGIN + n + MESSAGE_ITEMS_OF_TYPE + item.getType());
			return VALUE_MINUS_ONE;
		}
		if (n > 0) { // add just if amount is positive
			if (itemsCount.containsKey(item.getType())) { // item exist in itemsCount
				itemsCount.put(item.getType(), itemsCount.get(item.getType()) + n);
			} else {    // item does not exist in itemsCount
				itemsCount.put(item.getType(), n);
			}
			available_capacity -= (n * item.getVolume());
		}
		return VALUE_ZERO;
	}


	/**
	 * reset and delete all the items in the LTS
	 */
	public void resetInventory() {
		available_capacity = TOTAL_LTS_CAPACITY;
		itemsCount = new TreeMap<String, Integer>();
	}
}
