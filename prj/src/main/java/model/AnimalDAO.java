package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AnimalDAO extends DAO{
    private static AnimalDAO instance;
    
    private AnimalDAO(){
        getConnection();
        createTable();
    }
    
    //criando um AnimalDAO
    public static AnimalDAO getInstance(){
        return (instance == null ? (instance = new AnimalDAO()) : instance);
    } 

    
//CRUD
    //create
    public Animal create(String name, int year_birth, int gender, int id_owner, int id_species){
        PreparedStatement stmt;
        try {
            stmt = DAO.getConnection().prepareStatement("INSERT INTO animal (name, year_birth, gender, id_client, id_species) VALUES (?,?,?,?,?)");
            stmt.setString(1, name);
            stmt.setInt(2, year_birth);
            stmt.setInt(3, gender);
            stmt.setInt(4, id_owner);
            stmt.setInt(5, id_species);
            
            executeUpdate(stmt);
        } 
        catch (SQLException ex) {
            Logger.getLogger(AnimalDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return retrieveById(lastId("animal", "id"));
    }
    
    private Animal buildObject(ResultSet rs){
        Animal animal = null;

        try {
            animal = new Animal(rs.getInt("id"), rs.getInt("id_client"), rs.getString("name"), rs.getInt("year_birth"), rs.getInt("gender"), rs.getInt("id_species"));
        } 
        catch (SQLException ex) {
            Logger.getLogger(AnimalDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return animal;
    }
    
    
    // retriever 
    public List retrieve(String query){
        List<Animal> animais = new ArrayList();
        ResultSet rs = getResultSet(query);
        
        try {
            while(rs.next()){
                animais.add(buildObject(rs));
            }
        } 
        catch (SQLException ex) {
            Logger.getLogger(AnimalDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return animais;
    }
    
    public List retrieveAll(){
        return this.retrieve("SELECT * FROM animal");
    }
    
    public Animal retrieveLast(){
        List<Animal> animals = this.retrieve("SELECT * FROM animal WHERE id = " + lastId("animal","id"));
        return (animals.size() < 1 ? null : animals.get(0));
    }
    
    public Animal retrieveById(int id){
        List<Animal> animals = this.retrieve("SELECT * FROM animal WHERE id = " + id);
        return (animals.size() < 1 ? null : animals.get(0));
    }
    
    public List retrieveByIdClient(int id){
        return this.retrieve("SELECT * FROM animal WHERE id_client = " + id);
    }
    
    public List retrieveByIdSpecies(int id){
        return this.retrieve("SELECT * FROM animal WHERE id_species = " + id);
    }
    
    public List retrieveBySimilarName(String name){
        return this.retrieve("SELECT * FROM animal WHERE name LIKE '%" + name + "%'");
    }
    
    public List retrieveBySimilarName(String name, int id_owner){
        return this.retrieve("SELECT * FROM animal WHERE name LIKE '%" + name + "%' AND id_client = " + id_owner);
    }
    
    
    //update
    public void update(Animal animal){
        PreparedStatement stmt;
        
        try {
            stmt = DAO.getConnection().prepareStatement("UPDATE animal SET name = ?, year_birth = ?, gender = ?, id_species = ?, id_client = ? WHERE id = ?");
            stmt.setString(1, animal.getName());
            stmt.setInt(2, animal.getYearBirth());
            stmt.setInt(3, animal.getGender());
            stmt.setInt(4, animal.getIdSpecies());
            stmt.setInt(5, animal.getIdOwner());
            stmt.setInt(6, animal.getId());
            
            executeUpdate(stmt);
        }
        catch (SQLException ex) {
            Logger.getLogger(AnimalDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    // delete   
    public void delete(int id_animal) {
        PreparedStatement stmt;
        try {
            stmt = DAO.getConnection().prepareStatement("DELETE FROM animal WHERE id = ?");
            stmt.setInt(1, id_animal);
        
            executeUpdate(stmt);
        } 
        catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
    
}
