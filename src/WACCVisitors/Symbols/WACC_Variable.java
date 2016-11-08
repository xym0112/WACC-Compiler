package WACCVisitors.Symbols;


public class WACC_Variable extends WACC_Identifier{
    WACC_Type type;

    @Override
    public String toString() {
        return "WACC_Variable" +
                "of type:" + type;
    }
}
