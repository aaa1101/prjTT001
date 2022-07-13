package view;

import java.util.List;
import model.Vet;

public class VetTableModel extends GenericTableModel{

    public VetTableModel(List vDados){
        super(vDados, new String[]{"Nome", "Email", "Telefone"});
    }
    
    @Override
    public Class<?> getColumnClass(int columnIndex){
        switch(columnIndex){
            case 0:
                return String.class;
            case 1:
                return String.class;
            case 2:
                return String.class;
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds.");
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Vet vet = (Vet) vDados.get(rowIndex);
        
        switch(columnIndex){
            case 0:
                return vet.getName();
            case 1:
                return vet.getEmail();
            case 2:
                return vet.getTel();
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds."); 
        }
    }
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex){
        return false;
    }
    
}
