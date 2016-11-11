package WACCSemantics;


import WACCSemantics.types.WACC_Function;

import java.util.HashMap;

public class SymbolTable {
    private SymbolTable encSymTable;
    private HashMap<String, Variable> variableDictionary;
    private HashMap<String, WACC_Function> functionDictionary;

    SymbolTable(SymbolTable symbolTable) {
        variableDictionary = new HashMap<>();
        functionDictionary = new HashMap<>();
        encSymTable = symbolTable;
    }

    //Top Symbol Table
    SymbolTable(){
        variableDictionary = new HashMap<>();
        functionDictionary = new HashMap<>();
        encSymTable = null;
    }

    SymbolTable getEncSymTable() {
        return encSymTable;
    }
    
    Variable addVar(String varId, Variable var) {
        return variableDictionary.put(varId,var);
    }

    Variable lookupVar(String varId) {
        return variableDictionary.get(varId);
    }

    Variable lookUpAllVar(String varId) {

        SymbolTable self = this;
        Variable var;

        while (self != null) {
            var = self.lookupVar(varId);
            if (var  != null) {
                return var;
            }
            self = self.getEncSymTable();
        }
        return null;
    }

    WACC_Function addFunc(String funcName, WACC_Function func) {
        return functionDictionary.put(funcName, func);
    }

    private WACC_Function lookupFunc(String funcName) {
        return functionDictionary.get(funcName);
    }

    WACC_Function lookUpAllFunc(String funcName) {

        SymbolTable self = this;
        WACC_Function var;

        while (self != null) {
            var = self.lookupFunc(funcName);
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


