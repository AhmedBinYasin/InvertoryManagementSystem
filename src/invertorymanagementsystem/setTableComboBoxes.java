/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package invertorymanagementsystem;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
public class setTableComboBoxes{

public JComboBox setComboBoxes(JTable table1,int colNum,ArrayList<String> comboList){
    JComboBox comboBoxj = null;
        try {
            TableColumn column = table1.getColumnModel().getColumn(colNum);
            ComboBoxPanel.setComboList(comboList);
            ComboBoxCellRenderer cbcr =new ComboBoxCellRenderer();
             comboBoxj = cbcr.getComboBox();
            
            column.setCellRenderer(cbcr);
            column.setCellEditor(new ComboBoxCellEditor());
            
        } catch (SQLException ex) {
            Logger.getLogger(productMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
        return comboBoxj;
    }
}

class ComboBoxPanel extends JPanel {
   static ArrayList<String> list;
  protected JComboBox<String> comboBox = new JComboBox<String>() {
    @Override public Dimension getPreferredSize() {
      Dimension d = super.getPreferredSize();
      return new Dimension(100, d.height);
    }
  };
  public static void setComboList(ArrayList<String> list){
      ComboBoxPanel.list = list;
  }
  public ComboBoxPanel() throws SQLException {
    super();
    setOpaque(true);
    comboBox.setEditable(true);
    for(String s :list){
        comboBox.addItem(s);
    }
    add(comboBox);
  }
  public JComboBox getComboBox(){
      return comboBox;
  }
}
class ComboBoxCellRenderer extends ComboBoxPanel
                           implements TableCellRenderer {
  public ComboBoxCellRenderer() throws SQLException {
    super();
    setName("Table.cellRenderer");
  }
  @Override public Component getTableCellRendererComponent(
      JTable table, Object value, boolean isSelected,
      boolean hasFocus, int row, int column) {
    setBackground(isSelected?table.getSelectionBackground()
                            :table.getBackground());
    if(value!=null) {
      comboBox.setSelectedItem(value);
    }
    return this;
  }
}
class ComboBoxCellEditor extends ComboBoxPanel
                         implements TableCellEditor {
  public ComboBoxCellEditor() throws SQLException {
    super();
    comboBox.addActionListener(new ActionListener() {
      @Override public void actionPerformed(ActionEvent e) {
        fireEditingStopped();
      }
    });
    addMouseListener(new MouseAdapter() {
      @Override public void mousePressed(MouseEvent e) {
        fireEditingStopped();
      }
    });
  }
  @Override public Component getTableCellEditorComponent(
      JTable table, Object value, boolean isSelected, int row, int column) {
    this.setBackground(table.getSelectionBackground());
    comboBox.setSelectedItem(value);
    return this;
  }

  //Copid from DefaultCellEditor.EditorDelegate
  @Override public Object getCellEditorValue() {
    return comboBox.getSelectedItem();
  }
  @Override public boolean shouldSelectCell(EventObject anEvent) {
    if(anEvent instanceof MouseEvent) {
      MouseEvent e = (MouseEvent)anEvent;
      return e.getID() != MouseEvent.MOUSE_DRAGGED;
    }
    return true;
  }
  @Override public boolean stopCellEditing() {
    if(comboBox.isEditable()) {
      comboBox.actionPerformed(new ActionEvent(this, 0, ""));
    }
    fireEditingStopped();
    return true;
  }

  //Copid from AbstractCellEditor
  //protected EventListenerList listenerList = new EventListenerList();
  transient protected ChangeEvent changeEvent = null;

  @Override public boolean isCellEditable(EventObject e) {
    return true;
  }
  @Override public void  cancelCellEditing() {
    fireEditingCanceled();
  }
  @Override public void addCellEditorListener(CellEditorListener l) {
    listenerList.add(CellEditorListener.class, l);
  }
  @Override public void removeCellEditorListener(CellEditorListener l) {
    listenerList.remove(CellEditorListener.class, l);
  }
  public CellEditorListener[] getCellEditorListeners() {
    return listenerList.getListeners(CellEditorListener.class);
  }
  protected void fireEditingStopped() {
    // Guaranteed to return a non-null array
    Object[] listeners = listenerList.getListenerList();
    // Process the listeners last to first, notifying
    // those that are interested in this event
    for(int i = listeners.length-2; i>=0; i-=2) {
      if(listeners[i]==CellEditorListener.class) {
        // Lazily create the event:
        if(changeEvent == null) changeEvent = new ChangeEvent(this);
        ((CellEditorListener)listeners[i+1]).editingStopped(changeEvent);
      }
    }
  }
  protected void fireEditingCanceled() {
    // Guaranteed to return a non-null array
    Object[] listeners = listenerList.getListenerList();
    // Process the listeners last to first, notifying
    // those that are interested in this event
    for(int i = listeners.length-2; i>=0; i-=2) {
      if(listeners[i]==CellEditorListener.class) {
        // Lazily create the event:
        if(changeEvent == null) changeEvent = new ChangeEvent(this);
        ((CellEditorListener)listeners[i+1]).editingCanceled(changeEvent);
      }
    }
  }
}