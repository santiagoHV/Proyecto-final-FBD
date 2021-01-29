package Modelo.entidades;

import DatosSQL.Operaciones;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Huesped extends Persona
{
    private String n_direccion;
    private Persona persona;

    public Huesped(){
    }

    public Huesped(int k_identificacion, String k_tipo_documento_id, String f_nacimiento, String n_telefono, String n_nombre, String n_apellido, Habitacion habitacion) {
        super(k_identificacion, k_tipo_documento_id, f_nacimiento, n_telefono, n_nombre, n_apellido);
    }

    public String getN_direccion() {
        return n_direccion;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public void setN_direccion(String n_direccion) {
        this.n_direccion = n_direccion;
    }

    public Huesped ConsultarHuesped(int ID, Huesped huesped) {
        Operaciones op = new Operaciones();
        try {
            ResultSet resultSet = op.ConsultaEsp("SELECT * FROM Huesped WHERE k_identificacion = "+ID+"");
            resultSet.next();
            huesped.setN_direccion(resultSet.getString(1));
            huesped.setPersona(huesped.ConsultarPersona(resultSet.getInt(2),resultSet.getString(3),huesped));
            return huesped;

        }catch (SQLException ex){
            System.out.println(ex);
        }
        return null;
    }
}