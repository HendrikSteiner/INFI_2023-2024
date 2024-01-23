import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Bestellen {
    public static void ausgabe(Connection c) {
        Scanner scanner = new Scanner(System.in);
        try {
            Statement stmt = c.createStatement();

            String a1 = "SELECT * FROM kunden";
            ResultSet resultSet = stmt.executeQuery(a1);
            System.out.println("Kunden-Tabelle:");
            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String kundenName = resultSet.getString("name");
                String kundenEmail = resultSet.getString("email");
                System.out.println("KundenID: " + id + ", Name: " + kundenName + ", Email: " + kundenEmail);
            }

            System.out.println("-----------------------------------------");
            System.out.println("Wer soll etwas bestellen?");
            System.out.print("Kunden ID: ");
            int kundenId = Integer.parseInt(scanner.nextLine());

            String a2 = "SELECT * FROM artikel";
            ResultSet resultSet2 = stmt.executeQuery(a2);
            System.out.println("Artikel-Tabelle:");
            while (resultSet2.next()) {
                int idartikel = resultSet2.getInt("ID");
                String artikelBezeichnung = resultSet2.getString("bezeichnung");
                double artikelpreis = resultSet2.getDouble("preis");
                System.out.println("ArtikelID: " + idartikel + ", Bezeichnung: " + artikelBezeichnung + ", preis: " + artikelpreis);
            }

            System.out.println("-----------------------------------------");
            System.out.println("Was soll der Kunde " + kundenId + " bestellen?");
            System.out.print("Artikel ID: ");
            int artikelId = Integer.parseInt(scanner.nextLine());

            System.out.print("Anzahl: ");
            int anzahl = Integer.parseInt(scanner.nextLine());

            if (istArtikelVerfuegbar(artikelId, anzahl, c))
            {
                String sql = "INSERT INTO bestellung (kundenId, artikelId, anzahl, bestellzeit) " +
                        "VALUES (" + kundenId + ", " + artikelId + ", " + anzahl + ", NOW())";
                stmt.executeUpdate(sql);

                stmt.executeUpdate(sql);

                Lager.erhoeheLagerbestand(artikelId, -anzahl, c);

                System.out.println("Bestellung erfolgreich eingetragen.");
            } else {
                System.out.println("Fehler: Artikel nicht auf Lager oder nicht ausreichende Menge verfügbar.");
            }

            resultSet.close();
        } catch (NumberFormatException | SQLException e) {
            System.out.println("Fehler beim Eintragen der Bestellung: " + e.getMessage());
        }
    }

    private static boolean istArtikelVerfuegbar(int artikelId, int anzahl, Connection c) throws SQLException {
        Statement stmt = c.createStatement();
        String sql = "SELECT l.menge FROM lager l WHERE l.artikel_id = " + artikelId;
        ResultSet resultSet = stmt.executeQuery(sql);

        if (resultSet.next()) {
            int lagerMenge = resultSet.getInt("menge");
            return lagerMenge >= anzahl;
        } else {
            return false;
        }
    }
}
