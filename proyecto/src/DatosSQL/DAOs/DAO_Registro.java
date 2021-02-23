package DatosSQL.DAOs;

import DatosSQL.Conexion;
import DatosSQL.Operaciones;
import Modelo.entidades.Registro;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DAO_Registro {


    public int consultarHabitacion(String habitacion) {
        Operaciones op = new Operaciones();
        try {
            ResultSet res = op.ConsultaEsp("SELECT k_reserva FROM registro_checkin WHERE k_numero_habitacion = "+habitacion+"");
            res.next();
            return res.getInt(1);
        } catch (SQLException e) {
            return -1;
        }
    }
    public String consultarsalida(String habitacion){
        Operaciones op = new Operaciones();
        try {
            ResultSet res = op.ConsultaEsp("SELECT f_salida FROM registro_checkin WHERE k_numero_habitacion = "+habitacion+"");
            res.next();
            return res.getString(1);
        } catch (SQLException e) {
            return null;
        }
    }

    public Registro consultarRegistro(int ID){
        Operaciones op = new Operaciones();
        try {
            ResultSet resultSet = op.ConsultaEsp("SELECT * FROM registro_checkin WHERE k_registro = "+ID+"");

            List<Registro> registroList = new ArrayList<>();

            while(resultSet.next())
            {
                Registro registro = new Registro(resultSet.getInt(1),resultSet.getDate(2),resultSet.getDate(3),
                        new DAO_Huesped().consultarHuesped(resultSet.getInt(5), resultSet.getString(4)),
                        new DAO_Reserva().consultarReserva(resultSet.getInt(6)),
                        new DAO_Habitacion().consultarHabitacion(resultSet.getInt(7)));
                registroList.add(registro);
            }

            return (Registro) registroList;


        }catch (SQLException ex){
            System.out.println(ex);
        }
        return null;
    }

    public Registro consultarRegistroPorHuesped(int ID_Huesped, String tipo_ID){
        Operaciones op = new Operaciones();
        try {
            LocalDate locald = LocalDate.now();
            java.sql.Date sqlDate = java.sql.Date.valueOf(locald);
            ResultSet resultSet = op.ConsultaEsp("SELECT Registro_CheckIn.* FROM registro_checkin, reserva WHERE " +
                    "(registro_checkin.k_reserva = reserva.k_reserva and Registro_CheckIn.k_identificacion=Reserva.k_identificacion and " +
                    "Registro_CheckIn.k_tipo_documento=Reserva.k_tipo_documento) and (registro_checkin.k_identificacion = "+ID_Huesped+" and registro_checkin.k_tipo_documento = '"+tipo_ID+"'" +
                    " and '"+sqlDate+"' between f_entrada and (reserva.f_reserva))");

            if(resultSet.next())
            {
                DAO_Huesped Huesped = new DAO_Huesped();
                DAO_Reserva dao_reserva = new DAO_Reserva();
                DAO_Habitacion dao_habitacion = new DAO_Habitacion();

                Registro registro = new Registro(resultSet.getInt(1),resultSet.getDate(2),resultSet.getDate(3),
                        Huesped.consultarHuesped(resultSet.getInt(5), resultSet.getString(4)),
                        dao_reserva.consultarReserva(resultSet.getInt(6)),
                        dao_habitacion.consultarHabitacion(resultSet.getInt(7)));
                return registro;
            }

        }catch (SQLException ex){
            System.out.println(ex);
        }
        return null;
    }

    public List<Registro> consultarRegistroPorReserva(int ID_Reserva){
        Operaciones op = new Operaciones();
        try {
            ResultSet resultSet = op.ConsultaEsp("SELECT registro_checkin.* FROM registro_checkin, reserva " +
                    "WHERE (Registro_CheckIn.k_reserva=Reserva.k_reserva) and registro_checkin.k_reserva = "+ID_Reserva+"" +
                    " and n_estado = 'en curso'");

            List<Registro> registroList = new ArrayList<>();

            while(resultSet.next())
            {
                Registro registro = new Registro(resultSet.getInt(1),resultSet.getDate(2),resultSet.getDate(3),
                        new DAO_Huesped().consultarHuesped(resultSet.getInt(5), resultSet.getString(4)),
                        new DAO_Reserva().consultarReserva(resultSet.getInt(6)),
                        new DAO_Habitacion().consultarHabitacion(resultSet.getInt(7)));
                registroList.add(registro);
            }

            return registroList;
        }catch (SQLException ex){
            System.out.println(ex);
        }
        return null;
    }

    public int insertarRegistro(Registro registro){
        try {
            PreparedStatement preparedStatement = Conexion.getInstance().getConnection().prepareStatement(
                    "INSERT INTO registro_checkin VALUES((nextval('registro_checkin_k_registro_seq')),?,?,?,?,?,?);");

            //preparedStatement.setInt(1, registro.getK_registro());
            preparedStatement.setDate(1, registro.getF_entrada());
            preparedStatement.setDate(2, registro.getF_salida());
            preparedStatement.setString(3, registro.getHuesped().getK_tipo_documento_id());
            preparedStatement.setInt(4, registro.getHuesped().getK_identificacion());
            preparedStatement.setInt(5, registro.getReserva().getK_reserva());
            preparedStatement.setInt(6, registro.getHabitacion().getK_numero_habitacion());

           return preparedStatement.executeUpdate();

        }catch (Exception e){
            System.out.println(e + "En Registro");
        }
        return 0;
    }

    public int actualizarRegistro(Registro registro)
    {
        try {
            PreparedStatement preparedStatement = Conexion.getInstance().getConnection().prepareStatement(
                    "UPDATE registro_checkin set f_salida = ?, k_tipo_documento = ?, " +
                            "k_identificacion = ?, k_reserva = ?, k_numero_habitacion = ? WHERE k_registro = ?");

            //preparedStatement.setDate(1, registro.getF_entrada());
            preparedStatement.setDate(1, registro.getF_salida());
            preparedStatement.setString(2, registro.getHuesped().getK_tipo_documento_id());
            preparedStatement.setInt(3, registro.getHuesped().getK_identificacion());
            preparedStatement.setInt(4, registro.getReserva().getK_reserva());
            preparedStatement.setInt(5, registro.getHabitacion().getK_numero_habitacion());
            preparedStatement.setInt(6, registro.getK_registro());

            return preparedStatement.executeUpdate();

        }catch (Exception e){
            System.out.println(e + "En Registro");
        }
        return 0;
    }
}
