package WACCSemantics;

import WACCSemantics.types.*;
import antlr.WACCParser;
import antlr.WACCParser.*;
import antlr.WACCParserBaseVisitor;
import org.antlr.v4.runtime.misc.NotNull;

public class WACC_Semantics_Visitor extends WACCParserBaseVisitor<WACC_Type> {

    SymbolTable currentST;

    public WACC_Semantics_Visitor() {
        this.currentST = new SymbolTable();
    }

    @Override
    public WACC_Type visitProg(@NotNull ProgContext ctx) {

        // For each function:
        //  - generate semantic error if it is prev defined
        //  - add it to the currentST
        // Then:
        // visit all children
        // TODO .. VISIT BODY OF FUNCTION ? - IS THIS DONE IN VISITFUNC OR HERE.. ?
        for (FuncContext func : ctx.func()) {
            String funcName = func.Ident().getText();
            WACC_Type funcRetType = visit(func.type());

            if (currentST.lookUpAllFunc(funcName) != null) {
                semanticError("Function " + funcName + " previously defined",
                        func.Ident().getSymbol().getLine(),
                        func.Ident().getSymbol().getCharPositionInLine());

                System.exit(200);
            }

            WACC_Parameters funcParams = new WACC_Parameters();

            for (ParamContext param : func.paramList().param()) {
                funcParams.add(visit(param));
            }

            if (funcParams != null) {
                currentST.addFunc(funcName, new WACC_Function(funcRetType, funcParams, currentST));
            } else {
                currentST.addFunc(funcName, new WACC_Function(funcRetType, currentST));
            }
        }

        visitChildren(ctx); // VISIT THE FUNCTIONS AGAIN .. ?
        return null;
    }

    @Override
    public WACC_Type visitFunc(@NotNull FuncContext ctx) {
        return super.visitFunc(ctx);
    }

    @Override
    public WACC_Type visitParam(@NotNull ParamContext ctx) {
        WACC_Type paramType = visit(ctx.type());
        String paramName = ctx.Ident().getText();

        // WACC_Type param = new Variable();
        // TODO: A PROBLEM TO SOLVE BEFORE I GET BACK
        // TODO: IS HOW TO MAKE A VARIABLE A WACC_TYPE ..
        return super.visitParam(ctx);
    }

    @Override
    public WACC_Type visitParamList(@NotNull ParamListContext ctx) {
        WACC_Parameters params = new WACC_Parameters();

        for (ParamContext param : ctx.param()) {
            params.add(visit(param));
        }

        // somehow append params to context - don't worry about this until im with you.
        // the reason we want to do this is as we cant return a List of wacc types from
        // this function. DONT modify our structure, it's ok, I have a solution.
        return null;
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



    // Print Semantic Error helper method
    private void semanticError(String msg, int line, int pos) {
        System.out.println("Semantic Error: " + msg
                        + " at line " + line
                        + " and position " + pos);
    }
}
