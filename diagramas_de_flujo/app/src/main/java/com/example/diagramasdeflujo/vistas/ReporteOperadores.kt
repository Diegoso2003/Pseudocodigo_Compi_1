package com.example.diagramasdeflujo.vistas

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.diagramasdeflujo.Constantes
import com.example.diagramasdeflujo.backend.Pseudocodigo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReporteOperadores(
    navController: NavController,
    pseudocodigo: Pseudocodigo
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(Constantes.NOMBRE_APP) },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate(Constantes.rutaEditor)
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                }
            )
        },
        bottomBar = {
            BotonesExito(navController)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding)
        ) {

            val scrollState = rememberScrollState()
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.LightGray)
                    .padding(8.dp)
                    .horizontalScroll(scrollState)
            ) {
                Text("Operador", modifier = Modifier.weight(1f), fontWeight = FontWeight.Bold)
                Text("Linea", modifier = Modifier.weight(1f), fontWeight = FontWeight.Bold)
                Text("Columna", modifier = Modifier.weight(1f), fontWeight = FontWeight.Bold)
                Text("Ocurrencia", modifier = Modifier.weight(1f), fontWeight = FontWeight.Bold)
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(pseudocodigo.reporteOperador) { operador ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Text(operador.operador, modifier = Modifier.weight(1f))
                        Text(operador.linea.toString(), modifier = Modifier.weight(1f))
                        Text(operador.columna.toString(), modifier = Modifier.weight(1f))
                        Text(operador.ocurrencia, modifier = Modifier.weight(1f))
                    }
                    Divider()
                }
            }
        }
    }
}