package business.test;

import java.sql.SQLException;

import business.DbHandler;

public class Main
{
    public static void main(String[] args) throws SQLException
    {
        new DbHandler().execute();
    }
}