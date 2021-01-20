package Modelo;

import java.sql.Date;

public class Reserva {
    private int k_reserva;
    private String estado;
    private Date f_inicio;
    private Date f_reserva;
    private Date f_final;
    private int cantidad_huespedes;
    private double precio_reserva;
    private int k_condicion;

    public int getK_reserva() {
        return k_reserva;
    }

    public void setK_reserva(int k_reserva) {
        this.k_reserva = k_reserva;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getF_inicio() {
        return f_inicio;
    }

    public void setF_inicio(Date f_inicio) {
        this.f_inicio = f_inicio;
    }

    public Date getF_reserva() {
        return f_reserva;
    }

    public void setF_reserva(Date f_reserva) {
        this.f_reserva = f_reserva;
    }

    public Date getF_final() {
        return f_final;
    }

    public void setF_final(Date f_final) {
        this.f_final = f_final;
    }

    public int getCantidad_huespedes() {
        return cantidad_huespedes;
    }

    public void setCantidad_huespedes(int cantidad_huespedes) {
        this.cantidad_huespedes = cantidad_huespedes;
    }

    public double getPrecio_reserva() {
        return precio_reserva;
    }

    public void setPrecio_reserva(double precio_reserva) {
        this.precio_reserva = precio_reserva;
    }

    public int getK_condicion() {
        return k_condicion;
    }

    public void setK_condicion(int k_condicion) {
        this.k_condicion = k_condicion;
    }

    public int getK_identificacion() {
        return k_identificacion;
    }

    public void setK_identificacion(int k_identificacion) {
        this.k_identificacion = k_identificacion;
    }

    public String getK_tipo_documento() {
        return k_tipo_documento;
    }

    public void setK_tipo_documento(String k_tipo_documento) {
        this.k_tipo_documento = k_tipo_documento;
    }

    private int k_identificacion;
    private String k_tipo_documento;
}
