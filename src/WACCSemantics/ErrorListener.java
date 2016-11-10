package WACCSemantics;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.misc.Nullable;

public class ErrorListener extends ConsoleErrorListener {
    @Override
    public void syntaxError(@NotNull Recognizer<?, ?> recognizer, @Nullable Object offendingSymbol, int line, int charPositionInLine, @NotNull String msg, @Nullable RecognitionException e) {
//        underlineError(recognizer,(Token)offendingSymbol,
//                line, charPositionInLine);
        System.err.println("Syntax error at line " + line + " and position " + charPositionInLine + ":");
        System.exit(100);
    }

    protected void underlineError(Recognizer recognizer,
                                  Token offendingToken, int line,
                                  int charPositionInLine) {
        System.err.println("Syntax error at line " + line + " and position " + charPositionInLine + ":");
        CommonTokenStream tokens =
                (CommonTokenStream)recognizer.getInputStream();
        String input = tokens.getTokenSource().getInputStream().toString();
        String[] lines = input.split("\n");
        String errorLine = lines[line - 1];
        System.err.println("line " + (line - 2) + " " + lines[line - 2]);
        System.err.println("line " + (line - 1) + " " + errorLine);
        int offset = 6 + String.valueOf(line - 1).length();
        for (int i=0; i<charPositionInLine + offset; i++) System.err.print(" ");
        int start = offendingToken.getStartIndex();
        int stop = offendingToken.getStopIndex();
        if ( start>=0 && stop>=0 ) {
            for (int i=start; i<=stop; i++) System.err.print("^");
        }
        System.err.println("");
        System.err.println("line " + line + " ");
        System.err.println();
    }
}
