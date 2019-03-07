package client;

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
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class Client extends JFrame {

    private Container container;
    private String line;
    private PrintWriter pw;

    public Client(String title) {
        super(title);
        initComponents();
    }

    private void initComponents() {
        container = this.getContentPane();
        container.setLayout(new BorderLayout());
        initMenu();
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2));

        JTextArea areaTeilnehmer = new JTextArea();
        TitledBorder tbTeilnehmer = new TitledBorder("Teilnehmer");
        areaTeilnehmer.setBorder(tbTeilnehmer);

        JTextArea areaAktionen = new JTextArea();
        TitledBorder tbAktionen = new TitledBorder("Aktionen");
        areaAktionen.setBorder(tbAktionen);

        panel.add(areaAktionen);
        panel.add(areaTeilnehmer);

        container.add(panel, BorderLayout.CENTER);
        container.add(initMessages(), BorderLayout.SOUTH);

    }

    private void initMenu() {
        JMenu menu = new JMenu("Optionen");
        JMenuBar menubar = new JMenuBar();
        JMenuItem connect = new JMenuItem("connect");
        JMenuItem disconnect = new JMenuItem("disconnect");

        menu.add(connect);
        menu.add(disconnect);
        menubar.add(menu);
        this.setJMenuBar(menubar);

        connect.addActionListener(e -> {
            try {
                onConnect(e);
            } catch (UnknownHostException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
        disconnect.addActionListener(e -> onDisconnect(e));
    }

    private void onConnect(ActionEvent e) throws UnknownHostException {
        InetAddress addr = InetAddress.getLocalHost();
        Socket socket;
        BufferedReader br;
        try {
            socket = new Socket(addr, 9999);
            OutputStream os = socket.getOutputStream();
            pw = new PrintWriter(os);
            InputStream is = socket.getInputStream();
            br = new BufferedReader(new InputStreamReader(is));
            ClientThread ct = new ClientThread(br);
            Thread thread = new Thread(ct);
            thread.start();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Es muss zuerst der Server gestartet werden!");
        }

        line = "connected";
        pw.println(line);
        pw.flush();
        System.out.println("Client finished!");
    }

    private void onDisconnect(ActionEvent e) {
        line = "disconnected";
        pw.println(line);
        pw.flush();
    }

    private JPanel initMessages() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 3));
        JLabel labelMessages = new JLabel("Messages: ");
        JTextField tf = new JTextField();
        JButton bt = new JButton("Send");
        bt.addActionListener(e -> onSend(tf));
        panel.add(labelMessages);
        panel.add(tf);
        panel.add(bt);
        return panel;
    }

    private void onSend(JTextField tf) {
        String message = tf.getText();
        pw.println(message);
        pw.flush();
    }

    public static void main(String[] args) {
        Client gui = new Client("Client");
        gui.setVisible(true);
        gui.setDefaultCloseOperation(EXIT_ON_CLOSE);
        gui.setLocationRelativeTo(null);
        gui.setSize(500, 500);
    }
}
