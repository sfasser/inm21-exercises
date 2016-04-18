package threading.aufgabe3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import threading.aufgabe3.WeatherdataConsumer;
import threading.aufgabe3.WeatherData;
import threading.aufgabe3.WeatherMeasurementStation;


public class WeatherdataManager {
	/* Liste, in der die Wetterdaten temporär abgelegt werden */
	private static List<WeatherData> weatherdataList = new ArrayList<WeatherData>();

	/* Maximal erlaubte  Anzahl von Wetterdaten-Objekten in der Liste */
	public static final int maxListCapacity = 100;

	public static void main(String[] args) {
		int anzahlThreads = 15;
		Thread wStation = null;
		Thread wConsumer = null;

		for (int i = 0; i < anzahlThreads; i++) {
			wStation = new WeatherMeasurementStation(weatherdataList, " " + (i + 1));
			wStation.start();
		}

		do {
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} while (weatherdataList.size() == 0);

		for (int i = 0; i < anzahlThreads; i++) {
			wConsumer = new WeatherdataConsumer(weatherdataList, " " + (i + 1));
			wConsumer.start();
		}

		System.out.println("\nMain-Thread wird beendet!");
	}
}
