import javax.crypto.spec.PSource;
import java.sql.*;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class SQLiteJDBC {
    private static final String URL = "jdbc:sqlite:C:\\Users\\heste\\OneDrive\\Dokumente\\GitHub\\INFI_2023-2024\\sqlite-tools-win32-x86-3430100\\sqlite-tools-win32-x86-3430100\\steiner3.db";
    static Connection c = null;
    static Statement stmt = null;

    public static void main(String[] args)
    {
        try {
            c = erstelleDatenbank(c);
            stmt = erstelleTabelle(stmt);
            erstelleZufallszahlen();
            augabe_gerade_oder_ungerade(args);

            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(1);
        }
    }
    public static Connection erstelleDatenbank(Connection c) throws SQLException, ClassNotFoundException
    {
        Class.forName("org.sqlite.JDBC");
        c = DriverManager.getConnection(URL);
        System.out.println("Opened database successfully");
        return c;
    }

    public static Statement erstelleTabelle(Statement stmt) throws SQLException
    {
        stmt = c.createStatement();
        stmt.executeUpdate("drop table if exists zufallszahlen");
        String createTableSQL = "CREATE TABLE IF NOT EXISTS ZUFALLSZAHLEN " +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT ," +
                " value1 INT, " +
                " value2 INT)";
        stmt.executeUpdate(createTableSQL);
        System.out.println("Table created successfully");
        return stmt;
    }

    public static void erstelleZufallszahlen() throws SQLException {
        Random random = new Random();

        for (int i = 1; i < 21; i++) {
            int value1_rdm = random.nextInt(1, 11);
            int value2_mod = value1_rdm % 2;
            String tabelleBefuellen = ("INSERT INTO ZUFALLSZAHLEN (value1, value2) VALUES (" + value1_rdm + ", " + value2_mod + ")");
            stmt.executeUpdate(tabelleBefuellen);
            System.out.println("ID = " + i);
            System.out.println("Value1 = " + value1_rdm);
            System.out.println("Value2 = " + value2_mod);
            System.out.println("---------------------------");
        }
    }

    public static void augabe_gerade_oder_ungerade(String[] args)
    {
        try {
            if(args[0].equals("even"))
            {
                ResultSet rseven = stmt.executeQuery("SELECT count(*) AS even FROM ZUFALLSZAHLEN WHERE value1 % 2 = 0");
                int gerade = rseven.getInt("even");
                System.out.println("Anzahl gerader Zufallszahlen: " + gerade);
            }
            else
            {
                ResultSet rsodd = stmt.executeQuery("SELECT count(*) AS even FROM ZUFALLSZAHLEN WHERE value1 % 2 = 1");
                int ungerade = rsodd.getInt("odd");
                System.out.println("Anzahl ungerader Zufallszahlen: " + ungerade);
            }
        }
        catch (IndexOutOfBoundsException | SQLException e)
        {
            System.out.println("Falsches Argument!");

        }
    }
}
