package csci446.project3.ID3;

import java.util.ArrayList;
import csci446.project3.Util.Data;
import csci446.project3.Util.DataSet;

/**
 * Created by cetho on 11/12/2016.
 */

public class Node {

    public final Boolean isFeature;
    public final int column;

    public Node parent;
    public ArrayList<Node> children;

    public DataSet data;

    public Node(int column) {
        this.isFeature = true;
        this.column = column;
    }

    public Node(DataSet data, Node parent) {
        this.isFeature = false;
        this.column = -1;
        this.parent = parent;

        this.data = data;
    }
}
