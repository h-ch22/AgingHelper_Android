package com.cj.aginghelper.home.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cj.aginghelper.ui.theme.AgingHelperColorPalette
import com.cj.aginghelper.ui.theme.AgingHelperTheme

@Composable
fun HomeView(){
    AgingHelperTheme {
        Surface(color = AgingHelperColorPalette.current.background, modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.Top) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Rounded.Home, contentDescription = null)
                    Text(text = "홈", color = AgingHelperColorPalette.current.txtColor, fontWeight = FontWeight.SemiBold)
                }


            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeViewPreview(){
    HomeView()
}