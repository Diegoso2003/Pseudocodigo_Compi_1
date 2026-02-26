package com.example.diagramasdeflujo.vistas

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
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
    var texto by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(""))
    }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->

        uri?.let {
            context.contentResolver.openInputStream(it)?.use { inputStream ->
                var contenido: String = inputStream.bufferedReader().readText()
                texto = TextFieldValue(contenido)
            }
        }
    }
    val lineas = texto.text.substring(0, texto.selection.start)
    val numeroLinea = lineas.count { it == '\n' } + 1
    val numeroColumna = lineas.length - (lineas.lastIndexOf('\n') + 1)
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
                .imePadding()
        ) {
            val scrollState = rememberScrollState()
            OutlinedTextField(
                value = texto,
                onValueChange = { texto = it },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .verticalScroll(scrollState),
                label = { Text("Escribe el pseudocódigo aquí") }
            )
            Text(
                text = "Línea: $numeroLinea  Columna: $numeroColumna",
                modifier = Modifier.padding(8.dp)
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
                    contralador.texto = "${texto.text}\n"
                    if(!contralador.texto.isBlank()){
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
}