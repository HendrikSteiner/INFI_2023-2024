
import javax.crypto.spec.PSource;
import java.sql.*;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class Main {
    private static final String URL = "jdbc:mysql://localhost";
    private static Connection c = null;
    private static Statement stmt = null;

    public static void main(String[] args) {
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
        } finally {
            closeResources();
        }
    }
    public static Connection erstelleDatenbank(Connection c) throws SQLException, ClassNotFoundException
    {
        Class.forName("com.mysql.cj.jdbc.Driver");
        c = DriverManager.getConnection(URL, "root", "root");
        stmt = c.createStatement();
        stmt.executeUpdate("create database if not exists 3BHWII_test1");
        stmt.executeUpdate("use 3BHWII_test1");
        System.out.println("Opened mysql successfully");
        return c;
    }

    public static Statement erstelleTabelle(Statement stmt) throws SQLException
    {
        stmt = c.createStatement();
        stmt.executeUpdate("drop table if exists zufallszahlen");
        String createTableSQL = "CREATE TABLE ZUFALLSZAHLEN " +
                "(ID INTEGER PRIMARY KEY AUTO_INCREMENT ," +
                " value1 INT, " +
                " value2 INT)";
        stmt.executeUpdate(createTableSQL);
        System.out.println("Table created successfully");
        return stmt;
    }

    public static void erstelleZufallszahlen() throws SQLException {
        Random random = new Random();

        for (int i = 1; i < 22; i++) {
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


    public static void augabe_gerade_oder_ungerade(String[] args) {
        try {
            if (args[0].equals("even")) {
                String even = "SELECT count(*) AS even FROM ZUFALLSZAHLEN WHERE value1 % 2 = 0";
                ResultSet rseven = stmt.executeQuery(even);
                if (rseven.next()) {
                    int gerade = rseven.getInt("even");
                    System.out.println("Anzahl gerader Zufallszahlen: " + gerade);
                }
            } else {
                String odd = "SELECT count(*) AS odd FROM ZUFALLSZAHLEN WHERE value1 % 2 = 1";
                ResultSet rsodd = stmt.executeQuery(odd);
                if (rsodd.next()) {
                    int ungerade = rsodd.getInt("odd");
                    System.out.println("Anzahl ungerader Zufallszahlen: " + ungerade);
                }
            }
        } catch (IndexOutOfBoundsException | SQLException e) {
            System.out.println("Falsches Argument!");
        }
    }


    public static void closeResources() {
        try {
            if (stmt != null) {
                stmt.close();
            }
            if (c != null) {
                c.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
