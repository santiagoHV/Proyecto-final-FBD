package Controlador;

import Vista.Main;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.lang.model.element.Element;
import java.io.IOException;
import java.net.ConnectException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controlador_home_recepcionista implements Initializable {

    //Botones de navegacion
    public Button reserve_button;
    public Button checkin_button;
    public Button checkout_button;
    public Button search_button;

    public TabPane TabPanePisos;
    public AnchorPane content;

    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        reserve_button.setDisable(true);

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

    public void navigave(ActionEvent actionEvent) throws IOException {

            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();

            if(actionEvent.getSource().equals(checkin_button)){
                loader.setLocation(Main.class.getResource("../Vista/recepcionista/checkin.fxml"));
            }else if(actionEvent.getSource().equals(checkout_button)){
                loader.setLocation(Main.class.getResource("../Vista/recepcionista/checkout.fxml"));
            }else if(actionEvent.getSource().equals(search_button)){
                loader.setLocation(Main.class.getResource("../Vista/recepcionista/search.fxml"));
            }


            Pane ventana = (Pane) loader.load();

            Scene scene = new Scene(ventana);
            scene.setFill(Color.TRANSPARENT);
            stage.setTitle("checkin");
            stage.setScene(scene);
            //Undecorated y No Resizable:
            stage.setResizable(false);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.initStyle(StageStyle.TRANSPARENT);

            //Mostrar Stage:
            stage.show();


    }

    public void EvSelecHabi(ActionEvent actionEvent) throws IOException {

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

        //System.out.println(Boton.getStyleClass());
    }

    private void makeStageDragable()
    {
        //obtiene posicion de click
        content.setOnMousePressed((mouseEvent ->
        {
            xOffset = mouseEvent.getSceneX();
            yOffset = mouseEvent.getSceneY();
        }));

        content.setOnMouseDragged((mouseEvent ->
        {
            Main.stage.setX(mouseEvent.getScreenX()-xOffset);
            Main.stage.setY(mouseEvent.getScreenY()-yOffset);

            Main.stage.setOpacity(0.8);
        }));

        content.setOnDragDone((dragEvent ->
        {
            Main.stage.setOpacity(1);
        }));

        content.setOnMouseReleased(mouseEvent ->
        {
            Main.stage.setOpacity(1);
        });
    }


}
