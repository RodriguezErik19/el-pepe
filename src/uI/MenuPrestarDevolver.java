package uI;

import java.util.Scanner;

import negocios.UniversidadService;
import negocios.utilidades.Util;
import negocios.utilidades.listaDoble.ListaDoble;
import negocios.utilidades.listaDoble.PosicionIlegalException;

public class MenuPrestarDevolver {
    private UniversidadService universidadService;

    public MenuPrestarDevolver(UniversidadService universidadService) {
        this.universidadService = universidadService;
    }
    
    public void prestarProducto(Scanner scanner){
        prestarProductoConNavegacion(scanner);

    }
    public void devolverProducto(Scanner scanner){
        devolverProductoConNavegacion(scanner);
        
    }
    public void prestarProductoConNavegacion(Scanner scanner){
        System.out.println("---- Préstamo de producto ----");

        // Leer código de estudiante
        int codigo = Util.leerEntero("Ingrese código del estudiante (-1 para cancelar): ", scanner, 0, 99999999, true);
        if (codigo == -1) {
            System.out.println("Operación cancelada.");
            return;
        }

        // Validar que el estudiante exista antes de pedir id del producto
        datos.Estudiante estudiante = null;
        try {
            estudiante = universidadService.buscarEstudiante(codigo);
        } catch (PosicionIlegalException ex) {
            System.out.println("No se pudo verificar la existencia del estudiante: " + ex.getMessage());
            return;
        }
        if (estudiante == null) {
            System.out.println("No existe un estudiante con código " + codigo + ". Operación abortada.");
            return;
        }

        // Mostrar información de préstamos activos del estudiante (si existen)
        try {
            ListaDoble<datos.Prestamo> prestamos = universidadService.cargarPrestamos();
            if (prestamos != null) {
                int activos = 0;
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < prestamos.getTamanio(); i++) {
                    datos.Prestamo p = prestamos.getValor(i);
                    if (p.getEstudiante() != null && p.getEstudiante().getCodigo() == codigo && p.getFechaDevolucion() == null) {
                        activos++;
                        // Intentar obtener nombre del producto para mostrar junto al id
                        String nombreProd = "(nombre desconocido)";
                        try {
                            datos.Producto prod = universidadService.buscarProducto(p.getProductoId());
                            if (prod != null && prod.getNombre() != null && !prod.getNombre().trim().isEmpty()) {
                                nombreProd = prod.getNombre();
                            }
                        } catch (PosicionIlegalException ex) {
                            // ignorar si no se puede obtener el producto desde el dominio
                        }
                        sb.append("[productoId=" + p.getProductoId() + ", nombre=" + nombreProd + ", fechaPrestamo=" + p.getFechaPrestamo() + "] ");
                    }
                }
                if (activos > 0) {
                    System.out.println("Préstamos activos para '" + estudiante.getNombre() + "' (codigo " + codigo + "): " + activos);
                    System.out.println(sb.toString());
                } else {
                    System.out.println("No hay préstamos activos para '" + estudiante.getNombre() + "'.");
                }
            }
        } catch (java.io.IOException | PosicionIlegalException ex) {
            // Si no podemos cargar préstamos desde persistencia, lo ignoramos y seguimos
        }

        // Leer id del producto (mostramos el nombre del estudiante en el prompt)
        int id = Util.leerEntero("Ingrese id del producto a prestar para '" + estudiante.getNombre() + "' (-1 para cancelar): ", scanner, 0, 99999999, true);
        if (id == -1) {
            System.out.println("Operación cancelada.");
            return;
        }

        // Validar que el producto exista y sea de una de las familias (Deportivos/Musicales/Escenicos)
        try {
                datos.Producto producto = universidadService.buscarProducto(id);
            if (producto == null) {
                System.out.println("El producto con id " + id + " no existe en ninguna familia. Operación abortada.");
                return;
            }
            // Verificar que sea prestable (implementa el contrato) — por seguridad
            if (!(producto instanceof negocios.Prestable)) {
                System.out.println("El producto con id " + id + " no es prestable. Operación abortada.");
                return;
            }
            // Verificar disponibilidad (no esté ya prestado)
            if (!producto.isDisponible()) {
                System.out.println("El producto con id " + id + " no está disponible (ya está prestado). Operación abortada.");
                return;
            }
            // Comprobación adicional en la persistencia/dominio: si existe un préstamo activo
            // para este producto (fechaDevolucion == null) debemos considerarlo no disponible.
            try {
                datos.Prestamo prestActivo = universidadService.obtenerPrestamoActivoPorProducto(id);
                if (prestActivo != null && prestActivo.getFechaDevolucion() == null) {
                    System.out.println("El producto con id " + id + " ya tiene un préstamo activo (persistido). Operación abortada.");
                    return;
                }
            } catch (Exception ex) {
                // Si hay algún problema consultando la persistencia, no bloqueamos la operación,
                // pero avisamos al usuario (opcional). Aquí lo ignoramos silenciosamente para no
                // romper UX en entornos de pruebas.
            }
        } catch (PosicionIlegalException ex) {
                System.out.println("No se pudo verificar la existencia del producto: " + ex.getMessage());
            return;
        }

        // (La existencia del estudiante fue verificada antes de solicitar el id del producto)

        // Leer fecha en formato DD/MM/AAAA. Permitimos cancelar con -1.
        datos.Fecha fechaPrestamo = null;
        while (fechaPrestamo == null) {
            System.out.print("Ingrese fecha de préstamo (DD/MM/AAAA) o -1 para cancelar: ");
            String entrada = scanner.nextLine().trim();
            if (entrada.equals("-1")) {
                System.out.println("Operación cancelada.");
                return;
            }
            try {
                // usamos la clase de utilidades (negocios.utilidades.Fecha) para validar
                negocios.utilidades.Fecha f = new negocios.utilidades.Fecha(entrada);
                fechaPrestamo = new datos.Fecha(f.getDia(), f.getMes(), f.getAnio());
            } catch (negocios.utilidades.FechaInvalidaException ex) {
                System.out.println("Fecha inválida. Formato correcto: DD/MM/AAAA. Intente de nuevo o ingrese -1 para cancelar.");
            } catch (Exception ex) {
                System.out.println("Entrada de fecha inválida. Intente de nuevo o ingrese -1 para cancelar.");
            }
        }

        // Invocar el servicio para prestar
        try {
            boolean ok = universidadService.prestarProducto(codigo, id, fechaPrestamo);
            if (ok) {
                System.out.println("Préstamo registrado correctamente.");
            } else {
                System.out.println("No se pudo registrar el préstamo. Verifique que el estudiante y el producto existan y que el producto no esté prestado.");
            }
    } catch (PosicionIlegalException ex) {
        System.out.println("Error al intentar prestar el producto: " + ex.getMessage());
    }

    }
    public void devolverProductoConNavegacion(Scanner scanner){

        System.out.println("---- Devolución de producto ----");

        // Leer id del producto a devolver
        int id = Util.leerEntero("Ingrese id del producto a devolver (-1 para cancelar): ", scanner, 0, 99999999, true);
        if (id == -1) {
            System.out.println("Operación cancelada.");
            return;
        }

        // Leer fecha de devolución
        datos.Fecha fechaDev = null;
        // Intentar recuperar la fecha de préstamo existente (si está disponible en persistencia)
        datos.Fecha fechaPrestamoExistente = null;
        try {
            ListaDoble<datos.Prestamo> prestamos = universidadService.cargarPrestamos();
            if (prestamos != null) {
                for (int i = 0; i < prestamos.getTamanio(); i++) {
                    datos.Prestamo p = prestamos.getValor(i);
                    if (p.getProductoId() == id && p.getFechaDevolucion() == null) {
                        fechaPrestamoExistente = p.getFechaPrestamo();
                        break;
                    }
                }
            }
        } catch (java.io.IOException | PosicionIlegalException ex) {
            // Si no podemos cargar préstamos desde DAO, continuamos y delegamos la validación al dominio
        }
        while (fechaDev == null) {
            System.out.print("Ingrese fecha de devolución (DD/MM/AAAA) o -1 para cancelar: ");
            String entrada = scanner.nextLine().trim();
            if (entrada.equals("-1")) {
                System.out.println("Operación cancelada.");
                return;
            }
            try {
                negocios.utilidades.Fecha f = new negocios.utilidades.Fecha(entrada);
                fechaDev = new datos.Fecha(f.getDia(), f.getMes(), f.getAnio());
                // Si conocemos la fecha de préstamo, validamos localmente antes de llamar al servicio
                if (fechaPrestamoExistente != null && fechaDev.compareTo(fechaPrestamoExistente) < 0) {
                    System.out.println("La fecha de devolución no puede ser anterior a la fecha de préstamo (" + fechaPrestamoExistente + "). Intente de nuevo.");
                    fechaDev = null; // forzar reingreso
                    continue;
                }
            } catch (negocios.utilidades.FechaInvalidaException ex) {
                System.out.println("Fecha inválida. Formato correcto: DD/MM/AAAA. Intente de nuevo o ingrese -1 para cancelar.");
            } catch (Exception ex) {
                System.out.println("Entrada de fecha inválida. Intente de nuevo o ingrese -1 para cancelar.");
            }
        }

        // Invocar el servicio para devolver
        try {
            boolean ok = universidadService.devolverProducto(id, fechaDev);
            if (ok) {
                System.out.println("Devolución registrada correctamente.");
            } else {
                System.out.println("No se pudo registrar la devolución. Verifique que el producto exista y que esté actualmente prestado.");
            }
    } catch (PosicionIlegalException ex) {
        System.out.println("Error al intentar devolver el producto: " + ex.getMessage());
    }

    }
}
