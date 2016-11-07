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
            ANTLRInputStream input = new ANTLRInputStream(new FileInputStream("valid/function/simple_functions/functionUpdateParameter.wacc"));
            WACCLexer lexer  = new WACCLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            WACCParser parser = new WACCParser(tokens);

            //Adding error Listener to the code
            parser.removeErrorListeners();
            parser.addErrorListener(new ErrorListener());

            //Parsing through the Tree based on defined grammer
            ParseTree tree = parser.prog();

            //Visting the tree to check for syntax errors
            SyntaxVisitor syntaxVisitor = new SyntaxVisitor();
            syntaxVisitor.visit(tree);



//            WACCVisitor visitor = new WACCVisitor();
//            visitor.visit(tree); // need to add thing
        } catch (IOException e) {
            System.out.println("File Not Accepted");
        } catch(IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}