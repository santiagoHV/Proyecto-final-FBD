package Controlador.vistas_gerente;

import Datos_NoSQL.Usuario;
import Datos_NoSQL.UsuarioDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        db = new UsuarioDAO();
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

    public ObservableList<Usuario> getUsuarios(){
        ObservableList<Usuario> obs = FXCollections.observableArrayList();
        for(Usuario user: db.getUsuarios()){
            if(user.getRole().equals("recept")){
                user.setRole("Recepcionista");
            }else if(user.getRole().equals("worker")){
                user.setRole("Trabajador");
            }else if(user.getRole().equals("gerente")){
                user.setRole("Gerente");
            }else if(user.getRole().equals("admin")){
                user.setRole("Administrador");
            }
            obs.add(user);
        }
        return obs;
    }
}

