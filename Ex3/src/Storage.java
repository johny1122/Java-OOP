import oop.ex3.spaceship.Item;
import oop.ex3.spaceship.ItemFactory;

import java.util.Map;
import java.util.TreeMap;

/**
 * Implementation of a genetic storage class
 */
public abstract class Storage{

	/** general error message */
	protected static final String MESSAGE_GENERAL_ERROR = "Error: Your request cannot be completed at this " +
														"time";

	/** error message of not enough available room - beginning*/
	protected static final String MESSAGE_NO_ROOM_BEGIN = "Error: Your request cannot be completed at this " +
														"time. Problem: no room for " ;

	/** end of error message with types of item*/
	protected static final String MESSAGE_ITEMS_OF_TYPE = " items of type ";

	/** symbols a return function value of zero */
	protected static final int VALUE_ZERO = 0;

	/** symbols a return function value of minus one */
	protected static final int VALUE_MINUS_ONE = (-1);

	/** symbols a return function value of one */
	protected static final int VALUE_ONE = 1;

	/** symbols 20% */
	protected static final double TWENTY_PERCENT = 0.2;

	/** symbols 50% */
	protected static final double FIFTY_PERCENT = 0.5;


	protected int capacity;
	protected int available_capacity;
	protected Map<String, Integer> itemsCount = new TreeMap<String, Integer>();


	/**
	 * This method returns the number of items of the given type the locker contains
	 */
	public int getItemCount(String type){
		if (type == null){
			System.out.println(MESSAGE_GENERAL_ERROR);
			return VALUE_MINUS_ONE;
		}
		if ((itemsCount.isEmpty()) || (!itemsCount.containsKey(type))){
			return 0;
		}
		return itemsCount.get(type);}


	/**
	 * This method returns the number of items of the given type the locker contains
	 */
	public Map<String, Integer> getInventory(){ return itemsCount;}


	/**
	 * This method returns the locker's total capacity
	 */
	public int getCapacity(){ return capacity;}


	/**
	 * This method returns the locker's available capacity
	 */
	public int getAvailableCapacity(){ return available_capacity;}


	/**
	 * Add item\items to the storage
	 * @param item: 	type of item to add
	 * @param n: 		number of items to add
	 * @return 0/1/(-1) according to the specific storage API
	 */
	abstract int addItem(Item item, int n);
}
