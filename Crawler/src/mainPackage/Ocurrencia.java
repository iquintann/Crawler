package mainPackage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

public class Ocurrencia implements Serializable {
	// Atributos
	// <URL, ocurrencia local>
	// Podiamos hacer que se guarde la URL del doc con mayor apariciones para que no
	// haya que ir a buscarlo
	private Map<Integer, Integer> arbolUrls;
	private Integer ocurrenciaGlobal;

	public Ocurrencia() {
		this.arbolUrls = new TreeMap<Integer, Integer>();
		ocurrenciaGlobal = new Integer(0);
	}

	// añadir nuevo documento o url
	public void anadirNuevoDoc(String url, Integer ocurrenciaLocal, Fat fat) {
		
		//buscar la URL en mi indice invertido
		Integer idUrl = fat.insertarURL(url);
		
		
		this.arbolUrls.put(idUrl, ocurrenciaLocal);
		// Incrementamos el valor de la variable global
		ocurrenciaGlobal = ocurrenciaGlobal + ocurrenciaLocal;

	}

	public Map<Integer, Integer> getArbolUrls() {
		return arbolUrls;
	}

	public void setArbolUrls(Map<Integer, Integer> arbolUrls) {
		this.arbolUrls = arbolUrls;
	}

	public void mostrarURLs(Fat fat) {
 
		Iterator it = arbolUrls.entrySet().iterator();
		ArrayList<String> listSalida = new ArrayList<String>();
		while (it.hasNext()) {
			HashMap.Entry pair = (HashMap.Entry) it.next();
			String url= fat.devolverUrl((Integer)pair.getKey());
			listSalida.add("URL: " + pair.getKey() + ", aparciones " + url);
		}
		
		for(int i = listSalida.size() -1; i >= 0; i--)
			System.out.println(listSalida.get(i));
		
		
		
		
	}
	

	public static <K, V extends Comparable<? super V>> SortedSet<Map.Entry<K, V>> entriesSortedByValues(Map<K, V> map) {

		SortedSet<Map.Entry<K, V>> sortedEntries = new TreeSet<Map.Entry<K, V>>(new Comparator<Map.Entry<K, V>>() {
			@Override
			public int compare(Map.Entry<K, V> e1, Map.Entry<K, V> e2) {
				int res = e1.getValue().compareTo(e2.getValue());
				return res != 0 ? res : 1;
			}
		});

		sortedEntries.addAll(map.entrySet());

		return sortedEntries;
	}

}
