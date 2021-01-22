package Modelo;

public class Tipo {
    private int k_tipo_habitacion;
    private double precio_habitacion;
    private String descripcion;

    public Tipo(int k_tipo_habitacion, double precio_habitacion, String descripcion) {
        this.k_tipo_habitacion = k_tipo_habitacion;
        this.precio_habitacion = precio_habitacion;
        this.descripcion = descripcion;
    }

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
}
