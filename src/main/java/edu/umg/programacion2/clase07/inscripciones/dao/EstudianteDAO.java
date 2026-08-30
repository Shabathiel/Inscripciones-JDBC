package edu.umg.programacion2.clase07.inscripciones.dao;

import edu.umg.programacion2.clase07.inscripciones.modelo.Estudiante;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Ya resuelto: es el mismo CRUD de la Clase 5, solo que ahora vive en este
 * proyecto. No es el foco de la tarea - lo que tienes que construir es
 * InscripcionDAO.
 */
public class EstudianteDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/prog2_db?useSSL=false&serverTimezone=UTC";
    private static final String USUARIO = "root";
    private static final String PASSWORD = "tu_password_aqui";

    public int crear(Estudiante estudiante) throws SQLException {
        String sql = "INSERT INTO estudiantes (nombre, carnet) VALUES (?, ?)";

        try (Connection conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
             PreparedStatement statement = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, estudiante.getNombre());
            statement.setString(2, estudiante.getCarnet());
            statement.executeUpdate();

            try (ResultSet claves = statement.getGeneratedKeys()) {
                if (claves.next()) {
                    return claves.getInt(1);
                }
                return -1;
            }
        }
    }

    public List<Estudiante> listarTodos() throws SQLException {
        String sql = "SELECT id, nombre, carnet FROM estudiantes ORDER BY id";
        List<Estudiante> estudiantes = new ArrayList<>();

        try (Connection conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
             PreparedStatement statement = conexion.prepareStatement(sql);
             ResultSet resultado = statement.executeQuery()) {

            while (resultado.next()) {
                estudiantes.add(mapearFila(resultado));
            }
        }
        return estudiantes;
    }

    public Optional<Estudiante> buscarPorCarnet(String carnet) throws SQLException {
        String sql = "SELECT id, nombre, carnet FROM estudiantes WHERE carnet = ?";

        try (Connection conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setString(1, carnet);

            try (ResultSet resultado = statement.executeQuery()) {
                if (resultado.next()) {
                    return Optional.of(mapearFila(resultado));
                }
                return Optional.empty();
            }
        }
    }

    private Estudiante mapearFila(ResultSet resultado) throws SQLException {
        int id = resultado.getInt("id");
        String nombre = resultado.getString("nombre");
        String carnet = resultado.getString("carnet");
        return new Estudiante(id, nombre, carnet);
    }
}
