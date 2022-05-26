/* Autor: Maja Terpi³owska, 259176
 * Grupa: PN/TN 15:15
 * Data utworzenia: grudzien 2021
 * Lab3 
 * Plik: ViewGroupOfMaps.java
 */

package gui;

import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import data.GroupOfMaps;
import data.Map;


public class ViewGroupOfMaps extends JScrollPane {

	private static final long serialVersionUID = 1L;
	
	private GroupOfMaps group;
	private JTable table;
	private DefaultTableModel tableModel;
	
	public ViewGroupOfMaps(GroupOfMaps group, int width, int height) {
		
	    this.group = group;
	    setPreferredSize(new Dimension(width, height));
	    setBorder(BorderFactory.createTitledBorder("Lista map"));
	    String[] tableHeader = { "Nazwa", "Skala", "Rodzaj", "Szerokoœæ", "Wysokoœæ", "Autor" };
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
	    for (Map m : this.group) {
	      String[] row = { m.getNazwa(), String.valueOf(m.getSkala()), m.getRodzaj().toString(), String.valueOf(m.getWymiarX()), String.valueOf(m.getWymiarY()), m.getAutor() };
	      this.tableModel.addRow((Object[])row);
	    } 
	  }
	  
	  int getSelectedIndex() {
	    int index = this.table.getSelectedRow();
	    if (index < 0)
	      JOptionPane.showMessageDialog(this, "¯adna mapa nie jest zaznaczona.", "B³¹d", 0); 
	    return index;
	  }
}
