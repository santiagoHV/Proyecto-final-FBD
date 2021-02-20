package Controlador.vistas_gerente.User;

import Datos_NoSQL.Usuario;
import Datos_NoSQL.UsuarioDAO;
import Vista.Main;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.events.JFXDialogEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controlador_Usuarios implements Initializable {

    public TableView<Usuario> table_users;
    public TableColumn<Usuario, String> col_user;
    public TableColumn<Usuario, String> col_pass;
    public TableColumn<Usuario, String> col_name;
    public TableColumn col_button;
    public UsuarioDAO db;
    public ObservableList<Usuario> OBS;
    public ProgressIndicator progress;
    public Button dropButton;
    public AnchorPane Anchor;
    public StackPane SPtabla;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        db = new UsuarioDAO();
        loadUsers();
    }

    public ObservableList<Usuario> getUsuarios() {
        ObservableList<Usuario> obs = FXCollections.observableArrayList();
        for (Usuario user : db.getUsuarios()) {
            if (user.getRole().equals("recept")) {
                user.setRole("Recepcionista");
            } else if (user.getRole().equals("worker")) {
                user.setRole("Trabajador");
            } else if (user.getRole().equals("gerente")) {
                user.setRole("Gerente");
            } else if (user.getRole().equals("admin")) {
                user.setRole("Administrador");
            }
            obs.add(user);
        }
        return obs;
    }

    public void dropSelectedUser() {
            Usuario user = table_users.getSelectionModel().getSelectedItem();
            if(user != null) {
                if (user.getRole().equals("Recepcionista")) {
                    user.setRole("recept");
                } else if (user.getRole().equals("Trabajador")) {
                    user.setRole("worker");
                } else if (user.getRole().equals("Gerente")) {
                    user.setRole("gerente");
                } else if (user.getRole().equals("Administrador")) {
                    user.setRole("admin");
                }
                db.dropUser(user);
                loadUsers();
            }else{
                JOptionPane.showMessageDialog(null,"Por favor, seleccione un usuario");
            }
    }

    public void loadUsers() {
        progress.setVisible(true);
        Task<Void> tareita = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                OBS = getUsuarios();
                return null;
            }
        };
        tareita.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent workerStateEvent) {
                col_user.setCellValueFactory(new PropertyValueFactory("user"));
                col_pass.setCellValueFactory(new PropertyValueFactory("password"));
                col_name.setCellValueFactory(new PropertyValueFactory("role"));
                table_users.setItems(OBS);
                progress.setVisible(false);
            }
        });
        Thread ricoJuanHurtado = new Thread(tareita);
        ricoJuanHurtado.start();
    }

    public void openAdd() throws IOException {
        BoxBlur blur = new BoxBlur(3, 3, 3);
        Parent parent = FXMLLoader.load(getClass().getResource("../../../Vista/gerente/add_user.fxml"));
        JFXDialog dialog = new JFXDialog(SPtabla, (Region) parent, JFXDialog.DialogTransition.TOP, true);
        //Esto es un machetazo el tenaz, pero no sé de que otra forma hacerlo:
        AnchorPane AP = (AnchorPane) parent.getChildrenUnmodifiable().get(0);
        Button BSalirDialog = (Button) AP.getChildrenUnmodifiable().get(6);

        //Definción del manejador de eventos del botón cancelar para que cierra el dialog
        BSalirDialog.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent) ->
        {
            dialog.close();
        });
        dialog.setOnDialogClosed((JFXDialogEvent event) ->
        {
            Anchor.setEffect(null);
            loadUsers();
        });

        //Aplicación del efecto
        Anchor.setEffect(blur);

        //Se muestra el dialog:
        dialog.show();
    }
}

