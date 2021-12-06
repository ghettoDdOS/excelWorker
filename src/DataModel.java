import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class DataModel {
    private static Object[] columnsHeaders = new String[] {"Город", "Область",
            "Население", "Достопримечательность"};
    private static Object[][] data = new String[][] {};
    private static TableModel tableModel = new DefaultTableModel(data, columnsHeaders);

    public DataModel(){
    }


    public static Object[] getColumnsHeaders(){
        return  columnsHeaders;
    }

    public static TableModel getTableModel(){
        return tableModel;
    }


}
// TODO: Справочник городов и областей с помощью выпадающего списка