package Modelo.fabricas;

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
            ResultSet resultSet =
                    op.ConsultaEsp("SELECT * FROM Reserva WHERE k_reserva = "+k_reserva+" or k_identificacion = "+num_doc+" or " +
                            "k_identificacion = (SELECT k_identificacion FROM Persona WHERE n_nombre IN ('"+nom_or_apel+"') or n_apellido IN ('"+nom_or_apel+"')) ");

            Reserva reserva;
            while (resultSet.next()) {

                reserva = new Reserva();
                reserva.setK_reserva(resultSet.getInt(1));
                reserva.setEstado(resultSet.getString(2));
                reserva.setF_inicio(resultSet.getDate(3));
                reserva.setF_reserva(resultSet.getDate(4));
                reserva.setF_final(resultSet.getDate(5));
                reserva.setCantidad_adultos(resultSet.getInt(6));
                reserva.setPrecio_reserva(resultSet.getDouble(7));

                Condicion_Hotel condicion_hotel = new Condicion_Hotel();
                condicion_hotel.ConsultarCondicionHotel(resultSet.getInt(8));
                reserva.setCondicion(condicion_hotel);

                Persona persona = new Persona();
                persona.ConsultarPersona(resultSet.getInt(9),resultSet.getString(10));
                reserva.setPersona(persona);

                reservaList.add(reserva);
            }

            return reservaList;

        }catch (SQLException ex){
            System.out.println(ex);
        }
        return null;
    }
}
