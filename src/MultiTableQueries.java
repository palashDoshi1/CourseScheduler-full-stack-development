import java.sql.*;
import java.util.ArrayList;

public class MultiTableQueries {
    private static Connection connection;
    private static PreparedStatement getAllClassDescriptions;
    private static ResultSet resultSet;

    public static ArrayList<ClassDescription> getAllClassDescriptions(String semester) {
        connection = DBConnection.getConnection();
        ArrayList<ClassDescription> list = new ArrayList<>();
        try {
            // join class + course to get description
            getAllClassDescriptions = connection.prepareStatement(
                "SELECT c.courseCode, co.description, c.seats " +
                "FROM class c JOIN course co ON c.courseCode = co.courseCode " +
                "WHERE c.semester = ? ORDER BY c.courseCode");
            getAllClassDescriptions.setString(1, semester);
            resultSet = getAllClassDescriptions.executeQuery();
            while (resultSet.next()) {
                list.add(new ClassDescription(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getInt(3)
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}