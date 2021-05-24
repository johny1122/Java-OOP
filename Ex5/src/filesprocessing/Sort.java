package filesprocessing;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;


/**
 * implementation of Quick-Sort algorithm with given costume Comparator
 */
public class Sort {

	/**
	 * the function sets the pivot to the last element in the array. the function place the pivot in the
	 * right
	 * place in the array where all the bigger elements than him are in the right and all the smaller
	 * elements
	 * than him are in his left
	 * @param files: ArrayList of files to do the partition on
	 * @param startIndex: start index to do the partition
	 * @param endIndex: end index to do the partition
	 * @param comparator: comparable function to compare between files
	 * @return the correct index of the pivot element
	 */
	private static int partition(ArrayList<File> files, int startIndex, int endIndex,
								 Comparator<File> comparator) {

		File pivotFile = files.get(endIndex);
		int i = (startIndex - 1);
		for (int j = startIndex; j < endIndex; j++) {
			if (comparator.compare(files.get(j), pivotFile) < 0) {
				i++;
				swap(files, i, j);
			}
		}
		swap(files, i + 1, endIndex);
		return i + 1;
	}


	/**
	 * swap array[i] and array[j]
	 * @param fileArrayList: ArrayList of files to swap in
	 * @param i: int of index to swap
	 * @param j: int of index to swap
	 */
	private static void swap(ArrayList<File> fileArrayList, int i, int j) {
		File tempFile = fileArrayList.get(i);
		fileArrayList.set(i, fileArrayList.get(j));
		fileArrayList.set(j, tempFile);
	}


	/**
	 * implementation of quickSort
	 * @param fileArrayList: ArrayList of files to sort
	 * @param startIndex: start index to do the partition
	 * @param endIndex: end index to do the partition
	 */
	protected static void sort(ArrayList<File> fileArrayList, int startIndex, int endIndex,
							   Comparator<File> comparator) {
		if (startIndex < endIndex) {
			int pivotElement = partition(fileArrayList, startIndex, endIndex, comparator);

			sort(fileArrayList, startIndex, pivotElement - 1, comparator);
			sort(fileArrayList, pivotElement + 1, endIndex, comparator);
		}
	}

}
