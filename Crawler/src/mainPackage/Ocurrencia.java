package mainPackage;

import java.io.Serializable;
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
	private Map<String, Integer> arbolUrls;
	private Integer ocurrenciaGlobal;

	public Ocurrencia() {
		this.arbolUrls = new TreeMap<String, Integer>();
		ocurrenciaGlobal = new Integer(0);
	}

	// añadir nuevo documento o url
	public void anadirNuevoDoc(String url, Integer ocurrenciaLocal) {
		this.arbolUrls.put(url, ocurrenciaLocal);
		// Incrementamos el valor de la variable global
		ocurrenciaGlobal = ocurrenciaGlobal + ocurrenciaLocal;

	}

	public Map<String, Integer> getArbolUrls() {
		return arbolUrls;
	}

	public void setArbolUrls(Map<String, Integer> arbolUrls) {
		this.arbolUrls = arbolUrls;
	}

	public void mostrarURLs() {
		System.out.println("aparece en las siguientes URLs");

		Iterator it = arbolUrls.entrySet().iterator();
		while (it.hasNext()) {
			HashMap.Entry pair = (HashMap.Entry) it.next();
			System.out.println("URL: " + pair.getKey() + ", aparciones " + pair.getValue());
		
		}
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
	
//	public void sort(final Comparator<? super T> dataComparator)
//    {
//        Collections.sort(this.children,
//            new Comparator<Tree<T>>()
//            {
//                @Override
//                public int compare(Tree<T> treeA, Tree<T> treeB)
//                {
//                    return dataComparator.compare(treeA.getData(), treeB.getData());
//                }
//            }
//        );
//        for(Tree<T> child: this.children)
//        {
//            child.sort(dataComparator);
//        }
//    }

}
