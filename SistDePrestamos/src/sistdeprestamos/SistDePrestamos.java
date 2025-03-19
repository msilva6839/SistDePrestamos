/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistdeprestamos;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Sistema de Préstamos
 */
class Solicitante {
    int cedula;
    String nombre;
    String apellido1;
    String apellido2;
    int telf;
    int movil;

    void registrar(boolean datosCompletos) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.print("Ingrese la cédula del solicitante: ");
            cedula = Integer.parseInt(br.readLine());

            System.out.print("Ingrese el nombre del solicitante: ");
            nombre = br.readLine();

            System.out.print("Ingrese el primer apellido del solicitante: ");
            apellido1 = br.readLine();

            if (datosCompletos) {
                System.out.print("Ingrese el segundo apellido del solicitante: ");
                apellido2 = br.readLine();

                System.out.print("Ingrese el número de teléfono de habitación del solicitante: ");
                telf = Integer.parseInt(br.readLine());

                System.out.print("Ingrese el número de teléfono móvil del solicitante: ");
                movil = Integer.parseInt(br.readLine());
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error: Entrada inválida. " + e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "Nombre: " + nombre + " Apellidos: " + apellido1 + " " + apellido2 +
                " Cédula: " + cedula +
                " Teléfono fijo: " + telf +
                " Móvil: " + movil;
    }
}

class Prestamo {
    static final double CREDITO_MAXIMO = 1000000.00;
    static final int DIAS_ENTREGA = 7;
    static final int DIAS_PAGO = 30;
    static final int MAX_CUOTAS = 6;
    static final int DIAS_AUTORIZACION = 20;

    int numeroPrestamo;
    double valorPrestamo;
    String fechaAutorizacion;
    String fechaEntrega;
    String[] fechasPago = new String[MAX_CUOTAS];
    static double creditoDisponible = CREDITO_MAXIMO;

    void registrar(int numeroPrestamo) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        this.numeroPrestamo = numeroPrestamo;

        try {
            System.out.print("Ingrese el valor del préstamo a solicitar: ");
            valorPrestamo = Double.parseDouble(br.readLine());

            if (valorPrestamo <= 0) {
                System.out.println("El valor del préstamo debe ser mayor a 0.");
                return;
            }

            if (valorPrestamo > creditoDisponible) {
                System.out.println("El valor del préstamo excede el crédito disponible.");
                return;
            }

            Calendar calendario = Calendar.getInstance();
            SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");

            // Validar que la fecha de autorización esté dentro de los primeros 20 días del mes
            int diaActual = calendario.get(Calendar.DAY_OF_MONTH);
            if (diaActual > DIAS_AUTORIZACION) {
                System.out.println("Los préstamos solo pueden ser autorizados dentro de los primeros 20 días del mes.");
                return;
            }

            // Fecha de autorización
            fechaAutorizacion = formatoFecha.format(calendario.getTime());

            // Fecha tentativa de entrega (7 días después)
            calendario.add(Calendar.DAY_OF_MONTH, DIAS_ENTREGA);
            fechaEntrega = formatoFecha.format(calendario.getTime());

            // Fechas de pago (cada 30 días a partir de la fecha de entrega)
            for (int i = 0; i < MAX_CUOTAS; i++) {
                calendario.add(Calendar.DAY_OF_MONTH, DIAS_PAGO);
                fechasPago[i] = formatoFecha.format(calendario.getTime());
            }

            // Actualizar crédito disponible
            creditoDisponible -= valorPrestamo;

            System.out.println("Préstamo registrado exitosamente.");
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error: Entrada inválida. " + e.getMessage());
        }
    }

    @Override
    public String toString() {
        StringBuilder detalles = new StringBuilder();
        detalles.append("Número de Préstamo: ").append(numeroPrestamo).append("\n");
        detalles.append("Valor del Préstamo: ").append(valorPrestamo).append("\n");
        detalles.append("Fecha de Autorización: ").append(fechaAutorizacion).append("\n");
        detalles.append("Fecha de Entrega: ").append(fechaEntrega).append("\n");
        detalles.append("Fechas de Pago: ").append(Arrays.toString(fechasPago)).append("\n");
        detalles.append("Crédito Disponible: ").append(creditoDisponible).append("\n");
        return detalles.toString();
    }
}

public class SistDePrestamos {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        List<Prestamo> prestamos = new ArrayList<>();
        Map<Integer, Solicitante> solicitantes = new HashMap<>(); // Mapa para almacenar solicitantes por cédula
        int numeroPrestamo = 1;

        while (true) {
            System.out.println("¿Desea registrar un nuevo préstamo? (s/n): ");
            String respuesta = br.readLine();
            if (!respuesta.equalsIgnoreCase("s")) {
                break;
            }

            Solicitante solicitante;
            System.out.print("Ingrese la cédula del solicitante: ");
            int cedula = Integer.parseInt(br.readLine());

            // Verificar si el solicitante ya existe
            if (solicitantes.containsKey(cedula)) {
                solicitante = solicitantes.get(cedula);
                System.out.println("El solicitante ya está registrado. Usando los datos existentes.");
            } else {
                solicitante = new Solicitante();
                System.out.println("¿Desea capturar los datos completos del solicitante? (s/n): ");
                boolean datosCompletos = br.readLine().equalsIgnoreCase("s");
                solicitante.registrar(datosCompletos);
                solicitantes.put(cedula, solicitante); // Guardar el solicitante en el mapa
            }

            Prestamo prestamo = new Prestamo();
            prestamo.registrar(numeroPrestamo++);
            prestamos.add(prestamo);

            System.out.println("Datos del solicitante:");
            System.out.println(solicitante);
            System.out.println("Datos del préstamo:");
            System.out.println(prestamo);
        }

        System.out.println("Registro de préstamos finalizado.");
        System.out.println("Préstamos registrados:");
        for (Prestamo p : prestamos) {
            System.out.println(p);
        }
    }
}
