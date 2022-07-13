package controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.JTextField;
import view.GenericTableModel;
import view.ClientTableModel;
import view.AnimalTableModel;
import view.SpeciesTableModel;
import view.VetTableModel;
import model.*;

public class Controller {
    private static Client client = null;
    private static Animal animal = null;
    private static Species species = null;
    private static Vet vet = null;
    private static List<Appointment> listAppointments = null;
    
    private static JTextField txtClient = null;
    private static JTextField txtAnimal = null;
    private static JTextField txtSpecies = null;
    private static JTable table = null;
    private static JList list = null;
    
    private static final String[] hours = {"08:00", "09:00", "10:00", "11:00", "13:15", "14:15", "15:15", "16:15", "17:15"};
    
    
    public static Client getClientSelected() {
        return client;
    }

    public static Animal getAnimalSelected() {
        return animal;
    }

    public static Species getSpeciesSelected() {
        return species;
    }

    public static Vet getVetSelected() {
        return vet;
    }
    
    public static Vet getVetById(int id_vet){
        return VetDAO.getInstance().retrieveById(id_vet);
    }
    
    public static Appointment getAppointment(int index){
        return listAppointments.get(index);
    }
    
    public static Appointment getAppointmentById(int id){
        return AppointmentDAO.getInstance().retrieveById(id);
    }
    
    public static List getSchedule(Calendar dt, int id_vet){
        List<String> schedules = new ArrayList<>();
        
        for(String s: hours){
            Appointment hour = AppointmentDAO.getInstance().retrieveByDatetimeVet(dt, s, id_vet);
            if(hour == null)
                schedules.add(s);
        }
        
        return schedules;
    }
    
    public static List getAllClient(){
        return ClientDAO.getInstance().retrieveAll();
    }
    
    public static List getAllAnimal(){
        return AnimalDAO.getInstance().retrieveAll();
    }
    
    public static List getAllSpecies(){
        return SpeciesDAO.getInstance().retrieveAll();
    }
    
    public static List<Vet> getAllVet(){
        return VetDAO.getInstance().retrieveAll();
    }
    
    public static Animal getAnimalById(int id_animal){
        return AnimalDAO.getInstance().retrieveById(id_animal);
    }
    
    public static List<Animal> getAnimalByIdClient(int id_client){
        return AnimalDAO.getInstance().retrieveByIdClient(id_client);
    }
    
    private static List getAppointmentsByIdAnimal(){
        return AppointmentDAO.getInstance().retrieveByIdAnimal(animal.getId());
    }
    
    public static Exam getExamByAppointment(int id_appointment){
        return ExamDAO.getInstance().retrieveByIdAppointment(id_appointment);
    }
    
    public static DefaultComboBoxModel getAnimalComboBoxModel(int id_client){
        List<Animal> animals = AnimalDAO.getInstance().retrieveByIdClient(id_client);
        DefaultComboBoxModel aModel = new DefaultComboBoxModel();
        
        for(Animal a: animals){
            aModel.addElement(a.getName());
            if(a.getId() == Controller.getAnimalSelected().getId())
                aModel.setSelectedItem(a.getName());
        }
        
        return aModel;
    }
    
    public static DefaultComboBoxModel getVetComboBoxModel(){
        List<Vet> vets = VetDAO.getInstance().retrieveAll();
        DefaultComboBoxModel vModel = new DefaultComboBoxModel();
        vModel.addElement("-- Selecione --");
        
        for(Vet v: vets)
            vModel.addElement(v.getName());
        
        return vModel;
    }
    
    
    
    public static void setTableModel(GenericTableModel tableModel){
        table.setModel(tableModel);
    }
    
    public static void setTableModel(JTable jtable, GenericTableModel tableModel){
        jtable.setModel(tableModel);
    }
    
    public static void setJTextFields(JTextField client, JTextField animal, JTextField species){
        txtClient = client;
        txtAnimal = animal;
        txtSpecies = species;
    }
    
    public static void setJTable(JTable jtable){
        table = jtable;
    }
    
    public static void setJList(JList jlist){
        list = jlist;
    }
    
    public static void setSelected(Object selected){
        if(selected instanceof Client){
            setClient((Client)selected);
            defineNullAnimal();
            setListAppointmentsByClient();
        }
        else if(selected instanceof Animal){
            setAnimal((Animal)selected);
            setByAnimal(animal);
            setListAppointmentsByIdAnimal();
        }
        else if(selected instanceof Species){
            if(species == null || !((Species)selected).getName().equals(species.getName())){
                defineNullAnimal();
                defineNullClient();
                setSpecies((Species)selected);
            }
            setListAppointmentsBySpecies();
        }
        else if(selected instanceof Vet){
            vet = (Vet)selected;
            defineNullAnimal();
            defineNullClient();
            setListAppointmentsByVet();
        }
        else{
            defineNullClient();
            defineNullAnimal();
            defineNullSpecies();
            defineNullVet();
            setListAllAppointments();
        }
    }
    
    private static void setClient(Client c){
        client = c;
        txtClient.setText(client.getName());
    }
    
    private static void setAnimal(Animal a){
        animal = a;
        txtAnimal.setText(animal.getName());
    }
    
    private static void setSpecies(Species s){
        species = s;
        txtSpecies.setText(species.getName());
    }
    
    private static void setByAnimal(Animal a){
        setClient(ClientDAO.getInstance().retrieveById(animal.getIdOwner()));
        setSpecies(SpeciesDAO.getInstance().retrieveById(animal.getIdSpecies()));
    }
    
    private static void setListAppointments(List listAppointment){
        listAppointments = listAppointment;
        DefaultListModel model = new DefaultListModel();
        model.addAll(listAppointment);
        
        list.setModel(model);
    }

    public static void setListAllAppointments(){
        setListAppointments(AppointmentDAO.getInstance().retrieveAll());
    }
        
    public static void setListAppointmentsByIdAnimal(){
        setListAppointments(getAppointmentsByIdAnimal());
    }
    
    public static void setListAppointmentsByClient(){
        List<Animal> animals = getAnimalByIdClient(client.getId());
        List<Appointment> appointment = new ArrayList<>();
        
        for(Animal a: animals){
            appointment.addAll(AppointmentDAO.getInstance().retrieveByIdAnimal(a.getId()));
        }
        
        setListAppointments(appointment);
    }
    
    public static void setListAppointmentsBySpecies(){
        List<Animal> animals = AnimalDAO.getInstance().retrieveByIdSpecies(species.getId());
        List<Appointment> appointment = new ArrayList<>();
        
        for(Animal a: animals){
            appointment.addAll(AppointmentDAO.getInstance().retrieveByIdAnimal(a.getId()));
        }
        
        setListAppointments(appointment);
    }
    
    public static void setListAppointmentsByVet(){
        setListAppointments(AppointmentDAO.getInstance().retrieveByIdVet(vet.getId()));
    }
    
    public static void setTextsFieldByIdAnimal(int id_animal){
        Animal ani = getAnimalById(id_animal);
        txtAnimal.setText(ani.getName());
        txtSpecies.setText(SpeciesDAO.getInstance().retrieveById(ani.getIdSpecies()).getName());
        txtClient.setText(ClientDAO.getInstance().retrieveById(ani.getIdOwner()).getName());
    }

    
    
    private static void defineNullClient(){
        client = null;
        txtClient.setText("");
    }
    
    private static void defineNullAnimal(){
        animal = null;
        txtAnimal.setText("");
        defineNullSpecies();
    }
    
    private static void defineNullSpecies(){
        species = null;
        txtSpecies.setText("");
        if(animal != null)
            defineNullAnimal();
    }
    
    private static void defineNullVet(){
        vet = null;
    }
    

        
    public static boolean createAnimal(String name, String sYear_birth, int gender, int id_species){
        if(name.isBlank() || id_species <= 0)
            return false;
        
        int year_birth;
        try{
            year_birth = Integer.parseInt(sYear_birth);
            if(year_birth < 1900 || year_birth > Calendar.getInstance().get(Calendar.YEAR))
                return false;
        }
        catch(NumberFormatException e){
            return false;
        }
        
        AnimalDAO.getInstance().create(name, year_birth, gender, client.getId(), id_species);
        return true;
    }
    
    public static String createAppointment(Calendar date, String hour, String note, int id_animal, int id_vet){
        String err = "";
        if(Calendar.getInstance().compareTo(date) == 1)
            err = "A data escolhida não pode ser antes da data atual.\n";
        
        if(hour.equals("-- Selecione --"))
            err += "Selecione um horário para a consulta.\n";
        
        if(note.isBlank())
            err += "Informe o motivo da consulta! \n";
        
        if(id_animal == 0)
            err += "Selecione um animal! \n";
        
        if(id_vet == 0)
            err += "Selecione um veterinario! \n";
        
        if(err.equals(""))
            AppointmentDAO.getInstance().create(date, hour, note, id_animal, id_vet, false);
        
        return err;
    }

    public static String createClient(String name, String address, String tel, String cep, String email){
        String err = "";
        
        if(name.isBlank())
            err = "Insira um nome para cadastrar um cliente! \n";
        
        if(!tel.isBlank() && tel.replaceAll("[()-]","").length() < 10)
            err = "Telefone invalido! \n";
        
        if(!email.isBlank() && (!email.contains("@") || email.indexOf(".", email.indexOf("@")) == -1))
            err = "Email invalido! \n";
        
        if(err.equals(""))
            ClientDAO.getInstance().create(name, address, tel, cep, email);
        
        return err;
    }
    
    public static String createExam(String descr, int id_appointment){
        String err = "";
        if(descr.isBlank())
            err = "Informe qual o exame!\n";
        
        if(err.equals(""))
            ExamDAO.getInstance().create(descr, id_appointment);
        
        return err;
    }
    
    public static String createSpecies(String name){
        String err = "";
        if(name.isBlank())
            err = "Informe o nome da especie para finalizar!\n";
        
        if(err.equals(""))
            SpeciesDAO.getInstance().create(name);
        
        return err;
    }
    
    public static String createVet(String name, String email, String tel){
        String err = "";
        
        if(name.isBlank())
            err = "Informe o nome para finalizar o cadastro! \n";
        
        if(!tel.isBlank() && tel.replaceAll("[()-]","").length() < 10)
            err = "Telefone invalido! \n";
        
        if(!email.isBlank() && (!email.contains("@") || email.indexOf(".", email.indexOf("@")) == -1))
            err = "Email invalido! \n";
        
        if(err.equals(""))
            VetDAO.getInstance().create(name, email, tel);
        
        return err;
    }
    
    
    
    public static String updateClient(String name, String address, String tel, String cep, String email){
        String err = "";
        
        client.setName(name);
        client.setAddress(address);
        if(!tel.isBlank() && !client.setTel(tel))
            err = "Erro ao atualizar o telefone para contato: formato inconsistente\n";
        
        client.setCep(cep);
        if(!email.isBlank() && !client.setEmail(email))
            err = "Erro ao atualizar o email: formato inconsistente";
        
        ClientDAO.getInstance().update(client);
        return err;
    }
    
    public static String updateAnimal(String name, String sYear_birth, int gender, int id_species){
        String err = "";
        
        animal.setName(name);
        
        int year_birth;
        try{
            year_birth = Integer.parseInt(sYear_birth);
            if(!animal.setYearBirth(year_birth))
                err = "Erro ao atualizar o ano de nascimento: fora dos limites aceitos.";
            else
                animal.setYearBirth(year_birth);
        }
        catch(NumberFormatException e){
            err = "Erro ao atualizar o ano de nascimento: formato incorreto.";
        }
            
        animal.setGender(gender-1);
        animal.setIdSpecies(id_species);
        
        AnimalDAO.getInstance().update(animal);
        return err;
    }
    
    public static String updateExam(String descr, int id_appointment){
        String err = "";
        Exam exam = ExamDAO.getInstance().retrieveByIdAppointment(id_appointment);
                
        if(!exam.setDescr(descr))
            err = "Informe o exame que deseja solicitar!\n ";
        
        exam.setIdAppointment(id_appointment);
        
        if(err.equals(""))
            ExamDAO.getInstance().update(exam);
        
        return err;
    }
    
    public static String updateSpecies(String name){
        String err = "";
        if(!species.setName(name))
            err = "Informe o nome da especie para finalizar!";
        
        if(err.equals(""))
            SpeciesDAO.getInstance().update(species);
        
        return err;
    }
    
    public static String updateVet(String name, String email, String tel){
        String err = "";
        
        vet.setName(name);
        if(!email.isBlank() && !vet.setEmail(email))
            err = "Erro ao atualizar o email: formato incorreto\n";
        
        if(!tel.isBlank() && !vet.setTel(tel))
            err += "Erro ao atualizar o telefone para contato: formato incorreto";
        
        VetDAO.getInstance().update(vet);
        return err;
    }

    
    
    public static void deleteClient(){      
        List<Animal> animais = AnimalDAO.getInstance().retrieveByIdClient(client.getId());
        for(Animal a: animais){
            animal = a;
            deleteAnimal();
        }
        
        ClientDAO.getInstance().delete(client.getId());
        defineNullClient();
    }
    
    public static void deleteAnimal(){
        AnimalDAO.getInstance().delete(animal.getId());
        defineNullAnimal();
    }
    
    public static void deleteAppointment(int id_appointment){
        AppointmentDAO.getInstance().delete(id_appointment);
    }
    
    public static void deleteSpecies(){
        List<Animal> animais = AnimalDAO.getInstance().retrieveByIdSpecies(species.getId());
        for(Animal a: animais){
            animal = a;
            deleteAnimal();
        }
        
        SpeciesDAO.getInstance().delete(species.getId());
        defineNullSpecies();
    }
    
    public static void deleteVet(){
        VetDAO.getInstance().delete(vet.getId());
        defineNullVet();
    }

    public static void deleteExam(int id_exam){
        ExamDAO.getInstance().delete(id_exam);
    }
    
    
    
    public static void searchTable(String name, int selected){
        GenericTableModel tableModel;
        
        switch(selected){
            case 1: //client
                tableModel = new ClientTableModel(ClientDAO.getInstance().retrieveBySimilarName(name));
                break;
            case 2: //animal
                if(client != null)
                    tableModel = new AnimalTableModel(AnimalDAO.getInstance().retrieveBySimilarName(name, client.getId()));
                else    
                    tableModel = new AnimalTableModel(AnimalDAO.getInstance().retrieveBySimilarName(name));
                break;
            case 3: //species
                tableModel = new SpeciesTableModel(SpeciesDAO.getInstance().retrieveBySimilarName(name));
                break;
            case 4: //vet
                tableModel = new VetTableModel(VetDAO.getInstance().retrieveBySimilarName(name));
                break;
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds.");
        }
        
        setTableModel(tableModel);
    }
    
    public static void finishAppointment(int id_appointment, String note){
        Appointment a = getAppointmentById(id_appointment);
        a.setFinished(true);
        a.setNote(note);
        AppointmentDAO.getInstance().update(a);
    }

}
