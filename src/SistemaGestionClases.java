import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class SistemaGestionClases {

    static abstract class Usuario {
        String nombre;
        String contraseña;

        Usuario(String nombre, String contraseña) {
            this.nombre = nombre;
            this.contraseña = contraseña;
        }

        abstract void mostrarMenu(Scanner scanner, DateTimeFormatter dtf);
    }

    static class Admin extends Usuario {
        Admin(String nombre, String contraseña) {
            super(nombre, contraseña);
        }

        @Override
        void mostrarMenu(Scanner scanner, DateTimeFormatter dtf) {
            System.out.println("Menú de Administrador");
            String opcion;

            do {
                System.out.println("\nSeleccione una opción:");
                System.out.println("1. Agregar clase");
                System.out.println("2. Borrar clase");
                System.out.println("3. Ver todas las clases");
                System.out.println("4. Volver al menú principal");

                opcion = scanner.nextLine();

                switch (opcion) {
                    case "1":
                        agregarClase(scanner, dtf);
                        break;
                    case "2":
                        borrarClase(scanner);
                        break;
                    case "3":
                        verTodasLasClases();
                        break;
                    case "4":
                        System.out.println("Regresando al menú principal.");
                        return;
                    default:
                        System.out.println("Opción inválida. Por favor, intente de nuevo.");
                }
            } while (true);
        }

        private void agregarClase(Scanner scanner, DateTimeFormatter dtf) {
            System.out.println("Ingrese el título de la clase:");
            String titulo = scanner.nextLine();

            System.out.println("Ingrese la fecha de inicio (yyyy-MM-dd):");
            LocalDate inicio = LocalDate.parse(scanner.nextLine(), dtf);

            System.out.println("Ingrese la fecha de fin (yyyy-MM-dd):");
            LocalDate fin = LocalDate.parse(scanner.nextLine(), dtf);

            Profesor profesor = seleccionarProfesor(scanner);

            if (profesor != null) {
                Clase nuevaClase = new Clase(titulo, inicio, fin, profesor);
                clases.add(nuevaClase);
                System.out.println("Clase agregada exitosamente.");
            } else {
                System.out.println("No se pudo agregar la clase. Profesor no encontrado.");
            }
        }

        private void borrarClase(Scanner scanner) {
            if (clases.isEmpty()) {
                System.out.println("No hay clases disponibles para borrar.");
                return;
            }

            System.out.println("Seleccione la clase a borrar (0 para regresar):");
            for (int i = 0; i < clases.size(); i++) {
                System.out.println((i + 1) + ". " + clases.get(i).titulo);
            }

            int eleccion = Integer.parseInt(scanner.nextLine());
            if (eleccion == 0) {
                System.out.println("Regresando al menú anterior.");
                return;
            } else if (eleccion > 0 && eleccion <= clases.size()) {
                clases.remove(eleccion - 1);
                System.out.println("Clase borrada exitosamente.");
            } else {
                System.out.println("Selección inválida.");
            }
        }


        private void verTodasLasClases() {
            if (clases.isEmpty()) {
                System.out.println("No hay clases disponibles.");
            } else {
                for (Clase clase : clases) {
                    clase.mostrarInformacionClase();
                }
            }
        }

        private Profesor seleccionarProfesor(Scanner scanner) {
            System.out.println("Seleccione un profesor:");
            for (int i = 0; i < profesores.size(); i++) {
                System.out.println((i + 1) + ". " + profesores.get(i).nombre);
            }

            int eleccion = Integer.parseInt(scanner.nextLine());
            if (eleccion > 0 && eleccion <= profesores.size()) {
                return profesores.get(eleccion - 1);
            } else {
                System.out.println("Selección inválida.");
                return null;
            }
        }
    }

    static class Estudiante extends Usuario {
        String email;
        String telefono;

        Estudiante(String nombre, String email, String telefono, String contraseña) {
            super(nombre, contraseña);
            this.email = email;
            this.telefono = telefono;
        }

        @Override
        void mostrarMenu(Scanner scanner, DateTimeFormatter dtf) {
            System.out.println("Menú de Estudiante");
            String opcion;

            do {
                System.out.println("\nSeleccione una opción:");
                System.out.println("1. Ver clases disponibles");
                System.out.println("2. Inscribirse en una clase");
                System.out.println("3. Ver clases matriculadas");
                System.out.println("4. Volver al menú principal");

                opcion = scanner.nextLine();

                switch (opcion) {
                    case "1":
                        verClases();
                        break;
                    case "2":
                        inscribirseEnClase(scanner);
                        break;
                    case "3":
                        verClasesMatriculadas();
                        break;
                    case "4":
                        System.out.println("Regresando al menú principal.");
                        return;
                    default:
                        System.out.println("Opción inválida. Por favor, intente de nuevo.");
                }
            } while (true);
        }

        private void inscribirseEnClase(Scanner scanner) {
            if (clases.isEmpty()) {
                System.out.println("No hay clases disponibles para inscribirse.");
                return;
            }

            System.out.println("Seleccione la clase en la que desea inscribirse (0 para regresar):");
            for (int i = 0; i < clases.size(); i++) {
                System.out.println((i + 1) + ". " + clases.get(i).titulo);
            }

            int eleccion = Integer.parseInt(scanner.nextLine());
            if (eleccion == 0) {
                System.out.println("Regresando al menú anterior.");
                return;
            } else if (eleccion > 0 && eleccion <= clases.size()) {
                Clase claseSeleccionada = clases.get(eleccion - 1);
                claseSeleccionada.agregarEstudiante(this);
                System.out.println("Inscripción realizada con éxito en: " + claseSeleccionada.titulo);
            } else {
                System.out.println("Selección inválida.");
            }
        }


        void verClases() {
            if (clases.isEmpty()) {
                System.out.println("No hay clases disponibles.");
                return;
            }
            for (Clase clase : clases) {
                System.out.println(clase.titulo + " - Profesor: " + clase.profesor.nombre);
            }
        }

        private void verClasesMatriculadas() {
            boolean matriculado = false;
            for (Clase clase : clases) {
                if (clase.estudiantes.contains(this)) {
                    System.out.println(clase.titulo + " - Profesor: " + clase.profesor.nombre);
                    matriculado = true;
                }
            }
            if (!matriculado) {
                System.out.println("No estás matriculado en ninguna clase.");
            }
        }
    }

    static class Invitado extends Usuario {
        Invitado() {
            super("Invitado", "");
        }

        @Override
        void mostrarMenu(Scanner scanner, DateTimeFormatter dtf) {
            System.out.println("Menú de Invitado");
            String opcion;

            do {
                System.out.println("\nSeleccione una opción:");
                System.out.println("1. Ver clases disponibles");
                System.out.println("2. Volver al menú principal");

                opcion = scanner.nextLine();

                switch (opcion) {
                    case "1":
                        verClases();
                        break;
                    case "2":
                        System.out.println("Regresando al menú principal.");
                        return;
                    default:
                        System.out.println("Opción inválida. Por favor, intente de nuevo.");
                }
            } while (true);
        }

        void verClases() {
            for (Clase clase : clases) {
                System.out.println(clase.titulo);
            }
        }
    }

    static class Profesor extends Usuario {
        String email;
        String telefono;

        Profesor(String nombre, String email, String telefono) {
            super(nombre, "");
            this.email = email;
            this.telefono = telefono;
        }

        @Override
        void mostrarMenu(Scanner scanner, DateTimeFormatter dtf) {
            System.out.println("Menú de Profesor");
            String opcion;

            do {
                System.out.println("\nSeleccione una opción:");
                System.out.println("1. Ver mis clases");
                System.out.println("2. Volver al menú principal");

                opcion = scanner.nextLine();

                switch (opcion) {
                    case "1":
                        verMisClases();
                        break;
                    case "2":
                        System.out.println("Regresando al menú principal.");
                        return;
                    default:
                        System.out.println("Opción inválida. Por favor, intente de nuevo.");
                }
            } while (true);
        }

        void verMisClases() {
            for (Clase clase : clases) {
                if (clase.profesor.equals(this)) {
                    clase.mostrarInformacionClase();
                }
            }
        }
    }

    static class Clase {
        String titulo;
        LocalDate fechaInicio;
        LocalDate fechaFin;
        Profesor profesor;
        List<Estudiante> estudiantes;

        Clase(String titulo, LocalDate fechaInicio, LocalDate fechaFin, Profesor profesor) {
            this.titulo = titulo;
            this.fechaInicio = fechaInicio;
            this.fechaFin = fechaFin;
            this.profesor = profesor;
            this.estudiantes = new ArrayList<>();
        }

        void agregarEstudiante(Estudiante estudiante) {
            estudiantes.add(estudiante);
        }

        void mostrarInformacionClase() {
            System.out.println("Título: " + titulo);
            System.out.println("Fecha de inicio: " + fechaInicio);
            System.out.println("Fecha de fin: " + fechaFin);
            System.out.println("Profesor: " + profesor.nombre);
        }
    }

    static Map<String, Usuario> usuarios = new HashMap<>();
    static List<Clase> clases = new ArrayList<>();
    static List<Profesor> profesores = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        inicializarUsuarios();
        inicializarProfesores();

        System.out.println("Bienvenido al Sistema de Gestión de Clases");

        while (true) {
            System.out.println("\nSeleccione el tipo de usuario:\n1. Admin\n2. Estudiante\n3. Invitado\n4. Salir");
            String tipoUsuario = scanner.nextLine();

            switch (tipoUsuario) {
                case "1":
                    if (confirmarIngreso(scanner, "Admin")) {
                        Usuario usuarioAdmin = autenticarUsuario(scanner, "Admin");
                        if (usuarioAdmin != null) {
                            usuarioAdmin.mostrarMenu(scanner, dtf);
                        }
                    }
                    break;
                case "2":
                    if (confirmarIngreso(scanner, "Estudiante")) {
                        Usuario usuarioEstudiante = autenticarUsuario(scanner, "Estudiante");
                        if (usuarioEstudiante != null) {
                            usuarioEstudiante.mostrarMenu(scanner, dtf);
                        }
                    }
                    break;
                case "3":
                    new Invitado().mostrarMenu(scanner, dtf);
                    break;
                case "4":
                    System.out.println("Saliendo del sistema.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Opción inválida.");
                    break;
            }
        }
    }

    static void inicializarUsuarios() {
        usuarios.put("admin", new Admin("Admin", "1234"));
        usuarios.put("estudiante", new Estudiante("Estudiante", "estudiante@mail.com", "1234567890", "4321"));
        // Añadir más usuarios aquí si es necesario
    }

    static void inicializarProfesores() {
        profesores.add(new Profesor("Profesor1", "profesor1@mail.com", "1234567890"));
        profesores.add(new Profesor("Profesor2", "profesor2@mail.com", "0987654321"));
        profesores.add(new Profesor("Profesor3", "profesor3@mail.com", "2658743156"));
        // Añadir más profesores aquí si es necesario
    }

    static boolean confirmarIngreso(Scanner scanner, String tipoUsuario) {
        System.out.println("¿Desea ingresar como " + tipoUsuario + "? (si/no)");
        String respuesta = scanner.nextLine();
        if (respuesta.equalsIgnoreCase("si")) {
            return true;
        } else {
            System.out.println("Regresando al menú principal.");
            return false;
        }
    }

    static Usuario autenticarUsuario(Scanner scanner, String tipoUsuario) {
        System.out.println("Ingrese su contraseña:");
        String contraseña = scanner.nextLine();

        for (Usuario usuario : usuarios.values()) {
            if (usuario instanceof Admin && tipoUsuario.equals("Admin") && usuario.contraseña.equals(contraseña)) {
                return usuario;
            } else if (usuario instanceof Estudiante && tipoUsuario.equals("Estudiante") && usuario.contraseña.equals(contraseña)) {
                return usuario;
            }
        }

        System.out.println("Contraseña incorrecta o usuario no encontrado.");
        return null;
    }
}