import java.awt.*;
import java.util.Enumeration;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.table.TableStringConverter;

public class TableSorter extends JPanel {
    private JTable jTable = new JTable(DataModel.getTableModel());
    private TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(jTable.getModel());
    private JTextField jtfFilter = new JTextField();
    public String getSelectedButtonText(ButtonGroup buttonGroup) {
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();

            if (button.isSelected()) {
                return button.getText();
            }
        }

        return null;
    }
    public TableSorter() {

        jTable.setRowSorter(rowSorter);
        JPanel panel = new JPanel(new BorderLayout());
        JPanel filtersPanel = new JPanel(new GridLayout());
        JRadioButton byCity = new JRadioButton("По городу");
        byCity.setSelected(true);
        JRadioButton bySight = new JRadioButton("По достопримечательности");
        JRadioButton byPeoples = new JRadioButton("По населению");
        JRadioButton ByCountry = new JRadioButton("По области");

        ButtonGroup filterButtons = new ButtonGroup();
        filterButtons.add(byCity);
        filterButtons.add(bySight);
        filterButtons.add(byPeoples);
        filterButtons.add(ByCountry);

        filtersPanel.add(byCity);
        filtersPanel.add(bySight);
        filtersPanel.add(byPeoples);
        filtersPanel.add(ByCountry);
        panel.add(filtersPanel, BorderLayout.SOUTH);
        panel.add(new JLabel("Поисковой запрос:"), BorderLayout.WEST);
        panel.add(jtfFilter, BorderLayout.CENTER);
        setLayout(new BorderLayout());
        add(panel, BorderLayout.SOUTH);
        add(new JScrollPane(jTable), BorderLayout.CENTER);
        DocumentListener event = new DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = jtfFilter.getText().toLowerCase();
                if (text.trim().length() == 0)
                    rowSorter.setRowFilter(null);
                else
                    rowSorter.setStringConverter(new TableStringConverter() {
                        @Override
                        public String toString(TableModel model, int row, int column) {
                            int filterRow = 0;
                            switch (getSelectedButtonText(filterButtons)){
                                case "По городу":
                                    filterRow = 0;
                                    break;
                                case "По достопримечательности":
                                    filterRow = 3;
                                    break;
                                case "По населению":
                                    filterRow = 2;
                                    break;
                                case "По области":
                                    filterRow = 1;
                                    break;
                                default:
                                    filterRow = 0;
                                    break;
                            }
                            return model.getValueAt(row, filterRow).toString().toLowerCase();
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

        };
        jtfFilter.getDocument().addDocumentListener(event);
    }
}