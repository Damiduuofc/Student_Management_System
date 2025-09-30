package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnection {
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Damidu12";
    private static final String DATA_CONN =
            "jdbc:mysql://localhost:3306/student_management?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC";

    private static Connection con = null;

    public static Connection getConnection() {
        try {
            if (con == null || con.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection(DATA_CONN, USERNAME, PASSWORD);
                con.setAutoCommit(true);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Connection failed: " + ex.getMessage());
        }
        return con;
    }
}
