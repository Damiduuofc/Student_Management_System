package db;

import java.sql.Connection;
import java.sql.DriverManager;

public class MyConnection {
    private static final String username = "root";
    private static final String password = "Damidu12";
    private static final String dataConn = "jdbc:mysql://localhost:3306/student_management";
    private static Connection con = null;

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Use username AND password correctly
            con = DriverManager.getConnection(dataConn, username, password);
        } catch (Exception ex) {
            System.out.println("Connection failed: " + ex.getMessage());
        }
        return con;
    }
}
