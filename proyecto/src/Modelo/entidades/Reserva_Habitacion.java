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

    public Reserva_Habitacion ConsultaReservHab(int k_reserva, int k_numero_habitacion, Reserva_Habitacion reserva_habitacion) {
        Operaciones op = new Operaciones();
        try {
            ResultSet resultSet = op.ConsultaEsp("SELECT * FROM reserva_habitacion WHERE k_reserva = "+k_reserva+" and k_numero_habitacion = "+k_numero_habitacion+" ");
            resultSet.next();

            Reserva reserva = new Reserva();
            reserva.ConsultarReserva(resultSet.getInt(1));
            this.reserva = reserva;

            Habitacion habitacion = new Habitacion();
            habitacion.ConsultarHabitacion(resultSet.getInt(2));
            this.habitacion = habitacion;

        }catch (SQLException ex){
            System.out.println(ex);
        }
        return null;
    }
}
