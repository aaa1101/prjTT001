package model;

import java.util.Calendar;

public class Appointment {
    private final int id;
    private Calendar date;
    private String hour;
    private String note;  
    private int id_animal;
    private int id_vet;
    private boolean finished;   

    public Appointment(int id, Calendar date, String hour, String note, int id_animal, int id_vet, boolean finished) {
        this.id = id;
        this.date = date;
        this.hour = hour;
        this.note = note;
        this.id_animal = id_animal;
        this.id_vet = id_vet;
        this.finished = finished;
    }

    public int getId() {
        return id;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        if(!hour.isBlank())
            this.hour = hour;
    }

    public String getNote() {
        return note;
    }

    public boolean setNote(String note) {
        if(note.isBlank())
            return false;
        
        this.note = note;
        return true;
    }

    public int getIdAnimal() {
        return id_animal;
    }

    public void setIdAnimal(int id_animal) {
        this.id_animal = id_animal;
    }

    public int getIdVet() {
        return id_vet;
    }

    public void setIdVet(int id_vet) {
        this.id_vet = id_vet;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    @Override
    public String toString() {
        String s = "[" + date.get(Calendar.DATE) + "/" + (date.get(Calendar.MONTH)+1) + "/" + date.get(Calendar.YEAR) + " - " + hour + "] (";
        s += (finished ? "Finalizado): " : "Pendente): ");
        s += note;
        
        return s;
    }
        
    
}
