package threading.aufgabe2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class InputThread implements Runnable {
	
	@Override
	public void run() 
	{
		while ( !Main.exit )
		{
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	        try 
	        {
	            if ( br.read() == 13 ) //Check if key pressed is the "Enter"-key
	            {
	            	Main.exit = true;
	            }
	        }
	        catch (IOException e)
	        {
	            e.printStackTrace();
	        }
		}
	}
}
