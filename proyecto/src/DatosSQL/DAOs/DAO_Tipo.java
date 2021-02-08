package DatosSQL.DAOs;

import DatosSQL.Operaciones;
import Modelo.entidades.Tipo;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DAO_Tipo {

    public Tipo consultarTipoHab(String ID){
        Operaciones op = new Operaciones();
        try {
            ResultSet resultSet = op.ConsultaEsp("SELECT * FROM tipo WHERE k_tipo_habitacion = '"+ID+"'");
            resultSet.next();

            Tipo tipo = new Tipo(resultSet.getString(1),resultSet.getDouble(2),resultSet.getString(3));

            return tipo;

        }catch (SQLException ex){
            System.out.println(ex + "Tipo");
        }
        return null;
    }
}
