package mainPackage;
/*
 * FichContPalabras.java
 * Contabiliza palabras contenidas en un fichero
 * (i) Félix R. Rodríguez, EPCC, Universidad de Extremadura, 2009
 * http://madiba.unex.es/felix
 */

import java.io.*;
import java.util.*;

public class FichContPalabras implements Serializable {

	private Map<String, Ocurrencia> mapOcurrencia;
	private HashMap<String, Integer> stopwords;

	/**
	 * Constructor
	 * @param Hasmap <String,Integer> de las stopwords
	 */
	public FichContPalabras(HashMap<String, Integer> stopwords) {
		mapOcurrencia = new TreeMap<String, Ocurrencia>();
		this.stopwords = stopwords;
	}
		
	public Map<String, Ocurrencia> getMapOcurrencia() {
		return mapOcurrencia;
	}

	public void setMapOcurrencia(Map<String, Ocurrencia> mapOcurrencia) {
		this.mapOcurrencia = mapOcurrencia;
	}

	/**
	 * Todas las paralabras que encontremos las a�adirmeos a una estructura Map, si
	 * esta ya se encontrase dentro incrementamos su valor en +1
	 * 
	 * @param file
	 * @throws IOException
	 */
	public void wordCount(String file,Fat fat) throws IOException {

		String fichEntrada = file;

		BufferedReader br = new BufferedReader(new FileReader(fichEntrada));
		String linea;

		Map<String, Integer> mapLocal = new TreeMap<String, Integer>();

		while ((linea = br.readLine()) != null) {

			/*
			 * Lee fichero Meto hash Termine fuichero Hash a hashGrande <termino,ocurrenc
			 */
			StringTokenizer tokenLinea = new StringTokenizer(linea, " ,.:;¿?¡()[]{}'\"\t-_");
			while (tokenLinea.hasMoreTokens()) {
				String palabra = tokenLinea.nextToken();

				if (!stopwords.containsKey(palabra.toLowerCase())) {
					
					// no existe en el stopword, la guardamos
					Object o = mapLocal.get(palabra);
					if (o == null) {
						
						// Si no existe la palabra la introduzco
						mapLocal.put(palabra, 1);
					} else {
						// Si existe +1
						Integer cont = (Integer) o;
						mapLocal.put(palabra, cont.intValue() + 1);
					}

				} else {
					// System.out.println("Palabras a ignorar:" + palabra);

				}
			}
		}
		br.close();
		
		//Treelocal --> treeOcurrencia
		///URL = fichEntrada
		// Termino --> treeloca.getKey
		// Ocurrencia --> treeloca.getPair
		
		//System.out.println(mapLocal.toString());
		guardarInformacionTreeLocal(mapLocal,file,fat);
		
	}

	/***
	 * Guardamos toda la informacion del fichero y lo asociamos a un numero de id añadiendolo en la estructura fat
	 * @param mapLocal hasmap de un fichero ya analizado
	 * @param file url de l fichero para asociarlo a un id de la fat
	 * @param fat estructura de indice inverso
	 */
	private void guardarInformacionTreeLocal(Map<String, Integer> mapLocal,String file, Fat fat) {
		for(Map.Entry<String,Integer> entry : mapLocal.entrySet()) {
			  String key = entry.getKey();
			  Integer value = entry.getValue();
			  
			  
			  if(this.mapOcurrencia.containsKey(key)) {
				  //añado nueva URL
				  Ocurrencia ocur=mapOcurrencia.get(key);
				  ocur.anadirNuevoDoc(file,value,fat);
				  
			  }else{
				  //creo nuevo nodo en el mapOcurrencia y añado URL
				  Ocurrencia ocur = new Ocurrencia();
				  ocur.anadirNuevoDoc(file, value,fat);
				  mapOcurrencia.put(key, ocur);
			  }

			}
		
	}

	

	

}
