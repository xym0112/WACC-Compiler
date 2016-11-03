lexer grammar WACCLexer;

// Reserved Keywords

BEGIN       : 'begin';
BOOL        : 'bool';
CALL        : 'call';
CHR         : 'chr';
DO          : 'do';
DONE        : 'done';
ELSE        : 'else';
END         : 'end';
EXIT        : 'exit';
FALSE       : 'false';
FI          : 'fi';
FREE        : 'free';
FST         : 'fst';
IF          : 'if';
INT         : 'int';
IS          : 'is';
LEN         : 'len';
NEWPAIR     : 'nepair';
NULL        : 'null';
ORD         : 'ord';
PAIR        : 'pair';
PRINT       : 'print';
PRINTLN     : 'println';
READ        : 'read';
RETURN      : 'return';
SKIPSTAT    : 'skip';
SND         : 'snd';
STRING      : 'string';
THEN        : 'then';
TRUE        : 'true';
WHILE       : 'while';

// Seperators

COMMA       : ',';
DOT         : '.';
LBRACE      : '{';
LBRACK      : '[';
RBRACE      : '}';
RBRACK      : ']';
LPAREN      : '(';
RPAREN      : ')';
SEMI        : ';';

HASH        : '#';
QUOTE       : '"';
SINGLEQUOTE : '\'';
BACKSLASH   : '\\';
UNDERSCORE  : '_';
ESCAPEDCHAR : '0' | 'b' | 't' | 'n' | 'f' | 'r' | QUOTE | SINGLEQUOTE | BACKSLASH;


// Operators

ADD         : '+';
AND         : '&&';
ASSIGN      : '=';
COLON       : ':';
DIV         : '/';
EQUAL       : '==';
EXCLAMATION : '!';
GE          : '>=';
GT          : '>';
LE          : '<=';
LT          : '<';
MOD         : '%';
MUL         : '*';
NOTEQUAL    : '!=';
OR          : '||';
QUESTION    : '?';
SUB         : '-';
TILDE       : '~';

// Types

DIGIT         : [0..9]+;
CHARACTER     : ~('\\' | '\'' | '"')*;
LOWERCHAR     : [a..z];
UPPERCHAR     : [A..Z];



