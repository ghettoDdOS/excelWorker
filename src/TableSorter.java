import java.awt.BorderLayout;
import java.util.Locale;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.table.TableStringConverter;

public class TableSorter extends JPanel {
    private JTable jTable = new JTable(DataModel.getTableModel());
    private TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(jTable.getModel());
    private JTextField jtfFilter = new JTextField();
    public TableSorter() {
        jTable.setRowSorter(rowSorter);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("Поисковой запрос:"), BorderLayout.WEST);
        panel.add(jtfFilter, BorderLayout.CENTER);
        setLayout(new BorderLayout());
        add(panel, BorderLayout.SOUTH);
        add(new JScrollPane(jTable), BorderLayout.CENTER);
        jtfFilter.getDocument().addDocumentListener(new DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = jtfFilter.getText().toLowerCase();
                if (text.trim().length() == 0)
                    rowSorter.setRowFilter(null);
                else
                    rowSorter.setStringConverter(new TableStringConverter() {
                        @Override
                        public String toString(TableModel model, int row, int column) {
                            return model.getValueAt(row, column).toString().toLowerCase();
                        }
                    });
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = jtfFilter.getText();
                if (text.trim().length() == 0)
                    rowSorter.setRowFilter(null);
                else
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                throw new UnsupportedOperationException("Ошибка");
            }

        });
    }
}