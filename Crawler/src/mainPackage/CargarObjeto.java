package mainPackage;
/*
 * CargarObjeto.java
 * Carga un objeto serializable en memoria previamente salvado en un fichero
 * (i) Félix R. Rodríguez, EPCC, Universidad de Extremadura, 2009
 * http://madiba.unex.es/felix
 */

import java.io.*;
import java.util.*;

public class CargarObjeto {
        public static void main (String args[]) {
                try {
                        FileInputStream fis = new FileInputStream("h.ser");
                        ObjectInputStream ois = new ObjectInputStream(fis);
                        Hashtable h = (Hashtable) ois.readObject();
                        System.out.println(h.toString());
                }
                catch (Exception e) { System.out.println(e); }
        }
}
