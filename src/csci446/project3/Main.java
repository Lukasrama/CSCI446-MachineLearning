package csci446.project3;

import com.sun.org.apache.xpath.internal.operations.Bool;
import csci446.project3.DataSets.HouseVotes;
import csci446.project3.ID3.ID3;
import csci446.project3.Util.Data;
import csci446.project3.Util.DataParser;
import csci446.project3.Util.DataSet;
import csci446.project3.Util.DataType;

public class Main {

    public static void main(String[] args) throws Exception {
        //Example of importing the data from the data/house-votes-84.data file.

        /*
         * Columns Need to be named manually.
         *
         * Data-types for each column need to be specified. You'll find all that in the names file.
         *
         * To keep this class clean, csci446.project3.DataSets has the all the basic info to pass to the algorithm.
         */
        DataSet dataSet = DataParser.parseData(HouseVotes.filename, HouseVotes.columnNames, HouseVotes.dataTypes);

        /*
         * Lets setup ID3:
         * DataSet, TestSet, column with the class categorization. (republican, democrat in this case)
         */

        ID3 id3 = new ID3(dataSet, dataSet, 0);




        /*
         * You can use any function in DataSet, its an extension to an ArrayList.
         * You can do anything you would normally do with an array list and a bit more.
         *
         */
        //Printing a DataSet.
        //dataSet.print();

        /*
         * Each item in DataSet represents one row of data. This is the first guys voting history.
         *
         * They are arrays of cells.
         * All the arrays are the size of the number of columns.
         * If an item was missing during parsing as is the case when someone didn't vote the spot in the array will be null.
         * Make sure to check for that.
         */
        //Data[] firstGuy = dataSet.get(0);
        /*
         * Each Data item can have a subtype. The first column is a string. These are the DataType Enum
         * I've added support for Doubles, Integers, Booleans and Strings.
         * DataType firstColumnType = firstGuy[0].type();
         * Lastly, this guy is a Republican. To get the value, you'll need to typecast to
         * Boolean, Double, Integer or String depending on the type. It's not as elegant as I'd like but oh well.
         */

        //String firstGuysParty = (String) firstGuy[0].value();

        /*
         * This should be a baseline for all of the learning algorithms. Good "luck".
         */
    }
}
