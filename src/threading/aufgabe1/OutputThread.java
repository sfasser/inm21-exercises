package threading.aufgabe1;

public class OutputThread implements Runnable {

	//public static int number;
	
	/*public OutputThread( int number )
	{
		this.number = number;
	}*/
	
	@Override
	public void run() {
		while (Main.number != 0)
		{
			System.out.println( Main.number );
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	/*public void setNumber( int number )
	{
		this.number = number;
		run = number == 0 ? false : true;
	}*/
	
}
