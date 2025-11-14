package datos;

import negocios.utilidades.listaDoble.ListaDoble;
import negocios.utilidades.listaDoble.PosicionIlegalException;

import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;

import java.util.Map;
import java.util.HashMap;

public class UniversidadDAO implements IUniversidadDAO{

    // Cache para evitar relecturas repetidas por familia: familia -> (id -> Producto)
    private final Map<FamiliaTipo, Map<Integer, Producto>> cache = new HashMap<>();

    /**
     * Recuperar un Producto por id y familia. Intenta usar cache; si no existe, carga
     * el fichero de la familia en cache y devuelve la instancia si existe.
     */
    public Producto obtenerProductoPorIdYFamilia(int id, FamiliaTipo familia) throws PosicionIlegalException {
        // Primero determinar la ruta base (producción o pruebas) según configuración
        String baseDir = obtenerBaseDir();
        String filename = null;
        switch (familia) {
            case MUSICALES: 
                filename = "ProductosMusicales.txt";
                break;
            case DEPORTIVOS: 
                filename = "ProductosDeportivos.txt"; 
                break;
            case ESCENICOS: 
                filename = "ProductosEscenicos.txt"; 
                break;
            default: filename = null; break;
        }

        // Si ya tenemos cache para la familia, devolver directamente si existe
        if (familia != null && cache.containsKey(familia)) {
            Map<Integer, Producto> famMap = cache.get(familia);
            if (famMap != null && famMap.containsKey(id)) return famMap.get(id);
        }

        if (filename != null) {
            File famFile = new File(baseDir, filename);
            if (famFile.exists()) {
                // Si no existe cache para la familia, cargar todo el fichero en cache
                if (!cache.containsKey(familia)) {
                    Map<Integer, Producto> famMap = new HashMap<>();
                    try (BufferedReader br = new BufferedReader(new FileReader(famFile))) {
                        String linea;
                        while ((linea = br.readLine()) != null) {
                            if (linea.trim().isEmpty()) continue;
                            String[] partes = linea.split(",");
                            String sId = partes[0].replace("\"", "").trim();
                            int fid;
                            try { fid = Integer.parseInt(sId); } catch (Exception e) { continue; }
                            Familia famObj = new Familia(familia.ordinal()+1, familia.name(), familia.name());
                            boolean prestado = false;
                            if (familia == FamiliaTipo.MUSICALES) {
                                String nombre = partes.length>1 ? partes[1].replace("\"", "").trim() : "";
                                String descripcion = partes.length>2 ? partes[2].replace("\"", "").trim() : "";
                                String material = partes.length>3 ? partes[3].replace("\"", "").trim() : "";
                                String rango = partes.length>4 ? partes[4].replace("\"", "").trim() : "";
                                String tipoInst = partes.length>5 ? partes[5].replace("\"", "").trim() : "";
                                if (partes.length>6) { try { prestado = Boolean.parseBoolean(partes[6].replace("\"", "").trim()); } catch(Exception e) { prestado=false; } }
                                famMap.put(fid, new ProductosMusicales(fid, nombre, descripcion, material, rango, tipoInst, famObj, prestado));
                            } else if (familia == FamiliaTipo.DEPORTIVOS) {
                                String nombre = partes.length>1 ? partes[1].replace("\"", "").trim() : "";
                                String descripcion = partes.length>2 ? partes[2].replace("\"", "").trim() : "";
                                String tipoDeporte = partes.length>3 ? partes[3].replace("\"", "").trim() : "";
                                String material = partes.length>4 ? partes[4].replace("\"", "").trim() : "";
                                String rango = partes.length>5 ? partes[5].replace("\"", "").trim() : "";
                                if (partes.length>6) { try { prestado = Boolean.parseBoolean(partes[6].replace("\"", "").trim()); } catch(Exception e) { prestado=false; } }
                                famMap.put(fid, new ProductosDeportivos(fid, nombre, descripcion, tipoDeporte, material, rango, famObj, prestado));
                            } else if (familia == FamiliaTipo.ESCENICOS) {
                                String nombre = partes.length>1 ? partes[1].replace("\"", "").trim() : "";
                                String descripcion = partes.length>2 ? partes[2].replace("\"", "").trim() : "";
                                String material = partes.length>3 ? partes[3].replace("\"", "").trim() : "";
                                String rango = partes.length>4 ? partes[4].replace("\"", "").trim() : "";
                                String tallas = partes.length>5 ? partes[5].replace("\"", "").trim() : "";
                                String genero = partes.length>6 ? partes[6].replace("\"", "").trim() : "";
                                String tipoEvento = partes.length>7 ? partes[7].replace("\"", "").trim() : "";
                                if (partes.length>8) { try { prestado = Boolean.parseBoolean(partes[8].replace("\"", "").trim()); } catch(Exception e) { prestado=false; } }
                                famMap.put(fid, new ProductosEscenicos(fid, nombre, descripcion, material, rango, tallas, genero, tipoEvento, famObj, prestado));
                            }
                        }
                    } catch (IOException e) {
                        // si hay error leyendo el archivo de familia, se continúa con fallback
                    }
                    cache.put(familia, famMap);
                }
                Map<Integer, Producto> famCache = cache.get(familia);
                if (famCache != null && famCache.containsKey(id)) return famCache.get(id);
            }
        }

        // Fallback: leer el archivo legacy 'recursos.txt' para compatibilidad histórica
    File f = new File("src/datos/recursos.txt");
        if (!f.exists()) {
            throw new PosicionIlegalException();
        }
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                // Simple parser: quitar comillas y separar por comas
                String[] partes = linea.split(",");
                if (partes.length < 2) continue;
                String sId = partes[0].replace("\"", "").trim();
                int fid;
                try { fid = Integer.parseInt(sId); } catch (Exception e) { continue; }
                if (fid != id) continue;
                String nombre = partes[1].replace("\"", "").trim();
                boolean prestado = false;
                if (partes.length > 2) {
                    String sPrest = partes[2].replace("\"", "").trim();
                    try { prestado = Boolean.parseBoolean(sPrest); } catch (Exception e) { prestado = false; }
                }
                // Construir instancia según familia (con campos vacíos)
                Familia famObj = new Familia(familia.ordinal()+1, familia.name(), familia.name());
                switch (familia) {
                    case MUSICALES:
                        return new ProductosMusicales(fid, nombre, "", "", "", "", famObj, prestado);
                    case DEPORTIVOS:
                        return new ProductosDeportivos(fid, nombre, "", "", "", "", famObj, prestado);
                    case ESCENICOS:
                        return new ProductosEscenicos(fid, nombre, "", "", "", "", "", "", famObj, prestado);
                    default:
                        return null;
                }
            }
        } catch (IOException e) {
            throw new PosicionIlegalException();
        }
        throw new PosicionIlegalException();
    }

    // Helper: determina la ruta base para archivos (producción o pruebas) usando Configuracion
    private String obtenerBaseDir() {
        String prodDir = "src/datos/archivosProduccion";
        String defaultTestDir = "src/miTest/archivos";
        String baseDir = prodDir;
        try {
            boolean modoPruebas = Configuracion.getBoolean("modo.pruebas");
            if (modoPruebas) {
                String cfgTest = Configuracion.get("ruta.pruebas");
                if (cfgTest != null && !cfgTest.trim().isEmpty()) {
                    baseDir = "src/" + cfgTest.replaceFirst("^/", "");
                } else {
                    baseDir = defaultTestDir;
                }
            } else {
                String cfgProd = Configuracion.get("ruta.produccion");
                if (cfgProd != null && !cfgProd.trim().isEmpty()) {
                    baseDir = "src/" + cfgProd.replaceFirst("^/", "");
                } else {
                    baseDir = prodDir;
                }
            }
        } catch (Exception ex) {
            baseDir = prodDir;
        }
        File maybeTest = new File(defaultTestDir);
        if (!(new File(baseDir)).exists() && maybeTest.exists()) baseDir = defaultTestDir;
        return baseDir;
    }

    /**
     * Expose resolved base directory used by DAO. Public helper for callers
     * that want to display or inspect the effective working directory.
     */
    public String getBaseDir() {
        return obtenerBaseDir();
    }

    private String obtenerRutaPrestamos() {
        return obtenerBaseDir() + "/prestamos.txt";
    }

    // Nota: la ruta de préstamos es dinámica según configuración; usamos helper

    /** Guarda un préstamo en formato CSV: productoId,familia,estudianteId,fechaPrestamo,fechaDevolucion */
    public void guardarPrestamo(Prestamo p) throws IOException {
        // TODO (Alumno): implementar persistencia de un préstamo en formato CSV.
        // Este método debe anexar una línea en el archivo `prestamos.txt`.
        // Implementación de ejemplo omitida para la versión estudiante.
    }

    /** Carga todos los préstamos desde el archivo y devuelve una lista. Si no existe, devuelve lista vacía. */
    public ListaDoble<Prestamo> cargarPrestamos() throws IOException {
        // TODO (Alumno): implementar la lectura de préstamos desde el archivo `prestamos.txt`.
        // Para la versión estudiante devolvemos una lista vacía por defecto.
        return new ListaDoble<Prestamo>();
    }

    /**
     * Elimina un producto de los archivos de familia (si existe). Retorna true si se modificó algún archivo.
     */
    public boolean eliminarProductoPorId(int id) throws IOException {
        // TODO (Alumno): implementar eliminación en archivos de productos.
        return false;
    }

    /**
     * Elimina un estudiante del archivo `src/datos/estudiantes.txt` por código.
     */
    public boolean eliminarEstudiantePorCodigo(int codigo) throws IOException {
        // Preferir archivo de estudiantes en la ruta base (pruebas) si existe
        String baseDir = obtenerBaseDir();
        File f = new File(baseDir + "/estudiantes.txt");
        if (!f.exists()) {
            f = new File("src/datos/estudiantes.txt");
            if (!f.exists()) return false;
        }
        if (!f.exists()) return false;
        File tmp = new File(f.getPath() + ".tmp");
        boolean any = false;
        try (BufferedReader br = new BufferedReader(new FileReader(f)); BufferedWriter bw = new BufferedWriter(new FileWriter(tmp))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) { bw.write(linea); bw.newLine(); continue; }
                String[] partes = linea.split(",");
                int cod = -1;
                try { cod = Integer.parseInt(partes[0].replaceAll("\"","\"").trim()); } catch (Exception e) { cod = -1; }
                if (cod == codigo) { any = true; continue; }
                bw.write(linea); bw.newLine();
            }
            bw.flush();
        }
        // TODO (Alumno): implementar la eliminación de estudiante en el archivo.
        return false;
    }

    /**
     * Limpia préstamos persistidos que correspondan al producto o estudiante dado.
     * Si un id es negativo se ignora en el filtro.
     */
    public boolean limpiarPrestamosPorProductoOEstudiante(int productoId, int estudianteCodigo) throws IOException {
    // TODO (Alumno): implementar limpieza de préstamos en el archivo según filtros.
    return false;
    }

    /**
     * Actualiza la fecha de devolución para la línea del préstamo que coincida
     * con productoId y estudianteCodigo y que actualmente no tenga fecha de devolución.
     * Si estudianteCodigo es negativo sólo compara por productoId.
     */
    public boolean actualizarPrestamoDevolucion(int productoId, int estudianteCodigo, Fecha fechaDevolucion) throws IOException {
        // TODO (Alumno): implementar actualización de la fecha de devolución en el archivo.
        return false;
    }

    /**
     * Reescribe el archivo de estudiantes basado en la lista proporcionada.
     */
    public boolean persistirEstudiantes(negocios.utilidades.listaDoble.ListaDoble<Estudiante> estudiantes) throws IOException {
        String baseDir = obtenerBaseDir();
        File f = new File(baseDir + "/estudiantes.txt");
        // si no existe usamos el archivo legacy
        if (!f.exists()) f = new File("src/datos/estudiantes.txt");
        File tmp = new File(f.getPath() + ".tmp");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(tmp))) {
            for (int i = 0; i < estudiantes.getTamanio(); i++) {
                Estudiante e = null;
                try {
                    e = estudiantes.getValor(i);
                } catch (negocios.utilidades.listaDoble.PosicionIlegalException pie) {
                    // Elemento inválido: saltar y continuar
                    continue;
                }
                StringBuilder sb = new StringBuilder();
                sb.append(e.getCodigo()).append(',');
                sb.append(e.getNombre() != null ? e.getNombre() : "").append(',');
                sb.append(e.getEmail() != null ? e.getEmail().toString() : "").append(',');
                sb.append(e.getFechaNac() != null ? e.getFechaNac().toString() : "").append(',');
                sb.append(e.getSexo() != null ? e.getSexo() : "").append(',');
                sb.append(e.getPrograma() != null ? e.getPrograma() : "");
                bw.write(sb.toString()); bw.newLine();
            }
            bw.flush();
        }
        // TODO (Alumno): implementar persistencia completa de estudiantes
        return false;
    }

    /**
     * Reescribe los archivos de productos por familia desde las listas en memoria.
     */
    public boolean persistirProductos(negocios.utilidades.listaDoble.ListaDoble<ProductosDeportivos> deportivos,
                                      negocios.utilidades.listaDoble.ListaDoble<ProductosEscenicos> escenicos,
                                      negocios.utilidades.listaDoble.ListaDoble<ProductosMusicales> musicales) throws IOException {
        // TODO (Alumno): implementar persistencia de las listas de productos en los archivos
        // Por ahora devolvemos false para indicar que no se hizo ninguna modificación.
        return false;
    }

}