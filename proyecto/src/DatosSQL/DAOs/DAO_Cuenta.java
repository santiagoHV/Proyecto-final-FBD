package DatosSQL.DAOs;

import DatosSQL.Operaciones;
import Modelo.entidades.Cuenta;
import Modelo.entidades.Pago;
import Modelo.entidades.PyS;
import Modelo.entidades.Reserva;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DAO_Cuenta {

    public Cuenta consultarCuenta(int ID){
        Operaciones op = new Operaciones();
        try {
            ResultSet resultSet = op.ConsultaEsp("SELECT * FROM Cuenta WHERE k_cuenta = "+ID+"");
            resultSet.next();

            Cuenta cuenta = new Cuenta(resultSet.getInt(1),resultSet.getDouble(2),
                                        new DAO_Pago().consultarPago(resultSet.getInt(3)),
                                        new DAO_Reserva().consultarReserva(resultSet.getInt(4)));
            return cuenta;


        }catch (SQLException ex){
            System.out.println(ex);
        }

        return null;
    }


    public static boolean actualizarPrecio(int Total,String reserva){
        Operaciones op = new Operaciones();
        try{
            op.UpdateEsp("UPDATE cuenta SET v_precio_acumulado = v_precio_acumulado+"+ Total + " WHERE k_reserva ="+ reserva );
            return true;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
    public static int conseguirCuenta(int k_reserva){
        Operaciones op = new Operaciones();
        try {
            ResultSet res = op.ConsultaEsp("SELECT * FROM cuenta WHERE k_reserva ="+ k_reserva+"");
            res.next();
            return res.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }

    }
}
