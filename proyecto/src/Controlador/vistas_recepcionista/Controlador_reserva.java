package Controlador.vistas_recepcionista;

import Controlador.Controlador_alerta;
import DatosSQL.DAOs.*;
import Modelo.entidades.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
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

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controlador_reserva implements Initializable {

    int CAPACIDAD_MAXIMA = 108;
    private double xOffset = 0;
    private double yOffset = 0;

    //General
    public AnchorPane content;
    public StackPane stackPane;
    public Button btn_editar_reserva;
    public Button btn_nueva_reserva;
    public Button btn_hacer_reserva;
    public Button btn_datos_titular;
    public Button btn_buscar_reserva;

    //Room table

    //State table
    public Pane state_panel;
    public Label habitaciones_separadas;
    public Label estado_acomodacion;
    public Label total_personas;
    public Label lbl_titulo_condicion;
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

    public MFXProgressSpinner progressIndReserva;
    public TabPane TabPanePisos;
    public Label lblCodigoDeReserva;

    public ArrayList<Tipo> tipos;

    //Datos escenciales de reserva
    public Persona titularDeReserva;
    public ArrayList<Habitacion> listaHabitaciones = new ArrayList<Habitacion>();
    public Date sqlFechaInicio;
    public Date sqlFechaFinal;
    public int huespedesBebes;
    public int huespedesNinos;
    public int huespedesAdultos;
    public int codigoDeReserva;
    public double precioReserva;
    Condicion_Hotel condicionHotel;

    public ArrayList<String> listaNombresHabitaciones = new ArrayList<String>();
    public String mensajeEstado;
    public int personasHospedadasEnLaFecha;
    public int cupoEnHabitacionesEnReserva = 0;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        progressIndReserva.setVisible(false);

        bloquearTodo();
        this.cantidad_adultos.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,120));
        this.cantidad_niños.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,120));
        this.cantidad_bebes.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,50));


    }

    /**
     * responde a los clicks de validar, reservar, nuevar reserva
     * @param actionEvent
     */
    public void click(ActionEvent actionEvent) throws SQLException {
        DAO_Reserva dao_reserva = new DAO_Reserva();

        /**
         * prepara para una reserva nueva, solicitando la condicion de hotel activa,
         * los tipos de habitacion y el k de la ultima reserva
         */
        if(actionEvent.getSource().equals(btn_nueva_reserva)){

            DAO_Tipo dao_tipo = new DAO_Tipo();
            DAO_CondicionHotel dao_condicionHotel = new DAO_CondicionHotel();


            progressIndReserva.setVisible(true);

            Task<ArrayList<Tipo>> tipoTask = new Task<ArrayList<Tipo>>() {
                @Override
                protected ArrayList<Tipo> call() throws Exception {
                    return dao_tipo.consultarTipos();
                }
            };
            Task<Condicion_Hotel> condicion_hotelTask = new Task<Condicion_Hotel>() {
                @Override
                protected Condicion_Hotel call() throws Exception {
                    return dao_condicionHotel.cosultarCondicionActiva();
                }
            };
            Task<Integer> numReservaTask = new Task<Integer>() {
                @Override
                protected Integer call() throws Exception {
                    return dao_reserva.consultarUltimoCodigo();
                }
            };

            Thread threadTipos = new Thread(tipoTask);
            Thread threadCondicion = new Thread(condicion_hotelTask);
            Thread threadNumReserva = new Thread(numReservaTask);

            tipoTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent workerStateEvent) {
                    tipos = tipoTask.getValue();

                    date_q_panel.setDisable(false);
                    state_panel.setDisable(false);
                    price_panel.setDisable(false);

                    threadCondicion.start();
                    threadNumReserva.start();
                    bloqueosNuevaReserva(true);

                    fecha_ingreso.setDayCellFactory(d ->
                            new DateCell() {
                                @Override public void updateItem(LocalDate item, boolean empty) {
                                    super.updateItem(item, empty);
                                    setDisable(item.isBefore(LocalDate.now()));
                                }});

                    fecha_salida.setDayCellFactory(d ->
                            new DateCell() {
                                @Override public void updateItem(LocalDate item, boolean empty) {
                                    super.updateItem(item, empty);
                                    setDisable(item.isBefore(LocalDate.now()));
                                }});
                    progressIndReserva.setVisible(false);

                }
            });

            condicion_hotelTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent workerStateEvent) {
                    condicionHotel = condicion_hotelTask.getValue();
                }
            });
            numReservaTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent workerStateEvent) {
                    codigoDeReserva = numReservaTask.getValue() + 1;
                    lblCodigoDeReserva.setText(String.valueOf(codigoDeReserva));
                }
            });

            threadTipos.start();

        }
        /**
         * verifica la correcta colocacion de las fechas, la cantidad de personas
         * y verifica disponibilidad por condicion hotelera
         */
        else if(actionEvent.getSource().equals(btn_verificar_fechas)){

            LocalDate fechaInicio = fecha_ingreso.getValue();
            LocalDate fechaFinal = fecha_salida.getValue();
            huespedesBebes = (int) cantidad_bebes.getValue();
            huespedesNinos = (int) cantidad_niños.getValue();
            huespedesAdultos = (int) cantidad_adultos.getValue();


            if(validarFechas(fechaInicio,fechaFinal)){
                this.sqlFechaInicio = Date.valueOf(fechaInicio.toString());
                this.sqlFechaFinal = Date.valueOf(fechaFinal.toString());


                DAO_Habitacion dao_habitacion = new DAO_Habitacion();
                progressIndReserva.setVisible(true);

                Task<ArrayList<Integer>> habitacionesTask = new Task<ArrayList<Integer>>() {
                    @Override
                    protected ArrayList<Integer> call() throws Exception {
                        personasHospedadasEnLaFecha = dao_reserva.consultarCantidadDePersonasHospedadas(sqlFechaInicio,sqlFechaFinal);
                        return dao_habitacion.consultarHbitacionesOcupadasPorFecha(sqlFechaInicio,sqlFechaFinal);
                    }
                };

                Thread threadConsultaHabitaciones = new Thread(habitacionesTask);

                habitacionesTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                    @Override
                    public void handle(WorkerStateEvent workerStateEvent) {
                        System.out.println("cantidad de personas" + personasHospedadasEnLaFecha);
                        System.out.println("aforo " + CAPACIDAD_MAXIMA * condicionHotel.getAforo());
                        if(validarCantidadDePersonas(huespedesAdultos,huespedesNinos,huespedesBebes, (int) ((CAPACIDAD_MAXIMA * condicionHotel.getAforo()) - personasHospedadasEnLaFecha))){
                            mostrarHabitacionesOcupadas(habitacionesTask.getValue());

                            TabPanePisos.setDisable(false);
                            total_personas.setText(String.valueOf(huespedesAdultos+huespedesNinos) + " (Sin contar bebés)");
                        }
                        progressIndReserva.setVisible(false);
                    }
                });
                threadConsultaHabitaciones.start();

            }
        }
        else if(actionEvent.getSource().equals(btn_hacer_reserva)){
            Reserva reserva = new Reserva(codigoDeReserva,"activa",sqlFechaInicio,Date.valueOf(LocalDate.now()),
                    sqlFechaFinal,huespedesBebes,huespedesNinos,huespedesAdultos,precioReserva,condicionHotel, titularDeReserva);
            progressIndReserva.setVisible(true);

            Task taskReserva = new Task() {
                @Override
                protected Object call() throws Exception {
                    dao_reserva.insertarReserva(reserva);

                    for(Habitacion habitacion: listaHabitaciones){
                        dao_reserva.insertarHabitacionEnReserva(reserva,habitacion);
                    }

                    return null;
                }
            };

            Thread threadReserva = new Thread(taskReserva);

            taskReserva.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent workerStateEvent) {
                    progressIndReserva.setVisible(false);

                    mostrarAlerta("¡Reserva exitosa!", "Código de reserva: " + codigoDeReserva);

                    reiniciarPagina();

                }
            });

            threadReserva.start();
        }
    }

    /**
     * Valida los datos de fecha para consultar disponibilidad
     * @param fechaInicio
     * @param fechaFinal
     * @return
     */
    public boolean validarFechas(LocalDate fechaInicio, LocalDate fechaFinal){

        if(fechaInicio != null && fechaFinal != null){
            if (fechaFinal.isBefore(fechaInicio) || fechaFinal.equals(fechaInicio)) {
                invalidarControl(true,fecha_ingreso);
                invalidarControl(true,fecha_salida);
                return false;
            }
            invalidarControl(false,fecha_ingreso);
            invalidarControl(false,fecha_salida);
            return true;
        }else if(fechaInicio == null){
            invalidarControl(true,fecha_ingreso);
            return false;
        } else if(fechaFinal == null){
            invalidarControl(true,fecha_salida);
            return false;
        }else{
            return false;
        }
    }

    /**
     * valida cantidad de personas en reserva comparado con la capacidad
     * @param adultos
     * @param ninos
     * @param bebes
     * @param capacidadMinima
     * @return
     */
    public boolean validarCantidadDePersonas( int adultos, int ninos, int bebes, int capacidadMinima){

        System.out.println("capacidad minima" + capacidadMinima);
        if(adultos + ninos + bebes == 0){
            invalidarControl(true,cantidad_adultos);
            invalidarControl(true,cantidad_niños);
            invalidarControl(true,cantidad_bebes);
            return false;
        }
        else if (adultos + ninos + bebes > capacidadMinima) {
            invalidarControl(true,cantidad_adultos);
            invalidarControl(true,cantidad_niños);
            invalidarControl(true,cantidad_bebes);
            return false;

        }
        else {
            invalidarControl(false,cantidad_adultos);
            invalidarControl(false,cantidad_niños);
            invalidarControl(false,cantidad_bebes);
            return true;
        }
    }

    /**
     * Bloqueos en interfaz cuando se selecciona nueva reserva
     */
    public void bloqueosNuevaReserva(boolean flagBloqueado){
        btn_editar_reserva.setDisable(flagBloqueado);
        codigo_reserva.setDisable(flagBloqueado);
        btn_buscar_reserva.setDisable(flagBloqueado);
    }

    /**
     * Hace los bloqueos iniciales en la interfaz
     */
    public void bloquearTodo(){
        price_panel.setDisable(true);
        state_panel.setDisable(true);
        date_q_panel.setDisable(true);
        TabPanePisos.setDisable(true);

        btn_hacer_reserva.setDisable(true);
        btn_datos_titular.setDisable(true);
    }

    /**
     * Vacia los elementos visuales y objetos del proceso de reserva
     */
    public void reiniciarPagina(){
        fecha_salida.getEditor().clear();
        fecha_ingreso.getEditor().clear();
        cantidad_bebes.getEditor().clear();
        cantidad_niños.getEditor().clear();
        cantidad_adultos.getEditor().clear();

        bloquearTodo();

        btn_buscar_reserva.setDisable(false);
        btn_nueva_reserva.setDisable(false);
        btn_editar_reserva.setDisable(false);
        codigo_reserva.setDisable(false);

        titularDeReserva = null;
        listaHabitaciones = null;
        listaNombresHabitaciones = null;
        sqlFechaFinal = null;
        sqlFechaInicio = null;
        huespedesAdultos = 0;
        huespedesNinos = 0;
        huespedesBebes = 0;
        condicionHotel = null;
        codigo_reserva.setText("");
        precioReserva = 0;
        mensajeEstado = null;
        cupoEnHabitacionesEnReserva = 0;
        personasHospedadasEnLaFecha = 0;

        habitaciones_separadas.setText("");
        estado_acomodacion.setText("");
        total_personas.setText("");
        lbl_titulo_condicion.setText("");
    }

    /**
     * Bloquea las habitaciones ocupadas en las fechas seleccionadas
     * @param habitacionesOcupadas
     */
    public void mostrarHabitacionesOcupadas(ArrayList<Integer> habitacionesOcupadas) {
        ObservableList<Tab> Tabs = TabPanePisos.getTabs();

        for (Tab e: Tabs) {
            AnchorPane Contenedor = (AnchorPane) e.getContent();

            VBox vBox = (VBox) Contenedor.getChildren().get(0);
            HBox hBoxSup = (HBox) vBox.getChildren().get(0);
            HBox hBoxInf = (HBox) vBox.getChildren().get(2);

            List<Node> NodosAnchorTab = new ArrayList<>();
            NodosAnchorTab.addAll(hBoxSup.getChildren());
            NodosAnchorTab.addAll(hBoxInf.getChildren());

            for (Node n : NodosAnchorTab) {
                Button BotonConvertido = (Button) n;
                BotonConvertido.getStyleClass().add("");

                for(int habitacion: habitacionesOcupadas){

                    if(BotonConvertido.getId().equals(String.valueOf(habitacion))){
                        BotonConvertido.getStyleClass().set(3,"map-red");
                        BotonConvertido.setDisable(true);

                    }
                }
                try {
                    BotonConvertido.setOnAction(actionEvent -> {
                        try {
                            EvSelecHabi(actionEvent);
                        } catch (IOException | SQLException ioException) {
                            ioException.printStackTrace();
                        }
                    });
                } catch (Exception exception) {
                    System.out.println(exception);
                    continue;
                }
            }
        }
    }

    /**
     * Selecciona una habitacion
     * @param actionEvent
     * @throws IOException
     * @throws SQLException
     */
    public void EvSelecHabi(ActionEvent actionEvent) throws IOException, SQLException {

        Node boton = (Node) actionEvent.getSource();

        if(boton.getStyleClass().get(3).equals("")) {
            boton.getStyleClass().set(3, "map-green");
            listaNombresHabitaciones.add(boton.getId());
            Tipo tipo;
            if(Integer.parseInt(boton.getId()) < 400){
                tipo = tipos.get(2);
            }else if(Integer.parseInt(boton.getId()) < 505){
                tipo = tipos.get(3);
            }else if(Integer.parseInt(boton.getId()) < 700){
                tipo = tipos.get(2);
            }else{
                tipo = tipos.get(0);
            }

            listaHabitaciones.add(new Habitacion(Integer.parseInt(boton.getId()),tipo));
            actualizarDatosDeReserva();


        } else {
            boton.getStyleClass().set(3, "");

            listaHabitaciones.remove(listaNombresHabitaciones.indexOf(boton.getId()));
            listaNombresHabitaciones.remove(boton.getId());
            actualizarDatosDeReserva();
        }
        validarHabitacionesConHuespedes();
        actualizarPrecios(precioReserva);

    }

    /**
     * event listener del boton de despliegue para datos de ususario
     * @param actionEvent
     * @throws IOException
     */
    public void desplegar_datos_titular(ActionEvent actionEvent) throws IOException {

        BoxBlur blur = new BoxBlur(3,3,3);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../Vista/recepcionista/ingreso_datos.fxml"));
        Parent parent = loader.load();
        JFXDialog dialog = new JFXDialog(stackPane, (Region) parent, JFXDialog.DialogTransition.BOTTOM, true);
        AnchorPane AP = (AnchorPane) parent.getChildrenUnmodifiable().get(0);
        HBox HB = (HBox) AP.getChildren().get(0);
        Button BSalirDialog = (Button)HB.getChildrenUnmodifiable().get(1);
        Button btnCargarDatos = (Button)HB.getChildrenUnmodifiable().get(0);

        Controlador_datos_ingreso controlador_datos_ingreso = loader.getController();
        controlador_datos_ingreso.direccion_in.setDisable(true);

        btnCargarDatos.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent) ->{
            CheckBox checkNuevo = (CheckBox) dialog.lookup("#checkNuevo");


            if(checkNuevo.isSelected()){
                titularDeReserva = controlador_datos_ingreso.solicitarPersona(true);
                try{
                    DAO_Persona dao_persona = new DAO_Persona();
                    dao_persona.insertarPersona(titularDeReserva);
                    dialog.close();
                }catch (Exception e){
                    System.out.println(e + "Guardado fallido");
                }
            }else{
                titularDeReserva = controlador_datos_ingreso.solicitarPersona(false);
                dialog.close();
            }
            btn_hacer_reserva.setDisable(false);


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

    /**
     * Actualiza los datos del estado de la reserva
     */
    public void actualizarDatosDeReserva(){
        habitaciones_separadas.setText("");
        for(String habitacion: listaNombresHabitaciones){
            if(habitaciones_separadas.getText().equals("")){
                habitaciones_separadas.setText(habitacion);
            }else {
                String habActual = habitaciones_separadas.getText();

                habitaciones_separadas.setText(habActual + ", " + habitacion);
            }

        }
    }

    /**
     * Verifica la cantidad de huespedes con relacion a la cantidad de cupos
     * separados en las habitaciones, y actualiza el precio de reserva
     */
    public void validarHabitacionesConHuespedes(){
        cupoEnHabitacionesEnReserva = 0;
        precioReserva = 0;
        boolean flagHabitacionDoble = false;
        boolean flagHabitacionTriple= false;

        for(Habitacion habitacion: this.listaHabitaciones){
            if(habitacion.getTipo_habitacion().getCupo() == 2){
                flagHabitacionDoble = true;
            }else if(habitacion.getTipo_habitacion().getCupo() == 3){
                flagHabitacionTriple = true;
            }
            cupoEnHabitacionesEnReserva += habitacion.getTipo_habitacion().getCupo();
            precioReserva += habitacion.getTipo_habitacion().getPrecio_habitacion();
        }

        System.out.println("cupo en habitaciones en reserva " + cupoEnHabitacionesEnReserva);

        if(flagHabitacionTriple){
            if(huespedesNinos + huespedesAdultos <= cupoEnHabitacionesEnReserva && huespedesNinos + huespedesAdultos >= (cupoEnHabitacionesEnReserva - 2)){
                btn_datos_titular.setDisable(false);
                mensajeEstado = "Correcto";
            }else{
                mensajeEstado = "Error cupo habitaciones";
                btn_datos_titular.setDisable(true);
            }
        }else if(flagHabitacionDoble){
            if(huespedesNinos + huespedesAdultos <= cupoEnHabitacionesEnReserva && huespedesNinos + huespedesAdultos >= (cupoEnHabitacionesEnReserva - 1)){
                btn_datos_titular.setDisable(false);
                mensajeEstado = "Correcto";
            }else{
                mensajeEstado = "Error cupo habitaciones";
                btn_datos_titular.setDisable(true);
            }
        }else{
            if(huespedesNinos + huespedesAdultos == cupoEnHabitacionesEnReserva){
                btn_datos_titular.setDisable(false);
                mensajeEstado = "Correcto";
            }else{
                mensajeEstado = "Error cupo habitaciones";
                btn_datos_titular.setDisable(true);

            }
        }

        estado_acomodacion.setText(mensajeEstado);
        //actualizar precios
    }

    /**
     * actualiza los precios de descuentos y general
     * @param precioGeneral
     */
    public void actualizarPrecios(double precioGeneral){
        double descuento;

        System.out.println(condicionHotel.getNumero_dias() + " " + condicionHotel.getDescuento());
        if((sqlFechaFinal.toLocalDate().getDayOfYear() - sqlFechaInicio.toLocalDate().getDayOfYear()) >= condicionHotel.getNumero_dias()){
            descuento = condicionHotel.getDescuento() * precioGeneral;
        }else{
            descuento = 0;
        };

        this.valor_estadia.setText(String.format("$ %(,.2f",precioGeneral));
        this.descuento.setText(String.format("$ %(,.2f",descuento));
        this.valor_total.setText(String.format("$ %(,.2f",(precioGeneral - descuento)));

    }

    /**
     * Ejecuta una consulta a una reserva existente segun su ID
     * @param actionEvent
     */
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

    /**
     * Valida o invalida un nodo
     * @param flagInvalido
     * @param nodo
     */
    public void invalidarControl(boolean flagInvalido, Node nodo){
        if(flagInvalido){
            nodo.getStyleClass().add("map-red");
        }else{
            nodo.getStyleClass().remove("map-red");
        }
    }

    public void mostrarAlerta(String titulo, String mensaje){
        FXMLLoader alertaDireccion = new FXMLLoader(getClass().getResource("../../Vista/recepcionista/alerta.fxml"));
        Parent contenedor = null;
        try {
            contenedor = alertaDireccion.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JFXDialog dialogAlerta = new JFXDialog(stackPane, (Region) contenedor, JFXDialog.DialogTransition.BOTTOM, true);

        Controlador_alerta controlador_alerta = alertaDireccion.getController();

        AnchorPane alertaAP = (AnchorPane) contenedor.getChildrenUnmodifiable().get(0);
        JFXButton btn_aceptar = (JFXButton) alertaAP.getChildren().get(2);
        btn_aceptar.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEventAceptar)->
        {
            dialogAlerta.close();
        });

        controlador_alerta.mensaje.setText(mensaje);
        controlador_alerta.titulo.setText(titulo);


        dialogAlerta.show();

    }

}
