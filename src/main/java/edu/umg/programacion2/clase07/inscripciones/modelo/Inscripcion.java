package edu.umg.programacion2.clase07.inscripciones.modelo;

/**
 * Representa UNA fila de la tabla intermedia `inscripciones`: la relacion
 * entre un estudiante y un curso especifico, mas su nota (si ya la tiene).
 *
 * IMPORTANTE: esta clase es el equivalente en Java de la tabla intermedia
 * que resuelve la relacion muchos-a-muchos entre Estudiante y Curso. Sin
 * esta tabla (y esta clase) no habria forma de decir "cual nota tiene ESTE
 * estudiante en ESTE curso" - un estudiante puede tener notas distintas en
 * cursos distintos.
 */
public class Inscripcion {

    private int id;
    private int estudianteId;
    private int cursoId;
    private Double nota; // Double (no double): null significa "sin nota todavia".

    public Inscripcion(int id, int estudianteId, int cursoId, Double nota) {
        this.id = id;
        this.estudianteId = estudianteId;
        this.cursoId = cursoId;
        this.nota = nota;
    }

    // Constructor de conveniencia para inscribir a alguien sin nota todavia.
    public Inscripcion(int estudianteId, int cursoId) {
        this(0, estudianteId, cursoId, null);
    }

    public int getId() {
        return id;
    }

    public int getEstudianteId() {
        return estudianteId;
    }

    public int getCursoId() {
        return cursoId;
    }

    public Double getNota() {
        return nota;
    }

    @Override
    public String toString() {
        String notaTexto = (nota == null) ? "sin nota" : String.valueOf(nota);
        return String.format("[%d] estudiante #%d - curso #%d (%s)", id, estudianteId, cursoId, notaTexto);
    }
}
