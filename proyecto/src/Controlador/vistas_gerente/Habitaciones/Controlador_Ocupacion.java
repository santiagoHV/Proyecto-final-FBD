package Controlador.vistas_gerente.Habitaciones;

import DatosSQL.DAOs.DAO_Ocupacion_Registro;
import Modelo.entidades.Ocupacion_registro;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class Controlador_Ocupacion implements Initializable {
    public TableView<Ocupacion_registro> tabla_historial;
    public TableColumn titular_column;
    public TableColumn fechai_column;
    public TableColumn fechaf_column;
    public TableColumn idres_column;
    public TableColumn estado_column;
    public ObservableList<Ocupacion_registro> OBS;
    public ProgressIndicator progresi;
    public Label label_habitacion;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        prepararTabla();
        loadHistorial();
    }

    public void loadHistorial(){
        Task tareita = new Task() {
            @Override
            protected Object call() throws Exception {
                OBS = FXCollections.observableArrayList(new DAO_Ocupacion_Registro().getHistoricoOcupacion(label_habitacion.getText()));
                return null;
            }
        };
        tareita.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent workerStateEvent) {
                tabla_historial.setItems(OBS);
                progresi.setVisible(false);
            }
        });
        Thread elhilito = new Thread(tareita);
        elhilito.start();
    }

    public void prepararTabla(){
        titular_column.setCellValueFactory(new PropertyValueFactory("titular"));
        fechai_column.setCellValueFactory(new PropertyValueFactory("fechai"));
        fechaf_column.setCellValueFactory(new PropertyValueFactory("fechaf"));
        idres_column.setCellValueFactory(new PropertyValueFactory("id_reserva"));
        estado_column.setCellValueFactory(new PropertyValueFactory("estado"));
    }
}
