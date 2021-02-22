package DatosSQL.DAOs;

import DatosSQL.Conexion;
import DatosSQL.Operaciones;
import Modelo.entidades.*;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DAO_Reserva {
    Operaciones op;

    public DAO_Reserva(){
        op = new Operaciones();
    }

    /**
     * Consulta una reserva por su ID
     * @param ID
     * @return
     */
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

    /**
     * Consulta el ultimo id ingresado en la tabla de reservas
     * @return
     * @throws SQLException
     */
    public int consultarUltimoCodigo() throws SQLException {

        ResultSet resultSet = op.ConsultaEsp("SELECT MAX(k_reserva) FROM reserva");
        resultSet.next();
        return resultSet.getInt(1);
    }

    /**
     * Consulta y retorna una lista de tuplas de habitacion_reserva
     * por su codigo de reserva
     * @param ID
     * @return
     */
    public List<Reserva_Habitacion> consultarReservaHabPorIdReserva(int ID) {
        try {
            List<Reserva_Habitacion> reserva_habitacions = new ArrayList<>();
            ResultSet resultSet = op.ConsultaEsp("SELECT Reserva_Habitacion.* FROM Reserva_Habitacion, reserva " +
                    "WHERE (Reserva_Habitacion.k_reserva = reserva.k_reserva) and Reserva_Habitacion.k_reserva = "+ID+" and n_estado='en curso'");
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

    /**
     * Inserta un nuevo registro a la tabla reserva
     * @param reserva
     */
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

    /**
     * Inserta un registro en reserva_habitacion
     * @param reserva
     * @param habitacion
     * @throws SQLException
     */
    public void insertarHabitacionEnReserva(Reserva reserva, Habitacion habitacion) throws SQLException {
        PreparedStatement preparedStatement = Conexion.getInstance().getConnection().prepareStatement(
                "INSERT INTO reserva_habitacion VALUES(?,?);");

        preparedStatement.setInt(1,reserva.getK_reserva());
        preparedStatement.setInt(2,habitacion.getK_numero_habitacion());

        preparedStatement.executeUpdate();
    }

    /**
     * Consulta y retorna la cantidad maxima de personas hospedadas entre dos fechas
     * @param fInicio
     * @param fFinal
     * @return
     */
    public int consultarCantidadDePersonasHospedadas(Date fInicio, Date fFinal) {
        try {
            ResultSet resultSet = op.ConsultaEsp("SELECT sum(q_cantidad_bebes), sum(q_cantidad_ninos), sum(q_cantidad_adultos) FROM reserva r" +
                    " WHERE " +
                    "      (('" + fInicio + "' BETWEEN r.f_inicio and r.f_final OR '" + fFinal + "' BETWEEN r.f_inicio and r.f_final) OR " +
                    "      (r.f_inicio BETWEEN '" + fInicio + "' and '" + fFinal + "' OR r.f_final BETWEEN '" + fInicio + "' and '" + fFinal + "'));");

            resultSet.next();
            return resultSet.getInt(1) + resultSet.getInt(2) + resultSet.getInt(3);
        } catch (SQLException ex) {
            System.out.println(ex + "cantidad de personas hospedadas");
        }
        return 0;
    }

    /**
     * Cambia el estado de reserva activa a en curso cuando inicia la fecha de reserva
     * @throws SQLException
     */
    public void actualizarEstadoDeReservasEnCurso(){
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = Conexion.getInstance().getConnection().prepareStatement("UPDATE reserva" +
                    " SET n_estado = 'en curso' WHERE f_inicio = ? AND n_estado != 'en curso'");

            preparedStatement.setDate(1, Date.valueOf(LocalDate.now()));
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            System.out.println(throwables + " en actualizacion");
            throwables.printStackTrace();
        }
    }

    public List<Reserva> BuscarReservas(int k_reserva, int num_doc, String nom_or_apel) {

        List<Reserva> reservaList = new ArrayList<>();

        Operaciones op = new Operaciones();
        try {
            String Query = "SELECT * FROM Reserva WHERE k_reserva = "+k_reserva+" or k_identificacion = "+num_doc+" or " +
                    "k_identificacion IN (SELECT k_identificacion FROM Persona WHERE n_nombre ILIKE ('"+nom_or_apel+"%') or n_apellido ILIKE ('"+nom_or_apel+"%')) ";

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
