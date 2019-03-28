
package main;

import gui.LoginGUI;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.Server;


public class Main {
    public static void main(String[] args) {
        try {
            new Server();
            new LoginGUI().setVisible(true);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
