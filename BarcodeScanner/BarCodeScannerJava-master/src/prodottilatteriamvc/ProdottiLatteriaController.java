/**
 * Sample Skeleton for 'ProdottiLatteria.fxml' Controller Class
 */
package prodottilatteriamvc;

import java.io.IOException;
import javafx.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import prodottilatteria.model.ProdottiLatteriaModel;
import prodottilatteria.model.Product;

public class ProdottiLatteriaController {

    private ProdottiLatteriaModel model;
    private Stage secondaryStage;
    Product product;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="lblCodiceABarre"
    private Label lblCodiceABarre; // Value injected by FXMLLoader

    @FXML // fx:id="lblMarca"
    private Label lblMarca; // Value injected by FXMLLoader

    @FXML // fx:id="lblNome"
    private Label lblNome; // Value injected by FXMLLoader

    @FXML // fx:id="lblPrezzoAcquisto"
    private Label lblPrezzoAcquisto; // Value injected by FXMLLoader

    @FXML // fx:id="lblPrezzoVendita"
    private Label lblPrezzoVendita; // Value injected by FXMLLoader

    @FXML // fx:id="lblBC"
    private Label lblBC; // Value injected by FXMLLoader

    @FXML // fx:id="txtMarca"
    private TextField txtMarca; // Value injected by FXMLLoader

    @FXML // fx:id="txtNome"
    private TextField txtNome; // Value injected by FXMLLoader

    @FXML // fx:id="txtPrezzoAcquisto"
    private TextField txtPrezzoAcquisto; // Value injected by FXMLLoader

    @FXML // fx:id="txtPrezzoVendita"
    private TextField txtPrezzoVendita; // Value injected by FXMLLoader

    @FXML // fx:id="btnAggiungiProdotto"
    private Button btnAggiungiProdotto; // Value injected by FXMLLoader

    @FXML // fx:id="btnPulisciCampi"
    private Button btnPulisciCampi; // Value injected by FXMLLoader

    @FXML // fx:id="btnScansionaBC"
    private Button btnScansionaBC; // Value injected by FXMLLoader

    @FXML
    private void handlePulisciCampi(ActionEvent event) {
        lblBC.setText("");
        txtMarca.setText("");
        txtNome.setText("");
        txtPrezzoAcquisto.setText("");
        txtPrezzoVendita.setText("");
    }

    @FXML
    private void handleAggiungiProdotto(ActionEvent event) {
        if (!"".equals(txtMarca.getText())
                && !"".equals(txtNome.getText())
                && !"".equals(txtPrezzoAcquisto.getText())
                && !"".equals(txtPrezzoVendita.getText())) {
            
            if (product == null) {
                product = new Product(0, lblBC.getText(), txtMarca.getText(), txtNome.getText(), txtPrezzoAcquisto.getText(), txtPrezzoVendita.getText());
            };
            
            product.setbc(lblBC.getText());
            product.setmarca(txtMarca.getText());
            product.setnome(txtNome.getText());
            product.setprezzoa(txtPrezzoAcquisto.getText());
            product.setprezzov(txtPrezzoVendita.getText());
            
            model.insertProductInDB(product);
        };
        
        lblBC.setText("");
        txtMarca.setText("");
        txtNome.setText("");
        txtPrezzoAcquisto.setText("");
        txtPrezzoVendita.setText("");
        openScanWindow();
        getIfExist();

    }

    @FXML
    void handleScansionaBC(ActionEvent event) {
        openScanWindow();
        getIfExist();

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert lblCodiceABarre != null : "fx:id=\"lblCodiceABarre\" was not injected: check your FXML file 'ProdottiLatteria.fxml'.";
        assert lblMarca != null : "fx:id=\"lblMarca\" was not injected: check your FXML file 'ProdottiLatteria.fxml'.";
        assert lblNome != null : "fx:id=\"lblNome\" was not injected: check your FXML file 'ProdottiLatteria.fxml'.";
        assert lblPrezzoAcquisto != null : "fx:id=\"lblPrezzoAcquisto\" was not injected: check your FXML file 'ProdottiLatteria.fxml'.";
        assert lblPrezzoVendita != null : "fx:id=\"lblPrezzoVendita\" was not injected: check your FXML file 'ProdottiLatteria.fxml'.";
        assert lblBC != null : "fx:id=\"lblBC\" was not injected: check your FXML file 'ProdottiLatteria.fxml'.";
        assert txtMarca != null : "fx:id=\"txtMarca\" was not injected: check your FXML file 'ProdottiLatteria.fxml'.";
        assert txtNome != null : "fx:id=\"txtNome\" was not injected: check your FXML file 'ProdottiLatteria.fxml'.";
        assert txtPrezzoAcquisto != null : "fx:id=\"txtPrezzoAcquisto\" was not injected: check your FXML file 'ProdottiLatteria.fxml'.";
        assert txtPrezzoVendita != null : "fx:id=\"txtPrezzoVendita\" was not injected: check your FXML file 'ProdottiLatteria.fxml'.";
        assert btnScansionaBC != null : "fx:id=\"btnScansionaBC\" was not injected: check your FXML file 'ProdottiLatteria.fxml'.";
        assert btnPulisciCampi != null : "fx:id=\"btnPulisciCampi\" was not injected: check your FXML file 'ProdottiLatteria.fxml'.";
        assert btnAggiungiProdotto != null : "fx:id=\"btnAggiungiProdotto\" was not injected: check your FXML file 'ProdottiLatteria.fxml'.";

    }

    public void setModel(ProdottiLatteriaModel model) {
        this.model = model;

        //aggiorna automaticamente le textbox in base ai dati letti dal DB
        lblBC.textProperty().bindBidirectional(model.getScannedBCProperty());
    }

    private void openScanWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ScanBC.fxml"));
        Parent root;
        try {
            secondaryStage = new Stage();
            root = loader.load();
            Scene scene = new Scene(root);

            ((ScanBCController) loader.getController()).setModel(model);

            secondaryStage.setScene(scene);
            secondaryStage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(ProdottiLatteriaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void getIfExist() {
        model.getProductFromDB();
        product = model.getProductFromDB();
        if (product != null) {
            txtMarca.setText(product.getmarca());
            txtNome.setText(product.getnome());
            txtPrezzoAcquisto.setText(product.getprezzoa());
            txtPrezzoVendita.setText(product.getprezzov());
        }
    }
}
