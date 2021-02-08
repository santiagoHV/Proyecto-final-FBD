package DatosSQL.DAOs;

import DatosSQL.Operaciones;
import Modelo.entidades.Condicion_Hotel;
import Modelo.entidades.Persona;
import Modelo.entidades.Reserva;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DAO_Reserva {

    public Reserva consultarReserva(int ID){
        Operaciones op = new Operaciones();
        try {
            ResultSet resultSet = op.ConsultaEsp("SELECT * FROM Reserva WHERE k_reserva = "+ID+"");
            resultSet.next();

            Reserva reserva = new Reserva(resultSet.getInt(1),resultSet.getString(2),resultSet.getDate(3),
                    resultSet.getDate(4),resultSet.getDate(5),resultSet.getInt(6),resultSet.getInt(7),
                    resultSet.getInt(8),resultSet.getDouble(9),
                    new DAO_CondicionHotel().consultarCondicion(resultSet.getInt(10)),
                    new DAO_Persona().consultarPersona(resultSet.getInt(11),resultSet.getString(12)));

            return reserva;

        }catch (SQLException ex){
            System.out.println(ex + "En Reserva");
        }
        return null;
    }
}
