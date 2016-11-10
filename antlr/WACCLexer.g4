lexer grammar WACCLexer;

// Reserved Keywords

fragment INT    : 'int';
fragment BOOL   : 'bool';
fragment CHAR   : 'char';
fragment STRING : 'string';

BEGIN       : 'begin';
CALL        : 'call';
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
THEN        : 'then';
WHILE       : 'while';

// Seperators

COMMA       : ',';
LBRACE      : '{';
LBRACK      : '[';
RBRACE      : '}';
RBRACK      : ']';
LPAREN      : '(';
RPAREN      : ')';
SEMI        : ';';

fragment HASH        : '#';
fragment UNDERSCORE  : '_';
fragment ESCAPEDCHAR : [0btnfr"\'\\];

// Operators

ASSIGN      : '=';
ADD         : '+';
AND         : '&&';
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
SUB         : '-';

// Types

fragment DIGIT     : '0'..'9';
fragment LOWERCHAR : 'a'..'z';
fragment UPPERCHAR : 'A'..'Z';
fragment CHARACTER : ~[\'"] | [\\] ESCAPEDCHAR;
fragment NULL      : 'null';
fragment INTSIGN   : ADD | SUB;
fragment TRUE      : 'true';
fragment FALSE     : 'false';

UNSIGNED           : DIGIT+;

// Literals

BoolLiter : TRUE | FALSE;
PairLiter : NULL;
CharLiter : '\'' CHARACTER '\'';
StrLiter  : '"' CHARACTER* '"';

BaseType : INT
         | BOOL
         | CHAR
         | STRING
;

Ident    : (UNDERSCORE | LOWERCHAR | UPPERCHAR) (UNDERSCORE | UPPERCHAR | LOWERCHAR | UNSIGNED)*;

Comment  : HASH ~[\r\n]* -> skip;
WS       : [ \t\n]+ -> skip;

ERROR : .;