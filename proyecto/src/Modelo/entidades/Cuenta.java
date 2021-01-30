package Modelo.entidades;

import DatosSQL.Operaciones;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Cuenta {
    private int k_cuenta;
    private double precio_acumulado;
    private Pago pago;
    private Reserva reserva;

    public Cuenta(int k_cuenta, double precio_acumulado, Pago pago, Reserva reserva) {
        this.k_cuenta = k_cuenta;
        this.precio_acumulado = precio_acumulado;
        this.pago = pago;
        this.reserva = reserva;
    }

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

    public Pago getPago() {
        return pago;
    }

    public void setPago(Pago pago) {
        this.pago = pago;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }

    public Cuenta ConsultarCuenta(int ID, Cuenta cuenta){
        Operaciones op = new Operaciones();
        try {
            ResultSet resultSet = op.ConsultaEsp("SELECT * FROM Cuenta WHERE k_cuenta = "+ID+"");
            resultSet.next();
            cuenta.setK_cuenta(resultSet.getInt(1));
            cuenta.setPrecio_acumulado(resultSet.getDouble(2));

            Pago pago = new Pago();
            cuenta.setPago(pago.ConsultarPago(resultSet.getInt(3),pago));

            Reserva reserva = new Reserva();
            cuenta.setReserva(reserva.ConsultarReserva(resultSet.getInt(4),reserva));

            return cuenta;

        }catch (SQLException ex){
            System.out.println(ex);
        }
        return null;
    }
}
