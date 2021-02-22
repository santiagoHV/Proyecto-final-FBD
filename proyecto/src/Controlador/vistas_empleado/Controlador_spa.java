package Controlador.vistas_empleado;

import DatosSQL.DAOs.DAO_Cuenta;
import DatosSQL.DAOs.DAO_Cuenta_Productos;
import DatosSQL.DAOs.DAO_PyS;
import DatosSQL.DAOs.DAO_Registro;
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

public class Controlador_spa implements Initializable {


    public StackPane empleado;
    public Label RC1,RN1,RP1;
    public JFXTextArea REMuestra,RCA1;
    public JFXTextField TCantidad;
    public JFXButton RCAR1,RPoner,RLimpiar;
    public AnchorPane RPane,PPane;
    public TableColumn ID,Nombre,Stock,Cantidad,Precio,Cargar;
    public ProgressIndicator progresi;
    public ComboBox<String> RDHabitacion,RDPiso;
    public ObservableList<PyS> OBS;
    public TableView<PyS> productos;
    public StackPane restaurante;
    public ArrayList<Integer> Precio_Cargo;
    public int ICantidad,IStock,identificacion;
    public double precio;
    public boolean cond=true,aceptar=true;
    public String habitacion;
    public String reserva;
    int cuenta ;
    private int Total=0;
    private String categoria="Spa";


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
        System.out.println(obs);
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
            JOptionPane.showMessageDialog(null, "Stock invalido, se pondra el maximo stock disponible");
            REMuestra.appendText("Cantidad: " + IStock + "\n");
            new DAO_PyS().modificarStock(String.valueOf(productos.getSelectionModel().getSelectedItem().getK_codigo_pys()), "0");
            loadProductos();
        }

        cuenta = DAO_Cuenta.conseguirCuenta(Integer.parseInt(reserva));

        DAO_Cuenta_Productos.agregarProducto(ICantidad,identificacion,cuenta,precio);

        Total += ICantidad*precio;
    }

    public void onclickhabitacion(ActionEvent actionEvent){

        habitacion= RDPiso.getValue() + RDHabitacion.getValue();
        reserva = String.valueOf(DAO_Registro.consultarHabitacion(habitacion));
        if(DAO_Registro.consultarHabitacion(habitacion)!=-1 && cond) {
            REMuestra.appendText("Habitación No: " + RDPiso.getValue() + RDHabitacion.getValue() + "\n");
            cond=false;
        } else if(cond==true){
            JOptionPane.showMessageDialog(null, "Está habitación está disponible, porfavor escoger una ocupada ");
        } else{
            JOptionPane.showMessageDialog(null,"Ya cargo una habitación");
        }

    }

    public void onClickedCargar(MouseEvent event){
        if(aceptar==false){
            DAO_Cuenta.actualizarPrecio(Total,reserva);
            REMuestra.appendText( "----------------------------\n");
            REMuestra.appendText( "Total:" + Total);
        }
        else{
            JOptionPane.showMessageDialog(null, "Ingrese algun producto");
        }

    }


    public void onClickedLimpiar(MouseEvent event){
            REMuestra.clear();
            Total=0;
            cond=true;
    }



}



