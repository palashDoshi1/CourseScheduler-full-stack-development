/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author acv
 */
public class SemesterQueries {
    private static Connection connection;
    private static ArrayList<String> faculty = new ArrayList<String>();
    private static PreparedStatement addSemester;
    private static PreparedStatement getSemesterList;
    private static ResultSet resultSet;
    
    public static void addSemester(SemesterEntry semester)
    {
        connection = DBConnection.getConnection();
        try
        {
            addSemester = connection.prepareStatement("insert into semester (semesterTerm, semesterYear) values (?, ?)");
            addSemester.setString(1, semester.getTerm());
            addSemester.setString(2, semester.getYear());
            addSemester.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
    }
    
    public static ArrayList<SemesterEntry> getSemesterList()
    {
        connection = DBConnection.getConnection();
        ArrayList<SemesterEntry> semesterList = new ArrayList<>();
        try
        {
            getSemesterList = connection.prepareStatement("select semesterTerm, semesterYear from semester order by semesterYear");
            resultSet = getSemesterList.executeQuery();
            
            while(resultSet.next())
            {
                SemesterEntry semester = new SemesterEntry(resultSet.getString(1), resultSet.getString(2));
                semesterList.add(semester);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return semesterList;
        
    }
    
    
}
