package WACCVisitors.Symbols;

import java.util.ArrayList;

public class WACC_Function extends WACC_Type {
    private WACC_Type returnType;
    private ArrayList<WACC_Type> parameters;

    public WACC_Function(WACC_Type returnType, WACC_Type[] parameters) {
        this.returnType = returnType;
        this.parameters = new ArrayList<WACC_Type>();
    }

    @Override
    protected boolean checkType(WACC_Type ident) {
        return ident.checkType(this.returnType);
    }
}
