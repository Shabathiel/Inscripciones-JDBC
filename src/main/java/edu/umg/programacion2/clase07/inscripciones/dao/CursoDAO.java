package edu.umg.programacion2.clase07.inscripciones.dao;

import edu.umg.programacion2.clase07.inscripciones.modelo.Curso;

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
 * Ya resuelto: mismo patron de EstudianteDAO, aplicado a la tabla cursos.
 * Tampoco es el foco de la tarea.
 */
public class CursoDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/prog2_db?useSSL=false&serverTimezone=UTC";
    private static final String USUARIO = "root";
    private static final String PASSWORD = "tu_password_aqui";

    public int crear(Curso curso) throws SQLException {
        String sql = "INSERT INTO cursos (nombre, creditos) VALUES (?, ?)";

        try (Connection conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
             PreparedStatement statement = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, curso.getNombre());
            statement.setInt(2, curso.getCreditos());
            statement.executeUpdate();

            try (ResultSet claves = statement.getGeneratedKeys()) {
                if (claves.next()) {
                    return claves.getInt(1);
                }
                return -1;
            }
        }
    }

    public List<Curso> listarTodos() throws SQLException {
        String sql = "SELECT id, nombre, creditos FROM cursos ORDER BY id";
        List<Curso> cursos = new ArrayList<>();

        try (Connection conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
             PreparedStatement statement = conexion.prepareStatement(sql);
             ResultSet resultado = statement.executeQuery()) {

            while (resultado.next()) {
                cursos.add(mapearFila(resultado));
            }
        }
        return cursos;
    }

    public Optional<Curso> buscarPorNombre(String nombre) throws SQLException {
        String sql = "SELECT id, nombre, creditos FROM cursos WHERE nombre = ?";

        try (Connection conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setString(1, nombre);

            try (ResultSet resultado = statement.executeQuery()) {
                if (resultado.next()) {
                    return Optional.of(mapearFila(resultado));
                }
                return Optional.empty();
            }
        }
    }

    private Curso mapearFila(ResultSet resultado) throws SQLException {
        int id = resultado.getInt("id");
        String nombre = resultado.getString("nombre");
        int creditos = resultado.getInt("creditos");
        return new Curso(id, nombre, creditos);
    }
}
