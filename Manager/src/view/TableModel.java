package view;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class TableModel extends AbstractTableModel{
    private int columnCount = 5; // кол-во колонок
    private ArrayList<String[]> arrayList;

    public TableModel() {
        arrayList = new ArrayList<String[]>();
        for (int i = 0; i < arrayList.size(); i++) {
            arrayList.add(new String[getColumnCount()]);
        }
    }

    @Override // кол-во строк в табл
    public int getRowCount() {
        return arrayList.size();
    }

    @Override // кол-во колонок в табл
    public int getColumnCount() {
        return columnCount;
    }

    @Override // шапка табл.
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0: return "Дата";
            case 1: return "Операция";
            case 2: return "Сумма";
            case 3: return "Описание";
            case 4: return "Категория";
            default: return "";
        }
    }

    @Override // получает знач с опред. ячейки табл.
    public Object getValueAt(int rowIndex, int columnIndex) {
        String[] rows = arrayList.get(rowIndex);
        return rows[columnIndex];
    }

    // Добавл. в табл.
    public void addDate(String[] row) {
        arrayList.add(row);

    }

    public void clearTable() {
        arrayList.clear();
    }
}
