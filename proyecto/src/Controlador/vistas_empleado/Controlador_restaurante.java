package Controlador.vistas_empleado;

import DatosSQL.DAOs.*;
import Modelo.entidades.PyS;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import javax.swing.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controlador_restaurante implements Initializable {


    public StackPane empleado;
    public Label RC1,RN1,RP1;
    public JFXTextArea REMuestra,RCA1;
    public JFXTextField TCantidad;
    public JFXButton RCAR1,RPoner,RLimpiar;
    public AnchorPane RPane,PPane;
    public TableColumn ID,Nombre,Stock,Cantidad,Precio,Cargar;
    public ComboBox<String> RDHabitacion,RDPiso;
    public ObservableList<PyS> OBS;
    public TableView<PyS> productos;
    public StackPane restaurante;
    public ArrayList<Integer> Precio_Cargo;
    public int ICantidad;
    public int IStock;
    public int identificacion;
    public double precio;
    public boolean cond=true,aceptar=true;
    public String habitacion;
    public String reserva;
    int cuenta ;
    private int Total=0;
    private String categoria="Restaurante";
    DAO_Cuenta DAOC = new DAO_Cuenta();
    DAO_Cuenta_Productos DAOCP = new DAO_Cuenta_Productos();
    DAO_Registro DAOR =  new DAO_Registro();
    DAO_Pago DAOP = new DAO_Pago();

   //Se genera las dos listas que van a servir para todos los botones
    ObservableList<String> comboPisoContent =
            FXCollections.observableArrayList(
                    "2","3","4","5","6","7","8","9"
            );
    ObservableList<String> comboHabitacionContent =
            FXCollections.observableArrayList(
                    "01","02","03","04","05","06"
            );

    //Se insertan las listas a la interfaz grafica
    @Override
     public void initialize(URL url, ResourceBundle resourceBundle) {
         RDPiso.setItems(comboPisoContent);
         RDHabitacion.setItems(comboHabitacionContent);
         loadProductos();

    }

    public ObservableList<PyS> getProductosByCategoria(){
        ObservableList<PyS> obs = FXCollections.observableArrayList();
        for(PyS p: new DAO_PyS().consultarPorCategoria(categoria)){
            obs.add(p);
        }
        return obs;
    }

    public void loadProductos() {

        OBS = getProductosByCategoria();

        ID.setCellValueFactory(new PropertyValueFactory("k_codigo_pys"));
        Nombre.setCellValueFactory(new PropertyValueFactory("unidad"));
        Stock.setCellValueFactory(new PropertyValueFactory("stock"));
        Precio.setCellValueFactory(new PropertyValueFactory("precio_producta"));
        productos.setItems(OBS);

    }
    //Aquí se determinan las acciones que va a realiza un boton al hacer click sobre el
    //Falta poner las condiciones para que no los vuelvan a registrar
    public void cargar(ActionEvent actionEvent){

        ICantidad=Integer.parseInt(TCantidad.getText());
        precio = productos.getSelectionModel().getSelectedItem().getPrecio_producta();
        IStock = productos.getSelectionModel().getSelectedItem().getStock();
        identificacion = productos.getSelectionModel().getSelectedItem().getK_codigo_pys();

        REMuestra.appendText( "Producto: " + String.valueOf(productos.getSelectionModel().getSelectedItem().getUnidad() + "\n"));
        REMuestra.appendText("Precio de venta:" + String.valueOf(productos.getSelectionModel().getSelectedItem().getPrecio_producta() + "\n"));

        if((IStock-ICantidad)>=0){
            REMuestra.appendText("Cantidad: " + TCantidad.getText() + "\n");
            new DAO_PyS().modificarStock(String.valueOf(productos.getSelectionModel().getSelectedItem().getK_codigo_pys()), String.valueOf(IStock-ICantidad));
            loadProductos();
            aceptar=false;
        }
        else{
            Alert a1 = new Alert(Alert.AlertType.WARNING);
            a1.setTitle("Stock invalido");
            a1.setContentText("Stock invalido, se pondra el maximo stock disponible");
            a1.setHeaderText(null);
            a1.showAndWait();
            REMuestra.appendText("Cantidad: " + IStock + "\n");
            new DAO_PyS().modificarStock(String.valueOf(productos.getSelectionModel().getSelectedItem().getK_codigo_pys()), "0");
            loadProductos();
            aceptar=false;
        }

        cuenta = DAOC.conseguirCuenta(Integer.parseInt(reserva));

           if(DAOCP.consultarcantidad(identificacion,cuenta)==-1) {
               DAOCP.agregarProducto(ICantidad, identificacion, cuenta, precio);
           }else{
               DAOCP.actualizarProducto(ICantidad,identificacion,cuenta);
           }

        Total += ICantidad*precio;
    }

    public void onclickhabitacion(ActionEvent actionEvent){
        habitacion= RDPiso.getValue() + RDHabitacion.getValue();
        reserva = String.valueOf(DAOR.consultarHabitacion(habitacion));
        if (DAOR.consultarHabitacion(habitacion)!=-1 && cond){
            cuenta = DAOC.conseguirCuenta(Integer.parseInt(reserva));
        }
        if(DAOR.consultarHabitacion(habitacion)!=-1 && cond && (DAOP.consultarExistenciaPago(cuenta)==1)){
            REMuestra.appendText("Habitación No: " + RDPiso.getValue() + RDHabitacion.getValue() + "\n");
            cond = false;
        } else if (DAOP.consultarExistenciaPago(cuenta) == -1){
            Alert a6 = new Alert(Alert.AlertType.ERROR);
            a6.setContentText("El huesped ya realizo el pago de la habitación");
            a6.setTitle("Pago cliente");
            a6.setHeaderText(null);
            a6.showAndWait();
        } else if(cond==true){
            Alert a2 = new Alert(Alert.AlertType.ERROR);
            a2.setContentText("Esta habitación está disponible, porfavor escoger una ocupada");
            a2.setTitle("Habitación vacia");
            a2.setHeaderText(null);
            a2.showAndWait();
        } else{
            Alert a3 = new Alert(Alert.AlertType.ERROR);
            a3.setContentText("Ya cargo una habitación");
            a3.setTitle("Error Habitación");
            a3.setHeaderText(null);
            a3.showAndWait();
        }

    }

    public void onClickedCargar(MouseEvent event){
        if(aceptar==false ){
            DAOC.actualizarPrecio(Total,reserva);
            REMuestra.appendText( "----------------------------\n");
            REMuestra.appendText( "Total:" + Total);
        } else{

            Alert a5 = new Alert(Alert.AlertType.ERROR);
            a5.setContentText("Ingrese algun producto");
            a5.setTitle("Error producto");
            a5.setHeaderText(null);
            a5.showAndWait();
        }

    }


    public void onClickedLimpiar(MouseEvent event){

        if(aceptar==false) {
            new DAO_PyS().modificarStock(String.valueOf(identificacion), String.valueOf(IStock));
            loadProductos();
            aceptar=true;
        }  else if(aceptar==false){
            Alert a5 = new Alert(Alert.AlertType.ERROR);
            a5.setContentText("No se puede borrar lo que está vacio");
            a5.setTitle("Error area vacia");
            a5.setHeaderText(null);
            a5.showAndWait();
        }
            REMuestra.clear();
            Total=0;
            cond=true;

    }



}



