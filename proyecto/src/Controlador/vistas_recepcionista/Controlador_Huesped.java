package Controlador.vistas_recepcionista;

import DatosSQL.DAOs.DAO_Habitacion;
import DatosSQL.DAOs.DAO_Registro;
import Modelo.entidades.Huesped;
import Modelo.entidades.Registro;
import com.jfoenix.controls.JFXComboBox;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.util.ResourceBundle;

public class Controlador_Huesped implements Initializable
{

    public Label LBnom_completo;
    public Label LBnum_id;
    public Label LBedad;
    public Label LBDireccion;
    public Label LBTelefono;
    public AnchorPane AnchorBG;
    public JFXComboBox comboHabitacion;
    public Label LB_TipoDoc;

    public void setValoresPanel(Huesped huesped)
    {
        LBnom_completo.setText(huesped.getN_nombre() + " " + huesped.getN_apellido());
        LBnum_id.setText(huesped.getK_identificacion()+"");
        Period period = Period.between(huesped.getF_nacimiento().toLocalDate(), LocalDate.now());
        LBedad.setText(period.getYears()+"");
        LBDireccion.setText(huesped.getN_direccion());
        LBTelefono.setText(huesped.getN_telefono());
        LB_TipoDoc.setText(huesped.getK_tipo_documento_id()+":");

        Task<Registro> registroTask = new Task<Registro>() {
            @Override
            protected Registro call() throws Exception {
                return new DAO_Registro().consultarRegistroPorHuesped(huesped.getK_identificacion(),huesped.getK_tipo_documento_id());
            }
        };

        registroTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent workerStateEvent) {
                Registro registro =registroTask.getValue();
                if(registro!=null)
                {
                    comboHabitacion.getItems().add(registro.getHabitacion().getK_numero_habitacion());

                }
                else
                {
                    comboHabitacion.getItems().add("No Registrado");
                }
                comboHabitacion.setValue(comboHabitacion.getItems().get(0));
            }
        });

        Thread consultaRegistro = new Thread(registroTask);
        consultaRegistro.start();
    }

    public void setValoresTemporales(String tipo)
    {
        LBnom_completo.setText("Por Asignar");
        LBnum_id.setText("Por Asignar");
        LBedad.setText("Por Asignar");
        LBDireccion.setText("Por Asignar");
        LBTelefono.setText("Por Asignar");

        if(tipo=="Bebe")
        {
            AnchorBG.getStyleClass().add("CardBebe");
        }
        else if(tipo=="Nino")
        {
            AnchorBG.getStyleClass().add("CardNinos");
        }
        else if (tipo=="Adulto")
        {
            AnchorBG.getStyleClass().add("CardAdultos");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
