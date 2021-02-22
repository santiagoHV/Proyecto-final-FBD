package DatosSQL.DAOs;

import DatosSQL.Operaciones;
import Modelo.entidades.Cuenta;
import Modelo.entidades.Cuenta_Productos;
import Modelo.entidades.PyS;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAO_Cuenta_Productos {

    public List<Cuenta_Productos> consultarCuentaProdPorCuenta(int k_cuenta) {
        List<Cuenta_Productos> cuenta_productosList = new ArrayList<>();
        Operaciones op = new Operaciones();
        try {
            ResultSet resultSet = op.ConsultaEsp("SELECT * FROM cuenta_productos WHERE k_cuenta = "+k_cuenta+" ");
            while(resultSet.next())
            {
                cuenta_productosList.add(new Cuenta_Productos(resultSet.getInt(1),resultSet.getDate(2),
                        resultSet.getDouble(5), new DAO_PyS().consultarPyS(resultSet.getInt(3)),
                        new DAO_Cuenta().consultarCuenta(resultSet.getInt(4))));
            }

            return cuenta_productosList;
        }catch (SQLException ex){
            System.out.println(ex);
        }
        return null;
    }
}
