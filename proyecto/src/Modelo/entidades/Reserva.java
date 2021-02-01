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
    private int cantidad_adultos;
    private int cantidad_ninos;
    private int cantidad_bebes;
    private double precio_reserva;
    private Condicion_Hotel condicion;
    private Persona persona;

    public Reserva(int k_reserva, String estado, Date f_inicio, Date f_reserva, Date f_final, int cantidad_adultos, int cantidad_ninos, int cantidad_bebes, double precio_reserva, Condicion_Hotel condicion, Persona persona) {
        this.k_reserva = k_reserva;
        this.estado = estado;
        this.f_inicio = f_inicio;
        this.f_reserva = f_reserva;
        this.f_final = f_final;
        this.cantidad_adultos = cantidad_adultos;
        this.cantidad_ninos = cantidad_ninos;
        this.cantidad_bebes = cantidad_bebes;
        this.precio_reserva = precio_reserva;
        this.condicion = condicion;
        this.persona = persona;
    }

    public void ConsultarReserva(int ID){
        Operaciones op = new Operaciones();
        try {
            ResultSet resultSet = op.ConsultaEsp("SELECT * FROM Reserva WHERE k_reserva = "+ID+"");
            resultSet.next();
            this.k_reserva = resultSet.getInt(1);
            this.estado = resultSet.getString(2);
            this.f_inicio = resultSet.getDate(3);
            this.f_reserva = resultSet.getDate(4);
            this.f_final = resultSet.getDate(5);
            this.cantidad_adultos = resultSet.getInt(6);
            this.cantidad_ninos = resultSet.getInt(7);
            this.cantidad_bebes = resultSet.getInt(8);
            this.precio_reserva = resultSet.getDouble(9);

            Condicion_Hotel condicion_hotel = new Condicion_Hotel();
            condicion_hotel.ConsultarCondicionHotel(resultSet.getInt(10));
            this.condicion = condicion_hotel;

            Persona persona = new Persona();
            persona.ConsultarPersona(resultSet.getInt(11),resultSet.getString(12));
            this.persona = persona;

        }catch (SQLException ex){
            System.out.println(ex);
        }
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

    public int getCantidad_adultos() {
        return cantidad_adultos;
    }

    public void setCantidad_adultos(int cantidad_adultos) {
        this.cantidad_adultos = cantidad_adultos;
    }

    public int getCantidad_ninos() {
        return cantidad_ninos;
    }

    public void setCantidad_ninos(int cantidad_ninos) {
        this.cantidad_ninos = cantidad_ninos;
    }

    public int getCantidad_bebes() {
        return cantidad_bebes;
    }

    public void setCantidad_bebes(int cantidad_bebes) {
        this.cantidad_bebes = cantidad_bebes;
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
