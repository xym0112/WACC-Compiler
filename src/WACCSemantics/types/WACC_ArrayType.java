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
    public boolean checkType(WACC_Type identifier) {

        if ((identifier instanceof WACC_BaseType) &&
                (((WACC_BaseType) identifier).getType() == BaseType.ANY))
                return true;

        if (!(identifier instanceof WACC_ArrayType))
            return false;


        WACC_ArrayType identifierArrayType = (WACC_ArrayType) identifier;

        return identifierArrayType.getType().checkType(this.type);
    }

    @Override
    public String toString() {
        return type + "[]";
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        WACC_ArrayType that = (WACC_ArrayType) o;

        return type != null ? type.equals(that.type) : that.type == null;
    }
}
