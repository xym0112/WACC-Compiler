package WACCSemantics.types;

public class WACC_BaseType implements WACC_Type {
    private BaseType type;

    public WACC_BaseType(BaseType type) {
        this.type = type;
    }

    public BaseType getType() {
        return type;
    }

    @Override
    public boolean checkType(WACC_Type ident) {


        // TODO: consider strings

        if ((ident instanceof WACC_ArrayType) &&
                ((WACC_ArrayType) ident).getType().checkType(new WACC_BaseType(BaseType.CHAR)) &&
                        (this.getType() == BaseType.STRING)){
            return true;
        }


        if (type == BaseType.ANY){
            return true;
        }

        if (!(ident instanceof WACC_BaseType)) return false;

        WACC_BaseType identBaseType = (WACC_BaseType) ident;

        if (identBaseType.getType() == BaseType.ANY)
            return true;

        return identBaseType.getType().equals(this.type);
    }

    @Override
    public String toString() {
        return type.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WACC_BaseType that = (WACC_BaseType) o;

        return type == that.type;

    }

    @Override
    public int hashCode() {
        return type != null ? type.hashCode() : 0;
    }
}
