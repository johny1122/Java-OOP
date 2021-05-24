import java.util.LinkedList;


/**
 * Wrapper class of a LinkedList<String>
 */
public class SingleLinkedList extends LinkedList<String> {

	LinkedList<String> list;

	/**
	 * constructor of the class
	 */
	SingleLinkedList(){
		list = new LinkedList<String>();
	}


	/**
	 * Inserts the specified element at the beginning of this list.
	 *
	 * @param newValue the string to add
	 */
	public void addValue(String newValue){
		list.addFirst(newValue);
	}


	/**
	 * Returns true iff this list contains at least one element of the given string
	 *
	 * @param searchVal string whose presence in this list is to be tested
	 * @return true if this list contains the specified element and false otherwise
	 */
	public boolean contains(String searchVal){
		return list.contains(searchVal);
	}


	/**
	 * Removes the element with the lowest index of the element if such an element exists
	 *
	 * @param toDelete string to be removed from this list, if present
	 */
	public void remove(String toDelete){
		list.remove(toDelete);
	}


	/**
	 * @return the linked list of this instance
	 */
	public LinkedList<String> getList(){ return list; }
}
