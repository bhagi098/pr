%{

#include<stdio.h>
#include<string.h>
int space_cnt=0,line_cnt=0,word_cnt=0,c_cnt=0,tab_count=0;

%}

%%
\n line_cnt++;
" " space_cnt++;
\t tab_count++;
[^\t" "\n] c_cnt++;
%%

int yywrap(){
	return 1;
}

int main(){

	yyin=fopen("test.txt","r");
	yylex();
	printf("Line count %d",line_cnt);
	printf("Space count %d",space_cnt);
	printf("Tab count %d",tab_count);
	printf("Char count %d",c_cnt);
  return 0;
}