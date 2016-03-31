package dbhandler.business;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;

import dbhandler.util.Util;

public class DbHandler extends AbstractDbHandler
{
    private static Logger logger = Logger.getLogger(DbHandler.class);

    /**
     * zeigt die Auswahl an
     */
    public static void showMenu()
    {
        String menu;

        menu = "\nMETADATA < 0 >   SELECT < 1 >   INSERT < 2 >   UPDATE < 3 >   DELETE < 4 >    BEENDEN < 5 >";
        System.out.print(menu + "\n\nIhre Wahl: ");
    }

    /**
     * steuert die DB-Aktivitaeten
     */
    public void execute()
    {
        int auswahl = -1;
        String query = "";

        do
        {
            try
            {
                auswahl = Util.getChoice();
           
                switch (auswahl)
                {
                    case 0:
                        showMetadata();
                        break;

                    case 1:
                        query = getQuery("SELECT - Query:\n===============\n");
                        if (query != null)
                            showResultSet(getResultSet(query));
                        break;

                    case 2:
                        query = getQuery("INSERT INTO - Query:\n====================\n");
                        if (query != null)
                            insert(query);
                        break;

                    case 3:
                        query = getQuery("UPDATE - Query:\n===============\n");
                        if (query != null)
                            update(query);
                        break;

                    case 4:
                        query = getQuery("DELETE - Query:\n===============\n");
                        if (query != null)
                            delete(query);
                }

            }
            catch (SQLException e)
            {
                logger.error("Fehler beim Zugriff auf Datenbank!\n", e);
                System.out.println("Ein Fehler ist aufgetreten.");
            }
            catch (Exception e)
            {
                logger.error("Fehler beim Zugriff auf Datenbank!\n", e);
                System.out.println("Ein Fehler ist aufgetreten.");
            }
        }
        while (auswahl != 5);

        /* Verbindung zur Datenbank schliessen */
        try
        {
            close();
        }
        catch (SQLException e)
        {
            logger.error("Fehler beim Zugriff auf Datenbank!\n", e);
            beep();
        }
    }

    /**
     * liest Query von der Tastatur ein
     * @param String
     * @return String
     */
    private String getQuery(String s)
    {
        String tmp = "";
        System.out.print("\n" + s);

        try
        {
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    System.in));

            tmp = in.readLine();

            System.out.println();

            return tmp;
        }
        catch (IOException ioe)
        {
            return null;
        }
    }

    @Override
    public void showResultSet(ResultSet rs)
    {
       
        try {
			while( rs.next() )
			{
				System.out.println( rs.getString("name") + "\t\t" +
						rs.getString("vorname") + "\t\t" +
						rs.getString("strasse") + "\t\t" +
						rs.getInt("plz") + " " + rs.getString("ort") + "\t\t" + 
						rs.getString("telefon"));
				
			}
		} catch (SQLException e) {
			logger.error("I believe I can fly", e);
		}
        
        
        
        
    }

    /*
     * (non-Javadoc)
     * @see AbstractDbHandler#showColumnsMetaData(java.util.HashMap)
     */
    @Override
    public void showColumnsMetaData(HashMap<String, String> metaDataMap)
    {
        Set<String> set = metaDataMap.keySet();

        TreeSet<String> ts = new TreeSet<String>(set);

        System.out.println("COLUMNS:");

        for (String s : ts)
        {
            String dTyp = metaDataMap.get(s);

            System.out.println("  NAME: " + s + ", TYPE: " + dTyp);
        }
    }

    /*
     * (non-Javadoc)
     * @see AbstractDbHandler#showTableNames(java.util.ArrayList)
     */
    @Override
    public void showTableNames(ArrayList<String> tableNames)
    {
        for (String s : tableNames)
        {
            System.out.println("TABLE-NAME: " + s);
        }
    }

    /**
     * Zeigt Tabellen mit deren Spalten (Namen und Typen) an.
     * @throws SQLException
     */
    public void showMetadata() throws SQLException
    {
        ArrayList<String> tables = getAllTableNames();

        for (String table : tables)
        {
            System.out.println("\nTABLE: " + table);
            HashMap<String, String> m = getColumnsData(table);
            showColumnsMetaData(m);
        }
    }

    /**
     * Schliesst die Verbindung zur Datenban und die Logdatei.
     */
    protected void close() throws SQLException
    {
        super.close();
    }
}