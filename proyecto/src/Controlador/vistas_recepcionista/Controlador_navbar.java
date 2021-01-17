package Controlador.vistas_recepcionista;

import Vista.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
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
        AbrirMenu("../Vista/recepcionista/Home_recepcionista.fxml");
    }

    public void navigate(ActionEvent actionEvent) {

        if(actionEvent.getSource() == reserve_button)
        {
            AbrirMenu("../Vista/recepcionista/Home_recepcionista.fxml");
        } else if (actionEvent.getSource() == checkin_button) {
            AbrirMenu("../Vista/recepcionista/checkin.fxml");
        } else if (actionEvent.getSource() == checkout_button) {
            AbrirMenu("../Vista/recepcionista/checkout.fxml");
        } else if (actionEvent.getSource()==search_button) {
            AbrirMenu("../Vista/recepcionista/search.fxml");
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
