
/**
 * implementation of ClosedHashSet class
 */
public class ClosedHashSet extends SimpleHashSet {

	private Object[] hashSet;

	/**
	 * inner private class representing a deleted element in the hashset
	 */
	private static class Deleted<String> {
		String value;

		Deleted(String value) {
			this.value = value;
		}
	}

	// ----------------------- Constructors -----------------------
	/**
	 * A default constructor. Constructs a new, empty table with default initial capacity (16), upper load
	 * factor (0.75) and lower load factor (0.25).
	 */
	public ClosedHashSet() {
		hashSet = new Object[capacity];
	}


	/**
	 * Constructs a new, empty table with the specified load factors, and the default initial capacity (16).
	 * @param upperLoadFactor The upper load factor of the hash table.
	 * @param lowerLoadFactor The lower load factor of the hash table.
	 */
	public ClosedHashSet(float upperLoadFactor, float lowerLoadFactor) {
		this.upperLoadFactor = upperLoadFactor;
		this.lowerLoadFactor = lowerLoadFactor;
		hashSet = new Object[capacity];
	}


	/**
	 * Data constructor - builds the hash set by adding the elements one by one. Duplicate values should be
	 * ignored. The new table has the default values of initial capacity (16), upper load factor (0.75), and
	 * lower load factor (0.25).
	 * @param data Values to add to the set.
	 */
	public ClosedHashSet(java.lang.String[] data) {
		hashSet = new Object[capacity];

		// add is also checking if the string is already exists in the hashset
		for (String str : data) {
			add(str);
		}
	}


	// ----------------------- Methods -----------------------
	/**
	 * calculate the index of the given string with string's hashcode and bitWise-and
	 * @param str: given String
	 * @param i: current index of the counter i
	 * @return the correct index in the hashset
	 */
	private int calculateIndex(String str, int i) {
		return (str.hashCode() + ((i + (i * i)) / 2)) & (capacity - 1);
	}


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

		Object[] newHashSet = new Object[capacity];
		int currIndex;
		for (Object element : hashSet) {
			if (element instanceof String) {
				for (int i = 0; i < capacity; i++) {
					currIndex = calculateIndex((String) element, i);
					if (newHashSet[currIndex] == null) {
						newHashSet[currIndex] = element;
						break;
					}
				}
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
		if ((newValue == null) || (contains(newValue))) {
			return false;
		}

		numOfElements++;
		if (loadFactor() > upperLoadFactor) {
			resize(INCREASE_SIZE);
		}

		int currIndex;
		for (int i = 0; i < capacity; i++) {
			currIndex = calculateIndex(newValue, i);
			if ((hashSet[currIndex] == null) || (hashSet[currIndex] instanceof Deleted)) {
				hashSet[currIndex] = newValue;
				break;
			}
		}
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

		int currIndex;
		for (int i = 0; i < capacity; i++) {
			currIndex = calculateIndex(searchVal, i);

			if (hashSet[currIndex] == null) {
				return false;
			}
			// found Deleted with same value
			if ((hashSet[currIndex] instanceof Deleted) &&
				(hashSet[currIndex].toString().equals(searchVal))) {
				return false;
			}
			// found String with same value
			if ((hashSet[currIndex] instanceof String) && (hashSet[currIndex].equals(searchVal))) {
				return true;
			}
		}
		return false;
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

		int currIndex;
		for (int i = 0; i < capacity; i++) {
			currIndex = calculateIndex(toDelete, i);
			if (hashSet[currIndex] instanceof String) {
				if (((hashSet[currIndex]) != null) &&
					(((hashSet[currIndex])).equals(toDelete))) {
					hashSet[currIndex] = new Deleted<Object>(hashSet[currIndex]);
					break;
				}
			}
		}

		numOfElements--;
		if (loadFactor() < lowerLoadFactor) {
			resize(DECREASE_SIZE);
		}
		return true;
	}

}
