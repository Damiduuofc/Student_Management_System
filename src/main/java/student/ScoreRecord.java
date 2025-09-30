
package student;

/**
 *
 * @author damiduuofc
 */
public class ScoreRecord {
    private int id;
    private int studentId;
    private int semester;
    private String course1, course2, course3, course4, course5;
    private double score1, score2, score3, score4, score5;
    private double average;

    public ScoreRecord(int id, int studentId, int semester,
                       String course1, double score1,
                       String course2, double score2,
                       String course3, double score3,
                       String course4, double score4,
                       String course5, double score5,
                       double average) {
        this.id = id;
        this.studentId = studentId;
        this.semester = semester;
        this.course1 = course1;
        this.score1 = score1;
        this.course2 = course2;
        this.score2 = score2;
        this.course3 = course3;
        this.score3 = score3;
        this.course4 = course4;
        this.score4 = score4;
        this.course5 = course5;
        this.score5 = score5;
        this.average = average;
    }

    // Getters
    public int getId() { return id; }
    public int getStudentId() { return studentId; }
    public int getSemester() { return semester; }
    public String getCourse1() { return course1; }
    public double getScore1() { return score1; }
    public String getCourse2() { return course2; }
    public double getScore2() { return score2; }
    public String getCourse3() { return course3; }
    public double getScore3() { return score3; }
    public String getCourse4() { return course4; }
    public double getScore4() { return score4; }
    public String getCourse5() { return course5; }
    public double getScore5() { return score5; }
    public double getAverage() { return average; }

    // Optional: setters if needed
}



