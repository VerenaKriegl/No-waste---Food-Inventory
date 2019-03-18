/**
 * Sample Skeleton for 'ScanBC.fxml' Controller Class
 */

package prodottilatteriamvc;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import prodottilatteria.model.ProdottiLatteriaModel;
import javafx.scene.input.KeyEvent;

public class ScanBCController {
    
    private ProdottiLatteriaModel model;
    String currentBC = "";

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="lblScanBC"
    private Label lblScanBC; // Value injected by FXMLLoader

    @FXML // fx:id="btnChiudi"
    private Button btnChiudi; // Value injected by FXMLLoader
    
    @FXML
    void keyPressed(KeyEvent event) {
        //controlla se il carattere acquisito è quello di invio (carattere di terminazione del BC)
        if (event.getCode().equals(KeyCode.ENTER)) {
            //passa il BC scansionato al model
            model.setBC(currentBC);
            //chiude la finestra
            Stage secondaryStage = (Stage) lblScanBC.getScene().getWindow();
            secondaryStage.close();
        }
        else {
            //aggiunge la n-esima cifra del BC alla variabile temporanea
            currentBC += event.getText();
        }
    }

    @FXML
    void closeButtonAction(ActionEvent event) {
        Stage secondaryStage = (Stage) btnChiudi.getScene().getWindow();
        secondaryStage.close();
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert lblScanBC != null : "fx:id=\"lblScanBC\" was not injected: check your FXML file 'ScanBC.fxml'.";
        assert btnChiudi != null : "fx:id=\"btnChiudi\" was not injected: check your FXML file 'ScanBC.fxml'.";

    }
    
    public void setModel(ProdottiLatteriaModel model){
        this.model = model;
    }
}
