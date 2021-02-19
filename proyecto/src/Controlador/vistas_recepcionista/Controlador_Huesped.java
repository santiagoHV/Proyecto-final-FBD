package Controlador.vistas_recepcionista;

import DatosSQL.DAOs.DAO_Habitacion;
import DatosSQL.DAOs.DAO_Persona;
import DatosSQL.DAOs.DAO_Registro;
import Modelo.entidades.Habitacion;
import Modelo.entidades.Huesped;
import Modelo.entidades.Persona;
import Modelo.entidades.Registro;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.events.JFXDialogEvent;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
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
    public Label txt_tipo_huesped;
    public JFXButton btn_cambiar;
    public JFXButton btn_ingreso;

    public void setValoresPanel(Huesped huesped, List<Habitacion> habitacionList)
    {
        LBnom_completo.setText(huesped.getN_nombre() + " " + huesped.getN_apellido());
        LBnum_id.setText(huesped.getK_identificacion()+"");
        Period period = Period.between(huesped.getF_nacimiento().toLocalDate(), LocalDate.now());
        LBedad.setText(period.getYears()+"");

        if(period.getYears()<=2)
        {
            AnchorBG.getStyleClass().add("CardBebe");
            txt_tipo_huesped.setText("Bebe");
        }
        else if(period.getYears()>2 && period.getYears()<18)
        {
            AnchorBG.getStyleClass().add("CardNinos");
            txt_tipo_huesped.setText("Niño");
        }
        else if (period.getYears()>=18)
        {
            AnchorBG.getStyleClass().add("CardAdultos");
            txt_tipo_huesped.setText("Adulto");
        }

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
                Registro registro = registroTask.getValue();
                if(registro!=null)
                {
                    comboHabitacion.getItems().add(registro.getHabitacion().getK_numero_habitacion());

                    for(Habitacion h: habitacionList)
                    {
                        if(!comboHabitacion.getItems().contains(h.getK_numero_habitacion()))
                        {
                            comboHabitacion.getItems().add(h.getK_numero_habitacion());
                        }
                    }
                }
                else
                {
                    if(habitacionList==null)
                    {
                        if(!comboHabitacion.getItems().get(comboHabitacion.getItems().size()-1).equals("No Registrado"))
                        {
                            comboHabitacion.getItems().add("No Registrado");
                        }
                    }
                    else
                    {
                        int i = 0;
                        for(Habitacion h: habitacionList)
                        {
                            if(!comboHabitacion.getItems().get(i).equals(h.getK_numero_habitacion()))
                            {
                                comboHabitacion.getItems().add(h.getK_numero_habitacion());
                            }
                            i++;
                        }
                    }
                }
                comboHabitacion.setValue(comboHabitacion.getItems().get(comboHabitacion.getItems().size()-1));
            }
        });

        Thread consultaRegistro = new Thread(registroTask);
        consultaRegistro.start();
    }

    public void setValoresTemporales(String tipo, List<Habitacion> habitacionList)
    {
        LBnom_completo.setText("Por Asignar");
        LBnum_id.setText("Por Asignar");
        LBedad.setText("Por Asignar");
        LBDireccion.setText("Por Asignar");
        LBTelefono.setText("Por Asignar");
        txt_tipo_huesped.setText(tipo);

        if(tipo.equals("Bebe"))
        {
            AnchorBG.getStyleClass().add("CardBebe");
        }
        else if(tipo.equals("Niño"))
        {
            AnchorBG.getStyleClass().add("CardNinos");
        }
        else if (tipo.equals("Adulto"))
        {
            AnchorBG.getStyleClass().add("CardAdultos");
        }

        for(Habitacion h: habitacionList)
        {
            comboHabitacion.getItems().add(h.getK_numero_habitacion());
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        
    }
}
