parser grammar WACCParser;

options  {
    tokenVocab=WACCLexer;
}

prog : BEGIN func* stat END EOF;

func      : type Ident LPAREN (paramList)? RPAREN IS stat END;
paramList : param (COMMA param)*;
param     : type Ident;

stat : SKIPSTAT                                     #SKIPSTAT
     | EXIT expr                                    #EXIT
     | type Ident ASSIGN assignRhs                  #CREATEVAR
     | assignLhs ASSIGN assignRhs                   #ASSIGNVAR
     | READ assignRhs                               #READ
     | FREE expr                                    #FREE
     | RETURN expr                                  #RETURN
     | PRINT expr                                   #PRINT
     | PRINTLN expr                                 #PRINTLN
     | IF expr THEN stat ELSE stat FI               #IF
     | WHILE expr DO stat DONE                      #WHILE
     | BEGIN stat END                               #BEGIN
     | stat SEMI stat                               #SEQUENCE
;

assignRhs : expr                                    #RHSEXPR
          | arrayLiter                              #RHSARRLITER
          | NEWPAIR LPAREN expr COMMA expr RPAREN   #RHSNEWPAIR
          | pairElem                                #RHSPAIR
          | CALL Ident LPAREN (argList)? RPAREN     #RHSCALLFUNC
;

assignLhs : Ident                                   #LHSIDENT
          | arrayElem                               #LHSARRAYELEM
          | pairElem                                #LHSPAIRELEM
;

argList   : expr (COMMA expr)*;

pairElem  : FST expr                                #PAIRFST
          | SND expr                                #PAIRSND
;

type : BaseType                                     #TYPEBASE
     | pairType                                     #TYPEPAIR
     | type LBRACK RBRACK                           #TYPEARRAY
;

pairType : PAIR LPAREN pairElemType COMMA pairElemType RPAREN;

pairElemType : BaseType                             #PAIRBASETYPE
             | type LBRACK RBRACK                   #PAIRARRAYTYPE
             | PAIR                                 #PAIRPAIR
;

expr : UNSIGNED                                     #UNSIGNED
     | BoolLiter                                    #BOOLLITER
     | CharLiter                                    #CHARLITER
     | StrLiter                                     #STRLITER
     | PairLiter                                    #PAIRLITER
     | Ident                                        #EXPRIDENT
     | arrayElem                                    #EXPRARRAYELEM
     | unaryOper expr                               #UNARYOP
     | expr binaryOper expr                         #BINARYOP
     | expr logicalOper expr                        #LOGICEXPR
     | LPAREN expr RPAREN                           #BRACKETEXPR
;

unaryOper  : EXCLAMATION                            #UNOPEXCLAMATION
           | SUB                                    #UNOPSUB
           | ADD                                    #UNOPADD
           | LEN                                    #UNOPLEN
           | ORD                                    #UNOPORD
           | CHR                                    #UNOPCHR
;

binaryOper : MUL                                    #BIOPMUL
           | DIV                                    #BIOPDIV
           | MOD                                    #BIOPMOD
           | ADD                                    #BIOPADD
           | SUB                                    #BIOPSUB
           | GT                                     #BIOPGT
           | GE                                     #BIOPGE
           | LT                                     #BIOPLT
           | LE                                     #BIOPLE
           | EQUAL                                  #BIOPEQUAL
           | NOTEQUAL                               #BIOPNQ
;

logicalOper : OR
            | AND
;

arrayElem  : Ident (LBRACK expr RBRACK)+;
arrayLiter : LBRACK (expr (COMMA expr)*)? RBRACK;







