import oop.ex3.searchengine.Hotel;
import org.junit.*;

import static org.junit.Assert.*;


public class BoopingSiteTest {


	/** name file of hotels_tst1 which has only one city */
	private static final String DATASET_ONE_CITY = "hotels_tst1.txt";

	/** name file of hotels_tst2 which is empty */
	private static final String DATASET_EMPTY = "hotels_tst2.txt";

	/** name file of hotels_dataset which is the full dataset */
	private static final String DATASET_FULL = "hotels_dataset.txt";

	/** city name not in database */
	private static final String CITY_NOT_IN_DATABASE = "makore";

	/** city name in database */
	private static final String CITY_IN_DATABASE = "manali";

	/** representing the maximum star rating possible */
	private static final int MAXIMUM_STAR_RATING = 5;

	/** representing a valid double latitude degree [-90,90] */
	private static final double VALID_LATITUDE = 85.2;

	/** representing double value over 90 degree */
	private static final double OVER_90_LATITUDE = 91.1;

	/** representing double value below (-90) degree */
	private static final double BELOW_MINUS_90_LATITUDE = (-91.3);

	/** representing a valid double longitude degree [-180,180] */
	private static final double VALID_LONGITUDE = 150.4;

	/** representing double value over 180 degree */
	private static final double OVER_180_LONGITUDE = 180.8;

	/** representing double value below (-180) degree */
	private static final double BELOW_MINUS_180_LONGITUDE = (-183.6);

	/** representing the numbers of hotels in DATASET_ONE_CITY (all of them) */
	private static final int NUMBER_OF_HOTELS_IN_DATASET_ONE_CITY = 70;

	/** representing the numbers of hotels in DATASET_EMPTY (no hotels because it's empty) */
	private static final int NUMBER_OF_HOTELS_IN_DATASET_EMPTY = 0;

	/** representing the smallest string possible according to the alphabet order */
	private static final String SMALLEST_STRING_POSSIBLE = "a";

	/** representing the equal value is returned from comparator method */
	private static final int COMPARE_EQUAL = 0;


	BoopingSite boopingSite_empty = new BoopingSite(DATASET_EMPTY);
	BoopingSite boopingSite_one_city = new BoopingSite(DATASET_ONE_CITY);
	BoopingSite boopingSite_full = new BoopingSite(DATASET_FULL);


	/**
	 * reset the boopingSite after each test
	 */
	@Before
	public void reset() {
		boopingSite_empty = new BoopingSite(DATASET_EMPTY);
		boopingSite_one_city = new BoopingSite(DATASET_ONE_CITY);
		boopingSite_full = new BoopingSite(DATASET_FULL);
	}


	/**
	 * test constructor function
	 */
	@Test
	public void constructorTest() {
		assertNotNull(boopingSite_empty);
		assertNotNull(boopingSite_one_city);
		assertNotNull(boopingSite_full);
	}


	/**
	 * test getHotelsInCityByRating function
	 */
	@Test
	public void getHotelsInCityByRatingTest() {
		hotelsInCityByRatingTest(boopingSite_empty);
		hotelsInCityByRatingTest(boopingSite_one_city);
		hotelsInCityByRatingTest(boopingSite_full);
	}


	/**
	 * test getHotelsInCityByRating function with given BoopingSite object
	 */
	private void hotelsInCityByRatingTest(BoopingSite boopingSite) {

		// --------------------- invalid inputs ---------------------
		Hotel[] empty_hotel_array = new Hotel[0];
		// no hotels in the given city (==city not in database)
		assertArrayEquals(empty_hotel_array, boopingSite.getHotelsInCityByRating(CITY_NOT_IN_DATABASE));

		// given city name is null
		assertArrayEquals(empty_hotel_array, boopingSite.getHotelsInCityByRating(null));


		// --------------------- valid inputs ---------------------
		Hotel[] sorted_rating_hotels = boopingSite.getHotelsInCityByRating(CITY_IN_DATABASE);
		int last_star_rating = MAXIMUM_STAR_RATING;
		String last_hotel_name = SMALLEST_STRING_POSSIBLE;

		// moving across all returned hotels and check if their rating and name is in descending order
		for (Hotel hotel : sorted_rating_hotels) {
			// current rate is bigger than last
			assertFalse(hotel.getStarRating() > last_star_rating);
			// if they have the same rating => check current name is before the last name in alphabet order
			if (hotel.getStarRating() == last_star_rating) {
				assertFalse(last_hotel_name.compareTo(hotel.getPropertyName()) < COMPARE_EQUAL);
			}
			last_star_rating = hotel.getStarRating();
			last_hotel_name = hotel.getPropertyName();
		}
	}


	/**
	 * calculating the distance from (x1,y1) to (x2,y2) according to the Euclidean distance
	 * @param x1 : double of latitude
	 * @param y1 : double of longitude
	 * @return double of the distance
	 */
	private double calculateDistance(double x1, double y1) {
		return Math.sqrt(((BoopingSiteTest.VALID_LONGITUDE - y1) * (BoopingSiteTest.VALID_LONGITUDE - y1)) +
						 ((BoopingSiteTest.VALID_LATITUDE - x1) * (BoopingSiteTest.VALID_LATITUDE - x1)));
	}


	/**
	 * test getHotelsByProximity function
	 */
	@Test
	public void getHotelsByProximityTest() {
		hotelsByProximityTest(boopingSite_empty);
		hotelsByProximityTest(boopingSite_one_city);
		hotelsByProximityTest(boopingSite_full);
	}


	/**
	 * test getHotelsByProximity function with given BoopingSite object
	 */
	private void hotelsByProximityTest(BoopingSite boopingSite) {
		// --------------------- invalid inputs ---------------------
		Hotel[] empty_hotel_array = new Hotel[0];
		// invalid: latitude > 90
		assertArrayEquals(empty_hotel_array,
						  boopingSite.getHotelsByProximity(OVER_90_LATITUDE, VALID_LONGITUDE));

		// invalid: latitude < (-90)
		assertArrayEquals(empty_hotel_array,
						  boopingSite.getHotelsByProximity(BELOW_MINUS_90_LATITUDE, VALID_LONGITUDE));

		// invalid: longitude > 180
		assertArrayEquals(empty_hotel_array,
						  boopingSite.getHotelsByProximity(VALID_LATITUDE, OVER_180_LONGITUDE));

		// invalid: longitude < (-180)
		assertArrayEquals(empty_hotel_array,
						  boopingSite.getHotelsByProximity(VALID_LATITUDE, BELOW_MINUS_180_LONGITUDE));

		// --------------------- valid inputs ---------------------
		Hotel[] sorted_proximity_hotels = boopingSite.getHotelsByProximity(VALID_LATITUDE, VALID_LONGITUDE);

		// checking the number of hotels in sorted_proximity_hotels
		if (boopingSite == boopingSite_empty) {
			assertEquals(NUMBER_OF_HOTELS_IN_DATASET_EMPTY, sorted_proximity_hotels.length);

		} else if (boopingSite == boopingSite_one_city) {
			assertEquals(NUMBER_OF_HOTELS_IN_DATASET_ONE_CITY, sorted_proximity_hotels.length);
		}

		double last_distance = 0.0; // initialize to not important value
		int last_POIs_number = 0;
		boolean first_hotel = true;

		// moving across all hotels and check if their proximity and POIs are in the right order
		for (Hotel hotel : sorted_proximity_hotels) {
			if (first_hotel) {
				last_distance = calculateDistance(hotel.getLatitude(), hotel.getLongitude());
				last_POIs_number = hotel.getNumPOI();
				first_hotel = false;
				continue;
			}

			double curr_distance = calculateDistance(hotel.getLatitude(), hotel.getLongitude());
			// current distance is smaller than lat distance
			assertFalse(curr_distance < last_distance);
			// if they have the same distance => check if curr number of POI is bigger than last POI number
			if (curr_distance == last_distance) {
				assertFalse(last_POIs_number < hotel.getNumPOI());
			}

			last_distance = curr_distance;
			last_POIs_number = hotel.getNumPOI();
		}
	}


	/**
	 * test getHotelsByProximity function
	 */
	@Test
	public void getHotelsInCityByProximityTest() {
		hotelsInCityByProximityTest(boopingSite_empty);
		hotelsInCityByProximityTest(boopingSite_one_city);
		hotelsInCityByProximityTest(boopingSite_full);
	}


	/**
	 * test getHotelsInCityByProximity function with given BoopingSite object
	 */
	private void hotelsInCityByProximityTest(BoopingSite boopingSite) {
		// --------------------- invalid inputs ---------------------
		Hotel[] empty_hotel_array = new Hotel[0];
		// invalid: city not in database
		assertArrayEquals(empty_hotel_array, boopingSite
				.getHotelsInCityByProximity(CITY_NOT_IN_DATABASE, VALID_LATITUDE, VALID_LONGITUDE));

		// invalid: city's name is null
		assertArrayEquals(empty_hotel_array, boopingSite
				.getHotelsInCityByProximity(null, VALID_LATITUDE, VALID_LONGITUDE));

		// invalid: latitude > 90
		assertArrayEquals(empty_hotel_array, boopingSite
				.getHotelsInCityByProximity(CITY_IN_DATABASE, OVER_90_LATITUDE, VALID_LONGITUDE));

		// invalid: latitude < (-90)
		assertArrayEquals(empty_hotel_array, boopingSite
				.getHotelsInCityByProximity(CITY_IN_DATABASE, BELOW_MINUS_90_LATITUDE, VALID_LONGITUDE));

		// invalid: longitude > 180
		assertArrayEquals(empty_hotel_array, boopingSite
				.getHotelsInCityByProximity(CITY_IN_DATABASE, VALID_LATITUDE, OVER_180_LONGITUDE));

		// invalid: longitude < (-180)
		assertArrayEquals(empty_hotel_array, boopingSite
				.getHotelsInCityByProximity(CITY_IN_DATABASE, VALID_LATITUDE, BELOW_MINUS_180_LONGITUDE));


		// --------------------- valid inputs ---------------------
		Hotel[] sorted_proximity_hotels_by_city = boopingSite
				.getHotelsInCityByProximity(CITY_IN_DATABASE, VALID_LATITUDE, VALID_LONGITUDE);

		// checking the number of hotels in sorted_proximity_hotels
		if (boopingSite == boopingSite_empty) {
			assertEquals(NUMBER_OF_HOTELS_IN_DATASET_EMPTY, sorted_proximity_hotels_by_city.length);

		} else if (boopingSite == boopingSite_one_city) {
			assertEquals(NUMBER_OF_HOTELS_IN_DATASET_ONE_CITY, sorted_proximity_hotels_by_city.length);
		}


		double last_distance = 0.0; // initialize to not important value
		int last_POIs_number = 0;
		boolean first_hotel = true;

		// moving across all returned hotels and check if their proximity and POIs are in the right order
		for (Hotel hotel : sorted_proximity_hotels_by_city) {
			// hotel is not from the given city
			assertEquals(CITY_IN_DATABASE, hotel.getCity());
			if (first_hotel) {
				last_distance = calculateDistance(hotel.getLatitude(), hotel.getLongitude());
				last_POIs_number = hotel.getNumPOI();
				first_hotel = false;
				continue;
			}

			double curr_distance = calculateDistance(hotel.getLatitude(), hotel.getLongitude());
			// current distance is smaller than lat distance
			assertFalse(curr_distance < last_distance);
			// if they have the same distance => check if curr number of POI is bigger than last POI number
			if (curr_distance == last_distance) {
				assertFalse(last_POIs_number < hotel.getNumPOI());
			}

			last_distance = curr_distance;
			last_POIs_number = hotel.getNumPOI();
		}

	}

}
