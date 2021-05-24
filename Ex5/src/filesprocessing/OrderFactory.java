package filesprocessing;

import java.io.File;
import java.util.Comparator;


public class OrderFactory {

	/** equal in comparator */
	protected static final int EQUAL = 0;

	/** period doesnt exist in the file name */
	protected static final int PERIOD_DOES_NOT_EXIST = (-1);

	/** the index at the beginning of a string */
	protected static final int FIRST_STRING_INDEX = 0;


	/**
	 * compare between files name (in lexicographically order)
	 * @return 0 iff their size is equal, number bigger than 0 if file1 > file2 and number smaller than 0 if
	 * 		file1 < file2
	 */
	protected static Comparator<File> absOrder() {
		return (file1, file2) -> (file1.getAbsolutePath().compareTo(file2.getAbsolutePath()));
	}


	/**
	 * find the file's type
	 * @param file: file to find the type of
	 * @return the extension after the last point in the name string
	 */
	private static String findFileType(File file) {
		String fileName = file.getName();
		String fileType;
		int fileLastPointIndex = fileName.lastIndexOf(".");

		if (file.isHidden()) { // if hidden
			if ((fileLastPointIndex == FIRST_STRING_INDEX) || (fileLastPointIndex == fileName.length() - 1)) {
				fileType = "";
			} else {
				fileType = fileName.substring(fileLastPointIndex + 1);
			}

		} else { // not hidden
			if ((fileLastPointIndex == PERIOD_DOES_NOT_EXIST) ||
				(fileLastPointIndex == fileName.length() - 1)) {
				fileType = "";
			} else {
				fileType = fileName.substring(fileLastPointIndex + 1);
			}
		}
		return fileType;
	}


	/**
	 * compare between files type (in lexicographically order)
	 * @return 0 iff their size is equal, number bigger than 0 if file1 > file2 and number smaller than 0 if
	 * 		file1 < file2
	 */
	protected static Comparator<File> typeOrder() {
		return (file1, file2) -> {
			int compare_value = findFileType(file1).compareTo(findFileType(file2));
			return (compare_value == EQUAL) ? file1.getAbsolutePath().compareTo(file2.getAbsolutePath()) :
				   compare_value;
		};
	}


	/**
	 * compare between files size
	 * @return 0 iff their size is equal, number bigger than 0 if file1 > file2 and number smaller than 0 if
	 * 		file1 < file2
	 */
	protected static Comparator<File> sizeOrder() {
		return (file1, file2) -> {
			int compare_value = Long.compare(file1.length(), file2.length());
			return (compare_value == EQUAL) ? file1.getAbsolutePath().compareTo(file2.getAbsolutePath()) :
				   compare_value;
		};
	}


}
