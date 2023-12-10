import javax.crypto.spec.PSource;
import java.sql.*;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

public class Main {
    private static final String URL = "jdbc:mysql://localhost/Kunde_Artikel_Bestellungsbeispiel";
    public static Connection c = null;
    private static Statement stmt = null;

    public static void main(String[] args) {
        try {
            c = erstelleDatenbank(c);
            Kunde k1 = new Kunde();
            k1.erstelleTabelleKunde(c);
            Artikel a1 = new Artikel();
            a1.erstelleTabelleArtikel(c);
            Bestellung b1 = new Bestellung();
            b1.erstelleBestellungstabelle(c);
            befuellen();

            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(1);
        } finally {
            closeResources();
        }
    }


    public static void befuellen()throws SQLException
    {
        Scanner scanner = new Scanner(System.in);

        System.out.println("#########################################################################################");
        System.out.println("| add Kunde [k] | add Artikel [a] | bestellen [b] | bestellung anzeigen [ba] | exit [e] |");
        System.out.println("#########################################################################################");

        System.out.print("Choose an Action: ");
        String input = scanner.nextLine();

            switch (input)
            {
                case "k": {
                    System.out.print("Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Email: ");
                    String email = scanner.nextLine();
                    stmt.executeUpdate("INSERT INTO kunden (name, email) VALUES ('" + name + "', '" + email + "')");
                    break;
                }
                case "a": {
                    try {
                    System.out.print("Bezeichnung: ");
                    String bezeichnung = scanner.nextLine();
                    System.out.print("Preis: ");
                    String preis = scanner.nextLine();
                    stmt.executeUpdate("INSERT INTO artikel (bezeichnung, preis) VALUES ('" + bezeichnung + "', '" + preis + "')");
                    } catch (NumberFormatException | SQLException e) {
                        System.out.println("Fehler beim Eintragen des Arikels: " + e.getMessage());
                    }
                    break;
                }
                case "b": {
                    try {
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

                        String a2 = "SELECT * FROM artikel";
                        ResultSet resultSet2 = stmt.executeQuery(a2);
                        System.out.println("Artikel-Tabelle:");
                        while (resultSet2.next())
                        {
                            int idartikel = resultSet2.getInt("ID");
                            String artikelBezeichnung = resultSet2.getString("bezeichnung");
                            double artikelpreis = resultSet2.getDouble("preis");
                            System.out.println("ArtikelID: " + idartikel+ ", Bezeichnung: " + artikelBezeichnung + ", preis: "+ artikelpreis);
                        }
                        System.out.println("-----------------------------------------");
                        System.out.println("Was soll der Kunde: "+kundenId+" bestellen?");
                        System.out.print("Artikel ID: ");
                        int artikelId = Integer.parseInt(scanner.nextLine());

                        System.out.print("Anzahl: ");
                        int anzahl = Integer.parseInt(scanner.nextLine());

                        String sql = "INSERT INTO bestellung (kundenId, artikelId, anzahl) VALUES (" + kundenId + ", " + artikelId + ", " + anzahl + ")";
                        stmt.executeUpdate(sql);

                        System.out.println("Bestellung erfolgreich eingetragen.");
                        resultSet.close();
                    } catch (NumberFormatException | SQLException e) {
                        System.out.println("Fehler beim Eintragen der Bestellung: " + e.getMessage());
                    }
                    break;
                }
                case "ba": {
                    try {
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
                } catch (NumberFormatException e) {
                    System.out.println("Fehler beim Eintragen der Bestellung: " + e.getMessage());
                }
                break;
                }
                case "e":
                    System.exit(0);
                    break;
                default:
                    System.out.println("Falsche Eingabe");
            }
    }
    public static Connection erstelleDatenbank(Connection c) throws SQLException, ClassNotFoundException
    {
        Class.forName("com.mysql.cj.jdbc.Driver");
        c = DriverManager.getConnection(URL, "root", "root");
        stmt = c.createStatement();
        stmt.executeUpdate("create database if not exists Kunde_Artikel_Bestellungsbeispiel");
        stmt.executeUpdate("use Kunde_Artikel_Bestellungsbeispiel");
        System.out.println("Opened mysql successfully");
        return c;
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
