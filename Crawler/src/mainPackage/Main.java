package mainPackage;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;


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
		wc.writeFileOut();

	}

}
