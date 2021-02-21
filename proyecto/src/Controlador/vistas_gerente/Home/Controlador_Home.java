package Controlador.vistas_gerente.Home;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.events.JFXDialogEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controlador_Home implements Initializable {
    public StackPane stackpane1;
    public AnchorPane AnchorMP;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void openIngresosHotelDialog() throws IOException {
        BoxBlur blur = new BoxBlur(3, 3, 3);
        Parent parent = FXMLLoader.load(getClass().getResource("../../../Vista/gerente/ingresos_hotel.fxml"));
        JFXDialog dialog = new JFXDialog(stackpane1, (Region) parent, JFXDialog.DialogTransition.BOTTOM, true);
        AnchorPane AP = (AnchorPane) parent.getChildrenUnmodifiable().get(0);
        VBox vbox = (VBox) AP.getChildren().get(1);
        JFXButton BSalirDialog = (JFXButton) vbox.getChildrenUnmodifiable().get(5);
        BSalirDialog.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent) ->
        {
            dialog.close();
        });
        dialog.setOnDialogClosed((JFXDialogEvent event) ->
        {
            AnchorMP.setEffect(null);
        });
        AnchorMP.setEffect(blur);
        dialog.show();
    }
    public void openIngresosHabitacionDialog() throws IOException {
        BoxBlur blur = new BoxBlur(3, 3, 3);
        Parent parent = FXMLLoader.load(getClass().getResource("../../../Vista/gerente/ingresos_habitacion.fxml"));
        JFXDialog dialog = new JFXDialog(stackpane1, (Region) parent, JFXDialog.DialogTransition.BOTTOM, true);
        AnchorPane AP = (AnchorPane) parent.getChildrenUnmodifiable().get(0);
        VBox vbox = (VBox) AP.getChildren().get(1);
        JFXButton BSalirDialog = (JFXButton) vbox.getChildrenUnmodifiable().get(5);
        BSalirDialog.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent) ->
        {
            dialog.close();
        });
        dialog.setOnDialogClosed((JFXDialogEvent event) ->
        {
            AnchorMP.setEffect(null);
        });
        AnchorMP.setEffect(blur);
        dialog.show();
    }
}
