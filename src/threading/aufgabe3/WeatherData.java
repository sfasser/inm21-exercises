/* Created on 11.04.2010 */
package threading.aufgabe3;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;

public class WeatherData implements Serializable {
  private static long cnt = 0;

  private static final Logger logger = Logger.getLogger(WeatherData.class);

  private static final long serialVersionUID = 2292806872730480184L;

  private String stationId;
  private GregorianCalendar timestamp;
  private long dataId;
  private double curentTemperature;
  private double windSpeed;
  private int windDirection;

  /**
   * @param stationId
   *            String
   * @param curentTemperature
   *            double
   * @param windSpeed
   *            double
   * @param windDirection
   *            int
   */
  public WeatherData(String stationId, GregorianCalendar timestamp,
      double curentTemperature, double windSpeed, int windDirection) {
    this.timestamp = timestamp;
    this.stationId = stationId;
    synchronized (WeatherData.class) {
      dataId = ++cnt;
      cnt %= 1000000;
    }
    this.curentTemperature = curentTemperature;
    this.windSpeed = windSpeed;
    this.windDirection = windDirection;

    if (curentTemperature > 31 || curentTemperature < 9) {
      logger.error("Temperatur: " + curentTemperature);
    }
  }

  /**
   * @return stationId String
   */
  public String getStationId() {
    return stationId;
  }

  /**
   * @param stationId
   *            String
   */
  public void setStationId(String stationId) {
    this.stationId = stationId;
  }

  /**
   * @return timestamp GregorianCalendar
   */
  public GregorianCalendar getTimestamp() {
    return timestamp;
  }

  /**
   * @return the dataId
   */
  public long getDataId() {
    return dataId;
  }

  /**
   * @param timestamp
   *            GregorianCalendar
   */
  public void setTimestamp(GregorianCalendar timestamp) {
    this.timestamp = timestamp;
  }

  /**
   * @return curentTemperature double
   */
  public double getCurentTemperature() {
    return curentTemperature;
  }

  /**
   * @param curentTemperature
   *            double
   */
  public void setCurentTemperature(double curentTemperature) {
    this.curentTemperature = curentTemperature;
  }

  /**
   * @return windSpeed double
   */
  public double getWindSpeed() {
    return windSpeed;
  }

  /**
   * @param windSpeed
   *            double
   */
  public void setWindSpeed(double windSpeed) {
    this.windSpeed = windSpeed;
  }

  /**
   * @return windDirection int
   */
  public int getWindDirection() {
    return windDirection;
  }

  /**
   * @param windDirection
   *            int
   */
  public void setWindDirection(int windDirection) {
    this.windDirection = windDirection;
  }

  /*
   * (non-Javadoc)
   *
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    int bevoreDataLength = 15;

    String wRichtung = "";

    switch (windDirection) {
    case 1:
      wRichtung = "Norden";
      break;
    case 2:
      wRichtung = "Nord-Osten";
      break;
    case 3:
      wRichtung = "Osten";
      break;
    case 4:
      wRichtung = "Sued-Osten";
      break;
    case 5:
      wRichtung = "Sueden";
      break;
    case 6:
      wRichtung = "Sued-West";
      break;
    case 7:
      wRichtung = "West";
      break;
    case 8:
      wRichtung = "Nord-West";
      break;
    }

    DecimalFormat df = new DecimalFormat("00.0");
    SimpleDateFormat sdf = new SimpleDateFormat(
        "dd.MM.yyyy ' um ' hh:mm:ss:SSS");
    String timestampAsString = sdf.format(timestamp.getTime());

    String praefixString = "Wetterstation " + stationId + ": ";

    String postfixString = "";

    for (int i = 0; i < (bevoreDataLength - praefixString.length()); i++) {
      postfixString += " ";
    }

    return praefixString + postfixString + "[Id: " + dataId + ", Time: "
        + timestampAsString + ", Temperatur: "
        + df.format(curentTemperature) + " °C, Windstaerke: "
        + df.format(windSpeed) + " kmh, Windrichtung: " + wRichtung
        + "]";

    //return /*praefixString + postfixString +*/ "[Data-Id: " + dataId + "]";
  }

  /*
   * (non-Javadoc)
   *
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object p) {

    if (this == p) {
      return true;
    } else {
      if (p instanceof WeatherData) {
        WeatherData d = (WeatherData) p;
        return stationId.equals(d.stationId)
            && timestamp.equals(d.timestamp)
            && curentTemperature == d.curentTemperature
            && windSpeed == windSpeed
            && windDirection == d.windDirection
            && windSpeed == d.windSpeed;
      } else {
        return false;
      }
    }
  }

}
