import antlr.WACCLexer;
import antlr.WACCParser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.FileInputStream;
import java.io.IOException;

public class Demo {
    public static void main(String[] args) throws Exception {

        try {
            ANTLRInputStream input = new ANTLRInputStream(new FileInputStream("valid/basic/exit/exit-1.wacc"));
            WACCLexer lexer  = new WACCLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            WACCParser parser = new WACCParser(tokens);
            //parser.setErrorHandler(new MyErrorStrategy());
            ParseTree tree = parser.prog();


//            WACC_Visitor visitor = new WACC_Visitor();
//            visitor.visit(tree); // need to add thing
        }catch (IOException e) {
            System.out.println("Not Accepted");
        }catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
    }
}