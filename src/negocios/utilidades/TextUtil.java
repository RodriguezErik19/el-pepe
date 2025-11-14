package negocios.utilidades;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Utilidades para normalización y tokenización de texto.
 */
public class TextUtil {

    public static String normalizar(String s) {
        if (s == null) 
            return "";
        String t = s.trim().toLowerCase();
        t = Normalizer.normalize(t, Normalizer.Form.NFD).replaceAll("\\p{M}", "");
        // reemplazar cualquier caracter no alfanumérico por espacio
        t = t.replaceAll("[^a-z0-9]+", " ");
        return t.trim();
    }

    /**
     * Devuelve tokens únicos (preserva orden de aparición)
     */
    public static List<String> tokens(String texto) {
        String norm = normalizar(texto);
        if (norm.isEmpty()) 
            return new ArrayList<>();
        String[] parts = norm.split("\\s+");
        Set<String> set = new LinkedHashSet<>();
        for (String p : parts) {
            if (p == null || p.isEmpty()) continue;
            set.add(p);
        }
        return new ArrayList<>(set);
    }
}
