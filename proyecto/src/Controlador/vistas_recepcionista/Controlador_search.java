package Controlador.vistas_recepcionista;

import Modelo.entidades.Huesped;
import Modelo.entidades.Reserva;
import Vista.Main;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

import javax.naming.spi.ResolveResult;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void Consultar_Reservas_Clientes(ActionEvent actionEvent) {
        BuscarReservas();
    }



    public void BuscarReservas(){

        Grid_Reservas.getChildren().clear();

        Reserva reserva = new Reserva();

        int CodReserva = 0;
        int NumDoc = 0;

        if(!txt_cod_reserva.getText().equals("")) {

            CodReserva = Integer.parseInt(txt_cod_reserva.getText());
        }

        if(!txt_num_doc.getText().equals("")){
            NumDoc = Integer.parseInt(txt_num_doc.getText());
        }

        List<Reserva> reservas = reserva.BuscarReservas(CodReserva, NumDoc, txt_nom_apel.getText());

        int column = 0;
        int row = 0;

        try
        {
            for(int i=0; i<reservas.size();i++) {
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


}
