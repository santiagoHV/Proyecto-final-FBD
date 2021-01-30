package Modelo.entidades;

import DatosSQL.Operaciones;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Registro {
    private int k_registro;
    private Date f_entrada;
    private Date f_salida;
    private Persona persona;
    private Reserva reserva;
    private Habitacion habitacion;

    public Registro(int k_registro, Date f_entrada, Date f_salida, Persona persona, Reserva reserva, Habitacion habitacion) {
        this.k_registro = k_registro;
        this.f_entrada = f_entrada;
        this.f_salida = f_salida;
        this.persona = persona;
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

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
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

    public Registro ConsultarRegistro(int ID, Registro registro){
        Operaciones op = new Operaciones();
        try {
            ResultSet resultSet = op.ConsultaEsp("SELECT * FROM registro_checkin WHERE k_registro = "+ID+"");
            resultSet.next();
            registro.setK_registro(resultSet.getInt(1));
            registro.setF_entrada(resultSet.getDate(2));
            registro.setF_entrada(resultSet.getDate(3));

            Persona persona = new Persona();
            registro.setPersona(persona.ConsultarPersona(resultSet.getInt(5), resultSet.getString(4),persona));

            Reserva reserva = new Reserva();
            registro.setReserva(reserva.ConsultarReserva(resultSet.getInt(6),reserva));

            Habitacion habitacion = new Habitacion();
            registro.setHabitacion(habitacion.ConsultarHabitacion(resultSet.getInt(7),habitacion));

            return registro;

        }catch (SQLException ex){
            System.out.println(ex);
        }
        return null;
    }
}
