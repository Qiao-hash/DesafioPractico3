package sv.edu.udb.desafiopractico3.dao;

import sv.edu.udb.desafiopractico3.model.LineaVenta;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LineaVentaDAO {

    public List<LineaVenta> listar() throws SQLException {
        List<LineaVenta> lineas = new ArrayList<>();
        String sql = "SELECT * FROM lineas_de_venta";
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                LineaVenta linea = new LineaVenta();
                linea.setIdLinea(rs.getInt("id_linea"));
                linea.setLinea(rs.getString("Linea"));
                lineas.add(linea);
                System.out.println("Línea recuperada: " + linea.getIdLinea() + " - " + linea.getLinea()); // Debug
            }
        }
        return lineas;
    }

    public List<LineaVenta> obtenerTodas() {
        List<LineaVenta> lista = new ArrayList<>();
        String sql = "SELECT * FROM lineas_de_venta";
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                LineaVenta l = new LineaVenta();
                l.setIdLinea(rs.getInt("id_linea"));
                l.setLinea(rs.getString("Linea")); 
                lista.add(l);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Líneas obtenidas: " + lista.size());

        return lista;

    }

}
