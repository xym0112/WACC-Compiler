parser grammar WACCParser;

options  {
    tokenVocab=WACCLexer;
}

// EOF indicates that the program must consume to the end of the input.
prog: BEGIN func* stat END EOF;

func: type Ident LPAREN (paramList)? RPAREN IS stat END;

paramList: param (COMMA param)*;

param: type Ident;

stat: SKIPSTAT
    | EXIT expr
    | type Ident ASSIGN assignRhs
    | assignLhs ASSIGN assignRhs
    | READ assignRhs
    | FREE expr
    | RETURN expr
    | PRINT expr
    | PRINTLN expr
    | IF expr THEN stat ELSE stat FI
    | WHILE expr DO stat DONE
    | BEGIN stat END
    | stat SEMI stat
;

assignRhs : expr
          | arrayLiter
          | NEWPAIR LPAREN expr COMMA expr RPAREN
          | pairElem
          | CALL Ident LPAREN (argList)? RPAREN
;

assignLhs: Ident
         | arrayElem
         | pairElem
;

argList: expr (COMMA expr)*;

pairElem : FST expr
         | SND expr
;

type: baseType
    | pairType
    | type LBRACK RBRACK
;

baseType: INT
        | BOOL
        | CHAR
        | STRING
;

pairType: PAIR LPAREN pairElemType COMMA pairElemType RPAREN;

pairElemType: baseType
            | type LBRACK RBRACK
            | PAIR
;

expr: (SIGNED | UNSIGNED)
    | (SIGNED | UNSIGNED) SIGNED
    | BoolLiter
    | CharLiter
    | StrLiter
    | PairLiter
    | Ident
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

arrayElem: Ident (LBRACK expr RBRACK)+;

arrayLiter: LBRACK (expr (COMMA expr)*)? RBRACK;









