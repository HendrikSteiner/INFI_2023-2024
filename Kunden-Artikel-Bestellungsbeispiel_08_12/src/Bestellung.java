
import java.sql.*;
public class Bestellung
{
    public  void erstelleBestellungstabelle(Connection c) {
        try {
            Statement stmt = c.createStatement();
            String createBestellungTable = "CREATE TABLE IF NOT EXISTS bestellung (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "kundenId INT," +
                    "artikelId INT," +
                    "anzahl INT," +
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
            while (resultSet.next())
            {
                int kundenIdResult = resultSet.getInt("kundenId");
                int artikelId = resultSet.getInt("artikelId");
                int anzahl = resultSet.getInt("anzahl");
                System.out.println("Der Kunden ID: " + kundenIdResult + ", hat den Artikel mit der Artikel ID: " + artikelId + ", so oft bestellt: " + anzahl);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
