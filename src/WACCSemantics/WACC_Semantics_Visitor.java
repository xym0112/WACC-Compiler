package WACCSemantics;

import WACCSemantics.types.BaseType;
import WACCSemantics.types.WACC_BaseType;
import WACCSemantics.types.WACC_Function;
import WACCSemantics.types.WACC_Type;
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

        // set current symbol table to parent
        WACC_Type statRetType = visit(ctx.stat());

        currentST = currentST.getEncSymTable();

        if (!funcParams.isEmpty()) {
            currentST.addFunc(funcName, new WACC_Function(funcRetType, funcParams, currentST));
        } else {
            currentST.addFunc(funcName, new WACC_Function(funcRetType, currentST));
        }

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
        Variable param = new Variable(paramType, false);
        currentST.addVar(paramName, param);

        return paramType;
    }



    //stats

    @Override
    public WACC_Type visitRETURN(@NotNull RETURNContext ctx) {
        return visit(ctx.expr());
    }

    @Override
    public WACC_Type visitCREATEVAR(@NotNull CREATEVARContext ctx) {

        WACC_Type  varType = visit(ctx.type());
        WACC_Type varValue = visit(ctx.assignRhs());
        String varName = ctx.Ident().getText();
        Variable variable = new Variable(varType);

        //TODO TESTING FOR DIFFERENT TYPES;

        // check if we already declared the variable
        if ((currentST.lookUpAllVar(varName) != null)
            && (currentST.lookUpAllVar(varName).isDeclared())) {
            semanticError("Variable " + varName + " is assigned to an already declared variable",
                    ctx.Ident().getSymbol().getLine(),
                    ctx.Ident().getSymbol().getCharPositionInLine());
        }

        // case if we added a nondeclared variable in symbolTable - This shouldnt happen ever.
        if (currentST.lookUpAllVar(varName) != null) {
            semanticError("Non declared variable " + varName + " found in Symbol Table.",
                    ctx.Ident().getSymbol().getLine(),
                    ctx.Ident().getSymbol().getCharPositionInLine());
        }

        // check if the var's types conflict with the lhs
        if (!varType.checkType(varValue)){
            semanticError("Variable " + varName + " is assigned to a value of different type",
                ctx.Ident().getSymbol().getLine(),
                ctx.Ident().getSymbol().getCharPositionInLine());
        }

        variable.setDeclared(true);
        currentST.addVar(varName, variable);
        return null;
    }

    @Override
    public WACC_Type visitASSIGNVAR(@NotNull ASSIGNVARContext ctx) {
        return super.visitASSIGNVAR(ctx);
    }

    @Override
    public WACC_Type visitBEGIN(@NotNull BEGINContext ctx) {
        // Create a new symbol table with the current one as its parent
        currentST = new SymbolTable(currentST);

        //TODO:Test var scopes once we have them
        WACC_Type statType = visit(ctx.stat());

        // Make current symbol table the previous ones parent
        currentST = currentST.getEncSymTable();

        return statType;
    }

    @Override
    public WACC_Type visitSEQUENCE(@NotNull SEQUENCEContext ctx) {
        WACC_Type fststat = visit(ctx.stat(0));
        WACC_Type sndstat = visit(ctx.stat(1));
        if ((fststat == null)) {
            return sndstat;
        } else {
            // if sndstat isnt null, then our syntax checker is wrong, so its fine to assume that sndstat is null
            assert(sndstat == null);
            return fststat;
        }

    }
    //assignRhs

    @Override
    public WACC_Type visitRHSEXPR(@NotNull RHSEXPRContext ctx) {
        return visit(ctx.expr());
    }


    //type

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

    // Expressions


    @Override
    public WACC_Type visitUNSIGNED(@NotNull UNSIGNEDContext ctx) {
        return new WACC_BaseType(BaseType.INT);
    }


    @Override
    public WACC_Type visitBOOLLITER(@NotNull BOOLLITERContext ctx) {
        return new WACC_BaseType(BaseType.BOOL);
    }

    @Override
    public WACC_Type visitCHARLITER(@NotNull CHARLITERContext ctx) {
        return new WACC_BaseType(BaseType.CHAR);
    }

    @Override
    public WACC_Type visitSTRLITER(@NotNull STRLITERContext ctx) {
        return new WACC_BaseType(BaseType.STRING);
    }

    @Override
    public WACC_Type visitPAIRLITER(@NotNull PAIRLITERContext ctx) {
        return new WACC_BaseType(BaseType.NULL);
    }

    @Override
    public WACC_Type visitEXPRIDENT(@NotNull EXPRIDENTContext ctx) {
        if (currentST.lookUpAllVar(ctx.Ident().getText()) == null) {
            semanticError(ctx.Ident().getText() + " Identifier doesn't exist", ctx.Ident().getSymbol().getLine(),
                    ctx.Ident().getSymbol().getCharPositionInLine());
        }
        return currentST.lookUpAllVar(ctx.Ident().getText()).getType();
    }

    @Override
    public WACC_Type visitUNARYOP(@NotNull UNARYOPContext ctx) {
        WACC_Type expr = visit(ctx.expr());
        String operation = ctx.unaryOper().getText();

        if (operation.equals("!")
                && !expr.checkType(new WACC_BaseType(BaseType.BOOL))) {

            unaryOperationError(operation, ctx, expr.toString());
        } else if (!expr.checkType(new WACC_BaseType(BaseType.INT))
                && (operation.equals("+") || operation.equals("-")
                || operation.equals("len") || operation.equals("chr"))) {

            unaryOperationError(operation, ctx, expr.toString());
        } else if (!expr.checkType(new WACC_BaseType(BaseType.CHAR))
                && operation.equals("ord")) {

            unaryOperationError(operation, ctx, expr.toString());
        }

        if (operation.equals("len") || operation.equals("ord")) return new WACC_BaseType(BaseType.INT);
        if (operation.equals("ord")) return new WACC_BaseType(BaseType.CHAR);

        return expr;
    }

    // remove extra labels in binary
    @Override
    public WACC_Type visitBINARYOP(@NotNull BINARYOPContext ctx) {
        String operation = ctx.binaryOper().getText();
        WACC_Type expr1 = visit(ctx.expr(0));
        WACC_Type expr2 = visit(ctx.expr(1));

        if (operation.equals("*") || operation.equals("/") || operation.equals("+")
                || operation.equals("-") || operation.equals("%")){

            if (!expr1.checkType(new WACC_BaseType(BaseType.INT)) ||
                    !expr2.checkType(new WACC_BaseType(BaseType.INT))) {
                semanticError("this binary can only be operated to Integer ", ctx.binaryOper().getStart().getLine()
                        , ctx.binaryOper().getStart().getCharPositionInLine());
            } else {
                return new WACC_BaseType(BaseType.INT);
            }
        }
        else {

            if (!expr1.checkType(visit(ctx.expr(1))) || !expr1.checkType(new WACC_BaseType(BaseType.INT))
                    || !expr1.checkType(new WACC_BaseType(BaseType.BOOL)) ||
                    !expr1.checkType(new WACC_BaseType(BaseType.STRING))){
                semanticError("this binary can only be operated to given type ", ctx.binaryOper().getStart().getLine()
                        , ctx.binaryOper().getStart().getCharPositionInLine());
            }   else {
                return new WACC_BaseType(BaseType.BOOL);
            }
        }
        return visit(ctx.expr(0));
    }

    @Override
    public WACC_Type visitBRACKETEXPR(@NotNull BRACKETEXPRContext ctx) {
        return visit(ctx.expr());
    }

    @Override
    public WACC_Type visitPAIRFST(@NotNull PAIRFSTContext ctx) {
        return visit(ctx.expr());
    }

    @Override
    public WACC_Type visitPAIRSND(@NotNull PAIRSNDContext ctx) {
        return visit(ctx.expr());
    }

    @Override
    public WACC_Type visitPAIRARRAYTYPE(@NotNull PAIRARRAYTYPEContext ctx) {
        return visit(ctx.type());
    }

    @Override
    public WACC_Type visitPairType(@NotNull PairTypeContext ctx) {
        WACC_Type left = visit(ctx.pairElemType(0));
        WACC_Type right = visit(ctx.pairElemType(1));

        return new WACC_PairType(left, right);
    }

    @Override
    public WACC_Type visitArrayElem(@NotNull ArrayElemContext ctx) {
        Variable var = currentST.lookUpAllVar(ctx.Ident().getText());
        if (var == null) {
            semanticError("var " + ctx.Ident().getText()
                    + " does not exist",
                    ctx.Ident().getSymbol().getLine(),
                    ctx.Ident().getSymbol().getCharPositionInLine());
        }

        if (!(var.getType() instanceof WACC_ArrayType)) {
            semanticError(ctx.Ident().getText()
                            + " is not an array",
                    ctx.Ident().getSymbol().getLine(),
                    ctx.Ident().getSymbol().getCharPositionInLine());
        }



        for (ExprContext expr : ctx.expr()) {
            WACC_ArrayType array = (WACC_ArrayType) var.getType();
            WACC_Type type = visit(expr);
            if (!(type.checkType(new WACC_BaseType(BaseType.INT)))) {
                semanticError(expr.getText()
                                + " should be an int",
                        expr.getStop().getLine(),
                        expr.getStop().getCharPositionInLine());
            }

            if (!(String.valueOf(array.getSize()).equals(expr.getText()))) {
                semanticError(expr.getText()
                                + " should be " + array.getSize(),
                        expr.getStop().getLine(),
                        expr.getStop().getCharPositionInLine());
            }

            visit(expr);
        }






    }

    private void unaryOperationError(String operation, UNARYOPContext ctx, String type) {
        semanticError(operation + " unary operation can not be applied to " + type,
                ctx.unaryOper().getStop().getLine(),
                ctx.unaryOper().getStop().getCharPositionInLine());
    }

    // Print Semantic Error helper method
    private void semanticError(String msg, int line, int pos) {
        System.err.println("Semantic Error: " + msg
                        + " at line " + line
                        + " and position " + pos);

        System.exit(200);
    }
}
