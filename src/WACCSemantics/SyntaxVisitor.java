package WACCSemantics;

import antlr.WACCParser;
import antlr.WACCParserBaseVisitor;
import org.antlr.v4.runtime.misc.NotNull;

import java.math.BigInteger;

public class SyntaxVisitor extends WACCParserBaseVisitor<Boolean>{

    private String functionName = null;
    private final double MAXINT = Math.pow(31, 2) - 1;
    private final double MININT = - Math.pow(31, 2);

    @Override
    public Boolean visitProg(@NotNull WACCParser.ProgContext ctx) {

        //Checking if no return statement is passed in main
        Boolean result = visit(ctx.stat());

        if (result) {
            printSyntaxError("return statement passed in Main Function");
        }

        for (WACCParser.FuncContext func: ctx.func()){
            visit(func);
        }

        return true;
    }


    @Override
    public Boolean visitFunc(@NotNull WACCParser.FuncContext ctx) {

        functionName = ctx.Ident().getText();
        Boolean result = visit(ctx.stat());
        if (!result){
            printSyntaxError("return statement missing in Function '" + functionName + "'");
        }
        return true;
    }

    // Statements

    @Override
    public Boolean visitSKIPSTAT(@NotNull WACCParser.SKIPSTATContext ctx) {
        return false;
    }

    @Override
    public Boolean visitEXIT(@NotNull WACCParser.EXITContext ctx) {
        visitChildren(ctx);
        return true;
    }

    @Override
    public Boolean visitCREATEVAR(@NotNull WACCParser.CREATEVARContext ctx) {
        visitChildren(ctx);
        return false;
    }

    @Override
    public Boolean visitASSIGNVAR(@NotNull WACCParser.ASSIGNVARContext ctx) {
        visitChildren(ctx);
        return false;
    }

    @Override
    public Boolean visitREAD(@NotNull WACCParser.READContext ctx) {
        visitChildren(ctx);
        return false;
    }

    @Override
    public Boolean visitFREE(@NotNull WACCParser.FREEContext ctx) {
        visitChildren(ctx);
        return false;
    }

    @Override
    public Boolean visitRETURN(@NotNull WACCParser.RETURNContext ctx) {
        return true;
    }

    @Override
    public Boolean visitPRINT(@NotNull WACCParser.PRINTContext ctx) {
        visitChildren(ctx);
        return false;
    }

    @Override
    public Boolean visitPRINTLN(@NotNull WACCParser.PRINTLNContext ctx) {
        visitChildren(ctx);
        return false;
    }

    @Override
    public Boolean visitIF(@NotNull WACCParser.IFContext ctx) {
        return visit(ctx.stat(0)) && visit(ctx.stat(1));
    }

    @Override
    public Boolean visitWHILE(@NotNull WACCParser.WHILEContext ctx) {
        return visit(ctx.stat());
    }

    @Override
    public Boolean visitBEGIN(@NotNull WACCParser.BEGINContext ctx) {
        return visit(ctx.stat());
    }

    @Override
    public Boolean visitSEQUENCE(@NotNull WACCParser.SEQUENCEContext ctx) {
        if (visit(ctx.stat(0))){
            printSyntaxError("cannot Implement code after Return statement in Function '" +  functionName + "'");
        }

        return visit(ctx.stat(1));
    }

    // Big int assignment

    @Override
    public Boolean visitUNSIGNED(@NotNull WACCParser.UNSIGNEDContext ctx) {
        double intVal = Double.parseDouble(ctx.UNSIGNED().getText());

        if (intVal > MAXINT || intVal < MININT) {
            printSyntaxError("this integermust be between -2^31 and 2^31 + 1");
        }

        return super.visitUNSIGNED(ctx);
    }

    private void printSyntaxError(String msg) {
        System.out.println("Syntax error: " + msg);
        System.exit(100);
    }
}
