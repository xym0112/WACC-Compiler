lexer grammar WACCLexer;

// Reserved Keywords

Comment  : HASH ~[\r\n]* -> skip;

WS          : [ \t\n]+ -> skip;

BEGIN       : 'begin';
BOOL        : 'bool';
CALL        : 'call';
CHR         : 'char';
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
NEWPAIR     : 'newpair';
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
UNDERSCORE  : '_';
fragment ESCAPEDCHAR : [0btnfr"\'\\];

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

DIGIT     : '0'..'9';
fragment LOWERCHAR : 'a'..'z';
fragment UPPERCHAR : 'A'..'Z';
fragment CHARACTER : ~[\'"] | [\\] ESCAPEDCHAR;
INTSIGN   : ADD | SUB;
CHAR      : '\'' CHARACTER '\'';

BoolLiter: TRUE | FALSE;
PairLiter: NULL;
CharLiter: (LOWERCHAR | UPPERCHAR);
StrLiter : '"' CHARACTER* '"';

