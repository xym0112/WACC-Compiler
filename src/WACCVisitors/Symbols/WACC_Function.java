package WACCVisitors.Symbols;

import java.util.Arrays;

public class WACC_Function extends WACC_Identifier {
    WACC_Type returnType;
    WACC_Parameter parameters[];
    SymbolTable st;

    public WACC_Type getReturnType() {
        return returnType;
    }

    public WACC_Parameter[] getParameters() {
        return parameters;
    }

    @Override
    public String toString() {
        return "WACC_Function " +
                "with returnType " + returnType +
                ", parameters " + Arrays.toString(parameters);
    }
}
