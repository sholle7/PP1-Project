package rs.ac.bg.etf.pp1;

import java_cup.runtime.Symbol;

%%

%{
	
	boolean errorD = false;

	// ukljucivanje informacije o poziciji tokena
	private Symbol new_symbol(int type) {
		return new Symbol(type, yyline+1, yycolumn);
	}
	
	// ukljucivanje informacije o poziciji tokena
	private Symbol new_symbol(int type, Object value) {
		return new Symbol(type, yyline+1, yycolumn, value);
	}

%}

%cup
%line
%column

%xstate COMMENT

%eofval{
	return new_symbol(sym.EOF);
%eofval}

%%

//--------------------------------ESCAPE CHARACTERS----------------------------------
" " 	{ }
"\b" 	{ }
"\t" 	{ }
"\r\n" 	{ }
"\f" 	{ }

//---------------------------KEYWORDS AND OPERATORS----------------------------------
"program"   { return new_symbol(sym.PROG, yytext()); }
"const"   { return new_symbol(sym.CONST, yytext()); }
"new"   { return new_symbol(sym.NEW, yytext()); }
"print" 	{ return new_symbol(sym.PRINT, yytext()); }
"read" 	{ return new_symbol(sym.READ, yytext()); }
"void" 		{ return new_symbol(sym.VOID, yytext()); }
"return" 	{ return new_symbol(sym.RETURN, yytext()); }



"+" 		{ return new_symbol(sym.PLUS, yytext()); }
"-" 		{ return new_symbol(sym.MINUS, yytext()); }
"*" 		{ return new_symbol(sym.MUL, yytext()); }
"/" 		{ return new_symbol(sym.DIV, yytext()); }
"%" 		{ return new_symbol(sym.MOD, yytext()); }


// "==" 		{ return new_symbol(sym.EQ, yytext()); }
// "!=" 		{ return new_symbol(sym.NEQ, yytext()); }
// ">" 		{ return new_symbol(sym.GRT, yytext()); }
// ">=" 		{ return new_symbol(sym.GEQ, yytext()); }
// "<" 		{ return new_symbol(sym.LE, yytext()); }
// "<=" 		{ return new_symbol(sym.LEQ, yytext()); }
// "&&" 		{ return new_symbol(sym.AND, yytext()); }
// "||" 		{ return new_symbol(sym.OR, yytext()); }


"++" 		{ return new_symbol(sym.INC, yytext()); }
"--" 		{ return new_symbol(sym.DEC, yytext()); }




"=" 		{ return new_symbol(sym.EQUAL, yytext()); }
";" 		{ return new_symbol(sym.SEMI, yytext()); }
"," 		{ return new_symbol(sym.COMMA, yytext()); }
":" 		{ return new_symbol(sym.COLON, yytext()); }
"." 		{ return new_symbol(sym.POINT, yytext()); }

"(" 		{ return new_symbol(sym.LPAREN, yytext()); }
")" 		{ return new_symbol(sym.RPAREN, yytext()); }
"{" 		{ return new_symbol(sym.LBRACE, yytext()); }
"}"			{ return new_symbol(sym.RBRACE, yytext()); }
"[" 		{ return new_symbol(sym.LSQUAREBRACKET, yytext()); }
"]" 		{ return new_symbol(sym.RSQUAREBRACKET, yytext()); }





//--------------------------------------BOOLEAN--------------------------------------
"true" 		{ return new_symbol(sym.BOOL, true); }
"false" 	{ return new_symbol(sym.BOOL, false); }

//-------------------------------------CHAR------------------------------------------
"'"."'"  	{ return new_symbol(sym.CHAR, new Character (yytext().charAt(1))); }


//------------------------------------COMMENT----------------------------------------
"//" 				{ yybegin(COMMENT); }
<COMMENT> . 		{ yybegin(COMMENT); }
<COMMENT> "\r\n" 	{ yybegin(YYINITIAL); }


//-----------------------------NUMBER/IDENTIFICATOR----------------------------------
[0-9]+  						{ return new_symbol(sym.NUMBER, new Integer (yytext())); }
([a-z]|[A-Z])[a-z|A-Z|0-9|_]* 	{ return new_symbol (sym.IDENT, yytext()); }

//---------------------------------ERROR---------------------------------------------
. { errorD=true; System.err.println("Leksicka greska ("+ yytext() + ") u liniji "+ (yyline + 1) + " , u koloni " + (yycolumn + 1)); }