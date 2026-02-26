package com.example.diagramasdeflujo.enums

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.example.diagramasdeflujo.R

enum class Letra(var fuente: FontFamily) {
    ARIAL(FontFamily.Default),
    COMICS_SAM(FontFamily(
    Font(R.font.comic_neue_regular)
    )),
    TIMES_NEW_ROMAN(FontFamily(
        Font(R.font.noto_serif_regular)
    )),
    VERDANA(FontFamily(
        Font(R.font.noto_sans_regular)
    ))
}