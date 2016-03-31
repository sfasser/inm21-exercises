package dbhandler.business.test;

import java.sql.SQLException;

import dbhandler.business.DbHandler;

public class Main
{
    public static void main(String[] args) throws SQLException
    {
        new DbHandler().execute();
    }
}