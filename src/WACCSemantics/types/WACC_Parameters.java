package WACCSemantics.types;

import java.util.ArrayList;

public class WACC_Parameters extends ArrayList<WACC_Type> {

    public WACC_Parameters() {
        super();
    }

    public WACC_Parameters(int initialCapacity) {
        super(initialCapacity);
    }

    public WACC_Parameters(WACC_Type... elems) {
        super();

        for (int i = 0; i < elems.length; i++) {
            this.add(elems[i]);
        }
    }
}
