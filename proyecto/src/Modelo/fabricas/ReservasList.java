package Modelo.fabricas;

import DatosSQL.DAOs.DAO_CondicionHotel;
import DatosSQL.DAOs.DAO_Persona;
import DatosSQL.Operaciones;
import Modelo.entidades.Condicion_Hotel;
import Modelo.entidades.Persona;
import Modelo.entidades.Reserva;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReservasList {

    public List<Reserva> BuscarReservas(int k_reserva, int num_doc, String nom_or_apel) {

        List<Reserva> reservaList = new ArrayList<>();

        Operaciones op = new Operaciones();
        try {
            String Query = "SELECT * FROM Reserva WHERE k_reserva = "+k_reserva+" or k_identificacion = "+num_doc+" or " +
                    "k_identificacion = (SELECT k_identificacion FROM Persona WHERE n_nombre IN ('"+nom_or_apel+"') or n_apellido IN ('"+nom_or_apel+"')) ";

            if(nom_or_apel.equals("") && k_reserva==0 && num_doc==0)
            {
                Query = "SELECT * FROM Reserva";
            }
            ResultSet resultSet =
                    op.ConsultaEsp(Query);

            Reserva reserva;
            while (resultSet.next()) {
                DAO_CondicionHotel condicion_hotel = new DAO_CondicionHotel();

                DAO_Persona persona = new DAO_Persona();

                reserva = new Reserva(resultSet.getInt(1),resultSet.getString(2),
                resultSet.getDate(3),resultSet.getDate(4),resultSet.getDate(5),
                resultSet.getInt(6), resultSet.getInt(7),resultSet.getInt(8),resultSet.getDouble(9),
                condicion_hotel.consultarCondicion(resultSet.getInt(10)),persona.consultarPersona(resultSet.getInt(11),resultSet.getString(12)));

                reservaList.add(reserva);
            }
            return reservaList;

        }catch (SQLException ex){
            System.out.println(ex);
        }
        return null;
    }
}