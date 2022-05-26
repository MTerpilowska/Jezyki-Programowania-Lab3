/* Autor: Maja Terpi³owska, 259176
 * Grupa: PN/TN 15:15
 * Data utworzenia: grudzien 2021
 * Lab3 
 * Plik: GroupOfMaps.java
 */

package data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import data.MapException;

public class GroupOfMaps implements Iterable<Map>, Serializable {
	
	private static final long serialVersionUID = 1L;

	private String name;
	private GroupType type;
	private Collection<Map> collection;

	public GroupOfMaps(GroupType type, String name) throws MapException {
		setName(name);
		if (type==null){
			throw new MapException("Nieprawid³owy typ kolekcji.");
		}
		this.type = type;
		collection = this.type.createCollection();
	}
	
	public GroupOfMaps(String type_name, String name) throws MapException {
		setName(name);
		GroupType type = GroupType.find(type_name);
		if (type==null){
			throw new MapException("Nieprawid³owy typ kolekcji.");
		}
		this.type = type;
		collection = this.type.createCollection();
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) throws MapException {
		if ((name == null) || name.equals(""))
			throw new MapException("Nazwa grupy musi byæ okreœlona.");
		this.name = name;
	}
		
	public GroupType getType() {
		return type;
	}

	public void setType(GroupType type) throws MapException {
		if (type == null) {
			throw new MapException("Typ kolekcji musi byæ okreœlny.");
		}
		if (this.type == type)
			return;
		Collection<Map> oldCollection = collection;
		collection = type.createCollection();
		this.type = type;
		for (Map mapa : oldCollection)
			collection.add(mapa);
	}
	
	public void setType(String type_name) throws MapException {
		for(GroupType type : GroupType.values()){
			if (type.toString().equals(type_name)) {
				setType(type);
				return;
			}
		}
		throw new MapException("Nie ma takiego typu kolekcji.");
	}
	
	public boolean add(Map m) {
		return collection.add(m);
	}

	public Iterator<Map> iterator() {
		return collection.iterator();
	}
			
	public int size() {
		return collection.size();
	}
	
	public void sortNazwa() throws MapException {
		if (type==GroupType.HASH_SET|| type==GroupType.TREE_SET ){
			throw new MapException("Kolekcje typu SET nie mog¹ byæ sortowane."); 
		}
		Collections.sort((List<Map>)collection);
	}
	
	public void sortSkala() throws MapException {
		if (type == GroupType.HASH_SET || type == GroupType.TREE_SET) {
			throw new MapException("Kolekcje typu SET nie mog¹ byæ sortowane.");
		}
		Collections.sort((List<Map>) collection, new Comparator<Map>() {

			@Override
			public int compare(Map m1, Map m2) {
				if (m1.getSkala() < m2.getSkala())
					return -1;
				if (m1.getSkala() > m2.getSkala())
					return 1;
				return 0;
			}

		});
	}

	public void sortRodzaj() throws MapException {
		if (type == GroupType.HASH_SET || type == GroupType.TREE_SET) {
			throw new MapException("Kolekcje typu SET nie mog¹ byæ sortowane.");
		}
		Collections.sort((List<Map>) collection, new Comparator<Map>() {

			@Override
			public int compare(Map m1, Map m2) {
				return m1.getRodzaj().toString().compareTo(m2.getRodzaj().toString());
			}

		});
	}	
	
	@Override
	public String toString() {
		return name + "  [" + type + "]";
	}
	
	public static void printToFile(PrintWriter writer, GroupOfMaps group) {
		writer.println(group.getName());
		writer.println(group.getType());
		for (Map mapa : group.collection)
			Map.printToFile(writer, mapa);
	}
	
	public static void printToFile(String file_name, GroupOfMaps group) throws MapException {
		try (PrintWriter writer = new PrintWriter(file_name)) {
			printToFile(writer, group);
		} catch (FileNotFoundException e){
			throw new MapException("Nie odnaleziono pliku " + file_name);
		}
	}
	
	public static GroupOfMaps readFromFile(BufferedReader reader) throws MapException{
		try {
			String group_name = reader.readLine();
			String type_name = reader.readLine();
			GroupOfMaps groupOfMaps = new GroupOfMaps(type_name, group_name);

			Map mapa;
			while((mapa = Map.readFromFile(reader)) != null)
				groupOfMaps.collection.add(mapa);
			return groupOfMaps;
		} catch(IOException e){
			throw new MapException("Wyst¹pi³ b³¹d podczas odczytu danych z pliku.");
		}	
	}
	
	
	public static GroupOfMaps readFromFile(String file_name) throws MapException {
		try (BufferedReader reader = new BufferedReader(new FileReader(new File(file_name)))) {
			return GroupOfMaps.readFromFile(reader);
		} catch (FileNotFoundException e){
			throw new MapException("Nie odnaleziono pliku " + file_name);
		} catch(IOException e){
			throw new MapException("Wyst¹pi³ b³¹d podczas odczytu danych z pliku.");
		}	
	}
	
	public static GroupOfMaps createGroupUnion(GroupOfMaps g1,GroupOfMaps g2) throws MapException {
		String name = "(" + g1.name + " OR " + g2.name +")";
		GroupType type;
		if (g2.collection instanceof Set && !(g1.collection instanceof Set) ){
			type = g2.type;
		} else {
			type = g1.type;
		}
		GroupOfMaps group = new GroupOfMaps(type, name);
		group.collection.addAll(g1.collection);
		group.collection.addAll(g2.collection);
		return group;
	}
	
	public static GroupOfMaps createGroupIntersection(GroupOfMaps g1,GroupOfMaps g2) throws MapException {
		String name = "(" + g1.name + " AND " + g2.name +")";
		GroupType type;
		if (g2.collection instanceof Set && !(g1.collection instanceof Set) ){
			type = g2.type;
		} else {
			type = g1.type;
		}
		GroupOfMaps group = new GroupOfMaps(type, name);
		
		Collection<Map> tmp_g2 = group.type.createCollection();
		tmp_g2.addAll(g2.collection);
		for(Map mapa : g1.collection){
			if(tmp_g2.remove(mapa)){
				group.collection.add(mapa);
			}
		}
		return group;
	}
	
	public static GroupOfMaps createGroupDifference(GroupOfMaps g1,GroupOfMaps g2) throws MapException {
		String name = "(" + g1.name + " SUB " + g2.name +")";
		GroupType type;
		if (g2.collection instanceof Set && !(g1.collection instanceof Set) ){
			type = g2.type;
		} else {
			type = g1.type;
		}
		GroupOfMaps group = new GroupOfMaps(type, name);
		group.collection.addAll(g1.collection);
		for(Map mapa : g2.collection){
			group.collection.remove(mapa);
		}
		return group;
	}
	
	public static GroupOfMaps createGroupSymmetricDiff(GroupOfMaps g1,GroupOfMaps g2) throws MapException {
		String name = "(" + g1.name + " XOR " + g2.name +")";
		GroupType type;
		if (g2.collection instanceof Set && !(g1.collection instanceof Set) ){
			type = g2.type;
		} else {
			type = g1.type;
		}
		// g2 - g1
		Collection<Map> tmp_sub = type.createCollection();
		tmp_sub.addAll(g2.collection);
		for(Map mapa : g1.collection){
			tmp_sub.remove(mapa);
		}
		// g1 - g2
		GroupOfMaps group = new GroupOfMaps(type, name);
		group.collection.addAll(g1.collection);
		for(Map mapa : g2.collection){
			group.collection.remove(mapa);
		}	
		// (g1-g2) +  (g2-g1)
		group.collection.addAll(tmp_sub);
		return group;
	}
	
}
