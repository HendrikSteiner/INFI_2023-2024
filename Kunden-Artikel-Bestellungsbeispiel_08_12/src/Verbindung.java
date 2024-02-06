import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Verbindung {
    private static final String URL = "jdbc:mysql://localhost/Kunde_Artikel_Bestellungsbeispiel";

    public static Connection erstelleDatenbank() throws SQLException, ClassNotFoundException {
        Connection c = null;

        Class.forName("com.mysql.cj.jdbc.Driver");
        c = DriverManager.getConnection(URL, "root", "root");
        System.out.println("Verbindung zur Datenbank hergestellt.");
        return c;
    }
}
