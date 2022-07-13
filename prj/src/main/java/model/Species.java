package model;

public class Species {
    private final int id;
    private String name;

    public Species(int id, String name) {
        this.id = id;
        this.name = name;
    }
    
    public int getId(){
        return id;
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

    @Override
    public String toString() {
        return name;
    }
       
}
