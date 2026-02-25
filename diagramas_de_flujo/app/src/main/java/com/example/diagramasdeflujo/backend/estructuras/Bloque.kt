package com.example.diagramasdeflujo.backend.estructuras

import com.example.diagramasdeflujo.enums.Inst

class Bloque(inst: String) : Accion(Inst.BLOQUE) {
    private var instrucciones = StringBuilder(inst)

    fun agregarInstruccion(inst:String){
        instrucciones.append("\\n")
        instrucciones.append(inst)
    }

    fun getInstrucciones():String{
        return instrucciones.toString()
    }
}