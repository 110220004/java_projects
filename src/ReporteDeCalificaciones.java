import java.util.Scanner;

public class ReporteDeCalificaciones {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int total = 0;
        int contadorCalificaciones = 0;
        int aCount = 0;
        int bCount = 0;
        int cCount = 0;
        int dCount = 0;
        int fCount = 0;

        System.out.println("Ingrese las calificaciones (0-100).");
        while (true) {
            System.out.print("Ingrese la siguiente calificación: ");
            int calificacion = input.nextInt();
            total += calificacion;
            contadorCalificaciones++;

            // Incrementar el contador de letras según la calificación
            if (calificacion >= 90) {
                aCount++;
            } else if (calificacion >= 80) {
                bCount++;
            } else if (calificacion >= 70) {
                cCount++;
            } else if (calificacion >= 60) {
                dCount++;
            } else {
                fCount++;
            }

            // Preguntar al usuario si desea continuar
            System.out.print("¿Desea ingresar otra calificación? (si/no): ");
            String respuesta = input.next();
            if (respuesta.equalsIgnoreCase("no")) {
                break;
            }
        }

        double promedio = (double) total / contadorCalificaciones;

        // Mostrar el reporte
        System.out.println("\nReporte de calificaciones:");
        System.out.println("El total de las " + contadorCalificaciones + " calificaciones introducidas es " + total);
        System.out.printf("El promedio de la clase es %.2f%n", promedio);
        System.out.println("Número de estudiantes que recibieron cada calificación:");
        System.out.println("A: " + aCount);
        System.out.println("B: " + bCount);
        System.out.println("C: " + cCount);
        System.out.println("D: " + dCount);
        System.out.println("F: " + fCount);
    }
}
