package WACCSemantics;

import antlr.WACCParser;
import antlr.WACCParserBaseVisitor;
import org.antlr.v4.runtime.misc.NotNull;

enum RETURNTYPE {
    RETURN,
    EXIT,
    NONE
}

class SyntaxVisitor extends WACCParserBaseVisitor < RETURNTYPE > {

    private String functionName = null;
    private final long MAXINT = 2147483647;


    @Override
    public RETURNTYPE visitProg(@NotNull WACCParser.ProgContext ctx) {
        //Checking if no return statement is passed in main
        visit(ctx.stat());

        ctx.func().forEach(this::visit);

        return RETURNTYPE.NONE;
    }

    @Override
    public RETURNTYPE visitFunc(@NotNull WACCParser.FuncContext ctx) {
        functionName = ctx.Ident().getText();
        RETURNTYPE result = visit(ctx.stat());

        // functions need to end with return or exit
        if (!(result == RETURNTYPE.RETURN || result == RETURNTYPE.EXIT)) {
            printSyntaxError("return statement missing in function '" + functionName + "'",
                    ctx.Ident().getSymbol().getLine(),
                    ctx.Ident().getSymbol().getCharPositionInLine());
        }

        return RETURNTYPE.NONE;
    }

    // Statements

    @Override
    public RETURNTYPE visitSKIPSTAT(@NotNull WACCParser.SKIPSTATContext ctx) {
        return RETURNTYPE.NONE;
    }

    @Override
    public RETURNTYPE visitEXIT(@NotNull WACCParser.EXITContext ctx) {
        visitChildren(ctx);
        return RETURNTYPE.EXIT;
    }

    @Override
    public RETURNTYPE visitCREATEVAR(@NotNull WACCParser.CREATEVARContext ctx) {
        visitChildren(ctx);
        return RETURNTYPE.NONE;
    }

    @Override
    public RETURNTYPE visitASSIGNVAR(@NotNull WACCParser.ASSIGNVARContext ctx) {
        visitChildren(ctx);
        return RETURNTYPE.NONE;
    }

    @Override
    public RETURNTYPE visitREAD(@NotNull WACCParser.READContext ctx) {
        visitChildren(ctx);
        return RETURNTYPE.NONE;
    }

    @Override
    public RETURNTYPE visitFREE(@NotNull WACCParser.FREEContext ctx) {
        visitChildren(ctx);
        return RETURNTYPE.NONE;
    }

    @Override
    public RETURNTYPE visitRETURN(@NotNull WACCParser.RETURNContext ctx) {
        return RETURNTYPE.RETURN;
    }

    @Override
    public RETURNTYPE visitPRINT(@NotNull WACCParser.PRINTContext ctx) {
        visitChildren(ctx);
        return RETURNTYPE.NONE;
    }

    @Override
    public RETURNTYPE visitPRINTLN(@NotNull WACCParser.PRINTLNContext ctx) {
        visitChildren(ctx);
        return RETURNTYPE.NONE;
    }

    @Override
    public RETURNTYPE visitIF(@NotNull WACCParser.IFContext ctx) {
        RETURNTYPE expr1 = visit(ctx.stat(0));
        RETURNTYPE expr2 = visit(ctx.stat(1));

        // if either body of if return nothing then return nothing
        if (expr1 == RETURNTYPE.NONE || expr2 == RETURNTYPE.NONE) return RETURNTYPE.NONE;

        return RETURNTYPE.RETURN;
    }

    @Override
    public RETURNTYPE visitWHILE(@NotNull WACCParser.WHILEContext ctx) {
        return visit(ctx.stat());
    }

    @Override
    public RETURNTYPE visitBEGIN(@NotNull WACCParser.BEGINContext ctx) {
        return visit(ctx.stat());
    }

    @Override
    public RETURNTYPE visitSEQUENCE(@NotNull WACCParser.SEQUENCEContext ctx) {
        RETURNTYPE expr1 = visit(ctx.stat(0));
        RETURNTYPE expr2 = visit(ctx.stat(1));

        if ((ctx.getParent() instanceof WACCParser.ProgContext) && expr1 == RETURNTYPE.RETURN) {
            // special semantic case to catch return statements in the top level scope
            System.out.println("Semantic error: return statement cannot be in main on line " + ctx.stat(1).getStop().getText() + " and position " + ctx.stat(1).getStop().getText());
            System.exit(200);
        }

        // if the first expr in sequence is return and the second exists
        if (expr1 == RETURNTYPE.RETURN && expr2 != null) {
            printSyntaxError("cannot implement code after return statement in function '" + functionName + "'",
                    ctx.stat(0).getStop().getLine(),
                    ctx.stat(0).getStop().getCharPositionInLine());
        }

        return expr2;
    }

    @Override
    public RETURNTYPE visitUNARYOP(@NotNull WACCParser.UNARYOPContext ctx) {
        // if the expression is a unary operation and its operation is minus
        if (ctx.unaryOper().getText().equals("-") && ctx.expr() instanceof WACCParser.UNSIGNEDContext) {
            long intVal = Long.parseLong(ctx.expr().getText());

            if (intVal > MAXINT + 1) {
                printSyntaxError("this integer must be between -2^31 and 2^31 + 1",
                        ctx.unaryOper().getStop().getLine(),
                        ctx.unaryOper().getStop().getCharPositionInLine());
            }
        }

        // if the unary operation is plus and the expr is string
        if (ctx.unaryOper().getText().equals("+") && ctx.expr() instanceof WACCParser.STRLITERContext) {
            printSyntaxError("++ cannot be applied to strings",
                    ctx.unaryOper().getStop().getLine(),
                    ctx.unaryOper().getStop().getCharPositionInLine());
        }

        return RETURNTYPE.NONE;
    }

    // Big int assignment

    @Override
    public RETURNTYPE visitUNSIGNED(@NotNull WACCParser.UNSIGNEDContext ctx) {
        // if the parent is a unary operation, then don't run checks as checking
        // has already been performed
        if (ctx.getParent() instanceof WACCParser.UNARYOPContext) return RETURNTYPE.NONE;

        long intVal = Long.parseLong(ctx.UNSIGNED().getText());

        if (intVal > MAXINT) {
            printSyntaxError("this integer must be between -2^31 and 2^31 + 1",
                    ctx.UNSIGNED().getSymbol().getLine(),
                    ctx.UNSIGNED().getSymbol().getCharPositionInLine());
        }

        return RETURNTYPE.NONE;
    }

    private void printSyntaxError(String msg, int line, int pos) {
        System.out.println("Syntax error: " + msg + " at line " + line + " and position " + pos);
        System.exit(100);
    }
}