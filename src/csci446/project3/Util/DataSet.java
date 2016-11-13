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

    /*
     * Returns a the last "percentage" of the data in the arraylist as a new dataset.
     * Deletes them from this list.
     */
    public DataSet getTestingSet(double percentage) {
        return getTestingSet((int) (percentage*this.size()));
    }

    public DataSet getTestingSet(int amount) {
        DataSet newSet = new DataSet();
        newSet.assignColumnNames(this.columnNames);

        if(amount <= this.size()) {
            int size = this.size();
            //Grab starting from the last item in the list.
            for(int i = size - 1; i >= size - amount; i--) {
                newSet.add(this.remove(i));
            }
        } else {
            throw new IndexOutOfBoundsException("Taking out more items than in the DataSet");
        }
        return newSet;
    }

    public DataSet createSubset(Integer[] indexes) {
        DataSet newSet = new DataSet();
        newSet.assignColumnNames(this.columnNames);
        for(int index : indexes) {
            newSet.add(this.get(index));
        }
        return newSet;
    }
}
