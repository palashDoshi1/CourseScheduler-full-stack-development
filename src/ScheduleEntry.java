import java.sql.Timestamp;

public class ScheduleEntry {

    private String semester;
    private String courseCode;
    private String studentID;
    private String status;      // "S" = scheduled, "W" = waitlisted
    private Timestamp timestamp;

    public ScheduleEntry(String semester,
                         String courseCode,
                         String studentID,
                         String status,
                         Timestamp timestamp) {
        this.semester   = semester;
        this.courseCode = courseCode;
        this.studentID  = studentID;
        this.status     = status;
        this.timestamp  = timestamp;
    }

    public String getSemester()   { return semester; }
    public String getCourseCode() { return courseCode; }
    public String getStudentID()  { return studentID; }
    public String getStatus()     { return status; }
    public Timestamp getTimestamp() { return timestamp; }

    @Override
    public String toString() {
        return semester + " " + courseCode + " " + studentID + " " + status;
    }
}