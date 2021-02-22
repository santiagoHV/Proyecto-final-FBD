package Controlador.vistas_empleado;

import DatosSQL.DAOs.DAO_PyS;
import Modelo.entidades.PyS;
import com.jfoenix.controls.*;
import com.jfoenix.controls.events.JFXDialogEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLOutput;
import java.util.ResourceBundle;

import static java.lang.Integer.*;

public class Controlador_piscina implements Initializable {


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
    private int Total=0;
    private String categoria="Restaurante";


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
        System.out.println(OBS);

        ID.setCellValueFactory(new PropertyValueFactory("k_codigo_pys"));
        Nombre.setCellValueFactory(new PropertyValueFactory("unidad"));
        Stock.setCellValueFactory(new PropertyValueFactory("stock"));
        Precio.setCellValueFactory(new PropertyValueFactory("precio_producta"));
        productos.setItems(OBS);


    }

    //Aquí se determinan las acciones que va a realiza un boton al hacer click sobre el

    public void cargar(ActionEvent actionEvent){

        REMuestra.appendText( "Producto: " + String.valueOf(productos.getSelectionModel().getSelectedItem().getUnidad() + "\n"));
        REMuestra.appendText("Precio de venta:" + String.valueOf(productos.getSelectionModel().getSelectedItem().getPrecio_producta() + "\n"));
        REMuestra.appendText("Cantidad: "+TCantidad.getText()+"\n");
        int Cantidad= Integer.parseInt(TCantidad.getText());
        int precio = (int) productos.getSelectionModel().getSelectedItem().getPrecio_producta();
        Total += Cantidad*precio;
    }
    public void onclickhabitacion(ActionEvent actionEvent){
        REMuestra.appendText( "Habitación No: " +  RDPiso.getValue() + RDHabitacion.getValue() + "\n");
    }

    public void onClickedCargar(MouseEvent event){
        REMuestra.appendText( "----------------------------\n");
        REMuestra.appendText( "Total:" + Total);
    }


    public void onClickedLimpiar(MouseEvent event){
            REMuestra.clear();
    }



}



