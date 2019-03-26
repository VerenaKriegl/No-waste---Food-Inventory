package server;

import beans.Product;
import beans.User;
import database.DBAccess;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {

    private Map<String, ArrayList<Product>> userMap = new HashMap();

    public Server() throws IOException {
        ServerSocket serverSocket = new ServerSocket(9999);
        ClientConnection clientConnection = new ClientConnection(serverSocket);
        clientConnection.start();
    }

    class ClientConnection extends Thread {

        private ServerSocket serverSocket;

        public ClientConnection(ServerSocket ss) {
            this.serverSocket = ss;
        }

        @Override
        public void run() {
            try {
                System.out.println("Server wartet");
                while (true) {
                    Socket s = serverSocket.accept();
                    System.out.println("Connected mit: " + s.getRemoteSocketAddress());
                    ClientCommunication clientCommunication = new ClientCommunication(s);
                    clientCommunication.start();
                }
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    class ClientCommunication extends Thread {

        private DBAccess dbAccess;
        private Socket socket;
        private ObjectInputStream ois;
        private ObjectOutputStream oos;

        public ClientCommunication(Socket s) {
            this.socket = s;
            try {
                oos = new ObjectOutputStream(s.getOutputStream());
                ois = new ObjectInputStream(s.getInputStream());
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        @Override
        public void run() {
            try {
                String username = null;
                dbAccess = new DBAccess();
                String line = (String) ois.readObject();
                ArrayList<String> userNames = dbAccess.showUser();
                if (line.contains("Anmelden")) {
                    username = (String) ois.readObject();
                    String password = dbAccess.getPassword(username);
                    line = (String) ois.readObject();
                    if (password.equals(line)) {
                        oos.writeObject("Willkommen, " + username);
                        oos.flush();
                    } else {
                        oos.writeObject("Falsches Password");
                        oos.flush();
                    }
                } else {
                    ArrayList<Product> productList = new ArrayList<>();
                    User user = (User) ois.readObject();
                    username = user.getUserName();
                    if (!userNames.contains(user.getUserName())) {
                        dbAccess.insertUser(user);
                        userMap.put(line, productList);
                        oos.writeObject("Herzlich Willkommen in unserer Community, " + user.getUserName());
                        oos.flush();
                    } else {
                        oos.writeObject("Registrieren fehlgeschlagen");
                        oos.flush();
                    }
                }
                ClientProducts clientProducts = new ClientProducts(oos, ois, dbAccess, username);
                clientProducts.start();

            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    private void log(String message) {
        System.out.println(message);
    }

    class ClientProducts extends Thread {

        ObjectInputStream ois;
        ObjectOutputStream oos;
        String username;
        DBAccess dba;

        public ClientProducts(ObjectOutputStream oos, ObjectInputStream ois, DBAccess dba, String username) {
            this.ois = ois;
            this.oos = oos;
            this.username = username;
            this.dba = dba;
        }

        @Override
        public void run() {
            ArrayList<Product> listProduct = new ArrayList<>();
            try {
                listProduct = dba.showAllProducts(username);
                oos.writeObject(listProduct);
                oos.flush();

                while (true) {
                    String line = (String) ois.readObject();
                    if (line.equals("hinzufuegen")) {
                        Product product = (Product) ois.readObject();
                        dba.insertProduct(product, username);
                        oos.writeObject(product);
                        oos.flush();
                    }
                    else
                    {
                        Product product = (Product)ois.readObject();
                        dba.deleteEntries(product.getProductNr(), username);
                    }
                }
            } catch (Exception ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
}
