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
import java.util.Scanner;
import java.util.TreeMap;
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
		
		//Instanciamos la memoria Fat, indice invertido
		Fat fat = new Fat();

		System.out.println("Cargando ficheros-0%");
		//Mientras el directorio este con ficheros, lo recorremos y lo a�adimos a un listado
		while(!directoryQueue.isEmpty()) {
			
			//System.out.println("\n\n-->Obteniendo fichero del directorio " + directoryQueue.peek() + "...\n");

			lister.listingFilesAndDirectories(directoryQueue.remove(), fileList, directoryQueue);
			
			//System.out.println("\n-->Lista de directorios " + directoryQueue + "\n");


			//System.out.println("\n	->Leyendo ficheros... \n");
			//Una vez obtenido todos los ficheros del directorio dado, procesaremos la lista de los mismos
			for (int j = 0; j < fileList.size(); j++) {
				//System.out.println("		Fichero " + fileList.get(j) + ".");
				//procedemos a analizar fichero por fichero
				wc.wordCount(fileList.get(j),fat);
				
			}
			
			fileList.clear();

		}
		System.out.println("Cargando ficheros-100%");
		
		Map<String, Ocurrencia> mapOcurrencia = wc.getMapOcurrencia();
		//mostrarTerminosUrls(mapOcurrencia,fat);
		//lee pasamos una palabra, la buscamos en el tree y nos devuelve las URLs y un ranking
		
	    creacionRanking(mapOcurrencia,fat);
		
		//fat.mostrarFat();
		wc.writeFileOut();

	}
	
	
	
	

	private static void creacionRanking(Map<String, Ocurrencia> mapOcurrencia, Fat fat) {
		
		System.out.println("=============================================");
		System.out.print("Introduzca una palabra para buscar: ");
		
		Scanner in = new Scanner(System.in); 
        String palabraBuscar = in.nextLine();
		System.out.println("=============================================\n"
				+ "Mostrando donde aparece la palabra '"+ palabraBuscar+"'\n=============================================");
		
		if(mapOcurrencia.containsKey(palabraBuscar)) {
			Ocurrencia ocurrenciaPalabra = mapOcurrencia.get(palabraBuscar);
			Map<Integer, Integer> map = ocurrenciaPalabra.getArbolUrls();
			for(Entry<Integer, Integer> entry : map.entrySet()) {
				Integer key = entry.getKey();
				Integer value = entry.getValue();
				String url=fat.devolverUrl(key);
				System.out.println("Apariciones "+value+" URL: "+url);
				
			}
			
			//Map<String, Integer> treeOrdenado = (Map<String, Integer>) ocurrenciaPalabra.entriesSortedByValues(ocurrenciaPalabra.getArbolUrls());
			//ocurrenciaPalabra.mostrarURLs();
//			Map<Integer, Integer> arbolOrdenar = ocurrenciaPalabra.getArbolUrls();
//			
//			
//			Map<Integer, Integer> sortedMap = 
//					arbolOrdenar.entrySet().stream().
//				    sorted(Entry.comparingByValue()).
//				    collect(Collectors.toMap(Entry::getKey, Entry::getValue,
//				                             (e1, e2) -> e1, LinkedHashMap::new));
//			
//			Iterator it = sortedMap.entrySet().iterator();
//			ArrayList<Integer> keyList = new ArrayList<Integer>(sortedMap.keySet());		
//			Collection<Integer> values = sortedMap.values();
//			ArrayList<Integer> valueList = new ArrayList<Integer>(values);
//			
//			for(int i=keyList.size()-1;i>=0;i--) {
//				Map<Integer, String> idTree = fat.getIds();
//				String url = idTree.get(valueList.get(i));
//				System.out.println(keyList.get(i)+", "+url);
//				
//				
//			}
		}else {
			System.err.println("No se han encontrado coincidencias");
		}
		
	}

	private static void mostrarTerminosUrls(Map<String, Ocurrencia> map, Fat fat) {
		for(Entry<String, Ocurrencia> entry : map.entrySet()) {
		  String key = entry.getKey();
		  Ocurrencia value = entry.getValue();

		  System.out.print("________________________________________\n\n"+key+" ");
		  value.mostrarURLs(fat);
		}
		
	}

}
