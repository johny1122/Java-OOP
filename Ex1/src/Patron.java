/**
 * This class represents a patron, which has a title, author, year of publication and different literary
 * aspects.
 */
public class Patron {

	/**
	 * The first name of the patron
	 */
	final String firstName;

	/**
	 * The last name of the patron
	 */
	final String lastName;

	/**
	 * The weight the patron assigns to the comic aspects of books
	 */
	final int comicTendency;

	/**
	 * The weight the patron assigns to the dramatic aspects of books.
	 */
	final int dramaticTendency;

	/**
	 * The weight the patron assigns to the educational aspects of books.
	 */
	final int educationalTendency;

	/**
	 * The minimal literary value a book must have for this patron  to enjoy it.
	 */
	final int enjoymentThreshold;


	/*-----  Constructors  ------*/

	/**
	 * Creates a new Patron with the given characteristic
	 * @param patronFirstName The first name of the patron.
	 * @param patronLastName The last name of the patron.
	 * @param patronComicTendency The weight the patron assigns to the comic aspects of books.
	 * @param patronDramaticTendency The weight the patron assigns to the dramatic aspects of books.
	 * @param patronEducationalTendency The weight the patron assigns to the educational aspects of books.
	 * @param patronEnjoymentThreshold The minimal literary value a book must have for this patron  to enjoy
	 * 		it.
	 */
	Patron(String patronFirstName, String patronLastName, int patronComicTendency,
		   int patronDramaticTendency, int patronEducationalTendency, int patronEnjoymentThreshold) {
		firstName = patronFirstName;
		lastName = patronLastName;
		comicTendency = patronComicTendency;
		dramaticTendency = patronDramaticTendency;
		educationalTendency = patronEducationalTendency;
		enjoymentThreshold = patronEnjoymentThreshold;
	}

	/*-----  Instance Methods  ------*/

	/**
	 * Returns a string representation of the patron, which is a sequence of its first and last name,
	 * separated by a single white space. For example, if the patron's first name is "Ricky" and his last
	 * name is "Bobby", this method will return the String "Ricky Bobby".
	 * @return the String representation of this patron.
	 */
	String stringRepresentation() {
		return firstName + " " + lastName;
	}


	/**
	 * Returns the literary value this patron assigns to the given book.
	 * @param book The book to asses
	 * @return the literary value this patron assigns to the given book.
	 */
	int getBookScore(Book book) {
		return ((comicTendency * book.comicValue) + (dramaticTendency * book.dramaticValue) +
				(educationalTendency * book.educationalValue));
	}


	/**
	 * Returns true if this patron will enjoy the given book, false otherwise.
	 * @param book The book to asses.
	 * @return true of this patron will enjoy the given book, false otherwise.
	 */
	boolean willEnjoyBook(Book book) {
		return getBookScore(book) > enjoymentThreshold;
	}

}
