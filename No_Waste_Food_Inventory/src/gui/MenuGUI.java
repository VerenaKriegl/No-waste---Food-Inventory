package gui;

import beans.Product;
import beans.Reminder;
import dialogs.AddProductDlg;
import dialogs.SettingsDlg;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import model.TableCellRenderer;
import model.TableModel;
import server.Client;

public final class MenuGUI extends JFrame
{

    private JTable productTable;
    private JToolBar tbTools;
    private TableModel tableModel;
    private TableCellRenderer tableCellRenderer;
    private ArrayList<Product> products = new ArrayList<>();
    private ArrayList<Reminder> reminders = new ArrayList<>();
    private Client client;

    public MenuGUI(Client client)
    {
        super("Menu");
        this.client = client;
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(500, 700));
        this.setLocationRelativeTo(null);

        initComponents();
        client.setModel(tableModel);
        this.pack();
    }

    public void initComponents()
    {
        Container con = new Container();
        con.setLayout(new BorderLayout());

        JLabel lbTitle = new JLabel("No Waste Food");

        tableModel = new TableModel();
        tableCellRenderer = new TableCellRenderer();

        con.add(lbTitle, BorderLayout.NORTH);
        con.add(getTable(), BorderLayout.CENTER);
        con.add(getToolBar(), BorderLayout.SOUTH);

        this.add(con);
    }

    private JPanel getTable()
    {
        productTable = new JTable();
        productTable.setModel(tableModel);
        productTable.setDefaultRenderer(Object.class, tableCellRenderer);

        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());

        JScrollPane spTable = new JScrollPane();

        JPopupMenu popup = new JPopupMenu();

        JMenuItem miDelete = new JMenuItem("Delete");
        miDelete.addActionListener(e -> onRemoveProduct());
        popup.add(miDelete);

        MouseListener popupListener = new PopupListener(popup);
        productTable.addMouseListener(popupListener);

        spTable.add(productTable);
        spTable.setViewportView(productTable);

        tablePanel.add(spTable);

        return tablePanel;
    }

    private JPanel getToolBar()
    {
        tbTools = new JToolBar();

        Button btAddProduct = new Button("Add product");
        btAddProduct.addActionListener(e -> onAddProduct());

        Button btSettings = new Button("Settings");
        btSettings.addActionListener(e -> onOpenSettings());

        tbTools.add(btAddProduct);
        tbTools.add(btSettings);

        JPanel panelTools = new JPanel();
        panelTools.setLayout(new BorderLayout());

        panelTools.add(tbTools);

        return panelTools;
    }

    private void onAddProduct()
    {
        AddProductDlg addDlg = new AddProductDlg(this, true);
        addDlg.setVisible(true);

        if (addDlg.isOK())
        {
            
            Product newProduct = addDlg.getProduct();
            client.addProduct(newProduct);
            products.add(newProduct);
        }
    }

    private void onRemoveProduct()
    {
        int indexOfSelectedProduct = productTable.getSelectedRow();

        if (indexOfSelectedProduct > -1)
        {
            tableModel.removeProductAtIndex(indexOfSelectedProduct);
        }
    }

    private void onOpenSettings()
    {
        SettingsDlg settingsDlg = new SettingsDlg(this, true, products);
        settingsDlg.setVisible(true);

        if (settingsDlg.isOK())
        {
            Reminder reminder = settingsDlg.getReminder();
            reminders.add(reminder);
            for (Product product : products)
            {
                if (product.getProductName().contains(reminder.getProductName()))
                {
                    product.setReminder(true);
                    tableModel.modify();
                }
            }
        }
    }


    class PopupListener extends MouseAdapter
    {

        JPopupMenu popup;

        PopupListener(JPopupMenu popupMenu)
        {
            popup = popupMenu;
        }

        public void mousePressed(MouseEvent e)
        {
            maybeShowPopup(e);
        }

        public void mouseReleased(MouseEvent e)
        {
            maybeShowPopup(e);
        }

        private void maybeShowPopup(MouseEvent e)
        {
            if (e.isPopupTrigger())
            {
                popup.show(e.getComponent(),
                        e.getX(), e.getY());
            }
        }
    }
}
