package vallegrande.edu.pe.agrofrutoslambayeque.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private static final String URL = "jdbc:mysql://mysql-agrofrutos-mysql-db-2.a.aivencloud.com:19732/agrofrutos_db_2?sslMode=REQUIRED";
    private static final String USER = "avnadmin";

    private static final String PASSWORD = (System.getenv("DB_PASSWORD") != null && !System.getenv("DB_PASSWORD").isEmpty())
            ? System.getenv("DB_PASSWORD")
            : "AVNS_xMgLrD1qRLAgDNS_LhW";

    public static Connection getConexion() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}