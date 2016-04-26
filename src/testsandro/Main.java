/* Sandro Sandro Sandro Sandro Sandro Sandro Sandro Sandro Sandro Sandro Sandro Sandro Sandro Sandro Sandro Sandro Sandro Sandro Sandro Sandro Sandro Sandro Sandro Sandro Sandro Sandro */

package testsandro;

import java.awt.Toolkit;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

import FinanceCalcInterface.FinanceCalc;
import rmi.aufgabe1.Finanzkalkulator;

public class Main {

	public static void main( String[] args )
	{
		try {
			IFinanceCalc financeCalc = new Finanzkalkulator();
			
			Registry reg = LocateRegistry.createRegistry(54321);
			
			if ( reg != null )
			{
				reg.rebind("Kari", financeCalc);
			}
			
			System.out.println( "Kari bound" );
			System.in.read();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	private FinanceCalc kalkulator = null;

	/*public static void main(String[] args) {

		try {
			new Main().execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

	private void execute() throws Exception {
		try {

			/* Referenz auf den Kalkulator holen */
			System.setProperty("java.security.policy", "lars.policy");
			System.setSecurityManager(new SecurityManager());
			String url = "rmi://10.4.113.30:55333/CalcObject";
			
			kalkulator = (FinanceCalc) Naming.lookup(url);
			
			// Ausführung starten
			int w = -1;

			do {
				w = getMenuAuswahl();

				switch (w) {
				case 'K':
					berechneEndkapital();
					break;

				case 'B':
					berechneBarwert();
					break;

				case 'A':
					berechneAnuitaeten();
					break;

				case 'E':
					System.out.println("\nProgramm wird beendet!\n");

				}
			} while (w != 'E');
		} catch (Exception e) {
			Toolkit.getDefaultToolkit().beep();
			System.out.println("\nVerbindung mit Server wurde abgebrochen!\n");
		}

	}

	/* Hilfsmethoden */
	public void berechneAnuitaeten() throws Exception {
		Scanner sc = new Scanner(System.in);

		try {
			System.out.print("\nStartkapital:        ");
			double sKap = sc.nextDouble();

			System.out.print("\nAnzahl Jahre:        ");
			double anzJahre = sc.nextDouble();

			System.out.print("\nZinssatz in Prozent: ");
			double zSatz = sc.nextDouble();

			/* Berechnung durchführen */
			double anuitaet = kalkulator.getAnuitaet(sKap, zSatz, anzJahre);

			/* Ausgabe */
			System.out.printf("\nAnuitaet:\n  jährlich):  %.2f", anuitaet);
			System.out.printf("\n  monatlich): %.2f\n", anuitaet / 12);
		} catch (RuntimeException e) {
			String msg = "\nFehler bei der Eingabe!";
			System.out.println(msg + "\n");
		}
	}

	public void berechneBarwert() throws Exception {
		Scanner sc = new Scanner(System.in);

		try {
			System.out.print("\nEndwert:       ");
			double endKapital = sc.nextDouble();

			System.out.print("\nAnzahl Jahre:  ");
			double anzJahre = sc.nextDouble();

			System.out.print("\nZinssatz [%]:  ");
			double zSatz = sc.nextDouble();

			/* Berechnung durchführen */
			double barwert = kalkulator.getBarwert(endKapital, zSatz / 100, anzJahre);

			/* Ausgabe */
			System.out.printf("\nBarwert: %.2f\n", barwert);
		} catch (RuntimeException e) {
			String msg = "\nFehler bei der Eingabe!";
			System.out.println(msg + "\n");
		}
	}

	public void berechneEndkapital() throws Exception {
		Scanner sc = new Scanner(System.in);

		try {
			System.out.print("\nStartkapital:  ");
			double sKap = sc.nextDouble();

			System.out.print("\nAnzahl Jahre:  ");
			double anzJahre = sc.nextDouble();

			System.out.print("\nZinssatz [%]:  ");
			double zSatz = sc.nextDouble();

			/* Berechnung durchführen */
			double endKapital = kalkulator.getEndkapital(sKap, zSatz / 100, anzJahre);

			/* Ausgabe */
			System.out.printf("\nEndkapital: %.2f\n", endKapital);
		} catch (RuntimeException e) {
			String msg = "\nFehler bei der Eingabe!";
			System.out.println(msg + "\n");
		}
	}

	private static void showMenu() {
		String menu = "\nEndkapital [ K ]     Barwert [ B ]     Anuitaeten [ A ]     Beenden [ E ]";

		System.out.println(menu);

		System.out.print("\nIhre Wahl: ");
	}

	private static int getMenuAuswahl() {
		int wahl = -1;
		String input = "";
		boolean inputValid = false;

		Scanner sc = new Scanner(System.in);

		do {
			showMenu();

			try {
				// Wahl einlesen
				input = sc.nextLine();

				wahl = Character.toUpperCase(input.charAt(0));

				// Input validieren
				inputValid = wahl == 'K' || wahl == 'B' || wahl == 'A' || wahl == 'E';

				if (!inputValid) {
					System.out.println("\nEingabe nicht korrekt!");
				}
			} catch (RuntimeException e) {
				// Ctrl + C oder so was ...
				break;
			}
		} while (!inputValid);

		return wahl;
	}

}

