import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Verbindung {
    private static final String URL = "jdbc:mysql://localhost/Kunde_Artikel_Bestellungsbeispiel";

    public static Connection erstelleDatenbank() throws SQLException, ClassNotFoundException {
        Connection c = null;
        Statement stmt = null;

        Class.forName("com.mysql.cj.jdbc.Driver");
        c = DriverManager.getConnection(URL, "root", "root");
        stmt = c.createStatement();
        stmt.executeUpdate("create database if not exists Kunde_Artikel_Bestellungsbeispiel");
        stmt.executeUpdate("use Kunde_Artikel_Bestellungsbeispiel");
        System.out.println("Opened mysql successfully");

        return c;
    }
}
