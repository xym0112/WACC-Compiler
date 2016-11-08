package WACCVisitors.Symbols;


public class WACC_Array extends WACC_Type {
    WACC_Type elementType;


    public WACC_Array(WACC_Type elementType) {
        this.elementType = elementType;
    }

    public WACC_Type getElementType() {
        return elementType;
    }

}
