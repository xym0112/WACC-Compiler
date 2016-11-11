package WACCSemantics.types;

import WACCSemantics.SymbolTable;

import java.util.ArrayList;

public class WACC_Function {
    private WACC_Type returnType;
    private ArrayList<WACC_Type> parameters;
    private SymbolTable symbolTable;

    public WACC_Function(WACC_Type returnType, ArrayList<WACC_Type> parameters, SymbolTable symbolTable) {
        this.returnType = returnType;
        this.parameters = parameters;
        this.symbolTable = symbolTable;
    }

    public WACC_Function(WACC_Type returnType, SymbolTable symbolTable) {
        this.symbolTable = symbolTable;
        this.parameters = new ArrayList<>();
        this.returnType = returnType;
    }

    public WACC_Type getReturnType() {
        return returnType;
    }

    @Override
    public String toString() {
        return returnType.toString();
    }

    public ArrayList<WACC_Type> getParameters() {
        return parameters;
    }

    public SymbolTable getSymbolTable() {
        return symbolTable;
    }
}
