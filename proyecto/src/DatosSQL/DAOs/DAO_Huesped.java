package DatosSQL.DAOs;

import DatosSQL.Operaciones;
import Modelo.entidades.Huesped;
import Modelo.entidades.Persona;

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

}
