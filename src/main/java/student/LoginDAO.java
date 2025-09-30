package student;

import db.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginDAO {

    public static boolean authenticate(String username, String password) {
        String query = "SELECT * FROM admin WHERE username=? AND password=?";
        try (Connection con = MyConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, username);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // returns true if username/password exists
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
