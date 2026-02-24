package com.example.diagramasdeflujo.vistas

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class AppViewModel : ViewModel() {

    var codigo by mutableStateOf("")
        private set

    var diagrama by mutableStateOf<String?>(null)
        private set

    var errores by mutableStateOf<List<String>>(emptyList())
        private set

    fun actualizarCodigo(nuevo: String) {
        codigo = nuevo
    }

    fun generarDiagrama() {
        diagrama = "Diagrama generado correctamente"
    }

    fun analizarCodigo() {
        errores = listOf(
            "Error en línea 3",
            "Variable no declarada en línea 7"
        )
    }
}