package com.example.diagramasdeflujo.backend.estructuras

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import com.example.diagramasdeflujo.Constantes
import com.example.diagramasdeflujo.enums.Figura
import com.example.diagramasdeflujo.enums.Inst
import com.example.diagramasdeflujo.enums.Letra

abstract class Accion(var tipo: Inst, var figura: Figura, var color: Color) {

    open fun setFigura(figura: Figura){
        this.figura = figura
    }

    open fun setTipoLetra(letra: Letra){
        this.tipoLetra = letra.fuente
    }

    open fun setTamañoLetra(tamaño: TextUnit){
        this.tamañoLetra = tamaño
    }

    open fun setColor(color: Color){
        this.color = color
    }

    open fun setColorTexto(color: Color){
        this.color = color
    }
    abstract fun porDefecto()
    abstract fun agregarLista(acciones: ArrayList<Accion>, indices: Array<ArrayList<Accion>>)
    abstract fun esCiclo(): Boolean
    abstract fun getTexto(): String
    var colorTexto = Constantes.COLOR_TEXTO_DEFECTO;
    var tamañoLetra = Constantes.TAMAÑO_NORMAL;
    var tipoLetra = Constantes.TIPO_LETRA;
}