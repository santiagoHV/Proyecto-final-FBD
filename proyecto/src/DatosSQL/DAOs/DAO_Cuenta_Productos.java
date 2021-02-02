package DatosSQL.DAOs;

import DatosSQL.Operaciones;
import Modelo.entidades.Cuenta;
import Modelo.entidades.Cuenta_Productos;
import Modelo.entidades.PyS;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DAO_Cuenta_Productos {

    public Cuenta_Productos consultarCuentaProd(int k_codigo_pys, int k_cuenta) {
        Operaciones op = new Operaciones();
        try {
            ResultSet resultSet = op.ConsultaEsp("SELECT * FROM cuenta_productos WHERE k_codigo_pys = "+k_codigo_pys+" and k_cuenta = "+k_cuenta+" ");
            resultSet.next();

            Cuenta_Productos cuenta_productos = new Cuenta_Productos(resultSet.getInt(1),resultSet.getDate(2),
                    new DAO_PyS().consultarPyS(resultSet.getInt(3)),
                    new DAO_Cuenta().consultarCuenta(resultSet.getInt(4)));

            return cuenta_productos;


        }catch (SQLException ex){
            System.out.println(ex);
        }
        return null;
    }
}
