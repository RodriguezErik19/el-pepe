package uI;
import java.io.IOException;
import java.util.Scanner;

import datos.UniversidadDAO;
import datos.FamiliaTipo;
import datos.Producto;
import negocios.UniversidadService;
import negocios.utilidades.Util;
import negocios.utilidades.listaDoble.ListaDoble;
import negocios.utilidades.listaDoble.PosicionIlegalException;


public class MenuProductos{
    private int id ;
	private String nombre;
    private String descripcion;
    private String tipoDeporte;
    private String materialPredominante;
    private String rangoPrecios;
    private FamiliaTipo familia;

    private String tallasDisponibles;
    private String generoUso;
    private String tipoEvento;

    private String tipoInstrumento;

    
    
    private UniversidadService universidadService;
    public MenuProductos(UniversidadService universidadService) {
        this.universidadService = universidadService;
    }
    public void agregarProductos(Scanner scanner){
        // Pedir al usuario que seleccione la familia antes de la captura
        System.out.println("Seleccione la familia del producto:");
        System.out.println("1 - DEPORTIVOS, 2 - MUSICALES, 3 - ESCENICOS (-1 SALIR)");
        int seleccion = Util.leerEntero("", scanner, 1, 3, true);
        if (seleccion == -1) {
            System.out.println("Operación cancelada o regreso.");
            return;
        }
        FamiliaTipo fam = FamiliaTipo.fromInt(seleccion);
        if (fam == null) {
            System.out.println("Familia no válida. Solo están permitidas las opciones 1..3.");
            return;
        }
        this.familia = fam;
        System.out.println("****** CAPTURA DE PRODUCTO :"+familia+" ******");
        // Bifurcar a la captura correspondiente
        switch (fam) {
            case DEPORTIVOS:
                agregarConNavegacionProductos(scanner);
                agregarConNavegacionProductosDeportivos(scanner);
                break;
            case MUSICALES:
                agregarConNavegacionProductos(scanner);
                agregarConNavegacionProductosMusicales(scanner);
                break;
            case ESCENICOS:
                agregarConNavegacionProductos(scanner);
                agregarConNavegacionProductosEscenicos(scanner);
                break;
            default:
                System.out.println("Familia no soportada.");
        }
    }
    public void eliminarProductos(Scanner scanner){
        eliminarConNavegacionProductos(scanner);
        
    }
    public void mostrarProductos(Scanner scanner){
        mostrarConNavegacionProductos(scanner);

    }
    public void agregarProductosDeportivos(Scanner scanner){
        agregarConNavegacionProductosDeportivos(scanner);

    }
    public void eliminarProductosDeportivos(Scanner scanner){
        eliminarConNavegacionProductosDeportivos(scanner);
        
    }
    public void mostrarProductosDeportivos(Scanner scanner){
        mostrarConNavegacionProductosDeportivos(scanner);

    }
    public void agregarProductosEscenicos(Scanner scanner){
        agregarConNavegacionProductosEscenicos(scanner);

    }
    public void eliminarProductosEscenicos(Scanner scanner){
        eliminarConNavegacionProductosEscenicos(scanner);
        
    }
    public void mostrarProductosEscenicos(Scanner scanner){
        mostrarConNavegacionProductosEscenicos(scanner);

    }
    public void agregarProductosMusicales(Scanner scanner){
        agregarConNavegacionProductosMusicales(scanner);

    }
    public void eliminarProductosMusicales(Scanner scanner){
        eliminarConNavegacionProductosMusicales(scanner);
        
    }
    public void mostrarProductosMusicales(Scanner scanner){
        mostrarConNavegacionProductosMusicales(scanner);

    }

    private void agregarConNavegacionProductos(Scanner scanner) {
        String[] datos = new String[2]; // Arreglo para almacenar los datos (id, nombre)
        String[] mensajes = {
            "Ingrese el Id del producto     : ",
            "Ingrese el nombre del producto : "
        };
    
        int paso = 0; // Índice para controlar el flujo de captura
    
        while (paso < mensajes.length) {
            System.out.print(mensajes[paso]);
    
            if (paso == 0) { // Validar el id
                int id = Util.leerEntero("", scanner, 0, 9999, true);
                if (id == -1) {
                    if (paso > 0) {
                        paso--; // Regresa al paso anterior
                    } else {
                        System.out.println("Ya estás en el primer paso, no puedes regresar más.");
                    }
                    continue;
                }
                datos[paso] = String.valueOf(id);
            } else {
                String entrada = scanner.nextLine();
                if (entrada.equals("-1")) {
                    if (paso > 0) {
                        paso--; // Regresa al paso anterior
                    } else {
                        System.out.println("Ya estás en el primer paso, no puedes regresar más.");
                    }
                    continue;
                }
                datos[paso] = entrada;
            }
            paso++; // Avanza al siguiente paso
        }
    
    // Asignar los datos capturados a las variables correspondientes
    id = Integer.parseInt(datos[0]);
    nombre = datos[1];

    }
    private void eliminarConNavegacionProductos(Scanner scanner){
        // Pedir al usuario que seleccione la familia antes de la eliminación
        System.out.println("Seleccione la familia del producto a eliminar:");
        System.out.println("1 - DEPORTIVOS, 2 - MUSICALES, 3 - ESCENICOS (-1 SALIR)");
        int seleccion = Util.leerEntero("", scanner, 1, 3, true);
        if (seleccion == -1) {
            System.out.println("Operación cancelada o regreso.");
            return;
        }
        FamiliaTipo fam = FamiliaTipo.fromInt(seleccion);
        if (fam == null) {
            System.out.println("Familia no válida. Solo están permitidas las opciones 1..3.");
            return;
        }
        this.familia = fam;
        System.out.println("****** ELIMINAR PRODUCTO FAMILIA :"+familia+" ******");
        // Delegar a la rutina de eliminación específica por familia
        switch (fam) {
            case DEPORTIVOS:
                eliminarConNavegacionProductosDeportivos(scanner);
                break;
            case MUSICALES:
                eliminarConNavegacionProductosMusicales(scanner);
                break;
            case ESCENICOS:
                eliminarConNavegacionProductosEscenicos(scanner);
                break;
            default:
                System.out.println("Familia no soportada.");
        }

    }
    private void mostrarConNavegacionProductos(Scanner scanner){
        // Pedir al usuario que seleccione la familia antes de la captura
        System.out.println("Seleccione la familia del producto:");
        System.out.println("1 - DEPORTIVOS, 2 - MUSICALES, 3 - ESCENICOS (-1 SALIR)");
        int seleccion = Util.leerEntero("", scanner, 1, 3, true);
        if (seleccion == -1) {
            System.out.println("Operación cancelada o regreso.");
            return;
        }
        FamiliaTipo fam = FamiliaTipo.fromInt(seleccion);
        if (fam == null) {
            System.out.println("Familia no válida. Solo están permitidas las opciones 1..3.");
            return;
        }
        this.familia = fam;
        System.out.println("****** CAPTURA DE PRODUCTO :"+familia+" ******");
        // Bifurcar a la captura correspondiente
        switch (fam) {
            case DEPORTIVOS:
                mostrarConNavegacionProductosDeportivos(scanner);
                
                break;
            case MUSICALES:
                mostrarConNavegacionProductosMusicales(scanner);
                break;
            case ESCENICOS:
                    mostrarConNavegacionProductosEscenicos(scanner);
               break;
            default:
                System.out.println("Familia no soportada.");
        }

    }
    private void agregarConNavegacionProductosDeportivos(Scanner scanner) {
        String[] datos = new String[4]; // Arreglo para almacenar los datos (id, nombre)
        String[] mensajes = {
           
            "Ingrese la descripción del Producto: ",
            "Ingrese el tipo de deporte         : ",
            "Ingrese el material predominante.  : ",
            "Ingrese el rango de precios.       : "
        };
    
        int paso = 0; // Índice para controlar el flujo de captura
    
        while (paso < mensajes.length) {
            System.out.print(mensajes[paso]);
            String entrada = scanner.nextLine();
            if (entrada.equals("-1")) {
                if (paso > 0) {
                     paso--; // Regresa al paso anterior
                 } else {
                     System.out.println("Ya estás en el primer paso, no puedes regresar más.");
                 }
                 continue;
             }
             datos[paso] = entrada;
            
            paso++; // Avanza al siguiente paso
        }
    
    // Asignar los datos capturados a las variables correspondientes
    descripcion = datos[0];
    tipoDeporte = datos[1];
    materialPredominante = datos[2];
    rangoPrecios =datos[3];
    // La familia ya fue seleccionada antes de llamar a este método
    // `familia` ya contiene el valor seleccionado (DEPORTIVOS/MUSICALES/ESCENICOS)
    
    System.out.println("\u001B[34mCaptura completada:\u001B[0m");
    System.out.println("Id del producto: " + id);
    System.out.println("Nombre         : " + nombre);
    System.out.println("descripción del Producto: "+descripcion);
    System.out.println("tipo de deporte         : "+tipoDeporte);
    System.out.println("material predominante.  : "+materialPredominante);
    System.out.println("rango de precios.       : "+rangoPrecios);
    System.out.println("Familia                 : "+ (familia != null ? familia.toString() : "(No válida)"));
       

    }
    private void eliminarConNavegacionProductosDeportivos(Scanner scanner){
        // Mostrar lista antes de solicitar id para mejorar la experiencia
        try {
            mostrarConNavegacionProductosDeportivos(scanner);
        } catch (Exception ignored) {}

        System.out.print("Ingrese el id del producto DEPORTIVO a eliminar (-1 para cancelar): ");
        int id = Util.leerEntero("", scanner, 0, 9999, true);
        if (id == -1) { System.out.println("Operación cancelada."); return; }
        // Verificar existencia en la familia seleccionada antes de pedir confirmación
        Producto p = null;
        try {
            p = universidadService.obtenerProductoPorIdYFamilia(id, FamiliaTipo.DEPORTIVOS);
        } catch (Exception e) {
            // ignorar y tratar como no encontrado
        }
        if (p == null) {
            // Buscar en otras familias y ofrecer eliminación si se encuentra en otra
            Producto otro = null;
            FamiliaTipo famEncontrada = null;
            for (FamiliaTipo ft : FamiliaTipo.values()) {
                if (ft == FamiliaTipo.DEPORTIVOS) continue;
                try {
                    Producto tmp = universidadService.obtenerProductoPorIdYFamilia(id, ft);
                    if (tmp != null) { otro = tmp; famEncontrada = ft; break; }
                } catch (Exception ignored) {}
            }
            if (otro != null) {
                System.out.println("El producto con id " + id + " existe en la familia " + famEncontrada + " como: " + otro.getNombre());
                System.out.print("¿Desea eliminarlo igualmente de la familia " + famEncontrada + "? (s/N): ");
                String r = scanner.nextLine().trim().toLowerCase();
                if (!r.equals("s") && !r.equals("si")) { System.out.println("Eliminación cancelada."); return; }
                // proceder a eliminar igualmente
            } else {
                System.out.println("No existe un producto con id " + id + " en la familia DEPORTIVOS. Eliminación abortada.");
                return;
            }
        }

        String nombre = p.getNombre();
        System.out.println("Se eliminará: " + id + " - " + nombre);
        System.out.print("Confirma eliminar el producto con id " + id + "? (s/N): ");
        String resp = scanner.nextLine().trim().toLowerCase();
        if (!resp.equals("s") && !resp.equals("si")) { System.out.println("Eliminación cancelada."); return; }
        try {
            boolean ok = universidadService.eliminarProducto(id);
            if (ok) System.out.println("Producto eliminado satisfactoriamente.");
            else System.out.println("No se encontró o no se pudo eliminar el producto con id " + id);
        } catch (Exception ex) {
            System.out.println("Error al eliminar producto: " + ex.getMessage());
        }

    }
    private void mostrarConNavegacionProductosDeportivos(Scanner scanner){
            try {
            ListaDoble<datos.ProductosDeportivos> lista = universidadService.mostrarProductosDeportivos();
            if (lista == null || lista.getTamanio() == 0) {
                System.out.println("No hay productos deportivos cargados.");
                return;
            }
            System.out.println("Lista de Productos Deportivos:");
            for (int i = 0; i < lista.getTamanio(); i++) {
                datos.ProductosDeportivos e = lista.getValor(i);
                System.out.println(e);
            }
        } catch (PosicionIlegalException ex) {
            System.out.println("Error mostrando estudiantes: " + ex.getMessage());
        }

    }
    private void agregarConNavegacionProductosEscenicos(Scanner scanner) {
        String[] datos = new String[6]; // Arreglo para almacenar los datos (id, nombre)
        String[] mensajes = {
            "Ingrese descripción                : ",
            "Ingrese material predominante      : ",
            "Ingrese el rango de precios        : ",
            "Ingrese las tallas disponibles     : ",
            "Ingrese el genero de uso.          : ",
            "Ingrese el tipo de evento          : "
        };
    
        int paso = 0; // Índice para controlar el flujo de captura
    
        while (paso < mensajes.length) {
            System.out.print(mensajes[paso]);
            String entrada = scanner.nextLine();
            if (entrada.equals("-1")) {
                if (paso > 0) {
                    paso--; // Regresa al paso anterior
                } else {
                    System.out.println("Ya estás en el primer paso, no puedes regresar más.");
                }
                 continue;
            }
            datos[paso] = entrada;
            
            paso++; // Avanza al siguiente paso
        }
    
    // Asignar los datos capturados a las variables correspondientes
    descripcion = datos[0];
    materialPredominante = datos[1];
    rangoPrecios = datos[2];
    tallasDisponibles = datos[3];
    generoUso = datos[4];
    tipoEvento = datos[5];
    // La familia ya fue seleccionada antes de llamar a este método
    
    System.out.println("\u001B[34mCaptura completada:\u001B[0m");
    System.out.println("Id del producto.      : " + id);
    System.out.println("Nombre                : " + nombre);
    System.out.println("Descripción           : " + descripcion);
    System.out.println("Material Predominante : "+ materialPredominante); 
    System.out.println("Rango de precios.     : "+ rangoPrecios);
    System.out.println("Tallas Disponibles    : "+ tallasDisponibles);
    System.out.println("Genero uso            : "+ generoUso);
    System.out.println("Tipo Evento           : "+ tipoEvento);
    System.out.println("Familia               : "+ (familia != null ? familia.toString() : "(No válida)"));
       

    }
    private void eliminarConNavegacionProductosEscenicos(Scanner scanner){
        // Mostrar lista antes de solicitar id para mejorar la experiencia
        try {
            mostrarConNavegacionProductosEscenicos(scanner);
        } catch (Exception ignored) {}

        System.out.print("Ingrese el id del producto ESCENICO a eliminar (-1 para cancelar): ");
        int id = Util.leerEntero("", scanner, 0, 9999, true);
        if (id == -1) { System.out.println("Operación cancelada."); return; }
        // Verificar existencia en la familia seleccionada antes de pedir confirmación
        Producto p = null;
        try {
            p = universidadService.obtenerProductoPorIdYFamilia(id, FamiliaTipo.ESCENICOS);
        } catch (Exception e) {
            // ignorar y tratar como no encontrado
        }
        if (p == null) {
            // Buscar en otras familias y ofrecer eliminación si se encuentra en otra
            Producto otro = null;
            FamiliaTipo famEncontrada = null;
            for (FamiliaTipo ft : FamiliaTipo.values()) {
                if (ft == FamiliaTipo.ESCENICOS) continue;
                try {
                    Producto tmp = universidadService.obtenerProductoPorIdYFamilia(id, ft);
                    if (tmp != null) { otro = tmp; famEncontrada = ft; break; }
                } catch (Exception ignored) {}
            }
            if (otro != null) {
                System.out.println("El producto con id " + id + " existe en la familia " + famEncontrada + " como: " + otro.getNombre());
                System.out.print("¿Desea eliminarlo igualmente de la familia " + famEncontrada + "? (s/N): ");
                String r = scanner.nextLine().trim().toLowerCase();
                if (!r.equals("s") && !r.equals("si")) { System.out.println("Eliminación cancelada."); return; }
                // proceder a eliminar igualmente
            } else {
                System.out.println("No existe un producto con id " + id + " en la familia ESCENICOS. Eliminación abortada.");
                return;
            }
        }

        String nombre = p.getNombre();
        System.out.println("Se eliminará: " + id + " - " + nombre);
        System.out.print("Confirma eliminar el producto con id " + id + "? (s/N): ");
        String resp = scanner.nextLine().trim().toLowerCase();
        if (!resp.equals("s") && !resp.equals("si")) { System.out.println("Eliminación cancelada."); return; }
        try {
            boolean ok = universidadService.eliminarProducto(id);
            if (ok) System.out.println("Producto eliminado satisfactoriamente.");
            else System.out.println("No se encontró o no se pudo eliminar el producto con id " + id);
        } catch (Exception ex) {
            System.out.println("Error al eliminar producto: " + ex.getMessage());
        }

    }
    private void mostrarConNavegacionProductosEscenicos(Scanner scanner){
        try {
            ListaDoble<datos.ProductosEscenicos> lista = universidadService.mostrarProductosEscenicos();
            if (lista == null || lista.getTamanio() == 0) {
                System.out.println("No hay productos escenicos cargados.");
                return;
            }
            System.out.println("Lista de Productos Escenicos:");
            for (int i = 0; i < lista.getTamanio(); i++) {
                datos.ProductosEscenicos e = lista.getValor(i);
                System.out.println(e);
            }
        } catch (PosicionIlegalException ex) {
            System.out.println("Error mostrando productos escenicos: " + ex.getMessage());
        }

    }
    private void agregarConNavegacionProductosMusicales(Scanner scanner) {
        String[] datos = new String[4]; // Arreglo para almacenar los datos (id, nombre)
        String[] mensajes = {
            "Ingrese la descripción del Producto: ",
            "Ingrese el material predominante   : ",
            "Ingrese rango de precios           : ",
            "Ingrese el tipo de instrumento     : "
        };
    
        int paso = 0; // Índice para controlar el flujo de captura
    
        while (paso < mensajes.length) {
            System.out.print(mensajes[paso]);
    
            String entrada = scanner.nextLine();
            if (entrada.equals("-1")) {
                 if (paso > 0) {
                     paso--; // Regresa al paso anterior
                 } else {
                     System.out.println("Ya estás en el primer paso, no puedes regresar más.");
                 }
                continue;
            }
            datos[paso] = entrada;
            
            paso++; // Avanza al siguiente paso
        }
    
    // Asignar los datos capturados a las variables correspondientes
    descripcion = datos[0];
    materialPredominante = datos[1];
    rangoPrecios = datos[2];
    tipoInstrumento = datos[3];
    // La familia ya fue seleccionada antes de llamar a este método
    
    System.out.println("\u001B[34mCaptura completada:\u001B[0m");
    System.out.println("Id del producto: " + id);
    System.out.println("Nombre         : " + nombre);
    System.out.println("descripción del Producto: "+descripcion);
    System.out.println("material predominante.  : "+materialPredominante);
    System.out.println("Rango de precios        : "+rangoPrecios);
    System.out.println("tipo de instrumento     : "+tipoInstrumento);
    System.out.println("material predominante.  : "+materialPredominante);
    System.out.println("Familia                 : "+ (familia != null ? familia.toString() : "(No válida)"));

    }
    private void eliminarConNavegacionProductosMusicales(Scanner scanner){
        // Mostrar lista antes de solicitar id para mejorar la experiencia
        try {
            mostrarConNavegacionProductosMusicales(scanner);
        } catch (Exception ignored) {}

        System.out.print("Ingrese el id del producto MUSICAL a eliminar (-1 para cancelar): ");
        int id = Util.leerEntero("", scanner, 0, 9999, true);
        if (id == -1) { System.out.println("Operación cancelada."); return; }
        // Verificar existencia en la familia seleccionada antes de pedir confirmación
        Producto p = null;
        try {
            p = universidadService.obtenerProductoPorIdYFamilia(id, FamiliaTipo.MUSICALES);
        } catch (Exception e) {
            // ignorar y tratar como no encontrado
        }
        if (p == null) {
            // Buscar en otras familias y ofrecer eliminación si se encuentra en otra
            Producto otro = null;
            FamiliaTipo famEncontrada = null;
            for (FamiliaTipo ft : FamiliaTipo.values()) {
                if (ft == FamiliaTipo.MUSICALES) continue;
                try {
                    Producto tmp = universidadService.obtenerProductoPorIdYFamilia(id, ft);
                    if (tmp != null) { otro = tmp; famEncontrada = ft; break; }
                } catch (Exception ignored) {}
            }
            if (otro != null) {
                System.out.println("El producto con id " + id + " existe en la familia " + famEncontrada + " como: " + otro.getNombre());
                System.out.print("¿Desea eliminarlo igualmente de la familia " + famEncontrada + "? (s/N): ");
                String r = scanner.nextLine().trim().toLowerCase();
                if (!r.equals("s") && !r.equals("si")) { System.out.println("Eliminación cancelada."); return; }
                // proceder a eliminar igualmente
            } else {
                System.out.println("No existe un producto con id " + id + " en la familia MUSICALES. Eliminación abortada.");
                return;
            }
        }

        String nombre = p.getNombre();
        System.out.println("Se eliminará: " + id + " - " + nombre);
        System.out.print("Confirma eliminar el producto con id " + id + "? (s/N): ");
        String resp = scanner.nextLine().trim().toLowerCase();
        if (!resp.equals("s") && !resp.equals("si")) { System.out.println("Eliminación cancelada."); return; }
        try {
            boolean ok = universidadService.eliminarProducto(id);
            if (ok) System.out.println("Producto eliminado satisfactoriamente.");
            else System.out.println("No se encontró o no se pudo eliminar el producto con id " + id);
        } catch (Exception ex) {
            System.out.println("Error al eliminar producto: " + ex.getMessage());
        }

    }
    private void mostrarConNavegacionProductosMusicales(Scanner scanner){
        try {
            ListaDoble<datos.ProductosMusicales> lista = universidadService.mostrarProductosMusicales();
            if (lista == null || lista.getTamanio() == 0) {
                System.out.println("No hay productos musicales cargados.");
                return;
            }
            System.out.println("Lista de Productos Musicales:");
            for (int i = 0; i < lista.getTamanio(); i++) {
                datos.ProductosMusicales e = lista.getValor(i);
                System.out.println(e);
            }
        } catch (PosicionIlegalException ex) {
            System.out.println("Error mostrando productos musicales: " + ex.getMessage());
        }


    }

    
}