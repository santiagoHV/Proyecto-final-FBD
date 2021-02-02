package Controlador.vistas_recepcionista;

import Modelo.entidades.Huesped;
import Modelo.entidades.Reserva;
import Modelo.fabricas.ReservasList;
import Vista.Main;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXSpinner;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

import javax.naming.spi.ResolveResult;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.*;

public class Controlador_search implements Initializable {
    public JFXButton btn_buscar;
    public TextField txt_cod_reserva;
    public TextField txt_nom_apel;
    public TextField txt_num_doc;
    public GridPane Grid_Reservas;
    public ScrollPane panel_reservas_halladas;
    public StackPane StackBG;
    public ProgressIndicator proSpinner;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void Consultar_Reservas_Clientes(ActionEvent actionEvent) throws InterruptedException, IOException {
        BuscarReservas();
    }

    public void BuscarReservas() throws InterruptedException {

        Grid_Reservas.getChildren().clear();

        proSpinner.setVisible(true);

        ReservasList reservasList = new ReservasList();

        int CodReserva = 0;
        int NumDoc = 0;

        if(!txt_cod_reserva.getText().equals("")) {

            CodReserva = Integer.parseInt(txt_cod_reserva.getText());
        }

        if(!txt_num_doc.getText().equals("")){
            NumDoc = Integer.parseInt(txt_num_doc.getText());
        }

        final int CodFinal = CodReserva;
        final int NumDocFinal = NumDoc;

        Task<Void> tarea = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                reservasList.BuscarReservas(CodFinal, NumDocFinal, txt_nom_apel.getText());
                return null;
            }
        };

        tarea.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent workerStateEvent) {
                proSpinner.setVisible(false);

                int column = 0;
                int row = 0;

                try
                {
                    for(int i=0; i< reservasList.PruebaRun.size();i++) {
                        //Carga de las plantillas para los paneles con información de los huespedes
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(Main.class.getResource("../Vista/recepcionista/Panel_Reserva.fxml"));

                        //Definición de los anchorpane:
                        //Para el panel de los huespedes:
                        AnchorPane PanelReservas = loader.load();

                        Controlador_Card_Reserva controlador_card_reserva = loader.getController();
                        controlador_card_reserva.setValoresCamposCardReserva(reservasList.PruebaRun.get(i));

                        row++;
                        Grid_Reservas.add(PanelReservas,column,row);
                        GridPane.setMargin(PanelReservas,new Insets(10));

                        //Alto y Ancho:

                        //Ancho:
                        Grid_Reservas.setMaxWidth(Region.USE_COMPUTED_SIZE);
                        Grid_Reservas.setPrefWidth(Region.USE_COMPUTED_SIZE);
                        Grid_Reservas.setMinWidth(Region.USE_COMPUTED_SIZE);

                        //Alto:
                        Grid_Reservas.setMaxHeight(Region.USE_COMPUTED_SIZE);
                        Grid_Reservas.setPrefHeight(Region.USE_COMPUTED_SIZE);
                        Grid_Reservas.setMinHeight(Region.USE_COMPUTED_SIZE);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread hola = new Thread(tarea);
        hola.start();

        //List<Reserva> reservas = reservasList.BuscarReservas(CodReserva, NumDoc, txt_nom_apel.getText());
    }
}
