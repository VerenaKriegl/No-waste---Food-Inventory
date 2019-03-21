package server;

import beans.User;
import gui.MenuGUI;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    public void loginClient(String userName, String password) throws ClassNotFoundException
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
                MenuGUI MenuGUI = new MenuGUI();
                MenuGUI.setVisible(true);
            }
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private User user;
    public void registrationClient(User user)
    {
        this.user = user;
    }
}
