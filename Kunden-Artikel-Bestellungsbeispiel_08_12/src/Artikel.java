import java.sql.*;
import java.util.Date;

public class Artikel {

    public static void erstelleTabelleArtikel(Connection c) throws SQLException {
        Statement stmt = c.createStatement();
        String createTableSQL = "CREATE TABLE IF NOT EXISTS artikel " +
                "(ID INTEGER PRIMARY KEY AUTO_INCREMENT, " +
                " bezeichnung CHAR(30), " +
                " preis DOUBLE, " +
                " time TIME, " +
                " menge INTEGER)";
        stmt.executeUpdate(createTableSQL);
        System.out.println("Artikel table created successfully");
    }


    public static void erstelleTabelleLager(Connection c) throws SQLException {
        Statement stmt = c.createStatement();
        String createTableSQL = "CREATE TABLE IF NOT EXISTS lager " +
                "(artikel_id INTEGER PRIMARY KEY, " +
                " menge INT)";
        stmt.executeUpdate(createTableSQL);
        System.out.println("Lager table created successfully");
    }

    public static void werteEintragen(String bezeichnung, double preis, int menge, Connection c) throws SQLException {
        Statement stmt = c.createStatement();

        java.sql.Time sqlTime = new java.sql.Time(new Date().getTime());
        String sql = "INSERT INTO artikel (bezeichnung, preis, time, menge) VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = c.prepareStatement(sql)) {
            preparedStatement.setString(1, bezeichnung);
            preparedStatement.setDouble(2, preis);
            preparedStatement.setTime(3, sqlTime);
            preparedStatement.setInt(4, menge);

            preparedStatement.executeUpdate();
        }
        ResultSet rs = stmt.executeQuery("SELECT MAX(ID) AS last_id FROM artikel");
        if (rs.next())
        {
            int lastId = rs.getInt("last_id");

            stmt.executeUpdate("UPDATE artikel SET time = '" + sqlTime + "' WHERE ID = " + lastId);

            stmt.executeUpdate("INSERT INTO lager (artikel_id, menge) VALUES (" + lastId + ", "+ menge +")");
        }

        System.out.println("Werte erfolgreich eingetragen!");
    }




    public static void artikelBestellen(int artikelId, int menge, Connection c) throws SQLException {
        if (!istArtikelImLager(artikelId, c)) {
            System.out.println("Fehler: Artikel nicht im Lager vorhanden!");
            return;
        }

        String updateSQL = "UPDATE lager SET menge = menge - ? WHERE artikel_id = ? ";
        try (PreparedStatement pstmt = c.prepareStatement(updateSQL)) {
            pstmt.setInt(1, menge);
            pstmt.setInt(2, artikelId);
            pstmt.executeUpdate();
        }
        System.out.println("Bestellung erfolgreich durchgeführt!");
    }

    private static boolean istArtikelImLager(int artikelId, Connection c) throws SQLException {
        Statement stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM lager WHERE artikel_id = " + artikelId);
        return rs.next();
    }
}
