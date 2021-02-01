package Modelo.entidades;

import DatosSQL.Operaciones;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Persona
{
    private int k_identificacion;
    private String k_tipo_documento_id;
    private String f_nacimiento;
    private String n_telefono;
    private String n_nombre;
    private String n_apellido;

    public Persona(int k_identificacion, String k_tipo_documento_id, String f_nacimiento, String n_telefono, String n_nombre, String n_apellido) {
        this.k_identificacion = k_identificacion;
        this.k_tipo_documento_id = k_tipo_documento_id;
        this.f_nacimiento = f_nacimiento;
        this.n_telefono = n_telefono;
        this.n_nombre = n_nombre;
        this.n_apellido = n_apellido;
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

    public String getF_nacimiento() {
        return f_nacimiento;
    }

    public void setF_nacimiento(String f_nacimiento) {
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

    public void ConsultarPersona(int ID, String Tipo) {
        Operaciones op = new Operaciones();
        try
        {
            ResultSet resultSet = op.ConsultaEsp("SELECT * FROM Persona Where k_identificacion = "+ID+" and k_tipo_documento = '"+Tipo+"'");
            if (resultSet.next())
            {
                this.k_identificacion = resultSet.getInt(1);
                this.k_tipo_documento_id = resultSet.getString(2);
                this.n_nombre = resultSet.getString(3);
                this.n_apellido = resultSet.getString(4);
                this.f_nacimiento = resultSet.getString(5);
                this.n_telefono = resultSet.getString(6);
            }
        }
        catch (SQLException exception){
            System.out.println(exception);
        }
    }
}
