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
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

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

public boolean isEmailExist(String email){
        try {
            ps = con.prepareStatement("select * from student where email = ?");
            ps.setString(1,email);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return true;
            }
        } catch (SQLException ex) {
            System.getLogger(Student.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return false;
}
public boolean isPhoneExist(String phone) {
    try {
        ps = con.prepareStatement("SELECT * FROM student WHERE phone = ?");
        ps.setString(1, phone);
        ResultSet rs = ps.executeQuery();
        return rs.next(); // true if at least one record exists
    } catch (SQLException ex) {
        Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
    }
    return false;
}

public boolean isIdExist(int id) {
    try {
        ps = con.prepareStatement("SELECT * FROM student WHERE id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        return rs.next(); // true if at least one record exists
    } catch (SQLException ex) {
        Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
    }
    return false;
}


public void getStudentValue(JTable table, String searchValue) {
    String sql = "SELECT * FROM student WHERE CONCAT(id,name,email,phone) LIKE ? ORDER BY id DESC";
    try {
        ps = con.prepareStatement(sql);
        ps.setString(1, "%" + searchValue + "%");
        ResultSet rs = ps.executeQuery();

        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // clear table

        while (rs.next()) {
            Object[] row = new Object[11];
            row[0] = rs.getInt("id");
            row[1] = rs.getString("name");
            row[2] = rs.getString("date_of_birth");
            row[3] = rs.getString("gender");
            row[4] = rs.getString("email");
            row[5] = rs.getString("phone");
            row[6] = rs.getString("father_name");
            row[7] = rs.getString("mother_name");
            row[8] = rs.getString("address1");
            row[9] = rs.getString("address2");
            row[10] = rs.getString("image_path");
            model.addRow(row);
        }

        // 🔑 Force table UI refresh
        table.setModel(model);
        model.fireTableDataChanged();

    } catch (SQLException ex) {
        Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
    }
}

public void update(int id, String sname, String date, String gender, String email,
                   String phone, String father, String mother,
                   String address1, String address2, String imagePath) {
    
    String sql = "UPDATE student SET name=?, date_of_birth=?, gender=?, email=?, phone=?, father_name=?, mother_name=?, address1=?, address2=?, image_path=? WHERE id=?";
    
    try {
        ps = con.prepareStatement(sql);
        ps.setString(1, sname);
        ps.setString(2, date);
        ps.setString(3, gender);
        ps.setString(4, email);
        ps.setString(5, phone);
        ps.setString(6, father);
        ps.setString(7, mother);
        ps.setString(8, address1);
        ps.setString(9, address2);
        ps.setString(10, imagePath);
        ps.setInt(11, id);
        
        if (ps.executeUpdate() > 0) {
            JOptionPane.showMessageDialog(null, "Student data updated successfully");
        }
        
    } catch (SQLException ex) {
        ex.printStackTrace(); // or use a proper logger
    }
}
public void delete(int id) {
    int yesOrNo = JOptionPane.showConfirmDialog(null, 
        "Courses and scores record will also be deleted",
        "Student Delete", JOptionPane.OK_CANCEL_OPTION, 0);

    if (yesOrNo == JOptionPane.OK_OPTION) {
        try {
            ps = con.prepareStatement("DELETE FROM student WHERE id = ?");
            ps.setInt(1, id);

            if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Student deleted successfully");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}


    


}
