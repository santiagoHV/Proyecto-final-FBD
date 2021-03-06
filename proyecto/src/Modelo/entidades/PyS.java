package Modelo.entidades;

import DatosSQL.Operaciones;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PyS {
    private int k_codigo_pys;
    private String unidad;
    private int stock;
    private double precio_producta;
    private String categoria;


    public PyS(int k_codigo_pys, String unidad, int stock, double precio_producta, String categoria) {
        this.k_codigo_pys = k_codigo_pys;
        this.unidad = unidad;
        this.stock = stock;
        this.precio_producta = precio_producta;
        this.categoria = categoria;
    }


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

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }



}
