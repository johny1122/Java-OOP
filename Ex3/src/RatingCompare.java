import oop.ex3.searchengine.Hotel;

import java.util.Comparator;


/**
 * implementation of hotels rating star compare class
 */
public class RatingCompare implements Comparator<Hotel> {


	/** representing bigger return value in comparator */
	private static final int BIGGER = 1;

	/** representing smaller return value in comparator */
	private static final int SMALLER = (-1);


	/**
	 * @param hotel1: hotel object
	 * @param hotel2: hotel object
	 * @return 1 if hotel1's star rating is bigger, (-1) if hotel1's star rating is smaller. if the two
	 * 		hotels have the same star rating it will compare between their names with String.compareTo()
	 */
	public int compare(Hotel hotel1, Hotel hotel2) {
		if (hotel1.getStarRating() < hotel2.getStarRating()) {
			return SMALLER;
		}
		if (hotel1.getStarRating() > hotel2.getStarRating()) {
			return BIGGER;
		}
		return hotel1.getPropertyName().compareTo(hotel2.getPropertyName());
	}

}
