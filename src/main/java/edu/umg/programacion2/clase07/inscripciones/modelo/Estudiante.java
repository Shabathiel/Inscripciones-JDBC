package edu.umg.programacion2.clase07.inscripciones.modelo;

/**
 * Igual a la clase Estudiante de la Clase 5: solo datos + encapsulamiento.
 */
public class Estudiante {

    private int id;
    private String nombre;
    private String carnet;

    public Estudiante(int id, String nombre, String carnet) {
        this.id = id;
        this.nombre = nombre;
        this.carnet = carnet;
    }

    public Estudiante(String nombre, String carnet) {
        this(0, nombre, carnet);
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCarnet() {
        return carnet;
    }

    @Override
    public String toString() {
        return String.format("[%d] %s - carnet %s", id, nombre, carnet);
    }
}
