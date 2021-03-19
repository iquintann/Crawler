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
/**
 * Se encarga de analizar un documento e indicar cuantas veces coincide un término
 *
 */
public class Ocurrencia implements Serializable {
	// Variables
	private Map<Integer, Integer> arbolUrls;
	private Integer ocurrenciaGlobal;
	
	public Ocurrencia() {
		this.arbolUrls = new TreeMap<Integer, Integer>();
		ocurrenciaGlobal = new Integer(0);
	}

	/**
	 * Añade una nueva ur al arbol de urls y declara la ocurrencia del término en esta url
	 * Es decir, una vez recorrido el fichero guardará la url si aparece el termino X y cuantas veces en dicha url
	 * @param url del documento
	 * @param ocurrenciaLocal numero de veces que se repite el termino en el fichero actual (url)
	 * @param fat estructura de indice inverso
	 */
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

	public void mostrarURLsMayoraMenor(Fat fat) {
		
		Map sortedMap = valueSort(arbolUrls);
		Iterator it= sortedMap.entrySet().iterator();
		while (it.hasNext()) {
			HashMap.Entry pair = (HashMap.Entry) it.next();
			String url= fat.devolverUrl((Integer)pair.getKey());
			System.out.println("URL: " + url + ", aparciones " + pair.getValue());
		}
	}
	
	/**
	 * Comparable utilizado para la ordenación de las url por ocurrencia local
	 * @param <K>
	 * @param <V>
	 * @param map
	 * @return
	 */
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
	
	public static <K, V extends Comparable<V> > Map<K, V> 
    valueSort(final Map<K, V> map) 
    { 
        Comparator<K> valueComparator = new Comparator<K>() { 
                  public int compare(K k1, K k2) 
                  { 
                      int comp = map.get(k2).compareTo( 
                          map.get(k1)); 
                      if (comp == 0) 
                          return 1; 
                      else
                          return comp; 
                  } 
            
              }; 
        
        Map<K, V> sorted = new TreeMap<K, V>(valueComparator); 
        
        sorted.putAll(map); 
        
        return sorted; 
    }

}
