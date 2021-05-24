import java.util.HashSet;
import java.util.LinkedList;
import java.util.TreeSet;


/**
 * implementation of SimpleSetPerformanceAnalyzer class
 */
public class SimpleSetPerformanceAnalyzer {

	/** number of collection to be in the CollectionFacadeSet array */
	static final int NUMBER_OF_COLLECTIONS = 5;

	/** index of OpenHashSet in the CollectionFacadeSet array */
	static final int INDEX_OF_OPEN_HASH = 0;

	/** index of ClosedHashSet in the CollectionFacadeSet array */
	static final int INDEX_OF_CLOSED_HASH = 1;

	/** index of TreeSet in the CollectionFacadeSet array */
	static final int INDEX_OF_TREE_SET = 2;

	/** index of HashSet in the CollectionFacadeSet array */
	static final int INDEX_OF_HASH_SET = 3;

	/** index of LinkedList in the CollectionFacadeSet array */
	static final int INDEX_OF_LINKED_LIST = 4;

	/** path of data1.txt file */
	static final String DATA_1_PATH = "src/data1.txt";

	/** path of data2.txt file */
	static final String DATA_2_PATH = "src/data2.txt";

	/** number of strings in data1.txt file */
	static final int DATA_1_LENGTH = 99999;

	/** number of strings in data2.txt file */
	static final int DATA_2_LENGTH = 99999;

	/** number of iterations to measure */
	static final int ANALYZE_ITERATIONS = 70000;

	/** number of iterations to measure just linkedList */
	static final int LINKED_LIST_ITERATIONS = 7000;

	/** from nanoseconds to milliseconds */
	static final int FROM_NANO_TO_MILLI = 1000000;


	static SimpleSet[] allCollections;
	static String[] data1 = new String[DATA_1_LENGTH];
	static String[] data2 = new String[DATA_2_LENGTH];

	/**
	 * constructor of the class
	 */
	SimpleSetPerformanceAnalyzer() {
		allCollections = new SimpleSet[NUMBER_OF_COLLECTIONS];
		allCollections[INDEX_OF_OPEN_HASH] = new OpenHashSet();
		allCollections[INDEX_OF_CLOSED_HASH] = new ClosedHashSet();
		allCollections[INDEX_OF_TREE_SET] = new CollectionFacadeSet(new TreeSet<String>());
		allCollections[INDEX_OF_LINKED_LIST] = new CollectionFacadeSet(new LinkedList<String>());
		allCollections[INDEX_OF_HASH_SET] = new CollectionFacadeSet(new HashSet<String>());

		data1 = Ex4Utils.file2array(DATA_1_PATH);
		data2 = Ex4Utils.file2array(DATA_2_PATH);
	}


	/**
	 * reset all the collections to empty ones
	 */
	private void reset() {
		allCollections[INDEX_OF_OPEN_HASH] = new OpenHashSet();
		allCollections[INDEX_OF_CLOSED_HASH] = new ClosedHashSet();
		allCollections[INDEX_OF_TREE_SET] = new CollectionFacadeSet(new TreeSet<String>());
		allCollections[INDEX_OF_LINKED_LIST] = new CollectionFacadeSet(new LinkedList<String>());
		allCollections[INDEX_OF_HASH_SET] = new CollectionFacadeSet(new HashSet<String>());
	}


	// ---------------------------- add data ----------------------------

	/**
	 * call the add method of the given collection with all the strings in the given data one by one. It's
	 * also measuring the time it takes for the collection to add all the data
	 * @param collection: collection to add to
	 * @param data: given array of strings to insert into the the collection
	 * @return total time in it takes in milliseconds
	 */
	private long addData(SimpleSet collection, String[] data) {
		long timeBefore = System.nanoTime();
		for (String str : data) {
			collection.add(str);
		}
		long difference = System.nanoTime() - timeBefore;
		return (difference / FROM_NANO_TO_MILLI);
	}


	/**
	 * print for each collection the time it takes to add the given strings to the collection
	 * @param data: given array of strings to insert into the the collection
	 */
	private void printAddData(String[] data) {
		for (int i = 0; i < NUMBER_OF_COLLECTIONS; i++) {
			switch (i) {
			case INDEX_OF_OPEN_HASH:
				System.out.println("OpenHash: " + addData(allCollections[INDEX_OF_OPEN_HASH], data));
				break;
			case INDEX_OF_CLOSED_HASH:
				System.out.println("ClosedHash: " + addData(allCollections[INDEX_OF_CLOSED_HASH], data));
				break;
			case INDEX_OF_TREE_SET:
				System.out.println("TreeSet: " + addData(allCollections[INDEX_OF_TREE_SET], data));
				break;
			case INDEX_OF_HASH_SET:
				System.out.println("HashSet: " + addData(allCollections[INDEX_OF_HASH_SET], data));
				break;
			case INDEX_OF_LINKED_LIST:
				System.out.println("LinkedList: " + addData(allCollections[INDEX_OF_LINKED_LIST], data));
				break;
			}
		}
	}


	// ---------------------------- contain ----------------------------

	/**
	 * call the contain method of the given collection for 'iterations' number of time
	 * @param collection: collection to check on
	 * @param iterations: number of iterations to repeat the check
	 * @param str: given string to check if contain
	 */
	private void contain(SimpleSet collection, int iterations, String str) {
		for (int i = 0; i < iterations; i++) {
			collection.contains(str);
		}
	}


	/**
	 * measure the time it take for the contain method
	 * @param collection: collection to check on
	 * @param iterations: number of iterations to repeat the check
	 * @param str: given string to check if contain
	 * @return total time in nano-seconds divided by the number of iterations
	 */
	private long containWithTime(SimpleSet collection, int iterations, String str) {
		long timeBefore = System.nanoTime();
		contain(collection, iterations, str);
		long difference = System.nanoTime() - timeBefore;
		return (difference / iterations);
	}


	/**
	 * print for each collection the time it takes to check if the str contain in it
	 * @param str: given string to check if contain
	 */
	private void printContain(String str) {
		contain(allCollections[INDEX_OF_OPEN_HASH], ANALYZE_ITERATIONS, str);
		long time = containWithTime(allCollections[INDEX_OF_OPEN_HASH], ANALYZE_ITERATIONS, str);
		System.out.println("OpenHash: " + time);

		contain(allCollections[INDEX_OF_CLOSED_HASH], ANALYZE_ITERATIONS, str);
		time = containWithTime(allCollections[INDEX_OF_CLOSED_HASH], ANALYZE_ITERATIONS, str);
		System.out.println("ClosedHash: " + time);

		contain(allCollections[INDEX_OF_TREE_SET], ANALYZE_ITERATIONS, str);
		time = containWithTime(allCollections[INDEX_OF_TREE_SET], ANALYZE_ITERATIONS, str);
		System.out.println("TreeSet: " + time);

		contain(allCollections[INDEX_OF_HASH_SET], ANALYZE_ITERATIONS, str);
		time = containWithTime(allCollections[INDEX_OF_HASH_SET], ANALYZE_ITERATIONS, str);
		System.out.println("HashSet: " + time);

		time = containWithTime(allCollections[INDEX_OF_LINKED_LIST], LINKED_LIST_ITERATIONS, str);
		System.out.println("LinkedList: " + time);
	}

	// ---------------------------- main method ----------------------------
	/**
	 * The main method - calls all the methods to analyze their performance time
	 */
	public static void main(String[] args) {
		SimpleSetPerformanceAnalyzer analyzer = new SimpleSetPerformanceAnalyzer();

		System.out.println("\n---- add data1 ----");
		analyzer.printAddData(data1);

		System.out.println("\n---- contain \"hi\" in data1 (different Hash) ----");
		analyzer.printContain("hi");

		System.out.println("\n---- contain \"-13170890158\" in data1 (same Hash) ----");
		analyzer.printContain("-13170890158");

		analyzer.reset();

		System.out.println("\n---- add data2 ----");
		analyzer.printAddData(data2);

		System.out.println("\n---- contain \"hi\" in data2 (not in Hash) ----");
		analyzer.printContain("hi");

		System.out.println("\n---- contain \"23\" in data2 (in Hash) ----");
		analyzer.printContain("23");
	}

}