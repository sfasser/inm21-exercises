package rmi.aufgabe1;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Finanzkalkulator extends UnicastRemoteObject implements IFinanceCalc{
    
    protected Finanzkalkulator() throws RemoteException {
		
	}
	/**
     * Berechnet das Endkapital.
     * @param startKapital double
     * @param zinssatz double
     * @param anzahlJahre double
     * @return endkapital double
     * @throws Exception
     */
	public double getEndkapital(double startKapital, double zinssatz,
			double anzahlJahre) throws RemoteException {

		double q = 1 + zinssatz;
		return startKapital * Math.pow(q, anzahlJahre);
	}
	/**
     * Berechnet den Barwert.
     * @param endKapital double
     * @param zinssatz double
     * @param anzahlJahre double
     * @return barwert double
     * @throws Exception
     */
	public double getBarwert(double endKapital, double zinssatz,
			double anzahlJahre) throws RemoteException {

		double q = 1 + zinssatz;
		return endKapital * Math.pow(q, ((-1) * anzahlJahre));
	}


    /**
     * Berechnet die Anuität.
     * @param kapital double
     * @param zinssatz double
     * @param anzahlJahre double
     * @return anuitaet double
     * @throws Exception
     */
	public double getAnuitaet(double kapital, double zinssatz,
			double anzahlJahre) throws RemoteException {

		double q = 1 + zinssatz/100;
        return kapital * (Math.pow(q, anzahlJahre) * (q - 1))
                / (Math.pow(q, anzahlJahre) - 1);
	}
}
