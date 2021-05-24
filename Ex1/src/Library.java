/**
 * This class represents a library, which hold a collection of books. Patrons can register at the library to
 * be able to check out books, if a copy of the requested book is available.
 */
public class Library {

	/**
	 * The maximal number of books this library can hold.
	 */
	final int maxBookCapacity;

	/**
	 * The maximal number of books this library allows a single patron to borrow at the same time.
	 */
	final int maxBorrowedBooks;

	/**
	 * The maximal number of registered patrons this library can handle.
	 */
	final int maxPatronCapacity;

	/**
	 * array of all the book in the library
	 */
	Book[] all_books;

	/**
	 * array of all the patrons registered to the library
	 */
	Patron[] all_patrons;

	/**
	 * current number of books owned by the library
	 */
	int current_number_of_books = 0;

	/**
	 * current number of patron registered to the library
	 */
	int current_number_of_patrons_registered = 0;


	/*-----  Constants  ------*/

	/**
	 * const number that represents not succeeded
	 */
	final static int NOT_SUCCEEDED = -1;

	/**
	 * const number that represents not registered
	 */
	final static int NOT_REGISTERED = -1;

	/**
	 * const number that represents not an id of a book/patron not already added to library
	 */
	final static int NOT_ADDED_ID = -1;

	/*-----  Constructors  ------*/

	/**
	 * Creates a new library with the given parameters.
	 * @param libraryMaxBookCapacity The maximal number of books this library can hold.
	 * @param libraryMaxBorrowedBooks The maximal number of books this library allows a single patron to
	 * 		borrow at the same time.
	 * @param libraryMaxPatronCapacity The maximal number of registered patrons this library can handle.
	 */
	Library(int libraryMaxBookCapacity, int libraryMaxBorrowedBooks, int libraryMaxPatronCapacity) {
		maxBookCapacity = libraryMaxBookCapacity;
		maxBorrowedBooks = libraryMaxBorrowedBooks;
		maxPatronCapacity = libraryMaxPatronCapacity;
		all_books = new Book[maxBookCapacity];
		all_patrons = new Patron[maxPatronCapacity];
	}

	/*-----  Instance Methods  ------*/

	/**
	 * Adds the given book to this library, if there is place available, and it isn't already in the library.
	 * @param book The book to add to this library.
	 * @return a non-negative id number for the book if there was a spot and the book was successfully added,
	 * 		or if the book was already in the library; a negative number otherwise.
	 */
	int addBookToLibrary(Book book) {
		int bookId = getBookId(book);
		if (bookId != NOT_ADDED_ID) {  // already added
			return bookId;
		}
		if (current_number_of_books < maxBookCapacity) {  // there is place for more
			all_books[current_number_of_books] = book;
			current_number_of_books++;
			return current_number_of_books - 1;
		}
		return NOT_SUCCEEDED;
	}


	/**
	 * Returns true if the given number is an id of some book in the library, false otherwise.
	 * @param bookId The id to check.
	 * @return true if the given number is an id of some book in the library, false otherwise.
	 */
	boolean isBookIdValid(int bookId) {
		if ((bookId < 0) || (bookId > maxBookCapacity - 1)) {
			return false;
		}
		return all_books[bookId] != null;
	}


	/**
	 * Returns the non-negative id number of the given book if he is owned by this library, -1 otherwise.
	 * @param book The book for which to find the id number.
	 * @return a non-negative id number of the given book if he is owned by this library, -1 otherwise.
	 */
	int getBookId(Book book) {
		for (int i = 0; i < current_number_of_books; i++) {
			if (all_books[i] == book) {
				return i;
			}
		}
		return NOT_REGISTERED;
	}


	/**
	 * Returns true if the book with the given id is available, false otherwise.
	 * @param bookId The id number of the book to check.
	 * @return true if the book with the given id is available, false otherwise.
	 */
	boolean isBookAvailable(int bookId) {
		return isBookIdValid(bookId) && all_books[bookId].getCurrentBorrowerId() == -1;
	}


	/**
	 * Registers the given Patron to this library, if there is a spot available.
	 * @param patron The patron to register to this library.
	 * @return a non-negative id number for the patron if there was a spot and the patron was successfully
	 * 		registered or if the patron was already registered. a negative number otherwise.
	 */
	int registerPatronToLibrary(Patron patron) {
		int patronId = getPatronId(patron);
		if (patronId != NOT_ADDED_ID) {  // already registered
			return patronId;
		}
		if (current_number_of_patrons_registered < maxPatronCapacity) {  // there is place for more
			all_patrons[current_number_of_patrons_registered] = patron;
			current_number_of_patrons_registered++;
			return current_number_of_patrons_registered - 1;
		}
		return NOT_SUCCEEDED;
	}


	/**
	 * Returns true if the given number is an id of a patron in the library, false otherwise.
	 * @param patronId The id to check.
	 * @return true if the given number is an id of a patron in the library, false otherwise.
	 */
	boolean isPatronIdValid(int patronId) {
		if ((patronId < 0) || (patronId > maxPatronCapacity - 1)) {
			return false;
		}
		return all_patrons[patronId] != null;
	}

	/**
	 * Returns the non-negative id number of the given patron if he is registered to this library, -1
	 * otherwise.
	 * @param patron The patron for which to find the id number.
	 * @return a non-negative id number of the given patron if he is registered to this library, -1
	 * otherwise.
	 */
	int getPatronId(Patron patron) {
		for (int i = 0; i < current_number_of_patrons_registered; i++) {
			if (all_patrons[i] == patron) {
				return i;
			}
		}
		return NOT_REGISTERED;
	}


	/**
	 * Marks the book with the given id number as borrowed by the patron with the given patron id, if this
	 * book is available, the given patron isn't already borrowing the maximal number of books allowed,
	 * and if the patron will enjoy this book.
	 * @param bookId The id number of the book to borrow.
	 * @param patronId The id number of the patron that will borrow the book.
	 * @return true if the book was borrowed successfully, false otherwise.
	 */
	boolean borrowBook(int bookId, int patronId) {
		if (!isBookAvailable(bookId) || !isPatronIdValid(patronId)) {  // book or patron not valid/available
			return false;
		}
		if (!all_patrons[patronId].willEnjoyBook(all_books[bookId])) {  // patron will not enjoy the book
			return false;
		}
		int number_of_books_patron_borrowing = 0;
		for (int i = 0; i < current_number_of_books; i++) {
			if (all_books[i].getCurrentBorrowerId() == patronId) {
				number_of_books_patron_borrowing++;
			}
		}
		if (number_of_books_patron_borrowing < maxBorrowedBooks) {  // patron didnt borrow the maximum
			all_books[bookId].setBorrowerId(patronId);
			return true;
		}
		return false;
	}


	/**
	 * Return the given book.
	 * @param bookId The id number of the book to return.
	 */
	void returnBook(int bookId) {
		if (isBookIdValid(bookId) && !isBookAvailable(bookId)) {  // book is borrowed
			all_books[bookId].returnBook();
		}
	}


	/**
	 * Suggest the patron with the given id the book he will enjoy the most, out of all available books he
	 * will enjoy, if any such exist.
	 * @param patronId The id number of the patron to suggest the book to.
	 * @return The available book the patron with the given ID will enjoy the most. Null if no book is
	 * 		available.
	 */
	Book suggestBookToPatron(int patronId) {
		if (!isPatronIdValid(patronId)) {
			return null;
		}
		int max_book_score = 0;
		Book most_enjoy_book = null;
		for (int i = 0; i < current_number_of_books; i++) {
			int curr_book_score = all_patrons[patronId].getBookScore(all_books[i]);
			// (enjoy the book) && (book is available) && (book's grade is the higher)
			if (all_patrons[patronId].willEnjoyBook(all_books[i]) && isBookAvailable(i) &&
				(curr_book_score > max_book_score)) {
				max_book_score = curr_book_score;
				most_enjoy_book = all_books[i];
			}
		}
		return most_enjoy_book;
	}


}
