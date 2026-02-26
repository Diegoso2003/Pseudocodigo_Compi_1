package com.example.diagramasdeflujo.vistas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.diagramasdeflujo.backend.Pseudocodigo

@Composable
fun BotonesExito(
    navController: NavController,
    pseudocodigo: Pseudocodigo
){
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ){
        Button(onClick = {

        }) {
            Text("Diagrama")
        }

        BotonReportes()
    }
}

@Composable
fun BotonReportes() {

    var expanded by remember { mutableStateOf(false) }

    Box {

        Button(onClick = { expanded = true }) {
            Text("Reportes")
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {

            DropdownMenuItem(
                text = { Text("Reporte operadores matemáticos") },
                onClick = {
                    expanded = false
                    println("Reporte estructuras de control")
                }
            )

            DropdownMenuItem(
                text = { Text("Reporte léxico") },
                onClick = {
                    expanded = false
                    println("Generar reporte léxico")
                }
            )
        }
    }
}