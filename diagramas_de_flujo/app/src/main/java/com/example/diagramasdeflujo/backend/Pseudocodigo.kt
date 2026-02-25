package com.example.diagramasdeflujo.backend

import com.example.diagramasdeflujo.backend.estructuras.Accion
import com.example.diagramasdeflujo.backend.estructuras.Bloque

class Pseudocodigo {
    var acciones = ArrayList<Accion>()
    var reporteEstructura = ArrayList<ReporteEstructura>()
    var indices = arrayOf(ArrayList<Accion>(), ArrayList<Accion>(), ArrayList<Accion>())
    lateinit var bloque: Bloque
    var agregado: Boolean = false

    fun agregarAccion(accion: Accion) {
        if (agregarBloque()){
            acciones.add(bloque)
            agregado = true
        }
        acciones.add(accion)
        indices[accion.tipo.ordinal].add(accion)
    }

    fun agregarInstruccion(instruccion: String){
        if(crearBloque()){
            bloque = Bloque(instruccion)
            agregado = false
        }
        bloque.agregarInstruccion(instruccion)
    }

    fun crearBloque():Boolean {
        return !::bloque.isInitialized || agregado
    }

    fun agregarBloque():Boolean {
        return ::bloque.isInitialized
    }

    fun agregarReporteDeEstructura(reporte: ReporteEstructura){
        reporteEstructura.add(reporte)
    }
}