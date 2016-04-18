package threading.aufgabe3;

import java.awt.Toolkit;
import java.util.List;

import org.apache.log4j.Logger;

import threading.aufgabe3.WeatherData;

public class WeatherdataConsumer extends Thread {
  private static final Logger logger = Logger
      .getLogger(WeatherdataConsumer.class);

  /* Liste, aus der die Wetterdaten abgeholt (konsumiert) werden */
  private List<WeatherData> weatherdataList;

  private String consumerId;

  public WeatherdataConsumer(List<WeatherData> weatherdataList,
      String consumerId) {
    this.weatherdataList = weatherdataList;
    this.consumerId = consumerId;
  }

  public void run() {
    WeatherData d = null;
    boolean suc = false;
    long dataId = 0;

    while (true) {
      try {
          if (weatherdataList.size() > 0) {
        	synchronized ( weatherdataList )
        	{
	            d = weatherdataList.get(0);
	            dataId = d.getDataId();
	
	            String msg = "\nCONSUME:\nConsumer " + consumerId
	                + " --> Datensatz [" + dataId
	                + "] wird konsumiert!" + "\tAnz. Elemente: "
	                + weatherdataList.size() + "\n" + d.toString();
	            System.out.println(msg);
	            logger.info(msg);
	
	            logger.info("\nREMOVE:\nConsumer " + consumerId
	                + " versucht den Datensatz [" + dataId
	                + "] zu entfernen:");
	
	            suc = weatherdataList.remove(d);
	
	            if (!suc) {
	              throw new Exception(
	                  "Objekt "
	                      + d
	                      + " konte in der Liste nicht mehr gefunden werden!");
	            } else {
	              logger.info("\nConsumer " + consumerId
	                  + " hat den Datensatz [" + dataId
	                  + "] entfernt!\n");
	            }
	
	            Thread.sleep(1000);
        	}
          } else {
            try {
              System.out.println("\nConsumer " + consumerId
                  + " --> MUSS WARTEN (Anz. Elemente: "
                  + weatherdataList.size() + ")");
              Thread.sleep(1000);
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
          }

        Thread.sleep(100);

      } catch (Exception e) {
        logger.error("\nConsumer: " + consumerId
            + " hat den Datensatz [" + dataId
            + "] NICHT entfernen können!\n");

        Toolkit.getDefaultToolkit().beep();
      }
    }
  }
}
