package filesprocessing;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import static filesprocessing.DirectoryProcessor.*;
import static filesprocessing.Errors.*;
import static filesprocessing.OrderFactory.*;

public class Order {

	/** abs order */
	protected static final String ABS = "abs";

	/** type order */
	protected static final String TYPE = "type";

	/** size order */
	protected static final String SIZE = "size";

	/** REVERSE suffix */
	protected static final String REVERSE = "REVERSE";

	/** start index of array */
	protected static final int START_OF_ARRAY = 0;


	/**
	 * check if the order line has a REVERSE suffix
	 * @param parsedLine: given parsed order line
	 * @return true if has and false if not
	 */
	private static boolean HasReverseSuffix(String[] parsedLine) {
		return parsedLine[parsedLine.length - 1].equals(REVERSE);
	}


	protected static void orderFiles(String orderLine, ArrayList<File> filteredFiles, int line_counter) {
		String[] parsedLine = orderLine.split(SEPARATOR_IN_LINE);
		boolean foundError = false;

		try {
			switch (parsedLine[0]) {

			case ABS:
				hasErrorInAbsOrder(parsedLine); // check for error
				Sort.sort(filteredFiles, START_OF_ARRAY, filteredFiles.size() - 1, absOrder());
				break;

			case TYPE:
				hasErrorInTypeOrder(parsedLine); // check for error
				Sort.sort(filteredFiles, START_OF_ARRAY, filteredFiles.size() - 1, typeOrder());
				break;

			case SIZE:
				hasErrorInSizeOrder(parsedLine); // check for error
				Sort.sort(filteredFiles, START_OF_ARRAY, filteredFiles.size() - 1, sizeOrder());
				break;

			default: // name is not valid
				foundError = true;
				break;

			}
		} catch (IllegalArgumentException exception) {
			foundError = true;
		}

		// found Type-1 error so order by abs
		if (foundError) {
			Sort.sort(filteredFiles, START_OF_ARRAY, filteredFiles.size() - 1, absOrder());
			System.err.println(WARNING_MESSAGE + line_counter);

		}

		if (!foundError && HasReverseSuffix(parsedLine)) {
			Collections.reverse(filteredFiles);
		}
	}

}
