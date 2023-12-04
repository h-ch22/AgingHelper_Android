package com.cj.aginghelper.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorScheme = darkColorScheme(
    primary = accent,
    secondary = accent,
    tertiary = accent
)

private val LightColorScheme = lightColorScheme(
    primary = accent,
    secondary = accent,
    tertiary = accent
)

private val onLightColorPalette = ColorPalette(
    background = background,
    txtColor = txtColor,
    btnColor = btnColor,
    txtFieldColor = txtField
)

private val onDarkColorPalette = ColorPalette(
    background = backgroundAsDark,
    txtColor = txtColorAsDark,
    btnColor = btnColorAsDark,
    txtFieldColor = txtFieldAsDark
)

@Composable
fun AgingHelperTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val colorPalette =
        if (darkTheme) onDarkColorPalette
        else onLightColorPalette

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )

    val systemUiController = rememberSystemUiController()

    if(darkTheme){
        systemUiController.setSystemBarsColor(
            color = backgroundAsDark
        )
    } else{
        systemUiController.setSystemBarsColor(
            color = background
        )
    }
}