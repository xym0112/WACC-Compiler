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

        if (ident instanceof WACC_BaseType){
            if(((WACC_BaseType) ident).checkType(new WACC_BaseType(BaseType.ANY)))
                return true;
        }
        if (!(ident instanceof WACC_ArrayType)) return false;

        WACC_ArrayType identArrayType = (WACC_ArrayType) ident;
        return identArrayType.getType().checkType(this.type);
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

    @Override
    public int hashCode() {
        return type != null ? type.hashCode() : 0;
    }
}
