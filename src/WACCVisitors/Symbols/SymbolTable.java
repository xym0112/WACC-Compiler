package WACCVisitors.Symbols;


import java.util.HashMap;

public class SymbolTable {
    SymbolTable encSymTable;
    HashMap<WACC_Variable, WACC_Type> dictionary;

    public SymbolTable(SymbolTable symbolTable) {
        dictionary = new HashMap();
        encSymTable = symbolTable;
    }

    //Top Symbol Table
    public SymbolTable(){
        dictionary = new HashMap();
        encSymTable = null;
    }

    public SymbolTable getEncSymTable() {
        return encSymTable;
    }

    public WACC_Type add(WACC_Variable varId, WACC_Type var) {
        return dictionary.put(varId,var);
    }

    public WACC_Type lookup(WACC_Variable varId) {
        return dictionary.get(varId);
    }

    public WACC_Type lookUpAll(WACC_Variable varId) {

        SymbolTable self = this;
        WACC_Type var = null;

        while (self != null) {
            var = lookup(varId);
            if (var  != null) {
                return var;
            }
            self = self.encSymTable;
        }
        return null;
    }


}
