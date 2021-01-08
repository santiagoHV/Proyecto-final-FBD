package Controlador;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;

import javax.lang.model.element.Element;
import java.io.IOException;
import java.net.ConnectException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controlador_home_recepcionista implements Initializable {

    public Button reserve_button;
    public Button checkin_button;
    public TabPane TabPanePisos;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        ObservableList<Tab> Tabs = TabPanePisos.getTabs();

        for (Tab e: Tabs)
        {
            AnchorPane Contenedor = (AnchorPane) e.getContent();
            ObservableList<Node> NodosAnchorTab = Contenedor.getChildren();

            for (Node n: NodosAnchorTab)
            {
                Button BotonConvertido = (Button) n;

                BotonConvertido.getStyleClass().add("map-green");

                BotonConvertido.setOnAction(actionEvent ->
                {
                    try
                    {
                        EvSelecHabi(actionEvent);
                    } catch (IOException ioException)
                    {
                        ioException.printStackTrace();
                    }
                });
            }
        }
    }

    public void navigave(ActionEvent actionEvent)
    {

        if(actionEvent.getSource().equals(reserve_button)){
            System.out.println("soy un boton de reserva");
        }else if(actionEvent.getSource().equals(checkin_button)){
            System.out.println("soy un boton de checkin");
        }
    }

    public void EvSelecHabi(ActionEvent actionEvent) throws IOException
    {
        Node Boton = (Node) actionEvent.getSource();
        String [] Partes = Boton.getStyleClass().toString().split(" ");
        System.out.println(Partes[2]);

        if(Boton.getStyleClass().get(2).equals("map-green"))
        {
            Boton.getStyleClass().set(2, "map-red");
        }
        else
        {
            Boton.getStyleClass().set(2, "map-green");
        }

        System.out.println(Boton.getStyleClass());
    }
}
