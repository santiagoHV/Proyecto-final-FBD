package Modelo.entidades;

import DatosSQL.Operaciones;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Cuenta {
    private int k_cuenta;
    private double precio_acumulado;
    private Reserva reserva;

    public Cuenta(int k_cuenta, double precio_acumulado, Reserva reserva) {
        this.k_cuenta = k_cuenta;
        this.precio_acumulado = precio_acumulado;
        this.reserva = reserva;
    }

    public Cuenta(){}

    public int getK_cuenta() {
        return k_cuenta;
    }

    public void setK_cuenta(int k_cuenta) {
        this.k_cuenta = k_cuenta;
    }

    public double getPrecio_acumulado() {
        return precio_acumulado;
    }

    public void setPrecio_acumulado(double precio_acumulado) {
        this.precio_acumulado = precio_acumulado;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }

}
