import oop.ex3.searchengine.Hotel;
import oop.ex3.searchengine.HotelDataset;
import oop.ex3.spaceship.*;

import java.util.Arrays;

// Test Behavior
//*********************************************************************************************
public class main {


	static double calculateDistance(double x1, double y1, double x2, double y2) {
		return Math.sqrt(((y2 - y1) * (y2 - y1)) + ((x2 - x1) * (x2 - x1)));
	}

	public static void main(String[] args) {
//		Hotel[] array_hotels = HotelDataset.getHotels("hotels_tst1.txt");
//
//		ProximityCompare proximityCompare = new ProximityCompare(32.2236026, 77.1858995);
//		Arrays.sort(array_hotels, proximityCompare);
//		for (Hotel hotel : array_hotels) {
//			System.out.println(calculateDistance(hotel.getLatitude(), hotel.getLongitude(), 32.2236026,
//												 77.1858995) + " , " + hotel.getNumPOI());
		Item item1 = new Item ("hat", 1);
		Item item2 = new Item ("coat", 2);

		LongTermStorage lts = new LongTermStorage();
		lts.addItem(item1, 1000);

		Locker l1 = new Locker(lts, 3, null);
		l1.addItem(item2, 1);


	}
}