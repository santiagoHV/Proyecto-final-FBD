package Modelo.entidades;

import DatosSQL.Operaciones;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Tipo {
    private String k_tipo_habitacion;
    private double precio_habitacion;
    private String descripcion;
    private int cupo;

    public Tipo(String k_tipo_habitacion, double precio_habitacion, String descripcion, int cupo) {
        this.k_tipo_habitacion = k_tipo_habitacion;
        this.precio_habitacion = precio_habitacion;
        this.descripcion = descripcion;
        this.cupo = cupo;
    }

    public Tipo(){};

    public String getK_tipo_habitacion() {
        return k_tipo_habitacion;
    }

    public void setK_tipo_habitacion(String k_tipo_habitacion) {
        this.k_tipo_habitacion = k_tipo_habitacion;
    }

    public double getPrecio_habitacion() {
        return precio_habitacion;
    }

    public void setPrecio_habitacion(double precio_habitacion) {
        this.precio_habitacion = precio_habitacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCupo() {
        return cupo;
    }

    public void setCupo(int cupo) {
        this.cupo = cupo;
    }
}
