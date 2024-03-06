
//TODOLIST:
//nächster Schritt Lager richitg aktualisieren bei bestellung loeschen oder aktualisieren (menge updaten)
//Bestellzeit nicht mehr doppelt ausgeben
//  DATE Formater einbauen in amerikansiches ZB
// bei Update und Delete Lagerbestand anpassen
//überlgen wie import schlauer wäre


import javax.crypto.spec.PSource;
import java.io.IOException;
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
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(1);
        } finally {
            closeResources();
        }
    }




    public static void befuellen() throws SQLException, ClassNotFoundException, IOException {
        while(true)
        {
            Scanner scanner = new Scanner(System.in);

            System.out.println("###################################################################################################################################################################################################################################");
            System.out.println("| add Kunde [k] | add kunden csv [ak] | add Artikel [a] | bestellen [b] | bestellung anzeigen [ba] | bestellung bearbeiten [br] | bestellung loeschen [bl] | Lagerbestand [lb] | import from csv [i] |  procedure [p] || exit [e] |");
            System.out.println("###################################################################################################################################################################################################################################");
            System.out.print("Choose an Action: ");
            String input = scanner.nextLine();

            switch (input) {
                case "k": {
                    try {
                        System.out.print("Name: ");
                        String name = scanner.nextLine();
                        System.out.print("Email: ");
                        String email = scanner.nextLine();
                        Kunde.werteEintragen(name, email, c);
                    } catch (NumberFormatException e) {
                        System.out.println("Fehler beim Eintragen des Kundens: " + e.getMessage());
                    }
                    break;
                }
                case "a": {
                    try {
                        System.out.print("Bezeichnung: ");
                        String bezeichnung = scanner.nextLine();
                        System.out.print("Preis: ");
                        double preis = Double.parseDouble(scanner.nextLine());
                        System.out.println("Menge: ");
                        int menge = Integer.parseInt(scanner.nextLine());
                        Artikel.werteEintragen(bezeichnung, preis, menge, c);
                    } catch (NumberFormatException e) {
                        System.out.println("Fehler beim Eintragen des Arikels: " + e.getMessage());
                    }
                    break;
                }
                case "ak": {
                    ExportData.kundenInCSV(c);
                    break;
                }
                case "i": {
                    ImportData.importiereKundenCSV(c);
                    break;
                }
                case "b": {
                    Bestellen.ausgabe(c);
                    break;
                }
                case "ba": {
                    Bestellung.bestellungAnzeigen(c);
                    break;
                }
                case "e": {
                    System.exit(0);
                    break;
                }
                case "br": {
                    Bestellung.bestellungAnzeigen(c);
                    Bestellung.aktualisiereBestellung(c);
                    break;
                }
                case "bl": {
                    Bestellung.bestellungAnzeigen(c);
                    Bestellung.loescheBestellung(c);
                    break;
                }
                case "lb": {
                    Lager.lagerbestandanzeigen(c);
                    break;
                }
                case "p": {

                    Artikel.proceduren(c);
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
