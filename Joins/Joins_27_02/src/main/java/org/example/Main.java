package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException
    {
        Connection c = Verbindung.erstelleDatenbank();
        soutTableKunde(c);
        soutTableBestellung(c);
        //LeftJoin(c);
        //RightJoin(c);
         // InnerJoin(c);
        FullJoin(c);
        UnionAll(c);



    }

    public static void LeftJoin(Connection c) throws SQLException
    {
        String sql = "SELECT * FROM Kunde LEFT JOIN Bestellung ON Kunde.kunden_id =" +
                " Bestellung.kunden_id";

        try (PreparedStatement statement = c.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int kundenId = resultSet.getInt("kunden_id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                System.out.println("Kunden ID: " + kundenId + ", Name: " + name + ", Email: " + email);
            }
        }
    }
    public static void RightJoin(Connection c) throws SQLException {
        String sql = "SELECT * FROM Kunde RIGHT JOIN Bestellung ON Kunde.kunden_id = " +
                "Bestellung.kunden_id";

        try (PreparedStatement statement = c.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int kundenId = resultSet.getInt("kunden_id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                System.out.println("Kunden ID: " + kundenId + ", Name: " + name + ", Email: " + email);
            }
        }
    }

    public static void InnerJoin(Connection c) throws SQLException {
        String sql = "SELECT * FROM Kunde INNER JOIN Bestellung ON Kunde.kunden_id = " +
                "Bestellung.kunden_id";

        try (PreparedStatement statement = c.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery())
        {
            while (resultSet.next()) {
                int kundenId = resultSet.getInt("kunden_id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                System.out.println("Kunden ID: " + kundenId + ", Name: " + name + ", Email: " + email);
            }
        }
    }
    public static void FullJoin(Connection c) throws SQLException
    {
        System.out.println(" FULL-JOIN:");
        String sql = "SELECT *"+
                "FROM Kunde " +
                "LEFT JOIN Bestellung ON Kunde.kunden_id = Bestellung.kunden_id " +
                "UNION " +
                "SELECT * " +
                "FROM Bestellung " +
                "RIGHT JOIN Kunde ON Kunde.kunden_id = Bestellung.kunden_id";
        
        try (PreparedStatement statement = c.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int kundenId = resultSet.getInt("kunden_id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String telefonnummer = resultSet.getString("telefonnummer");
                String adresse = resultSet.getString("adresse");
                System.out.println("Kunden ID: " + kundenId + ", Name: " + name + ", Email: "+email + ", Telefonnummer: "+telefonnummer+", Adresse: "+adresse);

            }
        }
        System.out.println("----------------------------------------------------------------------------");
    }

    public static void UnionAll(Connection c) throws SQLException
    {
        System.out.println("UNION ALL:");
        String sql = "(SELECT * FROM Kunde) " +
                "UNION ALL " +
                "(SELECT kunden_id, null AS name, null AS email, null as telefonnumer, null as adresse FROM Bestellung)";

        try (PreparedStatement statement = c.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int kundenId = resultSet.getInt("kunden_id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String telefonnummer = resultSet.getString("telefonnummer");
                String adresse = resultSet.getString("adresse");
                System.out.println("Kunden ID: " + kundenId + ", Name: " + name + ", Email: "+email + ", Telefonnummer: "+telefonnummer+", Adresse: "+adresse);
            }
        }
        System.out.println("--------------------------------------------------------------------------------");
    }

    public static void soutTableKunde(Connection c) throws SQLException {
        System.out.println("Inhalt der Tabelle Kunde:");
        String sql = "SELECT * FROM Kunde";

        try (PreparedStatement statement = c.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int kundenId = resultSet.getInt("kunden_id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String telefonnummer = resultSet.getString("telefonnummer");
                String adresse = resultSet.getString("adresse");

                System.out.println("Kunden ID: " + kundenId + ", Name: " + name +
                        ", Email: " + email + ", Telefonnummer: " + telefonnummer +
                        ", Adresse: " + adresse);
            }
        }
        System.out.println("----------------------------------------------------------------------------");
    }

    public static void soutTableBestellung(Connection c) throws SQLException {
        System.out.println("Inhalt der Tabelle Bestellung:");
        String sql = "SELECT * FROM Bestellung";

        try (PreparedStatement statement = c.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int bestellungsId = resultSet.getInt("bestellungs_id");
                int kundenId = resultSet.getInt("kunden_id");
                double gesamtbetrag = resultSet.getDouble("gesamtbetrag");

                System.out.println("Bestellungs ID: " + bestellungsId + ", Kunden ID: " + kundenId +
                        ", Gesamtbetrag: " + gesamtbetrag);
            }
        }
        System.out.println("----------------------------------------------------------------------------");
    }
}

