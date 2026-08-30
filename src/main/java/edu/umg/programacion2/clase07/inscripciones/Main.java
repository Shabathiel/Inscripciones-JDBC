package edu.umg.programacion2.clase07.inscripciones;

import edu.umg.programacion2.clase07.inscripciones.dao.CursoDAO;
import edu.umg.programacion2.clase07.inscripciones.dao.EstudianteDAO;
import edu.umg.programacion2.clase07.inscripciones.dao.InscripcionDAO;
import edu.umg.programacion2.clase07.inscripciones.modelo.Curso;
import edu.umg.programacion2.clase07.inscripciones.modelo.Estudiante;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * El menu ya esta completo: tu trabajo es implementar InscripcionDAO para
 * que estas opciones funcionen. Corre el programa despues de completar cada
 * metodo para probarlo contra los datos de sql/schema.sql.
 */
public class Main {

    private static final Scanner teclado = new Scanner(System.in);
    private static final EstudianteDAO estudianteDAO = new EstudianteDAO();
    private static final CursoDAO cursoDAO = new CursoDAO();
    private static final InscripcionDAO inscripcionDAO = new InscripcionDAO();

    public static void main(String[] args) {
        int opcion;

        do {
            mostrarMenu();
            opcion = leerOpcion();

            switch (opcion) {
                case 1:
                    listarEstudiantesYCursos();
                    break;
                case 2:
                    inscribirEstudiante();
                    break;
                case 3:
                    registrarNota();
                    break;
                case 4:
                    listarCursosDeEstudiante();
                    break;
                case 5:
                    listarEstudiantesDeCurso();
                    break;
                case 6:
                    promedioDeEstudiante();
                    break;
                case 7:
                    cursoConMasInscritos();
                    break;
                case 8:
                    System.out.println("Hasta luego.");
                    break;
                default:
                    System.out.println("Opcion invalida. Intenta de nuevo.");
            }
            System.out.println();
        } while (opcion != 8);

        teclado.close();
    }

    private static void mostrarMenu() {
        System.out.println("=== Cursos e Inscripciones (MySQL) ===");
        System.out.println("1. Listar estudiantes y cursos disponibles");
        System.out.println("2. Inscribir estudiante en un curso");
        System.out.println("3. Registrar nota de un estudiante en un curso");
        System.out.println("4. Ver los cursos de un estudiante (por carnet)");
        System.out.println("5. Ver los estudiantes de un curso (por nombre)");
        System.out.println("6. Ver el promedio de un estudiante (por carnet)");
        System.out.println("7. Ver el curso con mas inscritos");
        System.out.println("8. Salir");
        System.out.print("Elige una opcion: ");
    }

    private static int leerOpcion() {
        while (!teclado.hasNextInt()) {
            System.out.print("Escribe un numero valido: ");
            teclado.next();
        }
        int opcion = teclado.nextInt();
        teclado.nextLine();
        return opcion;
    }

    private static void listarEstudiantesYCursos() {
        try {
            System.out.println("-- Estudiantes --");
            for (Estudiante estudiante : estudianteDAO.listarTodos()) {
                System.out.println(estudiante);
            }
            System.out.println("-- Cursos --");
            for (Curso curso : cursoDAO.listarTodos()) {
                System.out.println(curso);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar: " + e.getMessage());
        }
    }

    private static void inscribirEstudiante() {
        System.out.print("ID del estudiante: ");
        int estudianteId = leerEntero();
        System.out.print("ID del curso: ");
        int cursoId = leerEntero();

        try {
            int id = inscripcionDAO.inscribir(estudianteId, cursoId);
            if (id == -1) {
                System.out.println("No se pudo inscribir (ya estaba inscrito, o el metodo aun no esta completo).");
            } else {
                System.out.println("Inscripcion creada con id " + id);
            }
        } catch (SQLException e) {
            System.err.println("Error al inscribir: " + e.getMessage());
        }
    }

    private static void registrarNota() {
        System.out.print("ID del estudiante: ");
        int estudianteId = leerEntero();
        System.out.print("ID del curso: ");
        int cursoId = leerEntero();
        System.out.print("Nota: ");
        double nota = Double.parseDouble(teclado.nextLine());

        try {
            boolean actualizado = inscripcionDAO.registrarNota(estudianteId, cursoId, nota);
            System.out.println(actualizado ? "Nota registrada." : "Esa inscripcion no existe.");
        } catch (SQLException e) {
            System.err.println("Error al registrar la nota: " + e.getMessage());
        }
    }

    private static void listarCursosDeEstudiante() {
        System.out.print("Carnet del estudiante: ");
        String carnet = teclado.nextLine();

        try {
            List<Curso> cursos = inscripcionDAO.listarCursosDeEstudiante(carnet);
            if (cursos.isEmpty()) {
                System.out.println("Ese estudiante no tiene cursos (o el metodo aun no esta completo).");
                return;
            }
            for (Curso curso : cursos) {
                System.out.println(curso);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar los cursos: " + e.getMessage());
        }
    }

    private static void listarEstudiantesDeCurso() {
        System.out.print("Nombre del curso: ");
        String nombreCurso = teclado.nextLine();

        try {
            List<Estudiante> estudiantes = inscripcionDAO.listarEstudiantesDeCurso(nombreCurso);
            if (estudiantes.isEmpty()) {
                System.out.println("Ese curso no tiene estudiantes (o el metodo aun no esta completo).");
                return;
            }
            for (Estudiante estudiante : estudiantes) {
                System.out.println(estudiante);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar los estudiantes: " + e.getMessage());
        }
    }

    private static void promedioDeEstudiante() {
        System.out.print("Carnet del estudiante: ");
        String carnet = teclado.nextLine();

        try {
            Optional<Double> promedio = inscripcionDAO.promedioDeEstudiante(carnet);
            if (promedio.isPresent()) {
                System.out.println("Promedio: " + promedio.get());
            } else {
                System.out.println("Ese estudiante no tiene notas registradas todavia.");
            }
        } catch (SQLException e) {
            System.err.println("Error al calcular el promedio: " + e.getMessage());
        }
    }

    private static void cursoConMasInscritos() {
        try {
            Optional<String> curso = inscripcionDAO.cursoConMasInscritos();
            if (curso.isPresent()) {
                System.out.println("Curso con mas inscritos: " + curso.get());
            } else {
                System.out.println("Todavia no hay inscripciones registradas.");
            }
        } catch (SQLException e) {
            System.err.println("Error al calcular el curso con mas inscritos: " + e.getMessage());
        }
    }

    private static int leerEntero() {
        while (!teclado.hasNextInt()) {
            System.out.print("Escribe un numero valido: ");
            teclado.next();
        }
        int valor = teclado.nextInt();
        teclado.nextLine();
        return valor;
    }
}
