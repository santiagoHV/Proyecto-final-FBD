package Modelo.entidades;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Cuenta_Productos_Table extends RecursiveTreeObject<Cuenta_Productos_Table> {

    public StringProperty codigoProducto;
    public StringProperty nombreProducto;
    public StringProperty cantidadPedida;
    public StringProperty precioVenta;
    public StringProperty fechaPedido;
    public StringProperty codigoCuenta;
    public StringProperty titularReserva;


    public Cuenta_Productos_Table(Cuenta_Productos cuenta_productos){
        this.codigoProducto = new SimpleStringProperty(cuenta_productos.getPys().getK_codigo_pys()+"");
        this.nombreProducto = new SimpleStringProperty(cuenta_productos.getPys().getUnidad()+"");
        this.cantidadPedida = new SimpleStringProperty(cuenta_productos.getPys_pedidos()+"");
        this.fechaPedido = new SimpleStringProperty(cuenta_productos.getF_pedido()+"");
        this.codigoCuenta = new SimpleStringProperty(cuenta_productos.getCuenta().getK_cuenta()+"");
        this.titularReserva = new SimpleStringProperty(cuenta_productos.getCuenta().getReserva().getPersona().getN_nombre() + " " +
                cuenta_productos.getCuenta().getReserva().getPersona().getN_apellido());
        this.precioVenta = new SimpleStringProperty(cuenta_productos.getPrecio_venta()+"");
    }
}
