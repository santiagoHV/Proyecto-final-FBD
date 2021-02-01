package Modelo.entidades;

import DatosSQL.Operaciones;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PyS {
    private int k_codigo_pys;
    private String unidad;
    private int stock;
    private double precio_producta;

    public PyS(int k_codigo_pys, String unidad, int stock, double precio_producta) {
        this.k_codigo_pys = k_codigo_pys;
        this.unidad = unidad;
        this.stock = stock;
        this.precio_producta = precio_producta;
    }

    public PyS(){}

    public int getK_codigo_pys() {
        return k_codigo_pys;
    }

    public void setK_codigo_pys(int k_codigo_pys) {
        this.k_codigo_pys = k_codigo_pys;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getPrecio_producta() {
        return precio_producta;
    }

    public void setPrecio_producta(double precio_producta) {
        this.precio_producta = precio_producta;
    }

    public void ConsultaProdYServ(int ID){
        Operaciones op = new Operaciones();
        try {
            ResultSet resultSet = op.ConsultaEsp("SELECT * FROM productoyservicio WHERE k_codigo_pys = "+ID+"");
            resultSet.next();
            this.k_codigo_pys = resultSet.getInt(1);
            this.unidad = resultSet.getString(2);
            this.stock = resultSet.getInt(3);
            this.precio_producta = resultSet.getDouble(4);


        }catch (SQLException ex){
            System.out.println(ex);
        }
    }
}
