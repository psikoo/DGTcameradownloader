package com.dgtcd.photo;

import com.dgtcd.utility.prompts.OpenImage;
import com.dgtcd.utility.FileData;

import java.awt.Component;
import java.awt.event.*;
import java.io.File;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;

public class PhotoTable {
    public static JTable createTable(String cameraID) { //generate the array to populate scTblConnected
        //get the number of files in the directory
        int numberOfFiles = 0;
        try { numberOfFiles = new File("C:\\ProgramData\\dgtcd\\assets\\photos\\"+cameraID).list().length; } catch(NullPointerException ignore) {}
        
        //initialize array and table
        Object[][] emptyData = new Object[numberOfFiles][3];
        String[] columnNames = { "File", "Date", "Open" };
        JTable jtable = new JTable(emptyData, columnNames);
        jtable.setBounds(0, 0, 910, 20);
        jtable.getTableHeader().setReorderingAllowed(false);
        //Check for empty folder
        if(numberOfFiles == 0){
            emptyData = new Object[0][0];
        } else {
            //set button rendered for "Open" column
            jtable.getColumn("Open").setCellRenderer(new ButtonRenderer());
            jtable.getColumn("Open").setCellEditor(new ButtonEditor(new JCheckBox()));
            //populate array
            for(int i=0; (i<jtable.getRowCount())&&(i<numberOfFiles); i++) {
                for(int j=0; j<jtable.getColumnCount(); j++) {
                    if(j==0) { //"File" column
                        jtable.setValueAt(i+".jpg",i,0);
                    } else if(j==1) { //"Date" column
                        jtable.setValueAt(FileData.getCreationTime("C:\\ProgramData\\dgtcd\\assets\\photos\\"+cameraID+"\\"+i+".jpg"),i,1);
                    } else{ //"Open" column
                        jtable.setValueAt("C:\\ProgramData\\dgtcd\\assets\\photos\\"+cameraID+"\\"+i+".jpg",i,2);
                    }
                }
            }
        }
    return jtable;
  }
}

class ButtonRenderer extends JButton implements TableCellRenderer {

    public ButtonRenderer() {
      setOpaque(true);
    }
  
    public Component getTableCellRendererComponent(JTable table, Object value,
        boolean isSelected, boolean hasFocus, int row, int column) {
        if (isSelected) {
          setForeground(table.getSelectionForeground());
          setBackground(table.getSelectionBackground());
        } else {
          setForeground(table.getForeground());
          setBackground(UIManager.getColor("Button.background"));
        }
        setText((value == null) ? "" : value.toString());
        return this;
    }
}

class ButtonEditor extends DefaultCellEditor {
    protected JButton button;
    private String label;
    private boolean isPushed;
  
    public ButtonEditor(JCheckBox checkBox) {
        super(checkBox);
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              fireEditingStopped();
            }
        });
    }
  
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        if (isSelected) {
            button.setForeground(table.getSelectionForeground());
            button.setBackground(table.getSelectionBackground());
        } else {
            button.setForeground(table.getForeground());
            button.setBackground(table.getBackground());
        }
        label = (value == null) ? "" : value.toString();
        button.setText(label);
        isPushed = true;
        return button;
    }
  
    public Object getCellEditorValue() {
        if (isPushed) {
            new OpenImage(label);
        }
        isPushed = false;
        return new String(label);
    }
  
    public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
    }
  
    protected void fireEditingStopped() {
        super.fireEditingStopped();
    }
}
