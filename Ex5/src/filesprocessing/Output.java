package filesprocessing;

import java.io.File;

import static filesprocessing.DirectoryProcessor.*;

/**
 * a class which is handling the final output print to System.out
 */
public class Output {

	/**
	 * Prints the sorted files
	 */
	protected static void printSortedFiles() {
		if (filtered_files != null) {
			for (File filtered_file : filtered_files) {
				System.out.println(filtered_file.getName());
			}
		}
	}
}
