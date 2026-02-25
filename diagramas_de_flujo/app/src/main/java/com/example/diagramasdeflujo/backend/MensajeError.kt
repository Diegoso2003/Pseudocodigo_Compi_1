package com.example.diagramasdeflujo.backend

import com.example.diagramasdeflujo.enums.TipoEnum

class MensajeError(var tipo: TipoEnum) {
    var linea:Int = 0
    var columna:Int = 0
    var lexema:String = ""
    var descripcion:String = ""
}