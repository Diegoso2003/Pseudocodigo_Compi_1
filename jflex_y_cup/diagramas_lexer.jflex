package com.example.diagramasdeflujo.analizadores;

import java_cup.runtime.*;
import com.example.diagramasdeflujo.enums.Inst;
import com.example.diagramasdeflujo.enums.Letra;
import com.example.diagramasdeflujo.enums.Figura;

%%

%public
%unicode
%class DiagramaLexer
%cup
%line
%column

%state CONFIGURACION, STRING, INSTRUCCION

%{

	private StringBuilder texto = new StringBuilder();
	private int linea;
	private int columna;
	
	private Symbol symbol(int type){
        	return new Symbol(type, yyline+1, yycolumn+1);
    	}
    	
    	private Symbol symbol(int type, Object object){
        	return new Symbol(type, yyline+1, yycolumn+1, object);
    	}
    	
    	private void iniciarCadena(){
    		texto.setLength(0);
    		linea = yyline+1;
    		columna = yycolumn+1;
    		texto.append("\"");
    	}
    	
    	private Symbol reportarCadena(){
    		texto.append("\"");
    		return new Symbol(sym.CADENA, linea, columna, texto.toString());
    	}
    	
    	private void reportarErrorLexico(){
    		int estado = yystate();
    		if (estado == 0 || estado == 3){
    			// simbolo no reconocido en pseudocodigo
    		} else if (estado == 1){
    			// simbolo no reconocido en configuracion
    		} else {
    			// cadena no cerrada
    		}
    	}

%}

LineTerminator = \r|\n|\r\n
WhiteSpace = {LineTerminator} | [\t\f]
Numero = [0-9]+
Decimal = {Numero}\.{Numero}
Hexadecimal = "H"[0-9A-F]{6}
Letra = [a-zA-Z]
Identifier = (_|{Letra})(_|{Letra}|{Numero})*
%%

<STRING> {
	\"							{ return reportarCadena(); }
	\n							{ reportarErrorLexico(); }
	.							{ texto.append(yytext()); }
}

	"("							{ return symbol(sym.PARENAPER); }
	")"							{ return symbol(sym.PARENCERRA); }
	"#".*							{ /** ignorar comentario **/ }
	{Decimal}						{ return symbol(sym.DECIMAL, Double.parseDouble(yytext())); }
	{Numero}						{ return symbol(sym.NUMERO, Integer.parseInt(yytext())); }

<YYINITIAL, INSTRUCCION> {
	"INICIO"						{ return symbol(sym.INICIO); }
	"FINMIENTRAS"						{ return symbol(sym.FINMIENTRAS); }
	"FINSI"							{ return symbol(sym.FINSI); }
	"FIN"							{ return symbol(sym.FIN); }
	"SI"							{ return symbol(sym.SI); }
	"ENTONCES"						{ return symbol(sym.ENTONCES); }
	"MIENTRAS"						{ return symbol(sym.MIENTRAS); }
	"HACER"							{ return symbol(sym.HACER); }
	"MOSTRAR"						{ yybegin(INSTRUCCION); return symbol(sym.MOSTRAR); }
	"LEER"							{ yybegin(INSTRUCCION); return symbol(sym.LEER); }
	"VAR"							{ yybegin(INSTRUCCION); return symbol(sym.VAR); }
	"="							{ yybegin(INSTRUCCION); return symbol(sym.ASIGNACION); }
	{Identifier}						{ return symbol(sym.IDENT, yytext()); }
	"=="							{ return symbol(sym.OPERARELA, yytext()); }
	"!="							{ return symbol(sym.OPERARELA, yytext()); }
	"<="							{ return symbol(sym.OPERARELA, yytext()); }
	">="							{ return symbol(sym.OPERARELA, yytext()); }
	"<"							{ return symbol(sym.OPERARELA, yytext()); }
	">"							{ return symbol(sym.OPERARELA, yytext()); }
	"&&"							{ return symbol(sym.OPERALOG, yytext()); }
	"||"							{ return symbol(sym.OPERALOG, yytext()); }
	"!"							{ return symbol(sym.OPERALOGNEG); }
	"+"							{ return symbol(sym.SUMA); }
	"-"							{ return symbol(sym.RESTA); }
	"*"							{ return symbol(sym.MULTI); }
	"/"							{ return symbol(sym.DIVI); }
	\"							{ yybegin(STRING); iniciarCadena(); }
	"%%%%"							{ yybegin(CONFIGURACION); return symbol(sym.DIVISOR); }
}

<CONFIGURACION> {
	"%DEFAULT"						{ return symbol(sym.DEFAULT); }
	"%COLOR_TEXTO_SI"					{ return symbol(sym.COLORTEXTO, Inst.SI); }
	"%COLOR_SI"						{ return symbol(sym.COLOR, Inst.SI); }
	"%FIGURA_SI"						{ return symbol(sym.FIGURA, Inst.SI); }
	"%LETRA_SI"						{ return symbol(sym.LETRA, Inst.SI); }
	"%LETRA_SIZE_SI"					{ return symbol(sym.LETRASIZE, Inst.SI); }
	"%COLOR_TEXTO_MIENTRAS"					{ return symbol(sym.COLORTEXTO, Inst.MIENTRAS); }
	"%COLOR_MIENTRAS"					{ return symbol(sym.COLOR, Inst.MIENTRAS); }
	"%FIGURA_MIENTRAS"					{ return symbol(sym.FIGURA, Inst.MIENTRAS); }
	"%LETRA_MIENTRAS"					{ return symbol(sym.LETRA, Inst.MIENTRAS); }
	"%LETRA_SIZE_MIENTRAS"					{ return symbol(sym.LETRASIZE, Inst.MIENTRAS); }
	"%COLOR_TEXTO_BLOQUE"					{ return symbol(sym.COLORTEXTO, Inst.BLOQUE); }
	"%COLOR_BLOQUE"						{ return symbol(sym.COLOR, Inst.BLOQUE); }
	"%FIGURA_BLOQUE"					{ return symbol(sym.FIGURA, Inst.BLOQUE); }
	"%LETRA_BLOQUE"						{ return symbol(sym.LETRA, Inst.BLOQUE); }
	"%LETRA_SIZE_BLOQUE"					{ return symbol(sym.LETRASIZE, Inst.BLOQUE); }
	"ELIPSE"						{ return symbol(sym.TFIGURA, Figura.ELIPSE); }
	"CIRCULO"						{ return symbol(sym.TFIGURA, Figura.CIRCULO); }
	"PARALELOGRAMO"						{ return symbol(sym.TFIGURA, Figura.PARALELOGRAMO); }
	"RECTANGULO"						{ return symbol(sym.TFIGURA, Figura.RECTANGULO); }
	"ROMBO"							{ return symbol(sym.TFIGURA, Figura.ROMBO); }
	"RECTANGULO_REDONDEADO"					{ return symbol(sym.TFIGURA, Figura.RECTANGULO_REDONDEADO); }
	"ARIAL"							{ return symbol(sym.TLETRA, Letra.ARIAL); }
	"TIMES_NEW_ROMAN"					{ return symbol(sym.TLETRA, Letra.TIMES); }
	"COMICS_SAM"						{ return symbol(sym.TLETRA, Letra.COMICS); }
	"VERDANA"						{ return symbol(sym.TLETRA, Letra.VERDANA); }
	"="							{ return symbol(sym.ASIGNACION); }
	"|"							{ return symbol(sym.ASIGINDI); }
	","							{ return symbol(sym.COMA); }
	"+"							{ return symbol(sym.MAS);}
	"-"							{ return symbol(sym.MENOS); }
	"*"							{ return symbol(sym.POR); }
	"/"							{ return symbol(sym.SIGNODIVI); }
	{Hexadecimal}						{ return symbol(sym.HEXADECIMAL, yytext().substring(1)); }
	\n							{ return symbol(sym.DELIMIT); }
}

<INSTRUCCION> {
	\n							{ yybegin(YYINITIAL); return symbol(sym.DELIMIT); }
}

	{WhiteSpace}						{ /** ignorar espacios en blanco **/ }
	.							{ reportarErrorLexico(); }

<<EOF>>								{ return symbol(sym.EOF); }
