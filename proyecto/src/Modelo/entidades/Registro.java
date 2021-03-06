package Modelo.entidades;

import java.sql.Date;

public class Registro {
    private int k_registro;
    private Date f_entrada;
    private Date f_salida;
    private Huesped Huesped;
    private Reserva reserva;
    private Habitacion habitacion;

    public Registro(int k_registro, Date f_entrada, Date f_salida, Huesped Huesped, Reserva reserva, Habitacion habitacion) {
        this.k_registro = k_registro;
        this.f_entrada = f_entrada;
        this.f_salida = f_salida;
        this.Huesped = Huesped;
        this.reserva = reserva;
        this.habitacion = habitacion;
    }

    public Registro(){}

    public int getK_registro() {

        return k_registro;
    }

    public void setK_registro(int k_registro) {
        this.k_registro = k_registro;
    }

    public Date getF_entrada() {
        return f_entrada;
    }

    public void setF_entrada(Date f_entrada) {
        this.f_entrada = f_entrada;
    }

    public Date getF_salida() {
        return f_salida;
    }

    public void setF_salida(Date f_salida) {
        this.f_salida = f_salida;
    }

    public Huesped getHuesped() {
        return Huesped;
    }

    public void setHuesped(Huesped huesped) {
        this.Huesped = huesped;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }

    public void setHabitacion(Habitacion habitacion) {
        this.habitacion = habitacion;
    }

    public Habitacion getHabitacion() {
        return habitacion;
    }
}

