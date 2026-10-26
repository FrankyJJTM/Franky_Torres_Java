package vallegrande.edu.pe.agrofrutoslambayeque.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private static final String URL = "jdbc:mysql://127.0.0.1:3307/agrofrutos_db_2";
    private static final String USER = "root";
    private static final String PASS = "12345";

    public static Connection getConexion() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}