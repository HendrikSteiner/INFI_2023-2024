import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ExportData {

    public static void kundenInCSV(Connection c) throws SQLException, IOException
    {
        Statement stmt = c.createStatement();
        String sql = "SELECT * FROM kunden";
        ResultSet rs = stmt.executeQuery(sql);

        String csvFilePath = "C:\\Users\\heste\\OneDrive\\Dokumente\\GitHub\\INFI_2023-2024\\Kunden-Artikel-Bestellungsbeispiel_08_12\\kunden_exp.csv";

        try (FileWriter writer = new FileWriter(csvFilePath)) {
            for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                writer.append(rs.getMetaData().getColumnName(i));
                if (i < rs.getMetaData().getColumnCount()) {
                    writer.append(',');
                } else {
                    writer.append('\n');
                }
            }

            while (rs.next()) {
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    writer.append(rs.getString(i));
                    if (i < rs.getMetaData().getColumnCount()) {
                        writer.append(',');
                    } else {
                        writer.append('\n');
                    }
                }
            }
        }catch(Exception e) {
            e.fillInStackTrace();
        }

        System.out.println("Daten erfolgreich in die CSV-Datei exportiert.");
    }
}
