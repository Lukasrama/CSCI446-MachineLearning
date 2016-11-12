package csci446.project3.ID3;

import com.sun.org.apache.xpath.internal.operations.Bool;
import csci446.project3.Util.Data;
import csci446.project3.Util.DataSet;
import csci446.project3.Util.DataType;

import java.util.ArrayList;
import java.util.Queue;
import java.util.Set;

/**
 * Created by cetho on 11/12/2016.
 */
public class ID3 {

    private DataSet fullDataSet;

    private boolean[] usedFeatures;
    private int classColumn;

    private String classes[];

    private Node root;
    private Queue<Node> unfinishedNodes;

    public ID3(DataSet dataSet, DataSet testSet, int classColumn) {
        //Create a boolean array to keep track of whether or not data has been split by the algorithm already.
        this.usedFeatures = new int[dataSet.columnCount()];
        //Mark the class column so it isn't used. Afterall, we're trying to derive that.
        this.classColumn = classColumn;
        this.usedFeatures[classColumn] = true;

        ArrayList<String> classes = new ArrayList<String>();
        //Create a unique list of all the possible classes.
        for (Data<?>[] data : dataSet) {
            if(!classes.contains((String) data[classColumn].value())) {
                classes.add((String) data[classColumn].value());
            }
        }
    }

    public void generateTree() {

    }

    public int findHighestGainColumn() {

    }

    public double gain(int column) {
        int[] classOccurences = getClassOccurrencesForFeature(column);
        double[] classProbabilities = occurrencesToProbability(classOccurences);
        double featureEntropy = entropy(classProbabilities); //Get the entropy for all the values of this feature/column.
        //Count up the feature's value counts
        int[] feature
    }

    public double[] occurrencesToProbability(int[] occurrences) {
        double[] probabilities = new double[occurrences.length];
        int points = 0;
        for(int i : occurrences) {
            points += i;
        }
        for(int i = 0; i < probabilities.length; i++) {
            probabilities[i] = occurrences[i]/points;
        }
        return probabilities;
    }

    public Integer[] getFeatureValueOccurrences(int column) throws Exception {
        //Get the available data for the node we want to expand on.
        DataSet data = unfinishedNodes.peek().data;
        //Find out what kind of data the dataset is.
        DataType dataType = data.get(0)[column].type();
        ArrayList<Integer> valueOccurrences = new ArrayList<Integer>();

        if(dataType == DataType.Boolean) {
            ArrayList<Boolean> values = new ArrayList<Boolean>();
            // Build a list of all values for a feature.
            for(int i = 0; i < data.size(); i++) {
                if(!values.contains((Boolean) data.get(i)[column].value())) {
                    values.add((Boolean) data.get(i)[column].value());
                }
            }
            //Count the number of occurrences of a feature value.
            for(int i = 0; i < data.size(); i++) {
                //See if this value already exists in the occurrence list.
                for(int j = 0; j < valueOccurrences.size(); j++) {
                    if(((Boolean) data.get(i)[column].value()).equals(values.get(j))) {
                        //Increment the number of occurrences for this feature.
                        valueOccurrences.set(j, valueOccurrences.get(j) + 1);
                        break;
                    }
                }
            }
        } else if (dataType == DataType.String) {
            ArrayList<String> values = new ArrayList<String>();
            // Build a list of all values for a feature.
            for(int i = 0; i < data.size(); i++) {
                if(!values.contains((String) data.get(i)[column].value())) {
                    values.add((String) data.get(i)[column].value());
                }
            }
            //Count the number of occurrences of a feature value.
            for(int i = 0; i < data.size(); i++) {
                //See if this value already exists in the occurrence list.
                for(int j = 0; j < valueOccurrences.size(); j++) {
                    if(((String) data.get(i)[column].value()).equals(values.get(j))) {
                        //Increment the number of occurrences for this feature.
                        valueOccurrences.set(j, valueOccurrences.get(j) + 1);
                        break;
                    }
                }
            }
        } else if (dataType == DataType.Integer) {
            ArrayList<Integer> values = new ArrayList<Integer>();
            // Build a list of all values for a feature.
            for(int i = 0; i < data.size(); i++) {
                if(!values.contains((Integer) data.get(i)[column].value())) {
                    values.add((Integer) data.get(i)[column].value());
                }
            }
            //Count the number of occurrences of a feature value.
            for(int i = 0; i < data.size(); i++) {
                //See if this value already exists in the occurrence list.
                for(int j = 0; j < valueOccurrences.size(); j++) {
                    if(((Integer) data.get(i)[column].value()).equals(values.get(j))) {
                        //Increment the number of occurrences for this feature.
                        valueOccurrences.set(j, valueOccurrences.get(j) + 1);
                        break;
                    }
                }
            }
        } else {
            ArrayList<Double> values = new ArrayList<Double>();
            // Build a list of all values for a feature.
            for(int i = 0; i < data.size(); i++) {
                if(!values.contains((Double) data.get(i)[column].value())) {
                    values.add((Double) data.get(i)[column].value());
                }
            }
            //Count the number of occurrences of a feature value.
            for(int i = 0; i < data.size(); i++) {
                //See if this value already exists in the occurrence list.
                for(int j = 0; j < valueOccurrences.size(); j++) {
                    if(((Double) data.get(i)[column].value()).equals(values.get(j))) {
                        //Increment the number of occurrences for this feature.
                        valueOccurrences.set(j, valueOccurrences.get(j) + 1);
                        break;
                    }
                }
            }
        }

        return (Integer[]) valueOccurrences.toArray();
    }

    public int[] getClassOccurrencesForFeature(int column) {
        //Get the available data for the node we want to expand on.
        DataSet data = unfinishedNodes.peek().data;

        int classOccurrences[] = new int[classes.length];

        for(int i = 0; i < data.size(); i++) {
            for(int j = 0; j < classes.length; j++) {
                //Look for the class index corresponding to the class of this data point.
                if(((String) data.get(i)[classColumn].value()).equals(classes[j])) {
                    //Increment the number of occurrences for this node.
                    classOccurrences[i]++;
                    break;
                }
            }
        }
        return classOccurrences;
    }

    public double entropy(double[] probabilities) {
        double result = 0;
        for(double p : probabilities) {
            result -= p*Math.log(p)/Math.log(2); //Uses change of base for logarithms since Math.log is base 10.
        }
        return result;
    }

    public Integer[] getUnusedFeatures() {
        ArrayList<Integer> features = new ArrayList<Integer>();
        for(int i = 0; i < usedFeatures.length; i++) {
            if(!usedFeatures[i]) {
                features.add(i);
            }
        }
        return (Integer[]) features.toArray();
    }
}
