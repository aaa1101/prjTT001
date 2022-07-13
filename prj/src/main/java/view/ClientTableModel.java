package view;

import java.util.List;
import model.Client;

public class ClientTableModel extends GenericTableModel{

    public ClientTableModel(List vDados){
        super(vDados, new String[]{"Nome", "Endereço", "Telefone", "CEP", "Email"});
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
            case 3:
                return String.class;
            case 4:
                return String.class;
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds.");
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Client client = (Client) vDados.get(rowIndex);
        
        switch(columnIndex){
            case 0:
                return client.getName();
            case 1:
                return client.getAddress();
            case 2:
                return client.getTel();
            case 3:
                return client.getCep();
            case 4:
                return client.getEmail();
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds."); 
        }
    }
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex){
        return true;
    }
    
}
