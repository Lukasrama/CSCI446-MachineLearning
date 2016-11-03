package csci446.project3.Util;

/**
 * Created by cetho on 11/2/2016.
 */
public class Data<T> {
    private T value;

    public Data(T value) {
        this.value = value;
    }

    public T value() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    public DataType type() throws Exception {
        if(value.getClass().getTypeName().equals("java.lang.String")) {
            return DataType.String;
        }
        if(value.getClass().getTypeName().equals("java.lang.Boolean")) {
            return DataType.Boolean;
        }
        if(value.getClass().getTypeName().equals("java.lang.Double")) {
            return DataType.Double;
        }
        if(value.getClass().getTypeName().equals("java.lang.Integer")) {
            return DataType.Integer;
        }
        throw new Exception("Invalid type.");
    }
}