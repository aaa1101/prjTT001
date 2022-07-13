package model;

public class Exam {
    private final int id;
    private String descr;
    private int id_appointment;

    public Exam(int id, String descr, int id_appointment) {
        this.id = id;
        this.descr = descr;
        this.id_appointment = id_appointment;
    }
    
    public int getId(){
        return id;
    }

    public String getDescr() {
        return descr;
    }

    public boolean setDescr(String descr) {
        if(descr.isBlank())
            return false;
        
        this.descr = descr;
        return true;
    }

    public int getIdAppointment() {
        return id_appointment;
    }

    public void setIdAppointment(int id_appointment) {
        this.id_appointment = id_appointment;
    }

    @Override
    public String toString() {
        return "Exam{" + "id=" + id + ", descr=" + descr + ", id_appointment=" + id_appointment + '}';
    }
    
}
