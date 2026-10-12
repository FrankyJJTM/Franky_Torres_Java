package vallegrande.edu.pe.agrofrutoslambayeque.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ContactoDao {

    public List<Contacto> listarContactos() {
        List<Contacto> lista = new ArrayList<>();
        String sql = "SELECT id, nombre, apellido, telefono, correo, mensaje, fecha_creacion FROM contactos";

        try (Connection conn = Conexion.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Contacto c = new Contacto();
                c.setId(rs.getInt("id"));
                c.setNombre(rs.getString("nombre"));
                c.setApellido(rs.getString("apellido"));
                c.setTelefono(rs.getString("telefono"));
                c.setCorreo(rs.getString("correo"));
                c.setMensaje(rs.getString("mensaje"));
                c.setFechaCreacion(rs.getTimestamp("fecha_creacion"));
                lista.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean agregarContacto(Contacto contacto) {
        String sql = "INSERT INTO contactos (nombre, apellido, telefono, correo, mensaje) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, contacto.getNombre());
            pstmt.setString(2, contacto.getApellido());
            pstmt.setString(3, contacto.getTelefono());
            pstmt.setString(4, contacto.getCorreo());
            pstmt.setString(5, contacto.getMensaje());

            return pstmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Contacto> buscarContactos(String criterio) {
        List<Contacto> lista = new ArrayList<>();
        String sql = "SELECT id, nombre, apellido, telefono, correo, mensaje, fecha_creacion " +
                "FROM contactos WHERE nombre LIKE ? OR apellido LIKE ? OR correo LIKE ?";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            String busqueda = "%" + criterio + "%";
            pstmt.setString(1, busqueda);
            pstmt.setString(2, busqueda);
            pstmt.setString(3, busqueda);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Contacto c = new Contacto();
                    c.setId(rs.getInt("id"));
                    c.setNombre(rs.getString("nombre"));
                    c.setApellido(rs.getString("apellido"));
                    c.setTelefono(rs.getString("telefono"));
                    c.setCorreo(rs.getString("correo"));
                    c.setMensaje(rs.getString("mensaje"));
                    c.setFechaCreacion(rs.getTimestamp("fecha_creacion"));
                    lista.add(c);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean actualizarContacto(Contacto contacto) {
        String sql = "UPDATE contactos SET nombre = ?, apellido = ?, telefono = ?, correo = ?, mensaje = ? WHERE id = ?";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, contacto.getNombre());
            pstmt.setString(2, contacto.getApellido());
            pstmt.setString(3, contacto.getTelefono());
            pstmt.setString(4, contacto.getCorreo());
            pstmt.setString(5, contacto.getMensaje());
            pstmt.setInt(6, contacto.getId());

            return pstmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarContacto(int id) {
        String sql = "DELETE FROM contactos WHERE id = ?";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            return pstmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // NUEVO MÉTODO PARA BORRAR TODOS LOS CONTACTOS
    public boolean eliminarTodos() {
        String sql = "DELETE FROM contactos";

        try (Connection conn = Conexion.getConexion();
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate(sql);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}