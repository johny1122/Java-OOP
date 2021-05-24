package filesprocessing;

import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.NotDirectoryException;

import static filesprocessing.DirectoryProcessor.*;
import static filesprocessing.Order.*;
import static filesprocessing.Filter.*;


/**
 * implementation of Error class
 */
public class Errors {

	/** yes value in hidden/writable/executable */
	protected static final String YES = "YES";

	/** no value in hidden/writable/executable */
	protected static final String NO = "NO";

	/** Type 1 error warnings message */
	protected static final String WARNING_MESSAGE = "Warning in line ";

	/** error message when commands file isn't a file */
	protected static final String COMMAND_FILE_ISNT_A_FILE = "Commands file isn't a file";


	/**
	 * checks if there is an error in the greater_than filter
	 * @param parsedLine: parsed filter list by '#'
	 */
	protected static void hasErrorInGreaterThanFilter(String[] parsedLine)
			throws IllegalArgumentException {
		if (!parsedLine[0].equals(GREATER_THAN)) { //bad function name
			throw new IllegalArgumentException();
		}
		if (Double.parseDouble(parsedLine[1]) < 0) {
			throw new NumberFormatException();
		}
	}


	/**
	 * checks if there is an error in the between filter
	 * @param parsedLine: parsed filter list by '#'
	 */
	protected static void hasErrorInBetweenFilter(String[] parsedLine)
			throws IllegalArgumentException, ArithmeticException {
		if (!parsedLine[0].equals(BETWEEN)) { //bad function name
			throw new IllegalArgumentException();
		}
		// negative
		if ((Double.parseDouble(parsedLine[1]) < 0) || (Double.parseDouble(parsedLine[2]) < 0)) {
			throw new NumberFormatException();
		}
		// first bigger than second
		if (Double.parseDouble(parsedLine[1]) > Double.parseDouble(parsedLine[2])) {
			throw new ArithmeticException();
		}
	}


	/**
	 * checks if there is an error in the smaller_than filter
	 * @param parsedLine: parsed filter list by '#'
	 */
	protected static void hasErrorInSmallerThanFilter(String[] parsedLine)
			throws IllegalArgumentException {
		if (!parsedLine[0].equals(SMALLER_THAN)) { //bad function name
			throw new IllegalArgumentException();
		}
		if (Double.parseDouble(parsedLine[1]) < 0) {
			throw new NumberFormatException();
		}
	}


	/**
	 * checks if there is an error in the file filter
	 * @param parsedLine: parsed filter list by '#'
	 */
	protected static void hasErrorInFileFilter(String[] parsedLine) throws IllegalArgumentException {
		if (!parsedLine[0].equals(FILE)) { //bad function name
			throw new IllegalArgumentException();
		}
	}


	/**
	 * checks if there is an error in the contains filter
	 * @param parsedLine: parsed filter list by '#'
	 */
	protected static void hasErrorInContainsFilter(String[] parsedLine) throws IllegalArgumentException {
		if (!parsedLine[0].equals(CONTAINS)) { //bad function name
			throw new IllegalArgumentException();
		}
	}


	/**
	 * checks if there is an error in the prefix filter
	 * @param parsedLine: parsed filter list by '#'
	 */
	protected static void hasErrorInPrefixFilter(String[] parsedLine) throws IllegalArgumentException {
		if (!parsedLine[0].equals(PREFIX)) { //bad function name
			throw new IllegalArgumentException();
		}
	}


	/**
	 * checks if there is an error in the suffix filter
	 * @param parsedLine: parsed filter list by '#'
	 */
	protected static void hasErrorInSuffixFilter(String[] parsedLine) throws IllegalArgumentException {
		if (!parsedLine[0].equals(SUFFIX)) { //bad function name
			throw new IllegalArgumentException();
		}
	}


	/**
	 * checks if there is an error in the writable filter
	 * @param parsedLine: parsed filter list by '#'
	 */
	protected static void hasErrorInWritableFilter(String[] parsedLine) throws IllegalArgumentException {
		if ((!parsedLine[0].equals(WRITABLE)) ||
			(!(parsedLine[1].equals(YES) || parsedLine[1].equals(NO)))) { //bad function name or value
			throw new IllegalArgumentException();
		}
	}


	/**
	 * checks if there is an error in the executable filter
	 * @param parsedLine: parsed filter list by '#'
	 */
	protected static void hasErrorInExecutableFilter(String[] parsedLine) throws IllegalArgumentException {
		if (!parsedLine[0].equals(EXECUTABLE) ||
			(!(parsedLine[1].equals(YES) || parsedLine[1].equals(NO)))) { //bad function name or value
			throw new IllegalArgumentException();
		}
	}


	/**
	 * checks if there is an error in the hidden filter
	 * @param parsedLine: parsed filter list by '#'
	 */
	protected static void hasErrorInHiddenFilter(String[] parsedLine) throws IllegalArgumentException {
		if (!parsedLine[0].equals(HIDDEN) ||
			(!(parsedLine[1].equals(YES) || parsedLine[1].equals(NO)))) { //bad function name or value
			throw new IllegalArgumentException();
		}
	}


	/**
	 * checks if there is an error in the all filter
	 * @param parsedLine: parsed filter list by '#'
	 */
	protected static void hasErrorInAllFilter(String[] parsedLine) throws IllegalArgumentException {
		if (!parsedLine[0].equals(ALL)) { //bad function name
			throw new IllegalArgumentException();
		}
	}


	/**
	 * checks if there is an error in the abs order
	 * @param parsedLine: parsed filter list by '#'
	 */
	protected static void hasErrorInAbsOrder(String[] parsedLine) throws IllegalArgumentException {
		if (!parsedLine[0].equals(ABS)) { //bad function name
			throw new IllegalArgumentException();
		}
	}


	/**
	 * checks if there is an error in the type order
	 * @param parsedLine: parsed filter list by '#'
	 */
	protected static void hasErrorInTypeOrder(String[] parsedLine) throws IllegalArgumentException {
		if (!parsedLine[0].equals(TYPE)) { //bad function name
			throw new IllegalArgumentException();
		}
	}


	/**
	 * checks if there is an error in the size order
	 * @param parsedLine: parsed filter list by '#'
	 */
	protected static void hasErrorInSizeOrder(String[] parsedLine) throws IllegalArgumentException {
		if (!parsedLine[0].equals(SIZE)) { //bad function name
			throw new IllegalArgumentException();
		}
	}


	/**
	 * check for Type 2 errors
	 * @return: true if found such an error and false otherwise
	 */
	protected static void foundType2Error() throws RuntimeException, IOException {
		if (!commandsFile.isFile()) { // check type (file)
			System.err.println(ERROR_MESSAGE_INVALID_ARGS);
			throw new InvalidPathException(commandsFilePath, COMMAND_FILE_ISNT_A_FILE);
		}
		if (!sourceDir.isDirectory()) { // check type (directory)
			System.err.println(ERROR_MESSAGE_INVALID_ARGS);
			throw new NotDirectoryException(sourceDirPath);
		}

		fileScanner = getFileScanner();

		// check for Commands File's content type-2 errors. throws RuntimeException if found error
		foundType2ErrorsInCommandsFile();
	}


	/**
	 * move over the lines in Commands File. check if the sub-section lines have the correct sub-section name
	 * and for bad format. handling bad name and missing sub-section name the same
	 * @return: true if found a sub-section line not matching the correct name and false otherwise
	 */
	private static void foundType2ErrorsInCommandsFile() throws RuntimeException {
		// check for first FILTER
		if (!fileScanner.nextLine().equals(FILTER_SECTION_NAME)) {
			System.err.println(ERROR_MESSAGE_BAD_OR_MISSING_SUBSECTION_NAME);
			throw new RuntimeException();
		}

		int lines_after_filter = 1;
		int lines_after_order = 1;
		boolean is_filter_next = false; // already checked for first FILTER so next is ORDER
		String curr_line;

		while (fileScanner.hasNextLine()) {
			curr_line = fileScanner.nextLine();
			if (is_filter_next) {
				if (curr_line.equals(FILTER_SECTION_NAME)) {
					lines_after_order = 1;
					is_filter_next = false; // next is ORDER
					continue;
				}
				// after 2 lines from ORDER check if FILTER was in one of those 2 lines
				if (lines_after_order == MAX_LINES_BETWEEN_SUBSECTIONS) {
					System.err.println(ERROR_MESSAGE_BAD_OR_MISSING_SUBSECTION_NAME);
					throw new RuntimeException();
				}
				lines_after_order++;


			} else { // order is next
				// exactly after 2 lines ORDER must appear
				if (lines_after_filter == MAX_LINES_BETWEEN_SUBSECTIONS) {
					if (!curr_line.equals(ORDER_SECTION_NAME)) {
						System.err.println(ERROR_MESSAGE_BAD_OR_MISSING_SUBSECTION_NAME);
						throw new RuntimeException();
					}
					lines_after_filter = 1;
					is_filter_next = true;
				} else {
					lines_after_filter++;
				}
			}
		}

		if (!is_filter_next) { // next sub-section is ORDER so last sub-section was FILTER and not ORDER
			System.err.println(ERROR_MESSAGE_MISSING_SUBSECTION_NAME);
			throw new RuntimeException();
		}
	}
}
