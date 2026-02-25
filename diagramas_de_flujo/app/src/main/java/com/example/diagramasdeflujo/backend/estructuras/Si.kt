package com.example.diagramasdeflujo.backend.estructuras

import com.example.diagramasdeflujo.enums.Inst

class Si(var condicion:String, var bloque:Bloque) : Accion(Inst.SI) {
}