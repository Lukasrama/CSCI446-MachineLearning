package csci446.project3.DataSets;

import csci446.project3.Util.DataType;

/**
 * Created by cetho on 11/12/2016.
 */
public class HouseVotes {
    public static final String[] columnNames = {
            "Class Name",
            "handicapped-infants",
            "water-project-cost-sharing",
            "adoption-of-the-budget-resolution",
            "physician-fee-freeze",
            "el-salvador-aid",
            "religious-groups-in-schools",
            "anti-satellite-test-ban",
            "aid-to-nicaraguan-contras",
            "mx-missile",
            "immigration",
            "synfuels-corporation-cutback",
            "education-spending",
            "superfund-right-to-sue",
            "crime",
            "duty-free-exports",
            "export-administration-act-south-africa",
    };

    public static final DataType[] dataTypes = {
            DataType.String,
            DataType.Boolean, DataType.Boolean, DataType.Boolean, DataType.Boolean,
            DataType.Boolean, DataType.Boolean, DataType.Boolean, DataType.Boolean,
            DataType.Boolean, DataType.Boolean, DataType.Boolean, DataType.Boolean,
            DataType.Boolean, DataType.Boolean, DataType.Boolean, DataType.Boolean,
    };

    public static final int[] ignoreColumns = {};

    public static final String filename = "house-votes-84";
    public static final int classColumn = 0;
}
