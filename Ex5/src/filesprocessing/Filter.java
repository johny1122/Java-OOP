package filesprocessing;


import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;


import static filesprocessing.DirectoryProcessor.*;
import static filesprocessing.Errors.*;
import static filesprocessing.FilterFactory.*;


public class Filter {

	/** greater_than filter */
	protected static final String GREATER_THAN = "greater_than";

	/** between filter */
	protected static final String BETWEEN = "between";

	/** smaller_than filter */
	protected static final String SMALLER_THAN = "smaller_than";

	/** file filter */
	protected static final String FILE = "file";

	/** contains filter */
	protected static final String CONTAINS = "contains";

	/** prefix filter */
	protected static final String PREFIX = "prefix";

	/** suffix filter */
	protected static final String SUFFIX = "suffix";

	/** writable filter */
	protected static final String WRITABLE = "writable";

	/** executable filter */
	protected static final String EXECUTABLE = "executable";

	/** hidden filter */
	protected static final String HIDDEN = "hidden";

	/** all filter */
	protected static final String ALL = "all";

	/** NOT suffix */
	protected static final String NOT = "NOT";

	/** empty string */
	protected static final String EMPTY_STRING = "";

	/** length of array of size 1 */
	protected static final int LENGTH_OF_ONE = 1;


	static boolean foundError = false;


	/**
	 * check if the filter line has a NOT suffix
	 * @param parsedLine: given parsed filter line
	 * @return true if has and false if not
	 */
	private static boolean HasNotSuffix(String[] parsedLine) {
		return parsedLine[parsedLine.length - 1].equals(NOT);
	}


	/**
	 * returns a filter by greater_than
	 * @param parsedLine: given parsed filter line
	 * @return FileFilter filtering files by greater_than
	 */
	private static FileFilter filterGreaterThan(String[] parsedLine) {
		try {
			hasErrorInGreaterThanFilter(parsedLine); // check for errors

			if (HasNotSuffix(parsedLine)) {
				return greaterThanCheck(Double.parseDouble(parsedLine[1]), true);
			}
			return greaterThanCheck(Double.parseDouble(parsedLine[1]), false);
		} catch (IllegalArgumentException e) {
			foundError = true;
			return null;
		}
	}


	/**
	 * returns a filter by between
	 * @param parsedLine: given parsed filter line
	 * @return FileFilter filtering files by between
	 */
	private static FileFilter filterBetween(String[] parsedLine) {
		try {
			hasErrorInBetweenFilter(parsedLine);// check for errors

			if (HasNotSuffix(parsedLine)) {
				return betweenCheck(Double.parseDouble(parsedLine[1]), Double.parseDouble(parsedLine[2]),
									true);
			}
			return betweenCheck(Double.parseDouble(parsedLine[1]), Double.parseDouble(parsedLine[2]),
								false);
		} catch (IllegalArgumentException | ArithmeticException exception) {
			foundError = true;
			return null;
		}
	}


	/**
	 * returns a filter by smaller_than
	 * @param parsedLine: given parsed filter line
	 * @return FileFilter filtering files by smaller_than
	 */
	private static FileFilter filterSmallerThan(String[] parsedLine) {
		try {
			hasErrorInSmallerThanFilter(parsedLine); // check for errors
			if (HasNotSuffix(parsedLine)) {
				return smallerThanCheck(Double.parseDouble(parsedLine[1]), true);
			}
			return smallerThanCheck(Double.parseDouble(parsedLine[1]), false);
		} catch (IllegalArgumentException exception) {
			foundError = true;
			return null;
		}
	}


	/**
	 * returns a filter by file
	 * @param parsedLine: given parsed filter line
	 * @return FileFilter filtering files by file
	 */
	private static FileFilter filterFile(String[] parsedLine) {
		try {
			hasErrorInFileFilter(parsedLine); // check for errors

			if (HasNotSuffix(parsedLine)) {
				return fileCheck(parsedLine[1], true);
			}
			return fileCheck(parsedLine[1], false);
		} catch (IllegalArgumentException exception) {
			foundError = true;
			return null;
		}
	}


	/**
	 * returns a filter by contains
	 * @param parsedLine: given parsed filter line
	 * @return FileFilter filtering files by contains
	 */
	private static FileFilter filterContains(String[] parsedLine) {
		try {
			hasErrorInContainsFilter(parsedLine); // check for errors

			if (HasNotSuffix(parsedLine)) {
				return containsCheck(parsedLine[1], true);
			}
			if (parsedLine.length == LENGTH_OF_ONE) { // in case for "contains#" (empty string after #)
				return containsCheck(EMPTY_STRING, false);
			}
			return containsCheck(parsedLine[1], false);
		} catch (IllegalArgumentException exception) {
			foundError = true;
			return null;
		}
	}


	/**
	 * returns a filter by prefix
	 * @param parsedLine: given parsed filter line
	 * @return FileFilter filtering files by prefix
	 */
	private static FileFilter filterPrefix(String[] parsedLine) {
		try {
			hasErrorInPrefixFilter(parsedLine); // check for errors

			if (HasNotSuffix(parsedLine)) {
				return prefixCheck(parsedLine[1], true);
			}
			if (parsedLine.length == LENGTH_OF_ONE) { // in case for "prefix#" (empty string after #)
				return prefixCheck(EMPTY_STRING, false);
			}
			return prefixCheck(parsedLine[1], false);
		} catch (IllegalArgumentException exception) {
			foundError = true;
			return null;
		}
	}


	/**
	 * returns a filter by suffix
	 * @param parsedLine: given parsed filter line
	 * @return FileFilter filtering files by suffix
	 */
	private static FileFilter filterSuffix(String[] parsedLine) {
		try {
			hasErrorInSuffixFilter(parsedLine); // check for errors

			if (HasNotSuffix(parsedLine)) {
				return suffixCheck(parsedLine[1], true);
			}
			if (parsedLine.length == LENGTH_OF_ONE) { // in case for "suffix#" (empty string after #)
				return suffixCheck(EMPTY_STRING, false);
			}
			return suffixCheck(parsedLine[1], false);
		} catch (IllegalArgumentException exception) {
			foundError = true;
			return null;
		}
	}


	/**
	 * returns a filter by writable
	 * @param parsedLine: given parsed filter line
	 * @return FileFilter filtering files by writable
	 */
	private static FileFilter filterWritable(String[] parsedLine) {
		try {
			hasErrorInWritableFilter(parsedLine); // check for errors

			if (HasNotSuffix(parsedLine)) {
				return writableCheck(parsedLine[1], true);
			}
			return writableCheck(parsedLine[1], false);
		} catch (IllegalArgumentException exception) {
			foundError = true;
			return null;
		}
	}


	/**
	 * returns a filter by executable
	 * @param parsedLine: given parsed filter line
	 * @return FileFilter filtering files by executable
	 */
	private static FileFilter filterExecutable(String[] parsedLine) {
		try {
			hasErrorInExecutableFilter(parsedLine); // check for errors
			if (HasNotSuffix(parsedLine)) {
				return executableCheck(parsedLine[1], true);
			}
			return executableCheck(parsedLine[1], false);
		} catch (IllegalArgumentException exception) {
			foundError = true;
			return null;
		}
	}


	/**
	 * returns a filter by hidden
	 * @param parsedLine: given parsed filter line
	 * @return FileFilter filtering files by hidden
	 */
	private static FileFilter filterHidden(String[] parsedLine) {
		try {
			hasErrorInHiddenFilter(parsedLine); // check for errors

			if (HasNotSuffix(parsedLine)) {
				return hiddenCheck(parsedLine[1], true);
			}
			return hiddenCheck(parsedLine[1], false);
		} catch (IllegalArgumentException exception) {
			foundError = true;
			return null;
		}
	}


	/**
	 * returns a filter by all
	 * @param parsedLine: given parsed filter line
	 * @return FileFilter filtering files by all
	 */
	private static FileFilter filterAll(String[] parsedLine) {
		try {
			hasErrorInAllFilter(parsedLine); // check for errors

			if (HasNotSuffix(parsedLine)) {
				return allCheck(true);
			}
			return allCheck(false);
		} catch (IllegalArgumentException exception) {
			foundError = true;
			return null;
		}
	}


	/**
	 * filter the files in the sourceDir directory according to the filter in the given filter line
	 * @param filterLine: String of the filter line
	 * @param sourceDir: Directory to filter files from
	 * @param line_counter: counter the current line in the Commands file
	 * @return ArrayList<File> of the filtered files in the sourceDir directory
	 */
	protected static ArrayList<File> filterFiles(String filterLine, File sourceDir, int line_counter) {
		String[] parsedLine = filterLine.split(SEPARATOR_IN_LINE);

		File[] files;
		FileFilter filter = null;

		switch (parsedLine[0]) {

		case GREATER_THAN:
			filter = filterGreaterThan(parsedLine);
			break;

		case BETWEEN:
			filter = filterBetween(parsedLine);
			break;

		case SMALLER_THAN:
			filter = filterSmallerThan(parsedLine);
			break;

		case FILE:
			filter = filterFile(parsedLine);
			break;

		case CONTAINS:
			filter = filterContains(parsedLine);
			break;

		case PREFIX:
			filter = filterPrefix(parsedLine);
			break;

		case SUFFIX:
			filter = filterSuffix(parsedLine);
			break;

		case WRITABLE:
			filter = filterWritable(parsedLine);
			break;

		case EXECUTABLE:
			filter = filterExecutable(parsedLine);
			break;

		case HIDDEN:
			filter = filterHidden(parsedLine);
			break;


		case ALL:
			filter = filterAll(parsedLine);
			break;

		default: // name is not valid
			foundError = true;
			break;
		}

		if (foundError) {
			filter = allCheck(false);
			System.err.println(WARNING_MESSAGE + line_counter);
			foundError = false;
		}

		// filter the files
		files = sourceDir.listFiles(filter);

		// move filtered files to ArrayList (easier to sort with)
		ArrayList<File> filesArrayList = null;
		if (files != null) {
			filesArrayList = new ArrayList<File>(Arrays.asList(files));
		}

		return filesArrayList;
	}
}
