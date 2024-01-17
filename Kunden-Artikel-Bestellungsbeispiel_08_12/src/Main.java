import javax.crypto.spec.PSource;
import java.sql.*;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

public class Main
{
    public static Connection c = null;

    public static void main(String[] args)
    {
        try {
            c = Verbindung.erstelleDatenbank();
            Kunde.erstelleTabelleKunde(c);
            Artikel.erstelleTabelleArtikel(c);
            Bestellung.erstelleBestellungstabelle(c);
            Lager.erstelleTabelleLager(c);
            befuellen();

            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(1);
        } finally {
            closeResources();
        }
    }


    public static void befuellen() throws SQLException, ClassNotFoundException {
        while(true)
        {
            Scanner scanner = new Scanner(System.in);

            System.out.println("#####################################################################################################################################################################");
            System.out.println("| add Kunde [k] | add Artikel [a] | bestellen [b] | bestellung anzeigen [ba] | bestellung bearbeiten [br] | bestellung loeschen [bl] | Lagerbestand [lb] | exit [e] |");
            System.out.println("#####################################################################################################################################################################");

            System.out.print("Choose an Action: ");
            String input = scanner.nextLine();

            switch (input)
            {
                case "k":
                {
                    System.out.print("Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Email: ");
                    String email = scanner.nextLine();
                    Kunde.werteEintragen(name, email, c);
                    break;
                }
                case "a":
                {
                    try {
                        System.out.print("Bezeichnung: ");
                        String bezeichnung = scanner.nextLine();
                        System.out.print("Preis: ");
                        double preis = Double.parseDouble(scanner.nextLine());
                        Artikel.werteEintragen(bezeichnung, preis, c);
                    } catch (NumberFormatException e)
                    {
                        System.out.println("Fehler beim Eintragen des Arikels: " + e.getMessage());
                    }
                    break;
                }
                case "b":
                {
                    Bestellen.ausgabe(c);
                    break;
                }
                case "ba":
                {
                    Bestellung.bestellungAnzeigen(c);
                    break;
                }
                case "e":
                {
                    System.exit(0);
                    break;
                }
                case "br":
                {
                    Bestellung.bestellungAnzeigen(c);
                    Bestellung.aktualisiereBestellung(c);
                    break;
                }
                case "bl":
                {
                    Bestellung.bestellungAnzeigen(c);
                    Bestellung.loescheBestellung(c);
                    break;
                }
                case "lb":
                {
                    try {
                        Lager.lagerbestandanzeigen(c);
                        System.out.print("Artikel ID: ");
                        int artikelId = Integer.parseInt(scanner.nextLine());
                        System.out.print("Menge: ");
                        int menge = Integer.parseInt(scanner.nextLine());
                        Lager.artikelBestellen(artikelId, menge, c);
                    } catch (NumberFormatException e) {
                        System.out.println("Fehler beim Bestellen des Artikels: " + e.getMessage());
                    }
                    break;
                }
                default:
                    System.out.println("Falsche Eingabe");
            }
        }
    }

    public static void closeResources()
    {
        try {
            if (c != null) {
                c.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
