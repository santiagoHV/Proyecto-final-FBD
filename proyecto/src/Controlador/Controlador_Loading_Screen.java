package Controlador;

import javafx.fxml.Initializable;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.Effect;

import java.net.URL;
import java.util.ResourceBundle;

public class Controlador_Loading_Screen implements Initializable {

    public ProgressIndicator progressCircle;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Bloom bloom = new Bloom();
        bloom.setThreshold(0.4);
        progressCircle.setEffect(bloom);
    }
}
