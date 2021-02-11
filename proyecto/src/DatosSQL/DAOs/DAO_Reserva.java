package DatosSQL.DAOs;

import DatosSQL.Operaciones;
import Modelo.entidades.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAO_Reserva {
    Operaciones op;

    public DAO_Reserva(){
        op = new Operaciones();
    }

    public Reserva consultarReserva(int ID){

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

    public int consultarUltimoCodigo() throws SQLException {

        ResultSet resultSet = op.ConsultaEsp("SELECT MAX(k_reserva) FROM reserva");
        resultSet.next();
        return resultSet.getInt(1);
    }

    public List<Reserva_Habitacion> consultarReservaHabPorIdReserva(int ID)
    {
        try {
            List<Reserva_Habitacion> reserva_habitacions = new ArrayList<>();
            ResultSet resultSet = op.ConsultaEsp("SELECT * FROM Reserva_Habitacion WHERE k_reserva = "+ID+"");
            Reserva_Habitacion reserva_habitacion;

            DAO_Habitacion dao_habitacion = new DAO_Habitacion();

            while(resultSet.next())
            {
                reserva_habitacion = new Reserva_Habitacion(consultarReserva(resultSet.getInt(1)), dao_habitacion.consultarHabitacion(resultSet.getInt(2)));
                reserva_habitacions.add(reserva_habitacion);
            }

            return reserva_habitacions;

        }catch (SQLException ex){
            System.out.println(ex + "En Reserva");
        }
        return null;
    }
}
