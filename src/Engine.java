import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Engine implements ActionListener {
    Interface parent;
    Engine(Interface parent){
        this.parent = parent;
    }

    public void removeSelectedRows(){
        DefaultTableModel model = (DefaultTableModel) Interface.table.getModel();
        int[] rows = Interface.table.getSelectedRows();
        for(int i=0;i<rows.length;i++){
            model.removeRow(rows[i]-i);
        }
    }

    public void addRow(String city, String region, String population, String sight){
        DefaultTableModel model = (DefaultTableModel) Interface.table.getModel();
        model.addRow(new Object[]{
                city, region, population, sight
        });
        Interface.inputCity.setText("");
        Interface.inputRegion.setText("");
        Interface.inputSight.setText("");
        Interface.inputPopulation.setText("");
        JOptionPane.showMessageDialog(null, "Поле добавлено", "Сообщение",1);
    }

    public int getRowByValue(Object value, int column) {
        DefaultTableModel model = (DefaultTableModel) Interface.table.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            if (model.getValueAt(i, column) != null && model.getValueAt(i, column).equals(value)) {
                return i;
            }
        }
        return -1;
    }

    public void actionPerformed(ActionEvent e){
        Object src = e.getSource();
        if (src == Interface.deleteButton) {
            removeSelectedRows();
            JOptionPane.showMessageDialog(null, "Поля удалены", "Сообщение",1);
        } else if (src == Interface.addButton) {
            Interface.createDialog.setVisible(true);
        }else if (src == Interface.searchButton) {
            Interface.searchDialog.setVisible(true);
        }else if (src == Interface.search) {

            int column = Interface.bySight.isSelected()?3:Interface.byCity.isSelected()?0:Interface.byRegion.isSelected()?1:-1;
            int index = getRowByValue(Interface.searchQuery.getText(),column);
            if (index > -1)
                Interface.table.changeSelection(index,0,false,false);
            else
                JOptionPane.showMessageDialog(null, "Не найдено", "Сообщение",1);
        }else if (src == Interface.saveButton) {
            ExcelWorker.save();
            JOptionPane.showMessageDialog(null, "Сохранено", "Сообщение",1);
        }else if (src == Interface.saveCreateDialog) {
            if (
                    Interface.inputCity.getText().equals("") ||
                    Interface.inputRegion.getText().equals("") ||
                    Interface.inputPopulation.getText().equals("") ||
                    Interface.inputSight.getText().equals("")
            )
                JOptionPane.showMessageDialog(null, "Заполните все поля!", "Сообщение",1);
            else
                addRow(Interface.inputCity.getText(),Interface.inputRegion.getText(), Interface.inputPopulation.getText(), Interface.inputSight.getText());

        }else if (src == Interface.discardCreateDialog) {
            Interface.createDialog.dispose();
        }
    }
}