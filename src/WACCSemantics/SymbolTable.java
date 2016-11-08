package WACCSemantics;


import WACCSemantics.types.WACC_Function;
import WACCSemantics.types.WACC_Type;

import java.util.HashMap;

public class SymbolTable {
    private SymbolTable encSymTable;
    private HashMap<String, WACC_Type> variableDictionary;
    private HashMap<String, WACC_Function> functionDictionary;

    public SymbolTable(SymbolTable symbolTable) {
        variableDictionary = new HashMap();
        functionDictionary = new HashMap();
        encSymTable = symbolTable;
    }

    //Top Symbol Table
    public SymbolTable(){
        variableDictionary = new HashMap();
        functionDictionary = new HashMap();
        encSymTable = null;
    }

    public SymbolTable getEncSymTable() {
        return encSymTable;
    }

    public WACC_Type addVar(String varId, WACC_Type var) {
        return variableDictionary.put(varId,var);
    }

    public WACC_Type lookupVar(String varId) {
        return variableDictionary.get(varId);
    }

    public WACC_Type lookUpAllVar(String varId) {

        SymbolTable self = this;
        WACC_Type var = null;

        while (self != null) {
            var = lookupVar(varId);
            if (var  != null) {
                return var;
            }
            self = self.getEncSymTable();
        }
        return null;
    }

    public WACC_Function addFunc(String funcId, WACC_Function var) {
        return functionDictionary.put(funcId,var);
    }

    public WACC_Function lookupFunc(String varId) {
        return functionDictionary.get(varId);
    }

    public WACC_Function lookUpAllFunc(String varId) {

        SymbolTable self = this;
        WACC_Function var = null;

        while (self != null) {
            var = lookupFunc(varId);
            if (var  != null) {
                return var;
            }
            self = self.getEncSymTable();
        }
        return null;
    }
}


