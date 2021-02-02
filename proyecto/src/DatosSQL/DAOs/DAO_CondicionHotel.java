package DatosSQL.DAOs;

import DatosSQL.Operaciones;
import Modelo.entidades.Condicion_Hotel;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DAO_CondicionHotel {

    /**
     *  Consulta la tabla persona por sus primary keys y retorna un objeto tipo Condicion_Hotel con sus atributos completos
     * @param ID
     * @return condicion_hotel
     */
    public Condicion_Hotel consultarCondicion(int ID) {
        Operaciones op = new Operaciones();
        try {
            ResultSet resultSet = op.ConsultaEsp("SELECT * FROM Condicion_Hotel WHERE k_condicion = "+ID+"");
            resultSet.next();

            Condicion_Hotel condicion_hotel = new Condicion_Hotel(resultSet.getInt(1),resultSet.getBoolean(2),
                    resultSet.getDouble(3),resultSet.getDouble(4),resultSet.getInt(5));

            return condicion_hotel;

        }catch (SQLException ex){
            System.out.println(ex);
        }
        return null;
    }
}
