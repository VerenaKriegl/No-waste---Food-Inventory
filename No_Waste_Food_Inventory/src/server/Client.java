package server;

import beans.Product;
import beans.User;
import gui.LoginGUI;
import gui.MenuGUI;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.TableModel;

public class Client 
{
    
   
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    public void loginClient(String userName, String password, LoginGUI loginGUI) throws ClassNotFoundException
    {
        try {
            InetAddress ia = InetAddress.getLocalHost();
            Socket socket = new Socket(ia, 9999);
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
                menu = new MenuGUI(this);
                menu.setVisible(true);
            }
            else
            {
                JOptionPane.showMessageDialog(loginGUI, "Login fehlgeschlagen");
            }
            ServerCommunication sc = new ServerCommunication();
            sc.start();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private User user;
    public void signUP(User user, LoginGUI loginGUI)
    {
        try {
            InetAddress ia = InetAddress.getLocalHost();
            Socket socket = new Socket(ia, 9999);
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
                menu = new MenuGUI(this);
                menu.setVisible(true);
            }
            else
            {
                JOptionPane.showMessageDialog(loginGUI, "Registrierung fehlgeschlagen!");
                System.out.println("Fehler");
            }
            ServerCommunication sc = new ServerCommunication();
            sc.start();
            
            
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    private MenuGUI menu;

    private TableModel model;
    public void addProduct(Product product) 
    {
        try {
            oos.writeObject(product);
            oos.flush();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    public void setModel(TableModel m)
    {
        this.model = m;
    }
    class ServerCommunication extends Thread
    {

        @Override
        public void run() {
            try {
                ArrayList<Product> listProduct = (ArrayList)ois.readObject();
                for(Product p : listProduct)
                {
//                    model.add(p);
                }
                System.out.println("hier");
                System.out.println(listProduct.get(0).getProductName());
            
                while(true)
                {
                    Product p = (Product)ois.readObject();
                    model.add(p);
                }
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
}
