package dialogs;

import beans.User;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.sql.Date;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;

public class SignUpDlg extends JDialog {

    private JTextField tfUsername;
    private JTextField tfPass;
    private JSpinner dateOfBirth;
    private boolean ok;

    public SignUpDlg(java.awt.Frame parent, boolean modal) {
        super(parent, modal);

        this.pack();
        this.setTitle("Add product");
        this.setSize(new Dimension(400, 500));

        initComponents();

        ok = false;
    }

    private void initComponents() {
        Container container = new Container();
        container.setLayout(new BorderLayout());

        container.add(getSignUpMenu(), BorderLayout.CENTER);

        this.add(container);
    }

    private JPanel getSignUpMenu() {
        JPanel plMenu = new JPanel();
        plMenu.setLayout(new GridLayout(4, 2));

        JLabel lbUsername = new JLabel("Username:");
        tfUsername = new JTextField();
        plMenu.add(lbUsername);
        plMenu.add(tfUsername);

        JLabel lbPass = new JLabel("Password:");
        tfPass = new JTextField();
        plMenu.add(lbPass);
        plMenu.add(tfPass);

        JLabel lbDateOfBirth = new JLabel("Date of birth:");
        dateOfBirth = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor d = new JSpinner.DateEditor(dateOfBirth, "dd.MM.yyyy");
        dateOfBirth.setEditor(d);
        plMenu.add(lbDateOfBirth);
        plMenu.add(dateOfBirth);

        JButton btAdd = new JButton("Sign up");
        btAdd.addActionListener(e -> onSignUp());

        JButton btCancel = new JButton("Cancel");
        btCancel.addActionListener(e -> onCancel());

        plMenu.add(btAdd);
        plMenu.add(btCancel);

        return plMenu;
    }

    private void onSignUp() {
        ok = true;
        setVisible(false);
    }

    private void onCancel() {
        ok = false;
        setVisible(false);
    }

    public User getUser() {  
        Date date = (java.sql.Date) dateOfBirth.getValue();
        User newUser = new User(tfUsername.getText(), tfPass.getText(), date);
        return newUser;
    }

    public Boolean isOK() {
        return ok;
    }
}
