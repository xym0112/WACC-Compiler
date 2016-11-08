package WACCSemantics;

import WACCSemantics.types.WACC_Function;
import WACCSemantics.types.WACC_Type;
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
        for (FuncContext func : ctx.func()) {
            if (currentST.lookUpAllFunc(func.Ident().getText()) != null) {
                visit(func);
            }
        }



        return super.visitProg(ctx);
    }

    @Override
    public WACC_Type visitFunc(@NotNull FuncContext ctx) {
        WACC_Function function = new WACC_Function(ctx.type(), )

        return super.visitFunc(ctx);
    }
}
