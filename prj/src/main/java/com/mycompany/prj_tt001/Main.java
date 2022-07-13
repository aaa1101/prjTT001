package com.mycompany.prj_tt001;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import model.*;

public class Main {
    public static void main(String[] args) {
        //ClientDAO.getInstance().retrieveAll();
        /*
        
        Client c1 = ClientDAO.getInstance().create("Ana", "rua x n y", "13123456789", "11111111", "ana@gmail.com");
        Client c2 = ClientDAO.getInstance().create("Marcos", "rua x2 n y2", "(13)2345-6789", "22222222", "marcos@gmail.com");
        Client c3 = ClientDAO.getInstance().create("Natalia", "rua x3 n y3", "1312345-6789", "333333333", "natalia@gmail.com");
        Client c4 = ClientDAO.getInstance().create("Maria", "rua x4 n y4", "13123456789", "444444444", "maria@gmail.com");
        
        Animal a1 = AnimalDAO.getInstance().create("Allanis", 2012, 1, 1, 2);
        Animal a2 = AnimalDAO.getInstance().create("Uly", 2016, 0, 1, 2);
        Animal a3 = AnimalDAO.getInstance().create("Duquesa", 2008, 0, 1, 3);
        Animal a4 = AnimalDAO.getInstance().create("Alfredo", 2000, 1, 2, 1);
        Animal a5 = AnimalDAO.getInstance().create("Manda-chuva", 2014, 1, 2, 4);
        Animal a6 = AnimalDAO.getInstance().create("Sky", 2020, 0, 3, 1);
        Animal a7 = AnimalDAO.getInstance().create("Grude", 2021, 0, 3, 2);
        Animal a8 = AnimalDAO.getInstance().create("Tiba", 2002, 1, 4, 3);
        
        Species s1 = SpeciesDAO.getInstance().create("Gato");
        Species s2 = SpeciesDAO.getInstance().create("Coelho");
        Species s3 = SpeciesDAO.getInstance().create("Hamster"); 
        
        Vet v1 = VetDAO.getInstance().create("Alice", "alice@gmail.com", "12123456789");
        Vet v2 = VetDAO.getInstance().create("Celia", "celia@gmail.com", "14123456789");
        Vet v3 = VetDAO.getInstance().create("Theo", "theo@gmail.com", "12123456789");
        Vet v4 = VetDAO.getInstance().create("Arthur", "arthur@gmail.com", "14123456789");
        
        Calendar c = Calendar.getInstance();
        c.set(2021, 10, 20);
        Appointment apt1 = AppointmentDAO.getInstance().create(c, "11:00", "dor de estomago", 2, 1, false);
        c.set(2021, 10, 20);
        Appointment apt2 = AppointmentDAO.getInstance().create(c, "09:00", "pulgas", 2, 2, false);
        c.set(2021, 10, 10);
        Appointment apt3 = AppointmentDAO.getInstance().create(c, "15:15", "pata quebrada", 8, 3, true);
        c.set(2021, 4, 6);
        Appointment apt4 = AppointmentDAO.getInstance().create(c, "10:00", "inflamacao na pele", 1, 4, true);
        
        List<Animal> animais = AnimalDAO.getInstance().retrieveAll();
        for(Animal a: animais){
            System.out.println(a.toString());
        }
        
        
        /*
        System.out.println("Species: ");
        speciesTest();

        System.out.println("Client: ");       
        clientTest();
        
        System.out.println("Animal: ");
        animalTest();
        
        System.out.println("Vet: ");
        vetTest();
        
        System.out.println("Treatment: ");
        treatmentTest();
        
        System.out.println("Appointment: ");
        appointmentTest();
        
        System.out.println("Exam: ");
        examTest();

*/
    }
    
    public static void animalTest(){
        Animal a1 = AnimalDAO.getInstance().create("pompom", 2012, 1, 1, 2);
        Animal a2 = AnimalDAO.getInstance().create("tufi", 2016, 0, 2, 2);
        Animal a3 = AnimalDAO.getInstance().create("mino", 2008, 0, 3, 3);
        Animal a4 = AnimalDAO.getInstance().create("excluir", 2000, 0, 1, 1);
        
        if(a1.setGender(2))
            AnimalDAO.getInstance().update(a1);
        
        if(a2.setName(""))
            AnimalDAO.getInstance().update(a2);
        
        if(a3.setGender(1))
            AnimalDAO.getInstance().update(a3);
        
        System.out.println("Similar name");
        List<Animal> cs = AnimalDAO.getInstance().retrieveBySimilarName("o");
        cs.forEach(i ->{
            System.out.println(i.toString());
        });
        
        System.out.println("All");
        AnimalDAO.getInstance().delete(a4.getId());
        List<Animal> c = AnimalDAO.getInstance().retrieveAll();        
        c.forEach(i ->{
            System.out.println(i.toString());
        });  
        
        System.out.println("");
    }
    
    public static void appointmentTest(){
        Calendar dt = Calendar.getInstance();
        dt.set(2021, 9, 13);
        
        Appointment a1 = AppointmentDAO.getInstance().create(dt, "16:30", "", 1, 2, false);
        
        dt.set(2021, 1, 19);
        AppointmentDAO.getInstance().create(dt, "12:00", "Nada identificado", 1, 2, true);
        Appointment a2 = AppointmentDAO.getInstance().create(dt, "18:45", "Excluir", 1, 2, true);
        
        a1.setFinished(true);
        a1.setNote("Nada identificado");
        AppointmentDAO.getInstance().update(a1);
        
        ClientDAO.getInstance().delete(a2.getId());
        List<Appointment> a = AppointmentDAO.getInstance().retrieveAll();        
        a.forEach(i ->{
            System.out.println(i.toString());
        });  
        
        System.out.println("");  
    }
    
    public static void clientTest(){
        Client c1 = ClientDAO.getInstance().create("Cliente 1", "rua x n y", "13123456789", "11111111", "aline@gmail.com");
        Client c2 = ClientDAO.getInstance().create("Cliente 2", "rua x n y", "(13)2345-6789", "22222222", "stefany@gmail.com");
        Client c3 = ClientDAO.getInstance().create("Cliente 3", "rua x n y", "1312345-6789", "333333333", "alice@gmail.com");
        Client c4 = ClientDAO.getInstance().create("excluir", "------", "1111111111", "11111111", "excluir@gmail.com");
        
        if(c1.setTel("(13)98142-7777"))
            ClientDAO.getInstance().update(c1);
        
        if(c2.setEmail("email@invalido"))
            ClientDAO.getInstance().update(c2);
        
        if(c3.setEmail("atualizado@email.com"))
            ClientDAO.getInstance().update(c3);
        
        
        System.out.println("Similar name");
        List<Client> cs = ClientDAO.getInstance().retrieveBySimilarName("ali");
        cs.forEach(i ->{
            System.out.println(i.toString());
        });
        
        System.out.println("All");
        ClientDAO.getInstance().delete(c4.getId());
        List<Client> c = ClientDAO.getInstance().retrieveAll();        
        c.forEach(i ->{
            System.out.println(i.toString());
        });   
        System.out.println("");     
    }
    
    public static void examTest(){
        ExamDAO.getInstance().create("exame 1", 1);
        Exam e1 = ExamDAO.getInstance().create("exame", 2);
        Exam e2 = ExamDAO.getInstance().create("excluir", 1);
                
        if(e1.setDescr("Exame 2"))
            ExamDAO.getInstance().update(e1);
        
        
        System.out.println("All");
        ExamDAO.getInstance().delete(e2.getId());
        List<Exam> s = ExamDAO.getInstance().retrieveAll();
        s.forEach(i -> {
            System.out.println(i.toString());
        });
        
        System.out.println("");
    }
    
    public static void speciesTest(){
        SpeciesDAO.getInstance().create("Gato");
        Species s1 = SpeciesDAO.getInstance().create("Coelho");
        Species s2 = SpeciesDAO.getInstance().create("passar");
        Species s3 = SpeciesDAO.getInstance().create("excluir");
        
        if(s1.setName(""))
            SpeciesDAO.getInstance().update(s1);
        
        if(s2.setName("Passaro"))
            SpeciesDAO.getInstance().update(s2);
        
        System.out.println("All");
        SpeciesDAO.getInstance().delete(s3.getId());
        List<Species> s = SpeciesDAO.getInstance().retrieveAll();
        s.forEach(i -> {
            System.out.println(i.toString());
        });
        
        System.out.println("");
    }

    public static void vetTest(){
        Vet v1 = VetDAO.getInstance().create("maria", "maria@gmail.com", "123456789");
        Vet v2 = VetDAO.getInstance().create("joao", "joao@gmail.com", "(12)3456-7890");
        Vet v3 = VetDAO.getInstance().create("marcia", "marcia@gmail.com", "129345-6789");
        Vet v4 = VetDAO.getInstance().create("excluir", "excluir@gmail.com", "123456789");
        
        if(v1.setTel("(13)98142-7777"))
            VetDAO.getInstance().update(v1);
        
        if(v2.setEmail("email.invalido@com"))
            VetDAO.getInstance().update(v2);
        
        if(v3.setEmail("email.atualizado@email.com"))
            VetDAO.getInstance().update(v3);
                
        System.out.println("Similar name");
        List<Vet> vs = VetDAO.getInstance().retrieveBySimilarName("mar");
        vs.forEach(i ->{
            System.out.println(i.toString());
        });
        
        System.out.println("All");
        VetDAO.getInstance().delete(v4.getId());
        List<Vet> v = VetDAO.getInstance().retrieveAll();        
        v.forEach(i ->{
            System.out.println(i.toString());
        });
        
        System.out.println("");
    }
}
