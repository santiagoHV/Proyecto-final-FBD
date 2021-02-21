package Controlador.vistas_recepcionista;

import Modelo.entidades.Huesped;
import Modelo.entidades.Registro;
import Modelo.entidades.Reserva;
import Modelo.fabricas.HuespedList;
import Modelo.fabricas.ReservasList;
import Vista.Main;
import animatefx.animation.BounceIn;
import animatefx.animation.BounceOut;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXScrollPane;
import io.github.palexdev.materialfx.controls.MFXProgressSpinner;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Controlador_search implements Initializable {
    public JFXButton btn_buscar;
    public TextField txt_cod_reserva;
    public TextField txt_nom_apel;
    public TextField txt_num_doc;
    public GridPane Grid_Reservas;
    public ScrollPane panel_reservas_halladas;
    public StackPane StackBG;
    public MFXProgressSpinner proSpinner;
    public ScrollPane panel_clientes_hallados;
    public GridPane Grid_Clientes;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        JFXScrollPane Prueba = new JFXScrollPane();
        Prueba.smoothScrolling(panel_reservas_halladas);
        Prueba.smoothScrolling(panel_clientes_hallados);
    }

    public void Consultar_Reservas_Clientes(ActionEvent actionEvent) throws InterruptedException, IOException {
        new BounceIn(proSpinner).play();
        proSpinner.setVisible(true);

        BuscarReservas();
        BuscarHuespedes();

        new BounceOut(proSpinner).play();
    }

    public void BuscarReservas() throws InterruptedException {

        Grid_Reservas.getChildren().clear();

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

        Task<List<Reserva>> taskConsultListReserv = new Task<List<Reserva>>() {
            @Override
            protected List<Reserva> call() throws Exception {

                return reservasList.BuscarReservas(CodFinal, NumDocFinal, txt_nom_apel.getText());
            }
        };

        taskConsultListReserv.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent workerStateEvent) {

                Task<Void> sleeper = new Task<Void>()
                {
                    @Override
                    protected Void call() throws Exception {
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                        }
                        return null;
                    }
                };

                sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                    @Override
                    public void handle(WorkerStateEvent workerStateEvent) {
                        proSpinner.setVisible(false);
                    }
                });

                int column = 0;
                int row = 0;

                try
                {
                    List<Reserva> reservas = taskConsultListReserv.getValue();
                    for(int i=0; i< reservas.size();i++) {
                        //Carga de las plantillas para los paneles con información de los huespedes
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(Main.class.getResource("../Vista/recepcionista/Panel_Reserva.fxml"));

                        //Definición de los anchorpane:
                        //Para el panel de los huespedes:
                        AnchorPane PanelReservas = loader.load();

                        Controlador_Card_Reserva controlador_card_reserva = loader.getController();
                        controlador_card_reserva.setValoresCamposCardReserva(reservas.get(i));

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

        Thread hiloReserva = new Thread(taskConsultListReserv);
        hiloReserva.start();
    }

    public void BuscarHuespedes()
    {
        Grid_Clientes.getChildren().clear();

        HuespedList huespedList = new HuespedList();

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

        Task<List<Huesped>> taskConsultListHuesped = new Task<List<Huesped>>() {
            @Override
            protected List<Huesped> call() throws Exception {

                return huespedList.BuscarHuespedes(CodFinal, NumDocFinal, txt_nom_apel.getText());
            }
        };

        taskConsultListHuesped.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent workerStateEvent) {

                Task<Void> sleeper = new Task<Void>()
                {
                    @Override
                    protected Void call() throws Exception {
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                        }
                        return null;
                    }
                };

                sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                    @Override
                    public void handle(WorkerStateEvent workerStateEvent) {
                        proSpinner.setVisible(false);
                    }
                });

                int column = 0;
                int row = 0;

                try
                {
                    List<Huesped> Huespedes = taskConsultListHuesped.getValue();
                    for(int i=0; i< Huespedes.size();i++) {
                        //Carga de las plantillas para los paneles con información de los huespedes
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(Main.class.getResource("../Vista/recepcionista/Panel_Huesped.fxml"));

                        //Definición de los anchorpane:
                        //Para el panel de los huespedes:
                        AnchorPane PanelHuesped = loader.load();

                        Controlador_Huesped controlador_huesped = loader.getController();
                        controlador_huesped.setValoresPanel(Huespedes.get(i), null);

                        controlador_huesped.btn_ingreso.setText("Actualizar");
                        controlador_huesped.btn_ingreso.setDisable(false);

                        row++;
                        Grid_Clientes.add(PanelHuesped,column,row);
                        GridPane.setMargin(PanelHuesped,new Insets(10));

                        //Alto y Ancho:

                        //Ancho:
                        Grid_Clientes.setMaxWidth(Region.USE_COMPUTED_SIZE);
                        Grid_Clientes.setPrefWidth(Region.USE_COMPUTED_SIZE);
                        Grid_Clientes.setMinWidth(Region.USE_COMPUTED_SIZE);

                        //Alto:
                        Grid_Clientes.setMaxHeight(Region.USE_COMPUTED_SIZE);
                        Grid_Clientes.setPrefHeight(Region.USE_COMPUTED_SIZE);
                        Grid_Clientes.setMinHeight(Region.USE_COMPUTED_SIZE);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread hiloHuesped = new Thread(taskConsultListHuesped);
        hiloHuesped.start();
    }
}
