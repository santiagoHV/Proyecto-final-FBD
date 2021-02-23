package Controlador;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class Alerta {

    public Alerta(String titulo, String mensaje, StackPane stackPane){
        FXMLLoader alertaDireccion = new FXMLLoader(getClass().getResource("../Vista/recepcionista/alerta.fxml"));

        Parent contenedor = null;
        try {
            contenedor = alertaDireccion.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JFXDialog dialogAlerta = new JFXDialog(stackPane, (Region) contenedor, JFXDialog.DialogTransition.BOTTOM, true);

        Controlador_alerta controlador_alerta = alertaDireccion.getController();

        AnchorPane alertaAP = (AnchorPane) contenedor.getChildrenUnmodifiable().get(0);
        JFXButton btn_aceptar = (JFXButton) alertaAP.getChildren().get(2);
        btn_aceptar.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEventAceptar)->
        {
            dialogAlerta.close();
        });

        controlador_alerta.mensaje.setText(mensaje);
        controlador_alerta.titulo.setText(titulo);


        dialogAlerta.show();

    }

}
