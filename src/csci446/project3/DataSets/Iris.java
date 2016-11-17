package csci446.project3.DataSets;

import csci446.project3.Util.DataType;

/**
 * Created by cetho on 11/12/2016.
 */
public class Iris {
    public static final String[] columnNames = {
            "Sepal Length",
            "Sepal Width",
            "Petal Length",
            "Petal Width",
            "Class"
    };

    public static final DataType[] dataTypes = {
            DataType.Double, DataType.Double, DataType.Double, DataType.Double, DataType.String,
    };

    //Negative to not discretize the corresponding column. Other values will discretize into that many columns.
    public static final int[] discretizeColumns = {10, 5, 10, 10, -1};

    public static final int[] ignoreColumns = {};

    public static final String filename = "iris";
    public static final int classColumn = 4;
}
