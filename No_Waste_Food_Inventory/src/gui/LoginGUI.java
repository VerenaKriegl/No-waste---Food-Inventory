
package gui;

import beans.User;
import dialogs.SignUpDlg;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import server.Client;


public class LoginGUI extends JFrame {
    
    private JTextField tfMail;
    private JTextField tfPass;
    private Client client;
    
    public LoginGUI() {
        super("Login");
        this.pack();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(new Dimension(500,700));
        this.setLocationRelativeTo(null);
        
        initComponents();
        
        client = new Client();
    }
    
    private void initComponents() {
        Container container = new Container();
        container.setLayout(new GridLayout(2, 1));
        
        container.add(getLogo());
        container.add(getLogInPanel());
        
        this.add(container);
    }
    
    private JPanel getLogo() {
        JPanel plLogo = new JPanel();
        plLogo.setLayout(new BorderLayout());
        
        ImageIcon iconUser = new ImageIcon("images/user.png");
        
        JLabel lbIcon = new JLabel(iconUser);
        
        plLogo.add(lbIcon, BorderLayout.CENTER);
        
        return plLogo;
    }
    
    private JPanel getLogInPanel() {
        JPanel plLogin = new JPanel();
        plLogin.setLayout(new GridLayout(4, 1));
        
        JLabel lbMail = new JLabel("E-Mail Address");
        JLabel lbPass = new JLabel("Password");
        
        tfMail = new JTextField();
        tfPass = new JTextField();
        
        JPanel plMail = new JPanel();
        plMail.setLayout(new GridLayout(1,2));

        plMail.add(lbMail);
        plMail.add(tfMail);
        
        JPanel plPass = new JPanel();
        plPass.setLayout(new GridLayout(1,2));
        
        plPass.add(lbPass);
        plPass.add(tfPass);

        JButton btLogIn = new JButton("Log in");
        btLogIn.addActionListener(e -> onLogIn());
        JButton btSignUp = new JButton("SignUp");
        btSignUp.addActionListener(e -> onSignUp());
     
        plLogin.add(plMail);
        plLogin.add(plPass);
        plLogin.add(btLogIn);
        plLogin.add(btSignUp);
        
        return plLogin;
    }
    
    private void onLogIn() {
        String mail = tfMail.getText();
        String pass = tfPass.getText();
        if(mail.equals("") || pass.equals("")) {
            JOptionPane.showMessageDialog(null, "You have to fill all the fields!");
        } else {
            try {
                client.loginClient(mail, pass);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(LoginGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void onSignUp() {
        SignUpDlg sdlg = new SignUpDlg(this, true);
        sdlg.setVisible(true);
        if(sdlg.isOK()) {
            User newUser = sdlg.getUser();
            
        }
    }
    
    public static void main(String[] args) {
        new LoginGUI().setVisible(true);
    }
}
