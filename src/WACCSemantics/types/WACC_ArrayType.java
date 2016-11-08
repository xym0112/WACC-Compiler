package WACCSemantics.types;

public class WACC_ArrayType implements WACC_Type {
    private WACC_Type type;

    public WACC_ArrayType(WACC_Type type) {
        this.type = type;
    }

    public WACC_Type getType() {
        return type;
    }

    @Override
    public boolean checkType(WACC_Type ident) {
        if (!(ident instanceof WACC_ArrayType)) return false;

        WACC_ArrayType identArrayType = (WACC_ArrayType) ident;
        return identArrayType.getType().checkType(this.type);
    }
}
