/* Autor: Maja Terpi³owska, 259176
 * Grupa: PN/TN 15:15
 * Data utworzenia: grudzien 2021
 * Lab3 
 * Plik: Map.java
 */

package data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

import data.MapException;
import data.MapType;

public class Map implements Comparable<Map> {
	
	private String nazwa;
	private int skala;
	private MapType rodzaj;
	private int wymiar_x;
	private int wymiar_y;
	private String autor;
	
	/*
	 * ograniczenia:
	 * - pola nazwa oraz autor musz¹ zawieraæ niepusty ci¹g znaków
	 * - pole skala musi zawieraæ liczby ca³kowite z przedzia³u (0,1000000>
	 * - pola wymiar_x oraz wymiar_y musz¹ zawieraæ liczby ca³kowite z przedzia³u (0,1000> (w centymetrach)
	 * - pole rodzaj musi zawieraæ wy³¹cznie jedn¹ z pozycji zdefiniowanych
	 *     w typie wyliczeniowym enum MapType
	 */
	
	public Map(String nazwa_mapy, String autor_mapy) throws MapException{
		setNazwa(nazwa_mapy);
		setAutor(autor_mapy);
		rodzaj = MapType.NIEZNANA;
	}
	
	public String getNazwa() {
		return nazwa;
	}
	
	public void setNazwa(String nazwa_mapy) throws MapException{
		if((nazwa_mapy == null) || nazwa_mapy.equals(""))
			throw new MapException("Pole <Nazwa> nie moze byc puste");
		this.nazwa = nazwa_mapy;
	}
	
	public String getAutor() {
		return autor;
	}
	
	public void setAutor(String autor_mapy) throws MapException{
		if((autor_mapy == null) || autor_mapy.equals(""))
			throw new MapException("Pole <Autor> nie moze byc puste");
		this.autor = autor_mapy;
	}
	
	public int getSkala() {
		return skala;
	}
	
	public void setSkala(int skala_mapy) throws MapException{
		if((skala_mapy <= 0) || (skala_mapy > 1000000))
			throw new MapException("Skala musi byc w przedziale (0,1000000>.");
		this.skala = skala_mapy;
	}
	
	public void setSkala(String skala_mapy) throws MapException {
		if (skala_mapy == null || skala_mapy.equals("")){
			setSkala(0);
			return;
		}
		try { 
			setSkala(Integer.parseInt(skala_mapy));
		} catch (NumberFormatException e) {
			throw new MapException("Skala musi byæ liczb¹ ca³kowit¹");
		}
	}
	
	public int getWymiarX() {
		return wymiar_x;
	}
	
	public void setWymiarX(int wymiar_x_mapy) throws MapException{
		if((wymiar_x_mapy <= 0) || (wymiar_x_mapy > 1000))
			throw new MapException("Wymiary musz¹ byc w przedziale (0,1000>.");
		this.wymiar_x = wymiar_x_mapy;
	}
	
	public void setWymiarX(String wymiar_x_mapy) throws MapException {
		if (wymiar_x_mapy == null || wymiar_x_mapy.equals("")){
			setWymiarX(0);
			return;
		}
		try { 
			setWymiarX(Integer.parseInt(wymiar_x_mapy));
		} catch (NumberFormatException e) {
			throw new MapException("Wymiary musza byæ liczba calkowita.");
		}
	}
	
	public int getWymiarY() {
		return wymiar_y;
	}
	
	public void setWymiarY(int wymiar_y_mapy) throws MapException{
		if((wymiar_y_mapy <= 0) || (wymiar_y_mapy > 1000))
			throw new MapException("Wymiary musz¹ byc w przedziale (0,1000>.");
		this.wymiar_y = wymiar_y_mapy;
	}
	
	public void setWymiarY(String wymiar_y_mapy) throws MapException {
		if (wymiar_y_mapy == null || wymiar_y_mapy.equals("")){
			setWymiarY(0);
			return;
		}
		try { 
			setWymiarY(Integer.parseInt(wymiar_y_mapy));
		} catch (NumberFormatException e) {
			throw new MapException("Wymiary musza byæ liczba calkowita.");
		}
	}
	
	public MapType getRodzaj() {
		return rodzaj;
	}
	
	public void setRodzaj(MapType rodzaj) {
		this.rodzaj = rodzaj;
	}
	
	public void setRodzaj(String rodzaj_mapy) throws MapException{
		if((rodzaj_mapy == null) || rodzaj_mapy.equals("")) {
			this.rodzaj = MapType.NIEZNANA;
			return;
		}
		for(MapType rodzaj : MapType.values()) {
			if(rodzaj.rodzajMapy.equals(rodzaj_mapy)) {
				this.rodzaj = rodzaj;
				return;
			}
		}
		throw new MapException("Nie istnieje taki rodzaj mapy");
	}
	
	@Override
	public String toString() {
		return "' " + nazwa + "', autor: " + autor; 
	}
	
	public int hashCode() {
	    return Objects.hash(new Object[] { this.nazwa, this.autor });
	  }
	  
	  public boolean equals(Object obj) {
	    if (this == obj)
	      return true; 
	    if (!(obj instanceof Map))
	      return false; 
	    Map other = (Map)obj;
	    return (Objects.equals(this.nazwa, other.nazwa) && Objects.equals(this.autor, other.autor));
	  }
	  
	  public int compareTo(Map m) {
	    int result = this.autor.compareTo(m.autor);
	    if (result == 0)
	      result = this.nazwa.compareTo(m.nazwa); 
	    return result;
	  }
	
	public static void printToFile(PrintWriter writer, Map mapa){
		writer.println(mapa.nazwa + "#" + mapa.autor + 
				"#" + mapa.skala + "#" + mapa.wymiar_x + "#" + mapa.wymiar_y + "#" + mapa.rodzaj);
	}
	
	public static void printToFile(String file_name, Map mapa) throws MapException {
		try (PrintWriter writer = new PrintWriter(file_name)) {
			printToFile(writer, mapa);
		} catch (FileNotFoundException e){
			throw new MapException("Nie odnaleziono pliku " + file_name);
		}
	}
	
	public static Map readFromFile(BufferedReader reader) throws MapException{
		try {
			String line = reader.readLine();
			String[] txt = line.split("#");
			Map mapa = new Map(txt[0], txt[1]);
			mapa.setSkala(txt[2]);
			mapa.setWymiarX(txt[3]);	
			mapa.setWymiarY(txt[4]);
			mapa.setRodzaj(txt[5]);
			return mapa;
		} catch(IOException e){
			throw new MapException("Wyst¹pi³ b³¹d podczas odczytu danych z pliku.");
		}	
	}
	
	public static Map readFromFile(String file_name) throws MapException {
		try (BufferedReader reader = new BufferedReader(new FileReader(new File(file_name)))) {
			return Map.readFromFile(reader);
		} catch (FileNotFoundException e){
			throw new MapException("Nie odnaleziono pliku " + file_name);
		} catch(IOException e){
			throw new MapException("Wyst¹pi³ b³¹d podczas odczytu danych z pliku.");
		}	
	}
	
}
