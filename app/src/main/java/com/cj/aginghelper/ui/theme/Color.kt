package com.cj.aginghelper.ui.theme

import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val accent = Color(0xFFE8919A)
val background = Color(0xFFFFFDF5)
val btnColor = Color(0xFFFFFFFF)
val petBG_1 = Color(0xFFFF7D77)
val petBG_2 = Color(0xFFFF93BC)
val petBG_3 = Color(0xFFFFCB50)
val petBG_4 = Color(0xFF55D368)
val petBG_5 = Color(0xFFAE5DFF)
val txtDark = Color(0xFFFFFFFF)
val txtColor = Color(0xFF303030)
val black = Color(0xFF000000)
val white = Color(0xFFFFFFFF)
val red = Color(0xFFff3b30)
val orange = Color(0xFFff9500)
val green = Color(0xFF34c759)
val mint = Color(0xFF00c7bd)
val teal = Color(0xFF30b0c7)
val cyan = Color(0xFF32ade6)
val blue = Color(0xFF007bff)
val indigo = Color(0xFF5856d6)
val purple = Color(0xFFaf52de)
val pink = Color(0xFFff2d54)
val brown = Color(0xFFa2855e)
val gray = Color(0xFF8e8e93)
val txtField = Color(0x80FFFFFF)

val backgroundAsDark = Color(0xFF52514E)
val btnColorAsDark = Color(0xFF40403F)
val txtColorAsDark = Color(0xFFFFFFFF)
val txtFieldAsDark = Color(0xFF40403F)

@Composable
fun getTxtFieldColors(): TextFieldColors {
    return TextFieldDefaults.colors(cursorColor = accent, focusedIndicatorColor = accent, focusedLabelColor = accent, focusedLeadingIconColor = accent)
}