package com.cj.aginghelper.frameworks.ui

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.ImageCaptureException
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.cj.aginghelper.R
import java.io.File
import java.util.concurrent.Executor

class MainActivity : ComponentActivity() {
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ){

    }

    private fun requestCameraPermission(){
        when{
            ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED -> {

            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                this, Manifest.permission.CAMERA
            ) -> {}

            else -> requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainView()
            requestCameraPermission()
        }
    }
}