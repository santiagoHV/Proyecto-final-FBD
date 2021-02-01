package Modelo.entidades;
import DatosSQL.Operaciones;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

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

    public void ConsultarCuentaProd(int k_codigo_pys, int k_cuenta) {
        Operaciones op = new Operaciones();
        try {
            ResultSet resultSet = op.ConsultaEsp("SELECT * FROM cuenta_productos WHERE k_codigo_pys = "+k_codigo_pys+" and k_cuenta = "+k_cuenta+" ");
            resultSet.next();
            this.pys_pedidos = resultSet.getInt(1);
            this.f_pedido = resultSet.getDate(2);

            PyS pyS = new PyS();
            pyS.ConsultaProdYServ(resultSet.getInt(3));
            this.pys = pyS;

            Cuenta cuenta = new Cuenta();
            cuenta.ConsultarCuenta(resultSet.getInt(4));
            this.cuenta = cuenta;


        }catch (SQLException ex){
            System.out.println(ex);
        }
    }
}
