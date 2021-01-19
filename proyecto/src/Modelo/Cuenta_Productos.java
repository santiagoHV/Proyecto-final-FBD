package Modelo;
import java.sql.Date;

public class Cuenta_Productos {
    private int pys_pedidos;
    private Date f_pedido;
    private int k_codigo_pys;
    private int k_cuenta;

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

    public int getK_codigo_pys() {
        return k_codigo_pys;
    }

    public void setK_codigo_pys(int k_codigo_pys) {
        this.k_codigo_pys = k_codigo_pys;
    }

    public int getK_cuenta() {
        return k_cuenta;
    }

    public void setK_cuenta(int k_cuenta) {
        this.k_cuenta = k_cuenta;
    }
}
