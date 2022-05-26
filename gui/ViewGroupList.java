/* Autor: Maja Terpi³owska, 259176
 * Grupa: PN/TN 15:15
 * Data utworzenia: grudzien 2021
 * Lab3 
 * Plik: ViewGroupList.java
 */

package gui;

import java.awt.Dimension;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import data.GroupOfMaps;

public class ViewGroupList extends JScrollPane  {
	
	 private static final long serialVersionUID = 1L;
	 
	 private List<GroupOfMaps> list;
	  
	 private JTable table;
	 private DefaultTableModel tableModel;
	  
	 public ViewGroupList(List<GroupOfMaps> list, int width, int height) {
		 
	    this.list = list;
	    setPreferredSize(new Dimension(width, height));
	    setBorder(BorderFactory.createTitledBorder("Lista grup:"));
	    String[] tableHeader = { "Nazwa grupy", "Typ kolekcji", "Liczba map"};
	    this.tableModel = new DefaultTableModel((Object[])tableHeader, 0);
	    this.table = new JTable(this.tableModel) {
	        private static final long serialVersionUID = 1L;
	        public boolean isCellEditable(int rowIndex, int colIndex) {
	          return false;
	        }
	      };
	      
	    this.table.setSelectionMode(0);
	    this.table.setRowSelectionAllowed(true);
	    setViewportView(this.table);
	  }
	  
	  void refreshView() {
	    this.tableModel.setRowCount(0);
	    for (GroupOfMaps group : this.list) {
	      if (group != null) {
	        String[] row = { group.getName(), group.getType().toString(), String.valueOf(group.size()) };
	        this.tableModel.addRow((Object[])row);
	      } 
	    } 
	  }
	  
	  int getSelectedIndex() {
	    int index = this.table.getSelectedRow();
	    if (index < 0)
	      JOptionPane.showMessageDialog(this, "¯adna grupa nie jest zaznaczona.", "B³¹d", 0); 
	    return index;
	  }
}
