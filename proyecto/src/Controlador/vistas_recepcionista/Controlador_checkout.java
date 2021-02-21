package Controlador.vistas_recepcionista;

import com.jfoenix.svg.SVGGlyph;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.net.URL;
import java.util.ResourceBundle;

public class Controlador_checkout implements Initializable {

    public AnchorPane anchorBG;
    public Pane panel_titular;
    private SVGGlyph information = new SVGGlyph(0,"information",
            "M12,2A10,10,0,1,0,22,12,10.01114,10.01114,0,0,0,12,2Zm0,18a8,8,0,1,1,8-8A8.00917,8.00917,0,0,1,12,20Zm0-8.5a1,1,0,0,0-1,1v3a1,1,0,0,0,2,0v-3A1,1,0,0,0,12,11.5Zm0-4a1.25,1.25,0,1,0,1.25,1.25A1.25,1.25,0,0,0,12,7.5Z"
            , Color.DARKGRAY);

    private SVGGlyph balance = new SVGGlyph(1,"balance",
            "M22.96423,13.82263a.94762.94762,0,0,0-.02819-.17419L20.63135,7.51135A2.99558,2.99558,0,0,0,22,5a1,1,0,0,0-2,0,1.00037,1.00037,0,0,1-1.88184.47266A2.8934,2.8934,0,0,0,15.54,4H13V3a1,1,0,0,0-2,0V4H8.46A2.8934,2.8934,0,0,0,5.88184,5.47266,1.00037,1.00037,0,0,1,4,5,1,1,0,0,0,2,5,2.99558,2.99558,0,0,0,3.36865,7.51135L1.064,13.64844a.94762.94762,0,0,0-.02819.17419A.94855.94855,0,0,0,1,14c0,.00928.00269.01782.00275.0271.0003.01318.003.02533.0039.03845a3.99379,3.99379,0,0,0,7.9867,0c.00085-.01312.0036-.02527.0039-.03845C8.99731,14.01782,9,14.00928,9,14a.94855.94855,0,0,0-.03577-.17737.94762.94762,0,0,0-.02819-.17419L6.62866,7.50421A2.98961,2.98961,0,0,0,7.64258,6.41992.917.917,0,0,1,8.46,6H11V20H8a1,1,0,0,0,0,2h8a1,1,0,0,0,0-2H13V6h2.54a.917.917,0,0,1,.81738.41992,2.98961,2.98961,0,0,0,1.01392,1.08429L15.064,13.64844a.94762.94762,0,0,0-.02819.17419A.94855.94855,0,0,0,15,14c0,.00928.00269.01782.00275.0271.0003.01318.003.02533.0039.03845a3.99379,3.99379,0,0,0,7.9867,0c.00085-.01312.0036-.02527.0039-.03845C22.99731,14.01782,23,14.00928,23,14A.94855.94855,0,0,0,22.96423,13.82263ZM5,8.85553,6.5564,13H3.4436ZM6.72266,15A2.02306,2.02306,0,0,1,5,16a2.00023,2.00023,0,0,1-1.73145-1ZM19,8.85553,20.5564,13H17.4436ZM19,16a2.00023,2.00023,0,0,1-1.73145-1h3.45411A2.02306,2.02306,0,0,1,19,16Z"
            , Color.DARKGRAY);

    private javafx.scene.control.Label promptTitular = new Label("Aquí se mostrará la información del titular de la reserva");



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //prueba por errores de saldamigo

        information.setLayoutX(248);
        information.setLayoutY(278-10);
        information.setPrefHeight(60);
        information.setPrefWidth(60);
        information.setMaxHeight(60);
        information.setMaxWidth(60);
        anchorBG.getChildren().add(information);

        balance.setLayoutX(248);
        balance.setLayoutY(278+80);
        balance.setPrefHeight(60);
        balance.setPrefWidth(60);
        balance.setMaxHeight(60);
        balance.setMaxWidth(60);
        anchorBG.getChildren().add(balance);

        promptTitular.setPrefHeight(70);
        promptTitular.setPrefWidth(480);
        promptTitular.setTextAlignment(TextAlignment.CENTER);
        promptTitular.setTextFill(Paint.valueOf("#575757"));
        promptTitular.setFont(Font.font("MAXWELL REGULAR",25));
        promptTitular.setAlignment(Pos.CENTER);
        promptTitular.setWrapText(true);
        promptTitular.setLayoutX(30);
        promptTitular.setLayoutY(334-10);
        promptTitular.setVisible(true);
        anchorBG.getChildren().add(promptTitular);

        information.setVisible(true);

        for(Node n: panel_titular.getChildren())
        {
            n.setVisible(false);
        }
    }

    public void ClickBuscar(ActionEvent actionEvent) {
    }

    public void btn_limpiar(ActionEvent actionEvent) {
    }
}
