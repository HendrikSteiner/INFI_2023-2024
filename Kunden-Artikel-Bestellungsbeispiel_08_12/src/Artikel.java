import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Artikel
{
    public  void erstelleTabelleArtikel(Connection c) throws SQLException
    {
        Statement stmt = c.createStatement();
        String createTableSQL = "CREATE TABLE IF NOT EXISTS artikel " +
                "(ID INTEGER PRIMARY KEY AUTO_INCREMENT ," +
                " bezeichnung char(30), " +
                " preis double)";
        stmt.executeUpdate(createTableSQL);
        System.out.println("Artikel table created successfully");
    }
}
