import antlr.WACCParser;
import antlr.WACCParserBaseVisitor;
import org.antlr.v4.runtime.misc.NotNull;
import org.omg.CORBA.StringHolder;

public class SyntaxVisitor extends WACCParserBaseVisitor<Boolean>{

    private String functionName = null;

    @Override
    public Boolean visitProg(@NotNull WACCParser.ProgContext ctx) {

        //Checking if no return statement is passed in main
        Boolean result = visit(ctx.stat());

        if (result) {
            System.out.println("Syntax Error: Return statement passed in Main Function");
            System.exit(100);
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
            System.out.println("Syntax Error: Return statement missing in Function '" + functionName + "'");
            System.exit(100);
        }
        return true;
    }


    @Override
    public Boolean visitSKIPSTAT(@NotNull WACCParser.SKIPSTATContext ctx) {
        return false;
    }

    @Override
    public Boolean visitEXIT(@NotNull WACCParser.EXITContext ctx) {
        return true;
    }

    @Override
    public Boolean visitCreateVariable(@NotNull WACCParser.CreateVariableContext ctx) {
        return false;
    }

    @Override
    public Boolean visitASSIGNVAR(@NotNull WACCParser.ASSIGNVARContext ctx) {
        return false;
    }

    @Override
    public Boolean visitFREE(@NotNull WACCParser.FREEContext ctx) {
        return false;
    }

    @Override
    public Boolean visitRETURN(@NotNull WACCParser.RETURNContext ctx) {
        return true;
    }

    @Override
    public Boolean visitPRINT(@NotNull WACCParser.PRINTContext ctx) {
        return false;
    }

    @Override
    public Boolean visitPRINTLN(@NotNull WACCParser.PRINTLNContext ctx) {
        return false;
    }

    @Override
    public Boolean visitBEGIN(@NotNull WACCParser.BEGINContext ctx) {
        return visit(ctx.stat());
    }

    @Override
    public Boolean visitSEQUENCE(@NotNull WACCParser.SEQUENCEContext ctx) {
        if (visit(ctx.stat(0))){
            System.out.println("Syntax Error: Cannot Implement code after Return statement in Function '" +  functionName + "'");
            System.exit(100);
        }
        return visit(ctx.stat(1));
    }

    @Override
    public Boolean visitIF(@NotNull WACCParser.IFContext ctx) {
        return visit(ctx.ifStatement().stat(0)) && visit(ctx.ifStatement().stat(1));
    }

    @Override
    public Boolean visitWHILE(@NotNull WACCParser.WHILEContext ctx) {
        return visit(ctx.whileStatement().stat());
    }
}
