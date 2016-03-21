package business;

import java.awt.Toolkit;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import org.apache.log4j.Logger;

abstract public class AbstractDbHandler
{
    private static Logger logger = Logger.getLogger(AbstractDbHandler.class);
    protected static Connection con = null;
    protected static Statement stmt = null;

    public AbstractDbHandler()
    {
        
            
				// TODO - Properties einlesen
				Properties p = new Properties();
				InputStream is;
				try 
				{
					is = new FileInputStream( "C:\\workspace\\INM21\\src\\db.properties" );
					p.load(is);
				} 
				catch (Exception ex) 
				{
					logger.error("File not found", ex);
				}

				

				// TODO - JDBC-Treiber, URL, Benutzername und Kennwort auslesen
				String driver = p.getProperty("driver");
				String url = p.getProperty("url");
				String user = p.getProperty("username");
				String pwd = p.getProperty("password");

				// TODO - Treiber laden
				try {
					Class.forName(driver);
				} catch (ClassNotFoundException cnfe) {
					logger.error("Driver could not be loaded", cnfe);
				}

				// TODO - Connection aufbauen
				try {
					con = DriverManager.getConnection(url, user, pwd);
				} catch (SQLException sqle) {
					logger.error("Connection failed", sqle);
				}

				// TODO - Statement kreieren
				try {
					stmt = con.createStatement();
				} catch (SQLException sqle) {
					logger.error("Statement could not be created", sqle);
				}
            

    }

    /**
     * Baut die Verbindung zur Datenbank ab
     * @throws SQLException
     */
    protected void close() throws SQLException
    {

        // TODO - Statement schliessen
        stmt.close();
        

        // TODO - Connection schliessen
        con.close();
        
    }

    /**
     * Liefert ein ResultSet zurueck als Ergebnismenge der Anfrage
     * @param query String SQL-SELECT-String
     * @return ResultSet
     * @throws SQLException
     */
    public ResultSet getResultSet(String query) throws SQLException
    {
        // TODO
        return stmt.executeQuery(query);



    }

    /**
     * Fuegt ein Tupel in die Tabele ein
     * @param query String SQL-INSERT-String
     * @return int - anzahl der eingefuegten Tupels
     * @throws SQLException
     */
    public int insert(String query) throws SQLException
    {
  
        // TODO
        
        return stmt.executeUpdate(query);
    }

    /**
     * Aendert ein vorhandenes Tupel
     * @param query String SQL-UPDATE-String
     * @return int - Anzahl der geaenderten Tupels
     * @throws SQLException
     */
    public int update(String query) throws SQLException
    {
        // TODO
        
        
        
        return stmt.executeUpdate(query);
    }

    /**
     * Loescht ein oder mehrere Tupel
     * @param query String SQL-DELETE-String
     * @return int - Anzahl der geloeschten Tupels
     * @throws SQLException
     */
    public int delete(String query) throws SQLException
    {
        // TODO


        return stmt.executeUpdate(query);
    }

    /**
     * Gibt ein akustisches Signal aus
     */
    protected void beep()
    {
        Toolkit.getDefaultToolkit().beep();
    }

    /**
     * Liefert Db-Metadaten zurück.
     * @return databaseMetaData DatabaseMetaData
     * @throws SQLException
     */
    public DatabaseMetaData getDatabaseMetaData() throws SQLException
    {
        return con.getMetaData();
    }

    /**
     * Liefert Namen aller Tabellen, die zugänglich sind (Schema public) zurück.
     * @return tableNames ArrayList<String>
     * @throws SQLException
     */
    public ArrayList<String> getAllTableNames() throws SQLException
		{
				ArrayList<String> tableNames = new ArrayList<String>();

				try
				{
						DatabaseMetaData dbMeta = con.getMetaData();

						ResultSet rs = dbMeta.getTables(null, null, null,
										new String[] { "TABLE" });

						while (rs.next())
						{
								tableNames.add(rs.getString("TABLE_NAME"));
						}
				}
				catch (SQLException e)
				{
						logger.error("Fehler bei der Anzeige von Metadaten!", e);
						throw e;
				}

				return tableNames;
    }

    /**
     * Liefert Spaltennamen und deren SQL-Typen in einem HashMap.
     * @param tableName String
     * @return columnsData HashMap<String, String> Mape mit Spaltendaten, wobei
     *         der Spaltenname als Schlüssel (key) und SQL-Typ der Spalte als
     *         Wert (value) behandelt werden.
     * @throws SQLException
     */
    public HashMap<String, String> getColumnsData(String tableName)
            throws SQLException
    {
        HashMap<String, String> map = new HashMap<String, String>();

        String query = "SELECT * FROM " + tableName;

        ResultSet rs = stmt.executeQuery(query);

        ResultSetMetaData rsmd = rs.getMetaData();

        if (rs.next())
        {
            int cnt = rsmd.getColumnCount();

            for (int i = 1; i <= cnt; i++)
            {
                String cName = rsmd.getColumnName(i);
                String cType = rsmd.getColumnTypeName(i);

                map.put(cName, cType);
            }
        }

        return map;
    }

    /**
     * Zeigt Namen der übergebenen Tabellen an.
     * @param tableNames ArrayList<String>
     */
    public abstract void showTableNames(ArrayList<String> tableNames);

    /**
     * Zeigt Namen und Typen aller Spalten der Tabelle an.
     * @param tableNames ArrayList<String>
     */
    public abstract void showColumnsMetaData(HashMap<String, String> metaDataMap);

    /**
     * Zeigt den Inhalt eines ResultSets an
     * @param ResultSet
     */
    abstract public void showResultSet(ResultSet rs);
}
