
import java.sql.*;

public class Kunde
{

    public void erstelleTabelleKunde(Connection c) throws SQLException
    {
       Statement stmt = c.createStatement();
        String createTableSQL = "CREATE TABLE IF NOT EXISTS kunden " +
                "(ID INTEGER PRIMARY KEY AUTO_INCREMENT ," +
                " name char(30), " +
                " email char(50))";
        stmt.executeUpdate(createTableSQL);
        System.out.println("Kunde table created successfully");
    }
}
