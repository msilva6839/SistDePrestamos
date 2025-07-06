/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistdeprestamos;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * @author symq9485
 */
class Solicitante {

    int cedula;
    String nombre;
    String apellido1;
    String apellido2;
    int telf;
    int movil;

    void registrar() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.print("Ingrese la cedula del solicitante: ");
            cedula = Integer.parseInt(br.readLine());
            System.out.print("Ingrese el nombre del solicitante: ");
            nombre = br.readLine();
            System.out.print("Ingrese el primer apellido del solicitante: ");
            apellido1 = br.readLine();
            System.out.print("Ingrese el segundo apellido del solicitante: ");
            apellido2 = br.readLine();
            System.out.print("Ingrese el numero de telefono de habitacion del solicitante: ");
            telf = Integer.parseInt(br.readLine());
            System.out.print("Ingrese numero de telefono movil del solicitante: ");
            movil = Integer.parseInt(br.readLine());
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "Nombre: " + nombre + " Apellidos: " + apellido1 + " " + apellido2 + "Cedula: " + cedula + "Telefono fijo: " + telf + "Movil: " + movil;
    }
}

class Prestamo {

    int N_Prestamo;
    double Credito;
    double Valor_de_prestamo;
    String Fecha_autorizacion;
    String Fecha_entrega;
    String[] Fecha_pagos = new String[6];
    private static int contadorPrestamos = 0; // Para generar N_Prestamo automáticamente

    public Prestamo() {
        contadorPrestamos++;
        this.N_Prestamo = contadorPrestamos;
        this.Credito = 1000000.00d; // Valor inicial del crédito
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar cal = GregorianCalendar.getInstance();
        this.Fecha_autorizacion = sdf.format(cal.getTime());
        // La fecha de entrega podría ser la misma o un día después, por ejemplo
        this.Fecha_entrega = sdf.format(cal.getTime());

        // Calcular fechas de pago (6 meses a partir de la fecha de autorización)
        for (int i = 0; i < 6; i++) { // Corregido el índice a 0-5
            Calendar pagoCal = (Calendar) cal.clone();
            pagoCal.add(Calendar.MONTH, i + 1); // Sumar 1, 2, 3... meses
            Fecha_pagos[i] = sdf.format(pagoCal.getTime());
        }
    }

    void registro() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.print("Ingrese el valor del prestamo a solicitar: ");
            Valor_de_prestamo = Double.parseDouble(br.readLine());
        } catch (IOException e) {
            System.err.println("Error al leer el valor del préstamo: " + e.getMessage());
            Valor_de_prestamo = 0; // Asignar un valor por defecto en caso de error
        }
    }

    void mostrarDetalles() {
        System.out.println("Número de Préstamo: " + N_Prestamo);
        System.out.println("Crédito Disponible: " + Credito);
        System.out.println("Valor del Préstamo Solicitado: " + Valor_de_prestamo);
        System.out.println("Fecha de Autorización: " + Fecha_autorizacion);
        System.out.println("Fecha de Entrega: " + Fecha_entrega);
        System.out.println("Fechas de Pago:");
        for (int i = 0; i < Fecha_pagos.length; i++) {
            System.out.println("  Mes " + (i + 1) + ": " + Fecha_pagos[i]);
        }
    }
}

public class SistDePrestamos {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int opcion;
        Solicitante solicitante = null;
        Prestamo prestamo = null;

        do {
            System.out.println("\n--- Menú Principal ---");
            System.out.println("1. Registrar Solicitante");
            System.out.println("2. Registrar Préstamo");
            System.out.println("3. Mostrar Detalles del Solicitante");
            System.out.println("4. Mostrar Detalles del Préstamo");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");

            try {
                opcion = Integer.parseInt(br.readLine());

                switch (opcion) {
                    case 1:
                        solicitante = new Solicitante();
                        solicitante.registrar();
                        System.out.println("Solicitante registrado con éxito.");
                        break;
                    case 2:
                        if (solicitante == null) {
                            System.out.println("Debe registrar un solicitante primero.");
                        } else {
                            prestamo = new Prestamo();
                            prestamo.registro();
                            System.out.println("Préstamo registrado con éxito.");
                        }
                        break;
                    case 3:
                        if (solicitante == null) {
                            System.out.println("No hay solicitante registrado.");
                        } else {
                            System.out.println("\n--- Detalles del Solicitante ---");
                            System.out.println(solicitante.toString());
                        }
                        break;
                    case 4:
                        if (prestamo == null) {
                            System.out.println("No hay préstamo registrado.");
                        } else {
                            System.out.println("\n--- Detalles del Préstamo ---");
                            prestamo.mostrarDetalles();
                        }
                        break;
                    case 5:
                        System.out.println("Saliendo del programa...");
                        break;
                    default:
                        System.out.println("Opción no válida. Intente de nuevo.");
                }
            } catch (NumberFormatException e) {
                System.err.println("Entrada inválida. Por favor, ingrese un número.");
                opcion = 0; // Para que el bucle continúe
            } catch (IOException e) {
                System.err.println("Error de lectura: " + e.getMessage());
                opcion = 0;
            }
        } while (opcion != 5);
    }
}
