package Modelo.entidades;

import DatosSQL.Operaciones;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Huesped extends Persona
{
    private String n_direccion;

    public Huesped(){
    }

    public Huesped(int k_identificacion, String k_tipo_documento_id, String n_nombre, String n_apellido, Date f_nacimiento, String n_telefono, String n_direccion) {
        super(k_identificacion, k_tipo_documento_id, n_nombre, n_apellido, f_nacimiento, n_telefono);
        this.n_direccion = n_direccion;
    }


    public String getN_direccion() {
        return n_direccion;
    }

    public void setN_direccion(String n_direccion) {
        this.n_direccion = n_direccion;
    }


}