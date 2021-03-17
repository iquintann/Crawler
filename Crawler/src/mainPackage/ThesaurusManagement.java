package mainPackage;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

public class ThesaurusManagement implements Serializable {

	private HashMap<String, Integer> stopwordsMap;
	private HashMap<String, List<String>> thesaurusMap;
	private List<List<String>> wordMat;
	private String stopwordsFile;
	private String thesaurusFile;

	public HashMap<String, Integer> getStopWords() {
		return stopwordsMap;
	}

	public HashMap<String, List<String>> getThesaurus() {
		return thesaurusMap;
	}

	public ThesaurusManagement() {
		// Inicializacion de variables
		stopwordsFile = "stopwords_es.txt";
		thesaurusFile = "Thesaurus_es_ES.txt";
		stopwordsMap = new HashMap<String, Integer>();
		thesaurusMap = new HashMap<String, List<String>>();
		wordMat = new ArrayList<List<String>>();

//		// Reading stopwords
//		readingStopWords();
//
//		// Reading thesaurus
//		readingTesaurus();
	}

	public void readingTesaurus() {
		String linea;
		String arrayWords[] = null;
		FileReader fileReader;
		try {
			fileReader = new FileReader(thesaurusFile);
			BufferedReader flujoLecturaThesaurus = new BufferedReader(fileReader);

			while ((linea = flujoLecturaThesaurus.readLine()) != null) {
				if (!linea.contains("# ")) {

					// Splitting a line
					arrayWords = linea.split(";");
					ArrayList<String> listaSinonimos = new ArrayList<String>();
					for (int i = 0; i < arrayWords.length; i++) {
						// System.out.print(arrayWords[i]);
						listaSinonimos.add(arrayWords[i]);
					}
					thesaurusMap.put(listaSinonimos.get(0), listaSinonimos);
				} else {
					// System.out.println("Linea de comentario ignorada: "+ linea);
				}

			}
			flujoLecturaThesaurus.close();
		} catch (FileNotFoundException e) {
			System.out.println("No se ha podido encontrar un tesaurus con nombre: " + thesaurusFile);
		} catch (IOException e) {

		}

	}

	public void readingStopWords() {
		String linea;
		String arrayWords[] = null;
		BufferedReader flujoLectura;
		try {
			flujoLectura = new BufferedReader(new FileReader(stopwordsFile));
			while ((linea = flujoLectura.readLine()) != null) {
				arrayWords = linea.split(" ");

				for (int i = 0; i < arrayWords.length; i++) {
					stopwordsMap.put(arrayWords[i], null);
				}

			}

			flujoLectura.close();
		} catch (IOException e) {
			System.out.println("No se ha podido encontrar un diccionario de stopwords con el nombre: " + stopwordsFile);
		}

	}

	public String addImput() {
		Scanner myObj = new Scanner(System.in);
		System.out.println("Enter a key: ");

		String input = myObj.nextLine();
		System.out.println("Input: " + input);
		return input;
	}

	public void buildDictionary(String key) {

	}

	public List<List<String>> getWordMat() {
		return wordMat;
	}

	public void setWordMat(List<List<String>> wordMat) {
		this.wordMat = wordMat;
	}

//	@Override
//	public String toString() {
//		return "ThesaurusManagement [stopwords=" + stopwordsMap + ", tsDictionary=" + thesaurusMap + ", wordMat="
//				+ wordMat + "]";
//	}

	public ThesaurusManagement(String dato) {
		try {
			ObjectInputStream leyendoFichero = new ObjectInputStream(new FileInputStream("thesaurus.dat"));
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		return this.thesaurusMap.toString();
	}

	public static void showTesaurus(ThesaurusManagement ts) {
		HashMap<String, List<String>> thesaurus = ts.getThesaurus();
		System.out.println("---Thesaurus con sinonimos---");
		Iterator it = thesaurus.entrySet().iterator();
		while (it.hasNext()) {
			HashMap.Entry pair = (HashMap.Entry) it.next();
			System.out.println(pair.getKey() + ", sinonimos --> " + pair.getValue());
			it.remove(); // avoids a ConcurrentModificationException
		}
		System.out.println();
	}

	public static void showStopWords(ThesaurusManagement ts) {
		HashMap<String, Integer> thesaurus = ts.getStopWords();
		System.out.println("---StopWords---");
		Iterator it = thesaurus.entrySet().iterator();
		while (it.hasNext()) {
			HashMap.Entry pair = (HashMap.Entry) it.next();
			System.out.println(pair.getKey());
			it.remove(); // avoids a ConcurrentModificationException
		}
		System.out.println();
	}

	public static void main(String[] args) {

		ThesaurusManagement ts = new ThesaurusManagement();
		// ts.addImput();
		showStopWords(ts);
		showTesaurus(ts);

	}

}
