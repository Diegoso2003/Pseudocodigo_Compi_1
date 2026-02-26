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
    var reporteOperador = ArrayList<ReporteOperador>()
    var indices = arrayOf(ArrayList<Accion>(), ArrayList<Accion>(), ArrayList<Accion>()
    , ArrayList<Accion>())
    var bloque: Bloque? = null

    fun agregarAccion(accion: Accion) {
        bloque = null
        accion.agregarLista(acciones, indices)
    }

    fun agregarInstruccion(instruccion: String){
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
        reporteEstructura.clear()
        reporteOperador.clear()
        for(i in 0 until indices.size){
            indices[i].clear()
        }
        val lexer = DiagramaLexer(StringReader(texto))
        lexer.setErrores(errores)
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
        if(indice <= listaAux.size && indice > 0){
            listaAux.get(indice-1).cambiarTipoLetra(letra)
        }
    }

    fun cambiarFigura(figura: Figura, indice: Int, inst: Inst){
        var listaAux = indices[inst.ordinal]
        Log.d("hecho","cambio figura 1")
        if(indice <= listaAux.size && indice > 0){
            listaAux.get(indice-1).cambiarFigura(figura)
            Log.d("hecho","cambio figura 2")
        }
    }

    fun cambiarTamaño(tamaño: Double, indice: Int, inst: Inst){
        var listaAux = indices[inst.ordinal]
        if(indice <= listaAux.size && indice > 0){
            listaAux.get(indice-1).`cambiarTamañoLetra`(tamaño.sp)
        }
    }

    fun cambiarColor(color: Object, indice: Int, inst: Inst){
        var listaAux = indices[inst.ordinal]
        if(indice <= listaAux.size && indice > 0){
            listaAux.get(indice-1).cambiarColor(color as Color)
        }
    }

    fun cambiarColorTexto(color: Object, indice: Int, inst: Inst){
        var listaAux = indices[inst.ordinal]
        if(indice <= listaAux.size && indice > 0){
            listaAux.get(indice-1).cambiarColorTexto(color as Color)
        }
    }

    fun porDefecto(indice: Int){
        var listaAux = indices[indices.size-1]
        if(indice < listaAux.size && indice > 0){
            listaAux.get(indice-1).porDefecto()
        }
    }

    fun hexaAColor(hex: String): Any {
        return Color(AndroidColor.parseColor(hex))
    }

    fun rgbAColor(r: Double, g: Double, b: Double): Any {
        return Color(r.toInt(), g.toInt(), b.toInt())
    }

    fun agregarReporteOperador(linea: Int, columna: Int, cadenas: Array<String>){
        var reporte = ReporteOperador()
        reporte.linea = linea
        reporte.columna = columna
        when(cadenas[1]){
            "+" -> {reporte.operador = "Suma"}
            "-" -> {reporte.operador = "Resta"}
            "*" -> {reporte.operador = "Multiplicación"}
            "/" -> {reporte.operador = "División"}
        }
        reporte.ocurrencia = "${cadenas[0]} ${cadenas[1]} ${cadenas[2]}"
        reporteOperador.add(reporte)
    }

}