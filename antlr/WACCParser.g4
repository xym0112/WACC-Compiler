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
     | BEGIN stat END                               #BEGIN
     | stat SEMI stat                               #SEQUENCE
     | ifStatement                                  #IF
     | whileStatement                               #WHILE
;

ifStatement : IF expr THEN stat ELSE stat FI
            | IF expr stat ELSE stat FI
             {System.out.println("missing Then statement: at line" + $IF.line);}
            | IF expr THEN stat stat FI
             {System.out.println("missing Else statement: at line" + String.valueOf($THEN.line+1));}
            | IF expr THEN stat ELSE stat
             {System.out.println("missing FI statement: at line" + String.valueOf($ELSE.line+2));}
            | expr THEN stat ELSE stat FI
             {System.out.println("missing IF statement: at line " + String.valueOf($THEN.line-1));}
            | IF THEN stat stat FI
             {System.out.println("missing if condition: at line" + $IF.line);}
            | IF expr THEN ELSE stat FI
             {System.out.println("missing Then statement: at line" + $THEN.line);}
            | IF expr THEN stat ELSE FI
             {System.out.println("missing Else statement: at line" + $ELSE.line);}
            ;

whileStatement : WHILE expr DO stat DONE
               | expr DO stat DONE
                {System.out.println("missing While statement: at line" + String.valueOf($DO.line-1));}
               | WHILE DO stat DONE
                {System.out.println("missing While statement: at line" + $WHILE.line);}
               | WHILE expr stat DONE
                {System.out.println("missing While statement: at line" + String.valueOf($WHILE.line+1));}
               | WHILE expr DO DONE
                {System.out.println("missing While statement: at line" + $DO.line);}
               | WHILE expr DO stat
                {System.out.println("missing While statement: at line" + String.valueOf($DO.line+2));}
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
           | AND                                    #BIOPAND
           | OR                                     #BIOPOR
;

arrayElem  : Ident (LBRACK expr RBRACK)+;
arrayLiter : LBRACK (expr (COMMA expr)*)? RBRACK;







