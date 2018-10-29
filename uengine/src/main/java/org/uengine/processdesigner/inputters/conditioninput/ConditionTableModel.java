package org.uengine.processdesigner.inputters.conditioninput;

import java.util.Vector;

import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;

import org.uengine.kernel.Evaluate;

public class ConditionTableModel extends AbstractTableModel {
    Vector columnNames; 
    Vector conditionRows;

    public ConditionTableModel(){
        columnNames = new Vector();
        conditionRows=new Vector();
    }
    
    // callback methods for rendering table /////////////////////////////////////////////

    public int getColumnCount() {
        return columnNames.size();
    }
    
    public int getRowCount() {
        return conditionRows.size();
    }

    public String getColumnName(int col) {
        return (String)columnNames.get(col);
    } 

    public Object getValueAt(int row, int col) {
        ConditionRow conditionRow = (ConditionRow)conditionRows.elementAt(row);
        Evaluate evaluatation = (Evaluate)conditionRow.get(getColumnName(col));
        
        return evaluatation;
    }
   
    // callback methods for editing /////////////////////////////////////////////

    public void setValueAt(Object value, int row, int col) {
        try{
            ((ConditionRow)conditionRows.elementAt(row)).put(getColumnName(col), value);
        }catch(Exception e){
            System.out.println("ConditionTableModel.setValueAt: ���� ������ �� ����ϴ�");
        }
    }
    
    public boolean isCellEditable(int row, int col) {
        return true; 
    }

    public Class getColumnClass(int columnIndex) {
        return Evaluate.class;
    }       
    
    // add-on methods ////////////////////////////////////////////

    public void addColumnName(int index, String colName){
        columnNames.add(index, colName);

        fireTableStructureChanged();
        fireTableChanged(new TableModelEvent(this));
    }

    public void removeColumnName(String colName){
        columnNames.remove(colName);

        fireTableStructureChanged();
        fireTableChanged(new TableModelEvent(this));
    }

    public void removeColumnName(int i){
        columnNames.remove(i);

        fireTableStructureChanged();
        fireTableChanged(new TableModelEvent(this));
    }
    
    public void addConditionRow(ConditionRow cr){
        fireTableStructureChanged();
        fireTableChanged(new TableModelEvent(this));
            
        conditionRows.add(cr);
        fireTableRowsInserted(conditionRows.size()-1, conditionRows.size()-1);  
    }
    
    public void clearAll(){
        int iRowCount = conditionRows.size();
        conditionRows.clear();
        fireTableRowsDeleted(0, iRowCount);
    }
    
    public Vector getAllConditionRows(){
        return conditionRows;
    }
    
    public ConditionRow getConditionRowAt(int index){
        return (ConditionRow)conditionRows.elementAt(index);
    }
    
    public void removeConditionRowAt(int i){
        if(i<conditionRows.size()){         
            conditionRows.removeElementAt(i);
            
            fireTableRowsDeleted(i, i);
        }else{
            System.out.println("can't remove row : Removing index is out of range");
        }
    }
    
    public void insertConditionRowAt(ConditionRow rec, int i){
        if(i<conditionRows.size()){
            conditionRows.insertElementAt(rec, i);
            
            fireTableRowsInserted(i, i);
        }else
        if(i==conditionRows.size()){
            addConditionRow(rec);
        }else
        {               
            System.out.println("can't insert row : inserting index is out of range");
        }
    }
    
    public void updateRowAt(ConditionRow rec, int i){
        if(i<conditionRows.size()){         
            conditionRows.setElementAt(rec, i);
            
            fireTableRowsUpdated(i, i);
        }else{
            System.out.println("can't update row : index is out of range");
        }
    }
   
}

