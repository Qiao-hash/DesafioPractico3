package sv.edu.udb.desafiopractico3.dao;

import sv.edu.udb.desafiopractico3.model.Venta;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VentaDAO {
    public List<Venta> listar() throws SQLException {
        List<Venta> ventas = new ArrayList<>();
        String sql = "SELECT v.id_venta, v.id_linea, v.fecha_venta, v.descripcion, l.Linea "
                + "FROM ventas v "
                + "INNER JOIN lineas_de_venta l ON v.id_linea = l.id_linea";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Venta venta = new Venta();
                venta.setIdVenta(rs.getInt("id_venta"));
                venta.setIdLinea(rs.getInt("id_linea"));
                venta.setFechaVenta(rs.getDate("fecha_venta"));
                venta.setDescripcion(rs.getString("descripcion"));
                ventas.add(venta);
            }
        } catch (SQLException e) {
            throw new SQLException("Error al listar ventas", e);
        }
        return ventas;
    }

    public void insertar(Venta venta) throws SQLException {
        String sql = "INSERT INTO ventas (id_linea, fecha_venta, descripcion) VALUES (?, ?, ?)";
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            System.out.println("Insertando venta con: idLinea=" + venta.getIdLinea()
                    + ", fecha=" + venta.getFechaVenta()
                    + ", descripcion=" + venta.getDescripcion());

            ps.setInt(1, venta.getIdLinea());
            ps.setDate(2, new java.sql.Date(venta.getFechaVenta().getTime()));
            ps.setString(3, venta.getDescripcion());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error al insertar venta: " + e.getMessage(), e);
        }
    }

    public void actualizar(Venta venta) throws SQLException {
        if (venta.getFechaVenta() == null) {
            throw new SQLException("La fecha de venta no puede ser nula");
        }
        if (venta.getIdVenta() <= 0) {
            throw new SQLException("El ID de la venta es inválido");
        }

        String sql = "UPDATE ventas SET id_linea = ?, fecha_venta = ?, descripcion = ? WHERE id_venta = ?";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, venta.getIdLinea());
            ps.setDate(2, new java.sql.Date(venta.getFechaVenta().getTime()));
            ps.setString(3, venta.getDescripcion());
            ps.setInt(4, venta.getIdVenta());

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated == 0) {
                throw new SQLException("No se encontró la venta con id " + venta.getIdVenta());
            }
        } catch (SQLException e) {
            throw new SQLException("Error al actualizar venta", e);
        }
    }


    public void eliminar(int idVenta) throws SQLException {
        String sql = "DELETE FROM ventas WHERE id_venta = ?";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idVenta);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error al eliminar venta", e);
        }
    }

    public Venta obtenerPorId(int idVenta) throws SQLException {
        String sql = "SELECT * FROM ventas WHERE id_venta = ?";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idVenta);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Venta venta = new Venta();
                    venta.setIdVenta(rs.getInt("id_venta"));
                    venta.setIdLinea(rs.getInt("id_linea"));
                    venta.setFechaVenta(rs.getDate("fecha_venta"));
                    venta.setDescripcion(rs.getString("descripcion"));
                    return venta;
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error al obtener venta", e);
        }
        return null;
    }

}
