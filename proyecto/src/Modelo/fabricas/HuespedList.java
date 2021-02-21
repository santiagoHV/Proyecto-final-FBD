package Modelo.fabricas;

import DatosSQL.DAOs.DAO_CondicionHotel;
import DatosSQL.DAOs.DAO_Persona;
import DatosSQL.Operaciones;
import Modelo.entidades.Huesped;
import Modelo.entidades.Reserva;
import com.sun.javafx.scene.traversal.Hueristic2D;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HuespedList
{
    public List<Huesped> BuscarHuespedes(int k_reserva, int num_doc, String nom_or_apel) {

        List<Huesped> huespedList = new ArrayList<>();

        Operaciones op = new Operaciones();
        try {
            String Query = "SELECT distinct Huesped.*, Persona.n_nombre, Persona.n_apellido, Persona.n_telefono, Persona.f_nacimiento " +
                    " FROM Huesped, Persona, registro_checkin WHERE (Huesped.k_identificacion = Persona.k_identificacion and Huesped.k_tipo_documento = Persona.k_tipo_documento) " +
                    " and (Huesped.k_identificacion = "+num_doc+" or Huesped.k_identificacion IN (SELECT Persona.k_identificacion FROM Persona WHERE n_nombre ILIKE ('"+nom_or_apel+"%') or n_apellido ILIKE ('"+nom_or_apel+"%')) or k_reserva="+k_reserva+");";

            if(nom_or_apel.equals("") && k_reserva==0 && num_doc==0)
            {
                Query = "SELECT distinct Huesped.*, Persona.n_nombre, Persona.n_apellido, Persona.n_telefono, Persona.f_nacimiento " +
                        " FROM Huesped, Persona, registro_checkin WHERE (Huesped.k_identificacion = Persona.k_identificacion and Huesped.k_tipo_documento = Persona.k_tipo_documento)";
            }
            ResultSet resultSet = op.ConsultaEsp(Query);

            Huesped Huesped = null;

            while (resultSet.next()) {

                Huesped = new Huesped(resultSet.getInt(2),resultSet.getString(3),resultSet.getString(4),
                        resultSet.getString(5),resultSet.getDate(7),resultSet.getString(6),resultSet.getString(1));
                huespedList.add(Huesped);
            }
            return huespedList;

        }catch (SQLException ex){
            System.out.println(ex);
        }
        return null;
    }
}
