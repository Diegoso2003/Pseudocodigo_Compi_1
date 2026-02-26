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
import com.example.diagramasdeflujo.Constantes
import com.example.diagramasdeflujo.backend.Pseudocodigo

@Composable
fun BotonesExito(
    navController: NavController
){
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ){
        Button(onClick = {
            navController.navigate(Constantes.rutaDiagrama)
        }) {
            Text("Diagrama")
        }

        BotonReportes(navController)
    }
}

@Composable
fun BotonReportes(
    navController: NavController
) {

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
                    navController.navigate(Constantes.rutaReportesOperadores)
                }
            )

            DropdownMenuItem(
                text = { Text("Reporte estructuras de control") },
                onClick = {
                    expanded = false
                    navController.navigate(Constantes.rutaReportesCiclos)
                }
            )
        }
    }
}