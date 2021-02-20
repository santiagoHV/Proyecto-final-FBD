package DatosSQL.DAOs;

import DatosSQL.Operaciones;
import Modelo.entidades.Pago;

import java.text.SimpleDateFormat;
import java.util.Date;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DAO_Pago {

    public Pago consultarPago(int ID){
        Operaciones op = new Operaciones();
        try {
            ResultSet resultSet = op.ConsultaEsp("SELECT * FROM Pago WHERE k_pago = "+ID+"");
            resultSet.next();

            Pago pago = new Pago(resultSet.getInt(1),resultSet.getDate(2),
                    resultSet.getDouble(3),resultSet.getString(4));

            return pago;

        }catch (SQLException ex){
            System.out.println(ex);
        }

        return null;
    }

    public int consultarMontoProductosAnho(){
        Date fechainicio = new Date();
        fechainicio.setYear(fechainicio.getYear()-1);
        Date fechafinal = new Date();
        SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
        Operaciones op = new Operaciones();
        try {
            ResultSet res = op.ConsultaEsp("SELECT SUM(cp.v_precio_venta*cp.q_pys_pedidos) " +
                                  "FROM cuenta_productos cp, cuenta c, pago p " +
                                  "WHERE cp.k_cuenta = c.k_cuenta AND c.k_pago = p.k_pago AND cp.f_pedido BETWEEN '"+formateador.format(fechainicio)+"' AND '"+formateador.format(fechafinal)+"'");
            res.next();
            return res.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
    public int consultarMontoProductosMes(){
        Date fechainicio = new Date();
        fechainicio.setMonth(fechainicio.getMonth()-1);
        Date fechafinal = new Date();
        SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
        Operaciones op = new Operaciones();
        try {
            ResultSet res = op.ConsultaEsp("SELECT SUM(cp.v_precio_venta*cp.q_pys_pedidos) " +
                    "FROM cuenta_productos cp, cuenta c, pago p " +
                    "WHERE cp.k_cuenta = c.k_cuenta AND c.k_pago = p.k_pago AND cp.f_pedido BETWEEN '"+formateador.format(fechainicio)+"' AND '"+formateador.format(fechafinal)+"'");
            res.next();
            return res.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
    public int consultarMontoProductosHoy(){
        Date fechainicio = new Date();
        fechainicio.setDate(fechainicio.getDay()-1);
        Date fechafinal = new Date();
        SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
        Operaciones op = new Operaciones();
        try {
            ResultSet res = op.ConsultaEsp("SELECT SUM(cp.v_precio_venta*cp.q_pys_pedidos) " +
                    "FROM cuenta_productos cp, cuenta c, pago p " +
                    "WHERE cp.k_cuenta = c.k_cuenta AND c.k_pago = p.k_pago AND cp.f_pedido BETWEEN '"+formateador.format(fechainicio)+"' AND '"+formateador.format(fechafinal)+"'");
            res.next();
            return res.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
    public int consultarMontoProductosSiempre(){
        Operaciones op = new Operaciones();
        try {
            ResultSet res = op.ConsultaEsp("SELECT SUM(cp.v_precio_venta*cp.q_pys_pedidos) " +
                    "FROM cuenta_productos cp, cuenta c, pago p " +
                    "WHERE cp.k_cuenta = c.k_cuenta AND c.k_pago = p.k_pago");
            res.next();
            return res.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
