package WACCVisitors.Symbols;


public class WACC_Pair extends WACC_Type {
    WACC_Type fstType;
    WACC_Type sndType;

    public WACC_Pair(WACC_Type fstType, WACC_Type sndType) {
        this.fstType = fstType;
        this.sndType = sndType;
    }

    @Override
    public String toString() {
        return "WACC_Pair:" +
                "(" + fstType +
                ", " + sndType +
                ')';
    }
}
