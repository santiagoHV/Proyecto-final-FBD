package Modelo.entidades;

import DatosSQL.Operaciones;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Huesped extends Persona
{
    private String n_direccion;

    public Huesped(){
    }

    public Huesped(int k_identificacion, String k_tipo_documento_id, String f_nacimiento, String n_telefono, String n_nombre, String n_apellido, Habitacion habitacion) {
        super(k_identificacion, k_tipo_documento_id, f_nacimiento, n_telefono, n_nombre, n_apellido);
    }

    public String getN_direccion() {
        return n_direccion;
    }

    public void setN_direccion(String n_direccion) {
        this.n_direccion = n_direccion;
    }

    public void ConsultarHuesped(int ID) {
        Operaciones op = new Operaciones();
        try {
            ResultSet resultSet = op.ConsultaEsp("SELECT * FROM Huesped WHERE k_identificacion = "+ID+"");
            resultSet.next();

            this.n_direccion = resultSet.getString(1);
            super.ConsultarPersona(resultSet.getInt(2),resultSet.getString(3));

        }catch (SQLException ex){
            System.out.println(ex);
        }
    }
}