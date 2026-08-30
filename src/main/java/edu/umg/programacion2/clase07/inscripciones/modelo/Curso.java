package edu.umg.programacion2.clase07.inscripciones.modelo;

public class Curso {

    private int id;
    private String nombre;
    private int creditos;

    public Curso(int id, String nombre, int creditos) {
        this.id = id;
        this.nombre = nombre;
        this.creditos = creditos;
    }

    public Curso(String nombre, int creditos) {
        this(0, nombre, creditos);
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCreditos() {
        return creditos;
    }

    @Override
    public String toString() {
        return String.format("[%d] %s (%d creditos)", id, nombre, creditos);
    }
}
