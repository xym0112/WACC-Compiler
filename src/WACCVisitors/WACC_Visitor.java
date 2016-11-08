package WACCVisitors;

import WACCVisitors.Symbols.SymbolTable;
import WACCVisitors.Symbols.*;
import antlr.WACCParser;
import antlr.WACCParserBaseVisitor;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.jetbrains.annotations.NotNull;


public class WACC_Visitor extends WACCParserBaseVisitor<WACC_Type> {

    SymbolTable ctxsymbolTable;



    public WACC_Visitor() {
        ctxsymbolTable = new SymbolTable();
        initCtxSymbolTable();
    }

    private void initCtxSymbolTable(){
        return;
    }

    @Override
    public WACC_Type visitPRINT(@NotNull WACCParser.PRINTContext ctx) {
        super.visitPRINT(ctx);
        return null;
    }

    @Override
    public WACC_Type visitArgList(@NotNull WACCParser.ArgListContext ctx) {
        return super.visitArgList(ctx);
    }

    @Override
    public WACC_Type visitArrayLiter(@NotNull WACCParser.ArrayLiterContext ctx) {
        if (ctx.COMMA(0) != null) {
            return new WACC_Array(visit(ctx.expr(0)));
        }else {
            return null;
        }
        //TODO
    }

    @Override
    public WACC_Type visitRHSCALLFUNC(@NotNull WACCParser.RHSCALLFUNCContext ctx) {
        return ((WACC_Function) ctxsymbolTable.lookUpAll(ctx.Ident().getText())).getReturnType();
    }

    @Override
    public WACC_Type visitBIOPOR(@NotNull WACCParser.BIOPORContext ctx) {
        return checkBinOpBool(ctx.OR());
    }

    @Override
    public WACC_Type visitArrayElem(@NotNull WACCParser.ArrayElemContext ctx) {
        // pre ctx is in array
        return ((WACC_Array)ctxsymbolTable.lookUpAll(ctx.Ident().getText())).getElementType();
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
        if((visit(ctx.getChild(0))) instanceof WACC_Int){
            return new WACC_Int();
        } else {
            System.out.println("Semantic Error");
            return null;
        }
    }

    @Override
    public WACC_Type visitBIOPGE(@NotNull WACCParser.BIOPGEContext ctx) {
        return checkBinOpInEqSigns(ctx.GE());
    }

    @Override
    public WACC_Type visitBIOPMOD(@NotNull WACCParser.BIOPMODContext ctx) {
        return checkBinOpInt(ctx.MOD());
    }

    @Override
    public WACC_Type visitBOOLLITER(@NotNull WACCParser.BOOLLITERContext ctx) {
        return new WACC_Bool();
    }

    @Override
    public WACC_Type visitTYPEARRAY(@NotNull WACCParser.TYPEARRAYContext ctx) {
        return new WACC_Array(visit(ctx.type()));
    }

    @Override
    public WACC_Type visitUNOPCHR(@NotNull WACCParser.UNOPCHRContext ctx) {
        if((visit(ctx.getChild(0)) instanceof WACC_Char)){
            return new WACC_Int();
        } else {
            System.out.println("Semantic Error");
            return null;
        }
    }

    @Override
    public WACC_Type visitRHSEXPR(@NotNull WACCParser.RHSEXPRContext ctx) {
        return super.visitRHSEXPR(ctx);
    }

    @Override
    public WACC_Type visitBIOPGT(@NotNull WACCParser.BIOPGTContext ctx) {
        return checkBinOpInEqSigns(ctx.GT());
    }

    //TODO
    @Override
    public WACC_Type visitASSIGNVAR(@NotNull WACCParser.ASSIGNVARContext ctx) {
        String lhsText = ctx.assignLhs().getText();
        visit(ctx.assignRhs());
        WACC_Type rhs = visit(ctx.assignRhs());
        ctxsymbolTable.add(lhsText,rhs);
        return null;
    }

    @Override
    public WACC_Type visitIF(@NotNull WACCParser.IFContext ctx) {
        if((visit(ctx.getChild(1)) instanceof WACC_Bool) && (visit(ctx.getChild(2)) instanceof WACC_Bool)) {
            return new WACC_Bool();
        } else {
            System.out.println("Semantics error:");
            return null;
        }
    }

    @Override
    public WACC_Type visitBIOPAND(@NotNull WACCParser.BIOPANDContext ctx) {
        return checkBinOpBool(ctx.AND());
    }

    @Override
    public WACC_Type visitEXPRIDENT(@NotNull WACCParser.EXPRIDENTContext ctx) {
        String name = ctx.Ident().getText();
        return (WACC_Type)ctxsymbolTable.lookUpAll(name);
    }

    @Override
    public WACC_Type visitPAIRSND(@NotNull WACCParser.PAIRSNDContext ctx) {
        return super.visitPAIRSND(ctx);
    }

    @Override
    public WACC_Type visitBIOPLE(@NotNull WACCParser.BIOPLEContext ctx) {
        return checkBinOpInEqSigns(ctx.LE());
    }

    @Override
    public WACC_Type visitBINARYOP(@NotNull WACCParser.BINARYOPContext ctx) {
        return super.visitBINARYOP(ctx);
    }

    @Override
    public WACC_Type visitUNOPSUB(@NotNull WACCParser.UNOPSUBContext ctx) {
        if((visit(ctx.getChild(0)) instanceof WACC_Int)){
            return new WACC_Int();
        } else {
            System.out.println("Semantic Error");
            return null;
        }
    }

    @Override
    public WACC_Type visitREAD(@NotNull WACCParser.READContext ctx) {
        return null;
    }

    @Override
    public WACC_Type visitParamList(@NotNull WACCParser.ParamListContext ctx) {
        super.visitParamList(ctx);
        return null;
    }

    @Override
    public WACC_Type visitSTRLITER(@NotNull WACCParser.STRLITERContext ctx) {
        return new WACC_String();
    }

    @Override
    public WACC_Type visitLHSIDENT(@NotNull WACCParser.LHSIDENTContext ctx) {
        super.visitLHSIDENT(ctx);
        return null;
    }

    @Override
    public WACC_Type visitPAIRPAIR(@NotNull WACCParser.PAIRPAIRContext ctx) {
        return super.visitPAIRPAIR(ctx);
    }

    @Override
    public WACC_Type visitBIOPLT(@NotNull WACCParser.BIOPLTContext ctx) {
        return checkBinOpInEqSigns(ctx.LT());
    }

    @Override
    public WACC_Type visitEXIT(@NotNull WACCParser.EXITContext ctx) {

        super.visitEXIT(ctx);
        return null;
    }

    @Override
    public WACC_Type visitPAIRLITER(@NotNull WACCParser.PAIRLITERContext ctx) {
        super.visitPAIRLITER(ctx);
        return null;
    }

    @Override
    public WACC_Type visitCHARLITER(@NotNull WACCParser.CHARLITERContext ctx) {
        return new WACC_Char();
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
        super.visitSKIPSTAT(ctx);
        return null;
    }

    @Override
    public WACC_Type visitPRINTLN(@NotNull WACCParser.PRINTLNContext ctx) {
        super.visitPRINTLN(ctx);
        return null;
    }

    @Override
    public WACC_Type visitRHSNEWPAIR(@NotNull WACCParser.RHSNEWPAIRContext ctx) {
        WACC_Type rhsType = visit(ctx.expr(0));
        WACC_Type lhsType = visit(ctx.expr(1));
        return new WACC_Pair(rhsType, lhsType);
    }

    @Override
    public WACC_Type visitUNSIGNED(@NotNull WACCParser.UNSIGNEDContext ctx) {
        return new WACC_Int();
    }

    @Override
    public WACC_Type visitUNOPEXCLAMATION(@NotNull WACCParser.UNOPEXCLAMATIONContext ctx) {
        if((visit(ctx.getChild(0)) instanceof WACC_Bool)){
            return new WACC_Bool();
        } else {
            System.out.println("Semantic Error");
            return null;

        }
    }

    @Override
    public WACC_Type visitCREATEVAR(@NotNull WACCParser.CREATEVARContext ctx) {
        WACC_Type rhs = visit(ctx.assignRhs());
        if (visit(ctx.type()).getClass().equals(rhs)) {
            ctxsymbolTable.add(ctx.Ident().getText(), rhs);
        }
        return null;
    }

    @Override
    public WACC_Type visitBEGIN(@NotNull WACCParser.BEGINContext ctx) {

        //TODO CHANGE CONTEXT
        ctxsymbolTable = new SymbolTable(ctxsymbolTable);
        super.visitBEGIN(ctx);
        ctxsymbolTable = ctxsymbolTable.getEncSymTable();
        return null;
    }

    @Override
    public WACC_Type visitFREE(@NotNull WACCParser.FREEContext ctx) {
        super.visitFREE(ctx);
        return null;
    }

    @Override
    public WACC_Type visitUNOPORD(@NotNull WACCParser.UNOPORDContext ctx) {
        if((visit(ctx.getChild(0)) instanceof WACC_Char)){
            return new WACC_Int();
        } else {
            System.out.println("Semantic Error");
            return null;
        }
    }

    @Override
    public WACC_Type visitRETURN(@NotNull WACCParser.RETURNContext ctx) {
        super.visitRETURN(ctx);
        return null;
    }

    @Override
    public WACC_Type visitBIOPSUB(@NotNull WACCParser.BIOPSUBContext ctx) {
        return checkBinOpInt(ctx.SUB());
    }

    @Override
    public WACC_Type visitParam(@NotNull WACCParser.ParamContext ctx) {
        ctxsymbolTable.add(ctx.Ident().getText(), visit(ctx.type()));
    }

    @Override
    public WACC_Type visitSEQUENCE(@NotNull WACCParser.SEQUENCEContext ctx) {

        super.visitSEQUENCE(ctx);
        return null;
    }

    @Override
    public WACC_Type visitTYPEBASE(@NotNull WACCParser.TYPEBASEContext ctx) {
        return super.visitTYPEBASE(ctx);
    }

    @Override
    public WACC_Type visitBIOPMUL(@NotNull WACCParser.BIOPMULContext ctx) {
        return checkBinOpInt(ctx.MUL());
    }

    @Override
    public WACC_Type visitPAIRARRAYTYPE(@NotNull WACCParser.PAIRARRAYTYPEContext ctx) {
        return super.visitPAIRARRAYTYPE(ctx);
    }

    @Override
    public WACC_Type visitBIOPDIV(@NotNull WACCParser.BIOPDIVContext ctx) {
        return checkBinOpInt(ctx.DIV())         ;
    }

    @Override
    public WACC_Type visitBIOPNQ(@NotNull WACCParser.BIOPNQContext ctx) {
        return checkBinOpInEq(ctx.NOTEQUAL());
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

    //TODO
    @Override
    public WACC_Type visitProg(@NotNull WACCParser.ProgContext ctx) {
        return super.visitProg(ctx);
    }

    @Override
    public WACC_Type visitPairType(@NotNull WACCParser.PairTypeContext ctx) {
        WACC_Type fstType = visit(ctx.pairElemType(0));
        WACC_Type sndType = visit(ctx.pairElemType(1));
        return new WACC_Pair(fstType, sndType);
    }

    @Override
    public WACC_Type visitPAIRBASETYPE(@NotNull WACCParser.PAIRBASETYPEContext ctx) {
        return super.visitPAIRBASETYPE(ctx);
    }

    @Override
    public WACC_Type visitBIOPEQUAL(@NotNull WACCParser.BIOPEQUALContext ctx) {
        return checkBinOpInEq(ctx.EQUAL());
    }


    @Override
    public WACC_Type visitUNOPLEN(@NotNull WACCParser.UNOPLENContext ctx) {
        if((ctx.getChild(0) instanceof WACC_Array)){
            return new WACC_Int();
        } else {
            System.out.println("Semantic Error");
            return null;
        }
    }

    @Override
    public WACC_Type visitBIOPADD(@NotNull WACCParser.BIOPADDContext ctx) {
        return checkBinOpInt(ctx.ADD());
    }

    //TODO
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

    //TODO
    @Override
    public WACC_Type visitWHILE(@NotNull WACCParser.WHILEContext ctx) {
        return super.visitWHILE(ctx);
    }



    private WACC_Type checkBinOpInt(TerminalNode BinNode) {
        //PRE: can only be called on (int,int) binary operations
        //String operType = BinNode.getSymbol().getText();
        //System.out.println(operType);
        WACC_Type lhsType = visit(BinNode.getChild(0));
        WACC_Type rhsType = visit(BinNode.getChild(1));
      /*  if (operType.equals("+")
                || operType.equals("-")
                || operType.equals("*")
                || operType.equals("*")
                || operType.equals("/")
                || operType.equals("%")) */
            if ((lhsType instanceof WACC_Int) && (rhsType instanceof WACC_Int)) {
                return rhsType;
            } else {
                //TODO:ERROR
                System.out.println("Semantics error: " + new WACC_Int().toString() + "expected," + lhsType.toString() + " and " + rhsType.toString() + "found.");
                return new WACC_Int();
            }

    }

    //TODO:DELET DIS
    //DoooOOOOooplication!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    private WACC_Type checkBinOpBool(TerminalNode BinNode) {
        //PRE: can only be called on (bool, bool) binary operations
        WACC_Type lhsType = visit(BinNode.getChild(0));
        WACC_Type rhsType = visit(BinNode.getChild(1));
        if ((lhsType instanceof WACC_Bool) && (rhsType instanceof WACC_Bool)) {
            return rhsType;
        } else {
            //TODO:ERROR
            System.out.println("Semantics error: " + new WACC_Bool().toString() + "expected," + lhsType.toString() + " and " + rhsType.toString() + "found.");
            return new WACC_Bool();
        }

    }


    // Passing types or why c# is good and java is bad
    //Triple Kill!!
    private WACC_Type checkBinOpInEqSigns(TerminalNode BinNode) {
        //PRE: can only be called on (int, int) or (char, char) binary operations
        WACC_Type lhsType = visit(BinNode.getChild(0));
        WACC_Type rhsType = visit(BinNode.getChild(1));
        if (   ((lhsType instanceof WACC_Int) && (rhsType instanceof WACC_Int))
            || ((lhsType instanceof WACC_Char) && (rhsType instanceof WACC_Char))) {
            return new WACC_Bool();
        } else {
            //TODO:ERROR
            System.out.println("Semantics error: " + new WACC_Bool().toString() + "expected," + lhsType.toString() + " and " + rhsType.toString() + "found.");
            return new WACC_Bool();
        }

    }

    //QUADRA KILL
    private WACC_Type checkBinOpInEq(TerminalNode BinNode) {
        //PRE: can only be called on (bool, bool) or (int, int) or (char, char) or (pair, pair) binary operations
        WACC_Type lhsType = visit(BinNode.getChild(0));
        WACC_Type rhsType = visit(BinNode.getChild(1));
        if (   ((lhsType instanceof WACC_Int) && (rhsType instanceof WACC_Int))
                || ((lhsType instanceof WACC_Char) && (rhsType instanceof WACC_Char))
                || ((lhsType instanceof WACC_Bool) && (rhsType instanceof WACC_Bool))
                || ((lhsType instanceof WACC_Pair) && (rhsType instanceof WACC_Pair))) {
            return new WACC_Bool();
        } else {
            //TODO:ERROR
            System.out.println("Semantics error: " + new WACC_Bool().toString() + "expected," + lhsType.toString() + " and " + rhsType.toString() + "found.");
            return new WACC_Bool();
        }

    }


}
