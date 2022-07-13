package view;

import java.util.List;
import model.Exam;

public class ExamTableModel extends GenericTableModel{

    public ExamTableModel(List vDados){
        super(vDados, new String[]{"ID", "Descrição"});
    }
    
    @Override
    public Class<?> getColumnClass(int columnIndex){
        switch(columnIndex){
            case 0:
                return Integer.class;
            case 1:
                return String.class;
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds.");
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Exam exam = (Exam) vDados.get(rowIndex);
        
        switch(columnIndex){
            case 0:
                return exam.getId();
            case 1:
                return exam.getDescr();
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds."); 
        }
    }
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex){
        return true;
    }
    
}
