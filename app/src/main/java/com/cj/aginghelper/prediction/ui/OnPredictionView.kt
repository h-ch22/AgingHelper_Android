package com.cj.aginghelper.prediction.ui

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Error
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.cj.aginghelper.prediction.models.PredictionProgressTypeModel
import com.cj.aginghelper.ui.theme.AgingHelperColorPalette
import com.cj.aginghelper.ui.theme.AgingHelperTheme
import com.cj.aginghelper.ui.theme.accent
import com.cj.aginghelper.ui.theme.gray
import com.cj.aginghelper.ui.theme.green
import com.cj.aginghelper.ui.theme.red

@Composable
fun OnPredictionView(uri: Uri, parentNavController: NavHostController, preferredAge: Int?) {
    val isDone = remember {
        mutableStateOf(false)
    }

    val isError = remember{
        mutableStateOf(false)
    }

    val currentStep = remember {
        mutableStateOf(PredictionProgressTypeModel.LOAD_DATA)
    }

    AgingHelperTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = AgingHelperColorPalette.current.background
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "예측 진행 중",
                    color = AgingHelperColorPalette.current.txtColor,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Text(
                    text = "AgingHelper에서 요청하신 작업을 처리하고 있습니다.\n잠시 기다려 주십시오.",
                    fontSize = 12.sp,
                    color = gray,
                    textAlign = TextAlign.Center,
                    lineHeight = 12.sp
                )

                Spacer(modifier = Modifier.weight(1f))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (currentStep.value == PredictionProgressTypeModel.LOAD_DATA && !isError.value) {
                        CircularProgressIndicator(color = accent, modifier = Modifier.size(12.dp), strokeWidth = 2.dp)
                        Spacer(modifier = Modifier.width(10.dp))
                    } else if(currentStep.value != PredictionProgressTypeModel.LOAD_DATA){
                        Icon(imageVector = Icons.Rounded.Check, contentDescription = null, tint = green, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(5.dp))
                    } else if(currentStep.value == PredictionProgressTypeModel.LOAD_DATA && isError.value){
                        Icon(imageVector = Icons.Rounded.Error, contentDescription = null, tint = red, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(5.dp))
                    }

                    Text(
                        text = "데이터 로드 중",
                        color = if (currentStep.value == PredictionProgressTypeModel.LOAD_DATA) AgingHelperColorPalette.current.txtColor else gray,
                        fontWeight = if(currentStep.value == PredictionProgressTypeModel.LOAD_DATA) FontWeight.SemiBold else FontWeight.Medium,
                        fontSize = if(currentStep.value == PredictionProgressTypeModel.LOAD_DATA) 15.sp else 12.sp
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (currentStep.value == PredictionProgressTypeModel.IN_PREDICTION && !isError.value) {
                        CircularProgressIndicator(color = accent, modifier = Modifier.size(12.dp), strokeWidth = 2.dp)
                        Spacer(modifier = Modifier.width(10.dp))
                    } else if(currentStep.value != PredictionProgressTypeModel.IN_PREDICTION && currentStep.value != PredictionProgressTypeModel.LOAD_DATA){
                        Icon(imageVector = Icons.Rounded.Check, contentDescription = null, tint = green, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(5.dp))
                    } else if(currentStep.value == PredictionProgressTypeModel.IN_PREDICTION && isError.value){
                        Icon(imageVector = Icons.Rounded.Error, contentDescription = null, tint = red, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(5.dp))
                    }

                    Text(
                        text = "예측 진행 중",
                        color = if (currentStep.value == PredictionProgressTypeModel.IN_PREDICTION) AgingHelperColorPalette.current.txtColor else gray,
                        fontWeight = if(currentStep.value == PredictionProgressTypeModel.IN_PREDICTION) FontWeight.SemiBold else FontWeight.Medium,
                        fontSize = if(currentStep.value == PredictionProgressTypeModel.IN_PREDICTION) 15.sp else 12.sp
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (currentStep.value == PredictionProgressTypeModel.SAVE_RESULTS && !isError.value && !isDone.value) {
                        CircularProgressIndicator(color = accent, modifier = Modifier.size(12.dp), strokeWidth = 2.dp)
                        Spacer(modifier = Modifier.width(10.dp))
                    } else if(currentStep.value == PredictionProgressTypeModel.SAVE_RESULTS && isError.value){
                        Icon(imageVector = Icons.Rounded.Error, contentDescription = null, tint = red, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(5.dp))
                    } else if(isDone.value){
                        Icon(imageVector = Icons.Rounded.Check, contentDescription = null, tint = green, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(5.dp))
                    }

                    Text(
                        text = "결과 저장 중",
                        color = if (currentStep.value == PredictionProgressTypeModel.SAVE_RESULTS && !isDone.value) AgingHelperColorPalette.current.txtColor else gray,
                        fontWeight = if(currentStep.value == PredictionProgressTypeModel.SAVE_RESULTS && !isDone.value) FontWeight.SemiBold else FontWeight.Medium,
                        fontSize = if(currentStep.value == PredictionProgressTypeModel.SAVE_RESULTS && !isDone.value) 15.sp else 12.sp
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                if (isDone.value) {
                    Text(
                        text = "AgingHelper에서 요청하신 작업을 모두 완료하였습니다.",
                        fontSize = 12.sp,
                        color = gray,
                        textAlign = TextAlign.Center,
                        lineHeight = 12.sp
                    )

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Spacer(modifier = Modifier.weight(1f))

                        Button(
                            onClick = { /*TODO*/ },
                            colors = ButtonDefaults.buttonColors(containerColor = accent)
                        ) {
                            Text(text = "결과 확인")
                        }
                    }
                } else if(isError.value){
                    Text(
                        text = "AgingHelper에서 요청하신 작업을 진행하는 중 문제가 발생했습니다.\n모델을 다시 설치하거나, 소프트웨어를 다시 시작한 후 다시 시도해보십시오.",
                        fontSize = 12.sp,
                        color = gray,
                        textAlign = TextAlign.Center,
                        lineHeight = 12.sp
                    )

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Spacer(modifier = Modifier.weight(1f))

                        Button(
                            onClick = { parentNavController.popBackStack() },
                            colors = ButtonDefaults.buttonColors(containerColor = accent)
                        ) {
                            Text(text = "닫기")
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun OnPredictionViewPreview() {
    OnPredictionView(uri = Uri.EMPTY, parentNavController = rememberNavController(), null)
}