package dialogs;

import beans.Product;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;

public class AddProductDlg extends JDialog
{

    private JTextField tfProductNr;
    private JTextField tfCategory;
    private JTextField tfProductName;
    private JSpinner spExpirationDate;
    private DateTimeFormatter dateTimeFormatter;
    private LocalDate localdate;
    private Product product;
    private boolean ok;

    public AddProductDlg(java.awt.Frame parent, boolean modal)
    {
        super(parent, modal);
        this.setTitle("Add product");
        this.setSize(new Dimension(500, 600));

        initComponents();

        this.pack();

        ok = false;
    }

    private void initComponents()
    {
        Container con = new Container();
        con.setLayout(new BorderLayout());

        con.add(getAddingMenu(), BorderLayout.CENTER);

        this.add(con);
    }

    private JPanel getAddingMenu()
    {
        JPanel panelMenu = new JPanel();
        panelMenu.setLayout(new GridLayout(5, 2));

        JLabel lbProductNumber = new JLabel("Product number:");
        tfProductNr = new JTextField();
        panelMenu.add(lbProductNumber);
        panelMenu.add(tfProductNr);

        JLabel lbExpirationDate = new JLabel("Expiration date:");
        panelMenu.add(lbExpirationDate);

        spExpirationDate = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor d = new JSpinner.DateEditor(spExpirationDate, "dd.MM.yyyy");
        spExpirationDate.setEditor(d);
        panelMenu.add(spExpirationDate);

        JLabel lbCategory = new JLabel("Category:");
        tfCategory = new JTextField();
        panelMenu.add(lbCategory);
        panelMenu.add(tfCategory);

        JLabel lbProductName = new JLabel("Product name:");
        tfProductName = new JTextField();
        panelMenu.add(lbProductName);
        panelMenu.add(tfProductName);

        JButton btAdd = new JButton("Add");
        btAdd.addActionListener(e -> onAdd());

        JButton btCancel = new JButton("Cancel");
        btCancel.addActionListener(e -> onCancel());

        panelMenu.add(btAdd);
        panelMenu.add(btCancel);

        return panelMenu;
    }

    private void onAdd()
    {
        ok = true;
        setVisible(false);
    }

    private void onCancel()
    {
        ok = false;
        setVisible(false);
    }

    public Product getProduct()
    {
        Date d = (Date) spExpirationDate.getValue();
        Instant i = d.toInstant();
        ZonedDateTime zdt = i.atZone(ZoneId.systemDefault());
        localdate = zdt.toLocalDate();

        dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        product = new Product(tfProductName.getText(),
                localdate,
                tfCategory.getText(), Integer.parseInt(tfProductNr.getText()), false);

        return product;
    }

    public boolean isOK()
    {
        return ok;
    }
}
