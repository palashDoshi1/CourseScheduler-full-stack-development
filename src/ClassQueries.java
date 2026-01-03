/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author sheetalmehta
 */

import java.sql.*;
import java.util.ArrayList;

public class ClassQueries {
    private static Connection connection;
    private static PreparedStatement addClass;
    private static PreparedStatement getClassSeats;
    private static PreparedStatement getAllCourseCodesForSemester;
    private static ResultSet resultSet;
    private static PreparedStatement dropClass;

    public static void addClass(ClassEntry cls) {
        connection = DBConnection.getConnection();
        try {
            addClass = connection.prepareStatement(
                "INSERT INTO class (semester, courseCode, seats) VALUES (?, ?, ?)");
            addClass.setString(1, cls.getSemester());
            addClass.setString(2, cls.getCourseCode());
            addClass.setInt(3, cls.getSeats());
            addClass.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> getAllCourseCodes(String semester) {
        connection = DBConnection.getConnection();
        ArrayList<String> codes = new ArrayList<>();
        try {
            getAllCourseCodesForSemester = connection.prepareStatement(
                "SELECT courseCode FROM class WHERE semester = ? ORDER BY courseCode");
            getAllCourseCodesForSemester.setString(1, semester);
            resultSet = getAllCourseCodesForSemester.executeQuery();
            while (resultSet.next()) {
                codes.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return codes;
    }

    public static int getClassSeats(String semester, String courseCode) {
        connection = DBConnection.getConnection();
        int seats = 0;
        try {
            getClassSeats = connection.prepareStatement(
                "SELECT seats FROM class WHERE semester = ? AND courseCode = ?");
            getClassSeats.setString(1, semester);
            getClassSeats.setString(2, courseCode);
            resultSet = getClassSeats.executeQuery();
            if (resultSet.next()) {
                seats = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return seats;
    }
    
    public static void dropClass(String semester, String courseCode) {
        connection = DBConnection.getConnection();
        try {
            dropClass = connection.prepareStatement(
                "DELETE FROM class WHERE semester = ? AND courseCode = ?"
            );
            dropClass.setString(1, semester);
            dropClass.setString(2, courseCode);
            dropClass.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
