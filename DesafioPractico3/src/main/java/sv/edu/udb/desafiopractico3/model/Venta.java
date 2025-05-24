package sv.edu.udb.desafiopractico3.model;

import java.util.Date;

public class Venta {
    private Integer idVenta;  
    private int idLinea;
    private Date fechaVenta;
    private String descripcion;

    // Getters
    public Integer getIdVenta() {
        return idVenta;
    }

    public int getIdLinea() {
        return idLinea;
    }

    public Date getFechaVenta() {
        return fechaVenta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    // Setters
    public void setIdVenta(Integer idVenta) {
        this.idVenta = idVenta;
    }

    public void setIdLinea(int idLinea) {
        this.idLinea = idLinea;
    }

    public void setFechaVenta(Date fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
