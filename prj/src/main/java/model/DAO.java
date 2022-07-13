package model;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class DAO {
    public static final String DBURL = "jdbc:sqlite:clinicavet1.db";
    private static Connection con;
    protected static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    // conexao com o banco
    public static Connection getConnection() {
        if (con == null) {
            try {
                con = DriverManager.getConnection(DBURL);
            } 
            catch (SQLException e) {
                System.err.println("Exception: " + e.getMessage());
            }
        }
        
        return con;
    }

    protected ResultSet getResultSet(String query) {
        Statement s;
        ResultSet rs = null;
        try {
            s = (Statement) con.createStatement();
            rs = s.executeQuery(query);
        } 
        catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        
        return rs;
    }

    protected int executeUpdate(PreparedStatement queryStatement) throws SQLException {
        int update;
        update = queryStatement.executeUpdate();
        
        return update;
    }

    protected int lastId(String tableName, String primaryKey) {
        Statement s;
        int lastId = -1;
        try {
            s = (Statement) con.createStatement();
            ResultSet rs = s.executeQuery("SELECT MAX(" + primaryKey + ") AS id FROM " + tableName);
            if (rs.next()) {
                lastId = rs.getInt("id");
            }
        } 
        catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        
        return lastId;
    }

    public static void closeConnection() {
        try {
            (DAO.getConnection()).close();
        } 
        catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
    
    // criando as tabelas do banco
    protected final boolean createTable() {
        try {
            PreparedStatement stmt;
            
            // cliente:
            stmt = DAO.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS client( \n"
                    + "id INTEGER PRIMARY KEY, \n"
                    + "name VARCHAR, \n"
                    + "address VARCHAR, \n"
                    + "tel VARCHAR, \n"
                    + "cep VARCHAR, \n"
                    + "email VARCHAR); \n");
            executeUpdate(stmt);
            
            // animal:
            stmt = DAO.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS animal( \n"
                    + "id INTEGER PRIMARY KEY, \n"
                    + "id_client INTEGER, \n"
                    + "name VARCHAR, \n"
                    + "year_birth INTEGER, \n"
                    + "gender INTEGER, \n"
                    + "id_species INTEGER); \n");
            executeUpdate(stmt);
            
            // especie:
            stmt = DAO.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS species( \n"
                    + "id INTEGER PRIMARY KEY, \n"
                    + "name VARCHAR); \n");
            executeUpdate(stmt);

            // veterinario:
            stmt = DAO.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS vet( \n"
                    + "id INTEGER PRIMARY KEY, \n"
                    + "name VARCHAR, \n"
                    + "email VARCHAR, \n"
                    + "tel VARCHAR); \n");
            executeUpdate(stmt);        
            
            // consulta:
            stmt = DAO.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS appointment( \n"
                    + "id INTEGER PRIMARY KEY, \n"
                    + "date TEXT, \n"
                    + "hour VARCHAR, \n"
                    + "note VARCHAR, \n"
                    + "id_animal INTEGER, \n"
                    + "id_vet INTEGER, \n"
                    + "id_treatment INTEGER, \n"
                    + "finished INTEGER); \n");
            executeUpdate(stmt);        
            
            // Table exame:
            stmt = DAO.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS exam( \n"
                    + "id INTEGER PRIMARY KEY, \n"
                    + "descr VARCHAR, \n"
                    + "id_appointment INTEGER); \n");
            executeUpdate(stmt);    
            
            // Default element for species:
            stmt = DAO.getConnection().prepareStatement("INSERT OR IGNORE INTO species (id, name) VALUES (1, 'Cachorro')");
            executeUpdate(stmt);
            
            return true;
        } 
        catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
     
        return false;
    }

}
