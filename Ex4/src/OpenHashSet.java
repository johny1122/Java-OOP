
/**
 * implementation of OpenHashSet class
 */
public class OpenHashSet extends SimpleHashSet {

	private SingleLinkedList[] hashSet;

	// ----------------------- Constructors -----------------------
	/**
	 * A default constructor. Constructs a new, empty table with default initial capacity (16), upper load
	 * factor (0.75) and lower load factor (0.25).
	 */
	public OpenHashSet() {
		hashSet = new SingleLinkedList[capacity];
		for (int i = 0; i < capacity; i++) {
			hashSet[i] = new SingleLinkedList();
		}
	}


	/**
	 * Constructs a new, empty table with the specified load factors, and the default initial capacity (16).
	 * @param upperLoadFactor: The upper load factor of the hash table.
	 * @param lowerLoadFactor: The lower load factor of the hash table.
	 */
	public OpenHashSet(float upperLoadFactor, float lowerLoadFactor) {
		this.upperLoadFactor = upperLoadFactor;
		this.lowerLoadFactor = lowerLoadFactor;
		hashSet = new SingleLinkedList[capacity];
		for (int i = 0; i < capacity; i++) {
			hashSet[i] = new SingleLinkedList();
		}
	}


	/**
	 * Data constructor - builds the hash set by adding the elements one by one. Duplicate values should be
	 * ignored. The new table has the default values of initial capacity (16), upper load factor (0.75), and
	 * lower load factor (0.25).
	 * @param data: Values to add to the set.
	 */
	public OpenHashSet(java.lang.String[] data) {
		hashSet = new SingleLinkedList[capacity];
		for (int i = 0; i < capacity; i++) {
			hashSet[i] = new SingleLinkedList();
		}
		// add is also checking if the string is already exists in the hashset
		for (String str : data) {
			add(str);
		}
	}


	// ----------------------- Methods -----------------------
	/**
	 * calculate the index of the given string with string's hashcode and bitWise-and
	 * @param str: given String
	 * @return the correct index in the hashset
	 */
	private int calculateIndex(String str) { return str.hashCode() & (capacity() - 1); }


	/**
	 * resizing the hash according to the given type type=1 : increase , type=(-1): decrease
	 * @param type: int of which resize type to apply
	 */
	@Override
	void resize(int type) {
		if (type == INCREASE_SIZE) {
			capacity *= 2;
		} else if (type == DECREASE_SIZE) {
			capacity /= 2;
		}

		SingleLinkedList[] newHashSet = new SingleLinkedList[capacity];
		// define new SingeLinkedList to each index
		for (int i = 0; i < capacity; i++) {
			newHashSet[i] = new SingleLinkedList();
		}
		// insert each string to the new hashset
		for (SingleLinkedList wrapper : hashSet) {
			for (String element : wrapper.getList()) {
				newHashSet[calculateIndex(element)].addValue(element);
			}
		}
		hashSet = newHashSet;
	}


	/**
	 * Add a specified element to the set if it's not already in it.
	 * @param newValue New value to add to the set
	 * @return False iff newValue already exists in the set
	 */
	@Override
	public boolean add(String newValue) {
		if ((newValue == null) || contains(newValue)) {
			return false;
		}

		numOfElements++;
		if (loadFactor() > upperLoadFactor) {
			resize(INCREASE_SIZE);
		}
		// add the string to the start of the linked-list in the specific index
		hashSet[calculateIndex(newValue)].addValue(newValue);
		return true;
	}


	/**
	 * Look for a specified value in the set.
	 * @param searchVal Value to search for
	 * @return True iff searchVal is found in the set
	 */
	@Override
	public boolean contains(String searchVal) {
		if (searchVal == null) {
			return false;
		}
		return hashSet[calculateIndex(searchVal)].contains(searchVal);
	}


	/**
	 * Remove the input element from the set.
	 * @param toDelete Value to delete
	 * @return True iff toDelete is found and deleted
	 */
	@Override
	public boolean delete(String toDelete) {
		if ((toDelete == null) || (!contains(toDelete))) {
			return false;
		}

		// delete the string from the linked-list in the specific index
		hashSet[calculateIndex(toDelete)].remove(toDelete);
		numOfElements--;
		if (loadFactor() < lowerLoadFactor) {
			resize(DECREASE_SIZE);
		}
		return true;
	}
}
