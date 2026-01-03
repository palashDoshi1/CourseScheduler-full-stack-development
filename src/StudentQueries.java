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

public class StudentQueries {
    
    private static Connection connection;
    private static PreparedStatement addStudent;
    private static PreparedStatement getAllStudents;
    private static ResultSet resultSet;
    private static PreparedStatement deleteStudent;
    private static PreparedStatement getStudentByID;
    
    public static void addStudent(StudentEntry student) {
        connection = DBConnection.getConnection();
        try {
            addStudent = connection.prepareStatement(
                "INSERT INTO student (studentID, firstName, lastName) VALUES (?, ?, ?)");
            addStudent.setString(1, student.getStudentID());
            addStudent.setString(2, student.getFirstName());
            addStudent.setString(3, student.getLastName());
            addStudent.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<StudentEntry> getAllStudents() {
        connection = DBConnection.getConnection();
        ArrayList<StudentEntry> students = new ArrayList<>();
        try {
            getAllStudents = connection.prepareStatement(
                "SELECT studentID, firstName, lastName FROM student ORDER BY studentID");
            resultSet = getAllStudents.executeQuery();
            while (resultSet.next()) {
                students.add(new StudentEntry(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3)
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }
    
    public static void deleteStudent(String studentID) {
        connection = DBConnection.getConnection();
        try {
            deleteStudent = connection.prepareStatement(
                "DELETE FROM student WHERE studentID = ?"
            );
            deleteStudent.setString(1, studentID);
            deleteStudent.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static StudentEntry getStudent(String studentID) {
        connection = DBConnection.getConnection();
        StudentEntry s = null;
        try {
            getStudentByID = connection.prepareStatement(
                "SELECT studentID, firstName, lastName FROM student WHERE studentID = ?"
            );
            getStudentByID.setString(1, studentID);
            resultSet = getStudentByID.executeQuery();
            if (resultSet.next()) {
                s = new StudentEntry(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return s;
    }
}
