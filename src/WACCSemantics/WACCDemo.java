package WACCSemantics;

import WACCSemantics.WACC_Semantics_Visitor;
import antlr.WACCLexer;
import antlr.WACCParser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


public class WACCDemo {




    public static void main(String[] args) throws Exception {

        String file = args[0];

        if (!file.endsWith(".wacc")){
            throw new IllegalArgumentException("Invalid file type must be a .wacc file");
        }

        try {
            ANTLRInputStream input = new ANTLRInputStream(new FileInputStream(file));
            // check if file is .wacc
            WACCLexer lexer = new WACCLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            WACCParser parser = new WACCParser(tokens);
            //parser.setErrorHandler(new MyErrorStrategy());
            ParseTree tree = parser.prog();


            WACC_Semantics_Visitor visitor = new WACC_Semantics_Visitor();
            visitor.visit(tree); // need to add thing
        } catch (IOException e) {
            System.out.println("Not Accepted");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

//    public static void showFiles(File[] files) {
//
//        for (File file : files) {
//            if (file.isDirectory()) {
////                System.out.println("Directory: " + file.getName());
//                showFiles(file.listFiles()); // Calls same method again.
//            } else {
//                if (file.getName().endsWith(".wacc"))
//                    System.out.println("File: " + file.getName());
//            }
//        }
//    }
}