package mainPackage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.stream.Collectors;




public class Main {

	public static void main(String[] args) throws Exception {

		// Obtenemos el path de un directorio o fichero
		String path = args[0];
		String fileOut = args[1];

		if (args.length != 2) {
			System.out.println("Argumentos inválidos");
			return;
		}

		

		Lister lister = new Lister();
		
		//Definimos un arraylista para los ficheros que vamos a encontrar
		ArrayList<String> fileList = new ArrayList<String>();
		
		//Definimos una cola para los directorios que vamos a navegar
		Queue<String> directoryQueue = new LinkedList<String>();
		directoryQueue.add(path);
	
		//Cargamos el stopwords
		ThesaurusManagement tesaurus = new ThesaurusManagement();
		
		
		//Creamos un objeto que nos ayudar� a llamar los m�todos de contar las palabras de un fichero
		FichContPalabras wc = new FichContPalabras(fileOut,tesaurus.getStopWords());

		//Mientras el directorio este con ficheros, lo recorremos y lo a�adimos a un listado
		while(!directoryQueue.isEmpty()) {
			
			System.out.println("\n\n-->Obteniendo fichero del directorio " + directoryQueue.peek() + "...\n");

			lister.listingFilesAndDirectories(directoryQueue.remove(), fileList, directoryQueue);
			
			System.out.println("\n-->Lista de directorios " + directoryQueue + "\n");


			System.out.println("\n	->Leyendo ficheros... \n");
			//Una vez obtenido todos los ficheros del directorio dado, procesaremos la lista de los mismos
			for (int j = 0; j < fileList.size(); j++) {
				System.out.println("		Fichero " + fileList.get(j) + ".");
				//procedemos a analizar fichero por fichero
				wc.wordCount(fileList.get(j));
				
			}
			
			fileList.clear();

		}
		Map<String, Ocurrencia> mapOcurrencia = wc.getMapOcurrencia();
		mostrarTerminosUrls(mapOcurrencia);
		//lee pasamos una palabra, la buscamos en el tree y nos devuelve las URLs y un ranking
		//TODO meter dentro del metodo "creacionRanking"
		String palabraBuscar="urna";
	    creacionRanking(palabraBuscar, mapOcurrencia);
		
		
		wc.writeFileOut();

	}
	
	
	
	

	private static void creacionRanking(String palabraBuscar, Map<String, Ocurrencia> mapOcurrencia) {
		System.out.println("=============================================\n"
				+ "Mostrando donde aparece la palabra '"+ palabraBuscar+"'\n=============================================");
		
		if(mapOcurrencia.containsKey(palabraBuscar)) {
			Ocurrencia ocurrenciaPalabra = mapOcurrencia.get(palabraBuscar);
			//Map<String, Integer> treeOrdenado = (Map<String, Integer>) ocurrenciaPalabra.entriesSortedByValues(ocurrenciaPalabra.getArbolUrls());
			//ocurrenciaPalabra.mostrarURLs();
			Map<String, Integer> arbolOrdenar = ocurrenciaPalabra.getArbolUrls();
			
			
			Map<String, Integer> sortedMap = 
					arbolOrdenar.entrySet().stream().
				    sorted(Entry.comparingByValue()).
				    collect(Collectors.toMap(Entry::getKey, Entry::getValue,
				                             (e1, e2) -> e1, LinkedHashMap::new));
			
			Iterator it = sortedMap.entrySet().iterator();
			ArrayList<String> keyList = new ArrayList<String>(sortedMap.keySet());		
			Collection<Integer> values = sortedMap.values();
			ArrayList<Integer> valueList = new ArrayList<Integer>(values);
			
			for(int i=keyList.size()-1;i>=0;i--) {
				System.out.println(keyList.get(i)+", "+valueList.get(i));
			}
		}
		
	}





	private static void mostrarTerminosUrls(Map<String, Ocurrencia> map) {
		for(Entry<String, Ocurrencia> entry : map.entrySet()) {
		  String key = entry.getKey();
		  Ocurrencia value = entry.getValue();

		  System.out.print("________________________________________\n\n"+key+" ");
		  value.mostrarURLs();
		}
		
	}

}
