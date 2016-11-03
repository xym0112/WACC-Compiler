parser grammar WACCParser;

options  {
    tokenVocab=WACCLexer;
}

// EOF indicates that the program must consume to the end of the input.
prog: BEGIN (func)* stat END EOF;

func: type ident LPAREN (paramList)? RPAREN IS stat END;

paramList: param (COMMA param)*;

param: type ident;

stat: SKIPSTAT
| EXIT expr
| type ident ASSIGN assignRhs
| assignLhs ASSIGN assignRhs
| READ assignRhs
| FREE expr
| PRINT expr
| PRINTLN expr
| IF expr THEN stat ELSE stat FI
| WHILE expr DO stat DONE
| BEGIN stat END
| stat SEMI stat
;

assignRhs: ident
| arrayElem
| pairElem
;

assignLhs: expr
| arrayLiter
| NEWPAIR LPAREN expr COMMA expr RPAREN
| pairElem
| CALL ident LPAREN (argList)? RPAREN
;

argList: expr (COMMA expr)*;

pairElem: FST
| SND
;

type: baseType
| pairType
| type LBRACK RBRACK
;

baseType: INT
| BOOL
| CHR
| STRING
;

pairType: PAIR LPAREN pairElemType COMMA pairElemType RPAREN;

pairElemType: baseType
| type LBRACK RBRACK
| PAIR
;

expr: IntLiter
| BoolLiter
| CharLiter
| StrLiter
| PairLiter
| ident
| arrayElem
| unArrayOper expr
| expr binaryOper expr
| LPAREN expr RPAREN
;

unArrayOper: EXCLAMATION
| SUB
| LEN
| ORD
| CHR
;

binaryOper: MUL
| DIV
| MOD
| ADD
| SUB
| GT
| GE
| LT
| LE
| EQUAL
| NOTEQUAL
| AND
| OR
;

ident: (UNDERSCORE | CharLiter) (UNDERSCORE | CharLiter | IntLiter)*;

arrayElem: ident LBRACK expr RBRACK;

arrayLiter: LBRACK (expr (COMMA expr)*)? RBRACK;










