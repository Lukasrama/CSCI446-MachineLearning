package csci446.project3.Util;

import java.util.ArrayList;

/**
 * Created by cetho on 11/2/2016.
 */
public class DataSet extends ArrayList<Data<?>[]> {
    String[] columnNames;

    public void assignColumnNames(String[] columnNames) {
        this.columnNames = columnNames;
    }

    public void print() {
        for(String columnName : this.columnNames) {
            System.out.print(columnName + ", ");
        }
        System.out.println("\n");

        for ( Data[] item : this) {
            for(Data col : item) {
                System.out.print(col + ", ");
            }
            System.out.println();
        }
    }

    public int columnCount() {
        return this.columnNames.length;
    }
}
