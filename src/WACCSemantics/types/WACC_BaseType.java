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

        if (!(ident instanceof WACC_BaseType)) return false;

        WACC_BaseType identBaseType = (WACC_BaseType) ident;
        return identBaseType.getType().equals(this.type);
    }

    @Override
    public String toString() {
        return "WACC_BaseType{" +
                "type=" + type +
                '}';
    }


}
