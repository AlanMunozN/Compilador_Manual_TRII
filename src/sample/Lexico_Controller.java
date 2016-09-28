package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Kaleb on 9/27/2016.
 */
public class Lexico_Controller{
    Controller ctrl;

    @FXML
    TextArea txtID,txtLinea,txtToken,txtIdentificador;

    public void llenarTablas_Lexico(){
        for (int i=0;i<ctrl.lexico_ID.size();i++)
            txtID.appendText("\n"+Integer.toString(ctrl.lexico_ID.get(i)));
        for (int i=0;i<ctrl.lexico_Linea.size();i++)
            txtLinea.appendText("\n"+Integer.toString(ctrl.lexico_Linea.get(i)));
        for (int i=0;i<ctrl.lexico_Token.size();i++)
            txtToken.appendText("\n"+ctrl.lexico_Token.get(i));
        for (int i=0;i<ctrl.lexico_Identificador.size();i++)
            txtIdentificador.appendText("\n"+ctrl.lexico_Identificador.get(i));
    }
}
