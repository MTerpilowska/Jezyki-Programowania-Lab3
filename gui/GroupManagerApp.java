/* Autor: Maja Terpi�owska, 259176
 * Grupa: PN/TN 15:15
 * Data utworzenia: grudzien 2021
 * Lab3 
 * Plik: GroupManagerApp.java
 */

package gui;

import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import data.MapException;
import data.GroupOfMaps;

public class GroupManagerApp extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;

	private static final String GREETING_MESSAGE = 
			"Lab3 - program do zarz�dzania grupami map \n" + 
			        "Autor: Maja Terpi�owska, 259176\n" +
					"Grupa: PN/TN 15:15\n" +
					"Data:  grudzien 2021 r\n";
	
	private static final String ALL_GROUPS_FILE = "LISTA_GRUP.BIN"; 
	
	public static void main(String[] args) {	
		new GroupManagerApp();
	}
	
	WindowAdapter windowListener = new WindowAdapter() {
	
		@Override
		public void windowClosed(WindowEvent e) {
			JOptionPane.showMessageDialog(null, "Program zako�czy� dzia�anie!");
			
		}

		@Override
		public void windowClosing(WindowEvent e) {
			windowClosed(e);
		}
	};
	
	private List<GroupOfMaps> currentList = new ArrayList<GroupOfMaps>();
	
	JMenuBar menuBar        = new JMenuBar();
	JMenu menuGroups        = new JMenu("Grupy");
	JMenu menuSpecialGroups = new JMenu("Grupy specjalne");
	JMenu menuAbout         = new JMenu("O programie");
	
	JMenuItem menuNewGroup           = new JMenuItem("Utw�rz grup�");
	JMenuItem menuEditGroup          = new JMenuItem("Edytuj grup�");
	JMenuItem menuDeleteGroup        = new JMenuItem("Usu� grup�");
	JMenuItem menuLoadGroup          = new JMenuItem("za�aduj grup� z pliku");
	JMenuItem menuSaveGroup          = new JMenuItem("Zapisz grup� do pliku");
	
	JMenuItem menuGroupUnion         = new JMenuItem("Po��czenie grup");
	JMenuItem menuGroupIntersection  = new JMenuItem("Cz�� wsp�lna grup");
	JMenuItem menuGroupDifference    = new JMenuItem("R�nica grup");
	JMenuItem menuGroupSymmetricDiff = new JMenuItem("R�nica symetryczna grup");

	JMenuItem menuAuthor             = new JMenuItem("Autor");
	
	JButton buttonNewGroup = new JButton("Utw�rz");
	JButton buttonEditGroup = new JButton("Edytuj");
	JButton buttonDeleteGroup = new JButton(" Usu� ");
	JButton buttonLoadGroup = new JButton("Otw�rz");
	JButton buttonSavegroup = new JButton("Zapisz");

	JButton buttonUnion = new JButton("Suma");
	JButton buttonIntersection = new JButton("Iloczyn");
	JButton buttonDifference = new JButton("R�nica");
	JButton buttonSymmetricDiff = new JButton("R�nica symetryczna");
			
	ViewGroupList viewList;
	
	public GroupManagerApp() {
		setTitle("GroupManager - zarz�dzanie grupami map");
		setSize(450, 400);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosed(WindowEvent event) {
				try {
					saveGroupListToFile(ALL_GROUPS_FILE);
					JOptionPane.showMessageDialog(null, "Dane zosta�y zapisane do pliku " + ALL_GROUPS_FILE);
				} catch (MapException e) {
					JOptionPane.showMessageDialog(null, e.getMessage(), "B��d", JOptionPane.ERROR_MESSAGE);
				}
			}

			@Override
			public void windowClosing(WindowEvent e) {
				windowClosed(e);
			}
		}
		);
	
		try {
			loadGroupListFromFile(ALL_GROUPS_FILE);
			JOptionPane.showMessageDialog(null, "Dane zosta�y wczytane z pliku " + ALL_GROUPS_FILE);
		} catch (MapException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "B��d", JOptionPane.ERROR_MESSAGE);
		}
		
		setJMenuBar(menuBar);
		menuBar.add(menuGroups);
		menuBar.add(menuSpecialGroups);
		menuBar.add(menuAbout);
				
		menuGroups.add(menuNewGroup);
		menuGroups.add(menuEditGroup);
		menuGroups.add(menuDeleteGroup);
		menuGroups.addSeparator();
		menuGroups.add(menuLoadGroup);
		menuGroups.add(menuSaveGroup);
				
		menuSpecialGroups.add(menuGroupUnion);
		menuSpecialGroups.add(menuGroupIntersection);
		menuSpecialGroups.add(menuGroupDifference);
		menuSpecialGroups.add(menuGroupSymmetricDiff);
				
		menuAbout.add(menuAuthor);
		
		menuNewGroup.addActionListener(this);
		menuEditGroup.addActionListener(this);
		menuDeleteGroup.addActionListener(this);
		menuLoadGroup.addActionListener(this);
		menuSaveGroup.addActionListener(this);
		menuGroupUnion.addActionListener(this);
		menuGroupIntersection.addActionListener(this);
		menuGroupDifference.addActionListener(this);
		menuGroupSymmetricDiff.addActionListener(this);
		menuAuthor.addActionListener(this);
				
		buttonNewGroup.addActionListener(this);
		buttonEditGroup.addActionListener(this);
		buttonDeleteGroup.addActionListener(this);
		buttonLoadGroup.addActionListener(this);
		buttonSavegroup.addActionListener(this);
		buttonUnion.addActionListener(this);
		buttonIntersection.addActionListener(this);
		buttonDifference.addActionListener(this);
		buttonSymmetricDiff.addActionListener(this);
		
		viewList = new ViewGroupList(currentList, 400, 250);
		viewList.refreshView();
				
		JPanel panel = new JPanel();
		
		panel.add(viewList);
		panel.add(buttonNewGroup);
		panel.add(buttonEditGroup);
		panel.add(buttonDeleteGroup);
		panel.add(buttonLoadGroup);
		panel.add(buttonSavegroup);
		panel.add(buttonUnion);
		panel.add(buttonIntersection);
		panel.add(buttonDifference);
		panel.add(buttonSymmetricDiff);
		
		setContentPane(panel);
		
		setVisible(true);
	}
	
	@SuppressWarnings("unchecked")
	void loadGroupListFromFile(String file_name) throws MapException {
		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file_name))) {
		currentList = (List<GroupOfMaps>)in.readObject();
		} catch (FileNotFoundException e) {
			throw new MapException("Nie odnaleziono pliku " + file_name);
		} catch (Exception e) {
			throw new MapException("Wyst�pi� b��d podczas odczytu danych z pliku.");
		}
	}
	
	void saveGroupListToFile(String file_name) throws MapException {
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file_name))) {
			out.writeObject(currentList);
		} catch (FileNotFoundException e) {
			throw new MapException("Nie odnaleziono pliku " + file_name);
		} catch (IOException e) {
			throw new MapException("Wyst�pi� b��d podczas zapisu danych do pliku.");
		}
	}
	
	private  GroupOfMaps chooseGroup(Window parent, String message){
		Object[] groups = currentList.toArray();
		GroupOfMaps group = (GroupOfMaps)JOptionPane.showInputDialog(
		                    parent, message,
		                    "Wybierz grup�",
		                    JOptionPane.QUESTION_MESSAGE,
		                    null,
		                    groups,
		                    null);
		return group;
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		
		try {
			if (source == menuNewGroup || source == buttonNewGroup) {
				GroupOfMaps group = GroupOfMapsWindowDialog.createNewGroupOfMaps(this);
				if (group != null) {
					currentList.add(group);
				}
			}
			
			if (source == menuEditGroup || source == buttonEditGroup) {
				int index = viewList.getSelectedIndex();
				if (index >= 0) {
					Iterator<GroupOfMaps> iterator = currentList.iterator();
					while (index-- > 0)
						iterator.next();
					new GroupOfMapsWindowDialog(this, iterator.next());
				}
			}
			
			if (source == menuDeleteGroup || source == buttonDeleteGroup) {
				int index = viewList.getSelectedIndex();
				if (index >= 0) {
					Iterator<GroupOfMaps> iterator = currentList.iterator();
					while (index-- >= 0)
						iterator.next();
					iterator.remove();
				}
			}
			
			if (source == menuLoadGroup || source == buttonLoadGroup) {
				JFileChooser chooser = new JFileChooser(".");
				int returnVal = chooser.showOpenDialog(this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					GroupOfMaps group = GroupOfMaps.readFromFile(chooser.getSelectedFile().getName());
					currentList.add(group);
				}
			}
			
			if (source == menuSaveGroup || source == buttonSavegroup) {
				int index = viewList.getSelectedIndex();
				if (index >= 0) {
					Iterator<GroupOfMaps> iterator = currentList.iterator();
					while (index-- > 0)
						iterator.next();
					GroupOfMaps group = iterator.next();

					JFileChooser chooser = new JFileChooser(".");
					int returnVal = chooser.showSaveDialog(this);
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						GroupOfMaps.printToFile(chooser.getSelectedFile().getName(), group);
					}
				}
			}
			
			if (source == menuGroupUnion || source == buttonUnion) {
				String message1 = 
						"SUMA GRUP\n\n" + 
			            "Tworzenie grupy zawieraj�cej wszystkie mapy z grupy pierwszej\n" + 
						"oraz wszystkie mapy z grupy drugiej.\n" + 
			            "Wybierz pierwsz� grup�:";
				String message2 = 
						"SUMA GRUP\n\n" + 
					    "Tworzenie grupy zawieraj�cej wszystkie mapy z grupy pierwszej\n" + 
						"oraz wszystkie mapy z grupy drugiej.\n" + 
					    "Wybierz drug� grup�:";
				GroupOfMaps group1 = chooseGroup(this, message1);
				if (group1 == null)
					return;
				GroupOfMaps group2 = chooseGroup(this, message2);
				if (group2 == null)
					return;
				currentList.add( GroupOfMaps.createGroupUnion(group1, group2) );
			}
				
			if (source == menuGroupIntersection || source == buttonIntersection) {
				String message1 = 
						"ILOCZYN GRUP\n\n" + 
				        "Tworzenie grupy map, kt�re nale�� zar�wno do grupy pierwszej,\n" +
						"jak i do grupy drugiej.\n" + 
				        "Wybierz pierwsz� grup�:";
				String message2 = 
						"ILOCZYN GRUP\n\n" + 
						"Tworzenie grupy map, kt�re nale�� zar�wno do grupy pierwszej,\n" +
						"jak i do grupy drugiej.\n" + 
						"Wybierz drug� grup�:";
				GroupOfMaps group1 = chooseGroup(this, message1);
				if (group1 == null)
					return;
				GroupOfMaps group2 = chooseGroup(this, message2);
				if (group2 == null)
					return;
				currentList.add( GroupOfMaps.createGroupIntersection(group1, group2) );
			}
			
			if (source == menuGroupDifference || source == buttonDifference) {
				String message1 = 
						"RӯNICA GRUP\n\n" + 
				        "Tworzenie grupy map, kt�re nale�� do grupy pierwszej\n" +
						"i nie ma ich w grupie drugiej.\n" + 
				        "Wybierz pierwsz� grup�:";
				String message2 = 
						"RӯNICA GRUP\n\n" + 
						"Tworzenie grupy map, kt�re nale�� do grupy pierwszej\n" +
						"i nie ma ich w grupie drugiej.\n" + 
						"Wybierz drug� grup�:";
				GroupOfMaps group1 = chooseGroup(this, message1);
				if (group1 == null)
					return;
				GroupOfMaps group2 = chooseGroup(this, message2);
				if (group2 == null)
					return;
				currentList.add( GroupOfMaps.createGroupDifference(group1, group2) );
			}
				
			if (source == menuGroupSymmetricDiff || source == buttonSymmetricDiff) {
				String message1 = "RӯNICA SYMETRYCZNA GRUP\n\n"
						+ "Tworzenie grupy zawieraj�cej mapy nale��ce tylko do jednej z dw�ch grup,\n"
						+ "Wybierz pierwsz� grup�:";
				String message2 = "RӯNICA SYMETRYCZNA GRUP\n\n"
						+ "Tworzenie grupy zawieraj�cej mapy nale��ce tylko do jednej z dw�ch grup,\n"
						+ "Wybierz drug� grup�:";
				GroupOfMaps group1 = chooseGroup(this, message1);
				if (group1 == null)
					return;
				GroupOfMaps group2 = chooseGroup(this, message2);
				if (group2 == null)
					return;
				currentList.add( GroupOfMaps.createGroupSymmetricDiff(group1, group2) );
			}
			
			if (source == menuAuthor) {
				JOptionPane.showMessageDialog(this, GREETING_MESSAGE);
			}
			
		} catch (MapException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "B��d", JOptionPane.ERROR_MESSAGE);
		}

		viewList.refreshView();
	}
	
}

class ViewGroupList extends JScrollPane {
	
	private static final long serialVersionUID = 1L;
	
	private List<GroupOfMaps> list;
	private JTable table;
	private DefaultTableModel tableModel;
	
	public ViewGroupList(List<GroupOfMaps> list, int width, int height){
		
		this.list = list;
		setPreferredSize(new Dimension(width, height));
		setBorder(BorderFactory.createTitledBorder("Lista grup:"));
		
		String[] tableHeader = { "Nazwa grupy", "Typ kolekcji", "Liczba map" };
		tableModel = new DefaultTableModel(tableHeader, 0);
		table = new JTable(tableModel) {
			
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int rowIndex, int colIndex) {
				return false;
			}
		};
		
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setRowSelectionAllowed(true);
		setViewportView(table);
	}
	
	void refreshView(){
		tableModel.setRowCount(0);
		for (GroupOfMaps group : list) {
			if (group != null) {
				String[] row = { group.getName(), group.getType().toString(), "" + group.size() };
				tableModel.addRow(row);
			}
		}
	}
	
	int getSelectedIndex(){
		int index = table.getSelectedRow();
		if (index<0) {
			JOptionPane.showMessageDialog(this, "�adana grupa nie jest zaznaczona.", "B��d", JOptionPane.ERROR_MESSAGE);
		}
		return index;
	}

}











