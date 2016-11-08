package WACCSemantics.types;

import WACCSemantics.SymbolTable;

import java.util.ArrayList;

public class WACC_Function {
    private WACC_Type returnType;
    private ArrayList<WACC_Type> parameters;
    private SymbolTable symbolTable;

    public WACC_Function(WACC_Type returnType, WACC_Type[] parameters, SymbolTable symbolTable) {
        this.returnType = returnType;
        this.parameters = new ArrayList<WACC_Type>();
        this.symbolTable = new SymbolTable(symbolTable);
    }

    public WACC_Type getReturnType() {
        return returnType;
    }
}
