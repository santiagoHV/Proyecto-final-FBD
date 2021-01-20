package Modelo;

public class Cuenta {
    private int k_cuenta;
    private double precio_acumulado;
    private int k_pago;
    private int k_registro;
    private int k_reserva;

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

    public int getK_pago() {
        return k_pago;
    }

    public void setK_pago(int k_pago) {
        this.k_pago = k_pago;
    }

    public int getK_registro() {
        return k_registro;
    }

    public void setK_registro(int k_registro) {
        this.k_registro = k_registro;
    }

    public int getK_reserva() {
        return k_reserva;
    }

    public void setK_reserva(int k_reserva) {
        this.k_reserva = k_reserva;
    }
}
