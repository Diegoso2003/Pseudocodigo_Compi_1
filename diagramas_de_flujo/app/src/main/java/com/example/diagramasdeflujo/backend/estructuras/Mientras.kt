package com.example.diagramasdeflujo.backend.estructuras

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import com.example.diagramasdeflujo.Constantes
import com.example.diagramasdeflujo.enums.Figura
import com.example.diagramasdeflujo.enums.Inst
import com.example.diagramasdeflujo.enums.Letra

class Mientras(var condicion:String, var bloque:Bloque) : Accion(Inst.MIENTRAS,
    Constantes.FIGURA_MIENTRAS, Constantes.COLOR_MIENSTRAS
) {
    private var ultimo:Bloque = Bloque("FIN MIENTRAS")

    override fun agregarLista(
        acciones: ArrayList<Accion>,
        indices: Array<ArrayList<Accion>>
    ) {
        ultimo.figura = this.figura
        ultimo.color = this.color
        acciones.add(this)
        indices[tipo.ordinal].add(this)
        indices[indices.size-1].add(this)
        acciones.add(bloque)
        indices[bloque.tipo.ordinal].add(bloque)
        acciones.add(ultimo)
    }

    override fun getTexto(): String {
        return "Mientras\n$condicion"
    }

    override fun esCiclo(): Boolean {
        return true;
    }

    override fun setFigura(figura: Figura) {
        super.setFigura(figura)
        ultimo.figura = figura
    }

    override fun setTipoLetra(letra: Letra) {
        super.setTipoLetra(letra)
        ultimo.tipoLetra = letra.fuente
    }

    override fun setTamañoLetra(tamaño: TextUnit) {
        super.setTamañoLetra(tamaño)
        ultimo.tamañoLetra = tamaño
    }

    override fun setColor(color: Color) {
        super.setColor(color)
        ultimo.color = color
    }

    override fun setColorTexto(color: Color) {
        super.setColorTexto(color)
        ultimo.colorTexto = color
    }

    override fun porDefecto() {
        color = Constantes.COLOR_MIENSTRAS
        ultimo.color = Constantes.COLOR_MIENSTRAS
        colorTexto = Constantes.COLOR_TEXTO_DEFECTO
        ultimo.color = Constantes.COLOR_TEXTO_DEFECTO
        figura = Constantes.FIGURA_MIENTRAS
        ultimo.figura = Constantes.FIGURA_MIENTRAS
        tipoLetra = Constantes.TIPO_LETRA
        ultimo.tipoLetra = Constantes.TIPO_LETRA
        tamañoLetra = Constantes.TAMAÑO_NORMAL
        ultimo.tamañoLetra = Constantes.TAMAÑO_NORMAL
    }
}