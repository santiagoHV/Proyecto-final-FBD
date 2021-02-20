package Controlador.vistas_gerente.Home;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressIndicator;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controlador_IngresosHotel implements Initializable {
    public JFXComboBox<String> combo;
    public ProgressIndicator progresi;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        progresi.setVisible(false);
        ArrayList<String> opc = new ArrayList<>();
        opc.add("Siempre");
        opc.add("Ultimo Año");
        opc.add("Ultimo Mes");
        opc.add("Ultima Semana");
        opc.add("Hoy");
        ObservableList<String> obs = FXCollections.observableArrayList(opc);
        combo.setItems(obs);
    }

    public void loadDatos(){
        progresi.setVisible(true);
    }
}
