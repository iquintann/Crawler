package mainPackage;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class Ocurrencia implements Serializable{
	//Atributos
	//<URL, ocurrencia local>
	//Podiamos hacer que se guarde la URL del doc con mayor apariciones para que no haya que ir a buscarlo
	private Map<String, Integer> arbolOcurrencia;
	private Integer ocurrenciaGlobal;
	
	public Ocurrencia() {
		this.arbolOcurrencia=new TreeMap<String, Integer>();
		ocurrenciaGlobal=0;
	}
	
	//añadir nuevo documento o url
	public void anadirNuevoDoc(String url,Integer ocurrenciaLocal) {
		this.arbolOcurrencia.put(url, ocurrenciaLocal);
		//comparamos que la variable local no sea menor que la mayor
		if(ocurrenciaGlobal<ocurrenciaLocal) {
			ocurrenciaGlobal=ocurrenciaLocal;
		}
	}
	
	public void mostrarURLs() {
		System.out.println("Se muestra las URLs acompañados del termino local"
				+ "\n_______________________________________________________");
		
		Iterator it = arbolOcurrencia.entrySet().iterator();
		while (it.hasNext()) {
			HashMap.Entry pair = (HashMap.Entry) it.next();
			System.out.println("URL: "+pair.getKey() + ", aparciones " + pair.getValue());
			it.remove(); // avoids a ConcurrentModificationException
		}
	}
	

}
