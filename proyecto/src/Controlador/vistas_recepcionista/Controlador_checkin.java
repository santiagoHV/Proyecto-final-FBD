package Controlador.vistas_recepcionista;

import Modelo.Huesped;
import Vista.Main;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controlador_checkin implements Initializable {

    public GridPane GridPanel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        List<Huesped> Huespedes = ConsultarHuespedes();

        int column = 0;
        int row = 0;
        try
        {
            for(int i=0; i<6;i++) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(Main.class.getResource("../Vista/recepcionista/Panel_Huesped.fxml"));

                AnchorPane Panel = loader.load();

                Controlador_Huesped controlador_huesped = loader.getController();
                controlador_huesped.setValoresPanel(Huespedes.get(i));

                row++;
                GridPanel.add(Panel,column,row);
                GridPane.setMargin(Panel,new Insets(8));

                //Alto y Ancho:

                //Ancho:
                GridPanel.setMaxWidth(Region.USE_COMPUTED_SIZE);
                GridPanel.setPrefWidth(Region.USE_COMPUTED_SIZE);
                GridPanel.setMinWidth(Region.USE_COMPUTED_SIZE);

                //Alto:
                GridPanel.setMaxHeight(Region.USE_COMPUTED_SIZE);
                GridPanel.setPrefHeight(Region.USE_COMPUTED_SIZE);
                GridPanel.setMinHeight(Region.USE_COMPUTED_SIZE);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Esto es temporal

    private List<Huesped> ConsultarHuespedes()
    {
        List<Huesped> Huespedes = new ArrayList<>();
        Huesped huesped = new Huesped();

        //Ciclo Temporal para llenar la interfaz mientras se conecta con la base de datos:
        for(int i=0; i<6;i++)
        {
            huesped.setK_identificacion("10000000");
            huesped.setN_direccion("Cualquier Calle");
            huesped.setF_nacimiento("20");
            huesped.setN_nombre("Juanito");
            huesped.setN_apellido("Vainas");
            huesped.setN_telefono("123");

            Huespedes.add(huesped);
        }

        return Huespedes;
    }
}
