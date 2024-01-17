
import java.sql.*;
import java.util.Scanner;

public class Kunde
{

    public static void erstelleTabelleKunde(Connection c) throws SQLException
    {
       Statement stmt = c.createStatement();
        String createTableSQL = "CREATE TABLE IF NOT EXISTS kunden " +
                "(ID INTEGER PRIMARY KEY AUTO_INCREMENT ," +
                " name char(30), " +
                " email char(50))";
        stmt.executeUpdate(createTableSQL);
        System.out.println("Kunde table created successfully");
    }

    public static void werteEintragen(String name, String email, Connection c) throws SQLException
    {
        Statement stmt = c.createStatement();
        stmt.executeUpdate("INSERT INTO kunden (name, email) VALUES ('" + name + "', '" + email + "')");
        System.out.println("Werte erfolgreich eingetragen!");
    }

    public static void ausgabe(Connection c) throws SQLException
    {
        Scanner scanner = new Scanner(System.in);
        Statement stmt = c.createStatement();
        String a1 = "SELECT * FROM kunden";
        ResultSet resultSet = stmt.executeQuery(a1);
        System.out.println("Kunden-Tabelle:");
        while (resultSet.next())
        {
            int id = resultSet.getInt("ID");
            String kundenName = resultSet.getString("name");
            String kundenEmail = resultSet.getString("email");
            System.out.println("KundenID: " + id + ", Name: " + kundenName + ", Email: "+ kundenEmail);
        }
        System.out.println("-----------------------------------------");
        System.out.println("Wer soll etwas bestellen?");
        System.out.print("Kunden ID: ");
        int kundenId = Integer.parseInt(scanner.nextLine());
    }
}
