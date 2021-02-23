package DatosSQL.DAOs;

import DatosSQL.Operaciones;
import Modelo.entidades.Ocupacion_registro;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DAO_Ocupacion_Registro {
    private Operaciones op = new Operaciones();

    public ArrayList<Ocupacion_registro> getHistoricoOcupacion(String hab){
        try{
            ResultSet res = op.ConsultaEsp("SELECT concat(Persona.n_nombre,' ',Persona.n_apellido), Registro_CheckIn.f_entrada, Registro_CheckIn.f_salida, Reserva.k_reserva, Reserva.n_estado " +
                    "FROM huesped, registro_checkin, persona, reserva " +
                    "WHERE Huesped.k_identificacion = Registro_CheckIn.k_identificacion " +
                    "AND Huesped.k_tipo_documento = Registro_CheckIn.k_tipo_documento " +
                    "AND Huesped.k_tipo_documento = Persona.k_tipo_documento " +
                    "AND Huesped.k_identificacion = Persona.k_identificacion " +
                    "AND Reserva.k_reserva = Registro_CheckIn.k_reserva " +
                    "and Registro_CheckIn.k_numero_habitacion = "+hab+"");
            ArrayList<Ocupacion_registro> arr = new ArrayList<>();
            while (res.next()){
                arr.add(new Ocupacion_registro(res.getString(1), res.getString(2), res.getString(3), res.getInt(4), res.getString(5)));
            }
            return arr;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

}
