/* Created on 11.04.2010 */
package threading.aufgabe3;

import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;

import threading.aufgabe3.WeatherdataManager;
import threading.aufgabe3.WeatherData;

public class WeatherMeasurementStation extends Thread {

	private static final Logger logger = Logger
			.getLogger(WeatherMeasurementStation.class);

	private String stationId = null;

	private double cTemp = 15;
	private Random rndTemp = new Random();
	private Random rndWind = new Random();
	private Random rndWindDir = new Random();
	private String sign = "+";

	/* Zeit, die der aktuelle Thread schlafen gelegt wird */
	private int sleepTimeInMillis = 300;

	/* Liste, in der die generierten Wetterdaten abzulegen sind */
	private List<WeatherData> weatherdataList;

	public WeatherMeasurementStation(List<WeatherData> weatherdataList,
			String stationId) {
		this.weatherdataList = weatherdataList;
		this.stationId = stationId;
	}

	/**
	 * @return the stationId
	 */
	public String getStationId() {
		return stationId;
	}

	/**
	 * @param stationId
	 *            the stationId to set
	 */
	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	public void run() {

		WeatherData wData = null;

		while (true) {
			if (weatherdataList.size() < WeatherdataManager.maxListCapacity) {

				/* Wetterdaten generieren */
				wData = getWeatherData();

				/* Wetterdaten in das Container-Objekt ablegen */
				weatherdataList.add(wData);

				try {
					Thread.sleep(sleepTimeInMillis);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			} else {
				/* Schlafen, bis es platz gibt */
				try {
					String msg = "\nWETTER-STATION " + this.getStationId()
					+ " muss warten, da die Liste voll!";

					logger.info(msg);
					System.out.println(msg);
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Hilfsmethode zum Generieren von Wetterdaten
	 *
	 * @return weatherData WeatherData
	 */
	private WeatherData getWeatherData() {

		double temperature = 0;

		/* Windrichtung */
		int windDirection = 1 + rndWindDir.nextInt(8);
		/* Temperatur-Änderung */
		double delta = rndTemp.nextDouble();

		if (delta < 0.75 && cTemp > 10) {
			delta *= -1;
		}

		if (cTemp > 30) {
			sign = "-";
		} else if (cTemp < 10) {
			sign = "+";
		} else {
			// keine Änderung
		}

		if (sign.equals("-")) {
			delta = -delta;
		}

		/* Neue Temperatur */
		temperature = cTemp + delta;

		/* Den neuen Temperaturwert 'in Erinnerung' behalten */
		cTemp = temperature;
		/* Windgeschwindigkeit */
		double windSpeed = rndWind.nextDouble() * 30;

		String strTemp = String.format("%6.1f", temperature);
		String strWinSpeed = String.format("%6.1f", windSpeed);

		double tempFormated = Double.parseDouble(strTemp);
		double windSpeedFormated = Double.parseDouble(strWinSpeed);

		GregorianCalendar gc = new GregorianCalendar();
		gc.setTimeInMillis(System.currentTimeMillis());

		return new WeatherData(stationId, gc, tempFormated, windSpeedFormated,
				windDirection);
	}
}
