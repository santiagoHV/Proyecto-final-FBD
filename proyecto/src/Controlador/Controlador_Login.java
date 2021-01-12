package Controlador;

import Vista.Main;
import animatefx.animation.Shake;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Controlador_Login implements Initializable
{
    public AnchorPane BGStackPane;
    public JFXButton BIniSes;
    public Label JLabel_TUsuario;
    public JFXTextField TUsuario;
    public JFXPasswordField TContrasena;
    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        //Invocación del método para volver el stage arrastrable:
        this.makeStageDragable();
    }

    //Método para arrastrar la ventana
    private void makeStageDragable()
    {
        //obtiene posicion de click
        BGStackPane.setOnMousePressed((mouseEvent ->
        {
            xOffset = mouseEvent.getSceneX();
            yOffset = mouseEvent.getSceneY();
        }));

        BGStackPane.setOnMouseDragged((mouseEvent ->
        {
            Main.stage.setX(mouseEvent.getScreenX()-xOffset);
            Main.stage.setY(mouseEvent.getScreenY()-yOffset);

            Main.stage.setOpacity(0.8);
        }));

        BGStackPane.setOnDragDone((dragEvent ->
        {
            Main.stage.setOpacity(1);
        }));

        BGStackPane.setOnMouseReleased(mouseEvent ->
        {
            Main.stage.setOpacity(1);
        });
    }

    public void Click(ActionEvent actionEvent) throws IOException, SQLException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("../Vista/recepcionista/navbar_recepcionista.fxml"));
        Pane ventana = (Pane) loader.load();

        //Show the scene containing the root layout.

        Scene scene = new Scene(ventana);
        scene.setFill(Color.TRANSPARENT);
        stage.setTitle("Menu Principal");
        stage.setScene(scene);
        //Undecorated y No Resizable:
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initStyle(StageStyle.TRANSPARENT);

        //Mostrar Stage:
        stage.show();
    }
}
