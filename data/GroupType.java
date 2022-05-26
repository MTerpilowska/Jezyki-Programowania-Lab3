/* Autor: Maja Terpi³owska, 259176
 * Grupa: PN/TN 15:15
 * Data utworzenia: grudzien 2021
 * Lab3 
 * Plik: GroupType.java
 */

package data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.TreeSet;
import java.util.Vector;

import data.MapException;

public enum GroupType {
	
	VECTOR("Lista   (klasa Vector)"), 
	ARRAY_LIST("Lista   (klasa ArrayList)"), 
	LINKED_LIST("Lista   (klasa LinkedList)"),
	HASH_SET("Zbiór   (klasa HashSet)"),
	TREE_SET("Zbiór   (klasa TreeSet)");
	
	String typeName;

	private GroupType(String type_name) {
		typeName = type_name;
	}
	
	@Override
	public String toString() {
		return typeName;
	}
	
	public static GroupType find(String type_name){
		for(GroupType type : values()){
			if (type.typeName.equals(type_name)){
				return type;
			}
		}
		return null;
	}
	
	public Collection<Map> createCollection() throws MapException {
		switch (this) {
		case VECTOR:      return new Vector<Map>();
		case ARRAY_LIST:  return new ArrayList<Map>();
		case HASH_SET:    return new HashSet<Map>();
		case LINKED_LIST: return new LinkedList<Map>();
		case TREE_SET:    return new TreeSet<Map>();
		default:          throw new MapException("Podany typ kolekcji nie zosta³ zaimplementowany.");
		}
	}
}
