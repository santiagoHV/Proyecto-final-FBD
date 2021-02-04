package Controlador.vistas_recepcionista;

import DatosSQL.DAOs.DAO_Reserva;
import Modelo.entidades.Reserva;
import Vista.Main;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.events.JFXDialogEvent;
import io.github.palexdev.materialfx.controls.MFXProgressSpinner;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.security.interfaces.RSAKey;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controlador_reserva implements Initializable {
    public MFXProgressSpinner progressIndReserva;
    public TabPane TabPanePisos;

    int CAPACIDAD_MAXIMA = 120;

    //General
    public AnchorPane content;
    public StackPane stackPane;
    public Button btn_editar_reserva;
    public Button btn_nueva_reserva;
    public Button btn_hacer_reserva;
    public Button btn_datos_titular;

    //Room table

    //State table
    public Pane state_panel;
    public Label habitaciones_separadas;
    public Label estado_acomodacion;
    public Label total_personas;
    //Price table
    public Pane price_panel;
    public Label valor_estadia;
    public Label descuento;
    public Label valor_total;

    //Date and people panel
    public Pane date_q_panel;
    public Button btn_verificar_fechas;
    public TextField codigo_reserva;
    public DatePicker fecha_ingreso;
    public DatePicker fecha_salida;
    public Spinner cantidad_adultos;
    public Spinner cantidad_bebes;
    public Spinner cantidad_niños;


    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        progressIndReserva.setVisible(false);
        ObservableList<Tab> Tabs = TabPanePisos.getTabs();

        for (Tab e: Tabs)
        {
            AnchorPane Contenedor = (AnchorPane) e.getContent();

            VBox vBox = (VBox) Contenedor.getChildren().get(0);
            HBox hBoxSup = (HBox) vBox.getChildren().get(0);
            HBox hBoxInf = (HBox) vBox.getChildren().get(2);

            List<Node> NodosAnchorTab = new ArrayList<>();
            NodosAnchorTab.addAll(hBoxSup.getChildren());
            NodosAnchorTab.addAll(hBoxInf.getChildren());
            
            for (Node n: NodosAnchorTab){

                try{
                    Button BotonConvertido = (Button) n;

                    BotonConvertido.getStyleClass().add("map-green");

                    //System.out.println(BotonConvertido.getStyleClass());

                    BotonConvertido.setOnAction(actionEvent ->
                    {
                        try {
                            EvSelecHabi(actionEvent);
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    });
                }catch (Exception exception){
                    System.out.println(exception);
                    continue;
                }
            }
        }

        ////bloqueos iniciales///
        bloquearTodo();
        this.cantidad_adultos.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,50));
        this.cantidad_niños.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,50));
        this.cantidad_bebes.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,50));


    }
    public void click(ActionEvent actionEvent) {
        if(actionEvent.getSource().equals(btn_nueva_reserva)){
            date_q_panel.setDisable(false);
            state_panel.setDisable(false);
            btn_datos_titular.setDisable(false);
        }else if(actionEvent.getSource().equals(btn_verificar_fechas)){
            LocalDate fechaInicio = fecha_ingreso.getValue();
            LocalDate fechaFinal = fecha_salida.getValue();
            int huespedesBebes = (int) cantidad_bebes.getValue();
            int huespedesNinos = (int) cantidad_niños.getValue();
            int huespedesAdultos = (int) cantidad_adultos.getValue();

            if(validarDatosPrincipales(fechaInicio,fechaFinal,huespedesAdultos,huespedesNinos,huespedesBebes)){
                Date sqlFechaInicio = Date.valueOf(fechaInicio.toString());
                Date sqlFechaFinal = Date.valueOf(fechaInicio.toString());

                //habilitar habitaciones por consulta
                TabPanePisos.setDisable(false);
            }

        }else if(actionEvent.getSource().equals(btn_hacer_reserva)){
            Controlador_datos_ingreso controlador_datos_ingreso = new Controlador_datos_ingreso();
            System.out.println(controlador_datos_ingreso.nombre);
        }


    }

    public boolean validarDatosPrincipales(LocalDate fechaInicio, LocalDate fechaFinal, int adultos, int ninos, int bebes){
        int capacidadMinima = CAPACIDAD_MAXIMA; //debe consultarse la capacidad maxima de la fecha

        if(adultos + ninos > capacidadMinima){
            return false;
        }else if(fechaFinal.isBefore(fechaInicio) || fechaFinal.equals(fechaInicio)){
            return false;
        }
        return true;
    }

    public void bloquearTodo(){
        price_panel.setDisable(true);
        state_panel.setDisable(true);
        date_q_panel.setDisable(true);
        TabPanePisos.setDisable(true);

        //btn_hacer_reserva.setDisable(true);
        btn_datos_titular.setDisable(true);
    }


    public void EvSelecHabi(ActionEvent actionEvent) throws IOException {

        Node Boton = (Node) actionEvent.getSource();

        if(Boton.getStyleClass().get(3).equals("map-green"))
        {
            Boton.getStyleClass().set(3, "map-red");
        }
        else
        {
            Boton.getStyleClass().set(3, "map-green");
        }

        //System.out.println(Boton.getStyleClass());
    }

    private void makeStageDragable()
    {
        //obtiene posicion de click
        content.setOnMousePressed((mouseEvent ->
        {
            xOffset = mouseEvent.getSceneX();
            yOffset = mouseEvent.getSceneY();
        }));

        content.setOnMouseDragged((mouseEvent ->
        {
            Main.stage.setX(mouseEvent.getScreenX()-xOffset);
            Main.stage.setY(mouseEvent.getScreenY()-yOffset);

            Main.stage.setOpacity(0.8);
        }));

        content.setOnDragDone((dragEvent ->
        {
            Main.stage.setOpacity(1);
        }));

        content.setOnMouseReleased(mouseEvent ->
        {
            Main.stage.setOpacity(1);
        });
    }

    /**
     * event listener del boton de despliegue para datos de ususario
     * @param actionEvent
     * @throws IOException
     */
    public void desplegar_datos_titular(ActionEvent actionEvent) throws IOException {

        BoxBlur blur = new BoxBlur(3,3,3);
        Parent parent = FXMLLoader.load(getClass().getResource("../../Vista/recepcionista/ingreso_datos.fxml"));
        JFXDialog dialog = new JFXDialog(stackPane, (Region) parent, JFXDialog.DialogTransition.BOTTOM, true);
        AnchorPane AP = (AnchorPane) parent.getChildrenUnmodifiable().get(0);
        HBox HB = (HBox) AP.getChildren().get(0);
        Button BSalirDialog = (Button)HB.getChildrenUnmodifiable().get(1);
        Button btnCargarDatos = (Button)HB.getChildrenUnmodifiable().get(0);

        btnCargarDatos.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent) ->{
            CheckBox checkNuevo = (CheckBox) dialog.lookup("#checkNuevo");
            if(checkNuevo.isSelected()){
                //obtiene lo nuevo
            }else{
                //obtiene lo viejo
            }
        });

        BSalirDialog.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent)->
        {
            dialog.close();
        });

        dialog.setOnDialogClosed((JFXDialogEvent event)->
        {
            content.setEffect(null);
        });

        content.setEffect(blur);
        dialog.show();
    }

    public void Buscar_Reserva_Por_ID(ActionEvent actionEvent) {
        if(!codigo_reserva.getText().equals(""))
        {
            progressIndReserva.setVisible(true);
            //Se instancia la clase dao correspondiente al objeto a consultar:
            DAO_Reserva dao_reserva = new DAO_Reserva();

            //Se crea la tarea encargada de realizar la consulta (con la intención de que la consulta no congele la interfaz gráfica)
            Task<Reserva> reservaTask = new Task<Reserva>() {
                @Override
                protected Reserva call() throws Exception {
                    //Se realiza la consulta usando la instancia del dao asociado a la clase correspondiente
                    return dao_reserva.consultarReserva(Integer.parseInt(codigo_reserva.getText()+""));
                }
            };

            //Se crea el hilo que llevará a cabo la tarea
            Thread threadReserva = new Thread(reservaTask);

            //Se define la operación a realizar en caso de completar la tarea exitosamente (que conlleva a la obtención de los datos consultados en interfaz)
            reservaTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent workerStateEvent) {
                    Reserva reserva = reservaTask.getValue();
                    fecha_ingreso.setValue(reserva.getF_inicio().toLocalDate());
                    fecha_salida.setValue(reserva.getF_final().toLocalDate());
                    total_personas.setText(reserva.getCantidad_adultos()+"");
                    progressIndReserva.setVisible(false);
                }
            });


            //Se inicia el hilo asignado
            threadReserva.start();
        }
    }
}
