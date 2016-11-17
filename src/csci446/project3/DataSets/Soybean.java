package csci446.project3.DataSets;

import csci446.project3.Util.DataType;

/**
 * Created by cetho on 11/12/2016.
 */
public class Soybean {
    public static final String[] columnNames = {
            "Unknown", "Unknown", "Unknown", "Unknown", "Unknown",
            "Unknown", "Unknown", "Unknown", "Unknown", "Unknown",
            "Unknown", "Unknown", "Unknown", "Unknown", "Unknown",
            "Unknown", "Unknown", "Unknown", "Unknown", "Unknown",
            "Unknown", "Unknown", "Unknown", "Unknown", "Unknown",
            "Unknown", "Unknown", "Unknown", "Unknown", "Unknown",
            "Unknown", "Unknown", "Unknown", "Unknown", "Unknown",
            "Class"
    };

    public static final DataType[] dataTypes = {
            DataType.Integer, DataType.Integer, DataType.Integer, DataType.Integer, DataType.Integer,
            DataType.Integer, DataType.Integer, DataType.Integer, DataType.Integer, DataType.Integer,
            DataType.Integer, DataType.Integer, DataType.Integer, DataType.Integer, DataType.Integer,
            DataType.Integer, DataType.Integer, DataType.Integer, DataType.Integer, DataType.Integer,
            DataType.Integer, DataType.Integer, DataType.Integer, DataType.Integer, DataType.Integer,
            DataType.Integer, DataType.Integer, DataType.Integer, DataType.Integer, DataType.Integer,
            DataType.Integer, DataType.Integer, DataType.Integer, DataType.Integer, DataType.Integer,
            DataType.String
    };

    //Negative to not discretize the corresponding column. Other values will discretize into that many columns.
    public static final int[] discretizeColumns = {
            -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1,
            -1
    };

    public static final int[] ignoreColumns = {};

    public static final String filename = "soybean-small";
    public static final int classColumn = 35;
}
