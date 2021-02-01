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

    public Pago(int k_pago, Date f_pago, double monto, String forma_de_pago) {
        this.k_pago = k_pago;
        this.f_pago = f_pago;
        this.monto = monto;
        this.forma_de_pago = forma_de_pago;
    }

    public Pago(){};

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

    public void ConsultarPago(int ID){
        Operaciones op = new Operaciones();
        try {
            ResultSet resultSet = op.ConsultaEsp("SELECT * FROM Pago WHERE k_pago = "+ID+"");
            resultSet.next();
            this.k_pago = resultSet.getInt(1);
            this.f_pago = resultSet.getDate(2);
            this.monto = resultSet.getDouble(3);
            this.forma_de_pago = resultSet.getString(4);


        }catch (SQLException ex){
            System.out.println(ex);
        }
    }
}
