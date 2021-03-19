package mainPackage;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

import javax.crypto.SecretKey;

import com.sun.source.doctree.SerialDataTree;

public class Fat implements Serializable {

	private String directory;
	private Map<Integer, String> ordenadoId;
	private Map<String, Integer> ordenadoURL;
	
	/**
	 * Constructor del objeto fat
	 * @param <String> directorio que deseamos crawlear
	 */
	public Fat(String directory) {
		this.directory = directory;
		ordenadoId = new TreeMap<Integer, String>();
		ordenadoURL = new TreeMap<String, Integer>();
	}

	public Map<Integer, String> getOrdenadoId() {
		return ordenadoId;
	}

	public void setOrdenadoId(Map<Integer, String> ordenadoId) {
		this.ordenadoId = ordenadoId;
	}

	public Map<String, Integer> getOrdenadoURL() {
		return ordenadoURL;
	}

	public void setOrdenadoURL(Map<String, Integer> ordenadoURL) {
		this.ordenadoURL = ordenadoURL;
	}

	public String getDirectory() {
		return directory;
	}

	public void setDirectory(String directory) {
		this.directory = directory;
	}

	public Integer insertarURL(String URL) {
		if (ordenadoURL.containsKey(URL)) {
			return ordenadoURL.get(URL);
		} else {
			Integer idElemento = new Integer(ordenadoURL.size());
			//System.out.println("Nuevo elemento con ID " + idElemento);
			ordenadoURL.put(URL, idElemento);
			ordenadoId.put(idElemento, URL);

			return idElemento;
		}
	}

	public String devolverUrl(Integer id) {
		return ordenadoId.get(id);
	}

	public Map<Integer, String> getIds() {
		return ordenadoId;
	}

	public void mostrarFat() {
		System.out.println("Mostrado arboles el 1º es el URL y el 2º de IDs");
		System.out.println("----------------IDS------------------");
		for (Map.Entry<String, Integer> entry : ordenadoURL.entrySet()) {
			String key = entry.getKey();
			Integer value = entry.getValue();
			System.out.println(key + " => " + value);
		}
		System.out.println("----------------IDS------------------");
		for (Map.Entry<Integer, String> entry : ordenadoId.entrySet()) {
			Integer key = entry.getKey();
			String value = entry.getValue();

			System.out.println(key + " => " + value);
		}

	}

}
