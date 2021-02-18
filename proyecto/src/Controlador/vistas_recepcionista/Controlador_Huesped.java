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
    public Controlador_checkin controlador_checkin;
    public JFXButton btn_cambiar;
    public JFXButton btn_ingreso;

    private Persona titularDeReserva;

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
                Registro registro = registroTask.getValue();
                if(registro!=null)
                {
                    comboHabitacion.getItems().add(registro.getHabitacion().getK_numero_habitacion());

                }
                else
                {
                    comboHabitacion.getItems().add("No Registrado");
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

        for(Habitacion h: habitacionList)
        {
            comboHabitacion.getItems().add(h.getK_numero_habitacion());
        }

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


    public void ev_ingresar_datos(ActionEvent actionEvent)
    {
        FXMLLoader loaderIngreso = new FXMLLoader(getClass().getResource("../../Vista/recepcionista/ingreso_datos.fxml"));
        Parent parent = null;
        try {
            parent = loaderIngreso.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        JFXDialog dialog = new JFXDialog(controlador_checkin.stackBG, (Region) parent, JFXDialog.DialogTransition.BOTTOM, true);
        AnchorPane AP = (AnchorPane) parent.getChildrenUnmodifiable().get(0);
        HBox HB = (HBox) AP.getChildren().get(0);
        Button BSalirDialog = (Button)HB.getChildrenUnmodifiable().get(1);
        Button btnCargarDatos = (Button)HB.getChildrenUnmodifiable().get(0);

        Controlador_datos_ingreso controlador_datos_ingreso = loaderIngreso.getController();

        btnCargarDatos.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEventIngreso) ->
        {
            CheckBox checkNuevo = (CheckBox) dialog.lookup("#checkNuevo");

            if(checkNuevo.isSelected()){
                titularDeReserva = controlador_datos_ingreso.solicitarPersona(true);
                try{
                    DAO_Persona dao_persona = new DAO_Persona();
                    dao_persona.insertarPersona(titularDeReserva);
                }catch (Exception e){
                    System.out.println(e + "Guardado fallido");
                }
            }else{
                titularDeReserva = controlador_datos_ingreso.solicitarPersona(false);
            }

            if(titularDeReserva.getClass().equals(Huesped.class))
            {
                setValoresPanel((Huesped) titularDeReserva);
                dialog.close();
                btn_ingreso.setDisable(false);
            }
            else
            {
                FXMLLoader alertaDireccion = new FXMLLoader(getClass().getResource("../../Vista/recepcionista/alerta.fxml"));
                Parent contenedor = null;
                try {
                    contenedor = alertaDireccion.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                JFXDialog dialogAlerta = new JFXDialog(controlador_checkin.stackBG, (Region) contenedor, JFXDialog.DialogTransition.BOTTOM, true);

                AnchorPane alertaAP = (AnchorPane) contenedor.getChildrenUnmodifiable().get(0);
                JFXButton btn_aceptar = (JFXButton) alertaAP.getChildren().get(2);
                btn_aceptar.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEventAceptar)->
                {
                    dialogAlerta.close();
                });

                dialogAlerta.show();
            }
        });

        BSalirDialog.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEventIngreso)->
        {
            dialog.close();
        });

        dialog.show();
    }
}
