package antlr;

import org.antlr.v4.runtime.misc.NotNull;

/**
 * Created by bb2015 on 05/11/16.
 */
public class WACCVisitor extends WACCParserBaseVisitor {

    @Override
    public Object visitUNSIGNED(@NotNull WACCParser.UNSIGNEDContext ctx) {
        System.out.println("Doing stuff in unsigned");
        return super.visitUNSIGNED(ctx);
    }

    @Override
    public Object visitEXIT(@NotNull WACCParser.EXITContext ctx) {
        System.out.println("Doing stuff before in EXIT");
        return super.visitEXIT(ctx);

    }

    @Override
    public Object visitProg(@NotNull WACCParser.ProgContext ctx) {
        System.out.println("Entering shite");
        return super.visitProg(ctx);

    }
}
