package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ClientDAO extends DAO{
    private static ClientDAO instance;
    
    private ClientDAO(){
        getConnection();
        createTable();
    }
    
    //criando um ClienteDAO
    public static ClientDAO getInstance(){
        return (instance == null ? (instance = new ClientDAO()) : instance);
    }
    
//CRUD
    // create
    public Client create(String name, String address, String tel, String cep, String email){
        try{
            PreparedStatement stmt;
            
            stmt = DAO.getConnection().prepareStatement("INSERT INTO client (name, address, tel, cep, email) VALUES (?,?,?,?,?)");
            stmt.setString(1, name);
            stmt.setString(2, address);
            stmt.setString(3, tel);
            stmt.setString(4, cep);
            stmt.setString(5, email);
            
            executeUpdate(stmt);
        }
        catch(SQLException e){
            Logger.getLogger(ClientDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        
        return retrieveById(lastId("client", "id"));
    }
    
     private Client buildObject(ResultSet rs) {
        Client client = null;
        try {
            client = new Client(rs.getInt("id"), rs.getString("name"), rs.getString("address"), rs.getString("tel"), rs.getString("cep"), rs.getString("email"));
        } 
        catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return client;
    }

         
    // Retriever 
    public List retrieve(String query) {
        List<Client> clients = new ArrayList();
        ResultSet rs = getResultSet(query);
        
        try {
            while (rs.next()) {
                clients.add(buildObject(rs));
            }
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        
        return clients;
    }
    
    public List retrieveAll() {
        return this.retrieve("SELECT * FROM client");
    }
    
    public Client retrieveLast(){
        List<Client> clients = this.retrieve("SELECT * FROM client WHERE id = " + lastId("client","id"));
        return (clients.size() < 1 ? null : clients.get(0));
    }

    public Client retrieveById(int id) {
        List<Client> clients = this.retrieve("SELECT * FROM client WHERE id = " + id);
        return (clients.isEmpty()?null:clients.get(0));
    }

    public List retrieveBySimilarName(String name) {
        return this.retrieve("SELECT * FROM client WHERE name LIKE '%" + name + "%'");
    }    
    
    
    // Updade
    public void update(Client client) {
        try {
            PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("UPDATE client SET name=?, address=?,  tel=?, cep=?, email=? WHERE id=?");
            stmt.setString(1, client.getName());
            stmt.setString(2, client.getAddress());
            stmt.setString(3, client.getTel());
            stmt.setString(4, client.getCep());
            stmt.setString(5, client.getEmail());
            stmt.setInt(6, client.getId());
            
            executeUpdate(stmt);
        } 
        catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }

    
    // Delete   
    public void delete(int id_client) {
        PreparedStatement stmt;
        try {
            stmt = DAO.getConnection().prepareStatement("DELETE FROM client WHERE id = ?");
            stmt.setInt(1, id_client);
        
            executeUpdate(stmt);
        } 
        catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
    
    
}
