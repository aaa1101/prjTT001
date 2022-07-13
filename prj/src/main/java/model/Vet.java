package model;

public class Vet {
    private final int id;
    private String name;
    private String email;
    private String tel;

    public Vet(int id, String name, String email, String tel) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.tel = tel.replaceAll("[()-]","");
    }

    public int getId() {
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

    public String getEmail() {
        return email;
    }

    public boolean setEmail(String email) {
        if(!email.contains("@") || email.indexOf(".", email.indexOf("@")) == -1)
            return false;
        
        this.email = email;
        return true;
    }

    public String getTel() {
        return tel;
    }

    public boolean setTel(String tel) {
        if(tel.replaceAll("[()-]","").length() < 10)
            return false;
        
        this.tel = tel;
        return true;
    }

    @Override
    public String toString() {
        return "Vet{" + "id=" + id + ", name=" + name + ", email=" + email + ", tel=" + tel + '}';
    }
    
}
