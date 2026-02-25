package com.example.diagramasdeflujo.analizadores

import com.example.diagramasdeflujo.enums.Inst

class DatosConfig(var lexema:String, var tipo: Inst) {
    override fun toString(): String{
        return lexema;
    }
}