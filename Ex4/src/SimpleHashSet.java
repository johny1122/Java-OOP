
/**
 * abstract class implementing SimpleSet Just the size method is being implemented and the other methods are
 * implemented in subclasses to their use
 */
abstract class SimpleHashSet implements SimpleSet {

	/** no elements in the HashSet */
	protected static final int NO_ELEMENTS = 0;

	/** default initial capacity of the HashSet */
	protected static final int DEFAULT_INIT_CAPACITY = 16;

	/** default initial upperLoadFactor of the HashSet */
	protected static final float DEFAULT_INIT_UPPER_LOAD_FACTOR = 0.75f;

	/** default initial lowerLoadFactor of the HashSet */
	protected static final float DEFAULT_INIT_LOWER_LOAD_FACTOR = 0.25f;

	/** representing an increase size */
	protected static final int INCREASE_SIZE = 1;

	/** representing a decrease size */
	protected static final int DECREASE_SIZE = (-1);


	protected int numOfElements;
	protected int capacity;
	protected float upperLoadFactor;
	protected float lowerLoadFactor;

	/**
	 * constructor of SimpleHashSet
	 */
	SimpleHashSet() {
		numOfElements = NO_ELEMENTS;
		capacity = DEFAULT_INIT_CAPACITY;
		upperLoadFactor = DEFAULT_INIT_UPPER_LOAD_FACTOR;
		lowerLoadFactor = DEFAULT_INIT_LOWER_LOAD_FACTOR;
	}


	/**
	 * @return The number of elements currently in the set
	 */
	@Override
	public int size() { return numOfElements; }


	/**
	 * @return The current capacity (number of cells) of the table.
	 */
	public int capacity() { return capacity; }


	/**
	 * @return the load factor - current number of elements in the hash divided by the capacity
	 */
	protected float loadFactor() { return ((float) numOfElements / (float) capacity);}


	/**
	 * resizing the hash according to the given type type=1 : increase , type=(-1): decrease
	 * @param type: int of which resize type to apply
	 */
	abstract void resize(int type);

}
