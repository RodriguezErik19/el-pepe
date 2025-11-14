package negocios.utilidades;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Util {

    public static int leerEntero(String mensaje, Scanner scanner, int min, int max, boolean permitirRegresar) {
        while (true) {
            try {
                System.out.print("\u001B[34m" + mensaje + "\u001B[0m"); // Mensaje en azul
                int valor = scanner.nextInt();
                scanner.nextLine(); // Consumir la nueva línea
    
                // Manejar el centinela para regresar
                if (permitirRegresar && valor == -1) {
                    return -1; // Indica que el usuario desea regresar
                }
    
                // Validar el rango
                if (valor < min || valor > max) {
                    System.out.println("\u001B[31m\t***Error: El valor debe estar entre " + min + " y " + max + ".***\u001B[0m");
                } else {
                    return valor; // Valor válido
                }
            } catch (InputMismatchException e) {
                System.out.println("\u001B[31m\t***Error: Debe ingresar un número entero válido.***\u001B[0m");
                scanner.nextLine(); // Consumir la nueva línea
            }
        }
    }

    public static double leerDecimal(String mensaje, Scanner scanner, double min, double max, boolean permitirRegresar) {
        while (true) {
            try {
                System.out.print("\u001B[34m" + mensaje + "\u001B[0m"); // Mensaje en azul
                double valor = scanner.nextDouble(); // Leer un número decimal
                scanner.nextLine(); // Consumir la nueva línea
    
                // Manejar el centinela para regresar
                if (permitirRegresar && valor == -1) {
                    return -1; // Indica que el usuario desea regresar
                }
    
                // Validar el rango
                if (valor < min || valor > max) {
                    System.out.println("\u001B[31m\t***Error: El valor debe estar entre " + min + " y " + max + ".***\u001B[0m");
                } else {
                    return valor; // Valor válido
                }
            } catch (InputMismatchException e) {
                System.out.println("\u001B[31m\t***Error: Debe ingresar un número decimal válido.***\u001B[0m");
                scanner.nextLine(); // Consumir la nueva línea
            }
        }
        
    }

    public static Fecha leerFecha(String mensaje, Scanner scanner) {
        Fecha fecha = null;
        while (fecha == null) {
            System.out.print(mensaje);
            String entrada = scanner.nextLine();
            try {
                fecha = new Fecha(entrada);
            } catch (FechaInvalidaException e) {
                System.out.println("Fecha inválida. Intente de nuevo. Formato correcto: DD/MM/AAAA");
            }
        }
        return fecha;
    }

    
}
