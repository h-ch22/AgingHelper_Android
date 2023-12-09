package com.cj.aginghelper.more.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.cj.aginghelper.ui.theme.AgingHelperColorPalette
import com.cj.aginghelper.ui.theme.AgingHelperTheme

@Composable
fun MoreView(){
    AgingHelperTheme {
        Surface(color = AgingHelperColorPalette.current.background, modifier = Modifier.fillMaxSize()) {

        }
    }
}