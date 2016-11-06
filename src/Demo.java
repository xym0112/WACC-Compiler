import antlr.WACCLexer;
import antlr.WACCParser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import java.io.FileInputStream;
import java.io.IOException;

public class Demo {
    public static void main(String[] args) throws Exception {
        try {
            ANTLRInputStream input = new ANTLRInputStream(new FileInputStream("valid/if/if3.wacc"));
            WACCLexer lexer  = new WACCLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            WACCParser parser = new WACCParser(tokens);
            parser.removeErrorListeners();
            parser.addErrorListener(new ErrorListener());
            parser.prog();

//            WACCVisitor visitor = new WACCVisitor();
//            visitor.visit(tree); // need to add thing
        } catch (IOException e) {
            System.out.println("Not Accepted");
        } catch(IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}