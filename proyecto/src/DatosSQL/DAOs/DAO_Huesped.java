package DatosSQL.DAOs;

import DatosSQL.Conexion;
import DatosSQL.Operaciones;
import Modelo.entidades.Huesped;
import Modelo.entidades.Persona;
import Modelo.entidades.Registro;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAO_Huesped {

    public Huesped consultarHuesped(int ID, String Tipo) {
        Operaciones op = new Operaciones();
        try {
            ResultSet resultSet = op.ConsultaEsp("SELECT * FROM Huesped WHERE k_identificacion = "+ID+" and  k_tipo_documento = '"+Tipo+"'");
            resultSet.next();

            DAO_Persona dao_persona = new DAO_Persona();
            Persona persona = dao_persona.consultarPersona(resultSet.getInt(2),resultSet.getString(3));

            Huesped huesped = new Huesped(persona.getK_identificacion(),persona.getK_tipo_documento_id(),persona.getN_nombre(),
                    persona.getN_apellido(), persona.getF_nacimiento(), persona.getN_telefono(), resultSet.getString(1));

            return huesped;

        }catch (SQLException ex){
            System.out.println(ex);
        }
        return null;
    }
    public void insertarHuesped(Huesped huesped){
        try {
            PreparedStatement preparedStatement = Conexion.getInstance().getConnection().prepareStatement(
                    "INSERT INTO huesped VALUES(?,?,?);");

            preparedStatement.setString(1, huesped.getN_direccion());
            preparedStatement.setInt(2, huesped.getK_identificacion());
            preparedStatement.setString(3, huesped.getK_tipo_documento_id());

            preparedStatement.executeUpdate();

        }catch (Exception e){
            System.out.println(e + "Error en insercion de huesped");
        }
    }

    public int actualizarHuesped(Huesped huesped) {
        try {
            PreparedStatement preparedStatement = Conexion.getInstance().getConnection().prepareStatement(
                    "UPDATE huesped set n_direccion = ?, k_identificacion = ?, k_tipo_documento = ? " +
                            " WHERE k_identificacion = ?");

            preparedStatement.setString(1, huesped.getN_direccion());
            preparedStatement.setInt(2, huesped.getK_identificacion());
            preparedStatement.setString(3, huesped.getK_tipo_documento_id());
            preparedStatement.setInt(4, huesped.getK_identificacion());

            return preparedStatement.executeUpdate();

        }catch (Exception e){
            System.out.println(e + "En huesped");
        }
        return 0;
    }

}
