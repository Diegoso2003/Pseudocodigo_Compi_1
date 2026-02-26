package com.example.diagramasdeflujo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.diagramasdeflujo.backend.Pseudocodigo
import com.example.diagramasdeflujo.backend.ReporteOperador
import com.example.diagramasdeflujo.vistas.Editor
import com.example.diagramasdeflujo.vistas.HacerDiagrama
import com.example.diagramasdeflujo.vistas.ReporteCiclos
import com.example.diagramasdeflujo.vistas.ReporteErrores
import com.example.diagramasdeflujo.vistas.ReporteOperadores

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val controlador = Pseudocodigo()

            NavHost(
                navController = navController,
                startDestination = "editor"
            ) {
                composable(Constantes.rutaEditor) {
                    Editor(navController, controlador)
                }

                composable(Constantes.rutaDiagrama) {
                    HacerDiagrama(navController, controlador)
                }

                composable(Constantes.rutaReporteErrores){
                    ReporteErrores(navController, controlador)
                }

                composable(Constantes.rutaReportesCiclos){
                    ReporteCiclos(navController, controlador)
                }

                composable(Constantes.rutaReportesOperadores){
                    ReporteOperadores(navController, controlador)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

/**
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DiagramasDeFlujoTheme {

    }
}**/