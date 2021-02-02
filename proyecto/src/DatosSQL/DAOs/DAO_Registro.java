package DatosSQL.DAOs;

import DatosSQL.Operaciones;
import Modelo.entidades.Habitacion;
import Modelo.entidades.Persona;
import Modelo.entidades.Registro;
import Modelo.entidades.Reserva;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DAO_Registro {

    public Registro consultarRegistro(int ID){
        Operaciones op = new Operaciones();
        try {
            ResultSet resultSet = op.ConsultaEsp("SELECT * FROM registro_checkin WHERE k_registro = "+ID+"");
            resultSet.next();

            Registro registro = new Registro(resultSet.getInt(1),resultSet.getDate(2),resultSet.getDate(3),
                    new DAO_Persona().consultarPersona(resultSet.getInt(5), resultSet.getString(4)),
                    new DAO_Reserva().consultarReserva(resultSet.getInt(6)),
                    new DAO_Habitacion().consultarHabitacion(resultSet.getInt(7)));

            return registro;


        }catch (SQLException ex){
            System.out.println(ex);
        }
        return null;
    }
}
