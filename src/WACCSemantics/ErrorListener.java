package WACCSemantics;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.misc.Nullable;

public class ErrorListener extends ConsoleErrorListener {
    @Override
    public void syntaxError(@NotNull Recognizer<?, ?> recognizer, @Nullable Object offendingSymbol, int line, int charPositionInLine, @NotNull String msg, @Nullable RecognitionException e) {
        underlineError(recognizer,(Token)offendingSymbol,
                line, charPositionInLine);
        System.exit(100);
    }

    protected void underlineError(Recognizer recognizer,
                                  Token offendingToken, int line,
                                  int charPositionInLine) {
        System.err.println("Syntax error at line " + line + " and position " + charPositionInLine);

        // get a stream of tokens around in this context
        CommonTokenStream tokens =
                (CommonTokenStream)recognizer.getInputStream();

        // convert tokens to a string of lines
        String input = tokens.getTokenSource().getInputStream().toString();
        String[] lines = input.split("\n");
        // get the line at which the error is at
        String errorLine = lines[line - 1];
        // print previous line
        System.err.println("line " + (line - 2) + " " + lines[line - 2]);
        // print error line
        System.err.println("line " + (line - 1) + " " + errorLine);

        // get the offset from the start of the line to the error itself
        int offset = 6 + String.valueOf(line - 1).length();

        // print offset number of spaces
        for (int i=0; i<charPositionInLine + offset; i++) System.err.print(" ");

        // start pos of token and end pos of token
        int start = offendingToken.getStartIndex();
        int stop = offendingToken.getStopIndex();

        // print the ^ char in the correct position
        if ( start>=0 && stop>=0 ) {
            for (int i=start; i<=stop; i++) System.err.print("^");
        }
    }
}
