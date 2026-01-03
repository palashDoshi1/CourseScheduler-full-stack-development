/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author acv
 */
public class SemesterEntry {
    private String year;
    private String term;

    public SemesterEntry(String year, String term) {
        this.year = year;
        this.term = term;
    }

    public String getYear() {
        return year;
    }

    public String getTerm() {
        return term;
    }
    
    @Override
    public String toString() {
        return term + " " + year;
    }
    
}
