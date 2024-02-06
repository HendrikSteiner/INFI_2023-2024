import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ImportData
{
    public static void importiereKundenCSV(Connection c) throws SQLException, IOException
    {
        PreparedStatement preparedStatement = null;
        String csvFile = "C:\\Users\\heste\\OneDrive\\Dokumente\\GitHub\\INFI_2023-2024\\Kunden-Artikel-Bestellungsbeispiel_08_12\\kunden_imp.csv";
        String insertQuery = "INSERT INTO kunden (name, email) VALUES (?, ?)";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            preparedStatement = c.prepareStatement(insertQuery);

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String name = data[0].trim();
                String email = data[1].trim();

                preparedStatement.setString(1, name);
                preparedStatement.setString(2, email);
                preparedStatement.executeUpdate();
            }
            System.out.println("Daten erfolgreich aus der CSV-Datei in die Tabelle 'kunden' eingefügt.");
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
    }
}
