/**
 * Enum represents type of currently stored values in BinaryTree
 */
public enum DataType {
    /** Data type is not specified */
    UNDEFINED(0),

    /** Data type is Integer */
    INTEGER(1),

    /** Data type is Double */
    DOUBLE(2),

    /** Data type is String */
    STRING(3);

    /** Integer representing chosen enum value */
    public final int value;

    /**
     * Constructor of enum. It allows creating enum from Integer
     * 
     * @param value Integer value representing enum value to set
     */
    DataType(int value) {
        this.value = value;
    }
}