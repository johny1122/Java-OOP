package filesprocessing;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import static filesprocessing.Errors.*;
import static filesprocessing.Filter.*;
import static filesprocessing.Order.*;
import static filesprocessing.Output.*;

/**
 * class implementation of DirectoryProcessor
 */
public class DirectoryProcessor {


	/** Error message of invalid number of arguments */
	protected static final String ERROR_MESSAGE_NOT_2_ARGS
			= "ERROR: invalid usage - didnt receive 2 arguments";

	/** Error message of invalid arguments */
	protected static final String ERROR_MESSAGE_INVALID_ARGS = "ERROR: invalid arguments";

	/** Error message of I/O problems */
	protected static final String ERROR_MESSAGE_IO_PROBLEM = "ERROR: I/O problems";

	/** Error message of missing sub-section */
	protected static final String ERROR_MESSAGE_MISSING_SUBSECTION_NAME = "ERROR: Missing subsection";

	/** Error message of bad sub-section name or missing sub-section */
	protected static final String ERROR_MESSAGE_BAD_OR_MISSING_SUBSECTION_NAME = "ERROR: Bad subsection " +
																				 "name";

	/** name of filter sub-section */
	protected static final String FILTER_SECTION_NAME = "FILTER";

	/** name of order sub-section */
	protected static final String ORDER_SECTION_NAME = "ORDER";

	/** number of the max lines distance between two sub-sections */
	protected static final int MAX_LINES_BETWEEN_SUBSECTIONS = 2;

	/** the separator between the filter line parts */
	protected static final String SEPARATOR_IN_LINE = "#";

	/** first line in a section */
	protected static final int FIRST_LINE = 1;

	/** second line in a section */
	protected static final int SECOND_LINE = 2;

	/** fourth line in a section */
	protected static final int FOURTH_LINE = 4;


	protected static String commandsFilePath;
	protected static String sourceDirPath;
	protected static File sourceDir;
	protected static File commandsFile;
	protected static Scanner fileScanner;
	protected static ArrayList<File> filtered_files = null;


	/**
	 * return a Scanner of the commands file
	 * @return Scanner of the commands file
	 * @throws IOException: if there was an error with accessing the file
	 */
	protected static Scanner getFileScanner() throws IOException {
		try {
			return new Scanner(commandsFile);
		} catch (IOException ioe) { // I/O problems
			System.err.println(ERROR_MESSAGE_IO_PROBLEM);
			throw new IOException();
		}
	}


	/**
	 * process the commands file to filter and order as written inside
	 * @param fileScanner: Scanner of the Commands file
	 */
	private static void processCommandsFile(Scanner fileScanner) {
		int line_in_section = 1;
		int line_counter = 1;
		String curr_line;
		boolean finished_section = false;
		boolean last_was_ORDER = false;

		while (fileScanner.hasNext()) { // every row
			curr_line = fileScanner.nextLine();

			switch (curr_line) {

			case FILTER_SECTION_NAME:
				if (line_in_section ==
					SECOND_LINE) { // last ORDER didnt have an order-name line - sort by abs
					filtered_files = filterFiles(curr_line, sourceDir, line_counter);
				}
				// when the section has only 3 lines so the 4th line is actually a new FILTER line
				if (line_in_section == FOURTH_LINE) {
					finished_section = true;
					line_in_section = FIRST_LINE;
				}
				break;

			case ORDER_SECTION_NAME:
				// sort-by ABS in case that's the last row in the file and obviously has no order-name
				if (line_in_section == FOURTH_LINE) {
					orderFiles(curr_line, filtered_files, line_counter);
					break;
				}
				// in case ORDER was instead of a filter-name line
				if (line_in_section == SECOND_LINE) {
					filtered_files = filterFiles(curr_line, sourceDir, line_counter);
					break;
				}
				orderFiles(ABS, filtered_files, line_counter);
				last_was_ORDER = true;
				break;

			default: // if not a title line
				if (line_in_section == SECOND_LINE) { // filter-name line
					filtered_files = filterFiles(curr_line, sourceDir, line_counter);

				} else if (line_in_section == FOURTH_LINE) { // order-name line
					orderFiles(curr_line, filtered_files, line_counter);
					finished_section = true;
					last_was_ORDER = false;
				}
				break;
			}
			line_in_section++;

			if (finished_section) {
				if (!curr_line.equals(FILTER_SECTION_NAME)) {
					line_in_section = FIRST_LINE;
				}
				printSortedFiles(); // Print files
				finished_section = false;
			}

			line_counter++;
		}
		if (last_was_ORDER) {
			printSortedFiles(); // Print files
		}
	}


	/**
	 * main method
	 * @param args: list of Strings where args[0] is sourceDir path and args[1] is Commands File path
	 */
	public static void main(String[] args) {

		try {
			if (args.length != 2) { // Type 2 error - not 2 arguments
				throw new IllegalArgumentException();
			}

			commandsFilePath = args[1];
			sourceDirPath = args[0];
			sourceDir = new File(args[0]);
			commandsFile = new File(commandsFilePath);

			// Type 2 Errors
			foundType2Error();

			// Filter and Order the files
			fileScanner = getFileScanner();

		} catch (IllegalArgumentException exception) {
			System.err.println(ERROR_MESSAGE_NOT_2_ARGS);
			return;
		}
		// Error messages already printed in the methods
		catch (RuntimeException exception) {
			return;
		} catch (IOException ioe) {
			System.err.println(ERROR_MESSAGE_IO_PROBLEM);
			return;
		}

		processCommandsFile(fileScanner);

		fileScanner.close();
	}
}
