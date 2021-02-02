package Modelo.entidades;

import DatosSQL.Operaciones;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Reserva_Habitacion {
    private Reserva reserva;
    private Habitacion habitacion;

    public Reserva_Habitacion(Reserva reserva, Habitacion habitacion) {
        this.reserva = reserva;
        this.habitacion = habitacion;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }

    public Habitacion getHabitacion() {
        return habitacion;
    }

    public void setHabitacion(Habitacion habitacion) {
        this.habitacion = habitacion;
    }

}
