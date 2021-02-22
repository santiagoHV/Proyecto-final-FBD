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
            ResultSet res = op.ConsultaEsp("SELECT CONCAT(p.n_nombre, ' ', p.n_apellido), r.f_inicio, r.f_final, r.k_reserva, r.n_estado " +
                                  "FROM habitacion h, reserva_habitacion rh, reserva r, persona p " +
                                  "WHERE h.k_numero_habitacion = rh.k_numero_habitacion AND " +
                                         "rh.k_reserva = r.k_reserva AND " +
                                         "p.k_identificacion = r.k_identificacion AND " +
                                         "p.k_tipo_documento = r.k_tipo_documento AND " +
                                         "h.k_numero_habitacion = " + hab);
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
