package WACCSemantics;

import WACCSemantics.types.*;
import antlr.WACCParser;
import antlr.WACCParser.*;
import antlr.WACCParserBaseVisitor;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.ArrayList;

public class WACC_Semantics_Visitor extends WACCParserBaseVisitor<WACC_Type> {

    SymbolTable currentST;

    public WACC_Semantics_Visitor() {
        this.currentST = new SymbolTable();
    }

    @Override
    public WACC_Type visitProg(@NotNull ProgContext ctx) {
        for (FuncContext func : ctx.func()) {
            String funcName = func.Ident().getText();

            if (currentST.lookUpAllFunc(funcName) != null) {
                semanticError("Function " + funcName + " previously defined",
                        func.Ident().getSymbol().getLine(),
                        func.Ident().getSymbol().getCharPositionInLine());
            }

            visit(func);
        }

        visit(ctx.stat());
        return null;
    }

    @Override
    public WACC_Type visitFunc(@NotNull FuncContext ctx) {
        // create a new symbol table with current table as parent
        currentST = new SymbolTable(currentST);

        String funcName = ctx.Ident().getText();
        WACC_Type funcRetType = visit(ctx.type());

        ArrayList<WACC_Type> funcParams = new ArrayList<WACC_Type>();

        if (ctx.paramList() != null) {
            for (int i = 0; i < ctx.paramList().param().size(); i++) {
                funcParams.add(visit(ctx.paramList().param(i)));
            }
        }

        if (!funcParams.isEmpty()) {
            currentST.addFunc(funcName, new WACC_Function(funcRetType, funcParams, currentST));
        } else {
            currentST.addFunc(funcName, new WACC_Function(funcRetType, currentST));
        }

        // set current symbol table to parent
        WACC_Type statRetType = visit(ctx.stat());
        currentST = currentST.getEncSymTable();

        if(!funcRetType.checkType(statRetType)) {
            semanticError("Function " + funcName + " has conflicting return types",
                    ctx.Ident().getSymbol().getLine(),
                    ctx.Ident().getSymbol().getCharPositionInLine());
        }

        return funcRetType;
    }

    @Override
    public WACC_Type visitParam(@NotNull ParamContext ctx) {

        WACC_Type paramType = visit(ctx.type());
        String paramName = ctx.Ident().getText();
        Variable param = new Variable(paramType, true);
        currentST.addVar(paramName, param);

        return paramType;
    }


    @Override
    public WACC_Type visitTYPEBASE(@NotNull TYPEBASEContext ctx) {
        if (ctx.BaseType().toString().equals("char")) {
            return new WACC_BaseType(BaseType.CHAR);
        } else if (ctx.BaseType().toString().equals("int")) {
            return new WACC_BaseType(BaseType.INT);
        } else if (ctx.BaseType().toString().equals("bool")) {
            return new WACC_BaseType(BaseType.BOOL);
        } else {
            return new WACC_BaseType(BaseType.STRING);
        }
    }

    @Override
    public WACC_Type visitRETURN(@NotNull RETURNContext ctx) {
        return visit(ctx.expr());
    }

    @Override
    public WACC_Type visitBEGIN(@NotNull BEGINContext ctx) {
        // Create a new symbol table with the current one as its parent
        currentST = new SymbolTable(currentST);

        //TODO:Test vars once we have them
        WACC_Type statType = visit(ctx.stat());

        // Make current symbol table the previous ones parent
        currentST = currentST.getEncSymTable();

        return statType;
    }

    @Override
    public WACC_Type visitUNSIGNED(@NotNull UNSIGNEDContext ctx) {
        return new WACC_BaseType(BaseType.INT);
    }

    // Print Semantic Error helper method
    private void semanticError(String msg, int line, int pos) {
        System.out.println("Semantic Error: " + msg
                        + " at line " + line
                        + " and position " + pos);

        System.exit(200);
    }
}
