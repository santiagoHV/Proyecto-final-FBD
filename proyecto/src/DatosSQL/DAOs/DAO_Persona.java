package DatosSQL.DAOs;

import DatosSQL.Operaciones;
import Modelo.entidades.Persona;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DAO_Persona {

    /**
     * Consulta la tabla persona por sus primary keys y retorna un objeto tipo Persona con sus atributos completos
     * @param ID
     * @param Tipo
     * @return persona
     */
    public Persona consultarPersona(int ID, String Tipo) {
        Operaciones op = new Operaciones();

        try {
            ResultSet resultSet = op.ConsultaEsp("SELECT * FROM Persona Where k_identificacion = "+ID+" and k_tipo_documento = '"+Tipo+"'");
            if (resultSet.next()) {

                Persona persona = new Persona(resultSet.getInt(1),resultSet.getString(2),resultSet.getString(3),
                        resultSet.getString(4), resultSet.getDate(5), resultSet.getString(6));

                return persona;
            }
        }
        catch (SQLException exception){
            System.out.println(exception);
        }
        return null;
    }
}
