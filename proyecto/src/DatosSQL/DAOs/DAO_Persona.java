package DatosSQL.DAOs;

import DatosSQL.Conexion;
import DatosSQL.Operaciones;
import Modelo.entidades.Persona;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAO_Persona {

    Operaciones op;

    public DAO_Persona(){
        op = new Operaciones();
    }

    /**
     * Consulta la tabla persona por sus primary keys y retorna un objeto tipo Persona con sus atributos completos
     * @param ID
     * @param Tipo
     * @return persona
     */
    public Persona consultarPersona(int ID, String Tipo) throws SQLException {

        try {
            ResultSet resultSet = op.ConsultaEsp("SELECT * FROM Persona Where k_identificacion = "+ID+" and k_tipo_documento = '"+Tipo+"'");
            if (resultSet.next()) {

                Persona persona = new Persona(resultSet.getInt(1),resultSet.getString(2),resultSet.getString(3),
                        resultSet.getString(4), resultSet.getDate(5), resultSet.getString(6));

                return persona;
            }
        }
        catch (SQLException exception){
            System.out.println(exception + " En Persona");
        }
        return null;
    }
    public void insertarPersona(Persona persona){
        try {
            PreparedStatement preparedStatement = Conexion.getInstance().getConnection().prepareStatement(
                    "INSERT INTO persona VALUES(?,?,?,?,?,?);");

            preparedStatement.setInt(1, persona.getK_identificacion());
            preparedStatement.setString(2, persona.getK_tipo_documento_id());
            preparedStatement.setString(3, persona.getN_nombre());
            preparedStatement.setString(4, persona.getN_apellido());
            preparedStatement.setDate(5, persona.getF_nacimiento());
            preparedStatement.setString(6, persona.getN_telefono());

            preparedStatement.executeUpdate();

        }catch (Exception e){
            System.out.println(e + "es aca");
        }
    }
}
