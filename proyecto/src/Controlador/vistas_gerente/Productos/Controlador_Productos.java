package Controlador.vistas_gerente.Productos;

import DatosSQL.DAOs.DAO_PyS;
import Modelo.entidades.PyS;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.events.JFXDialogEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controlador_Productos implements Initializable {
    public TableColumn col_id;
    public TableColumn col_nombre;
    public TableColumn col_stock;
    public TableColumn col_precio;
    public TableView<PyS> tabla_productos;
    public Button gimnasio_button;
    public Button cafeteria_button;
    public Button spa_button;
    public Button piscina_button;
    public Button restaurante_button;
    public ProgressIndicator progresi;
    public ObservableList<PyS> OBS;
    public StackPane stackPane1;
    public AnchorPane AnchorMP;
    public String actual_categoria;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        progresi.setVisible(false);
        loadProductos("Restaurante");
        actual_categoria="Restaurante";
    }

    public ObservableList<PyS> getProductosByCategoria(String categoria){
        ObservableList<PyS> obs = FXCollections.observableArrayList();
        for(PyS p: new DAO_PyS().consultarPorCategoria(categoria)){
            obs.add(p);
        }
        return obs;
    }

    public void loadProductos(String categoria){
        progresi.setVisible(true);
        Task<Void> tareita = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                OBS= getProductosByCategoria(categoria);
                return null;
            }
        };
        tareita.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent workerStateEvent) {
                col_id.setCellValueFactory(new PropertyValueFactory("k_codigo_pys"));
                col_nombre.setCellValueFactory(new PropertyValueFactory("unidad"));
                col_stock.setCellValueFactory(new PropertyValueFactory("stock"));
                col_precio.setCellValueFactory(new PropertyValueFactory("precio_producta"));
                tabla_productos.setItems(OBS);
                progresi.setVisible(false);
            }
        });
        Thread x = new Thread(tareita);
        x.start();
    }

    public void loadCategoria(ActionEvent actionEvent) {
        if(actionEvent.getSource() == gimnasio_button){
            loadProductos("Gimnasio");
            actual_categoria = "Gimnasio";
        } else if(actionEvent.getSource() == restaurante_button){
            loadProductos("Restaurante");
            actual_categoria = "Restaurante";
        } else if(actionEvent.getSource() == spa_button){
            loadProductos("Spa");
            actual_categoria = "Spa";
        } else if(actionEvent.getSource() == piscina_button){
            loadProductos("Piscina");
            actual_categoria = "Piscina";
        } else if(actionEvent.getSource() == cafeteria_button){
            loadProductos("Cafeteria");
            actual_categoria = "Cafeteria";
        }
    }

    public void loadDialog(ActionEvent actionEvent) throws IOException {
        if(tabla_productos.getSelectionModel().getSelectedItem() != null) {
            String Boton = actionEvent.getSource().toString();
            String[] Partes = Boton.split("'");
            BoxBlur blur = new BoxBlur(3, 3, 3);
            Parent parent = FXMLLoader.load(getClass().getResource("../../../Vista/gerente/productos_dialog.fxml"));
            JFXDialog dialog = new JFXDialog(stackPane1, (Region) parent, JFXDialog.DialogTransition.BOTTOM, true);
            AnchorPane AP = (AnchorPane) parent.getChildrenUnmodifiable().get(0);
            HBox HB = (HBox) AP.getChildren().get(0);
            ((Label) AP.getChildren().get(2)).setText(Partes[1]);
            ((Label) AP.getChildren().get(3)).setText(tabla_productos.getSelectionModel().getSelectedItem().getK_codigo_pys() + "");
            JFXButton BSalirDialog = (JFXButton) HB.getChildrenUnmodifiable().get(0);
            JFXButton ok_button = (JFXButton) HB.getChildrenUnmodifiable().get(1);
            BSalirDialog.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent) ->
            {
                dialog.close();
            });
            ok_button.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent) ->
            {
                dialog.close();
                loadProductos(actual_categoria);
            });
            dialog.setOnDialogClosed((JFXDialogEvent event) ->
            {
                AnchorMP.setEffect(null);
            });
            AnchorMP.setEffect(blur);
            dialog.show();
        }else{
            JOptionPane.showMessageDialog(null, "Seleccione el producto que desea modificar");
        }
    }
    public void addProducto(ActionEvent actionEvent) throws IOException {
        BoxBlur blur = new BoxBlur(3, 3, 3);
        Parent parent = FXMLLoader.load(getClass().getResource("../../../Vista/gerente/add_producto.fxml"));
        JFXDialog dialog = new JFXDialog(stackPane1, (Region) parent, JFXDialog.DialogTransition.BOTTOM, true);
        AnchorPane AP = (AnchorPane) parent.getChildrenUnmodifiable().get(0);
        HBox HB = (HBox) AP.getChildren().get(1);
        JFXButton BSalirDialog = (JFXButton) HB.getChildrenUnmodifiable().get(0);
        BSalirDialog.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent) ->
        {
            dialog.close();
            loadProductos(actual_categoria);
        });
        dialog.setOnDialogClosed((JFXDialogEvent event) ->
        {
            AnchorMP.setEffect(null);
        });
        AnchorMP.setEffect(blur);
        dialog.show();
    }
}
