package server;

import beans.User;
import gui.LoginGUI;
import gui.MenuGUI;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Client 
{
    private Socket socket;
    public Client()
    {
        try {
            InetAddress ia = InetAddress.getLocalHost();
            socket = new Socket(ia, 9999);
        } catch (UnknownHostException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void main(String[] args) {
        new Client();
    }
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    public void loginClient(String userName, String password, LoginGUI loginGUI) throws ClassNotFoundException
    {
        try {
            ois = new ObjectInputStream(socket.getInputStream());
            oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject("Anmelden");
            oos.flush();
            
            oos.writeObject(userName);
            oos.flush();
            
            oos.writeObject(password);
            oos.flush();
            
            String line = (String)ois.readObject();
            if(line.contains("Willkommen"))
            {
                System.out.println(line);
                loginGUI.setVisible(false);
                MenuGUI menuGUI = new MenuGUI();
                menuGUI.setVisible(true);
            }
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private User user;
    public void signUP(User user, LoginGUI loginGUI)
    {
        try {
            this.user = user;
            ois = new ObjectInputStream(socket.getInputStream());
            oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject("Registrieren");
            oos.flush();
            
            oos.writeObject(user);
            oos.flush();
            
            String line = (String) ois.readObject();
            if(line.contains("Willkommen"))
            {
                loginGUI.setVisible(false);
                MenuGUI menuGUI = new MenuGUI();
                menuGUI.setVisible(true);
            }
            else
            {
                JOptionPane.showMessageDialog(loginGUI, "Registrierung fehlgeschlagen!");
                System.out.println("Fehler");
            }
            
            
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
