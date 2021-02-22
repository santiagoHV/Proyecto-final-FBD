package Controlador.vistas_recepcionista;

import Modelo.entidades.Cuenta_Productos;
import Modelo.entidades.Cuenta_Productos_Table;
import com.jfoenix.controls.JFXScrollPane;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import io.github.palexdev.materialfx.controls.MFXProgressSpinner;
import javafx.beans.Observable;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.util.Callback;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controlador_Consumos implements Initializable {

    public JFXTreeTableView<Cuenta_Productos_Table> tabla_consumos;
    public MFXProgressSpinner progressIndicator;
    JFXTreeTableColumn<Cuenta_Productos_Table, String> columnCodPro;
    JFXTreeTableColumn<Cuenta_Productos_Table, String> columnNomPro;
    JFXTreeTableColumn<Cuenta_Productos_Table, String> columnCantPro;
    JFXTreeTableColumn<Cuenta_Productos_Table, String> columnPrePro;
    JFXTreeTableColumn<Cuenta_Productos_Table, String> columnFecPedido;
    JFXTreeTableColumn<Cuenta_Productos_Table, String> columnCodCuenta;
    JFXTreeTableColumn<Cuenta_Productos_Table, String> columnTilReser;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        
        //smooth.smoothScrolling(tabla_consumos.get);
        tabla_consumos.setVisible(false);
        progressIndicator.setVisible(true);
        columnCodPro = new JFXTreeTableColumn<>("Código Del Producto");
        columnCodPro.setPrefWidth(180);
        columnCodPro.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Cuenta_Productos_Table, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Cuenta_Productos_Table, String> cuenta_productos_tableStringCellDataFeatures) {
                return cuenta_productos_tableStringCellDataFeatures.getValue().getValue().codigoProducto;
            }
        });

        columnNomPro = new JFXTreeTableColumn<>("Nombre del Producto");
        columnNomPro.setPrefWidth(180);
        columnNomPro.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Cuenta_Productos_Table, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Cuenta_Productos_Table, String> cuenta_productos_tableStringCellDataFeatures) {
                return cuenta_productos_tableStringCellDataFeatures.getValue().getValue().nombreProducto;
            }
        });

        columnCantPro = new JFXTreeTableColumn<>("Cantidad del producto");
        columnCantPro.setPrefWidth(180);
        columnCantPro.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Cuenta_Productos_Table, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Cuenta_Productos_Table, String> cuenta_productos_tableStringCellDataFeatures) {
                return cuenta_productos_tableStringCellDataFeatures.getValue().getValue().cantidadPedida;
            }
        });

        columnPrePro = new JFXTreeTableColumn<>("Precio del Producto");
        columnPrePro.setPrefWidth(180);
        columnPrePro.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Cuenta_Productos_Table, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Cuenta_Productos_Table, String> cuenta_productos_tableStringCellDataFeatures) {
                return cuenta_productos_tableStringCellDataFeatures.getValue().getValue().precioVenta;
            }
        });

        columnFecPedido = new JFXTreeTableColumn<>("Fecha del Pedido");
        columnFecPedido.setPrefWidth(180);
        columnFecPedido.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Cuenta_Productos_Table, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Cuenta_Productos_Table, String> cuenta_productos_tableStringCellDataFeatures) {
                return cuenta_productos_tableStringCellDataFeatures.getValue().getValue().fechaPedido;
            }
        });

        columnCodCuenta = new JFXTreeTableColumn<>("Código de cuenta");
        columnCodCuenta.setPrefWidth(100);
        columnCodCuenta.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Cuenta_Productos_Table, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Cuenta_Productos_Table, String> cuenta_productos_tableStringCellDataFeatures) {
                return cuenta_productos_tableStringCellDataFeatures.getValue().getValue().codigoCuenta;
            }
        });

        columnTilReser = new JFXTreeTableColumn<>("Titular");
        columnTilReser.setPrefWidth(100);
        columnTilReser.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Cuenta_Productos_Table, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Cuenta_Productos_Table, String> cuenta_productos_tableStringCellDataFeatures) {
                return cuenta_productos_tableStringCellDataFeatures.getValue().getValue().titularReserva;
            }
        });
    }

    public void definirColumnas(Task<List<Cuenta_Productos>> listTask){
        Thread consultaConsumo = new Thread(listTask);
        consultaConsumo.start();
        listTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent workerStateEvent) {
                ObservableList<Cuenta_Productos_Table> cuenta_productos_tables  = FXCollections.observableArrayList();
                for (int i = 0; i<listTask.getValue().size();i++)
                {
                    cuenta_productos_tables.add(new Cuenta_Productos_Table(listTask.getValue().get(i)));
                }
                final TreeItem<Cuenta_Productos_Table> root = new RecursiveTreeItem<Cuenta_Productos_Table>(cuenta_productos_tables, RecursiveTreeObject::getChildren);
                tabla_consumos.getColumns().setAll(columnCodPro, columnNomPro, columnCantPro, columnPrePro, columnFecPedido, columnCodCuenta, columnTilReser);
                tabla_consumos.setRoot(root);
                progressIndicator.setVisible(false);
                tabla_consumos.setVisible(true);
            }
        });
    }
}
