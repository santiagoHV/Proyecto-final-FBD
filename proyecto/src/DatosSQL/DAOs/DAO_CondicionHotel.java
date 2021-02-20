package DatosSQL.DAOs;

import DatosSQL.Operaciones;
import Modelo.entidades.Condicion_Hotel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
                    resultSet.getDouble(3),resultSet.getDouble(4),resultSet.getInt(5), resultSet.getString(6));

            return condicion_hotel;

        }catch (SQLException ex){
            System.out.println(ex);
        }
        return null;
    }

    public ArrayList<Condicion_Hotel> getCondiciones(){
        Operaciones op = new Operaciones();
        ArrayList<Condicion_Hotel> cond = new ArrayList<>();
        try {
            ResultSet res = op.ConsultaEsp("SELECT * FROM condicion_hotel");
            while (res.next()){
                cond.add(new Condicion_Hotel(res.getInt(1),res.getBoolean(2), res.getDouble(3),res.getDouble(4),res.getInt(5), res.getString(6)));
            }
            return cond;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
