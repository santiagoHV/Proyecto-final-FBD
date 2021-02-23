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
    public boolean agregarProducto(int q_pedido,int codigo,int cuenta, double precio_venta){
        Operaciones op = new Operaciones();
        try {
            op.UpdateEsp("INSERT INTO cuenta_productos VALUES ("+q_pedido+",CURRENT_DATE,"+codigo+", "+cuenta+", '"+precio_venta+"')");
            return true;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
    public boolean actualizarProducto(int cantidad, int codigo_pys, int cuenta){
        Operaciones op = new Operaciones();
        try{
            op.UpdateEsp("UPDATE cuenta_productos SET q_pys_pedidos = "+ cantidad + ",f_pedido= CURRENT_DATE WHERE k_codigo_pys ="+ codigo_pys + "AND k_cuenta = "+ cuenta);
            return true;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
    public int consultarcantidad(int codigo_pys, int cuenta){
            Operaciones op = new Operaciones();
            try {
                ResultSet res = op.ConsultaEsp("SELECT q_pys_pedidos FROM cuenta_productos WHERE k_codigo_pys="+codigo_pys+"AND k_cuenta="+cuenta);
                res.next();
                return res.getInt(1);
            }catch (SQLException e){
                e.printStackTrace();
                return -1;

            }
        }
    }

