package model;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class TableCellRenderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component comp = 
                super.getTableCellRendererComponent
                (table, value, isSelected, hasFocus, row, column);

        int col = table.convertColumnIndexToModel(column);

        if (col == table.getColumnCount() - 1) {
            int daysTillExpirationDate = (Integer) value;
            
            if (daysTillExpirationDate < 3) {
                setBackground(Color.RED);
            } else if (daysTillExpirationDate < 5 && daysTillExpirationDate >= 3) {
                setBackground(Color.ORANGE);
            } else if (daysTillExpirationDate <= 8 && daysTillExpirationDate >= 5) {
                setBackground(Color.YELLOW);
            } else {
                setBackground(Color.GREEN);
            }
        } else {
            setBackground(table.getBackground());
        }
        return comp;
    }
}
