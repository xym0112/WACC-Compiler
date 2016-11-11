package WACCSemantics;

import WACCSemantics.types.WACC_Type;
import org.antlr.v4.runtime.misc.NotNull;

public class Variable {
    private WACC_Type type;
    private boolean declared;

    public Variable(WACC_Type type) {
        this.type = type;
        this.declared = false;
    }

    public Variable(WACC_Type type, boolean declared) {
        this.type = type;
        this.declared = declared;
    }

    public WACC_Type getType() {
        return type;
    }

    public boolean isDeclared() {
        return declared;
    }

    public void setDeclared(boolean declared) {
        this.declared = declared;
    }
}
