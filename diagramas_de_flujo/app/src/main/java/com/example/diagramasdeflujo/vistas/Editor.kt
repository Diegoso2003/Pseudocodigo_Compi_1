package com.example.diagramasdeflujo.vistas

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.diagramasdeflujo.Constantes
import com.example.diagramasdeflujo.backend.Pseudocodigo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Editor(
    navController: NavController,
    contralador: Pseudocodigo
) {
    val context = LocalContext.current
    var texto by remember { mutableStateOf("") }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->

        uri?.let {
            context.contentResolver.openInputStream(it)?.use { inputStream ->
                texto = inputStream.bufferedReader().readText()
            }
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(Constantes.NOMBRE_APP) }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            OutlinedTextField(
                value = texto,
                onValueChange = { texto = it },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                label = { Text("Escribe el pseudocódigo aquí") }
            )

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = {
                    launcher.launch(arrayOf("*/*"))
                }) {
                    Text("Abrir archivo")
                }

                Button(onClick = {
                    contralador.texto = "${texto}\n"
                    if(!texto.isEmpty()){
                        if(contralador.analizarCodigo()){
                            navController.navigate(Constantes.rutaDiagrama)
                        } else {
                            navController.navigate(Constantes.rutaReporteErrores)
                        }
                    }
                }) {
                    Text("Crear diagrama")
                }
            }
        }
    }

    if(!contralador.texto.isEmpty()){
        texto = contralador.texto
    }
}