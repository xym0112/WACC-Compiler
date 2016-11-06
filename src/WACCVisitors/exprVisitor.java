package WACCVisitors;

import antlr.WACCParser;
import antlr.WACCParserBaseVisitor;
import org.antlr.v4.runtime.misc.NotNull;

public class exprVisitor extends WACCParserBaseVisitor {
    @Override
    protected Object defaultResult() {
        return super.defaultResult();
    }

    @Override
    public Object visitUNSIGNED(@NotNull WACCParser.UNSIGNEDContext ctx) {


        return super.visitUNSIGNED(ctx);
    }

    @Override
    public Object visitBOOLLITER(@NotNull WACCParser.BOOLLITERContext ctx) {
        return super.visitBOOLLITER(ctx);
    }

    @Override
    public Object visitEXPRIDENT(@NotNull WACCParser.EXPRIDENTContext ctx) {
        return super.visitEXPRIDENT(ctx);
    }

    @Override
    public Object visitBINARYOP(@NotNull WACCParser.BINARYOPContext ctx) {
        return super.visitBINARYOP(ctx);
    }

    @Override
    public Object visitSTRLITER(@NotNull WACCParser.STRLITERContext ctx) {
        return super.visitSTRLITER(ctx);
    }

    @Override
    public Object visitPAIRLITER(@NotNull WACCParser.PAIRLITERContext ctx) {
        return super.visitPAIRLITER(ctx);
    }

    @Override
    public Object visitCHARLITER(@NotNull WACCParser.CHARLITERContext ctx) {
        return super.visitCHARLITER(ctx);
    }

    @Override
    public Object visitBRACKETEXPR(@NotNull WACCParser.BRACKETEXPRContext ctx) {
        return super.visitBRACKETEXPR(ctx);
    }

    @Override
    public Object visitEXPRARRAYELEM(@NotNull WACCParser.EXPRARRAYELEMContext ctx) {
        return super.visitEXPRARRAYELEM(ctx);
    }

    @Override
    public Object visitUNARYOP(@NotNull WACCParser.UNARYOPContext ctx) {
        return super.visitUNARYOP(ctx);
    }
}
