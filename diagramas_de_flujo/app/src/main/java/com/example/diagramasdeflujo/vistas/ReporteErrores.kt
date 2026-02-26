package com.example.diagramasdeflujo.vistas

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.diagramasdeflujo.backend.Pseudocodigo

@Composable
fun MostrarErrores(
    navController: NavController,
    pseudocodigo: Pseudocodigo){
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(pseudocodigo.errores) { item ->
                Text("${item.tipo}\n${item.descripcion}\n${item.lexema}\n${item.linea}")
            }
        }

}