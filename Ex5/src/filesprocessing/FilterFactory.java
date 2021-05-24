package filesprocessing;

import java.io.File;
import java.io.FileFilter;


/**
 * implementation of filter functions factory for the files
 */
public class FilterFactory {

	/** the number we should divide bytes to get k-bytes */
	protected static final double BYTES_TO_K_BYTES = 1024;

	/** yes in writable/executable/hidden filters */
	protected static final String YES = "YES";

	/** no in writable/executable/hidden filters */
	protected static final String NO = "NO";


	/**
	 * greater_than filter file size is strictly greater than the given number of k-bytes
	 * @param size: size file to check with
	 * @param hasNot: boolean if has a NOT suffix
	 * @return true if greater and false if not (the opposite if hasNot is true)
	 */
	protected static FileFilter greaterThanCheck(double size, boolean hasNot) {
		return new FileFilter() {
			@Override
			public boolean accept(File file) {
				return file.isFile() && (!hasNot == (file.length() / BYTES_TO_K_BYTES) > size);
			}
		};
	}


	/**
	 * between filter file size is between (inclusive) the given numbers (in k-bytes)
	 * @param size_small: smaller number in the range
	 * @param size_big: bigger number in range
	 * @param hasNot: boolean if has a NOT suffix
	 * @return true if between and false if not (the opposite if hasNot is true)
	 */
	protected static FileFilter betweenCheck(Double size_small, Double size_big, boolean hasNot) {
		return new FileFilter() {
			@Override
			public boolean accept(File file) {
				return file.isFile() && (!hasNot == ((size_small <= (file.length() / BYTES_TO_K_BYTES)) &&
													 ((file.length() / BYTES_TO_K_BYTES) <= size_big)));
			}
		};
	}


	/**
	 * smallerThan filter file size is strictly less than the given number of k-bytes
	 * @param size: size file to check with
	 * @param hasNot: boolean if has a NOT suffix
	 * @return true if smaller and false if not (the opposite if hasNot is true)
	 */
	protected static FileFilter smallerThanCheck(double size, boolean hasNot) {
		return new FileFilter() {
			@Override
			public boolean accept(File file) {
				return file.isFile() && (!hasNot == ((file.length() / BYTES_TO_K_BYTES) < size));
			}
		};
	}


	/**
	 * file filter given name equals the file name (excluding path)
	 * @param hasNot: boolean if has a NOT suffix
	 * @return true if equals and false if not (the opposite if hasNot is true)
	 */
	protected static FileFilter fileCheck(String name, boolean hasNot) {
		return new FileFilter() {
			@Override
			public boolean accept(File file) {
				return file.isFile() && (!hasNot == file.getName().equals(name));
			}
		};
	}


	/**
	 * contains filter given substring is contained in the file name (excluding path)
	 * @param hasNot: boolean if has a NOT suffix
	 * @return true if contains and false if not (the opposite if hasNot is true)
	 */
	protected static FileFilter containsCheck(String substring, boolean hasNot) {
		return new FileFilter() {
			@Override
			public boolean accept(File file) {
				return file.isFile() && (!hasNot == file.getName().contains(substring));
			}
		};
	}


	/**
	 * prefix filter given prefix is the prefix of the file name (excluding path)
	 * @param hasNot: boolean if has a NOT suffix
	 * @return true if is the prefix and false if not (the opposite if hasNot is true)
	 */
	protected static FileFilter prefixCheck(String predix, boolean hasNot) {
		return new FileFilter() {
			@Override
			public boolean accept(File file) {
				return file.isFile() && (!hasNot == file.getName().startsWith(predix));
			}
		};
	}


	/**
	 * suffix filter given suffix is the suffix of the file name (excluding path)
	 * @param hasNot: boolean if has a NOT suffix
	 * @return true if is the suffix and false if not (the opposite if hasNot is true)
	 */
	protected static FileFilter suffixCheck(String suffix, boolean hasNot) {
		return new FileFilter() {
			@Override
			public boolean accept(File file) {
				return file.isFile() && (!hasNot == file.getName().endsWith(suffix));
			}
		};
	}


	/**
	 * writable filter does the file have writing permission? (for the current user)
	 * @param hasNot: boolean if has a NOT suffix
	 * @return true if has and false if not (the opposite if hasNot is true)
	 */
	protected static FileFilter writableCheck(String hasPermission, boolean hasNot) {
		return new FileFilter() {
			@Override
			public boolean accept(File file) {
				return file.isFile() && (!hasNot == ((file.canWrite() && hasPermission.equals(YES)) ||
													 (!file.canWrite() && hasPermission.equals(NO))));
			}
		};
	}


	/**
	 * executable filter does the file have executable permission? (for the current user)
	 * @param hasNot: boolean if has a NOT suffix
	 * @return true if has and false if not (the opposite if hasNot is true)
	 */
	protected static FileFilter executableCheck(String hasPermission, boolean hasNot) {
		return new FileFilter() {
			@Override
			public boolean accept(File file) {
				return file.isFile() && (!hasNot == ((file.canExecute() && hasPermission.equals(YES)) ||
													 (!file.canExecute() && hasPermission.equals(NO))));
			}
		};
	}


	/**
	 * hidden filter is the file a hidden file?
	 * @param hasNot: boolean if has a NOT suffix
	 * @return true if does and false if not (the opposite if hasNot is true)
	 */
	protected static FileFilter hiddenCheck(String hasPermission, boolean hasNot) {
		return new FileFilter() {
			@Override
			public boolean accept(File file) {
				return file.isFile() && (!hasNot == ((file.isHidden() && hasPermission.equals(YES)) ||
													 (!file.isHidden() && hasPermission.equals(NO))));
			}
		};
	}


	/**
	 * all filter all files are matched
	 * @param hasNot: boolean if has a NOT suffix
	 * @return true if hasNot=false and false if hasNot=true
	 */
	protected static FileFilter allCheck(boolean hasNot) {
		return new FileFilter() {
			@Override
			public boolean accept(File file) {
				return !hasNot && file.isFile();
			}
		};
	}

}
