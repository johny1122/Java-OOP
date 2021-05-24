import oop.ex3.searchengine.Hotel;

import java.util.Comparator;

/**
 * implementation of hotels proximity compare class
 */
public class ProximityCompare implements Comparator<Hotel>{

	/** representing bigger return value in comparator */
	private static final int BIGGER = 1;

	/** representing smaller return value in comparator */
	private static final int SMALLER = (-1);

	/** representing equal return value in comparator */
	private static final int EQUAL = 0;


	private final double latitude;
	private final double longitude;


	public ProximityCompare(double latitude, double longitude){
		this.latitude = latitude;
		this.longitude = longitude;
	}

	/**
	 * calculating the distance from (x1,y1) to (x2,y2) according to the Euclidean distance
	 * @param x1: double of latitude
	 * @param y1: double of longitude
	 * @param x2: double of latitude
	 * @param y2: double of longitude
	 * @return double of the distance
	 */
	private double calculateDistance(double x1, double y1, double x2, double y2) {
		return Math.sqrt(((y2 - y1) * (y2 - y1)) + ((x2 - x1) * (x2 - x1)));
	}


	/**
	 * @param hotel1: hotel object
	 * @param hotel2: hotel object
	 * @return 1 if hotel1's star rating is bigger, (-1) if hotel1's star rating is smaller. if the two
	 * 		hotels have the same star rating it will compare between their number of Point Of Interest and
	 * 		return a value to sort them in descending order
	 */
	public int compare(Hotel hotel1, Hotel hotel2) {
		double distance1 = calculateDistance(latitude, longitude,
											 hotel1.getLatitude(), hotel1.getLongitude());
		double distance2 = calculateDistance(latitude, longitude,
											 hotel2.getLatitude(), hotel2.getLongitude());
		if (distance1 < distance2){
			return SMALLER;
		}
		if (distance1 > distance2){
			return BIGGER;
		}

		// need descending order in POI so returns are opposite of normal compare
		if (hotel1.getNumPOI() < hotel2.getNumPOI()){
			return BIGGER;
		}
		if (hotel1.getNumPOI() > hotel2.getNumPOI()){
			return SMALLER;
		}
		return EQUAL;
	}

}
