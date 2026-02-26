package com.example.diagramasdeflujo.vistas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.drawText
import androidx.navigation.NavController
import com.example.diagramasdeflujo.Constantes
import com.example.diagramasdeflujo.backend.Pseudocodigo
import com.example.diagramasdeflujo.backend.estructuras.Accion
import com.example.diagramasdeflujo.enums.Figura

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HacerDiagrama(
    navController: NavController,
    pseudocodigo: Pseudocodigo
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(Constantes.NOMBRE_APP) }
            )
        },
        bottomBar = {
            BotonesExito(navController, pseudocodigo)
        }
    ) { padding ->
        Dibujar(pseudocodigo.acciones, padding)
    }
}

@Composable
fun Dibujar(
    lista: ArrayList<Accion>,
    padding: PaddingValues
) {
    val scrollState = rememberScrollState()
    val textMeasurer = rememberTextMeasurer()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(padding)
            .background(Color(0xFFF5F5F5))
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height((lista.size * 100).dp)
                .padding(padding)
        ) {
            val nodoPositions = mutableListOf<Pair<Float, Float>>()
            var currentY = 0f
            lista.forEachIndexed { index, nodo ->

                val textLayout = textMeasurer.measure(
                    text = AnnotatedString(nodo.getTexto()),
                    style = TextStyle(
                        color = nodo.colorTexto,
                        fontSize = nodo.tamañoLetra,
                        fontFamily = nodo.tipoLetra
                    )
                )
                val spacing = 80f
                val padding = 60f
                val baseWidth = textLayout.size.width + padding
                val baseHeight = textLayout.size.height + padding

                val width = if (nodo.figura == Figura.ROMBO) baseWidth * 1.4f else baseWidth
                val height = if (nodo.figura == Figura.ROMBO) baseHeight * 1.4f else baseHeight

                val x = (size.width - width) / 2
                val centerX = size.width / 2
                val bottomY = currentY + height

                when (nodo.figura) {

                    Figura.RECTANGULO -> {
                        drawRect(nodo.color, Offset(x, currentY), Size(width, height))
                    }

                    Figura.RECTANGULO_REDONDEADO -> {
                        drawRoundRect(
                            nodo.color,
                            Offset(x, currentY),
                            Size(width, height),
                            CornerRadius(40f, 40f)
                        )
                    }

                    Figura.ELIPSE -> {
                        drawOval(nodo.color, Offset(x, currentY), Size(width, height))
                    }

                    Figura.CIRCULO -> {
                        val diameter = maxOf(width, height)
                        drawCircle(
                            nodo.color,
                            diameter / 2,
                            Offset(centerX, currentY + diameter / 2)
                        )
                    }

                    Figura.ROMBO -> {
                        val path = Path().apply {
                            moveTo(centerX, currentY)
                            lineTo(x + width, currentY + height / 2)
                            lineTo(centerX, currentY + height)
                            lineTo(x, currentY + height / 2)
                            close()
                        }
                        drawPath(path, nodo.color)
                    }

                    Figura.PARALELOGRAMO -> {

                        val inclinacion = 40f

                        val path = Path().apply {
                            moveTo(x + inclinacion, currentY)
                            lineTo(x + width, currentY)
                            lineTo(x + width - inclinacion, currentY + height)
                            lineTo(x, currentY + height)
                            close()
                        }

                        drawPath(path, nodo.color)
                    }
                }

                drawText(
                    textLayout,
                    topLeft = Offset(
                        x + (width - textLayout.size.width) / 2,
                        currentY + (height - textLayout.size.height) / 2
                    )
                )
                nodoPositions.add(centerX to bottomY)
                currentY += height + spacing

            }

            for (i in 0 until nodoPositions.size - 1) {

                val (startX, startY) = nodoPositions[i]
                val (endX, _) = nodoPositions[i + 1]

                drawArrow(
                    start = Offset(startX, startY),
                    end = Offset(endX, startY + 60f)
                )
            }
        }
    }
}

fun DrawScope.drawArrow(start: Offset, end: Offset) {

    drawLine(
        color = Color.Black,
        start = start,
        end = end,
        strokeWidth = 4f
    )

    val arrowSize = 20f

    val angle = kotlin.math.atan2(
        end.y - start.y,
        end.x - start.x
    )

    val arrowPath = Path().apply {
        moveTo(end.x, end.y)
        lineTo(
            end.x - arrowSize * kotlin.math.cos(angle - Math.PI / 6).toFloat(),
            end.y - arrowSize * kotlin.math.sin(angle - Math.PI / 6).toFloat()
        )
        lineTo(
            end.x - arrowSize * kotlin.math.cos(angle + Math.PI / 6).toFloat(),
            end.y - arrowSize * kotlin.math.sin(angle + Math.PI / 6).toFloat()
        )
        close()
    }

    drawPath(arrowPath, Color.Black)
}