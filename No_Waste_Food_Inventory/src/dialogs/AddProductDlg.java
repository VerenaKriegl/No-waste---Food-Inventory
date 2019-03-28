package dialogs;

import beans.Product;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;

public class AddProductDlg extends JDialog {

    private JTextField tfProductNr;
    private JComboBox cbCategory;
    private JTextField tfProductName;
    private JSpinner spExpirationDate;
    private Date expDate;
    private Product product;
    private boolean ok;
    private ArrayList<String> categories;
    private File file;

    public AddProductDlg(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        this.setTitle("Add product");
        this.setSize(new Dimension(500, 600));
        
        file = new File("src/beans/Category.csv");
        categories = getCategoriesFromFile(file);
        
        initComponents();

        this.pack();

        ok = false;
    }

    private void initComponents() {
        Container con = new Container();
        con.setLayout(new BorderLayout());

        con.add(getAddingMenu(), BorderLayout.CENTER);

        this.add(con);
    }

    private JPanel getAddingMenu() {
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
        cbCategory = new JComboBox();
        for(String category : categories) {
            cbCategory.addItem(category);
        }
        panelMenu.add(lbCategory);
        panelMenu.add(cbCategory);

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

    private void onAdd() {
        ok = true;
        setVisible(false);
    }

    private void onCancel() {
        ok = false;
        setVisible(false);
    }
  
    public Product getProduct() {        
        java.util.Date date = (java.util.Date)spExpirationDate.getValue();
        System.out.println(date);
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());

        product = new Product(tfProductName.getText(), sqlDate,
                cbCategory.getSelectedItem().toString(),
                Integer.parseInt(tfProductNr.getText()), false);

        return product;
    }

    public boolean isOK() {
        return ok;
    }
    
    public ArrayList<String> getCategoriesFromFile(File file) {
        try {
            InputStream is = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            
            String line = "";
            
            while((line = br.readLine()) != null) {
                if(!categories.contains(line)) {
                    categories.add(line);
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("file not found");
        } catch (UnsupportedEncodingException ex) {
            System.out.println("error");
        } catch (IOException ex) {
            System.out.println("error");
        }

        return categories;
    }
}
