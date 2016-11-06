parser grammar WACCParser;

options  {
    tokenVocab=WACCLexer;
}

prog : BEGIN func* stat END EOF;

func      : type Ident LPAREN (paramList)? RPAREN IS stat END
          | type Ident (paramList)? RPAREN IS stat END
           {System.out.println("Missing token '(' token at line " + $Ident.line); System.exit(100);}
          | type Ident LPAREN (paramList)? IS stat END
           {System.out.println("Missing token ')' token at line " + $Ident.line); System.exit(100);}
          | type Ident LPAREN (paramList)? RPAREN stat END
           {System.out.println("Missing token 'is' token around line " + $RPAREN.line); System.exit(100);}
          | type Ident LPAREN (paramList)? RPAREN IS stat
           {System.out.println("Missing token 'end' token around line " + String.valueOf($IS.line + 2)); System.exit(100);}
;

paramList : param (COMMA param)*;
param     : type Ident;

stat : SKIPSTAT                                     #SKIPSTAT
     | EXIT expr                                    #EXIT
     | createVariable                               #CREATEVAR
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

createVariable : type Ident ASSIGN assignRhs
               | type Ident assignRhs
                {System.out.println("Missing '=' token at line " + $Ident.line); System.exit(100);}
;

ifStatement : IF expr THEN stat ELSE stat FI
            | IF expr stat ELSE stat FI
             {System.out.println("Missing 'then' token at line " + $IF.line); System.exit(100);}
            | IF expr THEN stat stat FI
             {System.out.println("Missing 'else' token around line " + String.valueOf($THEN.line+1)); System.exit(100);}
            | IF expr THEN stat ELSE stat
             {System.out.println("Missing 'fi' token around line " + String.valueOf($ELSE.line+2)); System.exit(100);}
            | expr THEN stat ELSE stat FI
             {System.out.println("Missing 'if' token around line  " + String.valueOf($THEN.line-1)); System.exit(100);}
            | IF THEN stat stat FI
             {System.out.println("Missing condition at line " + $IF.line); System.exit(100);}
            | IF expr THEN ELSE stat FI
             {System.out.println("Missing then body at line " + $THEN.line); System.exit(100);}
            | IF expr THEN stat ELSE FI
             {System.out.println("Missing else body at line " + $ELSE.line); System.exit(100);}
            ;

whileStatement : WHILE expr DO stat DONE
               | expr DO stat DONE
                {System.out.println("Missing 'while' token around line " + String.valueOf($DO.line-1)); System.exit(100);}
               | WHILE DO stat DONE
                {System.out.println("Missing while condition at line " + $WHILE.line);}
               | WHILE expr stat DONE
                {System.out.println("Missing while body around line " + String.valueOf($WHILE.line+1)); System.exit(100);}
               | WHILE expr DO DONE
                {System.out.println("Missing do body at line " + $DO.line); System.exit(100);}
               | WHILE expr DO stat
                {System.out.println("Missing 'done' token around line " + String.valueOf($DO.line+2)); System.exit(100);}
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







