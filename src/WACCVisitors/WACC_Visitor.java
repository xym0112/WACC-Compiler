package WACCVisitors;

import WACCVisitors.Symbols.WACC_Char;
import WACCVisitors.Symbols.WACC_Type;
import antlr.WACCParser;
import antlr.WACCParserBaseVisitor;
import org.antlr.v4.runtime.misc.NotNull;


public class WACC_Visitor extends WACCParserBaseVisitor<WACC_Type> {
    @Override
    public WACC_Type visitPRINT(@NotNull WACCParser.PRINTContext ctx) {
        return super.visitPRINT(ctx);
    }

    @Override
    public WACC_Type visitArgList(@NotNull WACCParser.ArgListContext ctx) {
        return super.visitArgList(ctx);
    }

    @Override
    public WACC_Type visitArrayLiter(@NotNull WACCParser.ArrayLiterContext ctx) {
        return super.visitArrayLiter(ctx);
    }

    @Override
    public WACC_Type visitRHSCALLFUNC(@NotNull WACCParser.RHSCALLFUNCContext ctx) {
        return super.visitRHSCALLFUNC(ctx);
    }

    @Override
    public WACC_Type visitBIOPOR(@NotNull WACCParser.BIOPORContext ctx) {
        return super.visitBIOPOR(ctx);
    }

    @Override
    public WACC_Type visitArrayElem(@NotNull WACCParser.ArrayElemContext ctx) {
        return super.visitArrayElem(ctx);
    }

    @Override
    public WACC_Type visitLHSPAIRELEM(@NotNull WACCParser.LHSPAIRELEMContext ctx) {
        return super.visitLHSPAIRELEM(ctx);
    }

    @Override
    public WACC_Type visitPAIRFST(@NotNull WACCParser.PAIRFSTContext ctx) {
        return super.visitPAIRFST(ctx);
    }

    @Override
    public WACC_Type visitUNOPADD(@NotNull WACCParser.UNOPADDContext ctx) {
        return super.visitUNOPADD(ctx);
    }

    @Override
    public WACC_Type visitBIOPGE(@NotNull WACCParser.BIOPGEContext ctx) {
        return super.visitBIOPGE(ctx);
    }

    @Override
    public WACC_Type visitBIOPMOD(@NotNull WACCParser.BIOPMODContext ctx) {
        return super.visitBIOPMOD(ctx);
    }

    @Override
    public WACC_Type visitBOOLLITER(@NotNull WACCParser.BOOLLITERContext ctx) {
        return new WACC_Char();
    }

    @Override
    public WACC_Type visitTYPEARRAY(@NotNull WACCParser.TYPEARRAYContext ctx) {
        return super.visitTYPEARRAY(ctx);
    }

    @Override
    public WACC_Type visitUNOPCHR(@NotNull WACCParser.UNOPCHRContext ctx) {
        return super.visitUNOPCHR(ctx);
    }

    @Override
    public WACC_Type visitRHSEXPR(@NotNull WACCParser.RHSEXPRContext ctx) {
        return super.visitRHSEXPR(ctx);
    }

    @Override
    public WACC_Type visitBIOPGT(@NotNull WACCParser.BIOPGTContext ctx) {
        return super.visitBIOPGT(ctx);
    }

    @Override
    public WACC_Type visitASSIGNVAR(@NotNull WACCParser.ASSIGNVARContext ctx) {
        return super.visitASSIGNVAR(ctx);
    }

    @Override
    public WACC_Type visitIF(@NotNull WACCParser.IFContext ctx) {
        return super.visitIF(ctx);
    }

    @Override
    public WACC_Type visitBIOPAND(@NotNull WACCParser.BIOPANDContext ctx) {
        return super.visitBIOPAND(ctx);
    }

    @Override
    public WACC_Type visitEXPRIDENT(@NotNull WACCParser.EXPRIDENTContext ctx) {
        return super.visitEXPRIDENT(ctx);
    }

    @Override
    public WACC_Type visitPAIRSND(@NotNull WACCParser.PAIRSNDContext ctx) {
        return super.visitPAIRSND(ctx);
    }

    @Override
    public WACC_Type visitBIOPLE(@NotNull WACCParser.BIOPLEContext ctx) {
        return super.visitBIOPLE(ctx);
    }

    @Override
    public WACC_Type visitBINARYOP(@NotNull WACCParser.BINARYOPContext ctx) {
        return super.visitBINARYOP(ctx);
    }

    @Override
    public WACC_Type visitUNOPSUB(@NotNull WACCParser.UNOPSUBContext ctx) {
        return super.visitUNOPSUB(ctx);
    }

    @Override
    public WACC_Type visitREAD(@NotNull WACCParser.READContext ctx) {
        return super.visitREAD(ctx);
    }

    @Override
    public WACC_Type visitParamList(@NotNull WACCParser.ParamListContext ctx) {
        return super.visitParamList(ctx);
    }

    @Override
    public WACC_Type visitSTRLITER(@NotNull WACCParser.STRLITERContext ctx) {
        return super.visitSTRLITER(ctx);
    }

    @Override
    public WACC_Type visitLHSIDENT(@NotNull WACCParser.LHSIDENTContext ctx) {
        return super.visitLHSIDENT(ctx);
    }

    @Override
    public WACC_Type visitPAIRPAIR(@NotNull WACCParser.PAIRPAIRContext ctx) {
        return super.visitPAIRPAIR(ctx);
    }

    @Override
    public WACC_Type visitBIOPLT(@NotNull WACCParser.BIOPLTContext ctx) {
        return super.visitBIOPLT(ctx);
    }

    @Override
    public WACC_Type visitEXIT(@NotNull WACCParser.EXITContext ctx) {
        return super.visitEXIT(ctx);
    }

    @Override
    public WACC_Type visitPAIRLITER(@NotNull WACCParser.PAIRLITERContext ctx) {
        return super.visitPAIRLITER(ctx);
    }

    @Override
    public WACC_Type visitCHARLITER(@NotNull WACCParser.CHARLITERContext ctx) {
        return super.visitCHARLITER(ctx);
    }

    @Override
    public WACC_Type visitBRACKETEXPR(@NotNull WACCParser.BRACKETEXPRContext ctx) {
        return super.visitBRACKETEXPR(ctx);
    }

    @Override
    public WACC_Type visitLHSARRAYELEM(@NotNull WACCParser.LHSARRAYELEMContext ctx) {
        return super.visitLHSARRAYELEM(ctx);
    }

    @Override
    public WACC_Type visitSKIPSTAT(@NotNull WACCParser.SKIPSTATContext ctx) {
        return super.visitSKIPSTAT(ctx);
    }

    @Override
    public WACC_Type visitPRINTLN(@NotNull WACCParser.PRINTLNContext ctx) {
        return super.visitPRINTLN(ctx);
    }

    @Override
    public WACC_Type visitRHSNEWPAIR(@NotNull WACCParser.RHSNEWPAIRContext ctx) {
        return super.visitRHSNEWPAIR(ctx);
    }

    @Override
    public WACC_Type visitUNSIGNED(@NotNull WACCParser.UNSIGNEDContext ctx) {
        return super.visitUNSIGNED(ctx);
    }

    @Override
    public WACC_Type visitUNOPEXCLAMATION(@NotNull WACCParser.UNOPEXCLAMATIONContext ctx) {
        return super.visitUNOPEXCLAMATION(ctx);
    }

    @Override
    public WACC_Type visitCREATEVAR(@NotNull WACCParser.CREATEVARContext ctx) {
        return super.visitCREATEVAR(ctx);
    }

    @Override
    public WACC_Type visitBEGIN(@NotNull WACCParser.BEGINContext ctx) {
        return super.visitBEGIN(ctx);
    }

    @Override
    public WACC_Type visitFREE(@NotNull WACCParser.FREEContext ctx) {
        return super.visitFREE(ctx);
    }

    @Override
    public WACC_Type visitUNOPORD(@NotNull WACCParser.UNOPORDContext ctx) {
        return super.visitUNOPORD(ctx);
    }

    @Override
    public WACC_Type visitRETURN(@NotNull WACCParser.RETURNContext ctx) {
        return super.visitRETURN(ctx);
    }

    @Override
    public WACC_Type visitBIOPSUB(@NotNull WACCParser.BIOPSUBContext ctx) {
        return super.visitBIOPSUB(ctx);
    }

    @Override
    public WACC_Type visitParam(@NotNull WACCParser.ParamContext ctx) {
        return super.visitParam(ctx);
    }

    @Override
    public WACC_Type visitSEQUENCE(@NotNull WACCParser.SEQUENCEContext ctx) {
        return super.visitSEQUENCE(ctx);
    }

    @Override
    public WACC_Type visitTYPEBASE(@NotNull WACCParser.TYPEBASEContext ctx) {
        return super.visitTYPEBASE(ctx);
    }

    @Override
    public WACC_Type visitBIOPMUL(@NotNull WACCParser.BIOPMULContext ctx) {
        return super.visitBIOPMUL(ctx);
    }

    @Override
    public WACC_Type visitPAIRARRAYTYPE(@NotNull WACCParser.PAIRARRAYTYPEContext ctx) {
        return super.visitPAIRARRAYTYPE(ctx);
    }

    @Override
    public WACC_Type visitBIOPDIV(@NotNull WACCParser.BIOPDIVContext ctx) {
        return super.visitBIOPDIV(ctx);
    }

    @Override
    public WACC_Type visitBIOPNQ(@NotNull WACCParser.BIOPNQContext ctx) {
        return super.visitBIOPNQ(ctx);
    }

    @Override
    public WACC_Type visitRHSARRLITER(@NotNull WACCParser.RHSARRLITERContext ctx) {
        return super.visitRHSARRLITER(ctx);
    }

    @Override
    public WACC_Type visitEXPRARRAYELEM(@NotNull WACCParser.EXPRARRAYELEMContext ctx) {
        return super.visitEXPRARRAYELEM(ctx);
    }

    @Override
    public WACC_Type visitRHSPAIR(@NotNull WACCParser.RHSPAIRContext ctx) {
        return super.visitRHSPAIR(ctx);
    }

    @Override
    public WACC_Type visitProg(@NotNull WACCParser.ProgContext ctx) {
        return super.visitProg(ctx);
    }

    @Override
    public WACC_Type visitPairType(@NotNull WACCParser.PairTypeContext ctx) {
        return super.visitPairType(ctx);
    }

    @Override
    public WACC_Type visitPAIRBASETYPE(@NotNull WACCParser.PAIRBASETYPEContext ctx) {
        return super.visitPAIRBASETYPE(ctx);
    }

    @Override
    public WACC_Type visitBIOPEQUAL(@NotNull WACCParser.BIOPEQUALContext ctx) {
        return super.visitBIOPEQUAL(ctx);
    }

    @Override
    public WACC_Type visitUNOPLEN(@NotNull WACCParser.UNOPLENContext ctx) {
        return super.visitUNOPLEN(ctx);
    }

    @Override
    public WACC_Type visitBIOPADD(@NotNull WACCParser.BIOPADDContext ctx) {
        return super.visitBIOPADD(ctx);
    }

    @Override
    public WACC_Type visitFunc(@NotNull WACCParser.FuncContext ctx) {
        return super.visitFunc(ctx);
    }

    @Override
    public WACC_Type visitTYPEPAIR(@NotNull WACCParser.TYPEPAIRContext ctx) {
        return super.visitTYPEPAIR(ctx);
    }

    @Override
    public WACC_Type visitUNARYOP(@NotNull WACCParser.UNARYOPContext ctx) {
        return super.visitUNARYOP(ctx);
    }

    @Override
    public WACC_Type visitWHILE(@NotNull WACCParser.WHILEContext ctx) {
        return super.visitWHILE(ctx);
    }
}
