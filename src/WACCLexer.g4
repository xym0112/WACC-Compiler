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
SKIP_TODO   : 'skip'; // SKIP IS RESERVED BY ANTLR
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

// Whitespace and comments

WS          : [ \t\r\n\u000C]+ ->  skip;
COMMENT     : '#' ~[\r\n]* -> skip;

