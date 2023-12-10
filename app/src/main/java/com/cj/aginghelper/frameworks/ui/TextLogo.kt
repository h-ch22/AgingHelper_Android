package com.cj.aginghelper.frameworks.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.cj.aginghelper.R
import com.cj.aginghelper.ui.theme.AgingHelperColorPalette
import com.cj.aginghelper.ui.theme.AgingHelperTheme

@Composable
fun TextLogo(fontSize: Int){
    val header = "Aging"
    val globalText = stringResource(id = R.string.app_name, header)
    val start = globalText.indexOf(header)
    val spanStyles = listOf(
        AnnotatedString.Range(
            SpanStyle(fontWeight = FontWeight.SemiBold),
            start = start,
            end = start + header.length
        )
    )

    AgingHelperTheme {
        Text(text = AnnotatedString(text = globalText, spanStyles = spanStyles), fontSize = fontSize.sp, color = AgingHelperColorPalette.current.txtColor)
    }
}

@Preview
@Composable
fun TextLogoPreview(){
    TextLogo(fontSize = 18)
}