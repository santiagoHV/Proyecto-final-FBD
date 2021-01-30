package Modelo.entidades;

import DatosSQL.Operaciones;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

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

    public Reserva(int k_reserva, String estado, Date f_inicio, Date f_reserva, Date f_final, int cantidad_huespedes, double precio_reserva, Condicion_Hotel condicion, Persona persona) {
        this.k_reserva = k_reserva;
        this.estado = estado;
        this.f_inicio = f_inicio;
        this.f_reserva = f_reserva;
        this.f_final = f_final;
        this.cantidad_huespedes = cantidad_huespedes;
        this.precio_reserva = precio_reserva;
        this.condicion = condicion;
        this.persona = persona;
    }

    public Reserva(){};

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

    public Reserva ConsultarReserva(int ID, Reserva reserva){
        Operaciones op = new Operaciones();
        try {
            ResultSet resultSet = op.ConsultaEsp("SELECT * FROM Reserva WHERE k_reserva = "+ID+"");
            resultSet.next();
            reserva.setK_reserva(resultSet.getInt(1));
            reserva.setEstado(resultSet.getString(2));
            reserva.setF_inicio(resultSet.getDate(3));
            reserva.setF_reserva(resultSet.getDate(4));
            reserva.setF_final(resultSet.getDate(5));
            reserva.setCantidad_huespedes(resultSet.getInt(6));
            reserva.setPrecio_reserva(resultSet.getDouble(7));

            Condicion_Hotel condicion_hotel = new Condicion_Hotel();
            reserva.setCondicion(condicion_hotel.ConsultarCondicionHotel(resultSet.getInt(8),condicion_hotel));

            Persona persona = new Persona();
            reserva.setPersona(persona.ConsultarPersona(resultSet.getInt(9),resultSet.getString(10),persona));

            return reserva;

        }catch (SQLException ex){
            System.out.println(ex);
        }
        return null;
    }
}
