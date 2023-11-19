import java.sql.*;
import java.util.Random;

public class ForeignKey
{
    private static final String URL = "jdbc:sqlite:C:\\Users\\heste\\OneDrive\\Dokumente\\GitHub\\INFI_2023-2024\\sqlite-tools-win32-x86-3430100\\sqlite-tools-win32-x86-3430100\\steiner3.db";
    private static Connection connection = null;
    private static Statement statement = null;

    public static void main(String[] args) {
        try {
            connection = createDatabaseConnection();
            enableForeignKeys();
            //einfaches Beispiel mit ForeignKeys:
            //createTables();
           // insertIntoTables();
            //Anwendung von Set NULL:
            createTables2();
            insertIntoTables2();
            deleteFromSupplier2();
            selectFromTables2();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
    }




    private static Connection createDatabaseConnection() throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection(URL);
        System.out.println("Opened database successfully");
        return conn;
    }

    private static void enableForeignKeys() throws SQLException {
        statement = connection.createStatement();
        statement.executeUpdate("PRAGMA foreign_keys = ON");
    }

    private static void createTables() throws SQLException {
        statement = connection.createStatement();
        statement.executeUpdate("DROP TABLE IF EXISTS suppliers");
        statement.executeUpdate("CREATE TABLE suppliers (" +
                "supplier_id INTEGER PRIMARY KEY, " +
                "supplier_name TEXT NOT NULL, " +
                "group_id INTEGER NOT NULL, " +
                "FOREIGN KEY (group_id) REFERENCES supplier_groups (group_id))");

        statement.executeUpdate("DROP TABLE IF EXISTS supplier_groups");
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS supplier_groups (" +
                "group_id INTEGER PRIMARY KEY, " +
                "group_name TEXT NOT NULL)");

        System.out.println("Tables created successfully");
    }
    private static void createTables2() throws SQLException {
        statement = connection.createStatement();
        statement.executeUpdate("DROP TABLE IF EXISTS suppliers");
        statement.executeUpdate("CREATE TABLE suppliers (" +
                "supplier_id INTEGER PRIMARY KEY, " +
                "supplier_name TEXT NOT NULL, " +
                "group_id INTEGER, " +
                "FOREIGN KEY (group_id) REFERENCES supplier_groups(group_id)" +
                " ON UPDATE SET NULL" +
                " ON DELETE SET NULL)");

        statement.executeUpdate("DROP TABLE IF EXISTS supplier_groups");
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS supplier_groups (" +
                "group_id INTEGER PRIMARY KEY, " +
                "group_name TEXT NOT NULL)");

        System.out.println("Tables (with Set NULL) created successfully");

    }
    private static void insertIntoTables() throws SQLException {
        String supplierGroupsData = "INSERT INTO supplier_groups (group_name) VALUES ('Domestic'), ('Global'), ('One-Time')";
        statement.executeUpdate(supplierGroupsData);

        String supplierData = "INSERT INTO suppliers (supplier_name, group_id) VALUES ('HP', 2)";
        statement.executeUpdate(supplierData);

         //String supplier_befuellen2 = "INSERT INTO suppliers (supplier_name, group_id) VALUES('ABC Inc.', 4)";
         //statement.executeUpdate(supplier_befuellen2);

        System.out.println("Inserted into tables successfully");
    }

    private static void insertIntoTables2() throws SQLException
    {
        String supplierGroupsData = "INSERT INTO supplier_groups (group_name) VALUES ('Domestic'), ('Global'), ('One-Time')";
        statement.executeUpdate(supplierGroupsData);

        String supplierData1 = "INSERT INTO suppliers (supplier_name, group_id) VALUES('XYZ Corp', 3)";
        statement.executeUpdate(supplierData1);

        String supplierData2 = "INSERT INTO suppliers (supplier_name, group_id) VALUES('ABC Corp', 3)";
        statement.executeUpdate(supplierData2);

        System.out.println("Inserted into tables (with Set NULL successfully");
    }
    private static void deleteFromSupplier2() throws SQLException
    {
         statement.executeUpdate("DELETE FROM supplier_groups WHERE group_id = 3");
         System.out.println("Datensatz wurde aus Tabelle Supplier gelöscht");
    }
    private static void selectFromTables2() throws SQLException
    {
       statement.executeUpdate( "SELECT * FROM suppliers");
    }

    private static void closeResources()
    {
        try {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
