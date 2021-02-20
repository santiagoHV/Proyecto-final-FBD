package Controlador.vistas_gerente.Condiciones;

import DatosSQL.DAOs.DAO_CondicionHotel;
import Modelo.entidades.Condicion_Hotel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        col_aforo.setCellValueFactory(new PropertyValueFactory("aforo"));
        col_descuento.setCellValueFactory(new PropertyValueFactory("descuento"));
        col_dias.setCellValueFactory(new PropertyValueFactory("numero_dias"));
        col_estado.setCellValueFactory(new PropertyValueFactory("estado_condicion"));
        col_id.setCellValueFactory(new PropertyValueFactory("k_condicion"));
        col_descuento.setCellValueFactory(new PropertyValueFactory("descripcion"));
        loadCondiciones();
    }

    public void loadCondiciones(){
        ArrayList<Condicion_Hotel> cond_array = new DAO_CondicionHotel().getCondiciones();
        ObservableList<Condicion_Hotel> obs = FXCollections.observableArrayList(cond_array);
        tabla_condiciones.setItems(obs);
    }
}
