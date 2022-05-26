/* Autor: Maja Terpi³owska, 259176
 * Grupa: PN/TN 15:15
 * Data utworzenia: grudzien 2021
 * Lab2
 * Plik: MapWindowApp.java
 */

package Lab2gui;

import data.Map;
import data.MapException;
import data.MapType;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MapWindowApp extends JFrame implements ActionListener{
	
	private static final long serialVersionUID = 1L;

	private static final String GREETING_MESSAGE =
			"Program Lab2 - wersja okienkowa \n" + 
			        "Autor: Maja Terpi³owska, 259176\n" +
					"Grupa: PN/TN 15:15\n" +
					"Data:  grudzien 2021 r\n";
	
	public static void main(String[] args) {
		new MapWindowApp();
	}

	private Map currentMap;

	Font font = new Font("MonoSpaced", Font.BOLD, 12);
	
	JLabel nazwaLabel = new JLabel("      Nazwa: ");
	JLabel skalaLabel  = new JLabel("      Skala: ");
	JLabel wymiarxLabel       = new JLabel("   Wymiar X: ");
	JLabel wymiaryLabel       = new JLabel("   Wymiar Y: ");
	JLabel rodzajLabel      = new JLabel("Rodzaj mapy: ");
	JLabel autorLabel       = new JLabel("      Autor: ");	
	
	JTextField nazwaField = new JTextField(10);
	JTextField skalaField = new JTextField(10);
	JTextField wymiarxField = new JTextField(10);
	JTextField wymiaryField = new JTextField(10);
	JTextField rodzajField = new JTextField(10);
	JTextField autorField = new JTextField(10);
	
	JButton newButton    = new JButton("Nowa mapa");
	JButton editButton   = new JButton("Zmieñ dane");
	JButton saveButton   = new JButton("Zapisz do pliku");
	JButton loadButton   = new JButton("Wczytaj z pliku");
	JButton deleteButton = new JButton("Usuñ mapê");
	JButton infoButton   = new JButton("O autorze");
	JButton exitButton   = new JButton("Zakoñcz aplikacjê");
	
	public MapWindowApp(){
		setTitle("MapWindowApp");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(320, 320);
		setResizable(false);
		setLocationRelativeTo(null);

		nazwaLabel.setFont(font);
		skalaLabel.setFont(font);
		wymiarxLabel.setFont(font);
		wymiaryLabel.setFont(font);
		rodzajLabel.setFont(font);
		autorLabel.setFont(font);

		nazwaField.setEditable(false);
		skalaField.setEditable(false);
		wymiarxField.setEditable(false);
		wymiaryField.setEditable(false);
		rodzajField.setEditable(false);
		autorField.setEditable(false);

		newButton.addActionListener(this);
		editButton.addActionListener(this);
		saveButton.addActionListener(this);
		loadButton.addActionListener(this);
		deleteButton.addActionListener(this);
		infoButton.addActionListener(this);
		exitButton.addActionListener(this);

		JPanel panel = new JPanel();

		panel.add(nazwaLabel);
		panel.add(nazwaField);
		panel.add(skalaLabel);
		panel.add(skalaField);
		panel.add(wymiarxLabel);
		panel.add(wymiarxField);
		panel.add(wymiaryLabel);
		panel.add(wymiaryField);
		panel.add(rodzajLabel);
		panel.add(rodzajField);
		panel.add(autorLabel);
		panel.add(autorField);

		panel.add(newButton);
		panel.add(deleteButton);
		panel.add(saveButton);
		panel.add(loadButton);
		panel.add(editButton);
		panel.add(infoButton);
		panel.add(exitButton);

		setContentPane(panel);
		showCurrentMap();
		setVisible(true);
	}
	
	void showCurrentMap() {
		if (currentMap == null) {
			nazwaField.setText("");
			skalaField.setText("");
			wymiarxField.setText("");
			wymiaryField.setText("");
			rodzajField.setText("");
			autorField.setText("");
		} else {
			nazwaField.setText(currentMap.getNazwa());
			skalaField.setText("" + currentMap.getSkala());
			wymiarxField.setText("" + currentMap.getWymiarX());
			wymiaryField.setText("" + currentMap.getWymiarY());
			rodzajField.setText("" + currentMap.getRodzaj());
			autorField.setText(currentMap.getAutor());
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		Object eventSource = event.getSource();

		try {
			if (eventSource == newButton) {
				currentMap = MapWindowDialog.createNewMap(this);
			}
			if (eventSource == deleteButton) {
				currentMap = null;
			}
			if (eventSource == saveButton) {
				String fileName = JOptionPane.showInputDialog("Podaj nazwê pliku");
				if (fileName == null || fileName.equals("")) return;
				Map.printToFile(fileName, currentMap);
			}
			if (eventSource == loadButton) {
				String fileName = JOptionPane.showInputDialog("Podaj nazwê pliku");
				if (fileName == null || fileName.equals("")) return;
				currentMap = Map.readFromFile(fileName);
			}
			if (eventSource == editButton) {
				if (currentMap == null) throw new MapException("¯adna mapa nie zosta³a utworzona.");
				MapWindowDialog.changeMapData(this, currentMap);
			}
			if (eventSource == infoButton) {
				JOptionPane.showMessageDialog(this, GREETING_MESSAGE);
			}
			if (eventSource == exitButton) {
				System.exit(0);
			}
		} catch (MapException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "B³¹d", JOptionPane.ERROR_MESSAGE);
		}

		showCurrentMap();
	}

}

