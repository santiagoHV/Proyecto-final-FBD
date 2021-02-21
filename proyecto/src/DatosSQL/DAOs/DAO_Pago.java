package DatosSQL.DAOs;

import DatosSQL.Operaciones;
import Modelo.entidades.Pago;

import java.text.SimpleDateFormat;
import java.util.Date;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DAO_Pago {

    private Date fechainicio;
    private Date fechafinal;
    private SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");

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

    public double consultarMontoProductos(String opc){
        setFechas(opc);
        Operaciones op = new Operaciones();
        try {
            ResultSet res = op.ConsultaEsp("SELECT SUM(x.valores) FROM (SELECT cp.q_pys_pedidos * cp.v_precio_venta AS valores " +
                    "FROM cuenta_productos cp, cuenta c, pago p " +
                    "WHERE cp.k_cuenta = c.k_cuenta AND c.k_cuenta = p.k_cuenta AND p.f_pago BETWEEN '"+formateador.format(fechainicio)+"' AND '"+formateador.format(fechafinal)+"' " +
                    "GROUP BY c.k_cuenta, cp.q_pys_pedidos, cp.v_precio_venta) x");
            res.next();
            return res.getDouble(1);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
    public double consultarMontoProductos(){
        Operaciones op = new Operaciones();
        try {
            ResultSet res = op.ConsultaEsp("SELECT SUM(x.valores) FROM (SELECT cp.q_pys_pedidos * cp.v_precio_venta AS valores " +
                    "FROM cuenta_productos cp, cuenta c, pago p " +
                    "WHERE cp.k_cuenta = c.k_cuenta AND c.k_cuenta = p.k_cuenta " +
                    "GROUP BY c.k_cuenta, cp.q_pys_pedidos, cp.v_precio_venta) x");
            res.next();
            return res.getDouble(1);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int totalPagosRealizados(){
        Operaciones op = new Operaciones();
        try {
            ResultSet res = op.ConsultaEsp("SELECT COUNT(k_pago) FROM pago");
            res.next();
            return res.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int totalPagosRealizados(String opc){
        setFechas(opc);
        Operaciones op = new Operaciones();
        try {
            ResultSet res = op.ConsultaEsp("SELECT COUNT(k_pago) FROM pago WHERE f_pago BETWEEN '"+formateador.format(fechainicio)+"' AND '"+formateador.format(fechafinal)+"'");
            res.next();
            return res.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int totalProductosVendidos(){
        Operaciones op = new Operaciones();
        try {
            ResultSet res = op.ConsultaEsp("SELECT SUM(cp.q_pys_pedidos) FROM cuenta_productos cp, cuenta c, pago p where cp.k_cuenta = c.k_cuenta AND p.k_cuenta = c.k_cuenta");
            res.next();
            return res.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int totalProductosVendidos(String opc){
        setFechas(opc);
        Operaciones op = new Operaciones();
        try {
            ResultSet res = op.ConsultaEsp("SELECT SUM(cp.q_pys_pedidos) FROM cuenta_productos cp, cuenta c, pago p where cp.k_cuenta = c.k_cuenta AND p.k_cuenta = c.k_cuenta AND p.f_pago BETWEEN '"+formateador.format(fechainicio)+"' AND '"+formateador.format(fechafinal)+"'");
            res.next();
            return res.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
    public double VentaTotal(){
        Operaciones op = new Operaciones();
        try {
            ResultSet res = op.ConsultaEsp("SELECT SUM(v_monto) FROM pago");
            res.next();
            return res.getDouble(1);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public double VentaTotal(String opc){
        setFechas(opc);
        Operaciones op = new Operaciones();
        try {
            ResultSet res = op.ConsultaEsp("SELECT SUM(v_monto) FROM pago WHERE f_pago BETWEEN '"+formateador.format(fechainicio)+"' AND '"+formateador.format(fechafinal)+"'");
            res.next();
            return res.getDouble(1);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    private void setFechas(String opc){
        long dif = 0;
        fechainicio = new Date();
        fechafinal = new Date();
        if(opc.equals("Ultimo Año")){
            dif = Long.parseLong("31536000000");
            fechainicio.setTime(fechainicio.getTime()-dif);
        }else if(opc.equals("Ultimo Mes")){
            dif = Long.parseLong("2678400000");
            fechainicio.setTime(fechainicio.getTime()-dif);
        }else if(opc.equals("Ultima Semana")){
            dif = Long.parseLong("604800000");
            fechainicio.setTime(fechainicio.getTime()-dif);
        }else if(opc.equals("Hoy")){
            dif = Long.parseLong("86400000");
            fechainicio.setTime(fechainicio.getTime()-dif);
        }
    }
}
