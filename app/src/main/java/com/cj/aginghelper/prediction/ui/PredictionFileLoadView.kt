package com.cj.aginghelper.prediction.ui

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.util.Base64
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.ImageCaptureException
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.CameraAlt
import androidx.compose.material.icons.rounded.PhotoLibrary
import androidx.compose.material.icons.rounded.RestartAlt
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.cj.aginghelper.ui.theme.AgingHelperColorPalette
import com.cj.aginghelper.ui.theme.AgingHelperTheme
import com.cj.aginghelper.ui.theme.accent
import com.cj.aginghelper.ui.theme.white
import java.io.ByteArrayOutputStream
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Objects
import java.util.concurrent.Executor

fun createImageFile(context: Context): File {
    val timeStamp = SimpleDateFormat("yyyyMMdd_kkmmss").format(Date())
    val storageDir = context.getExternalFilesDir(
        Environment.DIRECTORY_PICTURES
    )

    return File.createTempFile(
        timeStamp, ".jpg", storageDir
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PredictionFileLoadView(parentNavController: NavHostController) {
    val navController = rememberNavController()

    val selectedType = remember {
        mutableIntStateOf(-1)
    }

    val selectedImage = remember {
        mutableStateOf<Uri?>(Uri.EMPTY)
    }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {
            selectedImage.value = it
        }
    )

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = {

        }
    )

    val context = LocalContext.current

    NavHost(navController = navController, startDestination = "PredictionFileLoadView") {
        composable("PredictionFileLoadView") {
            AgingHelperTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(title = {
                            Text(
                                text = "예측을 위한 파일 로드",
                                color = accent
                            )
                        },
                            navigationIcon = {
                                IconButton(onClick = { parentNavController.popBackStack() }) {
                                    Icon(
                                        imageVector = Icons.Rounded.ArrowBack,
                                        contentDescription = null,
                                        tint = accent
                                    )
                                }
                            })
                    }
                ) {
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it)
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp),
                            verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.PhotoLibrary,
                                contentDescription = null,
                                tint = AgingHelperColorPalette.current.txtColor,
                                modifier = Modifier.size(32.dp)
                            )
                            Text(
                                text = "예측을 위한 파일 로드하기",
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 18.sp,
                                color = AgingHelperColorPalette.current.txtColor
                            )

                            if (selectedImage.value != null && selectedImage.value != Uri.EMPTY) {
                                val painter = rememberAsyncImagePainter(
                                    ImageRequest
                                        .Builder(context)
                                        .data(data = selectedImage.value)
                                        .build()
                                )

                                Spacer(modifier = Modifier.height(20.dp))

                                Image(painter = painter, contentDescription = null)
                            }

                            Spacer(modifier = Modifier.weight(1f))

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Button(
                                    onClick = {
                                        selectedType.intValue = 0
                                        selectedImage.value = null
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = animateColorAsState(
                                            targetValue = if (selectedType.intValue == 0) accent else AgingHelperColorPalette.current.btnColor,
                                            animationSpec = tween(200, 0, LinearEasing),
                                            label = "animate_btn"
                                        ).value,
                                        contentColor = animateColorAsState(
                                            targetValue = if (selectedType.intValue == 0) white else AgingHelperColorPalette.current.txtColor,
                                            animationSpec = tween(200, 0, LinearEasing),
                                            label = "animate_btn"
                                        ).value
                                    ),
                                    modifier = Modifier.padding(20.dp)
                                ) {
                                    Column(
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Icon(
                                            imageVector = Icons.Rounded.CameraAlt,
                                            contentDescription = null,
                                            tint = animateColorAsState(
                                                targetValue = if (selectedType.intValue == 0) white else AgingHelperColorPalette.current.txtColor,
                                                animationSpec = tween(200, 0, LinearEasing),
                                                label = "animate_icon"
                                            ).value
                                        )
                                        Text(
                                            text = "사진 촬영",
                                            color = animateColorAsState(
                                                targetValue = if (selectedType.intValue == 0) white else AgingHelperColorPalette.current.txtColor,
                                                animationSpec = tween(200, 0, LinearEasing),
                                                label = "animate_text"
                                            ).value
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.weight(1f))

                                Button(
                                    onClick = {
                                        selectedType.intValue = 1
                                        selectedImage.value = null
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = animateColorAsState(
                                            targetValue = if (selectedType.intValue == 1) accent else AgingHelperColorPalette.current.btnColor,
                                            animationSpec = tween(200, 0, LinearEasing),
                                            label = "animate_btn"
                                        ).value,
                                        contentColor = animateColorAsState(
                                            targetValue = if (selectedType.intValue == 1) white else AgingHelperColorPalette.current.txtColor,
                                            animationSpec = tween(200, 0, LinearEasing),
                                            label = "animate_btn"
                                        ).value
                                    ),
                                    modifier = Modifier.padding(20.dp)
                                ) {
                                    Column(
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Icon(
                                            imageVector = Icons.Rounded.PhotoLibrary,
                                            contentDescription = null,
                                            tint = animateColorAsState(
                                                targetValue = if (selectedType.intValue == 1) white else AgingHelperColorPalette.current.txtColor,
                                                animationSpec = tween(200, 0, LinearEasing),
                                                label = "animate_icon"
                                            ).value
                                        )
                                        Text(
                                            text = "사진 불러오기",
                                            color = animateColorAsState(
                                                targetValue = if (selectedType.intValue == 1) white else AgingHelperColorPalette.current.txtColor,
                                                animationSpec = tween(200, 0, LinearEasing),
                                                label = "animate_text"
                                            ).value
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.weight(1f))

                            Row {
                                if (selectedImage.value != null && selectedImage.value != Uri.EMPTY) {
                                    TextButton(onClick = {
                                        if (selectedType.intValue == 1) {
                                            photoPickerLauncher.launch(
                                                PickVisualMediaRequest(
                                                    mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
                                                )
                                            )
                                        }
                                    }) {
                                        Icon(
                                            imageVector = Icons.Rounded.RestartAlt,
                                            contentDescription = null,
                                            tint = accent
                                        )

                                        Text(text = "다시 선택하기", color = accent)
                                    }
                                }

                                Spacer(modifier = Modifier.weight(1f))

                                Button(
                                    onClick = {
                                        if (selectedType.intValue == 1 && selectedImage.value == null && selectedImage.value == Uri.EMPTY) {
                                            photoPickerLauncher.launch(
                                                PickVisualMediaRequest(
                                                    mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
                                                )
                                            )
                                        } else if(selectedType.intValue == 0 && selectedImage.value == null && selectedImage.value == Uri.EMPTY){
                                            val file = createImageFile(context)
                                            val imageUri = FileProvider.getUriForFile(
                                                context, "com.cj.aginghelper", file
                                            )

                                            cameraLauncher.launch(imageUri)
                                        }
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = accent),
                                    enabled = (selectedType.intValue == 0 || selectedType.intValue == 1)
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

@Preview
@Composable
fun PredictionFileLoadViewPreview() {
    PredictionFileLoadView(parentNavController = rememberNavController())
}