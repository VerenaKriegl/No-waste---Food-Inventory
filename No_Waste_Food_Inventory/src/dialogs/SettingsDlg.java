package dialogs;

import beans.Product;
import beans.Reminder;
import java.awt.Button;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class SettingsDlg extends JDialog
{

    private JLabel lbLanguage;
    private JComboBox cbxLangs;
    private JLabel lbReminder;
    private JComboBox cbxProducts;
    private JSpinner spCounter;
    private JComboBox cbxUnit;
    private boolean ok;
    private ArrayList<Product> products;

    public SettingsDlg(java.awt.Frame parent, boolean modal, ArrayList<Product> products)
    {
        super(parent, modal);
        this.pack();
        this.setTitle("Settings");
        this.setSize(new Dimension(400, 100));
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.products = products;
        initComponents();

        ok = false;
    }

    private void initComponents()
    {
        Container container = new Container();
        container.setLayout(new GridLayout(3, 1));

        container.add(getLanguagePanel());
        container.add(getReminderPanel());
        container.add(getOperations());

        this.add(container);
    }

    private JPanel getLanguagePanel()
    {
        JPanel panelLang = new JPanel();
        panelLang.setLayout(new GridLayout());

        lbLanguage = new JLabel("Language: ");

        cbxLangs = new JComboBox(new String[]
        {
            "German", "English", "Italiano"
        });

        panelLang.add(lbLanguage);
        panelLang.add(cbxLangs);

        return panelLang;
    }

    private JPanel getReminderPanel()
    {
        JPanel panelReminder = new JPanel();
        panelReminder.setLayout(new GridLayout(1, 3));

        lbReminder = new JLabel("Remind me: ");

        cbxProducts = new JComboBox();
        cbxProducts.setModel(new DefaultComboBoxModel(products.toArray()));
        cbxProducts.removeAllItems();
        for (Product product : products)
        {
            cbxProducts.addItem(product.getProductName());
        }

        spCounter = new JSpinner();
        spCounter.setModel(new SpinnerNumberModel(0, 0, 15, 1));

        cbxUnit = new JComboBox(new String[]
        {
            "Days", "Weeks"
        });

        panelReminder.add(lbReminder);
        panelReminder.add(cbxProducts);
        panelReminder.add(spCounter);
        panelReminder.add(cbxUnit);

        return panelReminder;
    }

    private JPanel getOperations()
    {
        JPanel panelOperations = new JPanel();
        panelOperations.setLayout(new GridLayout(1, 2));

        Button btOK = new Button("OK");
        btOK.addActionListener(e -> onOK());

        Button btCancel = new Button("Cancel");
        btCancel.addActionListener(e -> onCancel());

        panelOperations.add(btOK);
        panelOperations.add(btCancel);

        return panelOperations;
    }

    private void onOK()
    {
        ok = true;
        this.setVisible(false);
    }

    public Reminder getReminder()
    {
        return new Reminder((int) spCounter.getValue(), cbxProducts.getSelectedItem().toString(), cbxUnit.getSelectedItem().toString());
    }

    private void onCancel()
    {
        ok = false;
        this.setVisible(false);
    }

    public boolean isOK()
    {
        return ok;
    }
}
