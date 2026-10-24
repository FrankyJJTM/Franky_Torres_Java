package vallegrande.edu.pe.agrofrutoslambayeque.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PartnerDao {

    public List<Partner> listar() {
        List<Partner> lista = new ArrayList<>();
        String sql = "SELECT * FROM Partner";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Partner p = new Partner();
                p.setMemberId(rs.getInt("Member_ID"));
                p.setDniRuc(rs.getString("DNI_RUC"));
                p.setMemberCode(rs.getString("Member_Code"));
                p.setPhone(rs.getString("Phone"));
                p.setAddress(rs.getString("Address"));
                p.setFirstNamesLastNames(rs.getString("First_Names_Last_Names"));
                p.setCertificationStatus(rs.getString("Certification_Status"));
                p.setDocumentTypeId(rs.getObject("Document_Type_ID") != null ? rs.getInt("Document_Type_ID") : null);
                lista.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean guardar(Partner p) {
        String sql = "INSERT INTO Partner (DNI_RUC, Member_Code, Phone, Address, First_Names_Last_Names, Certification_Status, Document_Type_ID) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, p.getDniRuc());
            ps.setString(2, p.getMemberCode());
            ps.setString(3, p.getPhone());
            ps.setString(4, p.getAddress());
            ps.setString(5, p.getFirstNamesLastNames());
            ps.setString(6, p.getCertificationStatus());

            // Asignación de Document_Type_ID
            Integer docTypeId = p.getDocumentTypeId();

            // Si llega como null, lo determinamos por la longitud del número de documento
            if (docTypeId == null && p.getDniRuc() != null) {
                String doc = p.getDniRuc().trim();
                if (doc.length() == 11) {
                    docTypeId = 2; // RUC
                } else {
                    docTypeId = 1; // DNI
                }
            } else if (docTypeId == null) {
                docTypeId = 1; // Valor por defecto en caso llegue totalmente vacío
            }

            ps.setInt(7, docTypeId);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizar(Partner p) {
        String sql = "UPDATE Partner SET DNI_RUC = ?, Member_Code = ?, Phone = ?, Address = ?, First_Names_Last_Names = ?, Certification_Status = ? WHERE Member_ID = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, p.getDniRuc());
            ps.setString(2, p.getMemberCode());
            ps.setString(3, p.getPhone());
            ps.setString(4, p.getAddress());
            ps.setString(5, p.getFirstNamesLastNames());
            ps.setString(6, p.getCertificationStatus());
            ps.setInt(7, p.getMemberId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM Partner WHERE Member_ID = ?";
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