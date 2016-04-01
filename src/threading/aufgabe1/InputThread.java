package threading.aufgabe1;

import java.util.Scanner;

public class InputThread implements Runnable {
	
	@Override
	public void run() 
	{
		while ( Main.number != 0 )
		{
			Scanner sc = new Scanner(System.in);
			Main.number = sc.nextInt();
		}
	}
}
