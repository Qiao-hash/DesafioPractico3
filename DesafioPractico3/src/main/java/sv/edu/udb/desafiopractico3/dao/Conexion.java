package sv.edu.udb.desafiopractico3.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/control_ventas?useUnicode=true&characterEncoding=UTF-8";            return DriverManager.getConnection(url, "root", "");
        } catch (ClassNotFoundException | SQLException e) {
            throw new SQLException("Error de conexión: " + e.getMessage());
        }
    }
}
