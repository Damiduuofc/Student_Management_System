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


}
