package Modelo;

import java.sql.Date;

public class Registro {
    private int k_registro;
    private Date f_entrada;
    private Date f_salida;
    private int k_cuenta;
    private Persona persona;
    private Reserva reserva;

    public Registro(int k_registro, Date f_entrada, Date f_salida, int k_cuenta, Persona persona, Reserva reserva) {
        this.k_registro = k_registro;
        this.f_entrada = f_entrada;
        this.f_salida = f_salida;
        this.k_cuenta = k_cuenta;
        this.persona = persona;
        this.reserva = reserva;
    }

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

    public int getK_cuenta() {
        return k_cuenta;
    }

    public void setK_cuenta(int k_cuenta) {
        this.k_cuenta = k_cuenta;
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
}
