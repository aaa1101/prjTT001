package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static model.DAO.getConnection;

public class VetDAO extends DAO {
    private static VetDAO instance;
    
    private VetDAO(){
        getConnection();
        createTable();
    }
    
    //criando um VetDAO
    public static VetDAO getInstance(){
        return (instance == null ? (instance = new VetDAO()) : instance);
    } 
    
        
//CRUD
    //create
    public Vet create(String name, String email, String tel){
        PreparedStatement stmt;
        try {
            stmt = DAO.getConnection().prepareStatement("INSERT INTO vet (name, email, tel) VALUES (?,?,?)");
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, tel);
            
            executeUpdate(stmt);
        } 
        catch (SQLException ex) {
            Logger.getLogger(VetDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return retrieveById(lastId("vet", "id"));
    }
    
    
    private Vet buildObject(ResultSet rs){
        Vet vet = null;

        try {
            vet = new Vet(rs.getInt("id"), rs.getString("name"), rs.getString("email"), rs.getString("tel"));
        } 
        catch (SQLException ex) {
            Logger.getLogger(VetDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return vet;
    }
    
    
    // retriever 
    public List retrieve(String query){
        List<Vet> vet = new ArrayList();
        ResultSet rs = getResultSet(query);
        
        try {
            while(rs.next()){
                vet.add(buildObject(rs));
            }
        } 
        catch (SQLException ex) {
            Logger.getLogger(VetDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return vet;
    }
    
    public List retrieveAll(){
        return this.retrieve("SELECT * FROM vet");
    }
    
    public Vet retrieveLast(){
        List<Vet> vet = this.retrieve("SELECT * FROM vet WHERE id = " + lastId("vet","id"));
        return (vet.size() < 1 ? null : vet.get(0));
    }
    
    public Vet retrieveById(int id){
        List<Vet> vet = this.retrieve("SELECT * FROM vet WHERE id = " + id);
        return (vet.size() < 1 ? null : vet.get(0));
    }
    
    public List retrieveBySimilarName(String name){
        return this.retrieve("SELECT * FROM vet WHERE name LIKE '%" + name + "%'");
    }
    
    
    //update
    public void update(Vet vet){
        PreparedStatement stmt;
        
        try {
            stmt = DAO.getConnection().prepareStatement("UPDATE vet SET name = ?, email = ?, tel = ? WHERE id = ?");
            stmt.setString(1, vet.getName());
            stmt.setString(2, vet.getEmail());
            stmt.setString(3, vet.getTel());
            stmt.setInt(4, vet.getId());
            
            executeUpdate(stmt);
        }
        catch (SQLException ex) {
            Logger.getLogger(VetDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    // delete   
    public void delete(int id_vet) {
        PreparedStatement stmt;
        try {
            stmt = DAO.getConnection().prepareStatement("DELETE FROM vet WHERE id = ?");
            stmt.setInt(1, id_vet);
        
            executeUpdate(stmt);
        } 
        catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
}
