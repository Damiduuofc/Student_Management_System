package student;

import db.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author damiduuofc
 */
public class MarksSheet {

    Connection con = MyConnection.getConnection();
    PreparedStatement ps;

    public boolean isIdExist(int sid) {
        try {
            ps = con.prepareStatement("SELECT * FROM score WHERE student_id = ?");
            ps.setInt(1, sid);
            ResultSet rs = ps.executeQuery();
            return rs.next(); // true if at least one record exists
        } catch (SQLException ex) {
            Logger.getLogger(MarksSheet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
// Overloaded method: fetch all semesters for a student
public void getScoreValue(JTable table, int studentId) {
    DefaultTableModel model = (DefaultTableModel) table.getModel();
    model.setRowCount(0); // clear table

    String query = "SELECT * FROM score WHERE student_id = ?";

    try (PreparedStatement ps = con.prepareStatement(query)) {
        ps.setInt(1, studentId); // only fetch this student's data
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getInt("id"),
                rs.getInt("student_id"),
                rs.getInt("semester"),
                rs.getString("course1"),
                rs.getDouble("score1"),
                rs.getString("course2"),
                rs.getDouble("score2"),
                rs.getString("course3"),
                rs.getDouble("score3"),
                rs.getString("course4"),
                rs.getDouble("score4"),
                rs.getString("course5"),
                rs.getDouble("score5"),
                rs.getDouble("average")
            });
        }

    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error fetching student marksheet: " + ex.getMessage());
    }
}



}
