package sistdeprestamos;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Sistema de Préstamos — Estructura de Datos, 5to semestre.
 */
class Solicitante {
    int cedula;
    String nombre;
    String apellido1;
    String apellido2;
    String telf;
    String movil;

    /**
     * Captura los datos del solicitante desde consola.
     *
     * @param sc            Scanner de entrada
     * @param datosCompletos true = captura todos los campos;
     *                       false = solo nombre y primer apellido
     */
    void registrar(Scanner sc, boolean datosCompletos) {
        System.out.print("Ingrese el primer nombre del solicitante: ");
        nombre = sc.nextLine().trim();

        System.out.print("Ingrese el primer apellido del solicitante: ");
        apellido1 = sc.nextLine().trim();

        if (datosCompletos) {
            System.out.print("Ingrese el segundo apellido del solicitante: ");
            apellido2 = sc.nextLine().trim();

            System.out.print("Ingrese el teléfono de casa del solicitante: ");
            telf = sc.nextLine().trim();

            System.out.print("Ingrese el teléfono móvil del solicitante: ");
            movil = sc.nextLine().trim();
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("  Cédula       : ").append(cedula).append("\n");
        sb.append("  Nombre       : ").append(nombre).append("\n");
        sb.append("  Apellidos    : ").append(apellido1);
        if (apellido2 != null && !apellido2.isEmpty()) {
            sb.append(" ").append(apellido2);
        }
        sb.append("\n");
        if (telf != null && !telf.isEmpty()) {
            sb.append("  Tel. fijo    : ").append(telf).append("\n");
        }
        if (movil != null && !movil.isEmpty()) {
            sb.append("  Tel. móvil   : ").append(movil).append("\n");
        }
        return sb.toString();
    }
}

class Prestamo {
    // --- Parámetros del sistema ---
    static final double CREDITO_MAXIMO = 1_000_000.00;
    static final String FECHA_MAXIMA_STR = "20/12/2025"; // Regla 7: fecha máxima de autorización
    static final int DIA_LIMITE_AUTORIZACION = 20;       // Regla 6: solo primeros 20 días del mes
    static final int DIAS_ENTREGA = 7;                   // Regla 4: entrega 7 días después
    static final int DIAS_PAGO = 30;                     // Regla 5: cuotas cada 30 días
    static final int MAX_CUOTAS = 6;                     // Máximo 6 cuotas

    static double creditoDisponible = CREDITO_MAXIMO;    // Regla 8: sumatoria no supera el máximo

    // --- Datos del préstamo ---
    int numeroPrestamo;
    Solicitante solicitante;
    double valorPrestamo;
    int numeroCuotas;
    String fechaAutorizacion;
    String fechaEntrega;
    String[] fechasPago;

    /**
     * Registra el préstamo capturando y validando todos los datos necesarios.
     *
     * @return true si el préstamo fue registrado correctamente, false en caso contrario.
     */
    boolean registrar(Scanner sc, int numPrestamo, Solicitante sol) {
        this.numeroPrestamo = numPrestamo;
        this.solicitante = sol;

        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
        fmt.setLenient(false);

        // --- Valor del préstamo ---
        System.out.print("Ingrese el valor del préstamo: ");
        double valor;
        try {
            valor = Double.parseDouble(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Error: Valor inválido.");
            return false;
        }
        if (valor <= 0) {
            System.out.println("Error: El valor del préstamo debe ser mayor a 0.");
            return false;
        }
        if (valor > creditoDisponible) {
            System.out.printf("Error: El valor excede el crédito disponible (%.2f).%n", creditoDisponible);
            return false;
        }

        // --- Número de cuotas (1–6) ---
        System.out.print("Ingrese el número de cuotas (1-" + MAX_CUOTAS + "): ");
        int cuotas;
        try {
            cuotas = Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Error: Número de cuotas inválido.");
            return false;
        }
        if (cuotas < 1 || cuotas > MAX_CUOTAS) {
            System.out.println("Error: El número de cuotas debe estar entre 1 y " + MAX_CUOTAS + ".");
            return false;
        }

        // --- Fecha de autorización ---
        System.out.print("Ingrese el día de autorización (dd): ");
        int dia;
        try {
            dia = Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Error: Día inválido.");
            return false;
        }
        if (dia < 1 || dia > DIA_LIMITE_AUTORIZACION) {
            System.out.println("Error: Los préstamos solo se pueden autorizar los primeros "
                    + DIA_LIMITE_AUTORIZACION + " días del mes.");
            return false;
        }

        System.out.print("Ingrese el mes de autorización (mm): ");
        int mes;
        try {
            mes = Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Error: Mes inválido.");
            return false;
        }

        System.out.print("Ingrese el año de autorización (aaaa): ");
        int anio;
        try {
            anio = Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Error: Año inválido.");
            return false;
        }

        String fechaAutoStr = String.format("%02d/%02d/%04d", dia, mes, anio);
        Date fechaAuto;
        try {
            fechaAuto = fmt.parse(fechaAutoStr);
        } catch (ParseException e) {
            System.out.println("Error: Fecha inválida (" + fechaAutoStr + ").");
            return false;
        }

        // Verificar que no supere la fecha máxima (Regla 7)
        try {
            Date fechaMaxima = fmt.parse(FECHA_MAXIMA_STR);
            if (fechaAuto.after(fechaMaxima)) {
                System.out.println("Error: La fecha de autorización supera la fecha máxima permitida ("
                        + FECHA_MAXIMA_STR + ").");
                return false;
            }
        } catch (ParseException e) {
            throw new RuntimeException("Fecha máxima interna mal configurada: " + FECHA_MAXIMA_STR, e);
        }

        // --- Calcular fechas ---
        Calendar cal = Calendar.getInstance();
        cal.setTime(fechaAuto);

        // Fecha de entrega = autorización + 7 días (Regla 4)
        cal.add(Calendar.DAY_OF_MONTH, DIAS_ENTREGA);
        String entrega = fmt.format(cal.getTime());

        // Fechas de pago: cada 30 días a partir de la fecha de entrega (Regla 5)
        String[] pagos = new String[cuotas];
        for (int i = 0; i < cuotas; i++) {
            cal.add(Calendar.DAY_OF_MONTH, DIAS_PAGO);
            pagos[i] = fmt.format(cal.getTime());
        }

        // --- Todo válido: guardar datos y actualizar crédito ---
        this.valorPrestamo = valor;
        this.numeroCuotas = cuotas;
        this.fechaAutorizacion = fechaAutoStr;
        this.fechaEntrega = entrega;
        this.fechasPago = pagos;
        creditoDisponible -= valor;

        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("--- Préstamo #").append(numeroPrestamo).append(" ---\n");
        sb.append("Solicitante:\n").append(solicitante);
        sb.append(String.format("Valor del préstamo  : %.2f%n", valorPrestamo));
        sb.append("Fecha de autorización: ").append(fechaAutorizacion).append("\n");
        sb.append("Fecha de entrega     : ").append(fechaEntrega).append("\n");
        sb.append("Número de cuotas     : ").append(numeroCuotas).append("\n");
        sb.append("Fechas de pago:\n");
        for (int i = 0; i < numeroCuotas; i++) {
            sb.append("  Cuota ").append(i + 1).append(": ").append(fechasPago[i]).append("\n");
        }
        sb.append(String.format("Crédito disponible  : %.2f%n", creditoDisponible));
        return sb.toString();
    }
}

public class SistDePrestamos {

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        List<Prestamo> prestamos = new ArrayList<>();
        Map<Integer, Solicitante> solicitantes = new HashMap<>();
        int numeroPrestamo = 1;

        System.out.println("=== Sistema de Préstamos ===");
        System.out.printf("Crédito máximo disponible : %.2f%n", Prestamo.CREDITO_MAXIMO);
        System.out.println("Fecha máxima de autorización: " + Prestamo.FECHA_MAXIMA_STR);

        while (true) {
            System.out.print("\n¿Desea registrar un nuevo préstamo? (s/n): ");
            String resp = sc.nextLine().trim();
            if (!resp.equalsIgnoreCase("s")) {
                break;
            }

            // --- Captura del solicitante ---
            System.out.print("Ingrese la cédula del solicitante: ");
            int cedula;
            try {
                cedula = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Error: Cédula inválida. Intente de nuevo.");
                continue;
            }

            Solicitante solicitante;
            if (solicitantes.containsKey(cedula)) {
                solicitante = solicitantes.get(cedula);
                System.out.println("Solicitante ya registrado. Usando datos existentes.");
            } else {
                solicitante = new Solicitante();
                solicitante.cedula = cedula;
                System.out.print("¿Capturar datos completos del solicitante? (s = completos / n = solo requeridos): ");
                boolean datosCompletos = sc.nextLine().trim().equalsIgnoreCase("s");
                solicitante.registrar(sc, datosCompletos);
                solicitantes.put(cedula, solicitante);
            }

            // --- Captura del préstamo ---
            Prestamo prestamo = new Prestamo();
            if (prestamo.registrar(sc, numeroPrestamo, solicitante)) {
                numeroPrestamo++;
                prestamos.add(prestamo);
                System.out.println("\nPréstamo registrado exitosamente:");
                System.out.println(prestamo);
            } else {
                System.out.println("El préstamo no pudo ser registrado. Intente de nuevo.");
            }
        }

        // --- Resumen final ---
        System.out.println("\n=== Resumen de Préstamos Registrados ===");
        if (prestamos.isEmpty()) {
            System.out.println("No se registraron préstamos.");
        } else {
            for (Prestamo p : prestamos) {
                System.out.println(p);
            }
        }

        // --- Guardar en archivo de texto ---
        guardarEnArchivo(prestamos);
        System.out.println("Datos guardados en 'prestamos.txt'.");
    }

    /**
     * Guarda todos los préstamos registrados en el archivo 'prestamos.txt'.
     */
    static void guardarEnArchivo(List<Prestamo> prestamos) throws IOException {
        String archivo = "prestamos.txt";
        try (PrintWriter pw = new PrintWriter(new FileWriter(archivo))) {
            pw.println("=== Registro de Préstamos ===");
            pw.println("Generado: " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
            pw.println();
            if (prestamos.isEmpty()) {
                pw.println("No se registraron préstamos.");
            } else {
                for (Prestamo p : prestamos) {
                    pw.println(p);
                }
            }
            pw.printf("Crédito restante disponible: %.2f%n", Prestamo.creditoDisponible);
        }
    }
}
