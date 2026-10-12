package vallegrande.edu.pe.agrofrutoslambayeque.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private static final String URL = "jdbc:mysql://mysql-agrofrutos-mysql-db-2.a.aivencloud.com:19732/agrofrutos_db_2?sslMode=REQUIRED";
    private static final String USER = "avnadmin";

    public static Connection getConexion() throws SQLException {
        String pass = System.getenv("DB_PASSWORD");

        // Si no hay variable de entorno, usamos la clave directa
        if (pass == null || pass.trim().isEmpty()) {
            pass = "AVNS_xMgLrOlqRLAGdN3_LhW";
        } else {
            pass = pass.trim();
        }

        // IMPRIMIR PARA VERIFICAR EN CONSOLA (solo para pruebas)
        System.out.println("CLAVE ENVIADA: [" + pass + "]");

        return DriverManager.getConnection(URL, USER, pass);
    }
}