
import java.sql.*;
import java.util.Scanner;

public class Bestellung
{

    public static void erstelleBestellungstabelle(Connection c) {
        try {
            Statement stmt = c.createStatement();
            String createBestellungTable = "CREATE TABLE IF NOT EXISTS bestellung (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "kundenId INT," +
                    "artikelId INT," +
                    "anzahl INT," +
                    "bestellzeit TIMESTAMP," +
                    "FOREIGN KEY (kundenId) REFERENCES kunden(id)," +
                    "FOREIGN KEY (artikelId) REFERENCES artikel(id)" +
                    ");";
            stmt.executeUpdate(createBestellungTable);
            System.out.println("Bestelltabelle erstellt");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void selectFromBestellungen(Connection c, String kundenId) {
        try {
            String sql = "SELECT * FROM bestellung WHERE kundenId = '" + kundenId + "'";
            Statement stmt = c.createStatement();

            ResultSet resultSet = stmt.executeQuery(sql);
            while (resultSet.next()) {
                int kundenIdResult = resultSet.getInt("kundenId");
                int artikelId = resultSet.getInt("artikelId");
                int anzahl = resultSet.getInt("anzahl");
                java.sql.Time bestellzeit = resultSet.getTime("bestellzeit");
                System.out.println("Der Kunden ID: " + kundenIdResult +
                        ", hat den Artikel mit der Artikel ID: " + artikelId +
                        ", so oft bestellt: " + anzahl +
                        ", Bestellzeit: " + bestellzeit);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void bestellungAnzeigen(Connection c) throws SQLException
    {
        Scanner scanner = new Scanner(System.in);
        try {
        Statement stmt = c.createStatement();
        String a3 = "SELECT * FROM kunden";
        ResultSet resultSet3 = stmt.executeQuery(a3);
        System.out.println("Kunden-Tabelle:");
        while (resultSet3.next())
        {
            int id = resultSet3.getInt("ID");
            String kundenName = resultSet3.getString("name");
            String kundenEmail = resultSet3.getString("email");
            System.out.println("KundenID: " + id + ", Name: " + kundenName + ", Email: "+ kundenEmail);
        }
        System.out.print("Kunden ID: ");
        String kundenId = scanner.nextLine();
        Bestellung.selectFromBestellungen(c, kundenId);
    } catch (NumberFormatException e)
        {
        System.out.println("Fehler beim Anzeigen der Bestellung: " + e.getMessage());
        }
    }

    public static void aktualisiereBestellung(Connection c) throws SQLException
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Von welchem Kunden wollen sie die Bestellung bearbeiten?");
        int gewaehlteNummer = Integer.parseInt(scanner.nextLine());
        System.out.println("Neue Anzahl an Artikel");
        int anzahlArtikel = Integer.parseInt(scanner.nextLine());
        Bestellung.update(gewaehlteNummer, anzahlArtikel, c);
    }
    public static void update(int gewaehlteNummer, int anzahlArtikel, Connection c) throws SQLException
    {
        String sql = "UPDATE bestellung SET anzahl = ? WHERE kundenId = ?";
        try (PreparedStatement pstmt = c.prepareStatement(sql)) {
            pstmt.setInt(1, anzahlArtikel);
            pstmt.setInt(2, gewaehlteNummer);
            pstmt.executeUpdate();
        }
    }

    public static void loescheBestellung(Connection c) throws SQLException
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Von welchem Kunden die Bestellung wollen sie löschen?");
        int gewaehlteNummer = Integer.parseInt(scanner.nextLine());
        delete(gewaehlteNummer, c);
    }

    public static void delete(int gewaehlteNummer, Connection c) throws SQLException
    {
        String sql = "DELETE from bestellung WHERE kundenId = ?";
        try (PreparedStatement pstmt = c.prepareStatement(sql)) {
            pstmt.setInt(1, gewaehlteNummer);
            pstmt.executeUpdate();
        }
    }


}
