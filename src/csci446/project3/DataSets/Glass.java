package csci446.project3.DataSets;

import csci446.project3.Util.DataType;

/**
 * Created by cetho on 11/12/2016.
 */
public class Glass {
    public static final String[] columnNames = {
            "ID (Removed)",
            "Refractive Index",
            "Sodium",
            "Magnesium",
            "Aluminum",
            "Silicon",
            "Potassium",
            "Calcium",
            "Barium",
            "Iron",
            "Class",
    };

    public static final DataType[] dataTypes = {
            DataType.Double, DataType.Double, DataType.Double, DataType.Double,
            DataType.Double, DataType.Double, DataType.Double, DataType.Double, DataType.Double,
            DataType.String
    };
    //Skip missing columns.
    public static final int[] discretizeColumns = {
            5, 10, 5, 10, 10,
            10, 10, 10, 5, -1,
    };

    public static final int[] ignoreColumns = {0};

    public static final String filename = "glass";
    public static final int classColumn = 9; //Account for offset from removing column 0.
}
