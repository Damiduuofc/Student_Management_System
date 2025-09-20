/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package student;

import db.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author damiduuofc
 */
public class Student {

    Connection con = MyConnection.getConnection();
    PreparedStatement ps;

public int getMax() {
    int id = 0;
    String query = "SELECT MAX(id) FROM student";
    
    try (Statement st = con.createStatement();
         ResultSet rs = st.executeQuery(query)) {
         
        if (rs.next()) {
            id = rs.getInt(1);
        }
    } catch (SQLException ex) {
        Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return id + 1;
}
public void insert(int id, String sname, String date, String gender, String email,
                   String phone, String father, String mother,
                   String address1, String address2, String imagePath) {
    
    String sql = "INSERT INTO student (id, name, date_of_birth, gender, email, phone, father_name, mother_name, address1, address2, image_path) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
    
    try (PreparedStatement ps = con.prepareStatement(sql)) {
        
        ps.setInt(1, id);
        ps.setString(2, sname);

        // since date_of_birth is DATE in MySQL
        ps.setDate(3, java.sql.Date.valueOf(date)); // date must be in "yyyy-MM-dd" format

        ps.setString(4, gender);
        ps.setString(5, email);
        ps.setString(6, phone);
        ps.setString(7, father);
        ps.setString(8, mother);
        ps.setString(9, address1);
        ps.setString(10, address2);
        ps.setString(11, imagePath);

        int rows = ps.executeUpdate();
        System.out.println("Rows inserted: " + rows);

        if (rows > 0) {
            JOptionPane.showMessageDialog(null, "New Student added successfully");
        } else {
            JOptionPane.showMessageDialog(null, "⚠ No data inserted!");
        }
        
    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(null, "SQL Error: " + ex.getMessage());
    }
}




}
