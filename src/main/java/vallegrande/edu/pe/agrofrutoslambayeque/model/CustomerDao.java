package vallegrande.edu.pe.agrofrutoslambayeque.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDao {

    public List<Customer> listar() {
        List<Customer> lista = new ArrayList<>();
        String sql = "SELECT * FROM Customer";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Customer c = new Customer();
                c.setCustomerId(rs.getInt("Customer_ID"));
                c.setName(rs.getString("Name"));
                c.setLastName(rs.getString("Last_Name"));
                c.setDocumentNumber(rs.getString("Document_Number"));
                c.setPhone(rs.getString("Phone"));
                c.setMail(rs.getString("Mail"));
                c.setAddress(rs.getString("Address"));
                c.setDocumentTypeId(rs.getObject("Document_Type_ID") != null ? rs.getInt("Document_Type_ID") : null);
                c.setActive(rs.getBoolean("Active"));
                lista.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean guardar(Customer c) {
        String sql = "INSERT INTO Customer (Name, Last_Name, Document_Number, Phone, Mail, Address, Document_Type_ID, Active) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, c.getName());
            ps.setString(2, c.getLastName());
            ps.setString(3, c.getDocumentNumber());
            ps.setString(4, c.getPhone());
            ps.setString(5, c.getMail());
            ps.setString(6, c.getAddress());

            // Asignación de Document_Type_ID según el documento ingresado
            Integer docTypeId = c.getDocumentTypeId();

            if (docTypeId == null && c.getDocumentNumber() != null) {
                String doc = c.getDocumentNumber().trim();
                if (doc.length() == 11) {
                    docTypeId = 2; // RUC
                } else {
                    docTypeId = 1; // DNI u otro
                }
            } else if (docTypeId == null) {
                docTypeId = 1; // Valor por defecto si viene completamente vacío
            }

            ps.setInt(7, docTypeId);
            ps.setBoolean(8, c.isActive());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizar(Customer c) {
        String sql = "UPDATE Customer SET Name = ?, Last_Name = ?, Document_Number = ?, Phone = ?, Mail = ?, Address = ?, Active = ? WHERE Customer_ID = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, c.getName());
            ps.setString(2, c.getLastName());
            ps.setString(3, c.getDocumentNumber());
            ps.setString(4, c.getPhone());
            ps.setString(5, c.getMail());
            ps.setString(6, c.getAddress());
            ps.setBoolean(7, c.isActive());
            ps.setInt(8, c.getCustomerId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Desactivar cliente (Borrado Lógico)
    public boolean eliminar(int id) {
        String sql = "UPDATE Customer SET Active = FALSE WHERE Customer_ID = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Permite cambiar el estado de un cliente dinámicamente (Activar/Desactivar)
    public boolean cambiarEstado(int id, boolean estado) {
        String sql = "UPDATE Customer SET Active = ? WHERE Customer_ID = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setBoolean(1, estado);
            ps.setInt(2, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}