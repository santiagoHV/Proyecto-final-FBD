package Controlador.vistas_recepcionista;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class Controlador_pagos implements Initializable {
    public Pane back_tarjeta;
    public Pane panel_tarjeta;
    public Pane back_consignacion;
    public Pane panel_consignacion;
    public Pane back_efectivo;
    public Pane panel_efectivo;
    public JFXButton btn_aceptar;

    private String metodoDePago;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        panel_consignacion.setDisable(true);
        panel_efectivo.setDisable(true);
        panel_tarjeta.setDisable(false);


        btn_aceptar.setDisable(true);
    }


    public void escoger_pago(MouseEvent mouseEvent) {
        if(mouseEvent.getSource().equals(back_tarjeta)){
            panel_consignacion.setDisable(true);
            panel_efectivo.setDisable(true);
            panel_tarjeta.setDisable(false);
        }else if(mouseEvent.getSource().equals(back_consignacion)){
            panel_consignacion.setDisable(false);
            panel_efectivo.setDisable(true);
            panel_tarjeta.setDisable(true);

        }else if(mouseEvent.getSource().equals(back_efectivo)){
            panel_consignacion.setDisable(true);
            panel_efectivo.setDisable(false);
            panel_tarjeta.setDisable(true);
        }
    }

    public void validar_tarjeta(ActionEvent actionEvent) {
        btn_aceptar.setDisable(false);
        panel_tarjeta.getStyleClass().add("map-green");
        metodoDePago = "tarjeta";
    }

    public void validar_consignacion(ActionEvent actionEvent) {
        btn_aceptar.setDisable(false);
        panel_consignacion.getStyleClass().add("map-green");

        metodoDePago = "consignacion";
    }

    public void validar_efectivo(ActionEvent actionEvent) {
        btn_aceptar.setDisable(false);
        panel_efectivo.getStyleClass().add("map-green");
        metodoDePago = "efectivo";
    }

    public String getMetodoDePago() {
        return metodoDePago;
    }
}
