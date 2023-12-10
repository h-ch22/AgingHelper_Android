package com.cj.aginghelper.prediction.ui

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.PersonSearch
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.cj.aginghelper.ui.theme.AgingHelperColorPalette
import com.cj.aginghelper.ui.theme.AgingHelperTheme
import com.cj.aginghelper.ui.theme.accent
import com.cj.aginghelper.ui.theme.gray
import com.cj.aginghelper.ui.theme.white

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PredictionAgeSelectionView(uri: Uri, navHostController: NavHostController) {
    val context = LocalContext.current
    val navController = rememberNavController()

    val ageSelection = remember {
        mutableFloatStateOf(0.2f)
    }

    val useAllAge = remember {
        mutableStateOf(false)
    }

    NavHost(navController = navController, startDestination = "PredictionAgeSelectionView") {
        composable("OnPredictionView"){
            OnPredictionView(uri = uri, parentNavController = navController, preferredAge = if(useAllAge.value) null else (ageSelection.floatValue * 100).toInt())
        }
        composable("PredictionAgeSelectionView") {
            AgingHelperTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text(text = "예측 나이 선택", color = accent) },
                            navigationIcon = {
                                IconButton(onClick = {
                                    navHostController.popBackStack()
                                }) {
                                    Icon(
                                        imageVector = Icons.Rounded.ArrowBack,
                                        contentDescription = null,
                                        tint = accent
                                    )
                                }
                            },
                            colors = TopAppBarDefaults.topAppBarColors(containerColor = AgingHelperColorPalette.current.background)
                        )
                    }
                ) {
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it), color = AgingHelperColorPalette.current.background
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp),
                            verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.PersonSearch,
                                contentDescription = null,
                                tint = AgingHelperColorPalette.current.txtColor,
                                modifier = Modifier.size(50.dp)
                            )

                            Text(
                                text = "예측 나이 선택하기",
                                fontWeight = FontWeight.SemiBold,
                                color = AgingHelperColorPalette.current.txtColor,
                                fontSize = 18.sp
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                text = "예측하려는 나이를 선택하십시오. 전체 연령대의 이미지를 모두 확인하려는 경우 [전체 연령 확인] 버튼을 이용하십시오.",
                                color = gray,
                                fontSize = 12.sp,
                                lineHeight = 12.sp,
                                textAlign = TextAlign.Center
                            )

                            Spacer(modifier = Modifier.height(20.dp))

                            if (uri != Uri.EMPTY) {
                                val painter = rememberAsyncImagePainter(
                                    ImageRequest
                                        .Builder(context)
                                        .data(uri)
                                        .build()
                                )

                                Image(
                                    painter = painter,
                                    contentDescription = null,
                                    modifier = Modifier.height(200.dp)
                                )

                                Spacer(modifier = Modifier.height(20.dp))
                            }

                            Slider(
                                value = ageSelection.floatValue,
                                onValueChange = {
                                    ageSelection.floatValue = it
                                },
                                colors = SliderDefaults.colors(
                                    thumbColor = accent,
                                    activeTickColor = accent,
                                    inactiveTickColor = gray,
                                    activeTrackColor = accent
                                ),
                                steps = 8,
                                valueRange = 0f..0.9f,
                                enabled = !useAllAge.value,
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                text = "예측 나이 : ${String.format("%.0f", ageSelection.floatValue * 100)}대",
                                fontWeight = FontWeight.SemiBold,
                                color = if (!useAllAge.value) accent else gray,
                                fontSize = 18.sp
                            )

                            Spacer(modifier = Modifier.height(20.dp))

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Start
                            ) {
                                Checkbox(
                                    checked = useAllAge.value,
                                    onCheckedChange = { useAllAge.value = it },
                                    colors = CheckboxDefaults.colors(
                                        checkedColor = accent,
                                        uncheckedColor = accent,
                                        checkmarkColor = white
                                    )
                                )
                                Text(
                                    text = "전체 연령 확인",
                                    color = AgingHelperColorPalette.current.txtColor
                                )

                                Spacer(modifier = Modifier.weight(1f))
                            }

                            Spacer(modifier = Modifier.height(20.dp))

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Spacer(modifier = Modifier.weight(1f))

                                Button(
                                    onClick = {
                                        navController.navigate("OnPredictionView"){
                                            popUpTo("PredictionAgeSelectionView"){
                                                inclusive = false
                                            }
                                        }
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = accent)
                                ) {
                                    Text(text = "다음")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PredictionAgeSelectionViewPreview() {
    PredictionAgeSelectionView(Uri.EMPTY, rememberNavController())
}