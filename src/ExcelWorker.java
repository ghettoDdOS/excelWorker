import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.*;
import jxl.Sheet;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.io.IOException;


public class ExcelWorker {

    private static final String EXCEL_FILE_LOCATION = System.getProperty("user.dir") + "/database.xls";

    public static void init() {
        if(!new File(EXCEL_FILE_LOCATION).exists()) {
            try {
                WritableWorkbook database = Workbook.createWorkbook(new File(EXCEL_FILE_LOCATION));
                WritableSheet excelSheet = database.createSheet("База данных", 0);

                excelSheet.addCell(new Label(0, 0, "Город"));
                excelSheet.addCell(new Label(1, 0, "Область"));
                excelSheet.addCell(new Label(2, 0, "Население"));
                excelSheet.addCell(new Label(3, 0, "Достопримечательность"));

                database.write();
                database.close();

            } catch (IOException | WriteException e) {
                e.printStackTrace();
            }
        } else
        {
            read();
        }
    }
    public static void read() {
        try {
            Workbook workbook = Workbook.getWorkbook(new File(EXCEL_FILE_LOCATION));

            Sheet sheet = workbook.getSheet(0);
            for (int i = 1; i < sheet.getRows(); i++){
                DefaultTableModel model = (DefaultTableModel) Interface.table.getModel();
                model.addRow(new Object[]{
                        sheet.getCell(0, i).getContents(),
                        sheet.getCell(1, i).getContents(),
                        sheet.getCell(2, i).getContents(),
                        sheet.getCell(3, i).getContents()
                });
            }
            workbook.close();
        } catch (BiffException | IOException e) {
            e.printStackTrace();
        }
    }
    public static void save(){
        try {
            WritableWorkbook database = Workbook.createWorkbook(new File(EXCEL_FILE_LOCATION));
            WritableSheet excelSheet = database.createSheet("База данных", 0);

            excelSheet.addCell(new Label(0, 0, "Город"));
            excelSheet.addCell(new Label(1, 0, "Область"));
            excelSheet.addCell(new Label(2, 0, "Население"));
            excelSheet.addCell(new Label(3, 0, "Достопримечательность"));
            for (int i = 0; i < Interface.table.getRowCount(); i++){
                excelSheet.addCell(new Label(0, i + 1, (String)GetData(i, 0)));
                excelSheet.addCell(new Label(1, i + 1, (String)GetData(i, 1)));
                excelSheet.addCell(new Label(2, i + 1, (String)GetData(i, 2)));
                excelSheet.addCell(new Label(3, i + 1, (String)GetData(i, 3)));
            }
            database.write();
            database.close();
        } catch (IOException | WriteException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Ошибка", "Сообщение",1);
        }
    }
    public static Object GetData(int row_index, int col_index){
        DefaultTableModel model = (DefaultTableModel) Interface.table.getModel();
        return model.getValueAt(row_index, col_index);
    }
}
