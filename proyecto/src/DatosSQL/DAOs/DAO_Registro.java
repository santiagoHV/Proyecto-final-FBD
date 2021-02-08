package DatosSQL.DAOs;

import DatosSQL.Operaciones;
import Modelo.entidades.Habitacion;
import Modelo.entidades.Persona;
import Modelo.entidades.Registro;
import Modelo.entidades.Reserva;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

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

    public Registro consultarRegistroPorHuesped(int ID_Huesped, String tipo_ID){
        Operaciones op = new Operaciones();
        try {
            LocalDate locald = LocalDate.now();
            java.sql.Date sqlDate = java.sql.Date.valueOf(locald);
            ResultSet resultSet = op.ConsultaEsp("SELECT * FROM registro_checkin " +
                    "WHERE (k_identificacion = "+ID_Huesped+" and k_tipo_documento = '"+tipo_ID+"') " +
                    "and ('"+sqlDate+"' BETWEEN f_entrada and f_salida)");

            if(resultSet.next())
            {
                DAO_Persona persona = new DAO_Persona();
                DAO_Reserva dao_reserva = new DAO_Reserva();
                DAO_Habitacion dao_habitacion = new DAO_Habitacion();

                Registro registro = new Registro(resultSet.getInt(1),resultSet.getDate(2),resultSet.getDate(3),
                        persona.consultarPersona(resultSet.getInt(5), resultSet.getString(4)),
                        dao_reserva.consultarReserva(resultSet.getInt(6)),
                        dao_habitacion.consultarHabitacion(resultSet.getInt(7)));
                return registro;
            }

        }catch (SQLException ex){
            System.out.println(ex);
        }
        return null;
    }
}
