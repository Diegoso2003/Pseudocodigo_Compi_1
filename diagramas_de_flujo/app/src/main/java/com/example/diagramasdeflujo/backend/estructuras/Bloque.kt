package com.example.diagramasdeflujo.backend.estructuras

import com.example.diagramasdeflujo.Constantes
import com.example.diagramasdeflujo.enums.Inst

class Bloque(inst: String) : Accion(Inst.BLOQUE, Constantes.FIGURA_BLOQUE,
    Constantes.COLOR_BLOQUE
) {
    private var instrucciones = StringBuilder(inst)

    fun agregarInstruccion(inst:String){
        instrucciones.append("\n")
        instrucciones.append(inst)
    }

    override fun getTexto():String{
        return instrucciones.toString()
    }

    override fun porDefecto() {
        color = Constantes.COLOR_BLOQUE
        colorTexto = Constantes.COLOR_TEXTO_DEFECTO
        figura = Constantes.FIGURA_BLOQUE
        tipoLetra = Constantes.TIPO_LETRA
        tamañoLetra = Constantes.TAMAÑO_NORMAL
    }

    override fun agregarLista(acciones: ArrayList<Accion>, indices: Array<ArrayList<Accion>>) {
        acciones.add(this)
        indices[tipo.ordinal].add(this)
        indices[indices.size-1].add(this)
    }

    override fun esCiclo(): Boolean {
        return false
    }
}