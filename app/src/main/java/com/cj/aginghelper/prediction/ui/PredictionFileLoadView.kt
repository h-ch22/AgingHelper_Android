package com.cj.aginghelper.prediction.ui

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.rounded.ArrowBack
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
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PredictionFileLoadView(parentNavController: NavHostController) {
    val navController = rememberNavController()

    val selectedImage = remember {
        mutableStateOf<Uri?>(Uri.EMPTY)
    }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {
            selectedImage.value = it
        }
    )

    val context = LocalContext.current

    NavHost(navController = navController, startDestination = "PredictionFileLoadView") {
        composable("PredictionAgeSelectionView") {
            PredictionAgeSelectionView(
                uri = selectedImage.value!!,
                navHostController = navController
            )
        }

        composable("PredictionFileLoadView") {
            AgingHelperTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
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
                            },
                            colors = TopAppBarDefaults.topAppBarColors(containerColor = AgingHelperColorPalette.current.background)
                        )
                    }
                ) {
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it),

                        color = AgingHelperColorPalette.current.background
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

                                Image(
                                    painter = painter,
                                    contentDescription = null,
                                    modifier = Modifier.height(200.dp)
                                )
                            }

                            Spacer(modifier = Modifier.weight(1f))

                            TextButton(
                                onClick = {
                                    selectedImage.value = null
                                    photoPickerLauncher.launch(
                                        PickVisualMediaRequest(
                                            mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
                                        )
                                    )
                                },
                                colors = ButtonDefaults.textButtonColors(
                                    contentColor = accent
                                ),
                                modifier = Modifier.padding(20.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.PhotoLibrary,
                                    contentDescription = null,
                                    tint = accent
                                )

                                Spacer(modifier = Modifier.width(10.dp))

                                Text(
                                    text = "이미지 불러오기",
                                    color = accent
                                )
                            }

                            Spacer(modifier = Modifier.weight(1f))

                            Row {
                                if (selectedImage.value != null && selectedImage.value != Uri.EMPTY) {
                                    TextButton(onClick = {
                                        photoPickerLauncher.launch(
                                            PickVisualMediaRequest(
                                                mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
                                            )
                                        )
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
                                        navController.navigate("PredictionAgeSelectionView") {
                                            popUpTo("PredictionFileLoadView") {
                                                inclusive = false
                                            }
                                        }
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = accent),
                                    enabled = selectedImage.value != null && selectedImage.value != Uri.EMPTY
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