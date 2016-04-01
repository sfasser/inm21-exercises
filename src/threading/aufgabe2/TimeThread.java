package threading.aufgabe2;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeThread implements Runnable {

	@Override
	public void run() {
		while(!Main.exit)
		{
			if ( !Main.isTimeWritten() )
			{
				SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");	
				System.out.print( timeFormat.format(new Date() ) + " " + "am " );
				Main.setTimeWritten(true);
			}
			
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}

}
