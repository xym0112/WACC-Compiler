package WACCSemantics;

import WACCSemantics.types.WACC_Type;

class Variable {
    private WACC_Type type;
    private boolean declared;

    Variable(WACC_Type type) {
        this.type = type;
        this.declared = false;
    }

    Variable(WACC_Type type, boolean declared) {
        this.type = type;
        this.declared = declared;
    }

    public WACC_Type getType() {
        return type;
    }

    boolean isDeclared() {
        return declared;
    }

    void setDeclared(boolean declared) {
        this.declared = declared;
    }
}
