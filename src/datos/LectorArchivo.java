package datos;

import datos.Estudiante;
import datos.ProductosDeportivos;
import datos.ProductosEscenicos;
import datos.ProductosMusicales;
import datos.Prestamo;
import negocios.utilidades.listaDoble.ListaDoble;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * LectorArchivo (versión esqueleto para estudiantes).
 * TODO (Alumno): implementar la lectura de los archivos de datos.
 */
public class LectorArchivo {

    public void leerArchivo(ListaDoble<Estudiante> estudiantes,
                            ListaDoble<ProductosDeportivos> productosDeportivos,
                            ListaDoble<ProductosEscenicos> productosEscenicos,
                            ListaDoble<ProductosMusicales> productosMusicales,
                            ListaDoble<Prestamo> prestamos) {
        // Implementación mínima: leer CSVs de productos y estudiantes.
        String prodDir = "src/datos/archivosProduccion";
        String defaultTestDir = "src/miTest/archivos";
        String baseDir = prodDir;
        try {
            boolean modoPruebas = Configuracion.getBoolean("modo.pruebas");
            if (modoPruebas) {
                String cfgTest = Configuracion.get("ruta.pruebas");
                if (cfgTest != null && !cfgTest.trim().isEmpty()) baseDir = "src/" + cfgTest.replaceFirst("^/", "");
                else baseDir = defaultTestDir;
            } else {
                String cfgProd = Configuracion.get("ruta.produccion");
                if (cfgProd != null && !cfgProd.trim().isEmpty()) baseDir = "src/" + cfgProd.replaceFirst("^/", "");
                else baseDir = prodDir;
            }
        } catch (Exception ex) {
            baseDir = prodDir;
        }

        File maybeTest = new File(defaultTestDir);
        if (!(new File(baseDir)).exists() && maybeTest.exists()) baseDir = defaultTestDir;

        String rutaDep = baseDir + "/ProductosDeportivos.txt";
        String rutaMus = baseDir + "/ProductosMusicales.txt";
        String rutaEsc = baseDir + "/ProductosEscenicos.txt";
        String rutaEst = baseDir + "/estudiantes.txt";

        // Cargar deportivos
        try (Scanner sc = new Scanner(new File(rutaDep))) {
            while (sc.hasNextLine()) {
                String linea = sc.nextLine().trim();
                if (linea.isEmpty() || linea.startsWith("#")) continue;
                String[] campos = linea.split(",");
                try {
                    int id = Integer.parseInt(campos[0].trim());
                    String nombre = campos.length>1 ? campos[1].trim() : "";
                    String descripcion = campos.length>2 ? campos[2].trim() : "";
                    String tipo = campos.length>3 ? campos[3].trim() : "";
                    String material = campos.length>4 ? campos[4].trim() : "";
                    String rango = campos.length>5 ? campos[5].trim() : "";
                    boolean prestado = false;
                    if (campos.length>6) try { prestado = Boolean.parseBoolean(campos[6].trim()); } catch(Exception e) {}
                    ProductosDeportivos p = new ProductosDeportivos(id, nombre, descripcion, tipo, material, rango, new datos.Familia(1,"Deportivos",""), prestado);
                    productosDeportivos.agregar(p);
                } catch (Exception e) {
                    // ignorar línea malformada
                }
            }
        } catch (FileNotFoundException e) {
            // ignorar
        }

        // Cargar musicales
        try (Scanner sc = new Scanner(new File(rutaMus))) {
            while (sc.hasNextLine()) {
                String linea = sc.nextLine().trim();
                if (linea.isEmpty() || linea.startsWith("#")) continue;
                String[] campos = linea.split(",");
                try {
                    int id = Integer.parseInt(campos[0].trim());
                    String nombre = campos.length>1 ? campos[1].trim() : "";
                    String descripcion = campos.length>2 ? campos[2].trim() : "";
                    String material = campos.length>3 ? campos[3].trim() : "";
                    String rango = campos.length>4 ? campos[4].trim() : "";
                    String tipoInst = campos.length>5 ? campos[5].trim() : "";
                    boolean prestado = false;
                    if (campos.length>6) try { prestado = Boolean.parseBoolean(campos[6].trim()); } catch(Exception ex) {}
                    ProductosMusicales p = new ProductosMusicales(id, nombre, descripcion, material, rango, tipoInst, new datos.Familia(2,"Musicales",""), prestado);
                    productosMusicales.agregar(p);
                } catch (Exception e) {}
            }
        } catch (FileNotFoundException e) {}

        // Cargar escenicos
        try (Scanner sc = new Scanner(new File(rutaEsc))) {
            while (sc.hasNextLine()) {
                String linea = sc.nextLine().trim();
                if (linea.isEmpty() || linea.startsWith("#")) continue;
                String[] campos = linea.split(",");
                try {
                    int id = Integer.parseInt(campos[0].trim());
                    String nombre = campos.length>1 ? campos[1].trim() : "";
                    String descripcion = campos.length>2 ? campos[2].trim() : "";
                    String material = campos.length>3 ? campos[3].trim() : "";
                    String rango = campos.length>4 ? campos[4].trim() : "";
                    String tallas = campos.length>5 ? campos[5].trim() : "";
                    String genero = campos.length>6 ? campos[6].trim() : "";
                    String tipoEv = campos.length>7 ? campos[7].trim() : "";
                    boolean prestado = false;
                    if (campos.length>8) try { prestado = Boolean.parseBoolean(campos[8].trim()); } catch(Exception ex) {}
                    ProductosEscenicos p = new ProductosEscenicos(id, nombre, descripcion, material, rango, tallas, genero, tipoEv, new datos.Familia(3,"Escenicos",""), prestado);
                    productosEscenicos.agregar(p);
                } catch (Exception e) {}
            }
        } catch (FileNotFoundException e) {}

        // Cargar estudiantes
        try (Scanner sc = new Scanner(new File(rutaEst))) {
            while (sc.hasNextLine()) {
                String linea = sc.nextLine().trim();
                if (linea.isEmpty() || linea.startsWith("#")) continue;
                String[] campos = linea.split(",");
                try {
                    int codigo = Integer.parseInt(campos[0].trim());
                    String nombre = campos.length>1 ? campos[1].trim() : "";
                    String email = campos.length>2 ? campos[2].trim() : "";
                    String fechaNac = campos.length>3 ? campos[3].trim() : "";
                    String sexo = campos.length>4 ? campos[4].trim() : "";
                    String programa = campos.length>5 ? campos[5].trim() : "";
                    Estudiante est = new Estudiante(codigo, nombre, new datos.Email(email), new datos.Fecha(fechaNac), sexo, programa);
                    estudiantes.agregar(est);
                } catch (Exception e) {}
            }
        } catch (FileNotFoundException e) {}
    }

    // Nota: la API antigua que devolvía `Lista<Producto>` fue eliminada.
}