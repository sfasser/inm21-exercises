package rmi.aufgabe1;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IFinanceCalc extends Remote 
{
	double getEndkapital( double startKapital, double zinssatz, double anzahlJahre ) throws RemoteException;
	
	double getBarwert(double endKapital, double zinssatz, double anzahlJahre) throws RemoteException;
	
	double getAnuitaet(double kapital, double zinssatz, double anzahlJahre) throws RemoteException;
}
