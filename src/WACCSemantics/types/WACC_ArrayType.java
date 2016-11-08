package WACCSemantics.types;

public class WACC_ArrayType implements WACC_Type {
    private WACC_Type type;
    private int size;

    public WACC_ArrayType(WACC_Type type) {
        this.type = type;
        this.size = 0;
    }

    public WACC_ArrayType(WACC_Type type, int size) {
        this.type = type;
        this.size = size;
    }

    public WACC_Type getType() {
        return type;
    }

    public int getSize() {
        return size;
    }

    @Override
    public boolean checkType(WACC_Type ident) {
        if (!(ident instanceof WACC_ArrayType)) return false;

        WACC_ArrayType identArrayType = (WACC_ArrayType) ident;
        return identArrayType.getType().checkType(this.type);
    }

    @Override
    public String toString() {
        return type + "[]";
    }
}
