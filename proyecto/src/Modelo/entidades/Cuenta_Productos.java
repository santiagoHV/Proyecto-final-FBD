package Modelo.entidades;
import java.sql.Date;

public class Cuenta_Productos {
    private int pys_pedidos;
    private Date f_pedido;
    private PyS pys;
    private Cuenta cuenta;

    public Cuenta_Productos(int pys_pedidos, Date f_pedido, PyS pys, Cuenta cuenta) {
        this.pys_pedidos = pys_pedidos;
        this.f_pedido = f_pedido;
        this.pys = pys;
        this.cuenta = cuenta;
    }

    public int getPys_pedidos() {
        return pys_pedidos;
    }

    public void setPys_pedidos(int pys_pedidos) {
        this.pys_pedidos = pys_pedidos;
    }

    public Date getF_pedido() {
        return f_pedido;
    }

    public void setF_pedido(Date f_pedido) {
        this.f_pedido = f_pedido;
    }

    public PyS getPys() {
        return pys;
    }

    public void setPys(PyS pys) {
        this.pys = pys;
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }
}