package com.cj.aginghelper.prediction.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccessTime
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.PersonSearch
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cj.aginghelper.ui.theme.AgingHelperColorPalette
import com.cj.aginghelper.ui.theme.AgingHelperTheme
import com.cj.aginghelper.ui.theme.accent
import com.cj.aginghelper.ui.theme.gray

@Composable
fun PredictionView(){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "PredictionView"){
        composable("PredictionFileLoadView"){
            PredictionFileLoadView(parentNavController = navController)
        }

        composable("PredictionView"){
            AgingHelperTheme {
                Surface(color = AgingHelperColorPalette.current.background, modifier = Modifier.fillMaxSize()) {
                    Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.Top) {
                        Row(verticalAlignment = Alignment.Top) {
                            Icon(imageVector = Icons.Rounded.PersonSearch, contentDescription = null)
                            Text(text = "예측 시작하기", fontWeight = FontWeight.SemiBold, color = AgingHelperColorPalette.current.txtColor)
                        }

                        Spacer(modifier = Modifier.weight(1f))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Rounded.PersonSearch, contentDescription = null, tint = accent)

                            Spacer(modifier = Modifier.width(10.dp))

                            Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.Start) {
                                Text(text = "이미지만으로 사람의 미래 모습 예측하기", fontWeight = FontWeight.SemiBold, color = accent)
                                Text(text = "한 장의 이미지만으로 사람의 미래 모습을 확인할 수 있습니다.", color = gray, fontSize = 12.sp)
                            }
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Rounded.Share, contentDescription = null, tint = accent)

                            Spacer(modifier = Modifier.width(10.dp))

                            Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.Start) {
                                Text(text = "이미지 공유", fontWeight = FontWeight.SemiBold, color = accent)
                                Text(text = "오래 전에 실종된 사람의 현재 모습을 예측하려 하시나요?\n이미지를 저장하고 공유해보세요.", color = gray, fontSize = 12.sp, lineHeight = 12.sp)
                            }
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Rounded.AccessTime, contentDescription = null, tint = accent)

                            Spacer(modifier = Modifier.width(10.dp))

                            Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.Start) {
                                Text(text = "예측 기록", fontWeight = FontWeight.SemiBold, color = accent)
                                Text(text = "더 보기 탭에서 예측 기록을 다시 확인할 수 있습니다.", color = gray, fontSize = 12.sp, lineHeight = 12.sp)
                            }
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Rounded.CheckCircle, contentDescription = null, tint = accent)

                            Spacer(modifier = Modifier.width(10.dp))

                            Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.Start) {
                                Text(text = "AI가 공허함을 채울 수 없다는 사실을 인지하기", fontWeight = FontWeight.SemiBold, color = accent)
                                Text(text = "AgingHelper는 AI 기술을 활용해 사람의 미래 모습을 예측하는데 있어 정확한 정보 반환을 보증하지 않습니다.\n(장기)실종자의 현재 모습을 예측하려는 경우 우선 수사기관에 의뢰하여 적합한 절차를 따르십시오.\n" +
                                        "사망한 사람의 현재 모습을 예측하려는 경우 사용자가 과도하게 이입되지 않도록 보호자와 함께 세션을 진행하십시오.", color = gray, fontSize = 12.sp, lineHeight = 15.sp)
                            }
                        }

                        Spacer(modifier = Modifier.weight(1f))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Spacer(modifier = Modifier.weight(1f))

                            Button(onClick = {
                                navController.navigate("PredictionFileLoadView"){
                                    popUpTo("PredictionView"){
                                        inclusive = false
                                    }
                                }
                            }, colors = ButtonDefaults.buttonColors(containerColor = accent)) {
                                Text(text = "다음")
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
fun PredictionViewPreview(){
    PredictionView()
}