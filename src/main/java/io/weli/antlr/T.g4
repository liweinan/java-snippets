grammar T;

a : expr ';' ;

expr : ID
 | expr '*' expr
 ;

ID : [a-z]+ ;
