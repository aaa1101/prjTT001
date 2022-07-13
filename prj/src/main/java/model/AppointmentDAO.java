package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AppointmentDAO extends DAO{
    private static AppointmentDAO instance;
    
    private AppointmentDAO(){
        getConnection();
        createTable();
    }
    
    //criando um AppointmentDAO
    public static AppointmentDAO getInstance(){
        return (instance == null ? (instance = new AppointmentDAO()) : instance);
    } 
    
    
//CRUD

    //create
    public Appointment create(Calendar date, String hour, String note, int id_animal, int id_vet, boolean finished){
        PreparedStatement stmt;
        try {
            stmt = DAO.getConnection().prepareStatement("INSERT INTO appointment (date, hour, note, id_animal, id_vet, finished) VALUES (?,?,?,?,?,?)");
            stmt.setString(1, dateFormat.format(date.getTime()));
            stmt.setString(2, hour);
            stmt.setString(3, note);
            stmt.setInt(4, id_animal);
            stmt.setInt(5, id_vet);
            stmt.setInt(6, (finished ? 1 : 0));
            
            executeUpdate(stmt);
        } 
        catch (SQLException ex) {
            Logger.getLogger(AppointmentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return retrieveById(lastId("appointment", "id"));
    }
    
    private Appointment buildObject(ResultSet rs){
        Appointment appointment = null;

        try {
            Calendar dt = Calendar.getInstance();
            dt.setTime(dateFormat.parse(rs.getString("date")));
            
            appointment = new Appointment(rs.getInt("id"), dt, rs.getString("hour"), rs.getString("note"), rs.getInt("id_animal"), rs.getInt("id_vet"), (rs.getInt("finished") == 1));
        } 
        catch (SQLException | ParseException ex) {
            Logger.getLogger(AppointmentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return appointment;
    }
    
   
    // retriever 
    public List retrieve(String query){
        List<Appointment> appointments = new ArrayList();
        ResultSet rs = getResultSet(query);
        
        try {
            while(rs.next()){
                appointments.add(buildObject(rs));
            }
        } 
        catch (SQLException ex) {
            Logger.getLogger(AppointmentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return appointments;
    }
    
    public List retrieveAll(){
        return this.retrieve("SELECT * FROM appointment ORDER BY date, hour DESC");
    }
    
    public Appointment retrieveLast(){
        List<Appointment> appointments = this.retrieve("SELECT * FROM appointment WHERE id = " + lastId("appointment","id"));
        return (appointments.size() < 1 ? null : appointments.get(0));
    }
    
    public Appointment retrieveById(int id){
        List<Appointment> appointments = this.retrieve("SELECT * FROM appointment WHERE id = " + id);
        return (appointments.size() < 1 ? null : appointments.get(0));
    }
    
    public List retrieveByIdAnimal(int id){
        return this.retrieve("SELECT * FROM appointment WHERE id_animal = " + id + " ORDER BY date, hour DESC");
    }
    
    public List retrieveByIdVet(int id){
        return this.retrieve("SELECT * FROM appointment WHERE id_vet = " + id + " ORDER BY date, hour DESC");
    }
    
    public List retrieveFinished(boolean status){
        return this.retrieve("SELECT * FROM appointment WHERE finished = " + (status ? 1 : 0) + " ORDER BY date, hour DESC");
    }
    
    public List retrieveByDate(Calendar dt){
        return this.retrieve("SELECT * FROM appointment WHERE date = '" + dateFormat.format(dt.getTime()) + "' ORDER BY date, hour DESC");
    }
    
    public Appointment retrieveByDatetimeVet(Calendar dt, String hour, int id_vet){
        List<Appointment> appointments = this.retrieve("SELECT * FROM appointment WHERE id_vet = " + id_vet + " AND date = '" + dateFormat.format(dt.getTime()) + "' AND hour = '" + hour + "'");
        return (appointments.size() < 1 ? null : appointments.get(0));
    }
    
    
    //update
    public void update(Appointment appointment){
        PreparedStatement stmt;
        
        try {
            stmt = DAO.getConnection().prepareStatement("UPDATE appointment SET date = ?, hour = ?, note = ?, id_animal = ?, id_vet = ?, finished = ? WHERE id = ?");
            stmt.setString(1, dateFormat.format(appointment.getDate().getTime()));
            stmt.setString(2, appointment.getHour());
            stmt.setString(3, appointment.getNote());
            stmt.setInt(4, appointment.getIdAnimal());
            stmt.setInt(5, appointment.getIdVet());
            stmt.setInt(6, (appointment.isFinished() ? 1 : 0));
            stmt.setInt(7, appointment.getId());
            
            executeUpdate(stmt);
        }
        catch (SQLException ex) {
            Logger.getLogger(AppointmentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    // delete   
    public void delete(int id_appointment) {
        PreparedStatement stmt;
        try {
            stmt = DAO.getConnection().prepareStatement("DELETE FROM appointment WHERE id = ?");
            stmt.setInt(1, id_appointment);
        
            executeUpdate(stmt);
        } 
        catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
}
