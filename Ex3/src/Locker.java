import oop.ex3.spaceship.Item;


/**
 * Locker implementation class
 */
public class Locker extends Storage {

	/** items moved to LTS message */
	private static final String MESSAGE_ITEMS_MOVED_TO_LTS = "Warning: Action successful, but has caused " +
															 "items to be moved to storage";

	/** tried to remove a negative number of items message */
	private static final String MESSAGE_REMOVE_NEGATIVE_NUMBER = "Error: Your request cannot be completed " +
																 "at this time. Problem: cannot remove a " +
																 "negative number of items of type ";

	/** error message of not containing enough items to remove - beginning */
	private static final String MESSAGE_REMOVE_LESS_THAN_CONTAIN_BEGIN = "Error: Your request cannot be " +
																		 "completed at this time. Problem:" +
																		 " the locker does not contain ";

	/** error message of found constraint - begin */
	private static final String MESSAGE_FOUND_CONSTRAINT_BEGIN = "Error: Your request cannot be completed " +
																 "at this time. Problem: the locker cannot" +
																 " contain items of type ";

	/** error message of found constraint - end */
	private static final String MESSAGE_FOUND_CONSTRAINT_END = ", as it contains a contradiction item";

	/** symbols a return function value of minus two */
	private static final int VALUE_MINUS_TWO = (-2);

	/** symbols a no amount of item in locker */
	private static final int ZERO_AMOUNT = 0;


	private LongTermStorage lts = null;
	private Item[][] constraints = null;


	/**
	 * constructor of Locker
	 * @param lts: LongTermStorage of the spaceship
	 * @param capacity: total capacity of this locker
	 * @param constraints: array of size-2 arrays of all the constraints between the items
	 */
	public Locker(LongTermStorage lts, int capacity, Item[][] constraints) {
		this.lts = lts;
		this.constraints = constraints;
		this.capacity = capacity;
		this.available_capacity = capacity;
	}


	/**
	 * trying to remove items from the locker.
	 * @param item: type of item to remove
	 * @param n: number of the given item to remove
	 * @return: (- 1) if n is negative or n is bigger the actual amount 0 if remove succeeded
	 */
	public int removeItem(Item item, int n) {
		if (item == null){
			System.out.println(MESSAGE_GENERAL_ERROR);
			return VALUE_MINUS_ONE;
		}
		// n is negative
		else if (n < 0) {
			System.out.println(MESSAGE_REMOVE_NEGATIVE_NUMBER + item.getType());
			return VALUE_MINUS_ONE;
		}
		// n is bigger the amount of item in the locker
		else if (getItemCount(item.getType()) < n) {
			System.out.println(
					MESSAGE_REMOVE_LESS_THAN_CONTAIN_BEGIN + n + MESSAGE_ITEMS_OF_TYPE + item.getType());
			return VALUE_MINUS_ONE;
		}
		// locker doesnt contain the item
		if (!itemsCount.containsKey(item.getType())){
			return VALUE_ZERO;
		}
		// n is equal to amount of item in the locker
		if (n == itemsCount.get(item.getType())) {
			itemsCount.remove(item.getType());
		} else {    // n is less than the amount of item in the locker
			itemsCount.put(item.getType(), itemsCount.get(item.getType()) - n);
		}
		available_capacity += (n * item.getVolume());
		return VALUE_ZERO;
	}


	/**
	 * check if the given item take more the 50% of locker's space
	 * @param item: given item
	 * @param amountToAdd: number of items trying to add
	 * @return true if more the 50% and false otherwise
	 */
	private boolean moreThan50(Item item, int amountToAdd) {
		int total_volume_of_type;
		total_volume_of_type = (getItemCount(item.getType()) + amountToAdd) * item.getVolume();
		return total_volume_of_type > (FIFTY_PERCENT * capacity);
	}


	/**
	 * check if locker can keep all 20% of the item or not. if the locker can the method calls the
	 * addItemsToLockerAndLTS to check if there is enough space in LTS. If the LTS does have, the method
	 * will add the correct amount to the locker and the LTS.
	 * @param item: given item
	 * @param amountToAdd: given amount to move to add to locker
	 * @return true if there is enough space: and do the addition false if there isn't enough space
	 */
	private boolean tryToAddToLTS(Item item, int amountToAdd) {
		// current amount + amount to add
		int total_item_amount = amountToAdd + getItemCount(item.getType());
		// (20% of total locker capacity) / (item's volume)
		int max_items_number_can_keep_in_locker = (int) (TWENTY_PERCENT * capacity) / item.getVolume();
		int min_number_of_items_to_move_to_LTS = total_item_amount - max_items_number_can_keep_in_locker;

		return addItemsToLockerAndLTS(item, max_items_number_can_keep_in_locker,
									  min_number_of_items_to_move_to_LTS);
	}


	/**
	 * check if there is enough space in LTS. If the LTS does have, the method will add the correct amount
	 * to the locker and the LTS.
	 * @param item: item to add
	 * @param number_of_items_can_keep_in_locker: number of items to add to locker
	 * @param number_of_items_must_move_to_LTS: number of items to add to LTS
	 * @return true is enough room in LTS and false otherwise.
	 */
	private boolean addItemsToLockerAndLTS(Item item, int number_of_items_can_keep_in_locker,
										   int number_of_items_must_move_to_LTS) {

		if (lts.addItem(item, number_of_items_must_move_to_LTS) == VALUE_ZERO) {
			if (itemsCount.containsKey(item.getType())) { // item exist in itemsCount
				// remove old item amount in locker
				available_capacity += item.getVolume() * itemsCount.get(item.getType());
			}
			// update to the new amount in locker
			itemsCount.put(item.getType(), number_of_items_can_keep_in_locker);
			// update the available capacity
			available_capacity -= item.getVolume() * number_of_items_can_keep_in_locker;
			return true;
		}
		return false;
	}


	/**
	 * trying to add the given amount of the given item to the locker's space
	 * @param item: given item
	 * @param n: amount of items to add
	 * @return -1 	if there is not enough room in the locker or LTS, or n is a negative number 1 	if some
	 * 		items have to move to LTS and there is place for them 0	if succeeded to add and no items have
	 * 		been
	 * 		moved to LTS
	 */
	public int addItem(Item item, int n) {
		if ((item == null) || (item.getType() == null)) {
			System.out.println(MESSAGE_GENERAL_ERROR);
			return VALUE_MINUS_ONE;
		}

		boolean foundConstraint = false;

		for (String storedItem : itemsCount.keySet()) {
			for (Item[] constraint : constraints) {
				if ((constraint[0].getType().equals(storedItem) &&
					 constraint[1].getType().equals(item.getType()))
					||
					(constraint[0].getType().equals(item.getType()) &&
					 constraint[1].getType().equals(storedItem))) {
						foundConstraint = true;
				}
			}
		}
		if (foundConstraint) {
			System.out
					.println(MESSAGE_FOUND_CONSTRAINT_BEGIN + item.getType() + MESSAGE_FOUND_CONSTRAINT_END);
			return VALUE_MINUS_TWO;
		}

		if (n < 0) { // n is negative
			System.out.println(MESSAGE_GENERAL_ERROR);
			return VALUE_MINUS_ONE;

		}
		//not enough space in locker for all n items
		else if (available_capacity < (item.getVolume() * n)) {
			System.out.println(MESSAGE_NO_ROOM_BEGIN + n + MESSAGE_ITEMS_OF_TYPE + item.getType());
			return VALUE_MINUS_ONE;
		}

		if (moreThan50(item, n)) { // space of item will be more than 50%
			if (tryToAddToLTS(item, n)) {    // there is enough space in LTS
				System.out.println(MESSAGE_ITEMS_MOVED_TO_LTS);
				return VALUE_ONE;
			}
			// not enough space in LTS
			// (The printout is already printed in the addItem of LTS)
			return VALUE_MINUS_ONE;
		}

		if (n > 0) {    // add items just if n positive
			if (itemsCount.containsKey(item.getType())) { // item exist in itemsCount
				itemsCount.put(item.getType(), itemsCount.get(item.getType()) + n);
			} else {    // item does not exist in itemsCount
				itemsCount.put(item.getType(), n);
			}
			available_capacity -= (n * item.getVolume());
		}
		return VALUE_ZERO;
	}


}
