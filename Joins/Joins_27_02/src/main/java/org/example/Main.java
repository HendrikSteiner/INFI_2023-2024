package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Connection c = Verbindung.erstelleDatenbank();
        LeftJoin(c);
        RightJoin(c);
        InnerJoin(c);
        FullJoin(c);
        c.close();
    }

    public static void LeftJoin(Connection c) throws SQLException
    {
        String sql = "SELECT * FROM Kunde LEFT JOIN Bestellung ON Kunde.kunden_id = Bestellung.kunden_id";

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
        String sql = "SELECT * FROM Kunde RIGHT JOIN Bestellung ON Kunde.kunden_id = Bestellung.kunden_id";

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
        String sql = "SELECT * FROM Kunde INNER JOIN Bestellung ON Kunde.kunden_id = Bestellung.kunden_id";

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

    public static void FullJoin(Connection c) throws SQLException {
        String sql = "SELECT * FROM Kunde FULL JOIN Bestellung ON Kunde.kunden_id = Bestellung.kunden_id";

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

}
