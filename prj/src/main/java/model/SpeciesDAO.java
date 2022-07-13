package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SpeciesDAO extends DAO{
    private static SpeciesDAO instance;
    
    private SpeciesDAO(){
        getConnection();
        createTable();
    }
    
    public static SpeciesDAO getInstance(){
        return (instance == null ? (instance = new SpeciesDAO()) : instance);
    }
    
//CRUD
    
    //create
    public Species create(String name){
        PreparedStatement stmt;
            
        try {
            stmt = DAO.getConnection().prepareStatement("INSERT INTO species (name) VALUES (?)");
            stmt.setString(1, name);
            
            executeUpdate(stmt);
        } catch (SQLException ex) {
            Logger.getLogger(SpeciesDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return retrieveById(lastId("species", "id"));
    }
    
    
    
    private Species buildObject(ResultSet rs){
        Species species = null;

        try {
            species = new Species(rs.getInt("id"), rs.getString("name"));
        } 
        catch (SQLException ex) {
            Logger.getLogger(SpeciesDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return species;
    }
    
    
    // retriever 
    public List retrieve(String query){
        List<Species> species = new ArrayList();
        ResultSet rs = getResultSet(query);
        
        try {
            while(rs.next()){
                species.add(buildObject(rs));
            }
        } 
        catch (SQLException ex) {
            Logger.getLogger(SpeciesDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return species;
    }
    
    public List retrieveAll(){
        return this.retrieve("SELECT * FROM species");
    }
    
    public Species retrieveLast(){
        List<Species> species = this.retrieve("SELECT * FROM species WHERE id = " + lastId("species","id"));
        return (species.size() < 1 ? null : species.get(0));
    }
    
    public Species retrieveById(int id){
        List<Species> species = this.retrieve("SELECT * FROM species WHERE id = " + id);
        return (species.size() < 1 ? null : species.get(0));
    }
    
    public List retrieveBySimilarName(String name) {
        return this.retrieve("SELECT * FROM species WHERE name LIKE '%" + name + "%'");
    }   
    

    //update
    public void update(Species species){
        PreparedStatement stmt;
        
        try {
            stmt = DAO.getConnection().prepareStatement("UPDATE species SET name = ? WHERE id = ?");
            stmt.setString(1, species.getName());
            stmt.setInt(2, species.getId());
            
            executeUpdate(stmt);
        }
        catch (SQLException ex) {
            Logger.getLogger(SpeciesDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    // delete   
    public void delete(int id_species) {
        PreparedStatement stmt;
        try {
            stmt = DAO.getConnection().prepareStatement("DELETE FROM species WHERE id = ?");
            stmt.setInt(1, id_species);
        
            executeUpdate(stmt);
        } 
        catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
    
    
}
