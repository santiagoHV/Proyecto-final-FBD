package Controlador.vistas_gerente;

import Vista.Main;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controlador_navbar implements Initializable {
    public Button button_home;
    public Button button_users;
    public Button button_rooms;
    public Button button_close;
    public AnchorPane router;
    public StackPane SP_navbar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setRouter("../Vista/gerente/home_gerente.fxml");
    }

    public void navigate(ActionEvent actionEvent) {

        if (actionEvent.getSource() == button_home) {
            setRouter("../Vista/gerente/home_gerente.fxml");
        } else if (actionEvent.getSource() == button_users) {
            setRouter("../Vista/gerente/tabla_usuarios.fxml");
        } else if (actionEvent.getSource() == button_rooms) {
            setRouter("../Vista/gerente/habitaciones_disp.fxml");
        }
    }

    public void out(ActionEvent actionEvent) {
        //Cierre de sesión temporal hasta que se implementen usuarios en el sistema con la base de datos:

        //Usando JFXDialog:
        JFXDialogLayout EstrucMensaje = new JFXDialogLayout();
        EstrucMensaje.setBody(new Text("¿Está seguro de que quiere cerrar sesión?"));
        JFXButton BotonAceptar = new JFXButton("Sí, me quiero ir");
        JFXButton BotonCancelar = new JFXButton("No, me pagan horas extra");

        JFXDialog DialogoCerrar = new JFXDialog(SP_navbar, EstrucMensaje, JFXDialog.DialogTransition.BOTTOM);

        BotonCancelar.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent) ->
        {
            DialogoCerrar.close();
        });

        BotonAceptar.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent) ->
        {
            Stage stage = (Stage) BotonAceptar.getScene().getWindow();
            stage.close();

            Stage stageMenuInicio = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("../Vista/ingreso/MenuInicio.fxml"));
            Pane ventana = null;

            //Try Catch creado por Intellij
            try {
                ventana = (Pane) loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //Show the scene containing the root layout.

            Scene scene = new Scene(ventana);
            scene.setFill(Color.TRANSPARENT);
            stageMenuInicio.setTitle("Menu Principal");
            stageMenuInicio.setScene(scene);
            //Undecorated y No Resizable:
            stageMenuInicio.setResizable(false);
            stageMenuInicio.initStyle(StageStyle.UNDECORATED);
            stageMenuInicio.initStyle(StageStyle.TRANSPARENT);

            //Mostrar Stage:
            stageMenuInicio.show();

            DialogoCerrar.close();
        });

        EstrucMensaje.setActions(BotonCancelar, BotonAceptar);
        DialogoCerrar.show();
    }

    public void setRouter(String url) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource(url));
        Node Contenido = null;
        try {
            Contenido = (Node) loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        router.getChildren().setAll(Contenido);
    }
}
