package Modelo.entidades;

import DatosSQL.Operaciones;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Pago {
    private int k_pago;
    private Date f_pago;
    private double monto;
    private String forma_de_pago;
    private Cuenta cuenta;

    public Pago(int k_pago, Date f_pago, double monto, String forma_de_pago, Cuenta cuenta) {
        this.k_pago = k_pago;
        this.f_pago = f_pago;
        this.monto = monto;
        this.forma_de_pago = forma_de_pago;
        this.cuenta = cuenta;
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }

    public int getK_pago() {
        return k_pago;
    }

    public void setK_pago(int k_pago) {
        this.k_pago = k_pago;
    }

    public Date getF_pago() {
        return f_pago;
    }

    public void setF_pago(Date f_pago) {
        this.f_pago = f_pago;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getForma_de_pago() {
        return forma_de_pago;
    }

    public void setForma_de_pago(String forma_de_pago) {
        this.forma_de_pago = forma_de_pago;
    }


}
