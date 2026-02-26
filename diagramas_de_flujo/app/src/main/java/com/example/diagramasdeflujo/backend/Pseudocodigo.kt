package com.example.diagramasdeflujo.backend

import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import android.graphics.Color as AndroidColor
import com.example.diagramasdeflujo.Constantes
import com.example.diagramasdeflujo.analizadores.DiagramaLexer
import com.example.diagramasdeflujo.analizadores.DiagramaParser
import com.example.diagramasdeflujo.backend.estructuras.Accion
import com.example.diagramasdeflujo.backend.estructuras.Bloque
import com.example.diagramasdeflujo.enums.Figura
import com.example.diagramasdeflujo.enums.Inst
import com.example.diagramasdeflujo.enums.Letra
import java.io.StringReader

class Pseudocodigo {
    var acciones = ArrayList<Accion>()
    var errores = ArrayList<MensajeError>()
    var texto:String = ""
    var reporteEstructura = ArrayList<ReporteEstructura>()
    var indices = arrayOf(ArrayList<Accion>(), ArrayList<Accion>(), ArrayList<Accion>()
    , ArrayList<Accion>())
    var bloque: Bloque? = null

    fun agregarAccion(accion: Accion) {
        bloque = null
        accion.agregarLista(acciones, indices)
        Log.d("MENSAJE", "agregado ${accion.tipo.name}")
    }

    fun agregarInstruccion(instruccion: String){
        Log.d("MENSAJE", "agregado bloque")
        if(crearBloque()){
            bloque = Bloque(instruccion)
            bloque?.agregarLista(acciones, indices)
            return
        }
        bloque?.agregarInstruccion(instruccion)
    }

    fun crearBloque():Boolean {
        return bloque == null
    }

    fun agregarReporteDeEstructura(reporte: ReporteEstructura){
        reporteEstructura.add(reporte)
    }

    fun analizarCodigo(): Boolean{
        errores.clear()
        val lexer = DiagramaLexer(StringReader(texto))
        val parser = DiagramaParser(lexer, this)
        bloque = null
        try {
            acciones.clear()
            var inicio = Bloque("Inicio")
            var ultimo = Bloque("Final")
            inicio.figura = Constantes.FIGURA_INICIO_FIN
            ultimo.figura = Constantes.FIGURA_INICIO_FIN
            inicio.color = Constantes.COLOR_INICIO_FIN
            ultimo.color = Constantes.COLOR_INICIO_FIN
            acciones.add(inicio)
            parser.parse()
            acciones.add(ultimo)
            return errores.isEmpty()
        } catch (e: Exception) {
            return false;
        }
    }

    fun cambiarLetra(letra: Letra, indice: Int, instru: Inst){
        var listaAux = indices[instru.ordinal]
        if(indice <= listaAux.size){
            listaAux.get(indice-1).setTipoLetra(letra)
        }
    }

    fun cambiarFigura(figura: Figura, indice: Int, inst: Inst){
        var listaAux = indices[inst.ordinal]
        if(indice <= listaAux.size && indice > 0){
            listaAux.get(indice-1).setFigura(figura)
        }
    }

    fun cambiarTamaño(tamaño: Double, indice: Int, inst: Inst){
        var listaAux = indices[inst.ordinal]
        if(indice <= listaAux.size && indice > 0){
            listaAux.get(indice-1).setTamañoLetra(tamaño.sp)
        }
    }

    fun cambiarColor(color: Object, indice: Int, inst: Inst){
        var listaAux = indices[inst.ordinal]
        if(indice <= listaAux.size && indice > 0){
            listaAux.get(indice-1).setColor(color as Color)
        }
    }

    fun cambiarColorTexto(color: Object, indice: Int, inst: Inst){
        var listaAux = indices[inst.ordinal]
        if(indice <= listaAux.size && indice > 0){
            listaAux.get(indice-1).setColorTexto(color as Color)
        }
    }

    fun porDefecto(indice: Int){
        var listaAux = indices[indices.size-1]
        if(indice <= listaAux.size && indice > 0){
            listaAux.get(indice).porDefecto()
        }
    }

    fun hexaAColor(hex: String): Any {
        return Color(AndroidColor.parseColor(hex))
    }

    fun rgbAColor(r: Double, g: Double, b: Double): Any {
        return Color(r.toInt(), g.toInt(), b.toInt())
    }


}