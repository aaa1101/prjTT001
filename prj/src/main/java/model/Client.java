package model;

public class Client {
    private final int id;
    private String name;
    private String address;
    private String tel;
    private String cep;
    private String email;

    public Client(int id, String name, String address, String tel, String cep, String email) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.tel = tel.replaceAll("[()-]","");
        this.cep = cep;
        this.email = email;
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

    public String getAddress() {
        return address;
    }

    public boolean setAddress(String address) {
        if(address.isBlank())
            return false;
        
        this.address = address;
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

    public String getCep() {
        return cep;
    }

    public boolean setCep(String cep) {
        if(cep.isBlank())
            return false;
        
        this.cep = cep;
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

    @Override
    public String toString() {
        return "Client{" + "id=" + id + ", name=" + name + ", address=" + address + ", tel=" + tel + ", cep=" + cep + ", email=" + email + '}';
    }
    
}
