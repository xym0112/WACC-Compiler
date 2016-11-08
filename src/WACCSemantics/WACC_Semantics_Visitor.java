package WACCSemantics;

import WACCSemantics.types.WACC_Type;
import antlr.WACCParser;
import antlr.WACCParserBaseVisitor;
import org.antlr.v4.runtime.misc.NotNull;

public class WACC_Semantics_Visitor extends WACCParserBaseVisitor<WACC_Type> {

    SymbolTable st;



    @Override
    public WACC_Type visitProg(@NotNull WACCParser.ProgContext ctx) {
        return super.visitProg(ctx);
    }
}
