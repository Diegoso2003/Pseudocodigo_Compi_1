package com.example.diagramasdeflujo.analizadores;

import java_cup.runtime.*;
import com.example.diagramasdeflujo.enums.Inst;
import com.example.diagramasdeflujo.enums.Letra;
import com.example.diagramasdeflujo.enums.Figura;
import com.example.diagramasdeflujo.enums.TipoEnum;
import com.example.diagramasdeflujo.backend.MensajeError;
import java.util.List;

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
	private List<MensajeError> errores;
	
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
    		MensajeError error = new MensajeError(TipoEnum.LEXICO);
            	error.setColumna(yycolumn+1);
            	error.setLinea(yyline+1);
    		if (estado == 0 || estado == 3){
    			error.setDescripcion("simbolo no reconocido en pseudocodigo");
              		error.setLexema(yytext());
    		} else if (estado == 1){
    			error.setDescripcion("simbolo no reconocido en configuracion");
              		error.setLexema(yytext());
    		} else {
    			error.setDescripcion("cadena no cerrada");
    		}
    	}
    	
    	public void setErrores(List<MensajeError> errores){
    		this.errores = errores;
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

	"("							{ return symbol(sym.PARENAPER, yytext()); }
	")"							{ return symbol(sym.PARENCERRA, yytext()); }
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
	"="							{ yybegin(INSTRUCCION); return symbol(sym.ASIGNACION, yytext()); }
	{Identifier}						{ return symbol(sym.IDENT, yytext()); }
	"=="							{ return symbol(sym.OPERARELA, yytext()); }
	"!="							{ return symbol(sym.OPERARELA, yytext()); }
	"<="							{ return symbol(sym.OPERARELA, yytext()); }
	">="							{ return symbol(sym.OPERARELA, yytext()); }
	"<"							{ return symbol(sym.OPERARELA, yytext()); }
	">"							{ return symbol(sym.OPERARELA, yytext()); }
	"&&"							{ return symbol(sym.OPERALOG, yytext()); }
	"||"							{ return symbol(sym.OPERALOG, yytext()); }
	"!"							{ return symbol(sym.OPERALOGNEG, yytext()); }
	"+"							{ return symbol(sym.SUMA, yytext()); }
	"-"							{ return symbol(sym.RESTA, yytext()); }
	"*"							{ return symbol(sym.MULTI, yytext()); }
	"/"							{ return symbol(sym.DIVI, yytext()); }
	\"							{ yybegin(STRING); iniciarCadena(); }
	"%%%%"							{ yybegin(CONFIGURACION); return symbol(sym.SEPARADOR, yytext()); }
}

<CONFIGURACION> {
	"%DEFAULT"						{ return symbol(sym.DEFAULT); }
	"%COLOR_TEXTO_SI"					{ return symbol(sym.COLORTEXTO, new DatosConfig(yytext(), Inst.SI)); }
	"%COLOR_SI"						{ return symbol(sym.COLOR, new DatosConfig(yytext(), Inst.SI)); }
	"%FIGURA_SI"						{ return symbol(sym.FIGURA, new DatosConfig(yytext(), Inst.SI)); }
	"%LETRA_SI"						{ return symbol(sym.LETRA, new DatosConfig(yytext(), Inst.SI)); }
	"%LETRA_SIZE_SI"					{ return symbol(sym.LETRASIZE, new DatosConfig(yytext(), Inst.SI)); }
	"%COLOR_TEXTO_MIENTRAS"					{ return symbol(sym.COLORTEXTO, new DatosConfig(yytext(), Inst.MIENTRAS)); }
	"%COLOR_MIENTRAS"					{ return symbol(sym.COLOR, new DatosConfig(yytext(), Inst.MIENTRAS)); }
	"%FIGURA_MIENTRAS"					{ return symbol(sym.FIGURA, new DatosConfig(yytext(), Inst.MIENTRAS)); }
	"%LETRA_MIENTRAS"					{ return symbol(sym.LETRA, new DatosConfig(yytext(), Inst.MIENTRAS)); }
	"%LETRA_SIZE_MIENTRAS"					{ return symbol(sym.LETRASIZE, new DatosConfig(yytext(), Inst.MIENTRAS)); }
	"%COLOR_TEXTO_BLOQUE"					{ return symbol(sym.COLORTEXTO, new DatosConfig(yytext(), Inst.BLOQUE)); }
	"%COLOR_BLOQUE"						{ return symbol(sym.COLOR, new DatosConfig(yytext(), Inst.BLOQUE)); }
	"%FIGURA_BLOQUE"					{ return symbol(sym.FIGURA, new DatosConfig(yytext(), Inst.BLOQUE)); }
	"%LETRA_BLOQUE"						{ return symbol(sym.LETRA, new DatosConfig(yytext(), Inst.BLOQUE)); }
	"%LETRA_SIZE_BLOQUE"					{ return symbol(sym.LETRASIZE, new DatosConfig(yytext(), Inst.BLOQUE)); }
	"ELIPSE"						{ return symbol(sym.TFIGURA, Figura.ELIPSE); }
	"CIRCULO"						{ return symbol(sym.TFIGURA, Figura.CIRCULO); }
	"PARALELOGRAMO"						{ return symbol(sym.TFIGURA, Figura.PARALELOGRAMO); }
	"RECTANGULO_REDONDEADO"					{ return symbol(sym.TFIGURA, Figura.RECTANGULO_REDONDEADO); }
	"RECTANGULO"						{ return symbol(sym.TFIGURA, Figura.RECTANGULO); }
	"ROMBO"							{ return symbol(sym.TFIGURA, Figura.ROMBO); }
	"ARIAL"							{ return symbol(sym.TLETRA, Letra.ARIAL); }
	"TIMES_NEW_ROMAN"					{ return symbol(sym.TLETRA, Letra.TIMES_NEW_ROMAN); }
	"COMICS_SAM"						{ return symbol(sym.TLETRA, Letra.COMICS_SAM); }
	"VERDANA"						{ return symbol(sym.TLETRA, Letra.VERDANA); }
	"="							{ return symbol(sym.ASIGNACION, yytext()); }
	"|"							{ return symbol(sym.ASIGINDI, yytext()); }
	","							{ return symbol(sym.COMA, yytext()); }
	"+"							{ return symbol(sym.MAS, yytext());}
	"-"							{ return symbol(sym.MENOS, yytext()); }
	"*"							{ return symbol(sym.POR, yytext()); }
	"/"							{ return symbol(sym.SIGNODIVI, yytext()); }
	{Hexadecimal}						{ return symbol(sym.HEXADECIMAL, yytext()); }
	\n							{ return symbol(sym.DELIMIT); }
}

<INSTRUCCION> {
	\n							{ yybegin(YYINITIAL); return symbol(sym.DELIMIT); }
}

	{WhiteSpace}						{ /** ignorar espacios en blanco **/ }
	.							{ reportarErrorLexico(); }

<<EOF>>								{ return symbol(sym.EOF); }
