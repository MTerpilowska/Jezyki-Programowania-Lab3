/* Autor: Maja Terpi³owska, 259176
 * Grupa: PN/TN 15:15
 * Data utworzenia: grudzien 2021
 * Lab3 
 * Plik: GroupOfMapsWindowDialog.java
 */

package gui;

import java.awt.Component;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import data.MapException;
import data.GroupOfMaps;
import data.GroupType;
import data.Map;
import Lab2gui.MapWindowDialog;

public class GroupOfMapsWindowDialog extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	  
	private static final String GREETING_MESSAGE = 
			"Lab3 - program do edytowania grup map \n" + 
			        "Autor: Maja Terpi³owska, 259176\n" +
					"Grupa: PN/TN 15:15\n" +
					"Data:  grudzien 2021 r\n";
	  
	  private GroupOfMaps currentGroup;
	  
	  public static void main(String[] args) {
		    try {
		      GroupOfMaps currentGroup = new GroupOfMaps(GroupType.VECTOR, "Grupa do testowania");
		      currentGroup.add(new Map("Kraków", "J. Kowalski"));
		      currentGroup.add(new Map("Babia Góra", "K. Majewski"));
		    } catch (MapException e) {
		      JOptionPane.showMessageDialog(null, e.getMessage(), "B³¹d", 0);
		    } 
	  }
	  
	  private static String enterGroupName(Window parent) {
		    return JOptionPane.showInputDialog(parent, "Podaj nazwê grupy: ");
		  }
		  
	  private static GroupType chooseGroupType(Window parent, GroupType current_type) {
		    GroupType[] arrayOfGroupType = GroupType.values();
		    GroupType type = (GroupType)JOptionPane.showInputDialog(
		        parent, 
		        "Wybierz typ kolekcji (Lista, Zbiór) \ni sposób implementacji:", 
		        "Zmieñ typ kolekcji", 
		        3, 
		        null, 
		        (Object[])arrayOfGroupType, 
		        current_type);
		    return type;
	  }
	  
	  public static GroupOfMaps createNewGroupOfMaps(Window parent) {
		    GroupOfMaps new_group;
		    String name = enterGroupName(parent);
		    if (name == null || name.equals(""))
		      return null; 
		    GroupType type = chooseGroupType(parent, (GroupType)null);
		    if (type == null)
		      return null; 
		    try {
		      new_group = new GroupOfMaps(type, name);
		    } catch (MapException e) {
		      JOptionPane.showMessageDialog(null, e.getMessage(), "B³¹d", 0);
		      return null;
		    } 
		    GroupOfMapsWindowDialog dialog = new GroupOfMapsWindowDialog(parent, new_group);
		    return dialog.currentGroup;
		  }
	  
	  public static void changeGroupOfMaps(Window parent, GroupOfMaps group) {}
	  
	  JMenuBar menuBar = new JMenuBar();
	  JMenu menuMap = new JMenu("Lista map");
	  JMenu menuSort = new JMenu("Sortowanie");
	  JMenu menuProperty = new JMenu("W³aœciwoœci");
			  
	  JMenuItem menuNewMap = new JMenuItem("Dodaj now¹ mapê");
	  JMenuItem menuEditMap = new JMenuItem("Edytuj mapê");
	  JMenuItem menuDeleteMap = new JMenuItem("Usuñ mapê");
	  JMenuItem menuLoadMap = new JMenuItem("Wczytaj mapê z pliku");
	  JMenuItem menuSaveMap = new JMenuItem("Zapisz mapê do pliku");
	  JMenuItem menuSortNazwa = new JMenuItem("Sortuj alfabetycznie");
	  JMenuItem menuSortSkala = new JMenuItem("Sortuj wg. skali mapy");
	  JMenuItem menuSortRodzaj = new JMenuItem("Sortuj wg. rodzaju mapy");
	  JMenuItem menuChangeName = new JMenuItem("Zmieñ nazwê kolekcji");
	  JMenuItem menuChangeType = new JMenuItem("Zmieñ typ kolekcji");
	  JMenuItem menuAuthor = new JMenuItem("O autorze");
	
	  Font font = new Font("MonoSpaced", 1, 12);
	  
	  JLabel labelGroupName = new JLabel("        Nazwa grupy: ");
	  JLabel labelGroupType = new JLabel("    Rodzaj kolekcji: ");
	  
	  JTextField fieldGroupName = new JTextField(15);
	  JTextField fieldGroupType = new JTextField(15);
	  
	  JButton buttonNewMap = new JButton("Dodaj now¹ mapê");
	  JButton buttonEditMap = new JButton("Edytuj mapê");
	  JButton buttonDeleteMap = new JButton("Usuñ mapê");
	  JButton buttonLoadMap = new JButton("Wczytaj mapê z pliku");
	  JButton buttonSaveMap = new JButton("Zapisz mapê do pliku");
	  
	  ViewGroupOfMaps viewList;
	  
	  public GroupOfMapsWindowDialog(Window parent, GroupOfMaps group) {
		  
		    super(parent, Dialog.ModalityType.DOCUMENT_MODAL);
		    setTitle("Modyfikowanie grupy map");
		    setDefaultCloseOperation(2);
		    setSize(450, 450);
		    setResizable(false);
		    
		    if (parent != null) {
		      Point location = parent.getLocation();
		      location.translate(100, 100);
		      setLocation(location);
		    } else {
		      setLocationRelativeTo((Component)null);
		    } 
		    
		    this.currentGroup = group;
		    
		    setJMenuBar(this.menuBar);
		    
		    this.menuBar.add(this.menuMap);
		    this.menuBar.add(this.menuSort);
		    this.menuBar.add(this.menuProperty);
		    this.menuBar.add(this.menuAuthor);
		    this.menuMap.add(this.menuNewMap);
		    this.menuMap.add(this.menuEditMap);
		    this.menuMap.add(this.menuDeleteMap);
		    this.menuMap.addSeparator();
		    this.menuMap.add(this.menuLoadMap);
		    this.menuMap.add(this.menuSaveMap);
		    this.menuSort.add(this.menuSortNazwa);
		    this.menuSort.add(this.menuSortSkala);
		    this.menuSort.add(this.menuSortRodzaj);
		    this.menuProperty.add(this.menuChangeName);
		    this.menuProperty.add(this.menuChangeType);
		    this.menuNewMap.addActionListener(this);
		    this.menuEditMap.addActionListener(this);
		    this.menuDeleteMap.addActionListener(this);
		    this.menuLoadMap.addActionListener(this);
		    this.menuSaveMap.addActionListener(this);
		    this.menuSortNazwa.addActionListener(this);
		    this.menuSortSkala.addActionListener(this);
		    this.menuSortRodzaj.addActionListener(this);
		    this.menuChangeName.addActionListener(this);
		    this.menuChangeType.addActionListener(this);
		    this.menuAuthor.addActionListener(this);
		    
		    this.labelGroupName.setFont(this.font);
		    this.labelGroupType.setFont(this.font);
		    
		    this.fieldGroupName.setEditable(false);
		    this.fieldGroupType.setEditable(false);
		    
		    this.viewList = new ViewGroupOfMaps(this.currentGroup, 400, 250);
		    this.viewList.refreshView();
		    
		    this.buttonNewMap.addActionListener(this);
		    this.buttonEditMap.addActionListener(this);
		    this.buttonDeleteMap.addActionListener(this);
		    this.buttonLoadMap.addActionListener(this);
		    this.buttonSaveMap.addActionListener(this);
		    
		    JPanel panel = new JPanel();
		    
		    panel.add(this.labelGroupName);
		    panel.add(this.fieldGroupName);
		    panel.add(this.labelGroupType);
		    panel.add(this.fieldGroupType);
		    panel.add(this.viewList);
		    panel.add(this.buttonNewMap);
		    panel.add(this.buttonEditMap);
		    panel.add(this.buttonDeleteMap);
		    panel.add(this.buttonLoadMap);
		    panel.add(this.buttonSaveMap);
		    
		    this.fieldGroupName.setText(this.currentGroup.getName());
		    this.fieldGroupType.setText(this.currentGroup.getType().toString());
		    
		    setContentPane(panel);
		    
		    setVisible(true);
	  }
	  
	  public void actionPerformed(ActionEvent event) {
		    Object source = event.getSource();
		    try {
		    
		      if (source == this.menuNewMap || source == this.buttonNewMap) {
		        Map newMap = MapWindowDialog.createNewMap(this);
		        if (newMap != null)
		          this.currentGroup.add(newMap); 
		      }
		      
		      if (source == this.menuEditMap || source == this.buttonEditMap) {
		        int index = this.viewList.getSelectedIndex();
		        if (index >= 0) {
		          Iterator<Map> iterator = this.currentGroup.iterator();
		          while (index-- > 0)
		            iterator.next(); 
		          MapWindowDialog.changeMapData(this, iterator.next());
		        } 
		      } 
		      
		      if (source == this.menuDeleteMap || source == this.buttonDeleteMap) {
		        int index = this.viewList.getSelectedIndex();
		        if (index >= 0) {
		          Iterator<Map> iterator = this.currentGroup.iterator();
		          while (index-- >= 0)
		            iterator.next(); 
		          iterator.remove();
		        } 
		      } 
		      
		      if (source == this.menuLoadMap || source == this.buttonLoadMap) {
		          JFileChooser chooser = new JFileChooser(".");
		          int returnVal = chooser.showOpenDialog(this);
		          if (returnVal == 0) {
		            Map mapa = Map.readFromFile(chooser.getSelectedFile().getName());
		            this.currentGroup.add(mapa);
		          } 
		        } 
		      
		        if (source == this.menuSaveMap || source == this.buttonSaveMap) {
		          int index = this.viewList.getSelectedIndex();
		          if (index >= 0) {
		            Iterator<Map> iterator = this.currentGroup.iterator();
		            while (index-- > 0)
		              iterator.next(); 
		            Map mapa = iterator.next();
		            JFileChooser chooser = new JFileChooser(".");
		            int returnVal = chooser.showSaveDialog(this);
		            if (returnVal == 0)
		              Map.printToFile(chooser.getSelectedFile().getName(), mapa); 
		          } 
		        } 
		        
		        if (source == this.menuSortNazwa)
		            this.currentGroup.sortNazwa(); 
		          if (source == this.menuSortSkala)
		            this.currentGroup.sortSkala(); 
		          if (source == this.menuSortRodzaj)
		            this.currentGroup.sortRodzaj(); 
		          if (source == this.menuChangeName) {
		            String newName = enterGroupName(this);
		            if (newName == null)
		              return; 
		            this.currentGroup.setName(newName);
		            this.fieldGroupName.setText(newName);
		          } 
		          
		          if (source == this.menuChangeType) {
		              GroupType type = chooseGroupType(this, this.currentGroup.getType());
		              if (type == null)
		                return; 
		              this.currentGroup.setType(type);
		              this.fieldGroupType.setText(type.toString());
		            } 
		            if (source == this.menuAuthor)
		              JOptionPane.showMessageDialog(this, "Lab3 - program do edytowania grup map \n" + 
		  			        "Autor: Maja Terpi³owska, 259176\n" +
							"Grupa: PN/TN 15:15\n" +
							"Data:  grudzien 2021 r\n"); 
		          } catch (MapException e) {
		            JOptionPane.showMessageDialog(this, e.getMessage(), "B³¹d", 0);
		          } 
		          this.viewList.refreshView();
	  }
	  
}
