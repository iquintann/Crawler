package mainPackage;
/*
 * ListIt.java
 * Imprime caracter�sticas de ficheros textuales
 * (c) F�lix R. Rodr�guez, EPCC, Universidad de Extremadura, 2009
 * http://madiba.unex.es/felix
 */

import java.io.*;
import java.util.List;
import java.util.Queue;

class Lister {

	/**
	 * 
	 * Función que lee un fichero dado su nombre.
	 * 
	 * @param fileName nombre de fichero que se va a leer.
	 * @return true si ha leeido el fichero correctamente, false si no ha leido el fichero.
	 * @throws Exception
	 */
	public boolean readingFiles(String fileName) throws Exception {

		File file = new File(fileName);
		if (!file.exists() || !file.canRead()) {
			System.out.println("No puedo leer " + file);
			return false;
		}else {
			try {
				FileReader fr = new FileReader(file);
				BufferedReader br = new BufferedReader(fr);
				String linea;

				while ((linea = br.readLine()) != null)
					System.out.println(linea);

				return true;
			} catch (FileNotFoundException fnfe) {
				System.out.println("Fichero desaparecido en combate  ;-)");
			}

			return false;

		}
	}

	
	/**
	 * 
	 * Método que devuelve una lista de ficheros y una cola de directorios
	 * en función
	 * 
	 * @param directory
	 * @param fileList
	 * @param directoryQueue
	 * @return
	 */
	public boolean listingFilesAndDirectories(String directory, List<String> fileList, Queue<String>directoryQueue) {
		File file = new File(directory);

		//si es un directorio no lo leemos, navegamos por el para encontrar los documentos que queremos leer
		if (file.isDirectory()) {
			String[] fileAndDirectoriesList = file.list();
			for (int i = 0; i < fileAndDirectoriesList.length; i++) {
				
				String path = directory + "/" + fileAndDirectoriesList[i];
				File fileAux = new File(path);

				//Dependiendo si es un directorio o un fichero lo a�adiremos a la cola o a la lista
				if (!fileAux.isDirectory())
					fileList.add(path);
				else
					directoryQueue.add(path);

				System.out.println(fileAndDirectoriesList[i]);
			}

			return true;
		}
		
		if(file.canRead()) {
			fileList.add(directory);
			return true;
		}
		
		
		
		
		return false;
	}

}
