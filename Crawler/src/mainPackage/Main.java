package mainPackage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
			System.out.println("Error de argumentos");
			return;
		}

		Lister lister = new Lister();

		// Definimos un arraylista para los ficheros que vamos a encontrar
		ArrayList<String> fileList = new ArrayList<String>();

		// Definimos una cola para los directorios que vamos a navegar
		Queue<String> directoryQueue = new LinkedList<String>();
		directoryQueue.add(path);

		/**
		 * 1, Existe tesaurus Si- lo cargamos en la estructura de tesaurus . No-
		 * cargamos * stopwords y creamos nuevo tesaurus 2, Analizamos los directorios
		 * 2.1, * mientras analizamos el directorio vamos creando el indice invertido 3,
		 * Una vez finalizado el analisis -Guardamos el tesaurus -Guardamos el indice
		 * invertido
		 */

		// 1. Creacion del tesaurus
		ThesaurusManagement tesaurus = new ThesaurusManagement();
		tesaurus.readingTesaurus();
		tesaurus.readingStopWords();

		// Creamos un objeto que nos ayudar� a llamar los m�todos de contar las palabras
		// de un fichero
		FichContPalabras elementoContarPalabras = new FichContPalabras(fileOut, tesaurus.getStopWords());

		// Instanciamos la memoria Fat, indice invertido
		Fat fat = new Fat();
		// fat.cargarFat()

		// boolean cargarTodo = deserializing(elementoContarPalabras, fat, args[0]);
		boolean cargarTodo = false;

		if (!cargarTodo) {

			// Analizado ficheros
			System.out.println("\n---Analizando ficheros-0%---\n");
			while (!directoryQueue.isEmpty()) {
				lister.listingFilesAndDirectories(directoryQueue.remove(), fileList, directoryQueue);
				for (int j = 0; j < fileList.size(); j++) {
					elementoContarPalabras.wordCount(fileList.get(j), fat);
				}
				fileList.clear();
			}
			System.out.println("\n---Analizando ficheros-100%---\n");
		} else {
			System.out.println("Información cargada de fichero.");
		}

		Map<String, Ocurrencia> mapOcurrencia = elementoContarPalabras.getMapOcurrencia();
		ejecucion(mapOcurrencia, fat, args[0]);

	}

	private static boolean deserializing(FichContPalabras wc, Fat fat, String newDir) {

		File file1 = new File("mapOcurrencia");
		File file2 = new File("FatOrdenadoId");
		File file3 = new File("FatOrdenadoURL");
		File file4 = new File("directorio");

		if (!file1.exists() || !file2.exists() || !file3.exists() || !file4.exists())
			return false;

		try {

			FileInputStream fileInOcur = new FileInputStream("mapOcurrencia");
			FileInputStream fileInFatOrdenadoId = new FileInputStream("FatOrdenadoId");
			FileInputStream fileInFatOrdenadoURL = new FileInputStream("FatOrdenadoURL");
			FileInputStream fileInDir = new FileInputStream("directorio");

			ObjectInputStream inOcur = new ObjectInputStream(fileInOcur);
			ObjectInputStream inFatId = new ObjectInputStream(fileInFatOrdenadoId);
			ObjectInputStream inFatURL = new ObjectInputStream(fileInFatOrdenadoURL);
			ObjectInputStream inDir = new ObjectInputStream(fileInDir);

			String directorio = (String) inDir.readObject();

			// Si el directorio guardado es distinto que el dir actual,
			// debemos de volver a cargar los datos.
			if (!directorio.equals(newDir))
				return false;

			wc.setMapOcurrencia((Map<String, Ocurrencia>) inOcur.readObject());
			fat.setOrdenadoId((Map<Integer, String>) inFatId.readObject());
			fat.setOrdenadoURL((Map<String, Integer>) inFatURL.readObject());

			inOcur.close();
			inFatId.close();
			inFatURL.close();

			fileInOcur.close();
			fileInFatOrdenadoId.close();
			fileInFatOrdenadoURL.close();

		} catch (IOException i) {
			i.printStackTrace();
			return false;
		} catch (ClassNotFoundException c) {
			System.out.println("El mapa de ocurrencia y la fat no existía previamente.");
			c.printStackTrace();
			return false;
		}
		return true;
	}

	private static void ejecucion(Map<String, Ocurrencia> mapOcurrencia, Fat fat, String dir) {

		Scanner sc;
		String str;
		boolean fin = false;

		do {
			System.out.println("Elija una opción:");
			System.out.println("1: Realizar una búsqueda.");
			System.out.println("2: Salir y guardar.");

			sc = new Scanner(System.in);
			str = sc.nextLine();

			switch (Integer.valueOf(str)) {
			case 1:
				System.out.print("Introduzca una palabra que desee buscar: ");
				sc = new Scanner(System.in);
				str = sc.nextLine();
				System.out.println("Buscando la palabra '" + str + "'");

				creacionRanking(mapOcurrencia, fat, str);

				break;
			case 2:
				System.out.println("Fin de la ejecución...");
				serializing(mapOcurrencia, fat, dir);
				fin = true;

				break;
			default:
				System.out.println("Error, fin de la ejecución...");
				fin = true;

			}

		} while (!fin);

	}

	private static void serializing(Map<String, Ocurrencia> mapOcurrencia, Fat fat, String dir) {
		try {
			FileOutputStream fileOutMapOcurrencia = new FileOutputStream("mapOcurrencia");
			FileOutputStream fileOutFatId = new FileOutputStream("FatOrdenadoId");
			FileOutputStream fileOutFatURL = new FileOutputStream("FatOrdenadoURL");
			FileOutputStream fileOutDir = new FileOutputStream("directorio");

			ObjectOutputStream outMapOcurrencia = new ObjectOutputStream(fileOutMapOcurrencia);
			ObjectOutputStream outFatId = new ObjectOutputStream(fileOutFatId);
			ObjectOutputStream outFatURL = new ObjectOutputStream(fileOutFatURL);
			ObjectOutputStream outDir = new ObjectOutputStream(fileOutDir);

			outMapOcurrencia.writeObject(mapOcurrencia);
			outFatId.writeObject(fat.getOrdenadoId());
			outFatURL.writeObject(fat.getOrdenadoURL());
			outDir.writeObject(dir);

			outMapOcurrencia.close();
			outFatId.close();
			outFatURL.close();
			outDir.close();

			fileOutMapOcurrencia.close();
			fileOutFatId.close();
			fileOutFatURL.close();
			fileOutDir.close();

			System.out.printf("Serialización realizada.");
		} catch (IOException i) {
			i.printStackTrace();
		}
	}

	private static void creacionRanking(Map<String, Ocurrencia> mapOcurrencia, Fat fat, String palabraBuscar) {

//		Ocurrencia ocurrenciaPalabra = mapOcurrencia.get(palabraBuscar);

		// Utilizado para ordenar el Map
//		ValueComparator bvc = new ValueComparator(map);
//		TreeMap<Integer, Integer> sorted_map = new TreeMap<Integer, Integer>(bvc);
//		sorted_map.putAll(map);
		if (mapOcurrencia.containsKey(palabraBuscar)) {

			Ocurrencia ocurrencia = mapOcurrencia.get(palabraBuscar);
			System.out.println("---");
			ocurrencia.mostrarURLs(fat);
			System.out.println("---");
		} else {
			System.out.println("Palabra no encontrada.");
		}

	}

	private static void mostrarTerminosUrls(Map<String, Ocurrencia> map, Fat fat) {
		for (Entry<String, Ocurrencia> entry : map.entrySet()) {
			String key = entry.getKey();
			Ocurrencia value = entry.getValue();

			System.out.print("________________________________________\n\n" + key + " ");
			value.mostrarURLs(fat);
		}

	}

}
