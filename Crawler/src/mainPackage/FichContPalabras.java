package mainPackage;
/*
 * FichContPalabras.java
 * Contabiliza palabras contenidas en un fichero
 * (i) Félix R. Rodríguez, EPCC, Universidad de Extremadura, 2009
 * http://madiba.unex.es/felix
 */

import java.io.*;
import java.util.*;

public class FichContPalabras {

	private String fichSalida;
	private PrintWriter pr;
	private Map<String, Integer> map;
	private HashMap<String, Integer> stopwords;

	public FichContPalabras(String file, HashMap<String, Integer> stopwords) {
		fichSalida = file;
		map = new TreeMap<String, Integer>();
		this.stopwords = stopwords;
	}

	/**
	 * Todas las paralabras que encontremos las a�adirmeos a una estructura Map, si
	 * esta ya se encontrase dentro incrementamos su valor en +1
	 * 
	 * @param file
	 * @throws IOException
	 */
	public void wordCount(String file) throws IOException {

		String fichEntrada = file;

		BufferedReader br = new BufferedReader(new FileReader(fichEntrada));
		String linea;

		while ((linea = br.readLine()) != null) {
			StringTokenizer tokenLinea = new StringTokenizer (linea, " ,.:;¿?¡()[]{}'\"\t-_");
			while (tokenLinea.hasMoreTokens()) {
				String palabra = tokenLinea.nextToken();
				
				if (!stopwords.containsKey(palabra.toLowerCase())) {
					// no existe en el stopword, la guardamos
					Object o = map.get(palabra);
					if (o == null) {
						// Si no existe la palabra la introduzco

						map.put(palabra, 1);
					} else {
						// Si existe +1
						Integer cont = (Integer) o;
						map.put(palabra, cont.intValue() + 1);
					}

				} else {
					System.out.println("Palabras a ignorar:" + palabra);

				}
			}
		}
		br.close();

	}

	/**
	 * Una vez leeido y explorado todos los documentos llamaremos a este m�todo para
	 * pasar la estructura map a un documento que tendr� el nombre de
	 * FichContPalabras.fichSalida
	 * 
	 * @throws IOException
	 */
	public void writeFileOut() throws IOException {
		pr = new PrintWriter(new FileWriter(fichSalida));

		ArrayList claves = new ArrayList(map.keySet());
		Collections.sort(claves);

		Iterator i = claves.iterator();
		while (i.hasNext()) {
			Object k = i.next();
			pr.println(k + " : " + map.get(k));
		}
		pr.close();

	}

}
