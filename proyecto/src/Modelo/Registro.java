package Modelo;

import java.sql.Date;

public class Registro {
    private int k_registro;
    private Date f_entrada;
    private Date f_salida;
    private int k_cuenta;
    private String k_tipo_documento;
    private int k_identificacion;
    private int k_reserva;

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

    public String getK_tipo_documento() {
        return k_tipo_documento;
    }

    public void setK_tipo_documento(String k_tipo_documento) {
        this.k_tipo_documento = k_tipo_documento;
    }

    public int getK_identificacion() {
        return k_identificacion;
    }

    public void setK_identificacion(int k_identificacion) {
        this.k_identificacion = k_identificacion;
    }

    public int getK_reserva() {
        return k_reserva;
    }

    public void setK_reserva(int k_reserva) {
        this.k_reserva = k_reserva;
    }
}
