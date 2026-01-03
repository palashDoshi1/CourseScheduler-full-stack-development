import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class ScheduleQueries {

    private static Connection connection;
    private static PreparedStatement addScheduleEntry;
    private static PreparedStatement getScheduleByStudent;
    private static PreparedStatement getScheduledStudentCount;
    private static ResultSet resultSet;
    private static PreparedStatement getScheduledStudentsByClass;
    private static PreparedStatement getWaitlistedStudentsByClass;
    private static PreparedStatement getSemestersForStudent;
    private static PreparedStatement deleteScheduleEntry;
    private static PreparedStatement deleteScheduleByClass;
    private static PreparedStatement updateStatus;

    // Insert one schedule row (S or W)
    public static void addScheduleEntry(ScheduleEntry entry) {
        connection = DBConnection.getConnection();
        try {
            addScheduleEntry = connection.prepareStatement(
                "INSERT INTO schedule " +
                "(semester, courseCode, studentID, status, timestamp) " +
                "VALUES (?, ?, ?, ?, ?)"
            );
            addScheduleEntry.setString(1, entry.getSemester());
            addScheduleEntry.setString(2, entry.getCourseCode());
            addScheduleEntry.setString(3, entry.getStudentID());
            addScheduleEntry.setString(4, entry.getStatus());
            addScheduleEntry.setTimestamp(5, entry.getTimestamp());
            addScheduleEntry.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // For Display Schedule tab – all classes for a student in a semester
    public static ArrayList<ScheduleEntry> getScheduleByStudent(String semester, String studentID) {
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> result = new ArrayList<>();

        try {
            getScheduleByStudent = connection.prepareStatement(
                "SELECT semester, courseCode, studentID, status, timestamp " +
                "FROM schedule " +
                "WHERE semester = ? AND studentID = ? " +
                "ORDER BY timestamp"
            );
            getScheduleByStudent.setString(1, semester);
            getScheduleByStudent.setString(2, studentID);

            resultSet = getScheduleByStudent.executeQuery();
            while (resultSet.next()) {
                String sem   = resultSet.getString("semester");
                String code  = resultSet.getString("courseCode");
                String sid   = resultSet.getString("studentID");
                String stat  = resultSet.getString("status");
                Timestamp ts = resultSet.getTimestamp("timestamp");
                result.add(new ScheduleEntry(sem, code, sid, stat, ts));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    // For deciding S vs W
    public static int getScheduledStudentCount(String semester, String courseCode) {
        connection = DBConnection.getConnection();
        int count = 0;

        try {
            PreparedStatement stmt = connection.prepareStatement(
                "SELECT COUNT(studentID) FROM schedule " +
                "WHERE semester = ? AND courseCode = ? AND status = 'S'"
            );

            stmt.setString(1, semester);
            stmt.setString(2, courseCode);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);   
            }
        } 
        catch (SQLException ex) {
            ex.printStackTrace();
        }

        return count;
    }
    
    // All scheduled students in a class for a semester
    public static ArrayList<ScheduleEntry> getScheduledStudentsByClass(
            String semester, String courseCode) {

        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> list = new ArrayList<>();

        try {
            getScheduledStudentsByClass = connection.prepareStatement(
                "SELECT semester, courseCode, studentID, status, timestamp " +
                "FROM schedule " +
                "WHERE semester = ? AND courseCode = ? AND status = 'S' " +
                "ORDER BY timestamp"
            );
            getScheduledStudentsByClass.setString(1, semester);
            getScheduledStudentsByClass.setString(2, courseCode);
            resultSet = getScheduledStudentsByClass.executeQuery();

            while (resultSet.next()) {
                list.add(new ScheduleEntry(
                        resultSet.getString("semester"),
                        resultSet.getString("courseCode"),
                        resultSet.getString("studentID"),
                        resultSet.getString("status"),
                        resultSet.getTimestamp("timestamp")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // All waitlisted students in a class for a semester (in waitlist order)
    public static ArrayList<ScheduleEntry> getWaitlistedStudentsByClass(
            String semester, String courseCode) {

        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> list = new ArrayList<>();

        try {
            getWaitlistedStudentsByClass = connection.prepareStatement(
                "SELECT semester, courseCode, studentID, status, timestamp " +
                "FROM schedule " +
                "WHERE semester = ? AND courseCode = ? AND status = 'W' " +
                "ORDER BY timestamp"
            );
            getWaitlistedStudentsByClass.setString(1, semester);
            getWaitlistedStudentsByClass.setString(2, courseCode);
            resultSet = getWaitlistedStudentsByClass.executeQuery();

            while (resultSet.next()) {
                list.add(new ScheduleEntry(
                        resultSet.getString("semester"),
                        resultSet.getString("courseCode"),
                        resultSet.getString("studentID"),
                        resultSet.getString("status"),
                        resultSet.getTimestamp("timestamp")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public static ArrayList<String> getSemestersForStudent(String studentID) {
        connection = DBConnection.getConnection();
        ArrayList<String> semesters = new ArrayList<>();

        try {
            getSemestersForStudent = connection.prepareStatement(
                "SELECT DISTINCT semester FROM schedule WHERE studentID = ? ORDER BY semester"
            );
            getSemestersForStudent.setString(1, studentID);
            resultSet = getSemestersForStudent.executeQuery();

            while (resultSet.next()) {
                semesters.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return semesters;
    }
    
    // Delete one schedule row
    public static void deleteScheduleEntry(String semester, String courseCode, String studentID) {
        connection = DBConnection.getConnection();
        try {
            deleteScheduleEntry = connection.prepareStatement(
                "DELETE FROM schedule WHERE semester = ? AND courseCode = ? AND studentID = ?"
            );
            deleteScheduleEntry.setString(1, semester);
            deleteScheduleEntry.setString(2, courseCode);
            deleteScheduleEntry.setString(3, studentID);
            deleteScheduleEntry.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete all schedule rows for a class in one semester
    public static void deleteScheduleByClass(String semester, String courseCode) {
        connection = DBConnection.getConnection();
        try {
            deleteScheduleByClass = connection.prepareStatement(
                "DELETE FROM schedule WHERE semester = ? AND courseCode = ?"
            );
            deleteScheduleByClass.setString(1, semester);
            deleteScheduleByClass.setString(2, courseCode);
            deleteScheduleByClass.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Change S <-> W for a given schedule row
    public static void updateStatus(String semester, String courseCode,
                                    String studentID, String newStatus) {
        connection = DBConnection.getConnection();
        try {
            updateStatus = connection.prepareStatement(
                "UPDATE schedule SET status = ? WHERE semester = ? AND courseCode = ? AND studentID = ?"
            );
            updateStatus.setString(1, newStatus);
            updateStatus.setString(2, semester);
            updateStatus.setString(3, courseCode);
            updateStatus.setString(4, studentID);
            updateStatus.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
}