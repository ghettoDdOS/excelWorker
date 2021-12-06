import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import javax.swing.*;


public class Interface {
    static Engine intEn = new Engine(null);
    static JFrame frame = new JFrame("Редактор БД Excel");
    static JPanel toolsPane = new JPanel();
    static Box contents = new Box(BoxLayout.Y_AXIS);
    static JTable table = new JTable(DataModel.getTableModel());
    static JButton deleteButton =new JButton("Удалить выделенные");
    static JButton addButton =new JButton("Добавить поля");
    static JButton searchButton =new JButton("Поиск");
    static JButton saveButton =new JButton("Сохранить");
    static JDialog searchDialog = new JDialog(frame, "Поиск");
    static JDialog createDialog = new JDialog(frame, "Добавить запись");
    static JPanel searchPanel = new JPanel();
    static JPanel filterPanel = new JPanel();
    static JPanel inputPanel = new JPanel();
    static Box searchDialogContents = new Box(BoxLayout.Y_AXIS);
    static Box createDialogContents = new Box(BoxLayout.Y_AXIS);
    static ButtonGroup searchFilter = new ButtonGroup();
    static JRadioButton byCity = new JRadioButton("По городу", true);
    static JRadioButton bySight = new JRadioButton("По достопримечательности", false);
    static JRadioButton byRegion = new JRadioButton("По области", false);
    static JButton search = new JButton("Поиск");
    static JTextField searchQuery = new JTextField();
    static JTextField inputCity = new JTextField();
    static JTextField inputRegion = new JTextField();
    static JTextField inputPopulation = new JTextField();
    static JTextField inputSight = new JTextField();
    static JLabel labelCity = new JLabel("Город");
    static JLabel labelRegion = new JLabel("Область");
    static JLabel labelPopulation = new JLabel("Население");
    static JLabel labelSight = new JLabel("Достопримечательность");
    static JButton saveCreateDialog = new JButton("Сохранить");
    static JButton discardCreateDialog = new JButton("Отмена");
    static JPanel inputToolsPanel = new JPanel();

    Interface(){
        toolsPane.setLayout(new GridLayout(2,2));
        toolsPane.add(deleteButton);
        toolsPane.add(addButton);
        toolsPane.add(searchButton);
        toolsPane.add(saveButton);


        searchFilter.add(byCity);
        searchFilter.add(bySight);
        searchFilter.add(byRegion);
        filterPanel.setLayout(new GridLayout(1, 3, 5, 0));
        filterPanel.setBorder(BorderFactory.createTitledBorder("Фильтры"));
        filterPanel.add(byCity);
        filterPanel.add(bySight);
        filterPanel.add(byRegion);
        searchPanel.setLayout(new GridLayout(2,1));
        searchPanel.setBorder(BorderFactory.createTitledBorder("Запрос"));
        searchPanel.add(searchQuery);
        searchPanel.add(search);

        filterPanel.setLayout(new GridLayout(1, 3, 5, 0));
        filterPanel.setBorder(BorderFactory.createTitledBorder("Фильтры"));
        filterPanel.add(byCity);
        filterPanel.add(bySight);
        filterPanel.add(byRegion);

        inputPanel.setLayout(new GridLayout(4, 2, 25, 15));
        inputPanel.add(labelCity);
        inputPanel.add(inputCity);
        inputPanel.add(labelRegion);
        inputPanel.add(inputRegion);
        inputPanel.add(labelPopulation);
        inputPanel.add(inputPopulation);
        inputPanel.add(labelSight);
        inputPanel.add(inputSight);
        inputToolsPanel.setLayout(new GridLayout(2, 1, 25, 15));
        inputToolsPanel.add(saveCreateDialog);
        inputToolsPanel.add(discardCreateDialog);
        createDialogContents.add(inputPanel);
        createDialogContents.add(inputToolsPanel);
        inputToolsPanel.setSize(new Dimension(30,15));

        searchDialogContents.add(filterPanel);
        searchDialogContents.add(searchPanel);
        contents.add(new JScrollPane(table));
        contents.add(toolsPane);
        packWin();
    }

    public static void packWin() {
        for(Component butt : toolsPane.getComponents()) {
            if(butt instanceof JButton) {
                ((JButton) butt).addActionListener(intEn);
            }
        }
        for(Component butt : searchPanel.getComponents()) {
            if(butt instanceof JButton) {
                ((JButton) butt).addActionListener(intEn);
            }
        }
        for(Component component : inputToolsPanel.getComponents()) {
            if(component instanceof JButton) {
                ((JButton) component).addActionListener(intEn);
            }
        }

        //searchDialog.setContentPane(searchDialogContents);
        searchDialog.add(new TableSorter());
        searchDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        searchDialog.setSize(320,500);
        createDialog.setContentPane(createDialogContents);
        createDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        createDialog.setSize(350,250);
        frame.setContentPane(contents);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent ev) {
                String[] options = {"Да", "Нет"};
                int x = JOptionPane.showOptionDialog(null, "Сохранить перед выходом?",
                        "Выход",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
                if (x == 0)
                    ExcelWorker.save();
            }
        });
    }
    public static void main(String[] args) throws IOException {
        ExcelWorker.init();
        new Interface();
    }
}