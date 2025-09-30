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

public class Course {

    private static final Logger LOGGER = Logger.getLogger(Course.class.getName());
    private Connection con = MyConnection.getConnection();
    PreparedStatement ps;

    // ✅ Get next course ID
    public int getMax() {
        int id = 0;
        String query = "SELECT MAX(id) FROM course";

        try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(query)) {
            if (rs.next()) {
                id = rs.getInt(1); // Will be 0 if no data
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error getting max course ID", ex);
        }

        return id + 1;
    }

    // ✅ Check if a student exists by ID
    public boolean checkStudentExists(int studentId) {
        String sql = "SELECT 1 FROM student WHERE id = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // true if exists
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error checking if student exists", ex);
        }
        return false;
    }

    // ✅ Count semesters taken by a student
    public int countSemester(int studentId) {
        int total = 0;
        String sql = "SELECT COUNT(*) FROM course WHERE student_id = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    total = rs.getInt(1);
                }
            }
            if (total == 8) {
                return -1; // All courses completed
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error counting semesters", ex);
        }
        return total;
    }

    // ✅ Check if a specific semester exists for a student
    public boolean isSemesterExist(int studentId, int semesterNo) {
        String sql = "SELECT 1 FROM course WHERE student_id = ? AND semester = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            ps.setInt(2, semesterNo);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error checking semester existence", ex);
        }
        return false;
    }

    // ✅ Check if a specific course exists for a student
    public boolean isCourseExist(int id, String columnName, String courseValue) {
        // Whitelist allowed columns to prevent SQL injection
        if (!columnName.equals("course1") && !columnName.equals("course2") &&
            !columnName.equals("course3") && !columnName.equals("course4") &&
            !columnName.equals("course5")) {
            throw new IllegalArgumentException("Invalid column name");
        }

        String sql = "SELECT 1 FROM course WHERE student_id = ? AND " + columnName + " = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.setString(2, courseValue);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error checking course existence", ex);
        }
        return false;
    }

    // ✅ Optional: Getter for connection if needed externally
    public Connection getConnection() {
        return con;
    }
    
    
public void insertStudent(int id, int sid, int semester, String course1, String course2,
                          String course3, String course4, String course5) {

    String sql = "INSERT INTO course (id, student_id, semester, course1, course2, course3, course4, course5) VALUES (?,?,?,?,?,?,?,?)";

    try (PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, id);          // course ID
        ps.setInt(2, sid);         // student ID
        ps.setInt(3, semester);    // semester number
        ps.setString(4, course1);  // course1
        ps.setString(5, course2);  // course2
        ps.setString(6, course3);  // course3
        ps.setString(7, course4);  // course4
        ps.setString(8, course5);  // course5

        int rows = ps.executeUpdate();

        if (rows > 0) {
            JOptionPane.showMessageDialog(null, "✅ New semester and courses added successfully");
        } else {
            JOptionPane.showMessageDialog(null, "⚠ No data inserted!");
        }

    } catch (SQLException ex) {
        LOGGER.log(Level.SEVERE, "SQL Error while inserting semester and courses", ex);
        JOptionPane.showMessageDialog(null, "SQL Error: " + ex.getMessage());
    }
}

public void insertCourse(int id, int sid, int semester, 
                         String course1, String course2, String course3, 
                         String course4, String course5) {
    
    String sql = "INSERT INTO course (id, student_id, semester, course1, course2, course3, course4, course5) VALUES (?,?,?,?,?,?,?,?)";
    
    try (PreparedStatement ps = con.prepareStatement(sql)) {
        
        ps.setInt(1, id);  // ⚠ If AUTO_INCREMENT, remove this line & '?' in SQL
        ps.setInt(2, sid);
        ps.setInt(3, semester);
        ps.setString(4, course1);
        ps.setString(5, course2);
        ps.setString(6, course3);
        ps.setString(7, course4);
        ps.setString(8, course5);  // ✅ corrected index

        int rows = ps.executeUpdate();
        System.out.println("Rows inserted: " + rows);
        
    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(null, "SQL Error: " + ex.getMessage());
    }
}

public void getCourseValue(JTable table, String studentId) {
    try {
        String query;
        if (studentId == null || studentId.trim().isEmpty()) {
            // ✅ If no studentId → show all records
            query = "SELECT * FROM course ORDER BY id DESC";
            ps = con.prepareStatement(query);
        } else {
            // ✅ If studentId given → filter by student_id
            query = "SELECT * FROM course WHERE student_id = ? ORDER BY id DESC";
            ps = con.prepareStatement(query);
            ps.setInt(1, Integer.parseInt(studentId)); 
        }

        ResultSet rs = ps.executeQuery();

        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // clear table

        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getInt("id"),
                rs.getInt("student_id"),
                rs.getInt("semester"),
                rs.getString("course1"),
                rs.getString("course2"),
                rs.getString("course3"),
                rs.getString("course4"),
                rs.getString("course5")
            });
        }

        table.setModel(model);
        model.fireTableDataChanged();

    } catch (SQLException ex) {
        Logger.getLogger(Course.class.getName()).log(Level.SEVERE, null, ex);
    }
}




}
