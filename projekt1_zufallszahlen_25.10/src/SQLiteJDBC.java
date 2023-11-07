import java.sql.*;
import java.util.Random;

public class SQLiteJDBC {
    private static final String URL = "jdbc:sqlite:C:\\Users\\heste\\OneDrive\\Dokumente\\GitHub\\INFI_2023-2024\\sqlite-tools-win32-x86-3430100\\sqlite-tools-win32-x86-3430100\\steiner3.db";

    public static void main(String[] args) {
        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(URL);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            stmt.executeUpdate("drop table if exists zufallszahlen");
            String sql = "CREATE TABLE IF NOT EXISTS ZUFALLSZAHLEN " +
                    "(ID INTEGER PRIMARY KEY AUTOINCREMENT ," +
                    " value1 INT, " +
                    " value2 INT)";
            stmt.executeUpdate(sql);
            System.out.println("Table created successfully");

            Random random = new Random();
            int countGerade = 0;
            int countUngerade = 0;

            for (int i = 1; i < 21; i++) {
                int value1_rdm = random.nextInt(1, 11);
                int value2_mod = value1_rdm % 2;
                stmt.execute("INSERT INTO ZUFALLSZAHLEN (value1, value2) VALUES (" + value1_rdm + ", " + value2_mod + ")");
                System.out.println("ID = " + i);
                System.out.println("Value1 = " + value1_rdm);
                System.out.println("Value2 = " + value2_mod);
                System.out.println("---------------------------");
                if (value2_mod == 0) {
                    countGerade++;
                } else {
                    countUngerade++;
                }
            }

            System.out.println("Anzahl gerader Zahlen: " + countGerade);
            System.out.println("Anzahl ungerader Zahlen: " + countUngerade);

            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }
}
