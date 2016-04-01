package threading.aufgabe2;

import java.util.Scanner;

public class Main {
	
	private static boolean timeWritten = false;
	public static boolean exit = false;
	
	public static void main( String[] args )
	{
		Thread tThread = new Thread( new TimeThread() );
		Thread dThread = new Thread( new DateThread() );
		Thread inputThread = new Thread( new InputThread() );
		
		tThread.start();
		dThread.start();
		inputThread.start();
		
	}
	
	public static boolean isTimeWritten()
	{
		return timeWritten;
	}
	
	public static void setTimeWritten( boolean timeWritten )
	{
		Main.timeWritten = timeWritten;
	}
	
}
