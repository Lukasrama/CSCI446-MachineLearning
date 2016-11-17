package csci446.project3.DataSets;

import csci446.project3.Util.DataType;

/**
 * Created by cetho on 11/12/2016.
 */
public class BreastCancer {

    public static final String[] columnNames = {
            "Sample Code Number (Removed)",
            "Clump Thickness",
            "Uniformity of Cell Size",
            "Uniformity of Cell Shape",
            "Marginal Adhesion",
            "Single Epithelial Cell Size",
            "Bare Nucleoli",
            "Bland Chromatin",
            "Normal Nucleoli",
            "Mitoses",
            "Class",
    };

    public static final DataType[] dataTypes = {
            DataType.Integer, DataType.Integer, DataType.Integer, DataType.Integer,
            DataType.Integer, DataType.Integer, DataType.Integer, DataType.Integer, DataType.Integer,
            DataType.String
    };

    //Integer is already in bins of 10 so it works out nicely. No need for discretizing.
    public static final int[] discretizeColumns = {
            -1, -1, -1, -1,
            -1, -1, -1, -1, -1,
            -1,
    };

    public static final int[] ignoreColumns = {0};

    public static final String filename = "breast-cancer-wisconsin";
    public static final int classColumn = 9; //Account for offset from removing column 0.
}
