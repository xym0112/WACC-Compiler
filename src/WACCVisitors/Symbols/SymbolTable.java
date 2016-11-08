package WACCVisitors.Symbols;


import java.util.HashMap;

public class SymbolTable {
    private SymbolTable encSymTable;
    private HashMap<String, WACC_Identifier> dictionary;

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

    public WACC_Identifier add(String varId, WACC_Identifier var) {
        return dictionary.put(varId,var);
    }

    public WACC_Identifier lookup(String varId) {
        return dictionary.get(varId);
    }

    public WACC_Identifier lookUpAll(String varId) {

        SymbolTable self = this;
        WACC_Identifier var = null;

        while (self != null) {
            var = lookup(varId);
            if (var  != null) {
                return var;
            }
            self = self.getEncSymTable();
        }
        return null;
    }
}


