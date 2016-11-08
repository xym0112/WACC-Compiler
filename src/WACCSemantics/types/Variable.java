package WACCSemantics.types;

public class Variable {
    private WACC_Type type;
    private boolean declared;

    public Variable(WACC_Type type) {
        this.type = type;
        this.declared = false;
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
