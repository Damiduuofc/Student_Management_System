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
public class Score {
    Connection con = MyConnection.getConnection();
    PreparedStatement ps;

    private static final Logger LOGGER = Logger.getLogger(Score.class.getName());

    // ✅ Get next score ID
public int getMax() {
    int id = 0;
    String query = "SELECT MAX(id) AS max_id FROM score";

    try (Statement st = con.createStatement();
         ResultSet rs = st.executeQuery(query)) {

        if (rs.next()) {
            id = rs.getInt("max_id"); // 0 if no rows
        }

    } catch (SQLException ex) {
        LOGGER.log(Level.SEVERE, "Error getting max score ID", ex);
    }

    return id + 1; // always give next available ID
}

    
    // ✅ Check if a student exists by ID
public boolean getDetails(int sid, int semNO) {
    try {
        String query = "SELECT * FROM course WHERE student_id = ? AND semester = ?";
        ps = con.prepareStatement(query);
        ps.setInt(1, sid);
        ps.setInt(2, semNO);

        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            // Use column names instead of indexes if possible
            Home.jTextFieldStudentID.setText(String.valueOf(rs.getInt("id"))); 
            Home.jTextFieldSemesterNO.setText(String.valueOf(rs.getInt("student_id")));
            Home.jTextCourse1.setText(rs.getString("course1"));
            Home.jTextCourse2.setText(rs.getString("course2"));
            Home.jTextCourse3.setText(rs.getString("course3"));
            Home.jTextCourse4.setText(rs.getString("course4"));
            Home.jTextCourse5.setText(rs.getString("course5"));

            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Student ID or semester number doesn't exist");
        }
    } catch (SQLException ex) {
        Logger.getLogger(Score.class.getName()).log(Level.SEVERE, null, ex);
    }
    return false;
}

public boolean isIdExist(int id) {
    try {
        ps = con.prepareStatement("SELECT * FROM score WHERE id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        return rs.next(); // true if at least one record exists
    } catch (SQLException ex) {
        Logger.getLogger(Score.class.getName()).log(Level.SEVERE, null, ex);
    }
    return false;
}

public boolean isSidOrSemesterNoExist(int sid, int SemNO) {
    try {
        ps = con.prepareStatement("SELECT * FROM score WHERE student_id = ? and semester = ?");
        ps.setInt(1, sid);
        ps.setInt(2, SemNO);

        ResultSet rs = ps.executeQuery();
        return rs.next(); // true if at least one record exists
    } catch (SQLException ex) {
        Logger.getLogger(Score.class.getName()).log(Level.SEVERE, null, ex);
    }
    return false;
}




public void insert(int id, int sid, int semester, 
                   String course1, double score1, 
                   String course2, double score2,
                   String course3, double score3, 
                   String course4, double score4, 
                   String course5, double score5, 
                   double average) {

    String sql = "INSERT INTO score (id, student_id, semester, course1, score1, course2, score2, " +
                 "course3, score3, course4, score4, course5, score5, average) " +
                 "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, id);
        ps.setInt(2, sid);
        ps.setInt(3, semester);

        ps.setString(4, course1);
        ps.setDouble(5, score1);

        ps.setString(6, course2);
        ps.setDouble(7, score2);

        ps.setString(8, course3);
        ps.setDouble(9, score3);

        ps.setString(10, course4);
        ps.setDouble(11, score4);

        ps.setString(12, course5);
        ps.setDouble(13, score5);

        ps.setDouble(14, average);

        int rows = ps.executeUpdate();

        if (rows > 0) {
            JOptionPane.showMessageDialog(null, "✅ Scores added successfully");
        } else {
            JOptionPane.showMessageDialog(null, "⚠ No data inserted!");
        }

    } catch (SQLException ex) {
        LOGGER.log(Level.SEVERE, "SQL Error while inserting scores for the courses", ex);
        JOptionPane.showMessageDialog(null, "SQL Error: " + ex.getMessage());
    }
}

public void getScoreValue(JTable table, String search) {
    DefaultTableModel model = (DefaultTableModel) table.getModel();
    model.setRowCount(0);

    String query = "SELECT * FROM score";
    if (!search.isEmpty()) {
        query += " WHERE student_id LIKE ?";
    }

    try (PreparedStatement ps = con.prepareStatement(query)) {
        if (!search.isEmpty()) {
            ps.setString(1, "%" + search + "%");
        }

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
    }
}

public boolean update(int studentId, int semesterNo, 
                      String course1, double score1,
                      String course2, double score2,
                      String course3, double score3,
                      String course4, double score4,
                      String course5, double score5,
                      double average) {

    String query = "UPDATE score SET course1=?, score1=?, course2=?, score2=?, course3=?, score3=?, "
            + "course4=?, score4=?, course5=?, score5=?, average=? "
            + "WHERE student_id=? AND semester=?";

    try (PreparedStatement ps = con.prepareStatement(query)) {
        ps.setString(1, course1);
        ps.setDouble(2, score1);
        ps.setString(3, course2);
        ps.setDouble(4, score2);
        ps.setString(5, course3);
        ps.setDouble(6, score3);
        ps.setString(7, course4);
        ps.setDouble(8, score4);
        ps.setString(9, course5);
        ps.setDouble(10, score5);
        ps.setDouble(11, average);
        ps.setInt(12, studentId);
        ps.setInt(13, semesterNo);

        int updated = ps.executeUpdate();
        return updated > 0; // returns true if update succeeded

    } catch (SQLException ex) {
        ex.printStackTrace();
        return false;
    }
}






    
}
