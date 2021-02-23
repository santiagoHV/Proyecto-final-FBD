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
    Operaciones op;
    public DAO_Cuenta_Productos(){
        op = new Operaciones();
    }

    public List<Cuenta_Productos> consultarCuentaProdPorCuenta(int k_cuenta) {
        List<Cuenta_Productos> cuenta_productosList = new ArrayList<>();

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

    /**
     * Consulta el total de consumos por una reserva
     * @param ID
     * @return
     * @throws SQLException
     */
    public double consultarTotalDeConsumosPorReserva(int ID) throws SQLException {

        try {
            ResultSet resultSet = op.ConsultaEsp("SELECT SUM( v_precio_venta ) FROM cuenta_productos cp WHERE k_cuenta = " + ID);

            resultSet.next();
            return resultSet.getDouble(1);
        }catch (Exception e){

            return 0;
        }
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
                if(res.next()){
                    return res.getInt(1);
                }
                else{
                    return -1;
                }
            }catch (SQLException e){
                e.printStackTrace();
                return -1;

            }
        }
    }

