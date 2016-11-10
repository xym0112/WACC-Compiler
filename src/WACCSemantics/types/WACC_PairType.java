package WACCSemantics.types;

public class WACC_PairType implements WACC_Type {
    private WACC_Type first;
    private WACC_Type second;

    public WACC_PairType(WACC_Type first, WACC_Type second) {
        this.first = first;
        this.second = second;
    }

    public WACC_PairType() {
        this.first = new WACC_BaseType(BaseType.ANY);
        this.second = new WACC_BaseType(BaseType.ANY);
    }

    public WACC_Type getFirst() {
        return first;
    }

    public WACC_Type getSecond() {
        return second;
    }

    @Override
    public boolean checkType(WACC_Type ident) {

        if (ident instanceof WACC_BaseType) {
            WACC_BaseType baseType = (WACC_BaseType) ident;
            return baseType.getType()==BaseType.ANY;
        }

        if (!(ident instanceof WACC_PairType)) return false;

        WACC_PairType identPairType = (WACC_PairType) ident;

        return identPairType.getFirst().checkType(this.first)
                && identPairType.getSecond().checkType(this.second);
    }

    @Override
    public String toString() {
        return "(" + first + ", " + second + ")";
    }
}
