import oop.ex3.searchengine.Hotel;
import oop.ex3.searchengine.HotelDataset;

import java.util.*;


public class BoopingSite {


	/** representing double max latitude valid value of 90 */
	private static final double MAX_LATITUDE_VALID = 90.0;

	/** representing double max longitude valid value of 180 */
	private static final double MAX_LONGITUDE_VALID = 180.0;


	private final ArrayList<Hotel> hotels = new ArrayList<Hotel>();

	/**
	 * constructor of BoopingSite
	 * @param name: string of the name of the dataset
	 */
	public BoopingSite(String name) {
		Hotel[] array_hotels = HotelDataset.getHotels(name);
		hotels.addAll(Arrays.asList(array_hotels));
	}


	/**
	 * return an ArrayList of all the hotels in the database which located in the given valid city
	 * @param city: String of valid city name in the database
	 * @return ArrayList of all hotels in the given city
	 */
	private ArrayList<Hotel> allHotelsInCity(String city) {
		ArrayList<Hotel> hotelsInCity = new ArrayList<Hotel>();

		// filter for all the hotels in the given city
		for (Hotel hotel : hotels) {
			if (hotel.getCity().equals(city)) {
				hotelsInCity.add(hotel);
			}
		}
		return hotelsInCity;
	}


	/**
	 * returns an Hotel array located in the given city sorted from the highest star rating to the lowest. if
	 * two hotels have the same rating it will sort the two hotels according to the alphabet order of the
	 * property name.
	 * @param city: String of the city
	 * @return: sorted hotel array by rating in the given city
	 */
	public Hotel[] getHotelsInCityByRating(String city) {
		ArrayList<Hotel> hotelsInCity = new ArrayList<Hotel>();

		// city name is null
		if (city == null) {
			return hotelsInCity.toArray(new Hotel[0]);
		}

		hotelsInCity = allHotelsInCity(city);

		RatingCompare ratingCompare = new RatingCompare();
		Collections.sort(hotelsInCity, ratingCompare);
		Collections.reverse(hotelsInCity);

		return hotelsInCity.toArray(new Hotel[0]);
	}


	/**
	 * returns an Hotel array sorted by proximity from the given location in ascending order. if two hotels
	 * have the same proximity it will sort the two hotels according to the number of points-of-interest they
	 * are close to.
	 * @param latitude: given latitude
	 * @param longitude: given longitude
	 * @return: sorted hotel array by proximity
	 */
	public Hotel[] getHotelsByProximity(double latitude, double longitude) {
		ArrayList<Hotel> hotelsProximity = new ArrayList<Hotel>();

		// latitude or longitude are not valid
		if ((Math.abs(latitude) > MAX_LATITUDE_VALID) || (Math.abs(longitude) > MAX_LONGITUDE_VALID)) {
			return hotelsProximity.toArray(new Hotel[0]);
		}

		hotelsProximity = hotels;

		ProximityCompare proximityCompare = new ProximityCompare(latitude, longitude);
		Collections.sort(hotelsProximity, proximityCompare);

		return hotelsProximity.toArray(new Hotel[0]);
	}


	/**
	 * returns an Hotel array sorted by proximity from the given location in ascending order in the given
	 * city. if two hotels have the same proximity it will sort the two hotels according to the number of
	 * points-of-interest they are close to.
	 * @param city: String of city name
	 * @param latitude: given latitude
	 * @param longitude: given longitude
	 * @return: sorted hotel array by proximity in the given city
	 */
	public Hotel[] getHotelsInCityByProximity(String city, double latitude, double longitude) {
		ArrayList<Hotel> hotelsInCity = new ArrayList<Hotel>();

		// city name is null or latitude/longitude are not valid
		if ((city == null) || (Math.abs(latitude) > MAX_LATITUDE_VALID) ||
			(Math.abs(longitude) > MAX_LONGITUDE_VALID)) {
			return hotelsInCity.toArray(new Hotel[0]);
		}

		hotelsInCity = allHotelsInCity(city);

		ProximityCompare proximityCompare = new ProximityCompare(latitude, longitude);
		Collections.sort(hotelsInCity, proximityCompare);

		return hotelsInCity.toArray(new Hotel[0]);
	}
}
