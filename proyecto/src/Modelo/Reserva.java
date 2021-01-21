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
    private Condicion_Hotel condicion;
    private Persona persona;

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

    public Condicion_Hotel getCondicion() {
        return condicion;
    }

    public void setCondicion(Condicion_Hotel condicion) {
        this.condicion = condicion;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }
}
