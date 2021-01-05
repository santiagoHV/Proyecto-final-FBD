package Controlador;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class Controlador_home_recepcionista implements Initializable {

    public Button reserve_button;
    public Button checkin_button;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void navigave(ActionEvent actionEvent) {

        if(actionEvent.getSource().equals(reserve_button)){
            System.out.println("soy un boton de reserva");
        }else if(actionEvent.getSource().equals(checkin_button)){
            System.out.println("soy un boton de checkin");
        }
    }
}
