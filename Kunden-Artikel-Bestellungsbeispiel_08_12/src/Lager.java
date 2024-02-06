import java.sql.*;

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
        String checkArtikelSQL = "SELECT * FROM lager WHERE artikel_id = ?";
        String updateLagerSQL = "UPDATE lager SET menge = menge + ? WHERE artikel_id = ?";
        String insertLagerSQL = "INSERT INTO lager (artikel_id, menge) VALUES (?, ?)";

        try (PreparedStatement checkStmt = c.prepareStatement(checkArtikelSQL)) {
            checkStmt.setInt(1, artikelId);
            ResultSet resultSet = checkStmt.executeQuery();

            if (resultSet.next()) {
                try (PreparedStatement updateStmt = c.prepareStatement(updateLagerSQL)) {
                    updateStmt.setInt(1, anzahl);
                    updateStmt.setInt(2, artikelId);
                    updateStmt.executeUpdate();
                }
            } else {
                try (PreparedStatement insertStmt = c.prepareStatement(insertLagerSQL)) {
                    insertStmt.setInt(1, artikelId);
                    insertStmt.setInt(2, anzahl);
                    insertStmt.executeUpdate();
                }
            }
        }
    }

}
