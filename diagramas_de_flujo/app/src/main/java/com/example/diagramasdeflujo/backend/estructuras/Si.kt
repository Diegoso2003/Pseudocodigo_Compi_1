package com.example.diagramasdeflujo.backend.estructuras

import com.example.diagramasdeflujo.Constantes
import com.example.diagramasdeflujo.enums.Figura
import com.example.diagramasdeflujo.enums.Inst
import com.example.diagramasdeflujo.enums.Letra

class Si(var condicion:String, var bloque:Bloque) : Accion(Inst.SI, Constantes.FIGURA_SI,
    Constantes.COLOR_SI) {

    private var ultimo = Bloque("FIN SI")
    override fun agregarLista(
        acciones: ArrayList<Accion>,
        indices: Array<ArrayList<Accion>>
    ) {
        indices[indices.size-1].add(this)
        ultimo.figura = this.figura
        ultimo.color = this.color
        acciones.add(this)
        indices[tipo.ordinal].add(this)
        acciones.add(bloque)
        indices[bloque.tipo.ordinal].add(bloque)
        acciones.add(ultimo)
    }

    override fun getTexto(): String {
        return "SI\n$condicion"
    }

    override fun esCiclo(): Boolean {
        return true
    }

    override fun setFigura(figura: Figura) {
        super.setFigura(figura)
        ultimo.figura = figura
    }

    override fun setTipoLetra(letra: Letra) {
        super.setTipoLetra(letra)
        ultimo.tipoLetra = letra.fuente
    }

    override fun porDefecto() {
        color = Constantes.COLOR_SI
        ultimo.color = Constantes.COLOR_SI
        colorTexto = Constantes.COLOR_TEXTO_DEFECTO
        ultimo.color = Constantes.COLOR_TEXTO_DEFECTO
        figura = Constantes.FIGURA_SI
        ultimo.figura = Constantes.FIGURA_SI
        tipoLetra = Constantes.TIPO_LETRA
        ultimo.tipoLetra = Constantes.TIPO_LETRA
        tamañoLetra = Constantes.TAMAÑO_NORMAL
        ultimo.tamañoLetra = Constantes.TAMAÑO_NORMAL
    }
}