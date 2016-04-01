package threading.aufgabe1;

public class Main {
	
	public static int number = 1;
	
	public static void main( String[] args )
	{
		Thread input = new Thread( new InputThread() );
		Thread output = new Thread( new OutputThread() );
		
		input.start();
		output.start();
	}
	
}
