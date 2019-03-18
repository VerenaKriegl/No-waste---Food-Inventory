/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prodottilatteriamvc;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import prodottilatteria.model.ProdottiLatteriaModel;

/**
 *
 * @author hale
 */
public class ProdottiLatteriaMVC extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ProdottiLatteria.fxml"));
        Parent root = loader.load();
        
        Scene scene = new Scene(root);
        
        ProdottiLatteriaModel model = new ProdottiLatteriaModel();
        ((ProdottiLatteriaController)loader.getController()).setModel(model);
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
