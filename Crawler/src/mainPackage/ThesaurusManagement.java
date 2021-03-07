package mainPackage;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

public class ThesaurusManagement {

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
		//Inicializacion de variables
		stopwordsFile = "stopwords_es.txt";
		thesaurusFile = "Thesaurus_es_ES.txt";
		stopwordsMap = new HashMap<String, Integer>();
		thesaurusMap = new HashMap<String, List<String>>();
		wordMat = new ArrayList<List<String>>();

		try {
			// Reading stopwords
			readingStopWords();

			// Reading thesaurus
			readingTesaurus();
			
		} catch (IOException e) {
			System.out.println("Error to load stopwords and/or thesaurus.");
			e.printStackTrace();
		}
	}

	private void readingTesaurus() throws IOException {
		String linea;
		String arrayWords[] = null;
		BufferedReader flujoLecturaThesaurus = new BufferedReader(new FileReader(thesaurusFile));

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
		
	}

	private void readingStopWords() throws IOException {
		String linea;
		String arrayWords[] = null;
		BufferedReader flujoLectura = new BufferedReader(new FileReader(stopwordsFile));

		while ((linea = flujoLectura.readLine()) != null) {
			arrayWords = linea.split(" ");

			for (int i = 0; i < arrayWords.length; i++) {
				stopwordsMap.put(arrayWords[i], null);
			}

		}

		flujoLectura.close();
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

	@Override
	public String toString() {
		return "ThesaurusManagement [stopwords=" + stopwordsMap + ", tsDictionary=" + thesaurusMap + ", wordMat="
				+ wordMat + "]";
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
	
	public static void showStopWords (ThesaurusManagement ts) {
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
