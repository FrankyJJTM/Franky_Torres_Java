package vallegrande.edu.pe.agrofrutoslambayeque.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDao {

    public List<Product> listar() {
        List<Product> lista = new ArrayList<>();
        String sql = "SELECT * FROM Product";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Product p = new Product();
                p.setProductId(rs.getInt("Product_ID"));
                p.setProductName(rs.getString("Product_Name"));
                p.setVariety(rs.getString("Variety"));
                p.setCertificationType(rs.getString("Certification_Type"));
                lista.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean guardar(Product p) {
        String sql = "INSERT INTO Product (Product_Name, Variety, Certification_Type) VALUES (?, ?, ?)";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, p.getProductName() != null ? p.getProductName() : "");
            ps.setString(2, p.getVariety() != null ? p.getVariety() : "");
            ps.setString(3, p.getCertificationType() != null ? p.getCertificationType() : "");

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizar(Product p) {
        String sql = "UPDATE Product SET Product_Name = ?, Variety = ?, Certification_Type = ? WHERE Product_ID = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, p.getProductName() != null ? p.getProductName() : "");
            ps.setString(2, p.getVariety() != null ? p.getVariety() : "");
            ps.setString(3, p.getCertificationType() != null ? p.getCertificationType() : "");
            ps.setInt(4, p.getProductId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM Product WHERE Product_ID = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}