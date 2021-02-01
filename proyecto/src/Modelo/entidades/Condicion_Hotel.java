package Modelo.entidades;

import DatosSQL.Operaciones;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Condicion_Hotel {
    private int k_condicion;
    private boolean estado_condicion;
    private double descuento;
    private double aforo;
    private int numero_dias;

    public Condicion_Hotel(int k_condicion, boolean estado_condicion, double descuento, double aforo, int numero_dias) {
        this.k_condicion = k_condicion;
        this.estado_condicion = estado_condicion;
        this.descuento = descuento;
        this.aforo = aforo;
        this.numero_dias = numero_dias;
    }

    public Condicion_Hotel(){};

    public int getK_condicion() {
        return k_condicion;
    }

    public void setK_condicion(int k_condicion) {
        this.k_condicion = k_condicion;
    }

    public boolean isEstado_condicion() {
        return estado_condicion;
    }

    public void setEstado_condicion(boolean estado_condicion) {
        this.estado_condicion = estado_condicion;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public double getAforo() {
        return aforo;
    }

    public void setAforo(double aforo) {
        this.aforo = aforo;
    }

    public int getNumero_dias() {
        return numero_dias;
    }

    public void setNumero_dias(int numero_dias) {
        this.numero_dias = numero_dias;
    }

    public void ConsultarCondicionHotel(int ID) {
        Operaciones op = new Operaciones();
        try {
            ResultSet resultSet = op.ConsultaEsp("SELECT * FROM Condicion_Hotel WHERE k_condicion = "+ID+"");
            resultSet.next();
            this.k_condicion = resultSet.getInt(1);
            this.estado_condicion = resultSet.getBoolean(2);
            this.descuento = resultSet.getDouble(3);
            this.aforo = resultSet.getDouble(4);
            this.numero_dias = resultSet.getInt(5);

        }catch (SQLException ex){
            System.out.println(ex);
        }
    }
}
