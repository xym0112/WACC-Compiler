package WACCSemantics;


import WACCSemantics.types.WACC_Function;

import java.util.HashMap;

public class SymbolTable {
    private SymbolTable encSymTable;
    private HashMap<String, Variable> variableDictionary;
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
    
    public Variable addVar(String varId, Variable var) {
        return variableDictionary.put(varId,var);
    }

    public Variable lookupVar(String varId) {
        return variableDictionary.get(varId);
    }

    public Variable lookUpAllVar(String varId) {

        SymbolTable self = this;
        Variable var = null;

        while (self != null) {
            var = lookupVar(varId);
            if (var  != null) {
                return var;
            }
            self = self.getEncSymTable();
        }
        return null;
    }

    public WACC_Function addFunc(String funcName, WACC_Function func) {
        return functionDictionary.put(funcName, func);
    }

    public WACC_Function lookupFunc(String funcName) {
        return functionDictionary.get(funcName);
    }

    public WACC_Function lookUpAllFunc(String funcName) {

        SymbolTable self = this;
        WACC_Function var = null;

        while (self != null) {
            var = lookupFunc(funcName);
            if (var  != null) {
                return var;
            }
            self = self.getEncSymTable();
        }
        return null;
    }


    @Override
    public String toString() {
        return "SymbolTable{" +
                "functionDictionary=" + functionDictionary +
                ", variableDictionary=" + variableDictionary +
                '}';
    }
}


