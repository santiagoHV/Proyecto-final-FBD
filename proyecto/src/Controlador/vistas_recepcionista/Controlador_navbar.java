package Controlador.vistas_recepcionista;

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
    public AnchorPane router;

    public Button reserve_button;
    public Button checkin_button;
    public Button checkout_button;
    public Button search_button;
    public Button out_button;
    public StackPane SP_Recepcionista;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AbrirMenu("../Vista/recepcionista/reserva.fxml");
    }

    public void navigate(ActionEvent actionEvent) {

        if(actionEvent.getSource() == reserve_button)
        {
            AbrirMenu("../Vista/recepcionista/reserva.fxml");
        } else if (actionEvent.getSource() == checkin_button) {
            AbrirMenu("../Vista/recepcionista/checkin.fxml");
        } else if (actionEvent.getSource() == checkout_button) {
            AbrirMenu("../Vista/recepcionista/checkout.fxml");
        } else if (actionEvent.getSource()==search_button) {
            AbrirMenu("../Vista/recepcionista/search.fxml");
        } else if(actionEvent.getSource()==out_button) {
            //Cierre de sesión temporal hasta que se implementen usuarios en el sistema con la base de datos:

            //Usando JFXDialog:
            JFXDialogLayout EstrucMensaje = new JFXDialogLayout();
            EstrucMensaje.setBody(new Text("¿Está seguro de que quiere cerrar sesión?"));
            JFXButton BotonAceptar = new JFXButton("Sí, me quiero ir");
            JFXButton BotonCancelar = new JFXButton("No, me pagan horas extra");

            JFXDialog DialogoCerrar = new JFXDialog(SP_Recepcionista,EstrucMensaje, JFXDialog.DialogTransition.BOTTOM);

            BotonCancelar.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent)->
            {
                DialogoCerrar.close();
            });

            BotonAceptar.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent)->
            {
                Stage stage = (Stage) BotonAceptar.getScene().getWindow();
                stage.close();

                Stage stageMenuInicio = new Stage();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(Main.class.getResource("../Vista/MenuInicio.fxml"));
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
    }

    public void AbrirMenu(String Ruta)
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource(Ruta));
        try
        {
            Node Contenido = (Node) loader.load();
            router.getChildren().setAll(Contenido);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
