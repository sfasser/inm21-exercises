package threading.aufgabe2;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateThread implements Runnable {

	@Override
	public void run() {
		while(!Main.exit)
		{
			try {
				Thread.sleep(100);
				if ( Main.isTimeWritten() )
				{
					SimpleDateFormat timeFormat = new SimpleDateFormat("dd.MM.yyyy");	
					System.out.println( timeFormat.format( new Date() ) );
					Main.setTimeWritten( false );
					Thread.sleep(2000);
				}
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}

}
