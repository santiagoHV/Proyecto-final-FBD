package Modelo.entidades;

import DatosSQL.Operaciones;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Persona
{
    private int k_identificacion;
    private String k_tipo_documento_id;
    private String n_nombre;
    private String n_apellido;
    private Date f_nacimiento;
    private String n_telefono;

    public Persona(int k_identificacion, String k_tipo_documento_id, String n_nombre, String n_apellido, Date f_nacimiento, String n_telefono) {
        this.k_identificacion = k_identificacion;
        this.k_tipo_documento_id = k_tipo_documento_id;
        this.n_nombre = n_nombre;
        this.n_apellido = n_apellido;
        this.f_nacimiento = f_nacimiento;
        this.n_telefono = n_telefono;
    }

    public Persona() {
    }

    public int getK_identificacion() {
        return k_identificacion;
    }

    public void setK_identificacion(int k_identificacion) {
        this.k_identificacion = k_identificacion;
    }

    public String getK_tipo_documento_id() {
        return k_tipo_documento_id;
    }

    public void setK_tipo_documento_id(String k_tipo_documento_id) {
        this.k_tipo_documento_id = k_tipo_documento_id;
    }

    public Date getF_nacimiento() {
        return f_nacimiento;
    }

    public void setF_nacimiento(Date f_nacimiento) {
        this.f_nacimiento = f_nacimiento;
    }

    public String getN_telefono() {
        return n_telefono;
    }

    public void setN_telefono(String n_telefono) {
        this.n_telefono = n_telefono;
    }

    public String getN_nombre() {
        return n_nombre;
    }

    public void setN_nombre(String n_nombre) {
        this.n_nombre = n_nombre;
    }

    public String getN_apellido() {
        return n_apellido;
    }

    public void setN_apellido(String n_apellido) {
        this.n_apellido = n_apellido;
    }


}
