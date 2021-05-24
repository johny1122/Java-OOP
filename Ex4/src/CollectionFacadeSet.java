import java.util.Collection;


/**
 * Wraps an underlying Collection and serves to both simplify its API and give it a common type with the
 * implemented SimpleHashSets.
 */
public class CollectionFacadeSet implements SimpleSet {

	Collection<String> collection;

	/**
	 * constructor of the class
	 */
	CollectionFacadeSet(Collection<String> collection) {
		this.collection = collection;
	}


	/**
	 * Adds the specified element to this set if it is not already present.
	 * @param newValue New value to add to the set
	 * @return true if this set did not already contain the specified
	 */
	@Override
	public boolean add(String newValue) {
		if (collection.contains(newValue)) {
			return false;
		}
		return collection.add(newValue);
	}


	/**
	 * Returns true if this set contains the specified element.
	 * @param searchVal Value to search for
	 * @return true if this set contains the specified element
	 */
	@Override
	public boolean contains(String searchVal) { return collection.contains(searchVal); }


	/**
	 * Removes the specified element from this set if it is present.
	 * @param toDelete Value to delete
	 * @return true if the set contained the specified element
	 */
	@Override
	public boolean delete(String toDelete) { return collection.remove(toDelete); }


	/**
	 * @return the number of elements in this set.
	 */
	@Override
	public int size() { return collection.size(); }
}
