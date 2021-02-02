package DatosSQL.DAOs;

import DatosSQL.Operaciones;
import Modelo.entidades.Pago;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DAO_Pago {

    public Pago consultarPago(int ID){
        Operaciones op = new Operaciones();
        try {
            ResultSet resultSet = op.ConsultaEsp("SELECT * FROM Pago WHERE k_pago = "+ID+"");
            resultSet.next();

            Pago pago = new Pago(resultSet.getInt(1),resultSet.getDate(2),
                    resultSet.getDouble(3),resultSet.getString(4));

            return pago;

        }catch (SQLException ex){
            System.out.println(ex);
        }

        return null;
    }
}
