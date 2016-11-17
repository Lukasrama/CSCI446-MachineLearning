package csci446.project3.Util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by cetho on 11/2/2016.
 */
public class DataParser {
    public static DataSet parseData(String dataName, String[] columnNames, DataType[] dataTypes, int[] ignoreColumns, int classColumn, int[] discretizeColumns) throws IOException, Exception {
        //Create a new DataSet
        DataSet dataSet = new DataSet();
        //Open the file.
        FileReader reader = new FileReader("data/" + dataName + ".data");
        BufferedReader textReader = new BufferedReader(reader);
        String row;
        String[] items;
        Data[] parsedData;
        dataSet.assignColumnNames(columnNames);
        while ((row = textReader.readLine()) != null) {
            //Got a row. Need the data. Split it by the commas.
            items = (String[]) Arrays.asList(row.split(",")).toArray();
            //Don't want to make space for the columns being ignored.
            parsedData = new Data[items.length - ignoreColumns.length];
            //Just making sure its a valid row.
            int offset = 0;
            for (int i = 0; i < items.length - ignoreColumns.length; i++) {
                if(i == classColumn) {
                    //Class Column is always parsed as a String
                    parsedData[i] = new Data<String>(items[i+offset]);
                } else {
                    parsedData[i] = generateDataPoint(items[i+offset], dataTypes[i]);
                }
                for(int j = 0; j < ignoreColumns.length; j++) {
                    if((i + offset) == ignoreColumns[j]) {
                        offset++;
                        i--;
                    }
                }
            }
            dataSet.add(parsedData);
        }

        fillEmptyData(dataTypes, dataSet);

        for(int i = 0; i < dataSet.get(0).length; i++) {
            discretizeColumn(dataTypes[i], i, dataSet, discretizeColumns);
        }

        return dataSet;
    }

    private static Data generateDataPoint(String raw, DataType type) {
        //Just so java says its there.
        Data data = new Data<Boolean>(false);
        //There can be null data. Its marked as a question mark in the datasets.

        if(raw.equals("?")) {
            return null;
        }

        switch (type) {
            case Boolean:
                if(raw.equals("y")) {
                    data = new Data<Boolean>(true);
                }
                else if(raw.equals("n")) {
                    data = new Data<Boolean>(false);
                }
                else {
                    data = new Data<Boolean>(Boolean.parseBoolean(raw));
                }
                break;
            case Double:
                data = new Data<Double>(Double.parseDouble(raw));
                break;
            case Integer:
                data = new Data<Integer>(Integer.parseInt(raw));
                break;
            case String:
                data = new Data<String>(raw);
                break;
        }
        return data;
    }

    public static void fillEmptyData(DataType[] dataTypes, DataSet dataset) {
        Random random = new Random();
        ArrayList<Data<?>[]> toDelete= new ArrayList<Data<?>[]>();
        for(int r = 0; r < dataset.size(); r++) {
            row:
            for(int i = 0; i < dataset.get(r).length; i++) {
                //Take null elements and grab just assign it something.
                if(dataset.get(r)[i] == null) {
                    switch(dataTypes[i]) {
                        case Boolean:
                            //This one is easy.
                            Data<?> next = new Data<Boolean>(random.nextBoolean());
                            dataset.get(r)[i] = next;
                            break;
                        case Double:
                            toDelete.add(dataset.get(r));
                            break row;
                        case String:
                            toDelete.add(dataset.get(r));
                            break row;
                        case Integer:
                            toDelete.add(dataset.get(r));
                            break row;
                    }
                }
            }
        }
        dataset.removeAll(toDelete);
    }

    public static void discretizeColumn(DataType dataType, int column, DataSet dataSet, int[] discretizeColumns) {
        if(discretizeColumns[column] == -1) {
            return;
        }
        if(dataType == DataType.Double) {
            Double max = getMaxDouble(column, dataSet);
            Double min = getMinDouble(column, dataSet);
            //Split into however many equal sized bins as specified in the discretizeColumns
            Double interval = (max - min)/((double) discretizeColumns[column]);

            for(double i = min; i <= max - interval; i += interval) {
                for(int j = 0; j < dataSet.size(); j++) {
                    if(((Double) dataSet.get(j)[column].value()) >= i && ((Double) dataSet.get(j)[column].value()) < (i + interval)) {
                        //Bin it!
                        dataSet.get(j)[column] = new Data<Double>(i);
                    }
                }
            }
        } else {
            Integer max = getMaxInteger(column, dataSet);
            Integer min = getMinInteger(column, dataSet);
            //Split into however many equal sized bins as specified in the discretizeColumns
            Integer interval = (max - min)/discretizeColumns[column];

            for(int i = min; i <= max - interval; i += interval) {
                for(int j = 0; j < dataSet.size(); j++) {
                    if(((Integer) dataSet.get(j)[column].value()) >= i && ((Integer) dataSet.get(j)[column].value()) < (i + interval)) {
                        //Bin it!
                        dataSet.get(j)[column] = new Data<Integer>(i);
                    }
                }
            }
        }
    }

    public static Double getMaxDouble(int column, DataSet dataSet) {
        Double max = Double.MIN_VALUE;
        for(Data<?>[] item : dataSet) {
            if(((Double) item[column].value()) > max) {
                max = (Double) item[column].value();
            }
        }
        return max;
    }
    public static Double getMinDouble(int column, DataSet dataSet) {
        Double min = Double.MAX_VALUE;
        for(Data<?>[] item : dataSet) {
            if(((Double) item[column].value()) < min) {
                min = (Double) item[column].value();
            }
        }
        return min;
    }

    public static Integer getMaxInteger(int column, DataSet dataSet) {
        Integer max = Integer.MIN_VALUE;
        for(Data<?>[] item : dataSet) {
            if(((Integer) item[column].value()) > max) {
                max = (Integer) item[column].value();
            }
        }
        return max;
    }

    public static Integer getMinInteger(int column, DataSet dataSet) {
        Integer min = Integer.MAX_VALUE;
        for(Data<?>[] item : dataSet) {
            if(((Integer) item[column].value()) < min) {
                min = (Integer) item[column].value();
            }
        }
        return min;
    }
}
