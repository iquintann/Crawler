package mainPackage;

import java.util.Map;
import java.util.TreeMap;

public class Fat {

	private Map<Integer, String> ordenadoId;
	private Map<String, Integer> ordenadoURL;

	public Fat() {
		ordenadoId = new TreeMap<Integer, String>();
		ordenadoURL = new TreeMap<String, Integer>();
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
