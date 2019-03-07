package server;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Server extends JFrame {

    private Container container;
    private JTextArea area;

    public Server(String title) {
        super(title);
        initComponents();
    }

    private void initComponents() {
        container = this.getContentPane();
        container.setLayout(new BorderLayout());
        area = new JTextArea();
        container.add(area, BorderLayout.CENTER);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2));
        JButton startBt = new JButton("Start Server");
        startBt.addActionListener(e -> onStartServer());
        JButton endBt = new JButton("End Server");
        endBt.addActionListener(e -> onEndServer());
        panel.add(startBt);
        panel.add(endBt);

        container.add(panel, BorderLayout.SOUTH);
    }

    private void onEndServer() {
        setVisible(false);
        dispose();
    }

    private void onStartServer() {
        try {
            communicate();
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        Server gui = new Server("Server");
        gui.setVisible(true);
        gui.setDefaultCloseOperation(EXIT_ON_CLOSE);
        gui.setLocationRelativeTo(null);
        gui.setSize(500, 500);
    }

    private static final int PORT_NR = 9999; //Port Nummer muss auf Client und Server Seite die gleiche sein

    public void communicate() throws IOException {
        ServerSocket server = new ServerSocket(PORT_NR);
        System.out.println("Server wartet am Port: " + PORT_NR);
        Socket socket = server.accept(); //Wartet im accept solange bis sich ein Client verbunden hat
        System.out.println(socket.getRemoteSocketAddress().toString());

        InputStream is = socket.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        OutputStream os = socket.getOutputStream();
        PrintWriter pw = new PrintWriter(os);

        String line = "";
        do {
            line = br.readLine(); //Wartet auf einen Zeilenumbruch
            System.out.println(line);
            area.append(line + "\n");
            area.updateUI();
            pw.println(line);
            pw.flush();
        } while (!line.equals("disconnected"));

        pw.close();
        br.close();
        socket.close();
        server.close();
        System.out.println("Server ist fertig!");
    }
}
