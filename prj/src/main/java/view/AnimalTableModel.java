package view;

import java.util.List;
import model.Animal;
import model.SpeciesDAO;

public class AnimalTableModel extends GenericTableModel{

    public AnimalTableModel(List vDados){
        super(vDados, new String[]{"Nome", "Ano de nascimento", "Gênero", "Espécie"});
    }

    @Override
    public Class<?> getColumnClass(int columnIndex){
        switch(columnIndex){
            case 0:
                return String.class;
            case 1:
                return Integer.class;
            case 2:
                return String.class;
            case 3:
                return String.class;
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds.");
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Animal animal = (Animal) vDados.get(rowIndex);
        
        switch(columnIndex){
            case 0:
                return animal.getName();
            case 1:
                return animal.getYearBirth();
            case 2:
                return (animal.getGender() == 0 ? "Femea" : "Macho");
            case 3:
                return SpeciesDAO.getInstance().retrieveById(animal.getIdSpecies()).getName();
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds."); 
        }
    }
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex){
        return false;
    }
    
}
