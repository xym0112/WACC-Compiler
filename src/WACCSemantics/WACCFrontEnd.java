package WACCSemantics;

import antlr.*;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.FileInputStream;
import java.io.IOException;


public class WACCFrontEnd {
    public static void main(String[] args) throws Exception {
        String file = args[0];

        if (!file.endsWith(".wacc")) {
            throw new IllegalArgumentException("Invalid file type must be a .wacc file");
        }

        try {
            ANTLRInputStream input = new ANTLRInputStream(new FileInputStream(file));

            // check if file is .wacc
            WACCLexer lexer = new WACCLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            WACCParser parser = new WACCParser(tokens);

            //Adding error Listener to the code
            parser.removeErrorListeners();
            parser.addErrorListener(new ErrorListener());

            //parser.setErrorHandler(new MyErrorStrategy());
            ParseTree tree = parser.prog();

            //Visting the tree to check for syntax errors
            SyntaxVisitor syntaxVisitor = new SyntaxVisitor();
            syntaxVisitor.visit(tree);

            WACC_Semantics_Visitor visitor = new WACC_Semantics_Visitor();
            visitor.visit(tree); // need to add thing
        } catch (IOException e) {
            System.out.println("Not Accepted");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}