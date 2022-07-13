package model;

import java.util.Calendar;

public class Animal {
    private final int id;
    private int id_owner;
    private String name;
    private int year_birth;
    private int gender; //0-femea, 1-macho
    private int id_species;

    public Animal(int id, int id_owner, String name, int year_birth, int gender, int id_species) {
        this.id = id;
        this.id_owner = id_owner;
        this.name = name;
        this.year_birth = year_birth;
        this.gender = gender;
        this.id_species = id_species;
    }

    public int getId(){
        return id;
    }
    
    public int getIdOwner() {
        return id_owner;
    }

    public void setIdOwner(int idOwner) {
        this.id_owner = idOwner;
    }

    public String getName() {
        return name;
    }

    public boolean setName(String name) {
        if(name.isBlank())
            return false;
        
        this.name = name;
        return true;
    }

    public int getYearBirth() {
        return year_birth;
    }

    public boolean setYearBirth(int year_birth) {
        if(year_birth < 1900 || year_birth > Calendar.getInstance().get(Calendar.YEAR))
            return false;
            
        this.year_birth = year_birth;
        return true;
    }

    public int getGender() {
        return gender;
    }

    public boolean setGender(int gender) {
        if(gender != 0 && gender != 1)
            return false;
        
        this.gender = gender;
        return true;
    }

    public int getIdSpecies() {
        return id_species;
    }

    public void setIdSpecies(int id_species) {
        this.id_species = id_species;
    }

    @Override
    public String toString() {
        return "Animal{" + "id=" + id + ", id_owner=" + id_owner + ", name=" + name + ", year_birth=" + year_birth + ", gender=" + gender + ", id_species=" + id_species + '}';
    }
    
    
    
}
