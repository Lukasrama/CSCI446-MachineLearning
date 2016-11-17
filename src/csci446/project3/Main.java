package csci446.project3;

import com.sun.org.apache.xpath.internal.operations.Bool;
import csci446.project3.DataSets.BreastCancer;
import csci446.project3.DataSets.HouseVotes;
import csci446.project3.ID3.ID3;
import csci446.project3.Util.Data;
import csci446.project3.Util.DataParser;
import csci446.project3.Util.DataSet;
import csci446.project3.Util.DataType;

import java.util.Collection;
import java.util.Collections;

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
        DataSet houseVotes = DataParser.parseData(HouseVotes.filename, HouseVotes.columnNames, HouseVotes.dataTypes, HouseVotes.ignoreColumns, HouseVotes.classColumn);
        DataSet breastCancer = DataParser.parseData(BreastCancer.filename, BreastCancer.columnNames, BreastCancer.dataTypes, BreastCancer.ignoreColumns, BreastCancer.classColumn);
        breastCancer.print();
        /*
         * The contents of the DataSet are not always random.
         * You can shuffle them using Collections.shuffle()
         */

        Collections.shuffle(houseVotes);
        Collections.shuffle(breastCancer);
        /*
         * Lastly, you want to split the data into a regular dataset and a testing set.
         * DataSet has a function for this, since it gets a little weird.
         * This grabs 10% of the data in the dataset and sets pulls it out to make the testing set.
         */

        DataSet houseVotesTestingSet = houseVotes.getTestingSet(.1);
        DataSet breastCancerTestingSet = breastCancer.getTestingSet(.1);

        /*
         * Lets setup ID3:
         * DataSet, TestSet, column with the class categorization. (republican, democrat in this case)
         */

        ID3 id3 = new ID3(houseVotes, houseVotesTestingSet, HouseVotes.classColumn);
        String[] id3HouseVotes = new String[houseVotesTestingSet.size()];
        for(int i = 0; i < houseVotesTestingSet.size(); i++) {
            id3HouseVotes[i] = id3.classify(houseVotesTestingSet.get(i));
        }
        for(int i = 0; i < houseVotesTestingSet.size(); i++) {
            if(id3HouseVotes[i].equals(houseVotesTestingSet.get(i)[HouseVotes.classColumn].value())) {
                System.out.println("ID3: Correct (" + id3HouseVotes[i] + ")");
            } else {
                System.out.println("ID3: Incorrect (" + id3HouseVotes[i] + ", actually " + houseVotesTestingSet.get(i)[HouseVotes.classColumn].value() + ")");
            }
        }

        id3 = new ID3(breastCancer, breastCancerTestingSet, BreastCancer.classColumn);
        String[] id3BreastCancer = new String[breastCancerTestingSet.size()];
        for(int i = 0; i < breastCancerTestingSet.size(); i++) {
            id3BreastCancer[i] = id3.classify(breastCancerTestingSet.get(i));
        }
        for(int i = 0; i < breastCancerTestingSet.size(); i++) {
            if(id3BreastCancer[i].equals(breastCancerTestingSet.get(i)[BreastCancer.classColumn].value())) {
                System.out.println("ID3: Correct (" + id3BreastCancer[i] + ")");
            } else {
                System.out.println("ID3: Incorrect (" + id3BreastCancer[i] + ", actually " + breastCancerTestingSet.get(i)[BreastCancer.classColumn].value() + ")");
            }
        }

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
