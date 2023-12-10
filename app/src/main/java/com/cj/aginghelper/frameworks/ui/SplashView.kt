package com.cj.aginghelper.frameworks.ui

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cj.aginghelper.R
import com.cj.aginghelper.frameworks.helper.ResourcesHelper
import com.cj.aginghelper.frameworks.models.CheckResourceTypeModel
import com.cj.aginghelper.frameworks.models.ResourceTypeModel
import com.cj.aginghelper.ui.theme.AgingHelperColorPalette
import com.cj.aginghelper.ui.theme.AgingHelperTheme
import com.cj.aginghelper.ui.theme.accent
import com.cj.aginghelper.ui.theme.gray
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch


@Composable
fun SplashView() {
    val currentStatus = remember {
        mutableStateOf(CheckResourceTypeModel.CHECK_RESOURCE_EXISTS)
    }

    val showAlert = remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current
    val helper = ResourcesHelper(context)

    AgingHelperTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = AgingHelperColorPalette.current.background
        ) {
            LaunchedEffect(key1 = true) {
                val isModelExists = helper.checkResourceExists()

                if(isModelExists){
                    context.startActivity(Intent(context, MainActivity::class.java))
                } else{
                    currentStatus.value = CheckResourceTypeModel.EXTRACT_RESOURCE

                    helper.extractResource{ result ->
                        if(!result){
                            showAlert.value = true
                        } else{
                            context.startActivity(Intent(context, MainActivity::class.java))
                        }
                    }
                }
            }

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(20.dp)
            ) {
                Spacer(modifier = Modifier.weight(1f))

                Card(
                    modifier = Modifier.size(150.dp),
                    shape = RoundedCornerShape(15.dp),
                    elevation = CardDefaults.cardElevation(5.dp)
                ) {
                    Image(
                        painterResource(R.drawable.ic_logo),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                TextLogo(fontSize = 24)

                Spacer(modifier = Modifier.weight(1f))

                if(!showAlert.value){
                    if(currentStatus.value == CheckResourceTypeModel.CHECK_RESOURCE_EXISTS){
                        CircularProgressIndicator(color = accent)
                    } else{
                        LinearProgressIndicator(progress = (helper.progress.intValue.toFloat()) / 100, color = accent)
                    }

                    Spacer(modifier = Modifier.height(5.dp))

                    Text(text = if(currentStatus.value == CheckResourceTypeModel.CHECK_RESOURCE_EXISTS) "데이터 검사 중"
                                else "데이터 추출 중 (${helper.progress.intValue}%)", fontSize = 12.sp, color = gray)
                } else{
                    Icon(imageVector = Icons.Rounded.Warning, contentDescription = null, tint = accent)
                }
            }

            if (showAlert.value) {
                AlertDialog(onDismissRequest = { showAlert.value = false },
                    title = { Text(text = "오류") },
                    text = { Text(text = "데이터를 추출하는 중 문제가 발생했습니다.\n사용 가능 공간을 확인하거나, 소프트웨어를 재실행한 후 다시 시도하십시오.") },
                    icon = {
                        Icon(
                            imageVector = Icons.Rounded.Warning,
                            contentDescription = null
                        )
                    },
                    confirmButton = {
                        TextButton(
                            onClick = { showAlert.value = false },
                            colors = ButtonDefaults.textButtonColors(contentColor = accent)
                        ) {
                            Text(text = "확인")
                        }
                    })
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SplashViewPreview() {
    SplashView()
}