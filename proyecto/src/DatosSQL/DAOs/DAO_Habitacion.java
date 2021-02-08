package DatosSQL.DAOs;

import DatosSQL.Operaciones;
import Modelo.entidades.Habitacion;
import Modelo.entidades.Tipo;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DAO_Habitacion {

    public Habitacion consultarHabitacion(int ID){
        Operaciones op = new Operaciones();
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
}
