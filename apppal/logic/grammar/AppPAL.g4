grammar AppPAL;

VARIABLE: UPPER TOKENCHAR*;
CONSTANT: '"' ~["]+ '"';
e: VARIABLE #variable
 | CONSTANT #constant
 ;

ZERO: '0';
INF: 'inf';
d: ZERO #zero
 | INF  #inf
 ;

PREDICATE_NAME: LOWER TOKENCHAR*;
vp: PREDICATE_NAME ('(' e (',' e)* ')')? #predicate
  | 'can-say' d fact                     #canSay
  | 'can-act-as' e                       #canActAs
  ;

fact: e vp;

claim: fact ('if' (fact (',' fact)* ))? ('where' c)?;

assertion: e 'says' claim '.';

ac: assertion+;

ce: e                                      #entity
  | PREDICATE_NAME '(' (ce (',' ce)*)? ')' #function
  | 'true'                                 #true
  | 'false'                                #false
  ;

c: 'sat'      #satisfaction
 | '!' c      #negation
 | ce '=' ce  #equality
 | c ',' c    #conjugation
 ;

WS: [ \t\r\n]+ -> skip;

fragment
LOWER: [a-z];

fragment
UPPER: [A-Z];

fragment
TOKENCHAR: [a-zA-Z0-9-];