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

    public static void lagerbestandanzeigen(Connection c) {
        try {
            Statement stmt = c.createStatement();
            String sql = "SELECT a.ID AS artikel_id, a.bezeichnung, a.time, l.menge " +
                    "FROM artikel a " +
                    "LEFT JOIN lager l ON a.ID = l.artikel_id";
            ResultSet resultSet = stmt.executeQuery(sql);

            System.out.println("Lagerbestand:");
            while (resultSet.next()) {
                int artikel_id = resultSet.getInt("artikel_id");
                String bezeichnung = resultSet.getString("bezeichnung");
                java.sql.Time time = resultSet.getTime("time");
                int menge = resultSet.getInt("menge");

                System.out.println("ArtikelID: " + artikel_id + ", Bezeichnung: " + bezeichnung +
                        ", Erstellungszeitpunkt: " + time + ", Menge: " + menge);
            }
        } catch (SQLException e) {
            System.out.println("Fehler beim Anzeigen des Lagerbestands: " + e.getMessage());
        }
    }

    public static void erhoeheLagerbestand(int artikelId, int anzahl, Connection c) throws SQLException {
        Statement stmt = c.createStatement();

        String checkArtikelSQL = "SELECT * FROM lager WHERE artikel_id = " + artikelId;
        boolean artikelImLager = stmt.executeQuery(checkArtikelSQL).next();

        if (artikelImLager) {
            String updateLagerSQL = "UPDATE lager SET menge = menge + " + anzahl + " WHERE artikel_id = " + artikelId;
            stmt.executeUpdate(updateLagerSQL);
        } else {
            String insertLagerSQL = "INSERT INTO lager (artikel_id, menge) VALUES (" + artikelId + ", " + anzahl + ")";
            stmt.executeUpdate(insertLagerSQL);
        }

        stmt.close();
    }
}
