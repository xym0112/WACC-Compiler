package WACCSemantics.types;

import WACCSemantics.SymbolTable;

public class WACC_Function {
    private WACC_Type returnType;
    private WACC_Parameters parameters;
    private SymbolTable symbolTable;

    public WACC_Function(WACC_Type returnType, WACC_Parameters parameters, SymbolTable parent) {
        this.returnType = returnType;
        this.parameters = parameters;
        this.symbolTable = new SymbolTable(parent);
    }

    public WACC_Function(WACC_Type returnType, SymbolTable parent) {
        this.symbolTable = new SymbolTable(parent);
        this.parameters = new WACC_Parameters();
        this.returnType = returnType;
    }

    public WACC_Type getReturnType() {
        return returnType;
    }
}
