# Clase 7 - Cursos e Inscripciones (JDBC con relacion N:M) — Tarea

## Enunciado

Un estudiante puede inscribirse en varios cursos, y un curso puede tener
varios estudiantes inscritos: es una relacion **muchos-a-muchos (N:M)**, que
se modela con una tabla intermedia (`inscripciones`). Tu tarea es completar
`InscripcionDAO.java`, el DAO que resuelve esa relacion contra MySQL,
siguiendo el mismo patron de `EstudianteDAO`/`CursoDAO` (ya resueltos en este
proyecto) y de `clase05-jdbc-con-maven`.

## Preparacion

1. MySQL 8 corriendo en `localhost:3306`.
2. Ejecuta `sql/schema.sql`:
   ```bash
   mysql -u root -p < sql/schema.sql
   ```
3. Ajusta `USUARIO` y `PASSWORD` en `EstudianteDAO.java`, `CursoDAO.java` e
   `InscripcionDAO.java`.
4. `mvn compile` para verificar que compila (los metodos con TODO ya
   retornan un valor por defecto, asi que el proyecto compila desde el
   principio - lo que falta es que hagan lo correcto).
5. `mvn exec:java` para probar el menu.

## Que ya esta resuelto (no es parte de la tarea)

- `Estudiante`, `Curso`, `Inscripcion`: clases de dominio.
- `EstudianteDAO` y `CursoDAO`: CRUD basico, identico en estilo al de la
  Clase 5.
- `Main.java`: el menu completo, ya conectado a los metodos que vas a
  construir.

## Lo que tienes que construir: `InscripcionDAO`

Seis metodos, cada uno con: que debe hacer, un ejemplo de entrada/salida con
los datos de `sql/schema.sql`, y pistas (la consulta SQL o la idea, pero no
el codigo Java completo).

1. **`inscribir(estudianteId, cursoId)`** — INSERT en la tabla intermedia.
   Trae un caso nuevo: manejar la violacion de la restriccion `UNIQUE` con
   `SQLIntegrityConstraintViolationException`.
2. **`registrarNota(estudianteId, cursoId, nota)`** — UPDATE simple, puro
   repaso de la Clase 5.
3. **`listarCursosDeEstudiante(carnet)`** — tu primer `JOIN` de **tres**
   tablas.
4. **`listarEstudiantesDeCurso(nombreCurso)`** — el JOIN "espejo" del
   anterior.
5. **`promedioDeEstudiante(carnet)`** — tu primer contacto con una funcion
   de **agregacion SQL** (`AVG`), incluyendo el caso "sin notas todavia"
   (`NULL`).
6. **`cursoConMasInscritos()`** — `GROUP BY` + `COUNT` + `ORDER BY` +
   `LIMIT`, para que MySQL calcule el resultado en vez de traer todo a Java
   y contar a mano.

Repasa `PrestamoDAO.listarPrestamosActivosConLibro()` en el proyecto
`clase07-biblioteca-jdbc` (actividad de clase) si necesitas un ejemplo ya
resuelto de como mapear un `ResultSet` que viene de un `JOIN`.

## Criterios de evaluacion

- Los 6 metodos de `InscripcionDAO` compilan y no cambian la firma que ya
  tienen (el `Main.java` dado depende de esas firmas exactas).
- Cada metodo produce la salida documentada en su propio javadoc, probada
  desde el menu de `Main.java` con los datos de `sql/schema.sql`.
- `inscribir(...)` no lanza una excepcion sin manejar cuando el estudiante ya
  esta inscrito en ese curso - debe retornar `-1` en ese caso.
- `promedioDeEstudiante(...)` retorna `Optional.empty()` (no lanza una
  excepcion ni retorna `null`) cuando el estudiante no tiene notas.

## Idea clave de la tarea

Una relacion 1:N (como libros -> prestamos, en `clase07-biblioteca-jdbc`) se
resuelve con una FK en la tabla "del lado muchos". Una relacion **N:M**
necesita una tabla intermedia propia (`inscripciones`), con una FK hacia
CADA una de las dos tablas relacionadas. Esa tabla intermedia es ademas el
lugar natural para guardar datos que pertenecen a la RELACION en si (aqui, la
`nota`) y no a ninguno de los dos lados por separado.

## Desafio opcional

Agrega un metodo `InscripcionDAO.estudiantesSinNota()` que liste los
estudiantes con al menos una inscripcion cuya `nota` sea `NULL` (pista:
`WHERE nota IS NULL`, y recuerda evitar estudiantes repetidos si aparecen en
mas de una fila - investiga `SELECT DISTINCT`).
