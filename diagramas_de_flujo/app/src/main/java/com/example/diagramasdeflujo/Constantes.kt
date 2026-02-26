package com.example.diagramasdeflujo

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import com.example.diagramasdeflujo.enums.Figura
import com.example.diagramasdeflujo.enums.Letra

class Constantes {
    companion object {
        const val NOMBRE_APP = "Diagramas de flujo"
        const val rutaEditor = "editor"
        const val rutaReportesCiclos = "reporte_ciclos"
        const val rutaReportesOperadores = "reporte_operadores"
        const val rutaDiagrama = "diagrama"
        const val rutaReporteErrores = "reporte_errores"
        val COLOR_TEXTO_DEFECTO = Color.Black
        val COLOR_FONDO_DEFECTO = Color.White
        val COLOR_INICIO_FIN = Color(0xFFE3F2FD)
        val COLOR_SI = Color(0xFFFFF9C4)
        val COLOR_BLOQUE = Color(0xFFC8E6C9)
        val COLOR_MIENSTRAS = Color(0xFFE1BEE7)
        val FIGURA_INICIO_FIN = Figura.ELIPSE
        val FIGURA_MIENTRAS = Figura.ROMBO
        val FIGURA_SI = Figura.ROMBO
        val FIGURA_BLOQUE = Figura.RECTANGULO
        val TAMAÑO_NORMAL = 14.sp
        val TIPO_LETRA: FontFamily = Letra.ARIAL.fuente
    }
}