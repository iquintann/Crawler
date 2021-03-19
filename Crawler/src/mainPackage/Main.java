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

	public static Fat fat;
	public static Map<String, Ocurrencia> mapOcurrencia;
	public static FichContPalabras wc;

	public static void main(String[] args) throws Exception {

		// Obtenemos el path de un directorio o fichero
		String path = args[0];
		if (args.length != 1) {
			System.out.println("Error de argumentos");
			return;
		}
		
		//Función que lee un fichero dado su nombre
		Lister lister = new Lister();
		// Definimos un arraylista para los ficheros que vamos a encontrar
		ArrayList<String> fileList = new ArrayList<String>();
		// Definimos una cola para los directorios que vamos a navegar
		Queue<String> directoryQueue = new LinkedList<String>();
		directoryQueue.add(path);
		// Cargamos el stopwords
		ThesaurusManagement tesaurus = new ThesaurusManagement();
		// Creamos un objeto que nos ayudar� a llamar los m�todos de contar las palabras
		// de un fichero
		wc = new FichContPalabras(tesaurus.getStopWords());
		// Instanciamos la memoria Fat, indice invertido
		fat = new Fat(path);

		boolean cargarTodo = deserializing(path);// Deserializamos los documentos sino existen devuelve false
		if (!cargarTodo) {
			creandoMapaDirecciones(directoryQueue,fileList,lister);
		} else {
			System.out.println("Información cargada desde ficheros guardados.");
		}
		mapOcurrencia = wc.getMapOcurrencia();

		busquedaRanking();
		serializing(path);

	}

	/**
	 * 
	 * @param <String> Cola de directorios
	 * @param <String> Lista de ficheros
	 * @param <Lister> lister
	 */
	private static void creandoMapaDirecciones(Queue<String> directoryQueue, ArrayList<String> fileList, Lister lister) {
		System.err.println("No se han encontrado ficheros previos, creando nuevo Tesaurus y memoria Fat.");
		System.out.println("\n---Cargando ficheros-0%---\n");
		while (!directoryQueue.isEmpty()) {
			lister.listingFilesAndDirectories(directoryQueue.remove(), fileList, directoryQueue);
			for (int j = 0; j < fileList.size(); j++) {
				try {
					wc.wordCount(fileList.get(j), fat);
				} catch (IOException e) {
					e.printStackTrace();
				} // analisis individual de fichero1
			}
			fileList.clear();
		}
		System.out.println("\n---Cargando ficheros-100%---\n");
		
	}

	/**
	 * Busqueda de un termino y las url de donde aparece dicho termino
	 */
	private static void busquedaRanking() {
		Scanner sc;
		String str;
		boolean fin = false;

		do {
			System.out.println("____________________________");
			System.out.println("1: Realizar una búsqueda.");
			System.out.println("2: Salir y guardar.");
			System.out.print("Elija una opción: ");
			sc = new Scanner(System.in);
			str = sc.nextLine();

			switch (str) {
			case "1":
				System.out.print("Introduzca una palabra que desee buscar: ");
				str = new Scanner(System.in).nextLine();
				System.out.println("Buscando la palabra '" + str + "'\n");
				busquedaRanking(str);
				break;
			case "2":
				System.out.println("Fin de la ejecución...");
				fin = true;
				break;
			default:
				System.err.println("Seleccione entre 1 o 2");
				break;
			}
		} while (!fin);
		sc.close();
	}

	/**
	 * Serializamos las estructuras necesarias para no tener que analizar los
	 * archivos de nuevo
	 * 
	 * @param dir path la que analizaremos los archivos
	 */
	private static void serializing(String dir) {
		try {
			FileOutputStream fileOutMapOcurrencia = new FileOutputStream("mapOcurrencia");
			FileOutputStream fileOutFat = new FileOutputStream("Fat");

			ObjectOutputStream outMapOcurrencia = new ObjectOutputStream(fileOutMapOcurrencia);
			ObjectOutputStream outFat = new ObjectOutputStream(fileOutFat);

			outMapOcurrencia.writeObject(mapOcurrencia);
			outFat.writeObject(fat);

			outMapOcurrencia.close();
			outFat.close();

			fileOutMapOcurrencia.close();
			fileOutFat.close();

			System.out.printf("Serialización realizada.");
		} catch (IOException i) {
			i.printStackTrace();
		}
	}

	/**
	 * Deserializamos los documentos sino existen devuelve false
	 * 
	 * @param String Directorio
	 * @return false sino existen los documentos serializados y true si existen
	 */
	private static boolean deserializing(String newDir) {

		File file1 = new File("mapOcurrencia");
		File file2 = new File("Fat");

		if (!file1.exists() || !file2.exists())
			return false;

		try {
			FileInputStream fileInOcur = new FileInputStream("mapOcurrencia");
			FileInputStream fileInFat = new FileInputStream("Fat");
			ObjectInputStream inOcur = new ObjectInputStream(fileInOcur);
			ObjectInputStream inFat = new ObjectInputStream(fileInFat);

			fat = ((Fat) inFat.readObject());
			if (!fat.getDirectory().equals(newDir))
				return false; // Si es nuevo directorio no valdran estos docs
			wc.setMapOcurrencia((Map<String, Ocurrencia>) inOcur.readObject());

			inOcur.close();
			inFat.close();
			fileInOcur.close();

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
	
	/**
	 * Busca una palabra introducida por parametro de entrada
	 * 
	 * @param palabra a Buscar
	 */
	private static void busquedaRanking(String palabraBuscar) {

		if (mapOcurrencia.containsKey(palabraBuscar)) {
			Ocurrencia ocurrencia = mapOcurrencia.get(palabraBuscar);
			System.out.println("---");
			ocurrencia.mostrarURLsMayoraMenor(fat);
			System.out.println("---");
		} else {
			System.out.println("Palabra no encontrada.");
		}

	}

}
