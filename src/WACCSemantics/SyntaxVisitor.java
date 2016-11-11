package WACCSemantics;

import antlr.WACCParser;
import antlr.WACCParserBaseVisitor;
import org.antlr.v4.runtime.misc.NotNull;

enum RETURNTYPE {
    RETURN,
    EXIT,
    NONE
}

public class SyntaxVisitor extends WACCParserBaseVisitor < RETURNTYPE > {

    private String functionName = null;
    private final long MAXINT = 2147483647;


    @Override
    public RETURNTYPE visitProg(@NotNull WACCParser.ProgContext ctx) {
        //Checking if no return statement is passed in main
        visit(ctx.stat());

        for (WACCParser.FuncContext func: ctx.func()) {
            visit(func);
        }

        return RETURNTYPE.NONE;
    }

    @Override
    public RETURNTYPE visitFunc(@NotNull WACCParser.FuncContext ctx) {

        functionName = ctx.Ident().getText();
        RETURNTYPE result = visit(ctx.stat());
        if (!(result == RETURNTYPE.RETURN || result == RETURNTYPE.EXIT)) {
            printSyntaxError("return statement missing in Function '" + functionName + "'",
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

        if (expr1 == RETURNTYPE.NONE) {
            return RETURNTYPE.NONE;
        } else if (expr2 == RETURNTYPE.NONE) {
            return RETURNTYPE.NONE;
        } else {
            return RETURNTYPE.RETURN;
        }
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
        RETURNTYPE expr = visit(ctx.stat(0));
        RETURNTYPE expr2 = visit(ctx.stat(1));

        if ((ctx.getParent() instanceof WACCParser.ProgContext) && expr == RETURNTYPE.RETURN) {
            System.out.println("Semantic error: return statement cannot be in main on line " + ctx.stat(1).getStop().getText() + " and position " + ctx.stat(1).getStop().getText());
            System.exit(200);
        }

        if (expr == RETURNTYPE.RETURN) {
            printSyntaxError("cannot implement code after Return statement in Function '" + functionName + "'",
                    ctx.stat(0).getStop().getLine(),
                    ctx.stat(0).getStop().getCharPositionInLine());
        }

        return expr2;
    }

    @Override
    public RETURNTYPE visitUNARYOP(@NotNull WACCParser.UNARYOPContext ctx) {
        if (ctx.unaryOper().getText().equals("-") && ctx.expr() instanceof WACCParser.UNSIGNEDContext) {
            long intVal = Long.parseLong(ctx.expr().getText());

            if (intVal > MAXINT + 1) {
                printSyntaxError("this integer must be between -2^31 and 2^31 + 1",
                        ctx.unaryOper().getStop().getLine(),
                        ctx.unaryOper().getStop().getCharPositionInLine());
            }
        }

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