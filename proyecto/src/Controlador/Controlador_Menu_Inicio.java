package Controlador;

import Vista.Main;
import animatefx.animation.*;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.events.JFXDialogEvent;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controlador_Menu_Inicio implements Initializable
{

    public ImageView Banner1;
    public Button BSalir;

    public AnchorPane AnchorMP;

    public JFXButton BRecep;
    public JFXButton BEmple;
    public JFXButton BAdmin;
    public StackPane stackPane1;

    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        //Invocación del método para volver el stage arrastrable:
        this.makeStageDragable();

        //Obtención del archivo por la ruta:
        File file = new File("assets/images/Slide1_MenuInicio.jpg");

        //Conversión del archivo:
        Image image = new Image(file.toURI().toString());

        //Establecer imagen:
        Banner1.setImage(image);

        //Para redondear los bordes de la imagen se debe hacer lo siguiente:

        //Se crea un objeto rectángulo para que contenga a la imagen:
        Rectangle clip = new Rectangle();

        //Se definen las dimensiones del rectángulo (las mismas que las del Imageview):
        clip.setWidth(Banner1.getFitWidth());
        clip.setHeight(Banner1.getFitHeight());

        //Se arquea el rectángulo:

        clip.setArcHeight(25);
        clip.setArcWidth(25);
        clip.setStroke(Color.BLACK);

        Banner1.setClip(clip);

        //Se toma Snapshot de la imagen con el clip asignado:

        SnapshotParameters snapshotParameters = new SnapshotParameters();
        snapshotParameters.setFill(Color.TRANSPARENT);

        //No entiendo esto la verdad xd
        WritableImage NImage = Banner1.snapshot(snapshotParameters, null);

        //Se elimina el clip
        Banner1.setClip(null);

        //Se vuelve a definir la imagen:
        Banner1.setImage(NImage);
    }

    public void EvIng(ActionEvent actionEvent) throws IOException {
        //Se obtiene la fuente del evento:
        String Boton = actionEvent.getSource().toString();

        //Se parte el string de la fuente del evento para obtener el texto del botón:
        String [] Partes = Boton.split("'");

        /*CREACIÓN DEL JFXDIALOG NO HE HECHO ESTO EN MI VIDA AYUDA POR FAVOR*/
        //Actualización: Ahora sé un poco más, me siento crack

        //Creación del efecto blur:

        BoxBlur blur = new BoxBlur(3,3,3);

        //Obtención del parent con la ruta del fxml a usar
        Parent parent = FXMLLoader.load(getClass().getResource("../Vista/ingreso/Login.fxml"));

        //Creación del Dialog usando el Parent como Region (cast) para poder personalizarlo:
        JFXDialog dialog = new JFXDialog(stackPane1, (Region) parent, JFXDialog.DialogTransition.BOTTOM, true);

        //Obtención de los hijos de Parent (en este caso el botón cancelar):

        //Esto es un machetazo el tenaz, pero no sé de que otra forma hacerlo:
        AnchorPane AP = (AnchorPane) parent.getChildrenUnmodifiable().get(0);

        HBox HB = (HBox) AP.getChildren().get(0);
        ((Label) AP.getChildren().get(3)).setText(Partes[1]);

        JFXButton BSalirDialog = (JFXButton)HB.getChildrenUnmodifiable().get(0);

        //Definción del manejador de eventos del botón cancelar para que cierra el dialog
        BSalirDialog.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent)->
        {
            dialog.close();
        });

        dialog.setOnDialogClosed((JFXDialogEvent event)->
        {
            AnchorMP.setEffect(null);
        });

        //Aplicación del efecto
        AnchorMP.setEffect(blur);

        //Se muestra el dialog:
        dialog.show();
    }

    //Método para arrastrar la ventana
    private void makeStageDragable()
    {
        stackPane1.setOnMousePressed((mouseEvent ->
        {
            xOffset = mouseEvent.getSceneX();
            yOffset = mouseEvent.getSceneY();
        }));

        stackPane1.setOnMouseDragged((mouseEvent ->
        {
            Main.stage.setX(mouseEvent.getScreenX()-xOffset);
            Main.stage.setY(mouseEvent.getScreenY()-yOffset);

            Main.stage.setOpacity(0.8);
        }));

        stackPane1.setOnDragDone((dragEvent ->
        {
            Main.stage.setOpacity(1);
        }));

        stackPane1.setOnMouseReleased(mouseEvent ->
        {
            Main.stage.setOpacity(1);
        });
    }

    public void EvSalir(ActionEvent actionEvent)
    {
        //Se reproduce la animación de rebote :3
        new BounceOut(BSalir).play();

        //Obtención del Stage desde donde ocurrió el evento
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        Scene scene = source.getScene();

        new FadeOut(scene.getRoot()).setDelay(Duration.millis(900)).play();

        //Hilo que prometo estudiar algún día

        Task<Void> sleeper = new Task<Void>()
        {
            @Override
            protected Void call() throws Exception {
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                }
                return null;
            }
        };

        //Método invocado una vez se termina correctamente el hilo anterior
        sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>()
        {
            //Método para cerrar la aplicación
            @Override
            public void handle(WorkerStateEvent event)
            {
                stage.close();
            }
        });
        new Thread(sleeper).start();
    }
}
