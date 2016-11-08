package WACCVisitors.Symbols;

public class WACC_BaseType extends WACC_Type {
    private BaseType type;

    public WACC_BaseType(BaseType type) {
        this.type = type;
    }

    public BaseType getType() {
        return type;
    }

    @Override
    protected boolean checkType(WACC_Type ident) {

        // TODO: consider strings

        if (!(ident instanceof WACC_BaseType)) return false;

        WACC_BaseType identBaseType = (WACC_BaseType) ident;
        return identBaseType.getType().equals(this.type);
    }
}
