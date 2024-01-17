import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Lager {

    public static void erstelleTabelleLager(Connection c) throws SQLException {
        Statement stmt = c.createStatement();
        String createTableSQL = "CREATE TABLE IF NOT EXISTS lager " +
                "(artikel_id INTEGER PRIMARY KEY, " +
                " menge INTEGER)";
        stmt.executeUpdate(createTableSQL);
        System.out.println("Lager table created successfully");
    }

    public static void artikelBestellen(int artikelId, int bestellMenge, Connection c) throws SQLException {
        if (!istArtikelImLager(artikelId, c)) {
            System.out.println("Fehler: Artikel nicht im Lager vorhanden!");
            return;
        }

        Statement stmt = c.createStatement();
        stmt.executeUpdate("UPDATE lager SET menge = menge - " + bestellMenge + " WHERE artikel_id = " + artikelId);

        ResultSet rs = stmt.executeQuery("SELECT menge FROM lager WHERE artikel_id = " + artikelId);
        if (rs.next()) {
            int lagerMenge = rs.getInt("menge");
            if (lagerMenge == 0) {
                stmt.executeUpdate("DELETE FROM lager WHERE artikel_id = " + artikelId);
                System.out.println("Artikel erfolgreich bestellt und aus dem Lager entfernt!");
            } else {
                System.out.println("Bestellung erfolgreich durchgeführt!");
            }
        } else {
            System.out.println("Fehler beim Überprüfen der Lagermenge nach der Bestellung.");
        }
    }

    public static boolean istArtikelImLager(int artikelId, Connection c) throws SQLException {
        Statement stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM lager WHERE artikel_id = " + artikelId);
        return rs.next();
    }

    public static void lagerbestandanzeigen(Connection c) {
        try {
            Statement stmt = c.createStatement();
            String a3 = "SELECT * FROM lager";
            ResultSet resultSet3 = stmt.executeQuery(a3);
            System.out.println("Lager-Tabelle:");
            while (resultSet3.next()) {
                int artikel_id = resultSet3.getInt("artikel_id");
                int menge = resultSet3.getInt("menge");
                System.out.println("ArtikelID: " + artikel_id + ", Menge: " + menge);
            }
        } catch (SQLException e) {
            System.out.println("Fehler beim Eintragen Lageranzeigen: " + e.getMessage());
        }
    }
}
