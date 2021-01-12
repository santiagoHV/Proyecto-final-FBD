package Controlador;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class Controlador_navbar implements Initializable {
    public AnchorPane router;

    public Button reserve_button;
    public Button checkin_button;
    public Button checkout_button;
    public Button search_button;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void navigate(ActionEvent actionEvent) {
    }
}
