package DatosSQL.DAOs;

import DatosSQL.Conexion;
import DatosSQL.Operaciones;
import Modelo.entidades.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAO_Reserva {
    Operaciones op;

    public DAO_Reserva(){
        op = new Operaciones();
    }

    public Reserva consultarReserva(int ID){

        try {
            ResultSet resultSet = op.ConsultaEsp("SELECT * FROM Reserva WHERE k_reserva = "+ID+"");
            resultSet.next();

            Reserva reserva = new Reserva(resultSet.getInt(1),resultSet.getString(2),resultSet.getDate(3),
                    resultSet.getDate(4),resultSet.getDate(5),resultSet.getInt(6),resultSet.getInt(7),
                    resultSet.getInt(8),resultSet.getDouble(9),
                    new DAO_CondicionHotel().consultarCondicion(resultSet.getInt(10)),
                    new DAO_Persona().consultarPersona(resultSet.getInt(11),resultSet.getString(12)));

            return reserva;

        }catch (SQLException ex){
            System.out.println(ex + "En Reserva");
        }
        return null;
    }

    public int consultarUltimoCodigo() throws SQLException {

        ResultSet resultSet = op.ConsultaEsp("SELECT MAX(k_reserva) FROM reserva");
        resultSet.next();
        return resultSet.getInt(1);
    }

    public List<Reserva_Habitacion> consultarReservaHabPorIdReserva(int ID)
    {
        try {
            List<Reserva_Habitacion> reserva_habitacions = new ArrayList<>();
            ResultSet resultSet = op.ConsultaEsp("SELECT * FROM Reserva_Habitacion WHERE k_reserva = "+ID+"");
            Reserva_Habitacion reserva_habitacion;

            DAO_Habitacion dao_habitacion = new DAO_Habitacion();

            while(resultSet.next())
            {
                reserva_habitacion = new Reserva_Habitacion(consultarReserva(resultSet.getInt(1)), dao_habitacion.consultarHabitacion(resultSet.getInt(2)));
                reserva_habitacions.add(reserva_habitacion);
            }

            return reserva_habitacions;

        }catch (SQLException ex){
            System.out.println(ex + "En Reserva");
        }
        return null;
    }

    public void insertarReserva(Reserva reserva){
        try {
            PreparedStatement preparedStatement = Conexion.getInstance().getConnection().prepareStatement(
                    "INSERT INTO reserva VALUES(?,?,?,?,?,?,?,?,?,?,?,?);");

            preparedStatement.setInt(1, reserva.getK_reserva());
            preparedStatement.setString(2, reserva.getEstado());
            preparedStatement.setDate(3, reserva.getF_inicio());
            preparedStatement.setDate(4, reserva.getF_reserva());
            preparedStatement.setDate(5, reserva.getF_final());
            preparedStatement.setInt(6,reserva.getCantidad_bebes());
            preparedStatement.setInt(7,reserva.getCantidad_ninos());
            preparedStatement.setInt(8,reserva.getCantidad_adultos());
            preparedStatement.setDouble(9, reserva.getPrecio_reserva());
            preparedStatement.setInt(10,reserva.getCondicion().getK_condicion());
            preparedStatement.setInt(11,reserva.getPersona().getK_identificacion());
            preparedStatement.setString(12, reserva.getPersona().getK_tipo_documento_id());

            preparedStatement.executeUpdate();

        }catch (Exception e){
            System.out.println(e + " insertar persona");
        }
    }

    public void insertarHabitacionEnReserva(Reserva reserva, Habitacion habitacion) throws SQLException {
        PreparedStatement preparedStatement = Conexion.getInstance().getConnection().prepareStatement(
                "INSERT INTO reserva_habitacion VALUES(?,?);");

        preparedStatement.setInt(1,reserva.getK_reserva());
        preparedStatement.setInt(2,habitacion.getK_numero_habitacion());

        preparedStatement.executeUpdate();

    }
}
