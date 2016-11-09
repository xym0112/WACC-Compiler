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
            WACC_Type funcRetType = visit(func.type());

            if (currentST.lookUpAllFunc(funcName) != null) {
                semanticError("function " + funcName + " previously defined",
                        func.Ident().getSymbol().getLine(),
                        func.Ident().getSymbol().getCharPositionInLine());
            }

            currentST = new SymbolTable(currentST);

            ArrayList<WACC_Type> funcParams = new ArrayList<WACC_Type>();

            if (func.paramList() != null) {
                for (int i = 0; i < func.paramList().param().size(); i++) {
                    funcParams.add(visit(func.paramList().param(i)));
                }
            }

            if (!funcParams.isEmpty()) {
                currentST.getEncSymTable().addFunc(funcName, new WACC_Function(funcRetType, funcParams, currentST));
            } else {
                currentST.getEncSymTable().addFunc(funcName, new WACC_Function(funcRetType, currentST));
            }

            currentST = currentST.getEncSymTable();
        }

        for (FuncContext func : ctx.func()){
            currentST = currentST.lookUpAllFunc(func.Ident().getText()).getSymbolTable();
            visit(func);
            currentST = currentST.getEncSymTable();
        }

        return null;
    }

    @Override
    public WACC_Type visitFunc(@NotNull FuncContext ctx) {
        WACC_Type funcRetType = currentST.lookUpAllFunc(ctx.Ident().getText()).getReturnType();

        WACC_Type statRetType = visit(ctx.stat());

        if (!funcRetType.checkType(statRetType)) {
            semanticError("Function " + ctx.Ident().getText() + " has conflicting return types",
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

    @Override
    public WACC_Type visitSKIPSTAT(@NotNull SKIPSTATContext ctx) {
        return null;
    }

    @Override
    public WACC_Type visitEXIT(@NotNull EXITContext ctx) {
        WACC_Type exprType = visit(ctx.expr());

        if(!(exprType.checkType(new WACC_BaseType(BaseType.INT)))) {
            semanticError("exit code should be a number",
                    ctx.expr().getStop().getLine(),
                    ctx.expr().getStop().getCharPositionInLine());
        }

        return null;
    }

    @Override
    public WACC_Type visitCREATEVAR(@NotNull CREATEVARContext ctx) {
        WACC_Type varType = visit(ctx.type());
        WACC_Type varValue = visit(ctx.assignRhs());
        String varName = ctx.Ident().getText();
        Variable variable = new Variable(varType);

        // check if we already declared the variable
        if ((currentST.lookUpAllVar(varName) != null)
                && (currentST.lookUpAllVar(varName).isDeclared())) {
            semanticError("variable " + varName + " is assigned to an already declared variable",
                    ctx.Ident().getSymbol().getLine(),
                    ctx.Ident().getSymbol().getCharPositionInLine());
        }

        // chck if we added a nondeclared variable in symbolTable
        if (currentST.lookUpAllVar(varName) != null) {
            semanticError("non declared variable " + varName + " found in Symbol Table.",
                    ctx.Ident().getSymbol().getLine(),
                    ctx.Ident().getSymbol().getCharPositionInLine());
        }

        // check if the var's types conflict with the lhs
        if (!varType.checkType(varValue)){
            semanticError("variable " + varName + " is assigned to a value of different type",
                    ctx.Ident().getSymbol().getLine(),
                    ctx.Ident().getSymbol().getCharPositionInLine());
        }

        variable.setDeclared(true);
        currentST.addVar(varName, variable);
        return null;
    }


    @Override
    public WACC_Type visitASSIGNVAR(@NotNull ASSIGNVARContext ctx) {
        WACC_Type lhs = visit(ctx.assignLhs());
        WACC_Type rhs = visit(ctx.assignRhs());
        if (!(lhs.checkType(rhs))) {
            semanticError("Variable assigned to wrong type at ",
                    ctx.ASSIGN().getSymbol().getLine(),
                    ctx.ASSIGN().getSymbol().getCharPositionInLine());
        }
        return null;
    }

    @Override
    public WACC_Type visitREAD(@NotNull READContext ctx) {
        WACC_Type exprType = visit(ctx.assignRhs());

        if(!(exprType.checkType(new WACC_BaseType(BaseType.INT))
                || (exprType.checkType(new WACC_BaseType(BaseType.CHAR))))) {
            semanticError("Variable cannot be read into at ",
                    ctx.assignRhs().getStop().getLine(),
                    ctx.assignRhs().getStop().getCharPositionInLine());
        }

        return null;
    }

    @Override
    public WACC_Type visitFREE(@NotNull FREEContext ctx) {
        WACC_Type exprType = visit(ctx.expr());

        if(!(exprType instanceof WACC_ArrayType)
                || (exprType instanceof WACC_PairType) ){
            semanticError("Variable cannot be freed at ",
                    ctx.expr().getStop().getLine(),
                    ctx.expr().getStop().getCharPositionInLine());
        }

        return null;
    }


    // Statements

    @Override
    public WACC_Type visitRETURN(@NotNull RETURNContext ctx) {
        return visit(ctx.expr());
    }

    @Override
    public WACC_Type visitPRINT(@NotNull PRINTContext ctx) {
        visit(ctx.expr());
        return null;
    }

    @Override
    public WACC_Type visitPRINTLN(@NotNull PRINTLNContext ctx) {
        visit(ctx.expr());
        return null;
    }

    @Override
    public WACC_Type visitIF(@NotNull IFContext ctx) {
        WACC_Type cond = visit(ctx.expr());

        if (!(cond.checkType(new WACC_BaseType(BaseType.BOOL)))) {
            semanticError(" condition of if statement evaluate to type bool ",
                    ctx.expr().getStart().getLine(),
                    ctx.expr().getStart().getCharPositionInLine());
        }

        currentST = new SymbolTable(currentST);
        WACC_Type ifType = visit(ctx.stat(0));
        currentST = currentST.getEncSymTable();
        currentST = new SymbolTable(currentST);
        WACC_Type fiType = visit(ctx.stat(1));
        currentST = currentST.getEncSymTable();

        if (ifType == null) {
            return fiType;
        } else {
            return ifType;
        }
    }

    @Override
    public WACC_Type visitWHILE(@NotNull WHILEContext ctx) {
        WACC_Type cond = visit(ctx.expr());

        if (!(cond.checkType(new WACC_BaseType(BaseType.BOOL)))) {
            semanticError(" condition of while statement evaluate to type bool ",
                    ctx.expr().getStart().getLine(),
                    ctx.expr().getStart().getCharPositionInLine());
        }

        currentST = new SymbolTable(currentST);
        WACC_Type innerType = visit(ctx.stat());
        currentST = currentST.getEncSymTable();


        return innerType;
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
        WACC_Type fstStat = visit(ctx.stat(0));
        WACC_Type sndStat = visit(ctx.stat(1));
        if ((fstStat == null)) {
            return sndStat;
        } else {
            // TODO: sAssuming syntax is correct allows us to assume that sndstat is null
            assert (sndStat == null);
            return fstStat;
        }
    }

    // Assign RHS

    @Override
    public WACC_Type visitRHSEXPR(@NotNull RHSEXPRContext ctx) {
        return visit(ctx.expr());
    }

    @Override
    public WACC_Type visitRHSNEWPAIR(@NotNull RHSNEWPAIRContext ctx) {
        return new WACC_PairType(visit(ctx.expr(0)), visit(ctx.expr(1)));
    }

    @Override
    public WACC_Type visitRHSCALLFUNC(@NotNull RHSCALLFUNCContext ctx) {
        String funcName = ctx.Ident().getText();
        WACC_Function function = currentST.lookUpAllFunc(funcName);

        if (function == null) {
            semanticError(" function " + funcName + " not defined",
                    ctx.Ident().getSymbol().getLine(),
                    ctx.Ident().getSymbol().getCharPositionInLine());
        }

        ArgListContext args = ctx.argList();
        ArrayList<WACC_Type> argList = new ArrayList<WACC_Type>();

        if (args != null) {
            for (ExprContext arg : args.expr()) {
                argList.add(visit(arg));
            }
        }

        if (!argList.equals(function.getParameters())) {
            semanticError(" arguments do not match function " + funcName,
                    ctx.argList().getStart().getLine(),
                    ctx.argList().getStart().getCharPositionInLine());
        }

        return function.getReturnType();
    }

    // Assign LHS

    @Override
    public WACC_Type visitLHSIDENT(@NotNull LHSIDENTContext ctx) {
        Variable var = currentST.lookUpAllVar(ctx.Ident().getText());

        if (var == null) {
            semanticError("variable named " + ctx.Ident().getText() + " doesn't exit",
                    ctx.Ident().getSymbol().getLine(),
                    ctx.Ident().getSymbol().getCharPositionInLine());
        }

        return var.getType();
    }

    // Pair Elem

    @Override
    public WACC_Type visitPAIRFST(@NotNull PAIRFSTContext ctx) {
        Variable var = currentST.lookUpAllVar(ctx.expr().getText());

        if (var == null) {
            semanticError("variable " + ctx.expr().getText() + " doesn't exit",
                    ctx.expr().getStart().getLine(),
                    ctx.expr().getStart().getCharPositionInLine());
        }

        if (!(var.getType() instanceof WACC_PairType)) {
            semanticError(ctx.expr().getText() + " is not a pair",
                    ctx.expr().getStart().getLine(),
                    ctx.expr().getStart().getCharPositionInLine());
        }

        WACC_PairType pair = (WACC_PairType) var.getType();

        return pair.getFirst();
    }

    @Override
    public WACC_Type visitPAIRPAIR(@NotNull PAIRPAIRContext ctx) {
        return null;
    }

    @Override
    public WACC_Type visitPAIRSND(@NotNull PAIRSNDContext ctx) {
        Variable var = currentST.lookUpAllVar(ctx.expr().getText());

        if (var == null) {
            semanticError("variable " + ctx.expr().getText() + " doesn't exit",
                    ctx.expr().getStart().getLine(),
                    ctx.expr().getStart().getCharPositionInLine());
        }

        if (!(var.getType() instanceof WACC_PairType)) {
            semanticError(ctx.expr().getText() + " is not a pair",
                    ctx.expr().getStart().getLine(),
                    ctx.expr().getStart().getCharPositionInLine());
        }

        WACC_PairType pair = (WACC_PairType) var.getType();

        return pair.getSecond();
    }

    // Types

    @Override
    public WACC_Type visitTYPEBASE(@NotNull TYPEBASEContext ctx) {
        return visitBaseTypeHelper(ctx.BaseType().toString());
    }

    @Override
    public WACC_Type visitTYPEARRAY(@NotNull TYPEARRAYContext ctx) {
        return new WACC_ArrayType(visit(ctx.type()));
    }

    @Override
    public WACC_Type visitPairType(@NotNull PairTypeContext ctx) {
        WACC_Type left = visit(ctx.pairElemType(0));
        WACC_Type right = visit(ctx.pairElemType(1));

        return new WACC_PairType(left, right);
    }

    @Override
    public WACC_Type visitPAIRBASETYPE(@NotNull PAIRBASETYPEContext ctx) {
        return visitBaseTypeHelper(ctx.BaseType().toString());
    }

    private WACC_Type visitBaseTypeHelper(String baseType) {
        if (baseType.equals("char")) {
            return new WACC_BaseType(BaseType.CHAR);
        } else if (baseType.equals("int")) {
            return new WACC_BaseType(BaseType.INT);
        } else if (baseType.equals("bool")) {
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
        } else if ((!expr.checkType(new WACC_BaseType(BaseType.INT))
                && (operation.equals("+") || operation.equals("-")
                 || operation.equals("chr")))) {

            unaryOperationError(operation, ctx, expr.toString());
        } else if (operation.equals("len") && !(expr instanceof WACC_ArrayType)) {
            unaryOperationError(operation, ctx, expr.toString());
        } else if (!expr.checkType(new WACC_BaseType(BaseType.CHAR))
                && operation.equals("ord")) {

            unaryOperationError(operation, ctx, expr.toString());
        }

        if (operation.equals("len") || operation.equals("ord")) return new WACC_BaseType(BaseType.INT);
        if (operation.equals("chr")) return new WACC_BaseType(BaseType.CHAR);

        return expr;
    }

    @Override
    public WACC_Type visitBINARYOP(@NotNull BINARYOPContext ctx) {
        String operation = ctx.binaryOper().getText();
        WACC_Type expr1 = visit(ctx.expr(0));
        WACC_Type expr2 = visit(ctx.expr(1));

        if ((operation.equals("*") || operation.equals("/") || operation.equals("+")
                || operation.equals("-") || operation.equals("%"))
            && (!expr1.checkType(new WACC_BaseType(BaseType.INT)) ||
                !expr2.checkType(new WACC_BaseType(BaseType.INT)))
                ) {
            semanticError("this binary operation can only be applied to int", ctx.binaryOper().getStart().getLine()
                    , ctx.binaryOper().getStart().getCharPositionInLine());
        }

        if ((operation.equals("<") || operation.equals(">") || operation.equals("<=")
                || operation.equals(">="))
                && !expr1.checkType(expr2) && (!expr1.checkType(new WACC_BaseType(BaseType.CHAR))
                || !expr1.checkType(new WACC_BaseType(BaseType.INT)))) {
            semanticError("this binary operation can only be applied two equal types of int and chars", ctx.binaryOper().getStart().getLine()
                    , ctx.binaryOper().getStart().getCharPositionInLine());
        }


        if ((operation.equals("==") || operation.equals("!=")) && !expr1.checkType(expr2) && (
                !expr1.checkType(new WACC_BaseType(BaseType.CHAR)) || !expr1.checkType(new WACC_BaseType(BaseType.BOOL))
                || !expr1.checkType(new WACC_BaseType(BaseType.INT)) || !(expr1 instanceof WACC_PairType))) {


            semanticError(operation + " operation can only be applied two equal types of char, bool, int and pair", ctx.binaryOper().getStart().getLine()
                    , ctx.binaryOper().getStart().getCharPositionInLine());
        }


        if (operation.equals("<=") || operation.equals(">=") || operation.equals("==")
                || operation.equals("!=") || operation.equals("<") || operation.equals(">")
                ) {
            return new WACC_BaseType(BaseType.BOOL);
        }

        return new WACC_BaseType(BaseType.INT);
    }


    @Override
    public WACC_Type visitLOGICEXPR(@NotNull LOGICEXPRContext ctx) {
        WACC_Type expr1 = visit(ctx.expr(0));
        WACC_Type expr2 = visit(ctx.expr(1));

        if (!expr1.checkType(expr2) && !expr1.checkType(new WACC_BaseType(BaseType.BOOL))) {
            semanticError("OR, AND operation can only be applied two booleans", ctx.logicalOper().getStart().getLine()
                    , ctx.logicalOper().getStart().getCharPositionInLine());
        }

        return new WACC_BaseType(BaseType.BOOL);
    }


    @Override
    public WACC_Type visitBRACKETEXPR(@NotNull BRACKETEXPRContext ctx) {
        return visit(ctx.expr());
    }

    @Override
    public WACC_Type visitPAIRARRAYTYPE(@NotNull PAIRARRAYTYPEContext ctx) {
        return visit(ctx.type());
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
            WACC_Type type = visit(expr);
            if (!(type.checkType(new WACC_BaseType(BaseType.INT)))) {
                semanticError(expr.getText()
                                + " should be an int",
                        expr.getStop().getLine(),
                        expr.getStop().getCharPositionInLine());
            }
        }

        return var.getType();
    }

    @Override
    public WACC_Type visitArrayLiter(@NotNull ArrayLiterContext ctx) {
        if (ctx.expr(0) == null) return new WACC_ArrayType(null);

        WACC_Type fstType = visit(ctx.expr(0));

        for (int i = 1; i < ctx.expr().size(); i++) {
            if (!(fstType.checkType(visit(ctx.expr(i))))) {
                semanticError("different types in array at ",
                        ctx.expr(i).getStop().getLine(),
                        ctx.expr(i).getStop().getCharPositionInLine());
            }
        }

        return new WACC_ArrayType(fstType);
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
