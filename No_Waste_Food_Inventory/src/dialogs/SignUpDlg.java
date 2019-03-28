package dialogs;

import beans.User;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.text.ParseException;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;

public class SignUpDlg extends JDialog {

    private JTextField tfUsername;
    private JPasswordField pfPass;
    private JPasswordField pfConfirmPass;
    private JSpinner dateOfBirth;
    private boolean ok;

    public SignUpDlg(java.awt.Frame parent, boolean modal) {
        super(parent, modal);

        this.pack();
        this.setTitle("Sign Up");
        this.setSize(new Dimension(300, 350));
        this.setLocationRelativeTo(null);

        initComponents();

        ok = false;
    }

    private void initComponents() {
        Container container = new Container();
        container.setLayout(new BorderLayout());

        container.add(getSignUpMenu());

        this.add(container);
    }

    private JPanel getSignUpMenu() {
        JPanel plMenu = new JPanel();
        plMenu.setLayout(new GridLayout(5, 2));

        JLabel lbUsername = new JLabel("Username:");
        tfUsername = new JTextField();
        plMenu.add(lbUsername);
        plMenu.add(tfUsername);

        JLabel lbPass = new JLabel("Password:");
        pfPass = new JPasswordField();
        plMenu.add(lbPass);
        plMenu.add(pfPass);
        
        JLabel lbConfirmPass = new JLabel("Confirm password:");
        pfConfirmPass = new JPasswordField();
        plMenu.add(lbConfirmPass);
        plMenu.add(pfConfirmPass);

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
        if(pfPass.getText().equals(pfConfirmPass.getText())) {
            ok = true;
            setVisible(false);
        } else {
            JOptionPane.showMessageDialog(null, "Password doesn't match");
        }
    }

    private void onCancel() {
        ok = false;
        setVisible(false);
    }

    public User getUser() throws ParseException {
        java.util.Date date = (java.util.Date)dateOfBirth.getValue();
        System.out.println(date);
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        User newUser = new User(tfUsername.getText(), pfPass.getText(), sqlDate);
        return newUser;
    }

    public Boolean isOK() {
        return ok;
    }
}
