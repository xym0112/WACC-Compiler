package WACCVisitors.Symbols;


public class WACC_Array extends WACC_Type {
    WACC_Type elementType;
    int arraySize;

    public WACC_Array(WACC_Type elementType, int arraySize) {
        this.elementType = elementType;
        this.arraySize = arraySize;
    }

    public WACC_Type getElementType() {
        return elementType;
    }

    public int getArraySize() {
        return arraySize;
    }
}
