package Modelo.entidades;

import DatosSQL.Operaciones;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Tipo {
    private int k_tipo_habitacion;
    private double precio_habitacion;
    private String descripcion;

    public Tipo(int k_tipo_habitacion, double precio_habitacion, String descripcion) {
        this.k_tipo_habitacion = k_tipo_habitacion;
        this.precio_habitacion = precio_habitacion;
        this.descripcion = descripcion;
    }

    public Tipo(){};

    public int getK_tipo_habitacion() {
        return k_tipo_habitacion;
    }

    public void setK_tipo_habitacion(int k_tipo_habitacion) {
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

    public Tipo ConsultarTipoHab(int ID, Tipo tipo){
        Operaciones op = new Operaciones();
        try {
            ResultSet resultSet = op.ConsultaEsp("SELECT * FROM tipo WHERE k_tipo_habitacion = "+ID+"");
            resultSet.next();

            tipo.setK_tipo_habitacion(resultSet.getInt(1));
            tipo.setPrecio_habitacion(resultSet.getDouble(2));
            tipo.setDescripcion(resultSet.getString(3));

            return tipo;

        }catch (SQLException ex){
            System.out.println(ex);
        }
        return null;
    }
}
