package csci446.project3.Util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by cetho on 11/2/2016.
 */
public class DataParser {
    public static DataSet parseData(String dataName, String[] columnNames, DataType[] dataTypes) throws IOException, Exception {
        if(columnNames.length != dataTypes.length) {
            throw new Exception("Column Length Mismatch");
        }
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
            parsedData = new Data[items.length];
            //Just making sure its a valid row.
            if(items.length != dataTypes.length) {
                throw new Exception("Column Length Mismatch");
            }

            for (int i = 0; i < items.length; i++) {
                parsedData[i] = generateDataPoint(items[i], dataTypes[i]);
            }
            dataSet.add(parsedData);
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
}
