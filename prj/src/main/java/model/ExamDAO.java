package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExamDAO extends DAO {
    private static ExamDAO instance;
    
    private ExamDAO(){
        getConnection();
        createTable();
    }
    
    //criando um ExamDAO
    public static ExamDAO getInstance(){
        return (instance == null ? (instance = new ExamDAO()) : instance);
    } 
    
    
//CRUD
    
    //create
    public Exam create(String descr, int id_appointment){
        PreparedStatement stmt;
        try {
            stmt = DAO.getConnection().prepareStatement("INSERT INTO exam (descr, id_appointment) VALUES (?,?)");
            stmt.setString(1, descr);
            stmt.setInt(2, id_appointment);
            
            executeUpdate(stmt);
        } 
        catch (SQLException ex) {
            Logger.getLogger(ExamDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return retrieveById(lastId("exam", "id"));
    }
    
    private Exam buildObject(ResultSet rs){
        Exam exam = null;

        try {
            exam = new Exam(rs.getInt("id"), rs.getString("descr"), rs.getInt("id_appointment"));
        } 
        catch (SQLException ex) {
            Logger.getLogger(ExamDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return exam;
    }
    
    
    // retriever 
    public List retrieve(String query){
        List<Exam> exams = new ArrayList();
        ResultSet rs = getResultSet(query);
        
        try {
            while(rs.next()){
                exams.add(buildObject(rs));
            }
        } 
        catch (SQLException ex) {
            Logger.getLogger(ExamDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return exams;
    }
    
    public List retrieveAll(){
        return this.retrieve("SELECT * FROM exam");
    }
    
    public Exam retrieveLast(){
        List<Exam> exams = this.retrieve("SELECT * FROM exam WHERE id = " + lastId("exam","id"));
        return (exams.size() < 1 ? null : exams.get(0));
    }
    
    public Exam retrieveById(int id){
        List<Exam> exams = this.retrieve("SELECT * FROM exam WHERE id = " + id);
        return (exams.size() < 1 ? null : exams.get(0));
    }
    
    public Exam retrieveByIdAppointment(int id){
        List<Exam> exams = this.retrieve("SELECT * FROM exam WHERE id_appointment = " + id);
        return (exams.size() < 1 ? null : exams.get(0));
    }
    
    //update
    public void update(Exam exam){
        PreparedStatement stmt;
        
        try {
            stmt = DAO.getConnection().prepareStatement("UPDATE exam SET descr = ?, id_appointment = ? WHERE id = ?");
            stmt.setString(1, exam.getDescr());
            stmt.setInt(2, exam.getIdAppointment());
            stmt.setInt(3, exam.getId());
            
            executeUpdate(stmt);
        }
        catch (SQLException ex) {
            Logger.getLogger(ExamDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    // delete   
    public void delete(int id_exam) {
        PreparedStatement stmt;
        try {
            stmt = DAO.getConnection().prepareStatement("DELETE FROM exam WHERE id = ?");
            stmt.setInt(1, id_exam);
        
            executeUpdate(stmt);
        } 
        catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
}
