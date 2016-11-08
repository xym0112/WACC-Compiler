package WACCVisitors.Symbols;

public class WACC_PairType extends WACC_Type {
    private WACC_Type first;
    private WACC_Type second;

    public WACC_PairType(WACC_Type first, WACC_Type second) {
        this.first = first;
        this.second = second;
    }

    public WACC_Type getFirst() {
        return first;
    }

    public WACC_Type getSecond() {
        return second;
    }

    @Override
    protected boolean checkType(WACC_Type ident) {
        if (!(ident instanceof WACC_PairType)) return false;

        WACC_PairType identPairType = (WACC_PairType) ident;
        return identPairType.getFirst().checkType(this.first)
                && identPairType.getSecond().checkType(this.second);
    }
}
