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
        this.usedFeatures = new boolean[dataSet.columnCount()];
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

    public int findHighestGainColumn() throws Exception {
        double highestValue = Integer.MIN_VALUE;
        int highestColumn = -1;
        for(int i = 0; i < fullDataSet.get(i).length; i++) {
            //Only evaluate unused columns.
            if(!usedFeatures[i]) {
                double gain = gain(i);
                if(highestValue < gain) {
                    highestValue = gain;
                    highestColumn = i;
                }
            }
        }
        return highestColumn;
    }

    public double gain(int column) throws Exception {
        int[] classOccurences = getClassOccurrencesForFeature(column);
        double[] classProbabilities = occurrencesToProbability(classOccurences);
        double featureEntropy = entropy(classProbabilities); //Get the entropy for all the values of this feature/column.
        //Count up the feature's value counts
        //TODO: MAKE EVALUATABLE ON AN EMPTY TREE.
        DataSet data = unfinishedNodes.peek().data;
        DataType dataType = data.get(0)[column].type();

        Double[] valueEntropies;
        int[] subsetSizes;

        if(dataType == DataType.Boolean) {
            Boolean[] dataValues = getBooleanValues(column);
            valueEntropies = new Double[dataValues.length];
            subsetSizes = new int[dataValues.length];

            for (int i = 0; i < dataValues.length; i++) {
                int[] occurrences = getClassOccurrences(column, dataValues[i]);
                for(int o : occurrences) {
                    subsetSizes[i]+= o;
                }
                valueEntropies[i] = entropy(occurrencesToProbability(occurrences))*subsetSizes[i]/data.size();
            }
        } else if (dataType == DataType.String) {
            String[] dataValues = getStringValues(column);
            valueEntropies = new Double[dataValues.length];
            subsetSizes = new int[dataValues.length];

            for (int i = 0; i < dataValues.length; i++) {
                int[] occurrences = getClassOccurrences(column, dataValues[i]);
                for(int o : occurrences) {
                    subsetSizes[i]+= o;
                }
                valueEntropies[i] = entropy(occurrencesToProbability(occurrences))*subsetSizes[i]/data.size();
            }
        } else if (dataType == DataType.Integer) {
            Integer[] dataValues = getIntegerValues(column);
            valueEntropies = new Double[dataValues.length];
            subsetSizes = new int[dataValues.length];

            for (int i = 0; i < dataValues.length; i++) {
                int[] occurrences = getClassOccurrences(column, dataValues[i]);
                for(int o : occurrences) {
                    subsetSizes[i]+= o;
                }
                valueEntropies[i] = entropy(occurrencesToProbability(occurrences))*subsetSizes[i]/data.size();
            }
        } else {
            Double[] dataValues = getDoubleValues(column);
            valueEntropies = new Double[dataValues.length];
            subsetSizes = new int[dataValues.length];

            for (int i = 0; i < dataValues.length; i++) {
                int[] occurrences = getClassOccurrences(column, dataValues[i]);
                for(int o : occurrences) {
                    subsetSizes[i]+= o;
                }
                valueEntropies[i] = entropy(occurrencesToProbability(occurrences))*subsetSizes[i]/data.size();
            }
        }

        double gain = featureEntropy;

        for(double e : valueEntropies) {
            gain -= e;
        }

        return gain;
    }

    public Boolean[] getBooleanValues(int column) {
        DataSet data = unfinishedNodes.peek().data;
        ArrayList<Boolean> values = new ArrayList<Boolean>();
        // Build a list of all values for a feature.
        for(int i = 0; i < data.size(); i++) {
            if(!values.contains((Boolean) data.get(i)[column].value())) {
                values.add((Boolean) data.get(i)[column].value());
            }
        }
        return (Boolean[]) data.toArray();
    }

    public String[] getStringValues(int column) {
        DataSet data = unfinishedNodes.peek().data;
        ArrayList<String> values = new ArrayList<String>();
        // Build a list of all values for a feature.
        for(int i = 0; i < data.size(); i++) {
            if(!values.contains((String) data.get(i)[column].value())) {
                values.add((String) data.get(i)[column].value());
            }
        }
        return (String[]) data.toArray();
    }

    public Integer[] getIntegerValues(int column) {
        DataSet data = unfinishedNodes.peek().data;
        ArrayList<Integer> values = new ArrayList<Integer>();
        // Build a list of all values for a feature.
        for(int i = 0; i < data.size(); i++) {
            if(!values.contains((Integer) data.get(i)[column].value())) {
                values.add((Integer) data.get(i)[column].value());
            }
        }
        return (Integer[]) data.toArray();
    }

    public Double[] getDoubleValues(int column) {
        DataSet data = unfinishedNodes.peek().data;
        ArrayList<Double> values = new ArrayList<Double>();
        // Build a list of all values for a feature.
        for(int i = 0; i < data.size(); i++) {
            if(!values.contains((Double) data.get(i)[column].value())) {
                values.add((Double) data.get(i)[column].value());
            }
        }
        return (Double[]) data.toArray();
    }


    public int[] getClassOccurrences(int column, Boolean value) {
        //Get the available data for the node we want to expand on.
        DataSet data = unfinishedNodes.peek().data;

        int classOccurrences[] = new int[classes.length];

        for(int i = 0; i < data.size(); i++) {
            //Filter out datasets that don't have this specific value.
            if(((Boolean) data.get(i)[column].value()).equals(value)) {
                for (int j = 0; j < classes.length; j++) {
                    //Look for the class index corresponding to the class of this data point.
                    if (((String) data.get(i)[classColumn].value()).equals(classes[j])) {
                        //Increment the number of occurrences for this node.
                        classOccurrences[i]++;
                        break;
                    }
                }
            }
        }
        return classOccurrences;
    }

    public int[] getClassOccurrences(int column, String value) {
        //Get the available data for the node we want to expand on.
        DataSet data = unfinishedNodes.peek().data;

        int classOccurrences[] = new int[classes.length];

        for(int i = 0; i < data.size(); i++) {
            //Filter out datasets that don't have this specific value.
            if(((String) data.get(i)[column].value()).equals(value)) {
                for (int j = 0; j < classes.length; j++) {
                    //Look for the class index corresponding to the class of this data point.
                    if (((String) data.get(i)[classColumn].value()).equals(classes[j])) {
                        //Increment the number of occurrences for this node.
                        classOccurrences[i]++;
                        break;
                    }
                }
            }
        }
        return classOccurrences;
    }

    public int[] getClassOccurrences(int column, Integer value) {
        //Get the available data for the node we want to expand on.
        DataSet data = unfinishedNodes.peek().data;

        int classOccurrences[] = new int[classes.length];

        for(int i = 0; i < data.size(); i++) {
            //Filter out datasets that don't have this specific value.
            if(((Integer) data.get(i)[column].value()).equals(value)) {
                for (int j = 0; j < classes.length; j++) {
                    //Look for the class index corresponding to the class of this data point.
                    if (((String) data.get(i)[classColumn].value()).equals(classes[j])) {
                        //Increment the number of occurrences for this node.
                        classOccurrences[i]++;
                        break;
                    }
                }
            }
        }
        return classOccurrences;
    }

    public int[] getClassOccurrences(int column, Double value) {
        //Get the available data for the node we want to expand on.
        DataSet data = unfinishedNodes.peek().data;

        int classOccurrences[] = new int[classes.length];

        for(int i = 0; i < data.size(); i++) {
            //Filter out datasets that don't have this specific value.
            if(((Double) data.get(i)[column].value()).equals(value)) {
                for (int j = 0; j < classes.length; j++) {
                    //Look for the class index corresponding to the class of this data point.
                    if (((String) data.get(i)[classColumn].value()).equals(classes[j])) {
                        //Increment the number of occurrences for this node.
                        classOccurrences[i]++;
                        break;
                    }
                }
            }
        }
        return classOccurrences;
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
