/* Created on 05.04.2010 */
package dbhandler.util;

import java.awt.Toolkit;
import java.util.Scanner;

import org.apache.log4j.Logger;

import dbhandler.business.DbHandler;

public class Util
{
    private static Logger logger = Logger.getLogger(Util.class);

    /**
     * Liest die Auswahl ein.
     * @return int
     */
    public static int getChoice()
    {
        boolean eingabeOK = false;
        int choice = -1;
        Scanner sc = new Scanner(System.in);
        
        do
        {
            try
            {
                DbHandler.showMenu();
                choice = sc.nextInt();

                if (choice >= 0 & choice <= 5)
                    eingabeOK = true;
                else
                {
                    eingabeOK = false;
                    Toolkit.getDefaultToolkit().beep();
                }
            }
            catch (NumberFormatException nfe)
            {
                logger.error("Eingabe nicht korrekt!", nfe);
                eingabeOK = false;
                Toolkit.getDefaultToolkit().beep();
            }
        }
        while (!eingabeOK);

        return choice;
    }
}
