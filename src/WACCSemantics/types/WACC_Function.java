package WACCSemantics.types;

import WACCSemantics.SymbolTable;

import java.util.ArrayList;

public class WACC_Function {
    private WACC_Type returnType;
    private ArrayList<WACC_Type> parameters;
//    private WACC_Parameters parameters;
    private SymbolTable symbolTable;

    public WACC_Function(WACC_Type returnType, ArrayList<WACC_Type> parameters, SymbolTable parent) {
        this.returnType = returnType;
        this.parameters = parameters;
        this.symbolTable = new SymbolTable(parent);
    }

    public WACC_Function(WACC_Type returnType, SymbolTable parent) {
        this.symbolTable = new SymbolTable(parent);
        this.parameters = new ArrayList<WACC_Type>();
        this.returnType = returnType;
    }

    public WACC_Type getReturnType() {
        return returnType;
    }

    @Override
    public String toString() {
        return returnType.toString();
    }
}
