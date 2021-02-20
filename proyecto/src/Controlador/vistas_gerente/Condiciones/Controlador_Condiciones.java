package Controlador.vistas_gerente.Condiciones;

import DatosSQL.DAOs.DAO_CondicionHotel;
import DatosSQL.DAOs.DAO_Pago;
import Modelo.entidades.Condicion_Hotel;
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
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controlador_Condiciones implements Initializable {

    public TableView<Condicion_Hotel> tabla_condiciones;
    public TableColumn col_id;
    public TableColumn col_descuento;
    public TableColumn col_aforo;
    public TableColumn col_dias;
    public TableColumn col_estado;
    public TableColumn col_descripcion;
    public ObservableList<Condicion_Hotel> OBS;
    public ProgressIndicator progresi;
    public StackPane stackpane1;
    public AnchorPane AnchorMP;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        progresi.setVisible(false);
        col_aforo.setCellValueFactory(new PropertyValueFactory("aforo"));
        col_descripcion.setCellValueFactory(new PropertyValueFactory("descripcion"));
        col_dias.setCellValueFactory(new PropertyValueFactory("numero_dias"));
        col_estado.setCellValueFactory(new PropertyValueFactory("estado_condicion"));
        col_id.setCellValueFactory(new PropertyValueFactory("k_condicion"));
        col_descuento.setCellValueFactory(new PropertyValueFactory("descuento"));
        loadCondiciones();
    }

    public void loadCondiciones(){
        progresi.setVisible(true);
        Task tareita = new Task() {
            @Override
            protected Object call() throws Exception {
                ArrayList<Condicion_Hotel> cond_array = new DAO_CondicionHotel().getCondiciones();
                for(Condicion_Hotel cond : cond_array){
                    cond.setAforo(cond.getAforo()*100);
                    cond.setDescuento(cond.getDescuento()*100);
                }
                OBS = FXCollections.observableArrayList(cond_array);
                return null;
            }
        };
        tareita.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent workerStateEvent) {
                tabla_condiciones.setItems(OBS);
                progresi.setVisible(false);
            }
        });
        Thread elhilito = new Thread(tareita);
        elhilito.start();
    }

    public void activarSeleccionado(ActionEvent actionEvent){
        if(tabla_condiciones.getSelectionModel().getSelectedItem() == null){
            JOptionPane.showMessageDialog(null, "Seleccione una condicion");
        }else{
            if(new DAO_CondicionHotel().activarCond(tabla_condiciones.getSelectionModel().getSelectedItem().getK_condicion())){
                loadCondiciones();
            }else{
                JOptionPane.showMessageDialog(null, "Error al cambiar estado");
            }
        }
    }

    public void openAddCondiciones() throws IOException {
        BoxBlur blur = new BoxBlur(3, 3, 3);
        Parent parent = FXMLLoader.load(getClass().getResource("../../../Vista/gerente/add_condicion.fxml"));
        JFXDialog dialog = new JFXDialog(stackpane1, (Region) parent, JFXDialog.DialogTransition.BOTTOM, true);
        AnchorPane AP = (AnchorPane) parent.getChildrenUnmodifiable().get(0);
        HBox HB = (HBox) AP.getChildren().get(1);
        JFXButton BSalirDialog = (JFXButton) HB.getChildrenUnmodifiable().get(0);
        BSalirDialog.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent) ->
        {
            dialog.close();
            loadCondiciones();
        });
        dialog.setOnDialogClosed((JFXDialogEvent event) ->
        {
            AnchorMP.setEffect(null);
        });
        AnchorMP.setEffect(blur);
        dialog.show();
    }
}
