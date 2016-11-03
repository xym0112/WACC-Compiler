lexer grammar WACCLexer;

// Reserved Keywords

Comment  : HASH ~[\r\n]* -> skip;

WS          : [ \t\n]+ -> skip;

BEGIN       : 'begin';
BOOL        : 'bool';
CALL        : 'call';
CHAR        : 'char';
CHR         : 'chr';
DO          : 'do';
DONE        : 'done';
ELSE        : 'else';
END         : 'end';
EXIT        : 'exit';
FI          : 'fi';
FREE        : 'free';
FST         : 'fst';
IF          : 'if';
INT         : 'int';
IS          : 'is';
LEN         : 'len';
NEWPAIR     : 'newpair';
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
fragment UNDERSCORE  : '_';
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

fragment DIGIT     : '0'..'9';
fragment LOWERCHAR : 'a'..'z';
fragment UPPERCHAR : 'A'..'Z';
fragment CHARACTER : ~[\'"] | [\\] ESCAPEDCHAR;
fragment NULL      : 'null';
INTSIGN   : ADD | SUB;
fragment TRUE      : 'true';
fragment FALSE     : 'false';

BoolLiter: TRUE | FALSE;
PairLiter: NULL;
CharLiter: '\'' CHARACTER '\'';
StrLiter : '"' CHARACTER* '"';
IntLiter : DIGIT+;

Ident: (UNDERSCORE | LOWERCHAR | UPPERCHAR) (UNDERSCORE | UPPERCHAR | LOWERCHAR | IntLiter)*;

