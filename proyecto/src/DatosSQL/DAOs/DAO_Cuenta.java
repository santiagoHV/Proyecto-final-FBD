package DatosSQL.DAOs;

import DatosSQL.Operaciones;
import Modelo.entidades.Cuenta;
import Modelo.entidades.Pago;
import Modelo.entidades.Reserva;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DAO_Cuenta {

    public Cuenta consultarCuenta(int ID){
        Operaciones op = new Operaciones();
        try {
            ResultSet resultSet = op.ConsultaEsp("SELECT * FROM Cuenta WHERE k_cuenta = "+ID+"");
            resultSet.next();

            Cuenta cuenta = new Cuenta(resultSet.getInt(1),resultSet.getDouble(2),
                    new DAO_Reserva().consultarReserva(resultSet.getInt(3)));

            return cuenta;


        }catch (SQLException ex){
            System.out.println(ex);
        }

        return null;
    }

    public Cuenta consultarCuentaPorReserva(int IDReserva){
        Operaciones op = new Operaciones();
        try {
            ResultSet resultSet = op.ConsultaEsp("SELECT * FROM Cuenta WHERE k_reserva= "+IDReserva+"");
            resultSet.next();

            Cuenta cuenta = new Cuenta(resultSet.getInt(1),resultSet.getDouble(2),
                    new DAO_Reserva().consultarReserva(resultSet.getInt(3)));

            return cuenta;


        }catch (SQLException ex){
            System.out.println(ex);
        }

        return null;
    }
}
