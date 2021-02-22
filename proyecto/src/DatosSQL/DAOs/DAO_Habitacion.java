package DatosSQL.DAOs;

import DatosSQL.Operaciones;
import Modelo.entidades.Habitacion;
import Modelo.entidades.Tipo;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DAO_Habitacion {
    Operaciones op;

    public DAO_Habitacion(){
        op = new Operaciones();
    }

    public Habitacion consultarHabitacion(int ID){

        try {
            ResultSet resultSet = op.ConsultaEsp("SELECT * FROM Habitacion WHERE k_numero_habitacion = "+ID+"");
            resultSet.next();

            Habitacion habitacion = new Habitacion(resultSet.getInt(1),
                    new DAO_Tipo().consultarTipoHab(resultSet.getString(2)));

            return habitacion;

        }catch (SQLException ex){
            System.out.println(ex + "En Habitación");
        }
        return null;
    }

    public ArrayList<Integer> consultarHbitacionesOcupadasPorFecha(Date fInicio, Date fFinal) throws SQLException {
        ArrayList<Integer> habitaciones = new ArrayList<Integer>();
        try{
            ResultSet resultSet = op.ConsultaEsp("SELECT distinct (rh.k_numero_habitacion) FROM reserva r, reserva_habitacion rh" +
                    " WHERE r.k_reserva = rh.k_reserva and " +
                    "      (('" + fInicio + "' BETWEEN r.f_inicio and r.f_final OR '" + fFinal + "' BETWEEN r.f_inicio and r.f_final) OR " +
                    "      (r.f_inicio BETWEEN '" + fInicio + "' and '" + fFinal + "' OR r.f_final BETWEEN '" + fInicio + "' and '" + fFinal + "'));");


            while (resultSet.next()){
                habitaciones.add(resultSet.getInt(1));
                System.out.println(resultSet.getInt(1));
            }

            return habitaciones;
        }catch (SQLException ex){
            System.out.println(ex + "En Habitación");
        }
        return null;
    }

    public String estadoHabitacion(String id){
        try{
            ResultSet res = op.ConsultaEsp("SELECT r.n_estado FROM habitacion h, reserva_habitacion rh, reserva r WHERE h.k_numero_habitacion = rh.k_numero_habitacion AND rh.k_reserva = r.k_reserva AND h.k_numero_habitacion = " + id);
            while(res.next()){
                if(res.getString(1).equals("en curso")){
                    return "ocupada";
                }
            }
            return "disponible";
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }


}
