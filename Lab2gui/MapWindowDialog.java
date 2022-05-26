/* Autor: Maja Terpi³owska, 259176
 * Grupa: PN/TN 15:15
 * Data utworzenia: grudzien 2021
 * Lab2
 * Plik: MapWindowDialog.java
 */

package Lab2gui;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import data.Map;
import data.MapException;
import data.MapType;

public class MapWindowDialog extends JDialog implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	private Map mapa;
	
	Font font = new Font("MonoSpaced", Font.BOLD, 12);
	
	JLabel nazwaLabel = new JLabel("      Nazwa: ");
	JLabel skalaLabel  = new JLabel("      Skala: ");
	JLabel wymiarxLabel = new JLabel("   Wymiar X: ");
	JLabel wymiaryLabel = new JLabel("   Wymiar Y: ");
	JLabel rodzajLabel = new JLabel("Rodzaj mapy: ");
	JLabel autorLabel = new JLabel("      Autor: ");	
	
	JTextField nazwaField = new JTextField(10);
	JTextField skalaField = new JTextField(10);
	JTextField wymiarxField = new JTextField(10);
	JTextField wymiaryField = new JTextField(10);
	JComboBox<MapType> typBox = new JComboBox<MapType>(MapType.values());
	JTextField autorField = new JTextField(10);
	
	JButton OKButton = new JButton("  OK  ");
	JButton CancelButton = new JButton("Anuluj");
	
    private MapWindowDialog(Window parent, Map mapa) {
		
		super(parent, Dialog.ModalityType.DOCUMENT_MODAL);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(400, 200);
		setLocationRelativeTo(parent);

		this.mapa = mapa;
		if (mapa==null){
			setTitle("Nowa mapa");
		} else{
			setTitle(mapa.toString());
			nazwaField.setText(mapa.getNazwa());
			skalaField.setText(""+mapa.getSkala());
			wymiarxField.setText(""+mapa.getWymiarX());
			wymiaryField.setText(""+mapa.getWymiarY());
			typBox.setSelectedItem(mapa.getRodzaj());
			autorField.setText(mapa.getAutor());
		}
		
		OKButton.addActionListener( this );
		CancelButton.addActionListener( this );
		
		JPanel panel = new JPanel();
		
		panel.setBackground(Color.white);

		panel.add(nazwaLabel);
		panel.add(nazwaField);
		panel.add(skalaLabel);
		panel.add(skalaField);
		panel.add(wymiarxLabel);
		panel.add(wymiarxField);
		panel.add(wymiaryLabel);
		panel.add(wymiaryField);
		panel.add(rodzajLabel);
		panel.add(typBox);
		panel.add(autorLabel);
		panel.add(autorField);
		
		panel.add(OKButton);
		panel.add(CancelButton);
		
		setContentPane(panel);
		setVisible(true);
	}

    @Override
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		
		if (source == OKButton) {
			try {
				if (mapa == null) { 
					mapa = new Map(nazwaField.getText(), skalaField.getText());
				} else {
					mapa.setNazwa(nazwaField.getText());
					mapa.setSkala(skalaField.getText());
				}
				mapa.setWymiarX(wymiarxField.getText());
				mapa.setWymiarY(wymiaryField.getText());
				mapa.setRodzaj((MapType) typBox.getSelectedItem());
				mapa.setAutor(autorField.getText());
				dispose();
			} catch (MapException e) {
				JOptionPane.showMessageDialog(this, e.getMessage(), "B³¹d", JOptionPane.ERROR_MESSAGE);
			}
		}

		if (source == CancelButton) {
			dispose();
		}
	}
    
    public static Map createNewMap(Window parent) {
		MapWindowDialog dialog = new MapWindowDialog(parent, null);
		return dialog.mapa;
	}
    
    public static void changeMapData(Window parent, Map mapa) {
		new MapWindowDialog(parent, mapa);
	}
	
}

