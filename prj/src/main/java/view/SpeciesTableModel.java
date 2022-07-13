package view;

import java.util.List;
import model.Species;

public class SpeciesTableModel extends GenericTableModel{

    public SpeciesTableModel(List vDados){
        super(vDados, new String[]{"Nome"});
    }
    
    @Override
    public Class<?> getColumnClass(int columnIndex){
        switch(columnIndex){
            case 0:
                return String.class;
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds.");
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Species species = (Species) vDados.get(rowIndex);
        
        switch(columnIndex){
            case 0:
                return species.getName();
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds."); 
        }
    }
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex){
        return false;
    }
    
}
